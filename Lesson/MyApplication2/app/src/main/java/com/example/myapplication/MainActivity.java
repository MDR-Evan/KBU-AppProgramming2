package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.google.zxing.qrcode.QRCodeWriter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button btnJsonToSqlite, btnSalaryDesc, btnSalaryAsc, btnMale, btnFemale;
    private RecyclerView rvEmployees;
    private TextView tvEmpty;
    private EditText etQrInput;
    private ImageView ivQrCode;
    private Button btnScanQr;

    private DBHelper dbHelper;
    private EmployeeAdapter adapter;

    // 정렬·필터 상태
    private boolean sortAsc = false;
    @Nullable private String genderFilter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DBHelper(this);

        // 뷰 바인딩
        btnJsonToSqlite = findViewById(R.id.btnJsonToSqlite);
        btnSalaryDesc   = findViewById(R.id.btnSalaryDesc);
        btnSalaryAsc    = findViewById(R.id.btnSalaryAsc);
        btnMale         = findViewById(R.id.btnMale);
        btnFemale       = findViewById(R.id.btnFemale);
        rvEmployees     = findViewById(R.id.rvEmployees);
        tvEmpty         = findViewById(R.id.tvEmpty);

        // RecyclerView & Adapter 설정
        rvEmployees.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EmployeeAdapter(this, new ArrayList<>());
        rvEmployees.setAdapter(adapter);

        // 리스트 클릭 시 QR 자동 생성
        adapter.setOnItemClickListener(e -> {
            etQrInput.setText(e.id);
            generateQr(e.id);
        });

        // 버튼 리스너 등록
        btnJsonToSqlite.setOnClickListener(v -> {
            new JsonToDbTask().execute("http://172.21.96.1:8081/sawon.json");
        });
        btnSalaryDesc.setOnClickListener(v -> { sortAsc = false;    loadAndShow(); });
        btnSalaryAsc.setOnClickListener(v -> { sortAsc = true;     loadAndShow(); });
        btnMale.setOnClickListener(      v -> { genderFilter = "남자"; loadAndShow(); });
        btnFemale.setOnClickListener(    v -> { genderFilter = "여자"; loadAndShow(); });

        // QR 스캔 버튼
        btnScanQr.setOnClickListener(v -> {
            IntentIntegrator integrator = new IntentIntegrator(this);
            integrator.setPrompt("QR 코드를 스캔하세요");
            integrator.setBeepEnabled(true);
            integrator.initiateScan();
        });

        // 초기 로드
        loadAndShow();
    }

    private void loadAndShow() {
        List<Employee> list = dbHelper.getEmployees(genderFilter, sortAsc);
        adapter.getItems().clear();
        adapter.getItems().addAll(list);
        adapter.notifyDataSetChanged();

        if (list.isEmpty()) {
            rvEmployees.setVisibility(View.GONE);
            tvEmpty.setVisibility(View.VISIBLE);
        } else {
            rvEmployees.setVisibility(View.VISIBLE);
            tvEmpty.setVisibility(View.GONE);
        }
    }

    private void generateQr(String text) {
        try {
            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix matrix = writer.encode(text, BarcodeFormat.QR_CODE, 400, 400);
            Bitmap bmp = Bitmap.createBitmap(400, 400, Bitmap.Config.RGB_565);
            for (int x = 0; x < 400; x++) {
                for (int y = 0; y < 400; y++) {
                    bmp.setPixel(x, y, matrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            ivQrCode.setImageBitmap(bmp);
        } catch (Exception ex) {
            Toast.makeText(this, "QR 생성 오류: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int req, int res, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(req, res, data);
        if (result != null && result.getContents() != null) {
            etQrInput.setText(result.getContents());
            generateQr(result.getContents());
        } else {
            super.onActivityResult(req, res, data);
        }
    }

    // --- JSON → SQLite 저장 AsyncTask ---
    private class JsonToDbTask extends AsyncTask<String, Void, Integer> {
        @Override protected Integer doInBackground(String... urls) {
            String jsonStr = fetchJson(urls[0]);
            if (jsonStr == null) return 0;
            try {
                JSONArray arr = new JSONArray(jsonStr);
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);
                    Employee e = new Employee(
                            obj.getString("id"),
                            obj.getString("name"),
                            obj.getString("gender"),
                            obj.getInt("salary"),
                            obj.optString("imageUrl","")
                    );
                    dbHelper.insertEmployee(e);
                }
                return arr.length();
            } catch (JSONException ex) {
                ex.printStackTrace();
                return 0;
            }
        }
        @Override protected void onPostExecute(Integer count) {
            Toast.makeText(MainActivity.this,
                    count > 0
                            ? count + "명 DB 저장 완료"
                            : "저장 실패",
                    Toast.LENGTH_SHORT).show();
            if (count > 0) loadAndShow();
        }
        private String fetchJson(String urlStr) {
            HttpURLConnection conn = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(urlStr);
                conn = (HttpURLConnection)url.openConnection();
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);
                InputStream in = conn.getInputStream();
                reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) sb.append(line);
                return sb.toString();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } finally {
                try { if (reader != null) reader.close(); }
                catch (IOException ignored) {}
                if (conn != null) conn.disconnect();
            }
        }
    }
}
