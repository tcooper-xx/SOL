package org.redout.sol.weather;

import org.redout.sol.weather.current.CurrentConditions;
import org.redout.sol.weather.forecast.FiveDayForecast;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetWxDataService {
    @GET("weather")
    Call<CurrentConditions> getWeather(@Query("lat") String lat, @Query("lon") String lon, @Query("appid") String appid);

    @GET("forecast")
    Call<FiveDayForecast> getForecast(@Query("lat") String lat, @Query("lon") String lon, @Query("appid") String appid);
}
