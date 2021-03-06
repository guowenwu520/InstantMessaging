package com.cd.myluntan.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cd.myluntan.adapter.LoadMoreWrapper;
import com.cd.myluntan.R;
import com.cd.myluntan.adapter.DynamicListAdapter;
import com.cd.myluntan.adapter.RecyclerViewOnScrollListenerAdapter;
import com.cd.myluntan.utils.ToolAnimation;

import java.util.ArrayList;

public class DynamicFragment extends BaseFragment {
    private final static String TAG = DynamicFragment.class.getCanonicalName();
    private View view;
    private RecyclerView recyclerView;
    private CardView releaseCardView;
    private ImageView release;

    private DynamicListAdapter dynamicListAdapter;
    private ToolAnimation toolAnimation=new ToolAnimation();

    private boolean isReleaseShow=true;
    private ArrayList<String> tests=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dynamic, container, false);
        initView();
        initRecyclerView();
        initRelease();
        return view;
    }

    private void initRelease() {
        release.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"点击===");
            }
        });
    }

    private void initRecyclerView() {
        for (int i = 0; i < 10; i++) {
            tests.add("teshi"+i);
        }
        dynamicListAdapter = new DynamicListAdapter(view.getContext());
        dynamicListAdapter.setData(tests);
        final LoadMoreWrapper loadMoreWrapper = new LoadMoreWrapper(dynamicListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(loadMoreWrapper);
        recyclerView.addOnScrollListener(new RecyclerViewOnScrollListenerAdapter() {
            @Override
            public void onLoadMore() {//加载跟多
                loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING);
                if (tests.size() < 100) {
                    dynamicListAdapter.setData(tests);
                    Log.d(TAG,"onLoadMore===="+tests.size());
                    loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_COMPLETE);
                } else {
                    // 显示加载到底的提示
                    loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_END);
                }
            }

            @Override
            public void onScroll(RecyclerView recyclerView, int dx, int dy) {
                interfaceCall.bottomBarShow(dy);
                if (isReleaseShow &&dy>5&&toolAnimation.isTranslationShowAnimation){
                    toolAnimation.scaleNarrowView(releaseCardView);
                    isReleaseShow = false;
                }else if (!isReleaseShow &&dy<-5&&toolAnimation.isTranslationShowAnimation){
                    toolAnimation.scaleEnlargeView(releaseCardView);
                    isReleaseShow = true;
                }
            }
        });
    }

    private void initView() {
        recyclerView=view.findViewById(R.id.recyclerView);
        releaseCardView = view.findViewById(R.id.releaseCardView);
        release = view.findViewById(R.id.release);
    }
}
