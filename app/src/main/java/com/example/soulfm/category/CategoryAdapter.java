package com.example.soulfm.category;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.soulfm.R;
import com.example.soulfm.activities.BookDetailActivity;
import com.example.soulfm.book.Book;
import com.example.soulfm.book.BookAdapter;
import com.example.soulfm.my_interface.IClickitemBookListener;
import com.example.soulfm.my_interface.IClickitemViewAllListener;

import java.io.Serializable;
import java.util.List;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>{

    private Context mContext;
    private List<Category> mListCategory;
    private IClickitemViewAllListener iClickitemViewAllListener;
    private int Id_user;

    public CategoryAdapter(Context mContext, IClickitemViewAllListener iClickitemViewAllListener,int Id_user){
        this.mContext = mContext;
        this.iClickitemViewAllListener = iClickitemViewAllListener;
        this.Id_user = Id_user;
    }


    public void setData(List<Category> mListCategory){
        this.mListCategory = mListCategory;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = mListCategory.get(position);
        if(category == null){
            return;
        }

        holder.tv_home_name_category.setText(category.getNameCategory());
        holder.tv_home_viewall.setText(category.getViewall());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL,false);
        holder.rcv_homebook.setLayoutManager(linearLayoutManager);

        BookAdapter bookAdapter = new BookAdapter();
        bookAdapter.setData(category.getBooks(), new IClickitemBookListener() {
            @Override
            public void clickItemBook(Book book) {
                onClickGoToDetai(book);
            }
        },Id_user);
        holder.rcv_homebook.setAdapter(bookAdapter);

        holder.tv_home_viewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickitemViewAllListener.clickItemViewAll(category);
            }
        });
    }

    private void onClickGoToDetai(Book book) {
        Intent intent = new Intent(mContext, BookDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("book", book);
        bundle.putInt("Id_user", Id_user);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        if(mListCategory != null){
            return mListCategory.size();
        }
        return 0;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_home_name_category;
        private TextView tv_home_viewall;
        private RecyclerView rcv_homebook;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_home_name_category = itemView.findViewById(R.id.tv_home_name_category);
            tv_home_viewall = itemView.findViewById(R.id.tv_home_viewall);
            rcv_homebook = itemView.findViewById(R.id.rcv_homeBook);
        }
    }
}
