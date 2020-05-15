package com.cd.myluntan.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

public class AttentionActivity extends BaseActivity implements ContactContract.View {
    private static final String TAG = AttentionActivity.class.getCanonicalName();
    private static final String TYPE_ATTENTION = "attention";
    private static final String TYPE_FAN = "fan";
    private ImageView backimg;
    private TextView textView;
    private SmartRefreshLayout smartRefreshLayout;
    private RecyclerView recyclerView;

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
        initSmartRefreshLayout();
        contactPresenter.loadAttention();
    }

    private void initSmartRefreshLayout() {
        //设置下拉刷新和上拉加载监听
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                smartRefreshLayout.setEnableRefresh(true);
                contactPresenter.onRefresh();
                refreshLayout.finishRefresh();
            }
        });

        //上拉加载监听
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                if (contactPresenter.getCurrentPage() >= contactPresenter.getPages()) {
                    refreshLayout.finishLoadMoreWithNoMoreData();  //全部加载完成,没有数据了调用此方法
                } else {
                    contactPresenter.loadAttention();
                }
                refreshLayout.finishLoadMore(2000);
            }
        });
    }

    private void initRecyclerView() {
        contactListAdapter = new ContactListAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(contactListAdapter);
    }

    private void initView() {
        smartRefreshLayout = findViewById(R.id.smartRefreshLayout);
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
    public void onLoadContactSuccess() {
        Log.d(TAG, "onLoadContactSuccess=========="+contactPresenter.getContacts().size());
        runOnUiThread(() -> {
            contactListAdapter.addData(contactListAdapter.getItemCount(), contactPresenter.getContacts());
        });
    }

    @Override
    public void onRefreshSuccess() {
        Log.d(TAG, "onRefreshSuccess=========="+contactPresenter.getContacts().size());
        runOnUiThread(() -> {
            contactListAdapter.replaceAll(contactPresenter.getContacts());
        });
    }

    @Override
    public void onLoadContactFailed(String err) {
        Log.d(TAG, "onLoadContactFailed=========="+err);
    }
}
