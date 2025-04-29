package com.example.week8;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class DomFragment extends Fragment {
    private ListView listView;
    private List<Nation> nations = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dom, container, false);
        listView = root.findViewById(R.id.listView);

        // 클릭: Toast, 롱클릭: Dialog
        listView.setOnItemClickListener((AdapterView<?> parent, View view, int pos, long id) ->
                Toast.makeText(getContext(), nations.get(pos).country, Toast.LENGTH_SHORT).show()
        );
        listView.setOnItemLongClickListener((parent, view, pos, id) -> {
            showDetailDialog(nations.get(pos));
            return true;
        });

        fetchAndPopulate();
        return root;
    }

    private void fetchAndPopulate() {
        new Thread(() -> {
            List<Nation> list = new ArrayList<>();
            try {
                // 🔄 Application에서 URL 획득
                MyApplication app = (MyApplication) getActivity().getApplication();
                String urlString = app.getPage(0);  // 예: "http://192.168.93.1:8081/weather.xml"

                URL url = new URL(urlString);
                InputStream is = url.openStream();

                // DOM 파싱
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.parse(new InputSource(is));
                doc.getDocumentElement().normalize();

                NodeList nl = doc.getElementsByTagName("nation");
                for (int i = 0; i < nl.getLength(); i++) {
                    Element e = (Element) nl.item(i);
                    String country     = getTagValue("country", e);
                    String flagUrl     = getTagValue("flag",    e);
                    String weather     = getTagValue("weather", e);
                    int    temperature = Integer.parseInt(getTagValue("temperature", e));
                    list.add(new Nation(country, flagUrl, weather, temperature));
                }

                is.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

            // UI 업데이트
            if (getActivity() != null) {
                getActivity().runOnUiThread(() -> {
                    nations = list;
                    listView.setAdapter(new NationAdapter(getContext(), nations));
                });
            }
        }).start();
    }


    private void showDetailDialog(Nation n) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(n.country + "의 날씨");

        View dialogView = LayoutInflater.from(getContext())
                .inflate(R.layout.dialog_dom, null);

        ImageView dlgFlag       = dialogView.findViewById(R.id.ivDialogFlag);
        TextView dlgTempWeather = dialogView.findViewById(R.id.tvDialogTempWeather);
        ImageView dlgIcon       = dialogView.findViewById(R.id.ivDialogIcon);


        Glide.with(this)
                .load(n.flagUrl)
                .into(dlgFlag);

        dlgTempWeather.setText(n.temperature + "℃  " + n.weather);
        dlgIcon.setImageResource(getWeatherIconRes(n.weather));

        builder.setView(dialogView)
                .setPositiveButton("확인", null)
                .show();
    }


    private String getTagValue(String tag, Element parent) {
        NodeList nl = parent.getElementsByTagName(tag);
        if (nl != null && nl.getLength() > 0) {
            return nl.item(0).getTextContent();
        }
        return "";
    }

    private int getWeatherIconRes(String weather) {
        switch (weather) {
            case "맑음": return R.drawable.nb01;
            case "흐림": return R.drawable.nb04;
            case "비":   return R.drawable.nb08;
            case "눈":   return R.drawable.nb11;
            case "우박": return R.drawable.nb07;
            default:     return R.drawable.nb01;
        }
    }
}
