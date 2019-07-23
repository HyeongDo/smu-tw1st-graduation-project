package com.smu.tw1st;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface RetrofitService {


    @Headers({"X-RapidAPI-Key: a39470e17emshbe92f4d0d6870d0p187877jsnbd6d7f919411"})
    @GET("/apiservices/autosuggest/v1.0/KR/KRW/ko-KR/")
    Call <ListPlace> getPlaces(
            @Header("X-RapidAPI-Key") String apiKey,
            @Query("query") String query);

}
