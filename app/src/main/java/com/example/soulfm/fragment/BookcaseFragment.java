package com.example.soulfm.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.soulfm.R;
import com.example.soulfm.activities.BookHeardActivity;
import com.example.soulfm.activities.BookInCategoryActivity;
import com.example.soulfm.activities.FavoriteBookActivity;
import com.example.soulfm.book_heard.BookHeard;
import com.example.soulfm.favorite_book.FavoriteBook;
import com.example.soulfm.list_bookcase.BookcaseCategory;
import com.example.soulfm.list_bookcase.BookcaseCategoryAdapter;
import com.example.soulfm.my_interface.IClickItemBookcaseCategory;

import java.util.ArrayList;
import java.util.List;


public class BookcaseFragment extends Fragment {

    private RecyclerView rcv_bookcase;
    private BookcaseCategoryAdapter bookcaseCategoryAdapter;
    private View view;
    private static final String ARG_ID_USER = "Id_user";
    private int Id_user;

    public BookcaseFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static BookcaseFragment newInstance(int Id_user) {
        BookcaseFragment fragment = new BookcaseFragment();
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
            Log.d("BookcaseFragment", "Id_user: " + Id_user);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_bookcase, container, false);
        initUI();
        return view;
    }

    private void initUI() {
        rcv_bookcase = view.findViewById(R.id.rcv_bookcase);

        bookcaseCategoryAdapter = new BookcaseCategoryAdapter();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        rcv_bookcase.setLayoutManager(gridLayoutManager);
        rcv_bookcase.setNestedScrollingEnabled(false);

        bookcaseCategoryAdapter.setData(getListTopicCategory(), new IClickItemBookcaseCategory() {
            @Override
            public void clickItemBookcaseCategory(BookcaseCategory bookcaseCategory) {
               String title = bookcaseCategory.getTitle();
               Intent intent;
               if(title.equals("Sách yêu thích")){
                   intent = new Intent(getActivity(), FavoriteBookActivity.class);
               }else if(title.equals("Sách đã nghe")){
                   intent = new Intent(getActivity(), BookHeardActivity.class);
               }else {
                   return;
               }
                intent.putExtra("bookcase_name", bookcaseCategory.getTitle());
                intent.putExtra("Id_user", Id_user);
                startActivity(intent);
            }
        });
        rcv_bookcase.setAdapter(bookcaseCategoryAdapter);
    }

    private List<BookcaseCategory> getListTopicCategory() {
        List<BookcaseCategory> bookcaseCategories = new ArrayList<>();

        bookcaseCategories.add(new BookcaseCategory(R.drawable.headphone, "Sách đã nghe"));
        bookcaseCategories.add(new BookcaseCategory(R.drawable.favourite, "Sách yêu thích"));

        return bookcaseCategories;
    }
}