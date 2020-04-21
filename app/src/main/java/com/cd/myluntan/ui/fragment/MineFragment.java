package com.cd.myluntan.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cd.myluntan.R;
import com.cd.myluntan.ui.activity.PersonalActivity;
import com.cd.myluntan.utils.WindowUitls;

public class MineFragment extends Fragment {
    private View view;
    private TextView personal;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        WindowUitls.setColorTopBar(getActivity(), R.color.colorPrimary);
        WindowUitls.setColorTextTopBarWriter(getActivity());
        view = inflater.inflate(R.layout.fragment_mine, container, false);
        initView();
        initPersonal();
        return view;
    }

    private void initPersonal() {
        personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), PersonalActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        personal = view.findViewById(R.id.personal);
    }
}