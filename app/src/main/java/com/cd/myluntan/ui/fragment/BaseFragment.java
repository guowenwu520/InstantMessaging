package com.cd.myluntan.ui.fragment;

import androidx.fragment.app.Fragment;

import com.cd.myluntan.interfaceo.BottomUpdateCallback;

public class BaseFragment extends Fragment {
    static BottomUpdateCallback bottomUpdateCallback;

    public static void setBottomUpdateCallback(BottomUpdateCallback bottomUpdateCallback2) {
        bottomUpdateCallback = bottomUpdateCallback2;
    }
}
