package com.example.soulfm.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.soulfm.R;
import com.example.soulfm.api.ApiUpdatePassword;

import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPasswordActivity extends AppCompatActivity {

    private ImageView iv_forgetpass_close;
    private EditText edt_username_reset, edt_phone_reset, edt_password_reset, edt_repeat_password_reset;
    private Button btn_reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

//        iv_forgetpass_close = findViewById(R.id.iv_forgetpass_close);
//        edt_username_reset = findViewById(R.id.edt_username_reset);
//        edt_phone_reset = findViewById(R.id.edt_phone_reset);
//        edt_password_reset = findViewById(R.id.edt_password_reset);
//        edt_repeat_password_reset = findViewById(R.id.edt_repeat_password_reset);
//        btn_reset = findViewById(R.id.btn_reset);
//
//        iv_forgetpass_close.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(ForgetPasswordActivity.this, LoginActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });
//
//        btn_reset.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                String username = edt_username_reset.getText().toString().trim();
//                String phone = edt_phone_reset.getText().toString().trim();
//                String newPassword = edt_password_reset.getText().toString().trim();
//                String repeatPassword = edt_repeat_password_reset.getText().toString().trim();
//
//                // Kiểm tra username và phone không được để trống
//                if (username.isEmpty() || phone.isEmpty()) {
//                    Toast.makeText(ForgetPasswordActivity.this, "Please enter username and phone number", Toast.LENGTH_SHORT).show();
//                    return; // Dừng việc thực hiện tiếp theo nếu username hoặc phone trống
//                }
//
//                // Kiểm tra mật khẩu không được để trống
//                if (newPassword.isEmpty() || repeatPassword.isEmpty()) {
//                    Toast.makeText(ForgetPasswordActivity.this, "Please enter password and repeat password", Toast.LENGTH_SHORT).show();
//                    return; // Dừng việc thực hiện tiếp theo nếu mật khẩu hoặc mật khẩu lặp lại trống
//                }
//
//                // Kiểm tra mật khẩu khớp nhau
//                if (!newPassword.equals(repeatPassword)) {
//                    Toast.makeText(ForgetPasswordActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
//                    return; // Dừng việc thực hiện tiếp theo nếu mật khẩu không khớp nhau
//                }
//
//                // Nếu các điều kiện đều hợp lệ, thực hiện cập nhật mật khẩu
//                updatePassword(username, phone, newPassword);
//            }
//        });
//    }
//
//    private void updatePassword(String username, String phone, String newPassword) {
//        ApiUpdatePassword.apiService.updatePassword(username, phone, newPassword).enqueue(new Callback<Void>() {
//            @Override
//            public void onResponse(Call<Void> call, Response<Void> response) {
//                if (response.isSuccessful()) {
//                    Toast.makeText(ForgetPasswordActivity.this, "Password updated successfully", Toast.LENGTH_SHORT).show();
//                } else {
//                    try {
//                        JSONObject jObjError = new JSONObject(response.errorBody().string());
//                        String errorMessage = jObjError.getString("error");
//                        Toast.makeText(ForgetPasswordActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
//                    } catch (Exception e) {
//                        Toast.makeText(ForgetPasswordActivity.this, "Failed to update password", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Void> call, Throwable t) {
//                Toast.makeText(ForgetPasswordActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}
