package com.example.soulfm.topic_category;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.soulfm.R;
import com.example.soulfm.my_interface.IClickItemCategory;

import java.util.List;

public class TopicCategoryAdapter extends RecyclerView.Adapter<TopicCategoryAdapter.TopicCategoryViewHolder>{

    private List<TopicCategory> topicCategoryList;

    private IClickItemCategory iClickItemCategory;

    public void setData(List<TopicCategory> topicCategoryList, IClickItemCategory iClickItemCategory){
        this.topicCategoryList = topicCategoryList;
        this.iClickItemCategory = iClickItemCategory;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TopicCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topic_category, parent, false);
        return new TopicCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopicCategoryViewHolder holder, int position) {
        TopicCategory topicCategory = topicCategoryList.get(position);
        if(topicCategory == null){
            return;
        }
        holder.iv_topic_category.setImageResource(topicCategory.getImageCategory());
        holder.tv_name_topic.setText(topicCategory.getTentheloai());
        holder.bind(topicCategory, iClickItemCategory);
    }

    @Override
    public int getItemCount() {
        if(topicCategoryList != null){
            return topicCategoryList.size();
        }
        return 0;
    }

    public class TopicCategoryViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_topic_category;
        private TextView tv_name_topic;
        public TopicCategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_topic_category = itemView.findViewById(R.id.iv_topic_category);
            tv_name_topic = itemView.findViewById(R.id.tv_name_topic);
        }

        public void bind(TopicCategory topicCategory, IClickItemCategory iClickItemCategory) {
            itemView.setOnClickListener(view -> iClickItemCategory.clickItemBook(topicCategory));
        }
    }
}
