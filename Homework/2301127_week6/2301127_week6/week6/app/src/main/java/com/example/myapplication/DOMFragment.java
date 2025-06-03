package com.example.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;

public class DOMFragment extends Fragment {
    private static final String XML_URL = "http://127.0.0.1:8081/week6_country.xml";

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_list, container, false);
        ListView lv = root.findViewById(R.id.listViewParsed);
        new FetchDOMTask(lv).execute(XML_URL);
        return root;
    }

    private static class FetchDOMTask extends AsyncTask<String, Void, List<String>> {
        private final ListView listView;
        FetchDOMTask(ListView lv) { this.listView = lv; }

        @Override
        protected List<String> doInBackground(String... urls) {
            List<String> data = new ArrayList<>();
            HttpURLConnection conn = null;
            try {
                URL url = new URL(urls[0]);
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);
                try (InputStream is = conn.getInputStream()) {
                    Document doc = DocumentBuilderFactory
                            .newInstance()
                            .newDocumentBuilder()
                            .parse(is);
                    NodeList countries = doc.getElementsByTagName("country");
                    for (int i = 0; i < countries.getLength(); i++) {
                        Element el = (Element) countries.item(i);
                        String name = el.getAttribute("name");
                        String flag = el.getAttribute("flag");
                        String lang = el.getElementsByTagName("language")
                                .item(0).getTextContent().trim();
                        String capital = el.getElementsByTagName("capital")
                                .item(0)
                                .getAttributes()
                                .getNamedItem("city")
                                .getTextContent();
                        Element curEl = (Element) el.getElementsByTagName("currency")
                                .item(0);
                        String currCode = curEl.getAttribute("code");
                        String currName = curEl.getTextContent().trim();
                        data.add(String.format(
                                "국가: %s\n플래그: %s\n언어: %s\n수도: %s\n통화: %s (%s)",
                                name, flag, lang, capital, currName, currCode
                        ));
                    }
                }
            } catch (Exception e) {
                data.clear();
                data.add("DOM 파싱 오류: " + e.getMessage());
            } finally {
                if (conn != null) conn.disconnect();
            }
            return data;
        }

        @Override
        protected void onPostExecute(List<String> result) {
            listView.setAdapter(new ArrayAdapter<>(
                    listView.getContext(),
                    android.R.layout.simple_list_item_1,
                    result
            ));
        }
    }
}
