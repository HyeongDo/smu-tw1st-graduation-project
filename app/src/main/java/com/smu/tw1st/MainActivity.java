package com.smu.tw1st;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private int mYear;
    private int mMonth;
    private int mDayOnMonth;
    private String dateCompareOut;
    private String dateCompareIn;

    TextView editOutDate;
    TextView editInDate;
    TextView textView2;

    String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editInDate = (TextView) findViewById(R.id.editInDate);
        editOutDate = (TextView) findViewById(R.id.editOutDate);
        textView2 = (TextView) findViewById(R.id.textView2);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        //.header("X-RapidAPI-Host","skyscanner-skyscanner-flight-search-v1.p.rapidapi.com")
                        .header("X-RapidAPI-Key","a39470e17emshbe92f4d0d6870d0p187877jsnbd6d7f919411")
                        .method(original.method(),original.body())
                        .build();
                return chain.proceed(request);
            }
        });

        OkHttpClient client = httpClient.build();


        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        RetrofitService retrofitService = retrofit.create(RetrofitService.class);

        Call<ListPlace> listPlaceCall = retrofitService.getPlaces("a39470e17emshbe92f4d0d6870d0p187877jsnbd6d7f919411","서울");
        listPlaceCall.enqueue(new Callback<ListPlace>() {
            @Override
            public void onResponse(Call<ListPlace> call, Response<ListPlace> response) {
                Log.d(TAG,"onResponse:"+response);
            }

            @Override
            public void onFailure(Call<ListPlace> call, Throwable t) {
                Log.d(TAG,"onFailure:"+t.getLocalizedMessage());
            }
        });

    }

/*
    public void outdateClicked(View view) {
        datePicker();
        dateCompareOut = mYear + "-" + mMonth + "-" + mDayOnMonth;
        editInDate = (TextView) findViewById(R.id.editInDate);
        editOutDate.setText(dateCompareOut);

    }

    public void indateClicked(View view) {
        datePicker();
        dateCompareIn = mYear + "-" + mMonth + "-" + mDayOnMonth;
        int tempIn = 0;
        int tempOut = 0;
        if (dateCompareIn != null && dateCompareOut != null) {
            tempIn = Integer.parseInt(dateCompareIn);
            tempOut = Integer.parseInt(dateCompareOut);
        }
        if (tempIn > tempOut) {
            editInDate = (TextView) findViewById(R.id.editInDate);
            editInDate.setText(dateCompareIn);
        } else {
            Toast.makeText(getApplicationContext(), "출발지보다 큰 날짜를 선택해주세요.", Toast.LENGTH_LONG).show();
        }
    }

    public void datePicker() {
        Calendar pickedData = Calendar.getInstance();
        Calendar minDate = Calendar.getInstance();
        Calendar maxDate = Calendar.getInstance();

        pickedData.set(2019, 8 - 1, 1);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                MainActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        mYear = year;
                        mMonth = month;
                        mDayOnMonth = dayOfMonth;
                    }
                },
                pickedData.get(Calendar.YEAR),
                pickedData.get(Calendar.MONTH),
                pickedData.get(Calendar.DATE)
        );
        minDate.set(2019, 8 - 1, 1);
        datePickerDialog.getDatePicker().setMinDate(minDate.getTime().getTime());
        maxDate.set(2019, 12 - 1, 31);
        datePickerDialog.getDatePicker().setMaxDate(maxDate.getTimeInMillis());

        datePickerDialog.show();
    }
*/
}
