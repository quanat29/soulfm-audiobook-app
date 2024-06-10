package com.example.soulfm.api;

import com.example.soulfm.favorite_book.FavoriteBook;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiRemoveFavoriteBook {
    Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyy").create();

    ApiRemoveFavoriteBook apiService = (ApiRemoveFavoriteBook) new Retrofit.Builder()
            .baseUrl("http://192.168.1.2:8080/soulfm/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(ApiRemoveFavoriteBook.class);
    @GET("remove_favorite_book.php")
    Call<Void> RemoveFavoriteBook(@Query("Id_book") int Id_book, @Query("Id_user") int Id_user);
}
