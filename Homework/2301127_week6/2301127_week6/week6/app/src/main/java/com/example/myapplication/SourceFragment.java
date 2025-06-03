package com.example.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SourceFragment extends Fragment {
    private static final String XML_URL = "http://192.168.93.1:8081/week6_country.xml";

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_source, container, false);
        TextView tv = root.findViewById(R.id.tvSourceCode);
        new FetchSourceTask(tv).execute(XML_URL);
        return root;
    }

    private static class FetchSourceTask extends AsyncTask<String, Void, String> {
        private final TextView tv;
        FetchSourceTask(TextView tv) { this.tv = tv; }

        @Override
        protected String doInBackground(String... urls) {
            StringBuilder sb = new StringBuilder();
            HttpURLConnection conn = null;
            try {
                URL url = new URL(urls[0]);
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);
                try (InputStream is = conn.getInputStream();
                     BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line).append('\n');
                    }
                }
                return sb.toString();
            } catch (Exception e) {
                return "소스 로드 오류: " + e.getMessage();
            } finally {
                if (conn != null) conn.disconnect();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            tv.setText(result);
        }
    }
}
