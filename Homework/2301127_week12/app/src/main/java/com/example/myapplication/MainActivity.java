package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DBHelper   dbHelper;
    private TextView   tvResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main),
                (v, insets) -> {
                    Insets sys = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                    v.setPadding(sys.left, sys.top, sys.right, sys.bottom);
                    return insets;
                }
        );

        dbHelper  = new DBHelper(this);
        tvResults = findViewById(R.id.tvResults);

        Button btnViewCustomers = findViewById(R.id.btnViewCustomers);
        Button btnViewProducts  = findViewById(R.id.btnViewProducts);
        Button btnViewSales     = findViewById(R.id.btnViewSales);
        Button btnViewHong      = findViewById(R.id.btnViewHong);
        Button btnViewKim       = findViewById(R.id.btnViewKim);

        btnViewCustomers.setOnClickListener(v -> showCustomers());
        btnViewProducts .setOnClickListener(v -> showProducts());
        btnViewSales    .setOnClickListener(v -> showSales());
        btnViewHong     .setOnClickListener(v -> showPurchases("홍길동"));
        btnViewKim      .setOnClickListener(v -> showPurchases("김대한"));

        // 최초 한 번 표시
        showCustomers();
    }

    private void showCustomers() {
        List<Customer> list = dbHelper.getAllCustomers();
        StringBuilder sb = new StringBuilder();
        sb.append("--customer table--\n");
        for (Customer c : list) {
            sb.append("id : ").append(c.getCid())
                    .append(", 고객명 : ").append(c.getCname())
                    .append("\n");
        }
        tvResults.setText(sb.toString());
    }

    private void showProducts() {
        List<Product> list = dbHelper.getAllProducts();
        StringBuilder sb = new StringBuilder();
        sb.append("--product table--\n");
        for (Product p : list) {
            sb.append("id : ").append(p.getPid())
                    .append(", 제품명 : ").append(p.getPname())
                    .append(", 가격 : ").append(p.getCost())
                    .append("\n");
        }
        tvResults.setText(sb.toString());
    }

    private void showSales() {
        List<Sale> list = dbHelper.getAllSales();
        StringBuilder sb = new StringBuilder();
        sb.append("--sale table--\n");
        for (Sale s : list) {
            sb.append("주문번호 : ").append(s.getOrdNo())
                    .append(", 고객번호 : ").append(s.getCid())
                    .append(", 상품코드 : ").append(s.getPid())
                    .append(", 수량 : ").append(s.getQty())
                    .append("\n");
        }
        tvResults.setText(sb.toString());
    }

    private void showPurchases(String cname) {
        List<Purchase> list = dbHelper.getPurchasesByCustomerName(cname);
        StringBuilder sb = new StringBuilder();
        sb.append("--").append(cname).append(" 구매 목록--\n");
        for (Purchase p : list) {
            sb.append("제품명 : ").append(p.getPname())
                    .append(" | 수량 : ").append(p.getQty())
                    .append(" | 가격 : ").append(p.getCost())
                    .append(" | 총금액 : ").append(p.getTotal())
                    .append("\n");
        }
        if (list.isEmpty()) {
            sb.append("구매 내역이 없습니다.\n");
        }
        tvResults.setText(sb.toString());
    }

}
