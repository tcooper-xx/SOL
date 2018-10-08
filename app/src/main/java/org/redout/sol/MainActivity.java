package org.redout.sol;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import org.redout.sol.weather.WxUtil;
import org.redout.sol.weather.current.CurrentConditions;
import org.redout.sol.weather.GetWxDataService;
import org.redout.sol.weather.WxRetrofitInstance;
import org.redout.sol.weather.dailyforecast.DailyForecast;
import org.redout.sol.weather.hourlyforecast.HourlyForecast;
import org.redout.sol.weather.hourlyforecast.HourlyForecastList;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity {
    private java.util.List<HourlyForecastList> forecastList = new ArrayList<>();
    private ForecastAdapter forecastAdapter;
    private DailyForecastAdapter dailyForecastAdapter;
    private RecyclerView recyclerView;
    private RecyclerView dailyForecastRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        doStuff();
    }

    public void doStuff() {
        if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this, new String[] {ACCESS_FINE_LOCATION},0);
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_COARSE_LOCATION},0);
            return;
        }
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if(location !=null) {
            getCurrentConditions(location.getLatitude(), location.getLongitude());



            getHourlyForecast(location.getLatitude(), location.getLongitude());
            getDailyForecast(location.getLatitude(), location.getLongitude());
        }
    }

    public void getCurrentConditions(double lat, double lon) {

        GetWxDataService service = WxRetrofitInstance.getWxRetofitInstance().create(GetWxDataService.class);
        Call<CurrentConditions> call = service.getWeather(Double.toString(lat), Double.toString(lon), "808e588f711f309776841c755a3c1802");
        call.enqueue(new Callback<CurrentConditions>() {
            @Override
            public void onResponse(Call<CurrentConditions> call, Response<CurrentConditions> response ) {
                int statusCode = response.code();
                CurrentConditions currentConditions = response.body();
                TextView tv = findViewById(R.id.locationName);
                tv.setText(currentConditions.getName());
                popCurrentConditions(currentConditions);
                System.out.println("URL :" +call.request().url());
            }

            @Override
            public void onFailure(Call<CurrentConditions> call, Throwable t) {
                Log.e("Error getting Current Conditions : ", t.getMessage());
            }
        });
    }
    public void getDailyForecast(double lat, double lon) {
        GetWxDataService service = WxRetrofitInstance.getWxRetofitInstance().create(GetWxDataService.class);
        Call<DailyForecast> call = service.getDailyForecast(Double.toString(lat), Double.toString(lon), "808e588f711f309776841c755a3c1802");
        call.enqueue(new Callback<DailyForecast>() {
            @Override
            public void onResponse(Call<DailyForecast> call, Response<DailyForecast> response) {
                DailyForecast dailyForecast = response.body();
                dailyForecastAdapter = new DailyForecastAdapter(dailyForecast.getList());
                RecyclerView.LayoutManager dailyForecastLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                dailyForecastRecyclerView = findViewById(R.id.dailyForecastRecycler);
                dailyForecastRecyclerView.setLayoutManager(dailyForecastLayoutManager);
                dailyForecastRecyclerView.setItemAnimator(new DefaultItemAnimator());
                dailyForecastRecyclerView.setAdapter(dailyForecastAdapter);

            }

            @Override
            public void onFailure(Call<DailyForecast> call, Throwable t) {
                Log.e("Error getting Forecast : ", t.getMessage());
            }
        });

    }
    public void getHourlyForecast(double lat, double lon) {
        GetWxDataService service = WxRetrofitInstance.getWxRetofitInstance().create(GetWxDataService.class);
        Call<HourlyForecast> call = service.getForecast(Double.toString(lat), Double.toString(lon), "808e588f711f309776841c755a3c1802");
        call.enqueue(new Callback<HourlyForecast>() {
            @Override
            public void onResponse(Call<HourlyForecast> call, Response<HourlyForecast> response) {
                int statusCode = response.code();
                HourlyForecast forecast = response.body();
                forecastAdapter = new ForecastAdapter(forecast.getList());
                RecyclerView.LayoutManager forecastLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                recyclerView = findViewById(R.id.forecastRecycler);
                recyclerView.setLayoutManager(forecastLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(forecastAdapter);
                System.out.println("URL :" +call.request().url());
            }

            @Override
            public void onFailure(Call<HourlyForecast> call, Throwable t) {
                Log.e("Error getting Forecast : ", t.getMessage());
            }
        });
    }

    public void popCurrentConditions(CurrentConditions cc)  {
        TextView temp = findViewById(R.id.temperature);
        TextView todayHigh = findViewById(R.id.todayHigh);
        TextView todayLow = findViewById(R.id.todayLow);
        ImageView wxIcon = findViewById(R.id.wxIcon);


        temp.setText(WxUtil.convTemp(cc.getMain().getTemp(), WxUtil.TEMP_UNIT_KELVIN, WxUtil.TEMP_UNIT_FAHRENHEIT) + "°F");
        todayHigh.setText(WxUtil.convTemp(cc.getMain().getTempMax(), WxUtil.TEMP_UNIT_KELVIN, WxUtil.TEMP_UNIT_FAHRENHEIT) + "°F ↑");
        todayLow.setText((WxUtil.convTemp(cc.getMain().getTempMin(), WxUtil.TEMP_UNIT_KELVIN, WxUtil.TEMP_UNIT_FAHRENHEIT) + "°F ↓"));
        int iconId = wxIcon.getContext().getResources().getIdentifier("wxicon_" + cc.getWeather().get(0).getIcon(), "drawable", wxIcon.getContext().getPackageName() );
        wxIcon.setImageResource(iconId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
