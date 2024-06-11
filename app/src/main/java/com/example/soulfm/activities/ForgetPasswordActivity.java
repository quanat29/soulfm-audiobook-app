package com.example.soulfm.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.soulfm.R;

import org.json.JSONObject;

import java.io.IOException;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ForgetPasswordActivity extends AppCompatActivity {

    private static final String UPDATE_PASSWORD_URL = "http://192.168.1.2:8080/soulfm/update_password.php";
    private static final String CHECK_USER_URL = "http://192.168.1.2:8080/soulfm/check_update_pass.php";

    private OkHttpClient client = new OkHttpClient();

    private ImageView iv_forgetpass_close;
    private EditText edt_username_reset, edt_phone_reset, edt_password_reset, edt_repeat_password_reset;
    private Button btn_reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        iv_forgetpass_close = findViewById(R.id.iv_forgetpass_close);
        edt_username_reset = findViewById(R.id.edt_username_reset);
        edt_phone_reset = findViewById(R.id.edt_phone_reset);
        edt_password_reset = findViewById(R.id.edt_password_reset);
        edt_repeat_password_reset = findViewById(R.id.edt_repeat_password_reset);
        btn_reset = findViewById(R.id.btn_reset);

        iv_forgetpass_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToLogin();
            }
        });

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edt_username_reset.getText().toString().trim();
                String phone = edt_phone_reset.getText().toString().trim();
                String newPassword = edt_password_reset.getText().toString().trim();
                String repeatPassword = edt_repeat_password_reset.getText().toString().trim();

                if (username.isEmpty() || phone.isEmpty()) {
                    Toast.makeText(ForgetPasswordActivity.this, "Please enter username and phone number", Toast.LENGTH_SHORT).show();
                } else if (newPassword.isEmpty() || repeatPassword.isEmpty()) {
                    Toast.makeText(ForgetPasswordActivity.this, "Please enter password and repeat password", Toast.LENGTH_SHORT).show();
                } else if (!newPassword.equals(repeatPassword)) {
                    Toast.makeText(ForgetPasswordActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                } else if (!isValidPhoneNumber(phone)) {
                    Toast.makeText(ForgetPasswordActivity.this, "Invalid phone number", Toast.LENGTH_SHORT).show();
                } else {
                    checkUser(username, phone, newPassword);
                }
            }
        });
    }

    private boolean isValidPhoneNumber(String phone) {
        String phonePattern = "^0\\d{9}$";
        return Pattern.matches(phonePattern, phone);
    }

    private void checkUser(String username, String phone, String newPassword) {
        String urlWithParams = CHECK_USER_URL + "?Tendangnhap=" + username + "&Sdt=" + phone;
        Request request = new Request.Builder().url(urlWithParams).get().build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(() -> Toast.makeText(ForgetPasswordActivity.this, "Failed to connect to server", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String respons = response.body().string().trim();
                    runOnUiThread(() -> {
                        if ("User does not exist".equals(respons)) {
                            Toast.makeText(ForgetPasswordActivity.this, "Username or phone number is incorrect", Toast.LENGTH_SHORT).show();
                        } else {
                            updatePassword(username, phone, newPassword);
                        }
                    });
                } else {
                    runOnUiThread(() -> Toast.makeText(ForgetPasswordActivity.this, "Failed to check user", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    private void updatePassword(String username, String phone, String newPassword) {
        RequestBody formData = new FormBody.Builder()
                .add("Tendangnhap", username)
                .add("Sdt", phone)
                .add("Matkhau", newPassword)
                .build();

        Request request = new Request.Builder().url(UPDATE_PASSWORD_URL).post(formData).build();
        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(() -> Toast.makeText(ForgetPasswordActivity.this, "Failed to connect to server", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    runOnUiThread(() -> {
                        edt_username_reset.setText("");
                        edt_phone_reset.setText("");
                        edt_password_reset.setText("");
                        edt_repeat_password_reset.setText("");
                        navigateToLogin();
                        Toast.makeText(ForgetPasswordActivity.this, "Password updated successfully", Toast.LENGTH_SHORT).show();
                    });
                } else {
                    runOnUiThread(() -> Toast.makeText(ForgetPasswordActivity.this, "Failed to update password", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    private void navigateToLogin() {
        Intent intent = new Intent(ForgetPasswordActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
