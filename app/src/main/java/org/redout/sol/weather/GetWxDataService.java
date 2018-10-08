package org.redout.sol.weather;

import org.redout.sol.weather.current.CurrentConditions;
import org.redout.sol.weather.dailyforecast.DailyForecast;
import org.redout.sol.weather.hourlyforecast.HourlyForecast;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetWxDataService {
    @GET("weather")
    Call<CurrentConditions> getWeather(@Query("lat") String lat, @Query("lon") String lon, @Query("appid") String appid);

    @GET("forecast")
    Call<HourlyForecast> getForecast(@Query("lat") String lat, @Query("lon") String lon, @Query("appid") String appid);

    @GET("forecast/daily")
    Call<DailyForecast> getDailyForecast(@Query("lat") String lat, @Query("lon") String lon, @Query("appid") String appid);
}
