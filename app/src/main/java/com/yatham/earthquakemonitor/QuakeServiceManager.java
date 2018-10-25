package com.yatham.earthquakemonitor;

import com.yatham.earthquakemonitor.dao.QuakeDetailResponse;
import com.yatham.earthquakemonitor.dao.QuakesResponse;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuakeServiceManager {
    private static final String BASE_URL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/";
    private static QuakeServiceManager quakeServiceManager;
    private QuakeService quakeService;

    public QuakeServiceManager() {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        quakeService = retrofit.create(QuakeService.class);
    }

    public static QuakeServiceManager getInstance() {
        if (quakeServiceManager == null) {
            quakeServiceManager = new QuakeServiceManager();
        }
        return quakeServiceManager;
    }

    public void getEarthQuakesSummary(Callback<QuakesResponse> callback) {

        Call<QuakesResponse> quakesResponseCall = quakeService.getEarthquakesSummary();
        quakesResponseCall.enqueue(callback);
    }

    public void getEarthquakeDetails(String id, Callback<QuakeDetailResponse> callback) {
        Call<QuakeDetailResponse> quakeDetailResponseCall = quakeService.getEarthquakeDetails(id);
        quakeDetailResponseCall.enqueue(callback);
    }
}
