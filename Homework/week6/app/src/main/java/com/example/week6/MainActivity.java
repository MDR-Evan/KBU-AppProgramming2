package com.example.week6;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private RadioGroup radioGroup;
    private RadioButton rbSource, rbDOM, rbSAX, rbPull;
    private TextView tvResult;

    private final String XML_URL = "http://127.0.0.1:8081/week6_country.xml";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroup = findViewById(R.id.RG);
        rbSource = findViewById(R.id.RB1);
        rbDOM = findViewById(R.id.RB2);
        rbSAX = findViewById(R.id.RB3);
        rbPull = findViewById(R.id.RB3);
        tvResult = findViewById(R.id.TV_result);
        ImageView iv = findViewById(R.id.IV);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.RB1:  // Source
                        new Thread(() -> {
                            final String rawXml = getRawXmlContent("http://127.0.0.1:8081/country.xml");
                            runOnUiThread(() -> {
                                tvResult.setText(rawXml);
                                iv.setImageDrawable(null); // 이미지 초기화
                            });
                        }).start();
                        break;

                    case R.id.RB2:  // DOM
                        new Thread(() -> {
                            final ParsedResult result = parseDom("http://127.0.0.1:8081/country.xml");
                            runOnUiThread(() -> {
                                tvResult.setText(result.getResultText());
                                if (!result.getFlagUrl().isEmpty()) {
                                    Glide.with(MainActivity.this)
                                            .load(result.getFlagUrl())
                                            .into(iv);
                                } else {
                                    iv.setImageDrawable(null);
                                }
                            });
                        }).start();
                        break;

                    case R.id.RB3:  // SAX
                        tv_result.setText("SAX 선택됨 (장식)");
                        iv.setImageDrawable(null);
                        break;

                    case R.id.RB4:  // PULL
                        tv_result.setText("PULL 선택됨 (장식)");
                        iv.setImageDrawable(null);
                        break;
                }
            }
        });
    }

    private class FetchXmlTask extends AsyncTask<String, Void, String> {
        private int checkedRadioId;
        private String rawXml;   // 원본 XML (Source 용)

        public FetchXmlTask(int checkedId) {
            this.checkedRadioId = checkedId;
        }

        @Override
        protected String doInBackground(String... urls) {
            String urlStr = urls[0];
            try {
                // 1) XML 원본 불러오기 (HTTP GET)
                rawXml = downloadXml(urlStr);
                if (rawXml == null) {
                    return "XML 다운로드 실패!";
                }

                // 2) RadioButton에 따라 파싱
                if (checkedRadioId == R.id.RG) {
                    // 원본 문자열 그대로 반환
                    return rawXml;

                } else if (checkedRadioId == R.id.RB2) {
                    List<Country> countries = parseDom(rawXml);
                    return makePrintString(countries);

                } else if (checkedRadioId == R.id.RB3) {
                    List<Country> countries = parseSax(rawXml);
                    return makePrintString(countries);

                } else if (checkedRadioId == R.id.RB4) {
                    List<Country> countries = parsePull(rawXml);
                    return makePrintString(countries);
                }

            } catch (Exception e) {
                e.printStackTrace();
                return "에러 발생: " + e.getMessage();
            }
            return "잘못된 라디오 버튼 선택";
        }

        @Override
        protected void onPostExecute(String result) {
            // 결과 문자열을 TextView에 표시
            tvResult.setText(result);
        }

        /**
         * HTTP 요청으로 XML 문자열 다운로드
         */
        private String downloadXml(String urlStr) {
            StringBuilder sb = new StringBuilder();
            HttpURLConnection conn = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(urlStr);
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);
                conn.setRequestMethod("GET");
                conn.connect();

                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line).append("\n");
                    }
                    return sb.toString();
                } else {
                    Log.e(TAG, "서버 응답 코드: " + responseCode);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) try { reader.close(); } catch (IOException ignored) {}
                if (conn != null) conn.disconnect();
            }
            return null;
        }
    }

    /**
     * 파싱 결과를 보기 좋게 문자열로 만드는 메서드
     */
    private String makePrintString(List<Country> countries) {
        if (countries == null || countries.isEmpty()) {
            return "파싱 결과가 없습니다.";
        }
        StringBuilder sb = new StringBuilder();
        for (Country c : countries) {
            sb.append("국가: ").append(c.getName()).append("\n");
            sb.append("수도: ").append(c.getCapital()).append("\n");
            sb.append("공식 언어: ").append(c.getLanguage()).append("\n");
            sb.append("화폐: ").append(c.getCurrency())
                    .append(" (").append(c.getCurrencyCode()).append(")\n");
            sb.append("국기URL: ").append(c.getFlagUrl()).append("\n\n");
        }
        return sb.toString();
    }

    //-----------------------------------------------------------------------------
    // 1) DOM 파싱
    //-----------------------------------------------------------------------------
    private List<Country> parseDom(String xml) throws Exception {
        List<Country> list = new ArrayList<>();
        // 문자열 -> InputStream
        InputStream is = new java.io.ByteArrayInputStream(xml.getBytes());

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(is);

        Element root = doc.getDocumentElement(); // <countries>
        NodeList countryNodes = root.getElementsByTagName("country");

        for (int i = 0; i < countryNodes.getLength(); i++) {
            Element countryElem = (Element) countryNodes.item(i);

            // country 태그의 속성 가져오기
            String name = countryElem.getAttribute("name");
            String flagUrl = countryElem.getAttribute("flag");

            // 자식 태그 <language>, <capital city="..."/>, <currency code="...">...</currency>
            String language = getTextValue(countryElem, "language");
            // capital 태그는 내용 대신 city 속성
            String capital = getAttributeValue(countryElem, "capital", "city");
            // currency 태그
            Element currencyElem = getFirstElementByTagName(countryElem, "currency");
            String currencyCode = currencyElem.getAttribute("code");
            String currencyText = currencyElem.getTextContent();

            Country c = new Country(name, flagUrl, language, capital, currencyText, currencyCode);
            list.add(c);
        }
        return list;
    }

    // 특정 태그명의 첫 번째 element에서 텍스트 노드 가져오기
    private String getTextValue(Element parent, String tagName) {
        Element e = getFirstElementByTagName(parent, tagName);
        if(e != null) {
            return e.getTextContent();
        }
        return "";
    }
    // 특정 태그명의 첫 번째 element에서 특정 attribute 값 가져오기
    private String getAttributeValue(Element parent, String tagName, String attrName) {
        Element e = getFirstElementByTagName(parent, tagName);
        if(e != null) {
            return e.getAttribute(attrName);
        }
        return "";
    }
    // 부모 element 아래 특정 태그명의 첫 번째 element 반환
    private Element getFirstElementByTagName(Element parent, String tagName) {
        NodeList list = parent.getElementsByTagName(tagName);
        if(list.getLength() > 0) {
            return (Element) list.item(0);
        }
        return null;
    }

    //-----------------------------------------------------------------------------
    // 2) SAX 파싱
    //-----------------------------------------------------------------------------
    private List<Country> parseSax(String xml) throws Exception {
        List<Country> list = new ArrayList<>();

        // 1) SAXParser 생성
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();

        // 2) DefaultHandler 구현
        DefaultHandler handler = new DefaultHandler() {
            Country currentCountry = null;
            StringBuilder content = new StringBuilder();

            @Override
            public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                if(qName.equalsIgnoreCase("country")) {
                    currentCountry = new Country();
                    // 속성 읽기
                    currentCountry.setName(attributes.getValue("name"));
                    currentCountry.setFlagUrl(attributes.getValue("flag"));
                } else if(qName.equalsIgnoreCase("capital")) {
                    // capital city="Seoul"
                    if(currentCountry != null) {
                        String city = attributes.getValue("city");
                        currentCountry.setCapital(city);
                    }
                } else if(qName.equalsIgnoreCase("currency")) {
                    // currency code="KRW"
                    if(currentCountry != null) {
                        currentCountry.setCurrencyCode(attributes.getValue("code"));
                    }
                }
                content.setLength(0); // 버퍼 초기화
            }

            @Override
            public void endElement(String uri, String localName, String qName) throws SAXException {
                // 태그가 끝날 때 누적된 content를 확인해서 할당
                if(currentCountry != null) {
                    if(qName.equalsIgnoreCase("language")) {
                        currentCountry.setLanguage(content.toString().trim());
                    } else if(qName.equalsIgnoreCase("currency")) {
                        currentCountry.setCurrency(content.toString().trim());
                    }
                    // country 태그가 끝나면 list에 추가
                    else if(qName.equalsIgnoreCase("country")) {
                        list.add(currentCountry);
                    }
                }
            }

            @Override
            public void characters(char[] ch, int start, int length) throws SAXException {
                content.append(ch, start, length);
            }
        };

        // 3) 파싱 실행
        InputStream is = new java.io.ByteArrayInputStream(xml.getBytes());
        saxParser.parse(is, handler);

        return list;
    }

    //-----------------------------------------------------------------------------
    // 3) Pull 파싱
    //-----------------------------------------------------------------------------
    private List<Country> parsePull(String xml) throws Exception {
        List<Country> list = new ArrayList<>();
        Country current = null;

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        XmlPullParser xpp = factory.newPullParser();
        xpp.setInput(new StringReader(xml));

        int eventType = xpp.getEventType();
        while(eventType != XmlPullParser.END_DOCUMENT) {
            switch(eventType) {
                case XmlPullParser.START_TAG:
                    if(xpp.getName().equalsIgnoreCase("country")) {
                        current = new Country();
                        // 속성 읽기
                        current.setName(xpp.getAttributeValue(null, "name"));
                        current.setFlagUrl(xpp.getAttributeValue(null, "flag"));
                    } else if(xpp.getName().equalsIgnoreCase("capital")) {
                        // capital 태그의 city 속성
                        if(current != null) {
                            String city = xpp.getAttributeValue(null, "city");
                            current.setCapital(city);
                        }
                    } else if(xpp.getName().equalsIgnoreCase("currency")) {
                        if(current != null) {
                            current.setCurrencyCode(xpp.getAttributeValue(null, "code"));
                        }
                    }
                    break;

                case XmlPullParser.TEXT:
                    // 바로 전 START_TAG가 무엇인지 확인해야 함
                    // getName()은 START_TAG, END_TAG 상태일 때만 가능하므로
                    // TEXT 시점에는 xpp.getName()이 null 일 수 있음
                    break;

                case XmlPullParser.END_TAG:
                    if(xpp.getName().equalsIgnoreCase("language")) {
                        if(current != null) {
                            // TEXT 이벤트에서 읽었던 텍스트 얻기
                            current.setLanguage(xpp.getText());
                        }
                    } else if(xpp.getName().equalsIgnoreCase("currency")) {
                        if(current != null) {
                            current.setCurrency(xpp.getText());
                        }
                    } else if(xpp.getName().equalsIgnoreCase("country")) {
                        // country 종료 시 리스트에 추가
                        list.add(current);
                    }
                    break;
            }
            eventType = xpp.next();
        }

        return list;
    }

}