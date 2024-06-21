package com.example.soulfm.api;

import com.example.soulfm.book_heard.BookHeard;
import com.example.soulfm.favorite_book.FavoriteBook;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiBookHeard {
    Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyy").create();

    ApiBookHeard apiService = (ApiBookHeard) new Retrofit.Builder()
            .baseUrl("http://192.168.1.2:8080/soulfm/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(ApiBookHeard.class);
    @GET("book_heard.php")
    Call<List<BookHeard>> getListBookHeard(@Query("Id_user") int Id_user);
}
