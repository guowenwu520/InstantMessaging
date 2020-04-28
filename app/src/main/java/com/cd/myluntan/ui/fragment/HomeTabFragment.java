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
import com.cd.myluntan.data_connection.Data_Access;
import com.cd.myluntan.entrty.Dynamic;
import com.cd.myluntan.adapter.HomeTabListAdapter;
import com.cd.myluntan.interfaceo.NetworkCallback;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

import static com.cd.myluntan.data_connection.Global_Url_Parameters.GETALLDYNAMIC;
import static com.cd.myluntan.data_connection.Global_Url_Parameters.SHIPING;
import static com.cd.myluntan.data_connection.Global_Url_Parameters.URL;

public class HomeTabFragment extends BaseFragment{
    private final static String TAG = HomeTabFragment.class.getCanonicalName();
    private View view;
    private RecyclerView recyclerView;
    private SmartRefreshLayout smartRefreshLayout;
    ArrayList<Dynamic> dynamics=new ArrayList<>();

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
        getData(1,name);
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
       getData(1, name);

        //设置下拉刷新和上拉加载监听
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                       getData(1, name);
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
                      getData(2, name);
                        refreshLayout.finishLoadMore();
                    }
                },2000);
            }
        });
    }

    private void getData(int k, String name){
        Map<String,String> map=new HashMap<>();
        map.put("pagenum",1+"");
        map.put("pagesize",10+"");
        map.put("type",SHIPING);
        map.put("classs",name);
        Data_Access.AccessStringDate(URL+GETALLDYNAMIC, map, new NetworkCallback() {
            @Override
            public Object parseNetworkResponse(Response response) {
                try {
                   // swipeRefreshLayout.setRefreshing(false);
                    if(response!=null) {
                        TypeToken<ArrayList<Dynamic>> dynamicTypeToken=new TypeToken<ArrayList<Dynamic>>(){};
                        Gson gson=new Gson();
                        dynamics= gson.fromJson(response.body().string(),dynamicTypeToken.getType());;
                    }else  return null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(k==2){
                            homeTabListAdapter.addData(homeTabListAdapter.getItemCount(),dynamics);
                        }else {
                            homeTabListAdapter.replaceAll(dynamics);
                        }
                    }
                });

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

    private void initView() {
        recyclerView =view.findViewById(R.id.recyclerView);
        smartRefreshLayout =view.findViewById(R.id.smartRefreshLayout);
    }
}
