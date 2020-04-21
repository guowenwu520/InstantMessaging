package com.cd.myluntan.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.cd.myluntan.R;
import com.cd.myluntan.adapter.HomeTabFragmentAdapter;
import com.cd.myluntan.entrty.Imgs;
import com.cd.myluntan.utils.WindowUitls;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class Show_Sing_Image_Fragment extends Fragment {
   Imgs imgs;
   ImageView imageView;
    public Show_Sing_Image_Fragment(Imgs imgs) {
        this.imgs=imgs;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        WindowUitls.setColorTopBar(getActivity(), R.color.black);
        WindowUitls.setColorTextTopBarWriter(getActivity());
        View view = inflater.inflate(R.layout.show_sing_images_fragment, container, false);
        initView(view);
        Glide.with(getActivity()).load(imgs.getIMGS_COLUMN_NAME_IMGURL()).into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        return view;
    }


    private void initView(View view) {
        imageView = view.findViewById(R.id.img);
    }
}