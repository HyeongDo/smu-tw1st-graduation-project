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
            try {
                String a= "https://ko.skyticket.com/international-flights/ia_fare_result_mix.php?select_career_only_flg=&trip_type=2&dep_port_name0=%EC%84%9C%EC%9A%B8%28SEL%29&dep_port0=SEL&arr_port_name0=%ED%98%B8%EC%B9%98%EB%AF%BC%28SGN%29&arr_port0=SGN&dep_date%5B%5D=2019-08-27&dep_port_name1=%ED%98%B8%EC%B9%98%EB%AF%BC%28SGN%29&dep_port1=SGN&arr_port_name1=%EC%84%9C%EC%9A%B8%28SEL%29&arr_port1=SEL&dep_date%5B%5D=2019-09-23&cabin_class=Y&adt_pax=1&chd_pax=0&inf_pax=0";
                // doc = Jsoup.connect(a).get(); //naver페이지를 불러옴
                doc = Jsoup.connect(a)

                        .get();
                System.out.println(doc);

                contents = doc.select("span .currency_conversion");//셀렉터로 span태그중 class값이 ah_k인 내용을 가져옴



            } catch (IOException e) {
                e.printStackTrace();
            }
            int cnt = 0;//숫자를 세기위한 변수
            for (Element element : contents) {
                cnt++;
                getP= element.text() + "\n";
                if (cnt == 1)//10위까지 파싱하므로
                    break;
            }
            return getP;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            Log.i("TAG",""+getP);

        }


}