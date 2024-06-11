package com.example.soulfm.api;

import com.example.soulfm.comment.Comment;
import com.example.soulfm.user.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiUserInfo {
    Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyy").create();

    ApiUserInfo apiService = (ApiUserInfo) new Retrofit.Builder()
            .baseUrl("http://192.168.1.2:8080/soulfm/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(ApiUserInfo.class);
    @GET("get_user_info.php")
    Call<List<User>> getlistUser(@Query("Id_user") int Id_user);
}
