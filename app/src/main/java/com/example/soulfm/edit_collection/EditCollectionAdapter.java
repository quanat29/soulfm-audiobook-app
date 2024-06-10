package com.example.soulfm.edit_collection;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.soulfm.R;

import java.util.List;

public class EditCollectionAdapter extends RecyclerView.Adapter<EditCollectionAdapter.EditCollectionAdapterViewHolder>{

    private List<EditCollection> editCollectionList;

    public void setData(List<EditCollection> editCollectionList){
        this.editCollectionList = editCollectionList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EditCollectionAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_edit_collection, parent, false);
        return new EditCollectionAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EditCollectionAdapterViewHolder holder, int position) {
        EditCollection editCollection = editCollectionList.get(position);
        if(editCollection == null){
            return;
        }

        holder.cb_item_edit_collection.setChecked(editCollection.isChecked());
        holder.tv_author_name_edit.setText(editCollection.getTitle());
        holder.iv_image_edit.setImageResource(editCollection.getImageId());
    }

    @Override
    public int getItemCount() {
        if(editCollectionList != null){
            return editCollectionList.size();
        }
        return 0;
    }

    public class EditCollectionAdapterViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_author_name_edit;
        private CheckBox cb_item_edit_collection;
        private ImageView iv_image_edit;
        public EditCollectionAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_author_name_edit = itemView.findViewById(R.id.tv_author_name_edit);
            cb_item_edit_collection = itemView.findViewById(R.id.cb_item_edit_collection);
            iv_image_edit = itemView.findViewById(R.id.iv_image_edit);
        }
    }

}
