package com.example.soulfm.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.soulfm.R;
import com.example.soulfm.activities.BookCollectionActivity;
import com.example.soulfm.activities.TrendBookActivity;
import com.example.soulfm.api.ApiArtCategory;
import com.example.soulfm.api.ApiFreeBook;
import com.example.soulfm.api.ApiLiteratureCategory;
import com.example.soulfm.api.ApiTrendBook;
import com.example.soulfm.book.Book;
import com.example.soulfm.category.Category;
import com.example.soulfm.category.CategoryAdapter;
import com.example.soulfm.my_interface.IClickitemViewAllListener;
import com.example.soulfm.trend_book.TrendBook;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements IClickitemViewAllListener{

    private RecyclerView rcv_home_category;
    private CategoryAdapter categoryAdapter;

    private String url = "https://librivox.org/api/feed/audiobooks/";

    private TextView tv_title;
    private List<Book> books;
    private List<Category> categories;
    private View mView;
    private static final String ARG_ID_USER = "Id_user";
    private int Id_user;

    public HomeFragment() {
    }

    public static HomeFragment newInstance(int Id_user) {
        HomeFragment fragment = new HomeFragment();
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
            Log.d("HomeFragment", "Id_user: " + Id_user);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView =  inflater.inflate(R.layout.fragment_home, container, false);
        initUI();
        return mView;
    }

    private void initUI() {
        rcv_home_category = mView.findViewById(R.id.rcv_home_category);
        tv_title = mView.findViewById(R.id.tv_title);
        categoryAdapter = new CategoryAdapter(getActivity(), (IClickitemViewAllListener) HomeFragment.this, Id_user);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        rcv_home_category.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        rcv_home_category.addItemDecoration(dividerItemDecoration);

        books = new ArrayList<>();
        categories = getListCategory();
        categoryAdapter.setData(categories);
        rcv_home_category.setAdapter(categoryAdapter);
        callApiGetTrendBook();
        callApiGetFreeBook();
        callApiLiteratureCategory();
        callApiArtCategory();
    }

    private List<Category> getListCategory() {


        List<Category> listCategory = new ArrayList<>();
        listCategory.add(new Category("Top Trending", "Xem tất cả", new ArrayList<>()));
        listCategory.add(new Category("Có thể bạn sẽ thích", "Xem tất cả", new ArrayList<>()));
        listCategory.add(new Category("Thể loại văn học", "Xem tất cả", new ArrayList<>()));
        listCategory.add(new Category("Thể loại nghệ thuật", "Xem tất cả", new ArrayList<>()));

        return  listCategory;


    }

    private void callApiGetTrendBook(){
        ApiTrendBook.apiService.getListTrendBook().enqueue(new Callback<List<TrendBook>>() {
            @Override
            public void onResponse(Call<List<TrendBook>> call, Response<List<TrendBook>> response) {
                List<TrendBook> trendBooks = response.body();
                if (trendBooks != null) {
                    categories.get(0).setBooks(convertTrendBooksToBooks(trendBooks));
                    categoryAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<TrendBook>> call, Throwable t) {
                Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<Book> convertTrendBooksToBooks(List<TrendBook> trendBooks) {
        List<Book> books = new ArrayList<>();
        for (TrendBook trendBook : trendBooks) {
            books.add(new Book(trendBook.getId(), trendBook.getImage_url(), trendBook.getTitle(), trendBook.getAuthors()));
        }
        return books;
    }


    private void callApiGetFreeBook(){
        ApiFreeBook.apiService.getListFreeBook().enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                books = response.body();
                categories.get(1).setBooks(books);
                categoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callApiLiteratureCategory(){
        ApiLiteratureCategory.apiService.getListLiteratureCategory().enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                books = response.body();
                categories.get(2).setBooks(books);
                categoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callApiArtCategory(){
        ApiArtCategory.apiService.getListArtCategory().enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                books = response.body();
                categories.get(3).setBooks(books);
                categoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void clickItemViewAll(Category category) {
        Intent intent;
        Bundle bundle = new Bundle();
        if (category.getNameCategory().equals("Top Trending")) {
            intent = new Intent(getContext(), TrendBookActivity.class);
            bundle.putSerializable("listbook", (Serializable) convertCategoryToTrendBook(category.getBooks()));
        } else {
            intent = new Intent(getContext(), BookCollectionActivity.class);
            bundle.putSerializable("listbook", (Serializable) category.getBooks());
        }

        bundle.putSerializable("category", category);
        bundle.putInt("Id_user", Id_user);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private List<TrendBook> convertCategoryToTrendBook(List<Book> books) {
        List<TrendBook> trendBooks = new ArrayList<>();
        for (Book book : books) {
            trendBooks.add(new TrendBook(book.getId(), book.getImage_url(), book.getTitle(),book.getAuthors()));
        }
        return trendBooks;

    }
}