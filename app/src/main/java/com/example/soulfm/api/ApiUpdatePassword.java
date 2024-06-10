package com.example.soulfm.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiUpdatePassword {
    Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();

    ApiUpdatePassword apiService = new Retrofit.Builder()
            .baseUrl("http://192.168.1.2:8080/soulfm/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(ApiUpdatePassword.class);

    @FormUrlEncoded
    @POST("update_password.php")
    Call<Void> updatePassword(@Field("username") String username, @Field("phone") String phone, @Field("new_password") String newPassword);
}
