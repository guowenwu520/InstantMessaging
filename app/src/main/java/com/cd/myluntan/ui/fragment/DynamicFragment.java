package com.cd.myluntan.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.cd.myluntan.adapter.Dynamic_Top_FragmentAdapter;
import com.cd.myluntan.adapter.LoadMoreWrapper;
import com.cd.myluntan.R;
import com.cd.myluntan.adapter.DynamicListAdapter;
import com.cd.myluntan.adapter.RecyclerViewOnScrollListenerAdapter;
import com.cd.myluntan.utils.ToolAnimation;
import com.cd.myluntan.utils.WindowUitls;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class DynamicFragment extends BaseFragment {
    private final static String TAG = DynamicFragment.class.getCanonicalName();
    private View view;
    private ViewPager viewPager;
    private TabLayout toolbar;
   private Dynamic_Top_FragmentAdapter dynamic_top_fragmentAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        WindowUitls.setColorTopBar(getActivity(),R.color.white);
        WindowUitls.setColorTextTopBarBlack(getActivity());
        view = inflater.inflate(R.layout.fragment_dynamic, container, false);
         initView();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        initRelease();
    }

    private void initRelease() {
        viewPager.setAdapter(dynamic_top_fragmentAdapter);
        toolbar.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(0);
    }


    private void initView() {
        toolbar=view.findViewById(R.id.dynamic_topbar);
        viewPager=view.findViewById(R.id.dynamic_viewpage);
        ArrayList<Fragment> list=new ArrayList<>();
        list.add(new Dynsmic_Hot_Fragment());
        list.add(new Dynamic_New_Fragment());
        ArrayList<String> names=new ArrayList<>();
        names.add("热门");
        names.add("最新");
        dynamic_top_fragmentAdapter=new Dynamic_Top_FragmentAdapter(getChildFragmentManager(),list,names);
    }

}
