package com.artiline.antoon.Database.Comics;

import com.artiline.antoon.Database.Models.Comics;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ComicsApiService {
    @GET
    Call<Comics> getComics(@Url String url);
}
