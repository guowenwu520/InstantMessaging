package com.cd.myluntan.ui.fragment;

/**
 * 消息fragment
 */

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cd.myluntan.R;
import com.cd.myluntan.adapter.InformationListAdapter;
import com.cd.myluntan.adapter.LoadMoreWrapper;
import com.cd.myluntan.adapter.RecyclerViewOnScrollListenerAdapter;
import com.cd.myluntan.contract.InformationContract;
import com.cd.myluntan.presenter.InformationPresenter;
import com.cd.myluntan.utils.WindowUitls;

import java.util.ArrayList;

public class InformationFragment extends BaseFragment implements InformationContract.View {
    private View view;
    private RecyclerView recyclerView;

    private InformationPresenter informationPresenter;
    private InformationListAdapter informationListAdapter;
    private LoadMoreWrapper loadMoreWrapper;
    private ArrayList<String> tests=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        WindowUitls.setColorTopBar(getActivity(),R.color.white);
        WindowUitls.setColorTextTopBarBlack(getActivity());
        view = inflater.inflate(R.layout.fragment_information, container, false);
        informationPresenter = new InformationPresenter(this);
        initView();
        initRecyclerView();
        informationPresenter.loadConversations();
        return view;
    }

    private void initView() {
        recyclerView = view.findViewById(R.id.recyclerView);
    }

    private void initRecyclerView() {
        informationListAdapter = new InformationListAdapter(view.getContext());
        informationListAdapter.setData(informationPresenter.conversations);
        loadMoreWrapper = new LoadMoreWrapper(informationListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(loadMoreWrapper);
        recyclerView.addOnScrollListener(new RecyclerViewOnScrollListenerAdapter() {
            @Override
            public void onLoadMore() {

            }

            @Override
            public void onScroll(RecyclerView recyclerView, int dx, int dy) {
                bottomUpdateCallback.bottomBarShow(dy);
            }
        });
    }

    @Override
    public void loadConversationSuccess() {
        informationListAdapter.setData(informationPresenter.conversations);
        loadMoreWrapper.notifyDataSetChanged();
    }

    @Override
    public void loadConversationFailed() {

    }
}