package com.cd.myluntan.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.cd.myluntan.adapter.HomeTabFragmentAdapter;
import com.cd.myluntan.R;
import com.cd.myluntan.ui.activity.Publish_Video_Dynamic_Activity;
import com.cd.myluntan.ui.activity.SearchActivity;
import com.cd.myluntan.utils.WindowUitls;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private final static String TAG = HomeFragment.class.getCanonicalName();
    private View view;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private LinearLayout search;
   private ImageView fabuhuatu;
    private HomeTabFragmentAdapter homeTabFragmentAdapter;


    private ArrayList<String> tabList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        WindowUitls.setColorTopBar(getActivity(),R.color.white);
        WindowUitls.setColorTextTopBarBlack(getActivity());
        view = inflater.inflate(R.layout.fragment_home, container, false);
        initView();
        tabList.clear();
        String strp[]=getActivity().getResources().getStringArray(R.array.toplan_string);
        for (int i = 0; i < strp.length; i++) {
            tabList.add(strp[i]);
        }
        initTabFragment();
        initSearch();
        fabuhuatu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), Publish_Video_Dynamic_Activity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void initSearch() {
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(view.getContext(), SearchActivity.class));
            }
        });
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

    private void initView() {
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);
        search=view.findViewById(R.id.search);
        fabuhuatu=view.findViewById(R.id.fabuhuatu);
    }
}
