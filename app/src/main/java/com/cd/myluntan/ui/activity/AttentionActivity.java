package com.cd.myluntan.ui.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cd.myluntan.R;
import com.cd.myluntan.adapter.ContactListAdapter;
import com.cd.myluntan.adapter.LoadMoreWrapper;
import com.cd.myluntan.contract.ContactContract;
import com.cd.myluntan.presenter.ContactPresenter;

import java.util.ArrayList;

public class AttentionActivity extends BaseActivity implements ContactContract.View {
    private static final String TAG = AttentionActivity.class.getCanonicalName();
    private static final String TYPE_ATTENTION = "attention";
    private static final String TYPE_FAN = "fan";

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    private LoadMoreWrapper loadMoreWrapper;
    private ContactListAdapter contactListAdapter;
    private ContactPresenter contactPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attention);
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
                contactPresenter.onRefresh();
            }
        });
    }

    private void initRecyclerView() {
        contactListAdapter = new ContactListAdapter(this);
        loadMoreWrapper = new LoadMoreWrapper(contactListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(loadMoreWrapper);
    }

    private void initView() {
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        recyclerView = findViewById(R.id.recyclerView);
    }

    @Override
    public void onLoadContactSuccess(int type) {
        Log.d(TAG, contactPresenter.contacts.size() + "");
        runOnUiThread(() -> {
            swipeRefreshLayout.setRefreshing(false);
            ArrayList<String> arrayList = new ArrayList<>(contactPresenter.contacts);
            if (type == ContactPresenter.PULL_DOWN_TO_REFRESH) {
                contactListAdapter.onRefreshData();
            }
            contactListAdapter.onBindData(arrayList);
            loadMoreWrapper.notifyDataSetChanged();
        });
    }

    @Override
    public void onLoadContactFailed(String err) {
        swipeRefreshLayout.setRefreshing(false);
    }
}
