package com.yatham.earthquakemonitor;

import com.yatham.earthquakemonitor.dao.QuakeDetailResponse;
import com.yatham.earthquakemonitor.dao.QuakesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface QuakeService {

    @GET("summary/all_hour.geojson")
    Call<QuakesResponse> getEarthquakesSummary();

    @GET("detail/{id}.geojson")
    Call<QuakeDetailResponse> getEarthquakeDetails(@Path("id") String id);
}
