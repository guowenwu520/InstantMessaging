package com.cd.myluntan.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cd.myluntan.R;
import com.cd.myluntan.adapter.ContactListAdapter;
import com.cd.myluntan.adapter.LoadMoreWrapper;
import com.cd.myluntan.adapter.RecyclerViewOnScrollListenerAdapter;
import com.cd.myluntan.contract.ContactContract;
import com.cd.myluntan.presenter.ContactPresenter;
import com.cd.myluntan.utils.WindowUitls;

import java.util.ArrayList;

public class AttentionActivity extends BaseActivity implements ContactContract.View {
    private static final String TAG = AttentionActivity.class.getCanonicalName();
    private static final String TYPE_ATTENTION = "attention";
    private static final String TYPE_FAN = "fan";
    private ImageView backimg;
    private TextView textView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    private LoadMoreWrapper loadMoreWrapper;
    private ContactListAdapter contactListAdapter;
    private ContactPresenter contactPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attention);
        WindowUitls.setColorTopBar(this, R.color.white);
        WindowUitls.setColorTextTopBarBlack(this);
        contactPresenter = new ContactPresenter(this);
        initView();
        initRecyclerView();
        initSwipeRefreshLayout();
        contactPresenter.loadAttention();
    }

    //下拉刷新
    private void initSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d(TAG, "initSwipeRefreshLayout=====onRefresh=====");
                contactPresenter.onRefresh();
            }
        });
    }

    private void initRecyclerView() {
        contactListAdapter = new ContactListAdapter(this);
        loadMoreWrapper = new LoadMoreWrapper(contactListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(loadMoreWrapper);
        recyclerView.addOnScrollListener(new RecyclerViewOnScrollListenerAdapter() {
            @Override
            public void onLoadMore() {
                loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING);
                contactPresenter.loadAttention();
            }

            @Override
            public void onScroll(RecyclerView recyclerView, int dx, int dy) {

            }
        });
    }

    private void initView() {
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        recyclerView = findViewById(R.id.recyclerView);
        backimg=findViewById(R.id.backimg);
        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        textView=findViewById(R.id.toptitle);
        textView.setText("关注");
    }

    @Override
    public void onLoadContactSuccess(int size) {
        Log.d(TAG, "onLoadContactSuccess=========="+contactPresenter.contacts.size());
        runOnUiThread(() -> {
            swipeRefreshLayout.setRefreshing(false);
            contactListAdapter.addData(contactListAdapter.getItemCount(), contactPresenter.contacts);
            if (size < 15) {
                loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_END);
            } else {
                loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_COMPLETE);
            }
            loadMoreWrapper.notifyDataSetChanged();
        });
    }

    @Override
    public void onRefreshSuccess(int size) {
        Log.d(TAG, "onRefreshSuccess=========="+contactPresenter.contacts.size());
        runOnUiThread(() -> {
            swipeRefreshLayout.setRefreshing(false);
            contactListAdapter.replaceAll(contactPresenter.contacts);
            if (size < 15) {
                loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_END);
            } else {
                loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_COMPLETE);
            }
            loadMoreWrapper.notifyDataSetChanged();
        });
    }

    @Override
    public void onLoadContactFailed(String err) {
        Log.d(TAG, "onLoadContactFailed=========="+err);
        swipeRefreshLayout.setRefreshing(false);
    }
}
