package com.artiline.antoon.Database.Comics;

import com.artiline.antoon.Database.Models.Comics;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ComicsRepository {
    /** @noinspection FieldMayBeFinal*/
    private ComicsApiService apiService;

    public ComicsRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.example.com/") // Replace with the actual base URL of the API
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ComicsApiService.class);
    }

    public void getComics(String url, Callback<Comics> callback) {
        Call<Comics> call = apiService.getComics(url);
        call.enqueue(callback);
    }
}