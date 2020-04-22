package com.cd.myluntan.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cd.myluntan.R;
import com.cd.myluntan.ui.activity.EditProfileActivity;
import com.cd.myluntan.ui.activity.PersonalActivity;
import com.cd.myluntan.utils.WindowUitls;

public class MineFragment extends Fragment implements View.OnClickListener {
    private View view;
    private LinearLayout attentionLL, fanLL, accessLL, rankLL, dynamic, topic, history, set;
    private TextView personal, signIn, attentionNum, attention, fanNum, fan, accessNum, access, rankNum, rank;
    private ImageView QR_code, avatar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        WindowUitls.setColorTopBar(getActivity(), R.color.colorPrimary);
        WindowUitls.setColorTextTopBarWriter(getActivity());
        view = inflater.inflate(R.layout.fragment_mine, container, false);
        initView();
        return view;
    }

    private void initView() {
        personal = view.findViewById(R.id.personal);
        attentionLL = view.findViewById(R.id.attentionLL);
        fanLL = view.findViewById(R.id.fanLL);
        accessLL = view.findViewById(R.id.accessLL);
        rankLL = view.findViewById(R.id.rankLL);
        dynamic = view.findViewById(R.id.dynamic);
        topic = view.findViewById(R.id.topic);
        history = view.findViewById(R.id.history);
        set = view.findViewById(R.id.set);
        signIn = view.findViewById(R.id.signIn);
        attentionNum = view.findViewById(R.id.attentionNum);
        attention = view.findViewById(R.id.attention);
        fanNum = view.findViewById(R.id.fanNum);
        fan = view.findViewById(R.id.fan);
        accessNum = view.findViewById(R.id.accessNum);
        access = view.findViewById(R.id.access);
        rankNum = view.findViewById(R.id.rankNum);
        rank = view.findViewById(R.id.rank);
        QR_code = view.findViewById(R.id.QR_code);
        avatar = view.findViewById(R.id.avatar);
        attentionLL.setOnClickListener(this);
        fanLL.setOnClickListener(this);
        accessLL.setOnClickListener(this);
        rankLL.setOnClickListener(this);
        dynamic.setOnClickListener(this);
        topic.setOnClickListener(this);
        history.setOnClickListener(this);
        set.setOnClickListener(this);
        personal.setOnClickListener(this);
        signIn.setOnClickListener(this);
        QR_code.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.attentionLL:
                Toast.makeText(view.getContext(),"attentionLL",Toast.LENGTH_SHORT).show();
                break;
            case R.id.fanLL:
                Toast.makeText(view.getContext(),"fanLL",Toast.LENGTH_SHORT).show();
                break;
            case R.id.accessLL:
                Toast.makeText(view.getContext(),"accessLL",Toast.LENGTH_SHORT).show();
                break;
            case R.id.rankLL:
                Toast.makeText(view.getContext(),"rankLL",Toast.LENGTH_SHORT).show();
                break;
            case R.id.dynamic:
                Toast.makeText(view.getContext(),"dynamic",Toast.LENGTH_SHORT).show();
                break;
            case R.id.topic:
                Toast.makeText(view.getContext(),"topic",Toast.LENGTH_SHORT).show();
                break;
            case R.id.history:
                Toast.makeText(view.getContext(),"history",Toast.LENGTH_SHORT).show();
                break;
            case R.id.set:
                Toast.makeText(view.getContext(),"set",Toast.LENGTH_SHORT).show();
                break;
            case R.id.signIn:
                Toast.makeText(view.getContext(),"signIn",Toast.LENGTH_SHORT).show();
                break;
            case R.id.QR_code:
                Toast.makeText(view.getContext(),"QR_code",Toast.LENGTH_SHORT).show();
                break;
            case R.id.personal:
                Intent intent = new Intent(view.getContext(), EditProfileActivity.class);
                startActivity(intent);
                break;
        }
    }
}