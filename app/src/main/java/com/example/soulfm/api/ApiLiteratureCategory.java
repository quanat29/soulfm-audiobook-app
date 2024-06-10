package com.example.soulfm.api;

import com.example.soulfm.book.Book;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface ApiLiteratureCategory {

    Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyy").create();

    ApiLiteratureCategory apiService = (ApiLiteratureCategory) new Retrofit.Builder()
            .baseUrl("http://192.168.1.2:8080/soulfm/literature_category.php/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(ApiLiteratureCategory.class);
    @GET("posts")
    Call<List<Book>> getListLiteratureCategory();
}
