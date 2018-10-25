package com.yatham.earthquakemonitor;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.yatham.earthquakemonitor.dao.Quake;
import com.yatham.earthquakemonitor.dao.QuakesResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SummaryActivity extends AppCompatActivity {

    private RecyclerView earthquakesRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        earthquakesRecyclerView = findViewById(R.id.earthquakesRecyclerView);
        earthquakesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        QuakeServiceManager quakeServiceManager = QuakeServiceManager.getInstance();

        quakeServiceManager.getEarthQuakesSummary(quakesResponseCallback);

    }

    private Callback<QuakesResponse> quakesResponseCallback = new Callback<QuakesResponse>() {
        @Override
        public void onResponse(Call<QuakesResponse> call, Response<QuakesResponse> response) {
            if (response.code() == 200) {
                QuakesResponse quakesResponse = response.body();
                earthquakesRecyclerView.setAdapter(new EarthquakesAdapter(quakesResponse.features, onItemClickListener));
            }
        }

        @Override
        public void onFailure(Call<QuakesResponse> call, Throwable t) {

        }
    };

    private EarthquakesAdapter.OnItemClickListener onItemClickListener = new EarthquakesAdapter.OnItemClickListener() {
        @Override
        public void onItemClicked(Quake quake) {
            Intent intent = new Intent(SummaryActivity.this, QuakeDetailsActivity.class);
            intent.putExtra("id", quake.id);
            startActivity(intent);
        }
    };

}
