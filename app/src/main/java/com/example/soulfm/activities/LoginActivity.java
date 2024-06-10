package com.example.soulfm.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.soulfm.R;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String BASE_URL = "http://192.168.1.2:8080/soulfm/login.php";

    private OkHttpClient client = new OkHttpClient();

    private EditText edt_username, edt_password;
    private Button btn_log_in;
    private TextView tv_sign_up_tag, tv_change_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        edt_username = findViewById(R.id.edt_username);
        edt_password = findViewById(R.id.edt_password);
        btn_log_in = findViewById(R.id.btn_log_in);
        tv_sign_up_tag = findViewById(R.id.tv_sign_up_tag);
        tv_change_pass = findViewById(R.id.tv_change_pass);

        btn_log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edt_username.getText().toString().trim();
                String password = edt_password.getText().toString().trim();

                if(username.isEmpty() || password.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }else{
                    login(username, password);
                }
            }
        });
        tv_sign_up_tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

        tv_change_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void login(String username, String password) {
        String urlWithParams = BASE_URL + "?Tendangnhap=" + username + "&Matkhau=" + password;
        RequestBody formData = new FormBody.Builder()
                .add("Tendangnhap", username)
                .add("Matkhau", password)
                .build();

        Request request = new Request.Builder().url(urlWithParams).get().build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String respons = response.body().string();
                    try {
                        JSONObject jsonResponse = new JSONObject(respons);
                        if (jsonResponse.getBoolean("success")) {
                            int Id_user = jsonResponse.getInt("Id_user");
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("Id_user", Id_user);
                            startActivity(intent);
                            finish();
                        } else {
                            runOnUiThread(() -> {
                                try {
                                    Toast.makeText(LoginActivity.this, jsonResponse.getString("message"), Toast.LENGTH_SHORT).show();
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    runOnUiThread(() -> Toast.makeText(LoginActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }
}
