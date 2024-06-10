package com.example.soulfm.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.soulfm.R;
import com.example.soulfm.api.ApiAddFavoriteBook;
import com.example.soulfm.book.BookAdapter;
import com.example.soulfm.favorite_book.FavoriteBookAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteBookActivity extends AppCompatActivity {

    private ImageView iv_icon_option, iv_icon_back_favorite;
    private RecyclerView rcv_favorite_book;
    private FavoriteBookAdapter favoriteBookAdapter;
    private int Id_user, Id_book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_book);
        iv_icon_option = findViewById(R.id.iv_icon_option);
        iv_icon_back_favorite = findViewById(R.id.iv_icon_back_favorite);
        rcv_favorite_book = findViewById(R.id.rcv_favorite_book);
        favoriteBookAdapter = new FavoriteBookAdapter();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        rcv_favorite_book.setLayoutManager(gridLayoutManager);
        rcv_favorite_book.setAdapter(favoriteBookAdapter);

        Id_user = getIntent().getIntExtra("Id_user", -1);
        Id_book = getIntent().getIntExtra("Id_book", -1);

        if (Id_user != -1 && Id_book != -1) {
            addFavoriteBook(Id_user, Id_book);
        }


        iv_icon_back_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void addFavoriteBook(int id_user, int id_book) {
        ApiAddFavoriteBook.apiService.addFavoriteBook(id_book,id_user).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(FavoriteBookActivity.this, "Đã thêm vào danh sách yêu thích", Toast.LENGTH_SHORT).show();
                    loadFavoriteBooks(Id_user);
                } else {
                    Toast.makeText(FavoriteBookActivity.this, "Không thể thêm sách vào danh sách yêu thích", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    private void loadFavoriteBooks(int id_user) {
    }
}