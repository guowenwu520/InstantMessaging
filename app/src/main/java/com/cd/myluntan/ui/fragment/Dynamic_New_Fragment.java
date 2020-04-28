package com.cd.myluntan.ui.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cd.myluntan.R;
import com.cd.myluntan.adapter.Dynamic_show_Adapter;
import com.cd.myluntan.adapter.RecyclerViewOnScrollListenerAdapter;
import com.cd.myluntan.data_connection.Data_Access;
import com.cd.myluntan.entrty.Dynamic;
import com.cd.myluntan.entrty.Imgs;
import com.cd.myluntan.entrty.Comment;
import com.cd.myluntan.entrty.Label;
import com.cd.myluntan.entrty.User;
import com.cd.myluntan.entrty.Praise;
import com.cd.myluntan.interfaceo.NetworkCallback;
import com.cd.myluntan.ui.activity.Publish_Dynamic_Activity;
import com.cd.myluntan.utils.JsonUitl;
import com.cd.myluntan.utils.Singletion;
import com.cd.myluntan.utils.ToolAnimation;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

import static com.cd.myluntan.data_connection.Global_Url_Parameters.GETALLDYNAMIC;
import static com.cd.myluntan.data_connection.Global_Url_Parameters.ISCOMMIT;
import static com.cd.myluntan.data_connection.Global_Url_Parameters.NEW;
import static com.cd.myluntan.data_connection.Global_Url_Parameters.NOCOMMIT;
import static com.cd.myluntan.data_connection.Global_Url_Parameters.URL;

public class Dynamic_New_Fragment extends BaseFragment {
    private final static String TAG = Dynamic_New_Fragment.class.getCanonicalName();
    Dynamic_show_Adapter myRecycleViewClassAdapter;
    private RecyclerView recyclerView;
    private CardView releaseCardView;
    private ImageView release;
    private View view;
    private RelativeLayout mian_lay;
    private int pageSize = 10;
    private int pageNum = 1;
    private boolean isLoading;
    private ProgressBar loadmorePB;
    private SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<Dynamic> dynamics = new ArrayList<>();
    private boolean isReleaseShow = true;
    private boolean isAnimationEnd = true;
    private Activity context;

    public Dynamic_New_Fragment(FragmentActivity activity) {
        context = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_new_dynamic, container, false);
        initView(view);
        setDataDynamic(new ArrayList<>());
        initRelease();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();
        showList();
    }

    private void initRelease() {
        //
        release.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Publish_Dynamic_Activity.class));
            }
        });
        showLiuList(true);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showLiuList(true);
            }
        });
    }

    private void initView(View view) {
        release = view.findViewById(R.id.release);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_layout);
        releaseCardView = view.findViewById(R.id.releaseCardView);
        recyclerView = view.findViewById(R.id.recyclerView);
        loadmorePB = (ProgressBar) view.findViewById(R.id.pb_load_more);
        mian_lay = view.findViewById(R.id.mian_lay);
        showLiuList(true);
    }

    private void showLiuList(boolean isRefresh) {
        isLoading = true;
        dynamics = new ArrayList<>();
        if (isRefresh) {
            pageNum = 1;
            swipeRefreshLayout.setRefreshing(true);
            showList();
        } else {
            pageNum++;
            loadmorePB.setVisibility(View.VISIBLE);
        }
    }

    //获取所有用户
    private void getUserNames() {
        ArrayList<User> follows = Singletion.getInstance().getFollow();
        for (int i = 0; i < follows.size(); i++) {
            for (int j = 0; j < dynamics.size(); j++) {
                if (dynamics.get(j).getUser().getName().equals(follows.get(i).getName())) {
                    dynamics.get(j).getUser().setIsFollow(User.TYPE_IS_FOLLOW);
                }
            }
        }
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setDataDynamicandFinsh(dynamics);
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    private void showList() {
        Map<String, String> map = new HashMap<>();
        map.put("pagenum", pageNum + "");
        map.put("pagesize", pageSize + "");
        map.put("type", NEW);
        Data_Access.AccessStringDate(URL + GETALLDYNAMIC, map, new NetworkCallback() {
            @Override
            public Object parseNetworkResponse(Response response) {

                try {
                    if (response != null) {
                        TypeToken<ArrayList<Dynamic>> dynamicTypeToken = new TypeToken<ArrayList<Dynamic>>() {
                        };
                        Gson gson = new Gson();
                        dynamics = gson.fromJson(response.body().string(), dynamicTypeToken.getType());
                        swipeRefreshLayout.setRefreshing(false);
                        getUserNames();

                    } else return null;
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(Object response) {

            }
        });
    }

    private void setDataDynamicandFinsh(ArrayList<Dynamic> dynamics) {
        if (myRecycleViewClassAdapter != null) {
            myRecycleViewClassAdapter.setDataDynamicandFinsh(dynamics);
        }
    }

    private void setDataDynamic(ArrayList<Dynamic> dynamics) {
        myRecycleViewClassAdapter = new Dynamic_show_Adapter(getActivity(), dynamics, 1, mian_lay);
        recyclerView.setAdapter(myRecycleViewClassAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addOnScrollListener(new RecyclerViewOnScrollListenerAdapter() {
            @Override
            public void onLoadMore() {

            }

            @Override
            public void onScroll(RecyclerView recyclerView, int dx, int dy) {
                bottomUpdateCallback.bottomBarShow(dy);
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
}
