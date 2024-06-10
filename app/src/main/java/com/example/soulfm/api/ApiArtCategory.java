package com.example.soulfm.api;

import com.example.soulfm.book.Book;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface ApiArtCategory {
    Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyy").create();

    ApiArtCategory apiService = (ApiArtCategory) new Retrofit.Builder()
            .baseUrl("http://192.168.1.2:8080/soulfm/art_category.php/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(ApiArtCategory.class);
    @GET("posts")
    Call<List<Book>> getListArtCategory();
}
