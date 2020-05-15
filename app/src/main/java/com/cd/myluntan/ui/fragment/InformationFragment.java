package com.cd.myluntan.ui.fragment;

/**
 * 消息fragment
 */

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cd.myluntan.R;
import com.cd.myluntan.adapter.EMMessageListenerAdapter;
import com.cd.myluntan.adapter.InformationListAdapter;
import com.cd.myluntan.adapter.LoadMoreWrapper;
import com.cd.myluntan.adapter.RecyclerViewOnScrollListenerAdapter;
import com.cd.myluntan.contract.InformationContract;
import com.cd.myluntan.presenter.InformationPresenter;
import com.cd.myluntan.utils.WindowUitls;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

public class InformationFragment extends BaseFragment implements InformationContract.View {
    private static final String TAG = InformationFragment.class.getCanonicalName();
    private static final int MaxSize = 15;
    private View view;
    private RecyclerView recyclerView;
    private ImageView backimg;
    private TextView textView;
    private SmartRefreshLayout smartRefreshLayout;
    private InformationPresenter informationPresenter;
    private InformationListAdapter informationListAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        WindowUitls.setColorTopBar(getActivity(), R.color.white);
        WindowUitls.setColorTextTopBarBlack(getActivity());
        view = inflater.inflate(R.layout.fragment_information, container, false);
        informationPresenter = new InformationPresenter(this);
        initView();
        initRecyclerView();
        initSmartRefreshLayout();
        EMClient.getInstance().chatManager().addMessageListener(messageListener);
        return view;
    }

    private void initSmartRefreshLayout() {
        //设置下拉刷新和上拉加载监听
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                smartRefreshLayout.setEnableRefresh(true);
                informationPresenter.refreshConversations();
                refreshLayout.finishRefresh();
            }
        });

        //上拉加载监听
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                if (informationPresenter.getCurrentPage() >= informationPresenter.getPages()) {
                    refreshLayout.finishLoadMoreWithNoMoreData();  //全部加载完成,没有数据了调用此方法
                } else {
                    informationPresenter.loadConversations();
                }
                refreshLayout.finishLoadMore(2000);
            }
        });
    }

    private void initView() {
        smartRefreshLayout = view.findViewById(R.id.smartRefreshLayout);
        recyclerView = view.findViewById(R.id.recyclerView);
        backimg = view.findViewById(R.id.backimg);
        backimg.setVisibility(View.GONE);
        textView = view.findViewById(R.id.toptitle);
        textView.setText("消息");
    }

    private void initRecyclerView() {
        informationListAdapter = new InformationListAdapter(view.getContext());
        informationListAdapter.replaceAll(informationPresenter.getConversations());
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(informationListAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                bottomUpdateCallback.bottomBarShow(dy);
            }
        });
    }

    private final EMMessageListener messageListener = new EMMessageListenerAdapter() {
        @Override
        public void onMessageReceived(List<EMMessage> list) {
            super.onMessageReceived(list);
            Log.d(TAG, "messageListener===========onMessageReceived======" + list.toString());
            informationPresenter.refreshConversations();
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        informationPresenter.refreshConversations();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().chatManager().removeMessageListener(messageListener);
    }

    @Override
    public void loadConversationSuccess(int size) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "loadConversationSuccess====" + informationPresenter.getConversations().size() + "========" + informationPresenter.getConversations().toString());
                informationListAdapter.addData(informationListAdapter.getItemCount(), informationPresenter.getConversations());
            }
        });
    }

    @Override
    public void loadConversationFailed() {

    }

    @Override
    public void refreshConversationsSuccess(int size) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "refreshConversationsSuccess====" + informationPresenter.getConversations().size() + "========" + informationPresenter.getConversations().toString());
                informationListAdapter.replaceAll(informationPresenter.getConversations());
            }
        });
    }
}