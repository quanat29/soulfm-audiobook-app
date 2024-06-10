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
import com.example.soulfm.activities.BookInCategoryActivity;
import com.example.soulfm.api.ApiCategory;
import com.example.soulfm.my_interface.IClickItemCategory;
import com.example.soulfm.topic_category.TopicCategory;
import com.example.soulfm.topic_category.TopicCategoryAdapter;
import com.example.soulfm.trend_book.TrendBook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TopicFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView rcv_topic_category;
    private TopicCategoryAdapter topicCategoryAdapter;
    private View mView;
    private static final String ARG_ID_USER = "Id_user";
    private int Id_user;

    public TopicFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static TopicFragment newInstance(int Id_user) {
        TopicFragment fragment = new TopicFragment();
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
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_topic, container, false);
        initUI();
        callApiCategory();
        return mView;
    }


    private void initUI() {
        rcv_topic_category = mView.findViewById(R.id.rcv_topic_category);
        topicCategoryAdapter = new TopicCategoryAdapter();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        rcv_topic_category.setLayoutManager(gridLayoutManager);
        rcv_topic_category.setNestedScrollingEnabled(false);


    }

    private void callApiCategory() {
        ApiCategory.apiService.getListCategory().enqueue(new Callback<List<TopicCategory>>() {
            @Override
            public void onResponse(Call<List<TopicCategory>> call, Response<List<TopicCategory>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<TopicCategory> apiCategories = response.body();
                    Map<String, Integer> categoryImageMap = getCategoryImageMap();

                    List<TopicCategory> topicCategoryList = new ArrayList<>();
                    for (TopicCategory apiCategory : apiCategories) {
                        String categoryName = apiCategory.getTentheloai();
                        Integer imageResId = categoryImageMap.get(categoryName);
                        if (imageResId != null) {
                            topicCategoryList.add(new TopicCategory(apiCategory.getId_category(), categoryName, imageResId));
                        }
                    }

                    topicCategoryAdapter.setData(topicCategoryList, new IClickItemCategory() {
                        @Override
                        public void clickItemBook(TopicCategory topicCategory) {
                            Intent intent = new Intent(getActivity(), BookInCategoryActivity.class);
                            intent.putExtra("category_id", topicCategory.getId_category());
                            intent.putExtra("category_name", topicCategory.getTentheloai());
                            intent.putExtra("Id_user", Id_user);
                            startActivity(intent);
                        }
                    });
                    rcv_topic_category.setAdapter(topicCategoryAdapter);
                } else {
                    // Xử lý khi response không thành công
                    Log.e("API_ERROR", "Response not successful");
                }
            }

            @Override
            public void onFailure(Call<List<TopicCategory>> call, Throwable t) {
                Log.e("API_ERROR", "API call failed", t);
            }
        });
    }

    private Map<String, Integer> getCategoryImageMap() {
        Map<String, Integer> categoryImageMap = new HashMap<>();
        categoryImageMap.put("Văn học", R.drawable.gradient_background);
        categoryImageMap.put("Cổ điển", R.drawable.gradient_background);
        categoryImageMap.put("Lịch sử", R.drawable.gradient_background);
        categoryImageMap.put("Lãng mạn", R.drawable.gradient_background);
        categoryImageMap.put("Phiêu lưu", R.drawable.gradient_background);
        categoryImageMap.put("Truyện ngắn", R.drawable.gradient_background);
        categoryImageMap.put("Nghệ thuật", R.drawable.gradient_background);
        categoryImageMap.put("Trinh thám", R.drawable.gradient_background);
        categoryImageMap.put("Kinh dị", R.drawable.gradient_background);
        categoryImageMap.put("Hài kịch", R.drawable.gradient_background);
        return categoryImageMap;
    }
}