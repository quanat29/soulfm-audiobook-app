package com.example.soulfm.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.soulfm.R;
import com.example.soulfm.api.ApiGetBookInCategory;
import com.example.soulfm.book.Book;
import com.example.soulfm.book.BookAdapter;
import com.example.soulfm.my_interface.IClickitemBookListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookInCategoryActivity extends AppCompatActivity {

    private RecyclerView rcv_BookInCategory;
    private BookAdapter bookAdapter;
    private TextView name_collection_category;
    private ImageView iv_icon_back_category;
    private int Id_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_in_category);

        rcv_BookInCategory = findViewById(R.id.rcv_BookInCategory);
        name_collection_category = findViewById(R.id.name_collection_category);
        iv_icon_back_category = findViewById(R.id.iv_icon_back_category);

        bookAdapter = new BookAdapter();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        rcv_BookInCategory.setLayoutManager(gridLayoutManager);
        rcv_BookInCategory.setAdapter(bookAdapter);

        Intent intent = getIntent();
        if (intent == null) {
            return;
        }

        int categoryId = intent.getIntExtra("category_id", -1);
        String categoryName = intent.getStringExtra("category_name");
        Id_user = intent.getIntExtra("Id_user", -1);

        if (categoryName != null) {
            name_collection_category.setText(categoryName);
        }

        // Fetch book list based on category ID
        fetchBooksByCategory(categoryId);

        iv_icon_back_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void fetchBooksByCategory(int categoryId) {
        ApiGetBookInCategory.apiService.getlistBookInCategory(categoryId).enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Book> bookList = response.body();
                    bookAdapter.setData(bookList, new IClickitemBookListener() {
                        @Override
                        public void clickItemBook(Book book) {
                            onClickGoToDetail(book);
                        }
                    }, Id_user);
                } else {
                    Log.e("API_ERROR", "Response not successful");
                }
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Log.e("API_ERROR", "API call failed", t);
            }
        });
    }

    private void onClickGoToDetail(Book book) {
        Intent intent = new Intent(BookInCategoryActivity.this, BookDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("book", book);
        bundle.putInt("Id_user", Id_user);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
    