package com.cd.myluntan.adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class Dynamic_Top_FragmentAdapter extends FragmentPagerAdapter {

    List<Fragment> list;
    ArrayList<String> names;

    public Dynamic_Top_FragmentAdapter(FragmentManager fm, List<Fragment> list, ArrayList<String> names) {
        super(fm);
        this.list = list;
        this.names=names;
    }//写构造方法，方便赋值调用

    @Override
    public Fragment getItem(int arg0) {
        return list.get(arg0);
    }//根据Item的位置返回对应位置的Fragment，绑定item和Fragment

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return names.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }//设置Item的数量
}