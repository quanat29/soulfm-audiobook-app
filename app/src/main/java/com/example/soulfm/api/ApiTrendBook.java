package com.example.soulfm.api;

import com.example.soulfm.book.Book;
import com.example.soulfm.trend_book.TrendBook;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface ApiTrendBook {

    Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyy").create();

    ApiTrendBook apiService = new Retrofit.Builder()
            .baseUrl("http://192.168.1.2:8080/soulfm/trend_book.php/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(ApiTrendBook.class);
    @GET("posts")
    Call<List<TrendBook>> getListTrendBook();
}
