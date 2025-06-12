// 파일 위치: app/src/main/java/com/example/afinal/DownloadRunnable.java
package com.example.afinal;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class DownloadRunnable implements Runnable {
    private final Context     context;
    private final String      url;
    private final Handler     handler;
    private final SawonDBHelper dbHelper;

    public DownloadRunnable(Context context,
                            String url,
                            Handler handler,
                            SawonDBHelper dbHelper) {
        this.context   = context;
        this.url       = url;
        this.handler   = handler;
        this.dbHelper  = dbHelper;
    }

    @Override
    public void run() {
        try {
            // 1) HTTP 연결
            URL u = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            InputStream is = conn.getInputStream();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(is, StandardCharsets.UTF_8)
            );

            // 2) 전체 읽기
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            reader.close();
            conn.disconnect();

            String json = builder.toString();

            // 3) UI 스레드에서 파싱 & 콜백
            handler.post(() -> {
                parseAndSave(json);
                onParseComplete();
            });

        } catch (Exception e) {
            handler.post(() ->
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show()
            );
        }
    }

    /** JSON 파싱 후 DB 저장 (public으로 변경) */
    public void parseAndSave(String json) {
        try {
            JSONArray array = new JSONArray(json);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                Sawon s = new Sawon();
                s.setId(obj.getInt("id"));
                s.setName(obj.getString("name"));
                s.setGender(obj.getString("gender"));
                s.setSalary(obj.getInt("salary"));
                s.setImageUrl(obj.getString("imageUrl"));
                dbHelper.insertSawon(s);
            }
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /** 파싱 완료 시 호출되는 콜백 (익명 서브클래스에서 override 가능) */
    protected void onParseComplete() { }
}
