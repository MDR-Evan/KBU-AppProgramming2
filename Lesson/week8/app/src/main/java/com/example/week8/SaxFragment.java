package com.example.week8;

import android.app.AlertDialog;
import android.os.AsyncTask;
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

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.SAXException;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class SaxFragment extends Fragment {
    private ListView listView;
    private List<Nation> nations = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_sax, container, false);
        listView = root.findViewById(R.id.listViewSax);

        // 클릭 리스너 설정
        listView.setOnItemClickListener((parent, view, pos, id) ->
                Toast.makeText(getContext(), nations.get(pos).country, Toast.LENGTH_SHORT).show()
        );
        listView.setOnItemLongClickListener((parent, view, pos, id) -> {
            showDetailDialog(nations.get(pos));
            return true;
        });

        // 파싱 시작
        new FetchSaxTask().execute();
        return root;
    }

    private class FetchSaxTask extends AsyncTask<Void, Void, List<Nation>> {
        @Override
        protected List<Nation> doInBackground(Void... voids) {
            List<Nation> result = new ArrayList<>();
            try {
                if (getActivity() == null) return result;

                // Application에서 URL 얻어오기
                MyApplication app = (MyApplication) getActivity().getApplication();
                String urlString = app.getPage(0);

                URL url = new URL(urlString);
                InputStream is = url.openStream();

                SAXParserFactory factory = SAXParserFactory.newInstance();
                SAXParser parser = factory.newSAXParser();
                parser.parse(new InputSource(is), new DefaultHandler() {
                    private Nation current;
                    private StringBuilder sb = new StringBuilder();
                    private boolean inCountry, inFlag, inWeather, inTemp;

                    @Override
                    public void startElement(String uri, String localName, String qName, Attributes atts) {
                        switch (qName) {
                            case "nation":
                                current = new Nation("", "", "", 0);
                                break;
                            case "country":
                                inCountry = true; sb.setLength(0); break;
                            case "flag":
                                inFlag = true; sb.setLength(0); break;
                            case "weather":
                                inWeather = true; sb.setLength(0); break;
                            case "temperature":
                                inTemp = true; sb.setLength(0); break;
                        }
                    }

                    @Override
                    public void characters(char[] ch, int start, int length) {
                        sb.append(ch, start, length);
                    }

                    @Override
                    public void endElement(String uri, String localName, String qName) {
                        switch (qName) {
                            case "country":
                                if (inCountry) current.country = sb.toString().trim();
                                inCountry = false;
                                break;
                            case "flag":
                                if (inFlag) current.flagUrl = sb.toString().trim();
                                inFlag = false;
                                break;
                            case "weather":
                                if (inWeather) current.weather = sb.toString().trim();
                                inWeather = false;
                                break;
                            case "temperature":
                                if (inTemp) {
                                    try {
                                        current.temperature = Integer.parseInt(sb.toString().trim());
                                    } catch (NumberFormatException ignored) {}
                                    inTemp = false;
                                }
                                break;
                            case "nation":
                                result.add(current);
                                break;
                        }
                    }
                });

                is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(List<Nation> result) {
            if (getActivity() != null && result != null) {
                // 이전 데이터 클리어 후 새로 채우기
                nations.clear();
                nations.addAll(result);
                // Adapter 연결
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

