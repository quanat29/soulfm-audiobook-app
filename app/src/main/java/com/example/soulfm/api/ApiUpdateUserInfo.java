package com.example.soulfm.api;

import com.example.soulfm.user.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiUpdateUserInfo {
    Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyy").create();

    ApiUpdateUserInfo apiService = (ApiUpdateUserInfo) new Retrofit.Builder()
            .baseUrl("http://192.168.1.2:8080/soulfm/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(ApiUpdateUserInfo.class);
    @POST("update_user_info.php")
    Call<Void> updateUsername(@Query("Id_user") int Id_user, @Query("Tendangnhap") String Tendangnhap);
}
