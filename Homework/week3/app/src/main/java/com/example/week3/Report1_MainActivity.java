package com.example.week3;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Report1_MainActivity extends AppCompatActivity {
    private int type = 1;
    private Button B;
    private TextView TV;
    private ImageView IV;
    private String textFile;
    private Bitmap imageFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report1_main);

        RadioGroup RG = findViewById(R.id.R1_RG);
        RadioButton RB1 = findViewById(R.id.R1_RB1);
        RadioButton RB2 = findViewById(R.id.R1_RB2);
        RadioButton RB3 = findViewById(R.id.R1_RB3);
        B = findViewById(R.id.R1_B);
        TV = findViewById(R.id.R1_TV);
        IV = findViewById(R.id.R1_IV);

        String page = "http://25.20.171.99:8081/week3_Textfile.txt";
        String[] image = {
                "https://postfiles.pstatic.net/MjAyNDA1MjhfMjM2/MDAxNzE2ODg1NTIxMjUx.FlV2lY46kbc_53PyhK1BceEdAUvWcszmx4y8Lad8NGsg.6i6vFuPYj55jtmqPP7hyTslpDbrFPyolX6MZKrQRO8Ig.JPEG/KakaoTalk_20240528_172858818_02.jpg?type=w580",
                "https://postfiles.pstatic.net/MjAyNDA1MjhfMjgw/MDAxNzE2ODg1NTIxMjkw.V7koBD6-HwcqadcmBJtmbWdFldyLeNizNpYThgpTFJ0g.Jd1XO0vGJqAwTWXbZrQPvMuogOh25bmTgYUa5RF9ZH4g.JPEG/KakaoTalk_20240528_172858818_03.jpg?type=w580",
                "https://postfiles.pstatic.net/MjAyNDA1MjhfMjcz/MDAxNzE2ODg1NTIxMjQ1.qDPkQa-gtatLwcxvNC2ia4wgoDIj_wrSOOPJG-zLcLAg.TMNlXGb_giGAwMw9GFu7DrdxzBvbCBUeqgNoBKWPVw8g.JPEG/KakaoTalk_20240528_172858818.jpg?type=w580"
        };

        if (RB1.isChecked()) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            textFile = StrictModeCon.SM_Text(Report1_MainActivity.this, page);
            imageFile = StrictModeCon.SM_Image(Report1_MainActivity.this, image[0]);
        }

        RG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.R1_RB1) {
                    Toast.makeText(Report1_MainActivity.this, "StricMode", Toast.LENGTH_SHORT).show();
                } else if (i == R.id.R1_RB2) {
                    Toast.makeText(Report1_MainActivity.this, "AsyncTask", Toast.LENGTH_SHORT).show();
                } else if (i == R.id.R1_RB3) {
                    Toast.makeText(Report1_MainActivity.this, "Thread", Toast.LENGTH_SHORT).show();
                }
            }
        });

        B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type == 1) {    // TextFile
                    if (RB1.isChecked()) {
                        textFile = StrictModeCon.SM_Text(Report1_MainActivity.this, page);
                        TV.setTextColor(Color.BLACK);
                        TV.setText(textFile);
                    } else if (RB2.isChecked()) {
                        TextAsync async = new TextAsync(Report1_MainActivity.this, TV);
                        async.execute(page);
                    } else if (RB3.isChecked()) {
                        TextThread thread = new TextThread(page, Report1_MainActivity.this);
                        thread.run();
                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                String result = thread.getResult();
                                TV.setTextColor(Color.BLUE);
                                TV.setText(result);
                            }
                        });
                    }

                } else if (type == 2) { // ImageFile
                    if (RB1.isChecked()) {
                        imageFile = StrictModeCon.SM_Image(Report1_MainActivity.this, image[0]);
                        IV.setImageBitmap(imageFile);
                    } else if (RB2.isChecked()) {
                        ImageAsync async = new ImageAsync(Report1_MainActivity.this, image);
                        async.execute(image);
                    } else if (RB3.isChecked()) {
                        ImageThread thread = new ImageThread(Report1_MainActivity.this, image);
                        thread.run();

                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                Bitmap result = thread.getResult();
                                IV.setImageBitmap(result);
                            }
                        });
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.report1_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.item1) {
            type = 1;   // Text
            B.setText("text file download");
            TV.setVisibility(View.VISIBLE);
            IV.setVisibility(View.INVISIBLE);
        } else {
            type = 2;   // Image
            B.setText("image file download");
            TV.setVisibility(View.INVISIBLE);
            IV.setVisibility(View.VISIBLE);
        }

        item.setChecked(true);
        return true;
    }
}