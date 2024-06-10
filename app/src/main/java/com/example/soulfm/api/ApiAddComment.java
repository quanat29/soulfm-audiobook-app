package com.example.soulfm.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiAddComment {
    Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyy").create();

    ApiAddComment  apiService = (ApiAddComment) new Retrofit.Builder()
            .baseUrl("http://192.168.1.2:8080/soulfm/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(ApiAddComment.class);
    @GET("add_comment.php")
    Call<Void> addFavoriteBook(@Query("Id_book") int Id_book, @Query("Id_user") int Id_user
            ,@Query("Binhluan") String content, @Query("comment_star") int star);
}
