package com.example.soulfm.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.soulfm.R;
import com.example.soulfm.list_bookcase.BookcaseCategory;
import com.example.soulfm.list_bookcase.BookcaseCategoryAdapter;

import java.util.ArrayList;
import java.util.List;


public class BookcaseFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView rcv_bookcase;
    private BookcaseCategoryAdapter bookcaseCategoryAdapter;
    private View view;

    public BookcaseFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static BookcaseFragment newInstance(String param1, String param2) {
        BookcaseFragment fragment = new BookcaseFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
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

        bookcaseCategoryAdapter.setData(getListTopicCategory());
        rcv_bookcase.setAdapter(bookcaseCategoryAdapter);
    }

    private List<BookcaseCategory> getListTopicCategory() {
        List<BookcaseCategory> bookcaseCategories = new ArrayList<>();

        bookcaseCategories.add(new BookcaseCategory(R.drawable.headphone, "Sách đã nghe"));
        bookcaseCategories.add(new BookcaseCategory(R.drawable.favourite, "Sách yêu thích"));

        return bookcaseCategories;
    }
}