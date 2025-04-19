package com.example.weather;

import android.app.Application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MyApplication extends Application {
    private String page = "https://www.kma.go.kr/wid/queryDFS.jsp?gridx=37.656953&gridy=127.177564";
    private String xml;
    private int type = R.id.item1;

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String addDate(String today, int day) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat outFormat = new SimpleDateFormat("MM월 dd일");
        Calendar calendar = Calendar.getInstance();
        try {
            Date date = format.parse(today);
            calendar.setTime(date);
            calendar.add(Calendar.DATE, day);
            return outFormat.format(calendar.getTime());
        } catch (ParseException e) {
            return "날짜 형식 오류";
        }
    }

    public float calculateDiscomfortIndex(float temperature, float humidity) {
        return 0.81f * temperature + 0.01f * humidity
                * (0.99f * temperature - 14.3f) + 46.39f;
    }

    public String getDiscomfortIndexMeaning(float discomfortIndex) {
        String meaning;
        if (discomfortIndex < 68.0f) {
            meaning = "쾌적함";
        } else if (discomfortIndex < 75.0f) {
            meaning = "일부 불쾌감";
        } else if (discomfortIndex < 80.0f) {
            meaning = "불쾌감";
        } else if (discomfortIndex < 85.0f) {
            meaning = "전원 불쾌함";
        } else
            meaning = "매우 불쾌함";
        return meaning;
    }
}
