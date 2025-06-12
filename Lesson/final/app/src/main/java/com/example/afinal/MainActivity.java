package com.example.afinal;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.afinal.adapter.SawonAdapter;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Handler       handler;
    private SawonDBHelper dbHelper;
    private ListView      listView;
    private TextView      tvEmpty;
    private Button        btnLoadJson, btnDesc, btnAsc, btnMale, btnFemale;

    // 상태 변수
    private boolean ascOrder = false;    // 내림차순(true) / 오름차순(false)
    private String  gender   = "남";     // "남" or "여"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler     = new Handler(Looper.getMainLooper());
        dbHelper    = new SawonDBHelper(this);
        listView    = findViewById(R.id.listViewSawon);
        tvEmpty     = findViewById(R.id.tvEmpty);
        btnLoadJson = findViewById(R.id.btnLoadJson);
        btnDesc     = findViewById(R.id.btnDesc);
        btnAsc      = findViewById(R.id.btnAsc);
        btnMale     = findViewById(R.id.btnMale);
        btnFemale   = findViewById(R.id.btnFemale);

        // ListView가 비었을 때 빈 뷰로 TextView를 자동으로 보여주게 설정
        listView.setEmptyView(tvEmpty);

        // 버튼 클릭 리스너 등록
        btnLoadJson.setOnClickListener(v -> loadJsonToSqlite());
        btnDesc.setOnClickListener(v -> {
            ascOrder = false;
            highlightButton(btnDesc, btnAsc);
            loadList();
        });
        btnAsc.setOnClickListener(v -> {
            ascOrder = true;
            highlightButton(btnAsc, btnDesc);
            loadList();
        });
        btnMale.setOnClickListener(v -> {
            gender = "남";
            highlightButton(btnMale, btnFemale);
            loadList();
        });
        btnFemale.setOnClickListener(v -> {
            gender = "여";
            highlightButton(btnFemale, btnMale);
            loadList();
        });

        // 초기 하이라이트
        highlightButton(btnDesc, btnAsc);
        highlightButton(btnMale, btnFemale);

        // 초기 리스트 로드 (DB에 아직 없으면 빈 화면)
        loadList();
    }

    /** JSON → SQLite 로딩 */
    private void loadJsonToSqlite() {
        // 빈뷰 메시지 변경
        tvEmpty.setText("JSON 데이터가 준비되지 않았습니다");
        tvEmpty.setVisibility(View.VISIBLE);

        new Thread(new DownloadRunnable(this,
                "http://10.0.2.2:8080/sawon.json",
                handler, dbHelper) {
            @Override
            protected void onParseComplete() {
                runOnUiThread(() -> {
                    Toast.makeText(
                            MainActivity.this,
                            "완료했습니다",
                            Toast.LENGTH_SHORT
                    ).show();
                    loadList();
                });
            }
        }).start();
    }

    /** DB에서 필터·정렬 후 리스트 바인딩 */
    private void loadList() {
        List<Sawon> data = dbHelper.getSawonByGender(gender, ascOrder);
        SawonAdapter adapter = new SawonAdapter(this, data, false);
        listView.setAdapter(adapter);
        // 빈 리스트라면 emptyView가 보이고, 아니면 ListView가 보입니다.
        if (data.isEmpty()) {
            tvEmpty.setText("데이터가 없습니다");
        }
        listView.setOnItemClickListener((p, view, pos, id) ->
                showDetailDialog(adapter.getItem(pos))
        );
    }

    /** 클릭된 사원 상세 다이얼로그 */
    private void showDetailDialog(Sawon s) {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        View dlg = getLayoutInflater()
                .inflate(R.layout.dialog_sawon_detail, null);
        b.setView(dlg);

        ImageView imgProfile = dlg.findViewById(R.id.imgDetailProfile);
        TextView  tvName     = dlg.findViewById(R.id.tvDetailName);
        TextView  tvId       = dlg.findViewById(R.id.tvDetailId);
        ImageView imgQr      = dlg.findViewById(R.id.imgQrCode);

        // 1) 프로필 이미지
        Glide.with(this)
                .load(s.getImageUrl())
                .placeholder(android.R.drawable.ic_menu_report_image)
                .centerCrop()
                .into(imgProfile);

        // 2) 이름·사번
        tvName.setText(s.getName());
        tvId.setText("사번: " + s.getId());

        // 3) QR 코드
        try {
            BitMatrix matrix = new MultiFormatWriter()
                    .encode(String.valueOf(s.getId()),
                            BarcodeFormat.QR_CODE, 200, 200);
            Bitmap qrBmp = new BarcodeEncoder().createBitmap(matrix);
            imgQr.setImageBitmap(qrBmp);
        } catch (Exception e) {
            e.printStackTrace();
        }

        b.setPositiveButton("확인", null)
                .setTitle("선택한 사원(" + s.getName() + ")")
                .show();
    }

    /** 선택된 버튼만 배경 강조 */
    private void highlightButton(Button selected, Button other) {
        selected.setEnabled(false);
        other.setEnabled(true);
    }
}
