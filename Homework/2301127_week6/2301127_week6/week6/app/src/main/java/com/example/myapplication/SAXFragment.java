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
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParserFactory;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SAXFragment extends Fragment {
    private static final String XML_URL = "http://121.163.245.130:8081/week6_country.xml";

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_list, container, false);
        ListView lv = root.findViewById(R.id.listViewParsed);
        new FetchSAXTask(lv).execute(XML_URL);
        return root;
    }

    private static class FetchSAXTask extends AsyncTask<String, Void, List<String>> {
        private final ListView lv;
        FetchSAXTask(ListView lv) { this.lv = lv; }

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
                    SAXParserFactory.newInstance()
                            .newSAXParser()
                            .parse(is, new DefaultHandler() {
                                boolean inCountry = false;
                                StringBuilder sb;

                                @Override
                                public void startElement(String uri, String localName,
                                                         String qName, Attributes attributes) {
                                    if ("country".equals(qName)) {
                                        inCountry = true;
                                        sb = new StringBuilder("국가: ")
                                                .append(attributes.getValue("name"));
                                    }
                                    if (inCountry && "language".equals(qName)) {
                                        sb.append("\n언어: ");
                                    }
                                    if (inCountry && "capital".equals(qName)) {
                                        sb.append("\n수도: ")
                                                .append(attributes.getValue("city"));
                                    }
                                    if (inCountry && "currency".equals(qName)) {
                                        sb.append("\n통화: ")
                                                .append(attributes.getValue("code"))
                                                .append(" ");
                                    }
                                }

                                @Override
                                public void characters(char[] ch, int start, int length) {
                                    if (inCountry) {
                                        sb.append(new String(ch, start, length).trim());
                                    }
                                }

                                @Override
                                public void endElement(String uri, String localName,
                                                       String qName) {
                                    if ("country".equals(qName)) {
                                        data.add(sb.toString());
                                        inCountry = false;
                                    }
                                }
                            });
                }
            } catch (Exception e) {
                data.clear();
                data.add("SAX 파싱 오류: " + e.getMessage());
            } finally {
                if (conn != null) conn.disconnect();
            }
            return data;
        }

        @Override
        protected void onPostExecute(List<String> result) {
            lv.setAdapter(new ArrayAdapter<>(
                    lv.getContext(),
                    android.R.layout.simple_list_item_1,
                    result
            ));
        }
    }
}
