package com.example.soulfm.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.soulfm.R;
import com.example.soulfm.api.ApiGetBookInfo;
import com.example.soulfm.book.Book;
import com.example.soulfm.book.BookAdapter;
import com.example.soulfm.my_interface.IClickitemBookListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView rcv_list_search;
    private BookAdapter bookAdapter;
    private EditText edt_search;
    private TextView tv_cancel;
    private int Id_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Id_user = getIntent().getIntExtra("Id_user", -1);

        rcv_list_search = findViewById(R.id.rcv_list_search);
        edt_search = findViewById(R.id.edt_search);
        tv_cancel = findViewById(R.id.tv_cancel);

        // Initialize BookAdapter and RecyclerView
        bookAdapter = new BookAdapter();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        rcv_list_search.setLayoutManager(gridLayoutManager);
        rcv_list_search.setAdapter(bookAdapter);

        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Not used in this implementation
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Not used in this implementation
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String keyword = editable.toString().trim();
                if (!keyword.isEmpty()) {
                    fetchBooks(keyword);
                } else {
                    clearRecyclerView();
                }
            }
        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void fetchBooks(String keyword) {
        ApiGetBookInfo.apiService.getlistBookInfo(keyword).enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Book> books = response.body();
                    bookAdapter.setData(books, new IClickitemBookListener() {
                        @Override
                        public void clickItemBook(Book book) {
                            onClickGoToDetail(book);
                        }
                    }, Id_user);
                } else {
                    clearRecyclerView();
                    Toast.makeText(SearchActivity.this, "No books found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                clearRecyclerView();
                Toast.makeText(SearchActivity.this, "Failed to fetch books", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onClickGoToDetail(Book book) {
        Intent intent = new Intent(SearchActivity.this, BookDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("book", book);
        bundle.putInt("Id_user", Id_user);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void clearRecyclerView() {
        if (bookAdapter != null) {
            bookAdapter.clearData();
        }
    }
}
