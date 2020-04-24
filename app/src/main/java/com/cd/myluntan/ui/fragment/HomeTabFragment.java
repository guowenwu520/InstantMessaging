package com.cd.myluntan.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.cd.myluntan.R;
import com.cd.myluntan.entrty.Home;
import com.cd.myluntan.entrty.User;
import com.cd.myluntan.adapter.HomeTabListAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.Date;

public class HomeTabFragment extends BaseFragment{
    private final static String TAG = HomeTabFragment.class.getCanonicalName();
    private View view;
    private RecyclerView recyclerView;
    private SmartRefreshLayout smartRefreshLayout;

    private HomeTabListAdapter homeTabListAdapter;
    private String name;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_tab, container, false);
        Bundle bundle = getArguments();
        name = bundle != null ? bundle.getString("name") : null;
        if (name == null) {
            name = "参数非法";
        }
        getData();
        initView();
        initSmartRefreshLayout();
        return view;
    }

    private void initSmartRefreshLayout() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(null);
        //垂直方向的2列
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        //防止Item切换
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recyclerView.setLayoutManager(layoutManager);
        final int spanCount = 2;
        //解决底部滚动到顶部时，顶部item上方偶尔会出现一大片间隔的问题
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                int[] first = new int[spanCount];
                layoutManager.findFirstCompletelyVisibleItemPositions(first);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && (first[0] == 1 || first[1] == 1)) {
                    layoutManager.invalidateSpanAssignments();
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                bottomUpdateCallback.bottomBarShow(dy);
            }
        });
        homeTabListAdapter = new HomeTabListAdapter(view.getContext());
        recyclerView.setAdapter(homeTabListAdapter);
        homeTabListAdapter.replaceAll(getData());

        //设置下拉刷新和上拉加载监听
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        homeTabListAdapter.replaceAll(getData());
                        refreshLayout.finishRefresh();
                    }
                },2000);
            }
        });

        //上拉加载监听
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        homeTabListAdapter.addData(homeTabListAdapter.getItemCount(),getData());
                        refreshLayout.finishLoadMore();
                    }
                },2000);
            }
        });
    }

    private ArrayList<Home> getData(){
        ArrayList<Home> homes=new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Home home=new Home();
            User user =new User();
            user.setName("提花之秀"+i);
            user.setAge(22+"");
            user.setNick("飒飒"+i);
            user.setSex("男");
            home.setAuthor(user);
            home.setTitle("标题太长不想写"+i);
            home.setCreateDate(new Date());
            homes.add(home);
        }
        return homes;
    }

    private void initView() {
        recyclerView =view.findViewById(R.id.recyclerView);
        smartRefreshLayout =view.findViewById(R.id.smartRefreshLayout);
    }
}
