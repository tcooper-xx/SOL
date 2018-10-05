package org.redout.sol;

import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.redout.sol.weather.GetWxDataService;
import org.redout.sol.weather.WxRetrofitInstance;
import org.redout.sol.weather.current.CurrentConditions;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WxClient {
    private final static String apiKey = "808e588f711f309776841c755a3c1802";
    private CurrentConditions cc = null;

    WxClient() {

    }
    public CurrentConditions getCurrentConditions(double lat, double lon) {

        GetWxDataService service = WxRetrofitInstance.getWxRetofitInstance().create(GetWxDataService.class);
        Call<CurrentConditions> call = service.getWeather(Double.toString(lat), Double.toString(lon), apiKey);
        call.enqueue(new Callback<CurrentConditions>() {
            @Override
            public void onResponse(Call<CurrentConditions> call, Response<CurrentConditions> response ) {
                int statusCode = response.code();
                CurrentConditions currentConditions = response.body();
            }

            @Override
            public void onFailure(Call<CurrentConditions> call, Throwable t) {
                Log.e("Error getting Current Conditions : ", t.getMessage());
            }
        });

//        try {
//            Response<CurrentConditions> response = call.execute();
//            currentConditions = response.body();
//        } catch (IOException e) {
//            Log.e("Error getting Current Conditions : ", e.getMessage());
//        }


        return cc;
    }



}
