package com.example.allactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by LG on 2015-05-25.
 */
public class WeatherActivity extends Activity {

    String xml;
    double x;
    double y;
    TextView out4;
    TextView location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.weather);


      Intent i = new Intent(this.getIntent());

       x = i.getDoubleExtra("x좌표", 0);
       y = i.getDoubleExtra("y좌표", 0);


       // x = 37.282987;
       // y = 127.046259;


        //out1 = (TextView) findViewById(R.id.result1);
      //  out2 = (TextView) findViewById(R.id.result2);
    //    out3 = (TextView) findViewById(R.id.result3);
        out4 = (TextView) findViewById(R.id.result4);
        location = (TextView) findViewById(R.id.location);

        int maxrate=0;
        float maxtemp=0;
        float maxwind=0;

        String weather = "";
        String current = "";
        String currentpop = "";
        String currentws = "";

        GregorianCalendar calendar = new GregorianCalendar();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        int selectedhour;

        if(0<=hour & hour<3){
            selectedhour = 3;
        }
        else if(3<=hour & hour<6){
            selectedhour = 6;
        }
        else if(6<=hour & hour<9){
            selectedhour = 9;
        }
        else if(9<=hour & hour<12){
            selectedhour = 12;
        }
        else if(12<=hour & hour<15){
            selectedhour = 15;
        }
        else if(15<=hour & hour<18){
            selectedhour = 18;
        }
        else if(18<=hour & hour<21){
            selectedhour = 21;
        }
        else{
            selectedhour = 24;
        }

        try{

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            String urlAddr = "http://www.kma.go.kr/wid/queryDFS.jsp?gridx="+x+"&gridy="+y+"";
            Log.d("url ? ",urlAddr);

            URL url = new URL(urlAddr);

            Document doc = builder.parse(urlAddr);
            Element root = doc.getDocumentElement();
            NodeList root_mem = root.getElementsByTagName("body");

            Element body = (Element) root_mem.item(0);

            NodeList data = body.getElementsByTagName("data");

            for (int j = 0; j < 15 ; j++) {

                Element point = (Element) data.item(j);
                Element point2 = (Element) data.item(0);

                NodeList pops = point.getElementsByTagName("pop");
                NodeList tmxs = point.getElementsByTagName("tmx");
                NodeList wss = point.getElementsByTagName("ws");

                NodeList temps = point2.getElementsByTagName("temp");
                NodeList wfKors = point2.getElementsByTagName("wfKor");
                NodeList wss2 = point2.getElementsByTagName("ws");
                NodeList pops2 = point2.getElementsByTagName("pop");

                Element pop = (Element) pops.item(0);
                Element tmx = (Element) tmxs.item(0);
                Element ws = (Element) wss.item(0);
                Element wfKor = (Element) wfKors.item(0);
                Element temp = (Element) temps.item(0);
                Element ws2 = (Element) wss2.item(0);
                Element pop2 = (Element) pops2.item(0);


                Node txt = pop.getFirstChild();
                Node txt1 = tmx.getFirstChild();
                Node txt4 = wfKor.getFirstChild();
                Node txt5 = ws.getFirstChild();
                Node txt6 = temp.getFirstChild();
                Node txt7 = ws2.getFirstChild();
                Node txt8 = pop2.getFirstChild();

                if(Integer.parseInt(txt.getNodeValue()) > maxrate)
                    maxrate = Integer.parseInt(txt.getNodeValue());

                if(Float.parseFloat(txt1.getNodeValue()) > maxtemp)
                    maxtemp = Float.parseFloat(txt1.getNodeValue());

                weather = txt4.getNodeValue();
                current = txt6.getNodeValue();
                currentpop = txt7.getNodeValue();
                currentws = txt8.getNodeValue();

                if(Float.parseFloat(txt5.getNodeValue()) > maxwind){
                    maxwind = Float.parseFloat(txt5.getNodeValue());
                }

                out4.setText("현재시간 "+hour+"시 기준, 현재 날씨는 '"+ weather +"', 온도는 "+ current +"입니다");

            }

        }catch(ParserConfigurationException e){
            e.printStackTrace();
        }catch(MalformedURLException e){
            e.printStackTrace();
        }catch(SAXException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }

        try {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            String urlAddr = "http://maps.googleapis.com/maps/api/geocode/xml?sensor=false&language=ko&latlng="+x+","+y+"";
            Log.d("url ? ",urlAddr);

            URL url = new URL(urlAddr);

            Document doc = builder.parse(urlAddr);
            Element root = doc.getDocumentElement();
            NodeList root_mem = root.getElementsByTagName("result");

            Element body = (Element) root_mem.item(0);

            NodeList data = body.getElementsByTagName("formatted_address");
            Element pop = (Element) data.item(0);

            Node txt = pop.getFirstChild();

            location.setText(txt.getNodeValue());

        }catch(ParserConfigurationException e){
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.KEYCODE_BACK){
            Intent i = new Intent(WeatherActivity.this,MainActivity.class);
            startActivity(i);
            finish();
        }
        return true;
    }

}

