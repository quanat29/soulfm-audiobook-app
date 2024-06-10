package com.example.soulfm.api;

import com.example.soulfm.book.Book;
import com.example.soulfm.book.BookAdapter;
import com.example.soulfm.chapter.Chapter;
import com.example.soulfm.detail_book.DetailBook;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiBookChapter {
    Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyy").create();

    ApiBookChapter apiService = (ApiBookChapter) new Retrofit.Builder()
            .baseUrl("http://192.168.1.2:8080/soulfm/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(ApiBookChapter.class);
    @GET("book_chapter.php")
    Call<List<Chapter>> getlistBookChapter(@Query("Id_book") int Id_book);
}
