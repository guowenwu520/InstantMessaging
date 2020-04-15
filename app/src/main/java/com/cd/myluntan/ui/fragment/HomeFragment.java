package com.cd.myluntan.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.cd.myluntan.adapter.HomeTabFragmentAdapter;
import com.cd.myluntan.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private final static String TAG = HomeFragment.class.getCanonicalName();

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private HomeTabFragmentAdapter homeTabFragmentAdapter;


    private ArrayList<String> tabList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        tabList.clear();
        for (int i = 0; i < 10; i++) {
            tabList.add("sdfsf==" + i);
        }
        initTabFragment();
        return view;
    }

    private void initTabFragment() {
        homeTabFragmentAdapter = new HomeTabFragmentAdapter(getChildFragmentManager());
        viewPager.setAdapter(homeTabFragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);
        // 更新适配器数据
        Log.d(TAG,"initTabFragment==tabList"+tabList.size());
        homeTabFragmentAdapter.setList(tabList);
        Log.d(TAG,"initTabFragment==tabList"+tabList.size());
    }

    private void initView(View view) {
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);
    }
}
