package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private TextView tvResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets sys = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(sys.left, sys.top, sys.right, sys.bottom);
            return insets;
        });

        dbHelper = new DBHelper(this);
        tvResults = findViewById(R.id.tvResults);

        // 버튼 바인딩
        Button btnInsert    = findViewById(R.id.btnInsert);
        Button btnSearch    = findViewById(R.id.btnSearch);
        Button btnDelete1   = findViewById(R.id.btnDelete1);
        Button btnDelete2   = findViewById(R.id.btnDelete2);
        Button btnDelete3   = findViewById(R.id.btnDelete3);
        Button btnDeleteAll = findViewById(R.id.btnDeleteAll);
        Button btnUpdate1   = findViewById(R.id.btnUpdate1);
        Button btnUpdate2   = findViewById(R.id.btnUpdate2);

        btnInsert.setOnClickListener(v -> {
            List<Product> list = Arrays.asList(
                    new Product("CD-0100", "Cookie", 150f),
                    new Product("CD-0101", "Candy", 80.5f),
                    new Product("CD-0102", "Cake", 360f)
            );
            dbHelper.insertProducts(list);
            Toast.makeText(this, "데이터 삽입 완료", Toast.LENGTH_SHORT).show();
            refreshResults();
        });

        btnSearch.setOnClickListener(v -> {
            refreshResults();
            Toast.makeText(this, "조회 완료", Toast.LENGTH_SHORT).show();
        });

        btnDelete1.setOnClickListener(v -> {
            dbHelper.deleteProduct("CD-0100");
            Toast.makeText(this, "CD-0100 삭제 완료", Toast.LENGTH_SHORT).show();
            refreshResults();
        });

        btnDelete2.setOnClickListener(v -> {
            dbHelper.deleteProductTransactional("CD-0101");
            Toast.makeText(this, "CD-0101 삭제 완료", Toast.LENGTH_SHORT).show();
            refreshResults();
        });

        btnDelete3.setOnClickListener(v -> {
            dbHelper.deleteProductTransactionalStatement("CD-0102");
            Toast.makeText(this, "CD-0102 삭제 완료 ", Toast.LENGTH_SHORT).show();
            refreshResults();
        });

        btnDeleteAll.setOnClickListener(v -> {
            dbHelper.deleteAllTransactionalStatement();
            Toast.makeText(this, "모든 레코드 삭제 완료", Toast.LENGTH_SHORT).show();
            refreshResults();
        });

        btnUpdate1.setOnClickListener(v -> {
            dbHelper.updateAllPricesReplace(2000f);
            Toast.makeText(this, "모든 가격 2000으로 변경", Toast.LENGTH_SHORT).show();
            refreshResults();
        });

        btnUpdate2.setOnClickListener(v -> {
            dbHelper.updateAllPrices(3000f);
            Toast.makeText(this, "모든 가격 3000으로 변경", Toast.LENGTH_SHORT).show();
            refreshResults();
        });

        // 앱 실행 시 최초 결과 표시
        refreshResults();
    }

    // DB에서 가져온 상품 리스트를 문자열로 변환해 TextView에 세팅
    private void refreshResults() {
        List<Product> all = dbHelper.getAllProducts();
        if (all.isEmpty()) {
            tvResults.setText("저장된 상품이 없습니다.");
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (Product p : all) {
            sb.append(p.getCode())
                    .append(" - ").append(p.getName())
                    .append(" - ").append(p.getPrice())
                    .append("\n");
        }
        tvResults.setText(sb.toString());
    }
}
