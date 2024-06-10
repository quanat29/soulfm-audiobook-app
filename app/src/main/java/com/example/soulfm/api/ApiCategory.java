package com.example.soulfm.api;

import com.example.soulfm.topic_category.TopicCategory;
import com.example.soulfm.trend_book.TrendBook;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface ApiCategory {
    Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyy").create();

    ApiCategory apiService = (ApiCategory) new Retrofit.Builder()
            .baseUrl("http://192.168.1.2:8080/soulfm/category.php/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(ApiCategory.class);
    @GET("posts")
    Call<List<TopicCategory>> getListCategory();
}
