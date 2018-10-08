package org.redout.sol.weather.darksky;

import org.redout.sol.weather.darksky.generated.WeatherData;
import org.redout.sol.weather.hourlyforecast.HourlyForecast;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GetDarkSkyDataService {
    @GET("forecast/{apikey}/{lat},{lon}")
    Call<WeatherData> getForecast(@Path("lat") String lat, @Path("lon") String lon, @Path("appid") String appid);
}
