package com.cd.myluntan.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cd.myluntan.R;
import com.cd.myluntan.adapter.SearchListAdapter;

public class SearchActivity extends BaseActivity {
    private EditText searchMessage;
    private ImageView search;
    private RecyclerView recyclerView;

    private SearchListAdapter searchListAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_search);
        initView();
        initSearch();
        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchListAdapter=new SearchListAdapter(this);
        recyclerView.setAdapter(searchListAdapter);

    }

    private void initSearch() {
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SearchActivity.this,searchMessage.getText(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        searchMessage=findViewById(R.id.searchMessage);
        search = findViewById(R.id.search);
        recyclerView=findViewById(R.id.recyclerView);
    }
}
