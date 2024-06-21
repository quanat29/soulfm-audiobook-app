package com.example.soulfm.fragment;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.soulfm.R;
import com.example.soulfm.activities.LoginActivity;
import com.example.soulfm.api.ApiUpdateUserInfo;
import com.example.soulfm.api.ApiUserInfo;
import com.example.soulfm.services.AudiobookService;
import com.example.soulfm.user.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountFragment extends Fragment {

    private static final String ARG_ID_USER = "Id_user";
    private int Id_user;
    private TextView tv_username_account, tv_change_account, tv_phone_account;
    private Button btn_log_out;
    private AudiobookService audiobookService;
    private boolean isBound = false;

    public AccountFragment() {
        // Required empty public constructor
    }

    public static AccountFragment newInstance(int Id_user) {
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ID_USER, Id_user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Id_user = getArguments().getInt(ARG_ID_USER);
            Log.d("AccountFragment", "Id_user: " + Id_user);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        tv_username_account = view.findViewById(R.id.tv_username_account);
        tv_phone_account = view.findViewById(R.id.tv_phone_account);
        tv_change_account = view.findViewById(R.id.tv_change_account);
        btn_log_out = view.findViewById(R.id.btn_log_out);
        loadUserInfo();

        tv_change_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditUsernameDialog();
            }
        });

        btn_log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        // Bind to the AudiobookService
        Intent intent = new Intent(getActivity(), AudiobookService.class);
        getActivity().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isBound) {
            getActivity().unbindService(serviceConnection);
            isBound = false;
        }
    }

    private void loadUserInfo() {
        ApiUserInfo.apiService.getlistUser(Id_user).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<User> users = response.body();
                    if (!users.isEmpty()) {
                        User user = users.get(0);
                        tv_username_account.setText(user.getTendangnhap());
                        tv_phone_account.setText(user.getSdt());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.e("AccountFragment", "Error loading user info", t);
            }
        });
    }

    private void showEditUsernameDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("");

        final EditText input = new EditText(getActivity());
        input.setText(tv_username_account.getText().toString());
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newUsername = input.getText().toString();
                tv_username_account.setText(newUsername);
                updateUsername(newUsername);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void updateUsername(String newUsername) {
        ApiUpdateUserInfo.apiService.updateUsername(Id_user, newUsername).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("AccountFragment", "Username updated successfully");
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        Log.e("AccountFragment", "Failed to update username: " + errorBody);
                    } catch (Exception e) {
                        Log.e("AccountFragment", "Failed to update username and failed to read errorBody", e);
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("AccountFragment", "Error updating username", t);
            }
        });
    }

    private void logout() {
        if (isBound && audiobookService != null) {
            audiobookService.stopService();
        }

        SharedPreferences preferences = getActivity().getSharedPreferences("user_prefs", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();

        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        getActivity().finish();
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            AudiobookService.LocalBinder binder = (AudiobookService.LocalBinder) service;
            audiobookService = binder.getService();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            audiobookService = null;
            isBound = false;
        }
    };
}
