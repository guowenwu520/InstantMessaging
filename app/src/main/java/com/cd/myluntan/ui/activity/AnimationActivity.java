package com.cd.myluntan.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cd.myluntan.adapter.LoadMoreWrapper;
import com.cd.myluntan.R;
import com.cd.myluntan.adapter.RecyclerViewOnScrollListenerAdapter;
import com.cd.myluntan.utils.ToolAnimation;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class AnimationActivity extends AppCompatActivity {
    private static final String TAG = AnimationActivity.class.getCanonicalName();
    private RecyclerView recyclerView;
    private CardView bottomBar;
    private LinearLayout first;
    private ArrayList<String> strings = new ArrayList<>();
    private boolean isBottomBarVisibility = true;
    private ToolAnimation toolAnimation = new ToolAnimation();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for (int i = 0; i < 10; i++) {
            strings.add("12313");
        }
        setContentView(R.layout.activity_animation);
        recyclerView = findViewById(R.id.recyclerView);
        bottomBar = findViewById(R.id.bottomBar);
        first = findViewById(R.id.first);
        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "first");
            }
        });

        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter();
        final LoadMoreWrapper loadMoreWrapper = new LoadMoreWrapper(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(loadMoreWrapper);
        recyclerView.addOnScrollListener(new RecyclerViewOnScrollListenerAdapter() {
            @Override
            public void onLoadMore() {//加载跟多
                loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING);

                if (strings.size() < 100) {
                    // 模拟获取网络数据，延时1s
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    getData();
                                    loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_COMPLETE);
                                }
                            });
                        }
                    }, 1000);
                } else {
                    // 显示加载到底的提示
                    loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_END);
                }
            }

            @Override
            public void onScroll(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 5 && isBottomBarVisibility && toolAnimation.isTranslationShowAnimation) {
                    toolAnimation.translationHideViewY(bottomBar);
                    isBottomBarVisibility = false;
                } else if (dy < -5 && !isBottomBarVisibility && toolAnimation.isTranslationShowAnimation) {
                    toolAnimation.translationShowViewY(bottomBar);
                    isBottomBarVisibility = true;
                }
            }
        });
    }

    private void getData() {
        for (int i = 0; i < 20; i++) {
            strings.add("12313");
        }
    }

    class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            TextView textView = new TextView(AnimationActivity.this);
//            View view = View.inflate(AnimationActivity.this, R.layout.activity_main, null);
            View view = LayoutInflater.from(AnimationActivity.this).inflate(R.layout.activity_main, parent, false);
            return new RecyclerViewViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return strings.size();
        }
    }

    class RecyclerViewViewHolder extends RecyclerView.ViewHolder {

        public RecyclerViewViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
