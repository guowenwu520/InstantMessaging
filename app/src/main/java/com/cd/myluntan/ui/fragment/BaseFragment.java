package com.cd.myluntan.ui.fragment;

import androidx.fragment.app.Fragment;

import com.cd.myluntan.interfaceo.InterfaceCall;

public class BaseFragment extends Fragment {
    static InterfaceCall interfaceCall;

    public static void setInterfaceCall(InterfaceCall interfaceCall2) {
        interfaceCall = interfaceCall2;
    }
}
