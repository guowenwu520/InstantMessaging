package com.cd.myluntan.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.cd.myluntan.R;
import com.cd.myluntan.adapter.SearchListAdapter;
import com.cd.myluntan.contract.SearchContract;
import com.cd.myluntan.presenter.SearchPresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

public class SearchActivity extends BaseActivity implements SearchContract.View {
    private static final String TAG = SearchActivity.class.getCanonicalName();
    private EditText searchMessage;
    private ImageView search;
    private RecyclerView recyclerView;
    private SearchPresenter searchPresenter;
    private SmartRefreshLayout smartRefreshLayout;
    private ProgressBar progressBar;
    private TextView notMessage;
    private String searchStr = null;

    private SearchListAdapter searchListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_search);
        searchPresenter = new SearchPresenter(this);
        initView();
        initSearch();
        initRecyclerView();
        initSmartRefreshLayout();
    }

    private void initSmartRefreshLayout() {
        //设置下拉刷新和上拉加载监听
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                showLog("刷新:" + searchStr);
                smartRefreshLayout.setEnableRefresh(true);
                searchPresenter.refreshSearch(searchStr);
                refreshLayout.finishRefresh();
                showLog("刷新结束:" + searchStr);
            }
        });

        //上拉加载监听
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                showLog("加载更多:" + searchStr);
                if (searchPresenter.getCurrentPage() >= searchPresenter.getPages()) {
                    refreshLayout.finishLoadMoreWithNoMoreData();  //全部加载完成,没有数据了调用此方法
                }else {
                    searchPresenter.loadMoreSearch(searchStr);
                }
                refreshLayout.finishLoadMore(2000);
                showLog("加载跟多结束:" + searchStr);
            }
        });
    }

    private void showLog(String message) {
        Log.d(TAG, message);
    }

    private void initRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(null);
        //垂直方向的2列
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        //防止Item切换
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recyclerView.setLayoutManager(layoutManager);
        final int spanCount = 2;
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                int[] first = new int[spanCount];
                layoutManager.findFirstCompletelyVisibleItemPositions(first);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && (first[0] == 1 || first[1] == 1)) {
                    layoutManager.invalidateSpanAssignments();
                }
            }
        });
        searchListAdapter = new SearchListAdapter(this);
        recyclerView.setAdapter(searchListAdapter);

    }

    private void initSearch() {
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                searchStr = searchMessage.getText().toString();
                searchPresenter.loadSearchMessage(searchStr);
            }
        });
    }

    private void initView() {
        searchMessage = findViewById(R.id.searchMessage);
        search = findViewById(R.id.search);
        recyclerView = findViewById(R.id.recyclerView);
        smartRefreshLayout = findViewById(R.id.smartRefreshLayout);
        progressBar = findViewById(R.id.progressBar);
        notMessage =findViewById(R.id.notMessage);
    }

    @Override
    public void searchSuccess() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showLog("搜索成功");
                progressBar.setVisibility(View.GONE);
                searchListAdapter.addData(searchListAdapter.getItemCount(), searchPresenter.getSearchList());
                if (searchPresenter.getCurrentPage()==1&&searchPresenter.getSearchList().size()==0){
                    smartRefreshLayout.setVisibility(View.GONE);
                    notMessage.setVisibility(View.VISIBLE);
                }else {
                    notMessage.setVisibility(View.GONE);
                    smartRefreshLayout.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void searchFailed(String err) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showLog(err);
                progressBar.setVisibility(View.GONE);
                toast(err);
            }
        });
    }

    @Override
    public void refreshSearchSuccess() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                searchListAdapter.replaceAll(searchPresenter.getSearchList());
                showLog("刷新成功");
            }
        });
    }

    private void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
