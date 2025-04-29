package com.example.week8;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SourceFragment extends Fragment {
    private TextView tvResult;
    private MyApplication app;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_source, container, false);
        tvResult = root.findViewById(R.id.tvResult);
        // Application 인스턴스 가져오기
        app = (MyApplication) requireActivity().getApplication();
        fetchAndDisplayRawXml();
        return root;
    }

    /** Application#getPage() 으로 URL 을 얻어와 원본 XML 표시 */
    private void fetchAndDisplayRawXml() {
        new Thread(() -> {
            StringBuilder sb = new StringBuilder();
            try {
                // MyApplication 에 설정된 URL 획득
                String pageUrl = app.getPage(0); // e.g. "http://192.168.93.1:8081/weather.xml"
                URL url = new URL(pageUrl);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(conn.getInputStream(), "UTF-8")
                );
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                reader.close();
                conn.disconnect();

            } catch (Exception e) {
                sb.append("Error: ").append(e.getMessage());
                e.printStackTrace();
            }

            // UI 스레드에서 TextView 에 설정
            if (getActivity() != null) {
                requireActivity().runOnUiThread(() ->
                        tvResult.setText(sb.toString())
                );
            }
        }).start();
    }
}
