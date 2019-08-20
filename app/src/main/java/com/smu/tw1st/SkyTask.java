package com.smu.tw1st;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
public class SkyTask  extends AsyncTask{

    String clientKey = "#########################";
    private String str, receiveMsg;
    private final String ID = "########";
    String getP;
    Elements contents;
    Document doc = null;
    private Context mContext = null;

    public SkyTask(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    protected String doInBackground(Object[] params) {
        String eDate,sDate,sPlace,ePlace;
        sDate = ((MainActivity) mContext).getStartDate();
        eDate = ((MainActivity) mContext).getEndDate();
        sPlace=((MainActivity) mContext).getInPlaceId();
        ePlace=((MainActivity) mContext).getOutPlaceId();

        sPlace = sPlace.substring(0,sPlace.indexOf("-"));
        ePlace = ePlace.substring(0,ePlace.indexOf("-"));
        if(ePlace == "TYOA") ePlace="TYO";
            try {
                String a= "https://ko.skyticket.com/international-flights/ia_fare_result_mix.php?select_career_only_flg=&trip_type=2&dep_port0="+sPlace+"&arr_port0="+ePlace+"&dep_date%5B%5D="+sDate+"&arr_date%5B%5D="+eDate+"&cabin_class=Y&adt_pax=1&chd_pax=0&inf_pax=0";
                // doc = Jsoup.connect(a).get(); //naver페이지를 불러옴
                //"https://ko.skyticket.com/international-flights/ia_fare_result_mix.php?select_career_only_flg=&trip_type=2&dep_port0=SEL&arr_port0=LOS&dep_date%5B%5D=2019-09-05&dep_port_name1=도쿄%28NRT%29&dep_port1=NRT&arr_port_name1=서울%28SEL%29&arr_port1=SEL&dep_date%5B%5D=2019-09-09&cabin_class=Y&adt_pax=1&chd_pax=0&inf_pax=0&ssid=85e760f3f7e3e4aed8876dc41cf33d46"
                doc = Jsoup.connect(a)

                        .get();
                //System.out.println(doc);

                contents = doc.select("span .currency_conversion");//셀렉터로 span태그중 class값이 ah_k인 내용을 가져옴
                int cnt = 0;//숫자를 세기위한 변수
                for (Element element : contents) {
                    cnt++;
                    getP= element.text();
                    if (cnt == 1)//10위까지 파싱하므로
                        break;
                }


            } catch (IOException e) {
                e.printStackTrace();
                return "0";
            }
        return getP;

        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            Log.i("TAG",""+getP);

        }


}