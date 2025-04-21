package com.mock.carservice.net;

import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceCall {
    int speed = 0;
    public int getSpeedValue(String cardId) {
        RetrofitClient.getInstance().getApi().getSpeedValue(cardId).enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                Data mData = response.body();
                String val = mData.getSvalue();
                speed = Integer.parseInt(val);
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {

            }
        });
        return speed;
    }
    public void pushSpeedValue(int speedvalue ,String cardId) {
        RetrofitClient.getInstance().getApi().pushSpeedValue(speedvalue,cardId).enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                Data mData = response.body();
                String rresponse = mData.getSvalue();
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {

            }
        });
    }
}
