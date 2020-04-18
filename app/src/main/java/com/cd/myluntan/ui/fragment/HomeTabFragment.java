package com.cd.myluntan.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cd.myluntan.adapter.LoadMoreWrapper;
import com.cd.myluntan.R;
import com.cd.myluntan.ui.activity.MainActivity;
import com.cd.myluntan.adapter.HomeTabListAdapter;
import com.cd.myluntan.adapter.RecyclerViewOnScrollListenerAdapter;

import java.util.ArrayList;

public class HomeTabFragment extends BaseFragment{
    private final static String TAG = HomeTabFragment.class.getCanonicalName();
    private Activity activity;
    private RecyclerView recyclerView;

    private String name;
    private ArrayList<String> homeTabList = new ArrayList<>();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (MainActivity)context;//保存Context引用
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_tab, container, false);
        Bundle bundle = getArguments();
        name = bundle != null ? bundle.getString("name") : null;
        if (name == null) {
            name = "参数非法";
        }
        initView(view);
        initRecyclerView(view);

        return view;
    }
//    public static void setBottomUpdateCallback(BottomUpdateCallback interfaceCall2) {
//        bottomUpdateCallback = interfaceCall2;
//    }

    private void initRecyclerView(final View view) {
        for (int i = 0; i < 10; i++) {
            homeTabList.add("数据"+i);
        }

        final HomeTabListAdapter homeTabListAdapter = new HomeTabListAdapter(view.getContext());
        homeTabListAdapter.setData(homeTabList);
        final LoadMoreWrapper loadMoreWrapper = new LoadMoreWrapper(homeTabListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(loadMoreWrapper);
        recyclerView.addOnScrollListener(new RecyclerViewOnScrollListenerAdapter() {
            @Override
            public void onLoadMore() {//加载跟多
                loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING);
                if (homeTabList.size() < 100) {
                    homeTabListAdapter.setData(homeTabList);
                    Log.d(TAG,"onLoadMore===="+homeTabList.size());
                    loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_COMPLETE);
                } else {
                    // 显示加载到底的提示
                    loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_END);
                }
            }

            @Override
            public void onScroll(RecyclerView recyclerView, int dx, int dy) {
                bottomUpdateCallback.bottomBarShow(dy);
            }
        });

    }

    private void initView(View view) {
        recyclerView =view.findViewById(R.id.recyclerView);
    }


}
