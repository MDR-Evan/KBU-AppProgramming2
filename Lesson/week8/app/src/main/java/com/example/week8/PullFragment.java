package com.example.week8;

import android.os.Bundle;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import org.xmlpull.v1.XmlPullParser;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class PullFragment extends Fragment {
    private ListView listView;
    private List<Nation> nations = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_pull, container, false);
        listView = root.findViewById(R.id.listViewSource);

        listView.setOnItemClickListener((parent, view, pos, id) ->
                Toast.makeText(getContext(), nations.get(pos).country, Toast.LENGTH_SHORT).show()
        );
        listView.setOnItemLongClickListener((parent, view, pos, id) -> {
            showDetailDialog(nations.get(pos));
            return true;
        });

        new FetchPullTask().execute();
        return root;
    }

    private class FetchPullTask extends AsyncTask<Void, Void, List<Nation>> {
        @Override
        protected List<Nation> doInBackground(Void... voids) {
            List<Nation> result = new ArrayList<>();
            try {
                // Application에서 URL 얻어오기
                MyApplication app = (MyApplication) getActivity().getApplication();
                String pageUrl = app.getPage(0);

                URL url = new URL(pageUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                InputStream is = conn.getInputStream();

                XmlPullParser parser = Xml.newPullParser();
                parser.setInput(new InputStreamReader(is, "UTF-8"));

                Nation current = null;
                String text = "";
                int eventType = parser.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    String tag = parser.getName();
                    switch (eventType) {
                        case XmlPullParser.START_TAG:
                            if ("nation".equals(tag)) {
                                current = new Nation("", "", "", 0);
                            }
                            break;
                        case XmlPullParser.TEXT:
                            text = parser.getText();
                            break;
                        case XmlPullParser.END_TAG:
                            if (current != null) {
                                switch (tag) {
                                    case "country":
                                        current.country = text.trim();
                                        break;
                                    case "flag":
                                        current.flagUrl = text.trim();
                                        break;
                                    case "weather":
                                        current.weather = text.trim();
                                        break;
                                    case "temperature":
                                        try {
                                            current.temperature = Integer.parseInt(text.trim());
                                        } catch (NumberFormatException ignored) {}
                                        break;
                                    case "nation":
                                        result.add(current);
                                        current = null;
                                        break;
                                }
                            }
                            break;
                    }
                    eventType = parser.next();
                }

                is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(List<Nation> list) {
            if (getActivity() != null && list != null) {
                nations.clear();
                nations.addAll(list);
                NationAdapter adapter = new NationAdapter(getContext(), nations);
                listView.setAdapter(adapter);
            }
        }
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
