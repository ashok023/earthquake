package com.yatham.earthquakemonitor;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.yatham.earthquakemonitor.dao.QuakeDetailResponse;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuakeDetailsActivity extends AppCompatActivity {

    private String id = "";
    private TextView textViewTitle;
    private TextView textViewDate;
    private TextView textViewLocation;
    private TextView textViewMag;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm");
    QuakeServiceManager quakeServiceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getIntent().getStringExtra("id");
        setContentView(R.layout.activity_quake_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewDate = findViewById(R.id.textViewDate);
        textViewLocation = findViewById(R.id.textViewLocation);
        textViewMag = findViewById(R.id.textViewMagnitude);

        quakeServiceManager = QuakeServiceManager.getInstance();
        quakeServiceManager.getEarthquakeDetails(id, quakeDetailResponseCallback);
    }

    private Callback<QuakeDetailResponse> quakeDetailResponseCallback = new Callback<QuakeDetailResponse>() {
        @Override
        public void onResponse(Call<QuakeDetailResponse> call, Response<QuakeDetailResponse> response) {
            if (response.code() == 200) {
                QuakeDetailResponse quakeDetailResponse = response.body();
                textViewTitle.setText(quakeDetailResponse.properties.title);
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(Long.parseLong(quakeDetailResponse.properties.time));
                textViewDate.setText(simpleDateFormat.format(calendar.getTime()));
                textViewLocation.setText(quakeDetailResponse.properties.place);
                textViewMag.setText(String.valueOf(quakeDetailResponse.properties.mag));

            }
        }

        @Override
        public void onFailure(Call<QuakeDetailResponse> call, Throwable t) {

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_details, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_refresh) {
            quakeServiceManager.getEarthquakeDetails(id, quakeDetailResponseCallback);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
