package com.example.soulfm.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.soulfm.R;
import com.example.soulfm.edit_collection.EditCollection;
import com.example.soulfm.edit_collection.EditCollectionAdapter;

import java.util.ArrayList;
import java.util.List;

public class EditCollectionActivity extends AppCompatActivity {

    private RecyclerView rcv_edit_collection;
    private EditCollectionAdapter editCollectionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_collection);

        rcv_edit_collection = findViewById(R.id.rcv_edit_collection);
        editCollectionAdapter = new EditCollectionAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rcv_edit_collection.setLayoutManager(linearLayoutManager);
        editCollectionAdapter.setData(getEditCollectionList());
        rcv_edit_collection.setAdapter(editCollectionAdapter);
    }

    private List<EditCollection> getEditCollectionList() {
        List<EditCollection> editCollectionList = new ArrayList<>();

        editCollectionList.add(new EditCollection(false,R.drawable.image_book, "Cha giàu cha nghèo"));
        editCollectionList.add(new EditCollection(false,R.drawable.image_book, "Cha giàu cha nghèo"));
        editCollectionList.add(new EditCollection(false,R.drawable.image_book, "Cha giàu cha nghèo"));
        editCollectionList.add(new EditCollection(false,R.drawable.image_book, "Cha giàu cha nghèo"));
        editCollectionList.add(new EditCollection(false,R.drawable.image_book, "Cha giàu cha nghèo"));
        editCollectionList.add(new EditCollection(false,R.drawable.image_book, "Cha giàu cha nghèo"));
        editCollectionList.add(new EditCollection(false,R.drawable.image_book, "Cha giàu cha nghèo"));

        return editCollectionList;
    }
}