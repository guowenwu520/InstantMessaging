package com.cd.myluntan.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cd.myluntan.R;
import com.cd.myluntan.adapter.LearnGroupListAdapter;
import com.cd.myluntan.entrty.LearnGroup;
import com.cd.myluntan.entrty.LearnMaterials;
import com.cd.myluntan.entrty.User;
import com.cd.myluntan.ui.activity.BaseActivity;
import com.cd.myluntan.utils.WindowUitls;

import java.util.ArrayList;
import java.util.Date;

public class LearnFragment extends BaseFragment {
    private static final String TAG = LearnFragment.class.getCanonicalName();
    private View view;
    private RecyclerView recyclerView;

    private LearnGroupListAdapter learnGroupListAdapter;
    private ArrayList<String> pagerListItems;
    private ArrayList<LearnGroup> learnGroups = new ArrayList<>();;

    /**
     * 上次高亮显示的位置
     */
    private int prePosition = 0;
    /**
     * 是否已经滚动
     */
    private boolean isDragging = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        WindowUitls.setColorTopBar(getActivity(), R.color.colorPrimary);
        WindowUitls.setColorTextTopBarWriter(getActivity());
        view = inflater.inflate(R.layout.fragment_learn, container, false);
        initView();
        initRecyclerView();
        return view;
    }

    private void initRecyclerView() {
        getData();
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        learnGroupListAdapter = new LearnGroupListAdapter(view.getContext());
        recyclerView.setAdapter(learnGroupListAdapter);
        learnGroupListAdapter.setData(learnGroups);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                bottomUpdateCallback.bottomBarShow(dy);
            }
        });
    }

    private void getData() {
        for (int i = 0; i < 2; i++) {
            LearnGroup learnGroup = new LearnGroup();
            learnGroup.setId(new Date().getTime() + "");
            learnGroup.setTitle("本周优质好课" + i);
            learnGroup.setCreateDate(new Date());
            ArrayList<LearnMaterials> learnMaterials = new ArrayList<>();
            for (int j = 0; j < 4; j++) {
                LearnMaterials learnMaterials1 = new LearnMaterials();
                learnMaterials1.setId(new Date().getTime() + "");
                User user =new User();
                user.setName("作者" + j);
                learnMaterials1.setAuthor(user);
                learnMaterials1.setClassHour(5);
                learnMaterials1.setTitle("标题" + j);
                learnMaterials1.setUserNum(1234);
                learnMaterials.add(learnMaterials1);
            }
            learnGroup.setLearnMaterials(learnMaterials);
            learnGroups.add(learnGroup);
        }
    }



    private void initView() {
        recyclerView = view.findViewById(R.id.recyclerView);
    }
}
