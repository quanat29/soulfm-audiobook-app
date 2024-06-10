package com.example.soulfm.api;

import com.example.soulfm.book.Book;
import com.example.soulfm.chapter.Chapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiGetBookInCategory {
    Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyy").create();

    ApiGetBookInCategory apiService = (ApiGetBookInCategory) new Retrofit.Builder()
            .baseUrl("http://192.168.1.2:8080/soulfm/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(ApiGetBookInCategory.class);
    @GET("select_book_category.php")
    Call<List<Book>> getlistBookInCategory(@Query("Id_category") int Id_category);
}
