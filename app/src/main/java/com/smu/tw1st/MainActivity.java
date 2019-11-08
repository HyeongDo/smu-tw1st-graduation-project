package com.smu.tw1st;import androidx.annotation.RequiresApi;import androidx.appcompat.app.AppCompatActivity;import androidx.recyclerview.widget.LinearLayoutManager;import androidx.recyclerview.widget.RecyclerView;import android.animation.Animator;import android.annotation.SuppressLint;import android.app.Activity;import android.app.AlarmManager;import android.app.DatePickerDialog;import android.app.PendingIntent;import android.content.Context;import android.content.Intent;import android.content.SharedPreferences;import android.net.Uri;import android.os.Build;import android.os.Bundle;import android.text.Editable;import android.text.TextWatcher;import android.util.Log;import android.view.View;import android.widget.ArrayAdapter;import android.widget.AutoCompleteTextView;import android.widget.Button;import android.widget.DatePicker;import android.widget.TextView;import android.widget.Toast;import com.airbnb.lottie.LottieAnimationView;import org.json.JSONArray;import org.json.JSONObject;import java.util.ArrayList;import java.util.Calendar;import java.util.Comparator;import java.util.HashMap;import java.util.concurrent.ExecutionException;import retrofit2.Call;import retrofit2.Callback;import retrofit2.Response;import retrofit2.Retrofit;import retrofit2.converter.gson.GsonConverterFactory;public class MainActivity extends AppCompatActivity implements View.OnClickListener {    private int startYear;    private int startMonth;    private int startDay;    private int endYear;    private int endMonth;    private int endDay;    private String startDate;    private String endDate;    private String OutCity;    private String InCity;    private String OutPlaceId;    private String InPlaceId;    private RecyclerView recyclerView;    private RecyclerView.LayoutManager layoutManager;    private TextView tvCalendarOutDate;    private TextView tvCalendarIndate;    private AutoCompleteTextView acOutCity;    private AutoCompleteTextView acInCity;    private Button btnSearch;    private ArrayList<String> dataListOut;    private ArrayList<String> dataListIn;    private String TAG = MainActivity.class.getName();    private String Base_Url = "https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com";    private int checkInOut;    private String prePrice = "100";    private String nowPrice = "100";    private ArrayAdapter<String> arrayAdapterIn;    private ArrayAdapter<String> arrayAdapterOut;    private String TresultText = "0", Turl = "값이없음";    private String ExresultText = "0", Exurl = "값이없음";    private String WpmresultText = "0", Wpurl = "값이없음";    private String SkiresultText = "0", Skiurl = "값이없음";    private ArrayList<Data> data;    @SuppressLint("ClickableViewAccessibility")    @Override    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        setContentView(R.layout.activity_main);        init();        getTextIn();        getTextOut();        alarmAtNine();        btnSearch.setOnClickListener(this);    }    @Override    protected void onResume() {        super.onResume();        getFile();    }    public void init() {        tvCalendarOutDate = findViewById(R.id.tvCalendarOutDate);        tvCalendarIndate = findViewById(R.id.tvCalendarInDate);        acInCity = findViewById(R.id.actvInCity);        acOutCity = findViewById(R.id.actvOutCity);        dataListOut = new ArrayList<>();        dataListIn = new ArrayList<>();        btnSearch = findViewById(R.id.btnSearch);    }    public void rcvManager() {        recyclerView = findViewById(R.id.rcvResult);        layoutManager = new LinearLayoutManager(this);        recyclerView.setLayoutManager(layoutManager);        data = new ArrayList<>();        data.add(new Data("티몬", R.drawable.timon, TresultText, Turl));        data.add(new Data("익스피디아", R.drawable.expedia, ExresultText, Exurl));        data.add(new Data("와이페이모어", R.drawable.whypaymore, WpmresultText, Wpurl));        data.add(new Data("스카이티켓", R.drawable.skyticket, SkiresultText, Skiurl));        data.sort(new Comparator<Data>() {            @Override            public int compare(Data arg0, Data arg1) {                int age0, age1;                if (arg0 == null) age0 = 0;                else age0 = Integer.parseInt(arg0.getMoney());                if (arg1 == null) age1 = 0;                else age1 = Integer.parseInt(arg1.getMoney());                if (age0 == age1) return 0;                else if (age0 > age1) return 1;                else return -1;            }        });        for (int i = 0; i < 3; i++) {            if (data.get(i).getMoney().equals("0")) {                data.get(i).setMoney("오류");            }        }        RcvAdapter rcvAdapter = new RcvAdapter(data);        recyclerView.setAdapter(rcvAdapter);        recyclerView.setVisibility(View.VISIBLE);        rcvAdapter.setOnClickListener(new RcvAdapter.RcvClickListener() {            @Override            public void onItemClicked(RcvAdapter.ViewHolder holder, View view, int position) {                String temp;                switch (position) {                    case 0:                        temp = data.get(0).getUrl();                        //첫번째가격실행                        Intent intentFrist = new Intent(Intent.ACTION_VIEW, Uri.parse(temp));                        startActivity(intentFrist);                        break;                    case 1:                        //두번째가격실행                        temp = data.get(1).getUrl();                        Intent intentSecond = new Intent(Intent.ACTION_VIEW, Uri.parse(temp));                        startActivity(intentSecond);                        break;                    case 2:                        //세번째가격실행                        temp = data.get(2).getUrl();                        Intent intentThird = new Intent(Intent.ACTION_VIEW, Uri.parse(temp));                        startActivity(intentThird);                        break;                    case 3:                        //네번째가격실행                        temp = data.get(3).getUrl();                        Intent intentFourth = new Intent(Intent.ACTION_VIEW, Uri.parse(temp));                        startActivity(intentFourth);                        break;                }            }        });        nowPrice = data.get(0).getMoney();    }    @Override    public void onClick(View view) {        searchClicked();    }    public void searchClicked() {        try {            TresultText = new TmonTask(this).execute().get();            if (TresultText != null) {                TresultText = TmonParser(TresultText);            } else {                TresultText = "0";            }            ExresultText = new ExTask(this).execute().get();            if (!ExresultText.equals("0")) {                ExresultText = ExParser(ExresultText);                if (!ExresultText.equals("0")) {                    ExresultText = ExresultText.substring(1);                    ExresultText = ExresultText.replace(",", "");                }            } else ExresultText = "0";            WpmresultText = new ElevenTask(this).execute().get();            if (!WpmresultText.equals("0")) {                WpmresultText = ElevenParser(WpmresultText);            } else {                WpmresultText = "0";            }            SkiresultText = new SkyTask(this).execute().get().toString();            if (!SkiresultText.equals("0")) {                SkiresultText = SkiresultText.replace("KRW", "");                SkiresultText = SkiresultText.replace(",", "");            } else {                SkiresultText = "0";            }            urlParse(startDate, endDate, InPlaceId, OutPlaceId);            rcvManager();        } catch (InterruptedException e) {            e.printStackTrace();        } catch (ExecutionException e) {            e.printStackTrace();        }    }    public void urlParse(String sDate, String eDate, String sPlace, String ePlace) {        sPlace = sPlace.substring(0, sPlace.indexOf("-"));        ePlace = ePlace.substring(0, ePlace.indexOf("-"));        Turl = "http://tour.tmon.co.kr/flight/result?trip=RT&sch=aa_" + sPlace + "_" + sDate + "|aa_" + ePlace + "_" + eDate + "&ps=1-0-0&seat=Y";        Exurl = "https://www.expedia.co.kr/Flights-Search?trip=roundtrip&leg1=from%3A" + sPlace + "%2Cto%3A" + ePlace + "%2Cdeparture%3A" + sDate + "TANYT&leg2=from%20" + ePlace + "%2Cto%20" + sPlace + "%2Cdeparture%3A" + eDate + "TANYT&passengers=adults%3A1%2Cchildren%3A0%2Cseniors%3A0%2Cinfantinlap%3AY&options=cabinclass%3Aeconomy&mode=search&origref=www.expedia.co.kr";        Wpurl = "https://www.whypaymore.co.kr/d/flt/intl/deals?appId=v2&tripType=2&searchSource=P&chNo=0&depLocCodes=" + sPlace + "&arrLocCodes=" + ePlace + "&dates=" + sDate + "&dates=" + eDate + "&cabinCls=Y&adtCnt=1&chdCnt=0&infCnt=0";        Skiurl = "https://ko.skyticket.com/international-flights/ia_fare_result_mix.php?select_career_only_flg=&trip_type=2&dep_port0=" + sPlace + "&arr_port0=" + ePlace + "&dep_date%5B%5D=" + sDate + "&arr_date%5B%5D=" + eDate + "&cabin_class=Y&adt_pax=1&chd_pax=0&inf_pax=0";    }    public void calendarOutDate(View view) {        Calendar calendar = Calendar.getInstance();        DatePickerDialog.OnDateSetListener dateSetListener =                new DatePickerDialog.OnDateSetListener() {                    @Override                    public void onDateSet(DatePicker datePicker, int Year, int monthOfYear, int dayOfMonth) {                        String strDate = (Year) + "년";                        strDate += (monthOfYear + 1) + "월";                        strDate += (dayOfMonth) + "일";                        startDate = Year + "-";                        if (monthOfYear + 1 < 10) {                            startDate += "0" + (monthOfYear + 1) + "-";                        } else startDate += (monthOfYear + 1) + "-";                        if (dayOfMonth < 10) {                            startDate += "0" + (dayOfMonth) + "";                        } else {                            startDate += (dayOfMonth) + "";                        }                        Toast.makeText(getApplicationContext(), strDate, Toast.LENGTH_SHORT).show();                        tvCalendarOutDate.setText(strDate);                        savePreferences("outDateFile", "OutDate", startDate);                    }                };        DatePickerDialog datePickerDialog = new DatePickerDialog(this,                dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));        startYear = calendar.get(Calendar.YEAR);        startMonth = calendar.get(Calendar.MONTH);        startDay = calendar.get(Calendar.DAY_OF_MONTH);        datePickerDialog.show();    }    public void calendarInDate(View view) {        Calendar calendar = Calendar.getInstance();        DatePickerDialog.OnDateSetListener dateSetListener =                new DatePickerDialog.OnDateSetListener() {                    @Override                    public void onDateSet(DatePicker datePicker, int Year, int monthOfYear, int dayOfMonth) {                        String strDate = (Year) + "년";                        strDate += (monthOfYear + 1) + "월";                        strDate += (dayOfMonth) + "일";                        endDate = Year + "-";                        if (monthOfYear + 1 < 10) {                            endDate += "0" + (monthOfYear + 1) + "-";                        } else endDate += (monthOfYear + 1) + "-";                        if (dayOfMonth < 10) {                            endDate += "0" + (dayOfMonth) + "";                        } else {                            endDate += (dayOfMonth) + "";                        }                        Toast.makeText(getApplicationContext(), strDate, Toast.LENGTH_SHORT).show();                        tvCalendarIndate.setText(strDate);                        savePreferences("inDateFile", "InDate", endDate);                    }                };        DatePickerDialog datePickerDialog = new DatePickerDialog(this,                dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));        endYear = calendar.get(Calendar.YEAR);        endMonth = calendar.get(Calendar.MONTH);        endDay = calendar.get(Calendar.DAY_OF_MONTH);        datePickerDialog.show();    }    public void getData(String query) {        Retrofit retrofit = new Retrofit.Builder()                .baseUrl(Base_Url)                .addConverterFactory(GsonConverterFactory.create())                .build();        RetrofitService service = retrofit.create(RetrofitService.class);        Call<ListPlace> call = service.getCityName(query);        call.enqueue(new Callback<ListPlace>() {            @Override            public void onResponse(Call<ListPlace> call, Response<ListPlace> response) {                try {                    ListPlace placeData = response.body();                    dataListOut.clear();                    dataListIn.clear();                    for (int i = 0; i < placeData.getPlaces().size(); i++) {                        if (checkInOut == 0) {                            dataListOut.add(placeData.getPlaces().get(i).getPlaceName());                            Log.d("들어간 것 : ", "::" + placeData.getPlaces().get(i).getPlaceName());                            InPlaceId = placeData.getPlaces().get(0).getPlaceId();                            Log.d(TAG, InPlaceId);                        } else {                            dataListIn.add(placeData.getPlaces().get(i).getPlaceName());                            Log.d("들어간 것 : ", "::" + placeData.getPlaces().get(i).getPlaceName());                            OutPlaceId = placeData.getPlaces().get(0).getPlaceId();                            Log.d(TAG, OutPlaceId);                        }                    }                    Log.d(TAG, "-------------------");                } catch (Exception e) {                    Log.d("onResponse", "통신됐지만 Error ");                    e.printStackTrace();                }            }            @Override            public void onFailure(Call<ListPlace> call, Throwable t) {                Log.d(TAG, "통신실패");            }        });    }    public void autoListOut(String tempString) {        checkInOut = 0;        getData(tempString);        arrayAdapterOut = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, dataListOut);        acOutCity.setAdapter(arrayAdapterOut);        OutCity = acOutCity.getText().toString();        savePreferences("outCityFile", "OutCity", OutCity);    }    public void autoListIn(String tempString) {        checkInOut = 1;        getData(tempString);        arrayAdapterIn = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, dataListIn);        acInCity.setAdapter(arrayAdapterIn);        InCity = acInCity.getText().toString();        savePreferences("inCityFile", "InCity", InCity);    }    public void getTextIn() {        acInCity.addTextChangedListener(new TextWatcher() {            @Override            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {            }            @Override            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {                String temp = acInCity.getText().toString();                autoListIn(temp);            }            @Override            public void afterTextChanged(Editable editable) {            }        });    }    public void getTextOut() {        acOutCity.addTextChangedListener(new TextWatcher() {            @Override            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {            }            @Override            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {                String temp = acOutCity.getText().toString();                autoListOut(temp);            }            @Override            public void afterTextChanged(Editable editable) {            }        });    }    public void savePreferences(String file, String key, String value) {        SharedPreferences preferences = getSharedPreferences(file, MODE_PRIVATE);        SharedPreferences.Editor editor = preferences.edit();        editor.putString(key, value);        editor.apply();    }    public String getPreferences(String file, String key, String value) {        SharedPreferences preferences = getSharedPreferences(file, MODE_PRIVATE);        String temp = preferences.getString(key, value);        return temp;    }    public void getFile() {        acOutCity.setText(getPreferences("outCityFile", "OutCity", OutCity));        acInCity.setText(getPreferences("inCityFile", "InCity", InCity));        tvCalendarOutDate.setText(getPreferences("outDateFile", "OutDate", startDate));        tvCalendarIndate.setText(getPreferences("inDateFile", "InDate", endDate));    }    @RequiresApi(api = Build.VERSION_CODES.O)    public void alarmAtNine() {        if (nowPrice.compareTo(prePrice) < 0) {            new AlarmHATT(getApplicationContext()).Alarm();        } else {            prePrice = nowPrice;        }    }    public String TmonParser(String jsonString) {        String low = null;        try {            JSONArray jarray = new JSONObject(jsonString).getJSONArray("data");            for (int i = 0; i < jarray.length(); i++) {                HashMap map = new HashMap<>();                JSONObject jObject = jarray.getJSONObject(i);                low = jObject.optString("adult1ManFare");                return low;            }        } catch (Exception e) {            e.printStackTrace();            return "0";        }        return "0";    }    public String ExParser(String jsonString) {        String low = null;        try {            JSONArray jarray = new JSONObject(jsonString).getJSONArray("assertions");            for (int i = 0; i < jarray.length(); i++) {                HashMap map = new HashMap<>();                JSONObject jObject = jarray.getJSONObject(i);                low = jObject.optString("formattedPrice");                return low;            }        } catch (Exception e) {            e.printStackTrace();            return "0";        }        return "0";    }    public String ElevenParser(String jsonString) {        String low = null;        try {            JSONArray jarray = new JSONObject(jsonString).getJSONArray("resultValue");            for (int i = 0; i < jarray.length(); i++) {                HashMap map = new HashMap<>();                JSONObject jObject = jarray.getJSONObject(i);                low = jObject.optString("adtPricing");                low = low.substring(low.indexOf("totalPrice") + 12, low.indexOf("}"));                return low;            }        } catch (Exception e) {            e.printStackTrace();            return "0";        }        return "0";    }    public String getStartDate() {        return startDate;    }    public String getEndDate() {        return endDate;    }    public String getOutPlaceId() {        return OutPlaceId;    }    public String getInPlaceId() {        return InPlaceId;    }    public class AlarmHATT {        private Context context;        public AlarmHATT(Context context) {            this.context = context;        }        public void Alarm() {            AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);            Intent intent = new Intent(MainActivity.this, BroadcastD.class);            PendingIntent sender = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);            Calendar calendar = Calendar.getInstance();            //알람시간 calendar에 set해주기            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 14, 0, 0);            //알람 예약            am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);        }    }}