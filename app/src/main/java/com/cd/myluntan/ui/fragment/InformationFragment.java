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

import java.util.List;

public class InformationFragment extends BaseFragment implements InformationContract.View {
    private static final String TAG = InformationFragment.class.getCanonicalName();
    private static final int MaxSize = 15;
    private View view;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
   private ImageView backimg;
   private TextView textView;
    private InformationPresenter informationPresenter;
    private InformationListAdapter informationListAdapter;
    private LoadMoreWrapper loadMoreWrapper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        WindowUitls.setColorTopBar(getActivity(), R.color.white);
        WindowUitls.setColorTextTopBarBlack(getActivity());
        view = inflater.inflate(R.layout.fragment_information, container, false);
        informationPresenter = new InformationPresenter(this);
        initView();
        initRecyclerView();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                informationPresenter.refreshConversations();
            }
        });
        EMClient.getInstance().chatManager().addMessageListener(messageListener);
        return view;
    }

    private void initView() {
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        recyclerView = view.findViewById(R.id.recyclerView);
        backimg=view.findViewById(R.id.backimg);
        backimg.setVisibility(View.GONE);
        textView=view.findViewById(R.id.toptitle);
        textView.setText("消息");
    }

    private void initRecyclerView() {
        informationListAdapter = new InformationListAdapter(view.getContext());
        informationListAdapter.replaceAll(informationPresenter.conversations);
        loadMoreWrapper = new LoadMoreWrapper(informationListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(loadMoreWrapper);
        recyclerView.addOnScrollListener(new RecyclerViewOnScrollListenerAdapter() {
            @Override
            public void onLoadMore() {
                loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING);
                informationPresenter.loadConversations();
            }

            @Override
            public void onScroll(RecyclerView recyclerView, int dx, int dy) {
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
                swipeRefreshLayout.setRefreshing(false);
                Log.d(TAG, "loadConversationSuccess====" + informationPresenter.conversations.size() + "========" + informationPresenter.conversations.toString());
                informationListAdapter.addData(informationListAdapter.getItemCount(), informationPresenter.conversations);
                if (size < MaxSize) {
                    loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_END);
                } else {
                    loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_COMPLETE);
                }
                loadMoreWrapper.notifyDataSetChanged();
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
                swipeRefreshLayout.setRefreshing(false);
                Log.d(TAG, "refreshConversationsSuccess====" + informationPresenter.conversations.size() + "========" + informationPresenter.conversations.toString());
                informationListAdapter.replaceAll(informationPresenter.conversations);
                if (size < MaxSize) {
                    loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_END);
                } else {
                    loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_COMPLETE);
                }
                loadMoreWrapper.notifyDataSetChanged();
            }
        });
    }
}