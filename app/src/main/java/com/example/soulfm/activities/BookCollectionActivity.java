package com.example.soulfm.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.soulfm.R;
import com.example.soulfm.book.Book;
import com.example.soulfm.book.BookAdapter;
import com.example.soulfm.category.Category;
import com.example.soulfm.my_interface.IClickitemBookListener;

import java.util.List;

public class BookCollectionActivity extends AppCompatActivity{

    private RecyclerView rcv_favoriteBook;
    private BookAdapter bookAdapter;
    private TextView name_collection;
    private ImageView iv_icon_back;
    private int Id_user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_collection);

        rcv_favoriteBook = findViewById(R.id.rcv_favoriteBook);
        name_collection = findViewById(R.id.name_collection);
        iv_icon_back = findViewById(R.id.iv_icon_back);

        bookAdapter = new BookAdapter();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        rcv_favoriteBook.setLayoutManager(gridLayoutManager);
        rcv_favoriteBook.setAdapter(bookAdapter);
        Bundle bundle = getIntent().getExtras();
        if(bundle == null){
            return;
        }

        Category category = (Category) bundle.get("category");
        Id_user = bundle.getInt("Id_user");
        List<Book> bookList = (List<Book>) bundle.getSerializable("listbook");
        name_collection.setText(category.getNameCategory());
        bookAdapter.setData(bookList, new IClickitemBookListener() {
            @Override
            public void clickItemBook(Book book) {
                onClickGoToDetai(book);
            }
        },Id_user);

        iv_icon_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    private void onClickGoToDetai(Book book) {
        Intent intent = new Intent(BookCollectionActivity.this, BookDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("book", book);
        bundle.putInt("Id_user", Id_user);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}