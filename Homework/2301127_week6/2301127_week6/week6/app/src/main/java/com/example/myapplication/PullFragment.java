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
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PullFragment extends Fragment {
    private static final String XML_URL = "http://127.0.0.1:8081/week6_country.xml";

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_list, container, false);
        ListView lv = root.findViewById(R.id.listViewParsed);
        new FetchPullTask(lv).execute(XML_URL);
        return root;
    }

    private static class FetchPullTask extends AsyncTask<String, Void, List<String>> {
        private final ListView lv;
        FetchPullTask(ListView lv) { this.lv = lv; }

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
                    XmlPullParser parser = XmlPullParserFactory
                            .newInstance()
                            .newPullParser();
                    parser.setInput(is, "UTF-8");

                    int eventType = parser.getEventType();
                    StringBuilder sb = null;
                    while (eventType != XmlPullParser.END_DOCUMENT) {
                        String name = parser.getName();
                        switch (eventType) {
                            case XmlPullParser.START_TAG:
                                if ("country".equals(name)) {
                                    sb = new StringBuilder("국가: ")
                                            .append(parser.getAttributeValue(null, "name"));
                                }
                                if (sb != null && "language".equals(name)) {
                                    sb.append("\n언어: ");
                                }
                                if (sb != null && "capital".equals(name)) {
                                    sb.append("\n수도: ")
                                            .append(parser.getAttributeValue(null, "city"));
                                }
                                if (sb != null && "currency".equals(name)) {
                                    sb.append("\n통화: ")
                                            .append(parser.getAttributeValue(null, "code"))
                                            .append(" ");
                                }
                                break;
                            case XmlPullParser.TEXT:
                                if (sb != null) {
                                    sb.append(parser.getText().trim());
                                }
                                break;
                            case XmlPullParser.END_TAG:
                                if ("country".equals(name) && sb != null) {
                                    data.add(sb.toString());
                                    sb = null;
                                }
                                break;
                        }
                        eventType = parser.next();
                    }
                }
            } catch (Exception e) {
                data.clear();
                data.add("PULL 파싱 오류: " + e.getMessage());
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
