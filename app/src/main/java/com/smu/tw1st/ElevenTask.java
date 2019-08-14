package com.smu.tw1st;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ElevenTask extends AsyncTask<String,String, String> {

    String clientKey = "#########################";
    private String str, receiveMsg;
    private final String ID = "########";

    @Override
    protected String doInBackground(String... params) {
        URL url = null;
        try {
            url = new URL("https://wpmesavail-staging.whypaymore.co.kr/flt/intl/fare-deals/ep?tripType=2&depLocCodes=SEL&depLocNames=서울(모든공항)&arrLocCodes=NRT&arrLocNames=도쿄(나리타)&dates=2019-08-20&dates=2019-08-23&seatCls=Y&adtCnt=1&appId=v2&searchSource=P&cabinCls=Y&opt=1&maxCxrDealLen=10");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setDoOutput(true);

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
