package com.cd.myluntan.adapter;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.cd.myluntan.ui.fragment.HomeTabFragment;

import java.util.ArrayList;

/**
 * 消息内容子页面适配器
 */
public class HomeTabFragmentAdapter extends FragmentPagerAdapter {
    private final static String TAG = HomeTabFragmentAdapter.class.getCanonicalName();
    private ArrayList<String> names=new ArrayList<>();

    public HomeTabFragmentAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    /**
     * 数据列表
     *
     * @param datas
     */
    public void setList(ArrayList<String> datas) {
        Log.d(TAG,"setList==datas"+datas.size());
        this.names.clear();
        Log.d(TAG,"setList==names"+names.size());
        this.names.addAll(datas);
        Log.d(TAG,"setList==names"+names.size());
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        HomeTabFragment homeTabFragment = new HomeTabFragment();
        Bundle bundle = new Bundle();
        bundle.putString("name", names.get(position));
        homeTabFragment.setArguments(bundle);
        return homeTabFragment;
    }

    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String plateName = names.get(position);
        if (plateName == null) {
            plateName = "";
        } else if (plateName.length() > 15) {
            plateName = plateName.substring(0, 15) + "...";
        }
        return plateName;
    }
}