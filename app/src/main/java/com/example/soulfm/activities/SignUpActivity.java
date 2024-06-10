package com.example.soulfm.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.soulfm.R;

import java.io.IOException;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SignUpActivity extends AppCompatActivity {

    private static final String BASE_URL = "http://192.168.1.2:8080/soulfm/signup.php";
    private static final String CHECK_USERNAME_URL = "http://192.168.1.2:8080/soulfm/checkuser.php";

    private OkHttpClient client = new OkHttpClient();

    EditText edt_username_signup, edt_phone, edt_password_signup, edt_repeat_password;
    Button btn_sign_up;
    CheckBox cb_check_box;
    TextView tv_haveAccount;
    ImageView iv_image_close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
//        getSupportActionBar().hide();

        edt_username_signup = findViewById(R.id.edt_username_signup);
        edt_password_signup = findViewById(R.id.edt_password_signup);
        edt_phone = findViewById(R.id.edt_phone);
        btn_sign_up = findViewById(R.id.btn_sign_up);
        edt_repeat_password = findViewById(R.id.edt_repeat_password);
        cb_check_box = findViewById(R.id.cb_check_box);
        tv_haveAccount = findViewById(R.id.tv_haveAccount);
        iv_image_close = findViewById(R.id.iv_image_close);

        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edt_username_signup.getText().toString().trim();
                String phone = edt_phone.getText().toString().trim();
                String password = edt_password_signup.getText().toString().trim();
                String repeatPassword = edt_repeat_password.getText().toString().trim();

                if (username.isEmpty() || phone.isEmpty() || password.isEmpty() || repeatPassword.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(repeatPassword)) {
                    Toast.makeText(SignUpActivity.this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                } else if (!cb_check_box.isChecked()) {
                    Toast.makeText(SignUpActivity.this, "Vui lòng đồng ý với điều khoản", Toast.LENGTH_SHORT).show();
                } else if (!isValidPhoneNumber(phone)) {
                    Toast.makeText(SignUpActivity.this, "Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
                }else {
                    checkUserName(username, phone, password);
                }
            }
        });

        tv_haveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        iv_image_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private boolean isValidPhoneNumber(String phone) {
        String phonePattern = "^0\\d{9}$";
        return Pattern.matches(phonePattern, phone);
    }


    private void checkUserName(String username, String phone, String password) {
        String urlWithParams = CHECK_USERNAME_URL + "?Tendangnhap=" + username;
        RequestBody formData = new FormBody.Builder()
                .add("Tendangnhap", username)
                .build();

        Request request = new Request.Builder().url(urlWithParams).get().build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    String respons = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                               if("User already exists".equals(respons)){
                                   Toast.makeText(SignUpActivity.this, "Tên người dùng này đã tồn tại", Toast.LENGTH_SHORT).show();
                               }else{
                                   saveData(username, phone , password );
                               }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });
    }

    private void saveData(String username, String sdt, String password) {

        RequestBody formData = new FormBody.Builder()
                .add("Tendangnhap", username)
                .add("Sdt", sdt)
                .add("Matkhau", password)
                .build();

        Request request = new Request.Builder().url(BASE_URL).post(formData).build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String respons = response.body().string();
                if(response.isSuccessful()){
                    SignUpActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                edt_username_signup.setText("");
                                edt_phone.setText("");
                                edt_password_signup.setText("");
                                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });

    }
}