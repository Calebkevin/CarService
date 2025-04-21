package com.mock.carservice.net;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
public interface Api
{
    @GET("speedvalue")
    Call<Data> getSpeedValue(@Query("CarId") String carId);
    @GET("pushspeedvalue")
    Call<Data> pushSpeedValue(@Query("SpeedValue") int speedvalue,@Query("CarId") String carId);
}
