package com.cd.myluntan.ui.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cd.myluntan.R;
import com.cd.myluntan.adapter.Dynamic_show_Adapter;
import com.cd.myluntan.adapter.RecyclerViewOnScrollListenerAdapter;
import com.cd.myluntan.entrty.Dynamic;
import com.cd.myluntan.entrty.Imgs;
import com.cd.myluntan.entrty.Comment;
import com.cd.myluntan.entrty.Label;
import com.cd.myluntan.entrty.User;
import com.cd.myluntan.entrty.Praise;
import com.cd.myluntan.ui.activity.Publish_Dynamic_Activity;
import com.cd.myluntan.utils.Singletion;
import com.cd.myluntan.utils.ToolAnimation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.cd.myluntan.data_connection.Global_Url_Parameters.ISCOMMIT;
import static com.cd.myluntan.data_connection.Global_Url_Parameters.NOCOMMIT;

public class Dynamic_New_Fragment extends BaseFragment{
    private final static String TAG = Dynamic_New_Fragment.class.getCanonicalName();

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
    ArrayList<Dynamic> dynamics=new ArrayList<>();
    private boolean isReleaseShow = true;
    private boolean isAnimationEnd = true;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_new_dynamic, container, false);
        initView(view);
        initRelease();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(Singletion.getInstance().getDynamic()!=null) {
            dynamics.set(Singletion.getInstance().getPostion(), Singletion.getInstance().getDynamic());
            setDataDynamic(dynamics);
            Singletion.getInstance().setDynamic(null);
        }
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
        recyclerView=view.findViewById(R.id.recyclerView);
        loadmorePB = (ProgressBar) view.findViewById(R.id.pb_load_more);
        mian_lay=view.findViewById(R.id.mian_lay);
        showLiuList(true);
    }

    private void showLiuList(boolean isRefresh) {
        isLoading = true;
        dynamics=new ArrayList<>();
        for (int i=0;i<20;i++){
            Dynamic dynamic=new Dynamic();
            dynamic.setId("1111"+i);
            dynamic.setTime("12:12");
            dynamic.setType("we");
            dynamic.setMag("的输入法堵塞封测人非常v二等分测温人非完人法国"+i);
            User user=new User();
            user.setAge("23");
            user.setHead_url("https://dss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3675415932,4054970339&fm=26&gp=0.jpg");
            user.setId("122332312"+i);
            user.setSignaturnre("天天向上");
            user.setName("拉是的黑吧");
            dynamic.setUser(user);
            ArrayList<Comment> comments=new ArrayList<>();
            Comment comment=new Comment();
            comment.setId("23423"+i);
            comment.setCommit_mag("kykyky可以");
            comment.setDynamic_id("1111"+i);
            comment.setCommit_time("12:23");
            comment.setType(NOCOMMIT);
            comment.setUser(user);
            comment.setUsered(user);
            comments.add(comment);
            comments.add(comment);
            if (i==2){
                comment.setType(ISCOMMIT);
                ArrayList<Comment> comments1=new ArrayList<>();
                comments1.add(comment);
                comment.setComments(comments1);
            }
            if (i==3){
                comment.setType(ISCOMMIT);
                ArrayList<Comment> comments1=new ArrayList<>();
                comments1.add(comment);
                comments1.add(comment);
                comment.setComments(comments1);
            }
            dynamic.setComments(comments);
            ArrayList<Label> labels=new ArrayList<>();
            Label label=new Label();
            label.setId("123213");
            label.setDynamic_id("1111"+i);
            label.setLabel_mag("科技");
            labels.add(label);
             dynamic.setLabels(labels);
            ArrayList<Praise> praises=new ArrayList<>();
            Praise praise=new Praise();
            praise.setId("23234"+i);
            praise.setDynamic_id("1111"+i);
            praise.setUser(user);
            praises.add(praise);
             dynamic.setPraises(praises);
            ArrayList<Imgs> imgs=new ArrayList<>();
            imgs.add(new Imgs("1111","https://dss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3675415932,4054970339&fm=26&gp=0.jpg"));
            dynamic.setImgs(imgs);
            dynamics.add(dynamic);
        }
        setDataDynamic(dynamics);

        if(isRefresh){
            pageNum = 1;
            swipeRefreshLayout.setRefreshing(true);
            showList();
        }else{
            pageNum++;
            loadmorePB.setVisibility(View.VISIBLE);
        }
    }
    @SuppressLint("StaticFieldLeak")
    private void showList() {
        Map<String,String> map=new HashMap<>();
        map.put("pagenum",pageNum+"");
        map.put("pagesize",pageSize+""); swipeRefreshLayout.setRefreshing(false);
//        Data_Access.AccessStringDate("sd", map, new NetworkCallback() {
//            @Override
//            public Object parseNetworkResponse(Response response) {
////                try {
//                    if(response!=null) {
////                        TypeToken<ArrayList<Dynamic>> dynamicTypeToken=new TypeToken<>();
////                      dynamics= (ArrayList<Dynamic>) JsonUitl.jsonToObgect(response.body().string(),dynamicTypeToken);
//                    }else  return null;
////                } catch (IOException e) {
////                    e.printStackTrace();
////                }
//                swipeRefreshLayout.setRefreshing(false);
//                setDataDynamic(dynamics);
//
//                return null;
//            }
//
//            @Override
//            public void onError(Call call, Exception e) {
//
//            }
//
//            @Override
//            public void onResponse(Object response) {
//
//            }
//        });
    }

    private void setDataDynamic(ArrayList<Dynamic> dynamics) {
        Dynamic_show_Adapter myRecycleViewClassAdapter=new Dynamic_show_Adapter(getActivity(),dynamics, 1, mian_lay);
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
