package com.example.soulfm.api;

import com.example.soulfm.comment.Comment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiAddFavoriteBook {
    Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyy").create();

    ApiAddFavoriteBook apiService = (ApiAddFavoriteBook) new Retrofit.Builder()
            .baseUrl("http://192.168.1.2:8080/soulfm/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(ApiAddFavoriteBook.class);
    @GET("add_favorite_book.php")
    Call<Void>addFavoriteBook(@Query("Id_book") int Id_book,@Query("Id_user") int Id_user);
}
