package com.cd.myluntan.ui.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
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

import com.cd.myluntan.R;
import com.cd.myluntan.adapter.DynamicListAdapter;
import com.cd.myluntan.adapter.LoadMoreWrapper;
import com.cd.myluntan.adapter.RecyclerViewOnScrollListenerAdapter;
import com.cd.myluntan.utils.ToolAnimation;

import java.util.ArrayList;

public class Dynamic_New_Fragment extends BaseFragment{
    private final static String TAG = Dynamic_New_Fragment.class.getCanonicalName();

    private RecyclerView recyclerView;
    private CardView releaseCardView;
    private ImageView release;
    private View view;

    private DynamicListAdapter dynamicListAdapter;

    private boolean isReleaseShow = true;
    private boolean isAnimationEnd = true;
    private ArrayList<String> tests = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_new_dynamic, container, false);
        initView(view);
        initRelease();
        initRecyclerView(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }
    private void initRecyclerView(View view) {
        for (int i = 0; i < 10; i++) {
            tests.add("teshi" + i);
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
                    Log.d(TAG, "onLoadMore====" + tests.size());
                    loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_COMPLETE);
                } else {
                    // 显示加载到底的提示
                    loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_END);
                }
            }

            @Override
            public void onScroll(RecyclerView recyclerView, int dx, int dy) {
                interfaceCall.bottomBarShow(dy);
                releaseCardViewAnimation(dy);
            }

            private void releaseCardViewAnimation(int dy) {
                AnimatorSet animatorSet;
                if (isReleaseShow && dy > 10 && isAnimationEnd) {
                    animatorSet = ToolAnimation.scaleNarrowView(releaseCardView);
                    animatorSet.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            super.onAnimationStart(animation);
                            isAnimationEnd = false;
                        }
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            isAnimationEnd = true;
                            releaseCardView.setVisibility(View.GONE);
                        }
                    });
                    animatorSet.start();
                    isReleaseShow = false;
                } else if (!isReleaseShow && dy < -10 && isAnimationEnd) {
                    animatorSet = ToolAnimation.scaleEnlargeView(releaseCardView);
                    animatorSet.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            super.onAnimationStart(animation);
                            releaseCardView.setVisibility(View.VISIBLE);
                            isAnimationEnd = false;
                        }
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            isAnimationEnd = true;
                        }
                    });
                    animatorSet.start();
                    isReleaseShow = true;
                }
            }
        });
    }


    private void initRelease() {
        //
        release.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"点击===");
            }
        });
    }

    private void initView(View view) {
        release = view.findViewById(R.id.release);
        releaseCardView = view.findViewById(R.id.releaseCardView);
        recyclerView=view.findViewById(R.id.recyclerView);
    }
}
