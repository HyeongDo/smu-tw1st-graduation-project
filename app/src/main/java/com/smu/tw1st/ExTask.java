package com.smu.tw1st;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ExTask extends AsyncTask<String,String, String> {

    String clientKey = "#########################";
    private String str, receiveMsg;
    private final String ID = "########";

    @Override
    protected String doInBackground(String... params) {
        URL url = null;
        try {
            url = new URL("https://www.expedia.co.kr/FlightDiscovery?origin=ICN&destination=TYO&depart=2019-08-14&return=2019-08-17&adults=1&children=0&childAges=&inLap=true");
//익스피디아는 출발일이 오늘날짜와 최소 5일 간격이 있어야함
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            conn.setRequestProperty("x-waple-authorization", clientKey);
            if (conn.getResponseCode() == conn.HTTP_OK) {
                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuffer buffer = new StringBuffer();
                while ((str = reader.readLine()) != null) {
                    buffer.append(str);
                }
                receiveMsg = buffer.toString();
                Log.i("receiveMsg : ", receiveMsg);

                reader.close();
            } else {
                Log.i("통신 결과", conn.getResponseCode() + "에러");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return receiveMsg;
    }
}