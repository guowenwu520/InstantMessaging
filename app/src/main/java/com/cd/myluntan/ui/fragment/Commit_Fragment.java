package com.cd.myluntan.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cd.myluntan.R;
import com.cd.myluntan.adapter.Commit_Adapter;
import com.cd.myluntan.adapter.Praise_Adapter;
import com.cd.myluntan.entrty.Comment;
import com.cd.myluntan.entrty.Praise;
import com.cd.myluntan.interfaceo.OnClicktitem;

import java.util.ArrayList;

public class Commit_Fragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<Comment> comments=new ArrayList<>();
    Commit_Adapter commit_adapter;
    public Commit_Fragment(ArrayList<Comment> comments) {
        this.comments=comments;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.commit_fragment, container, false);
        initView(view);initData();
        return view;
    }

    private void initData() {
        commit_adapter=new Commit_Adapter(getContext(),comments);
        recyclerView.setAdapter(commit_adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        commit_adapter.setOnClisterItem(new OnClicktitem() {
            @Override
            public void OnClick(View.OnClickListener onClickListener, int k) {

            }

            @Override
            public void OnLongClick(View.OnLongClickListener onLongClickListener, int k) {

            }
        });
    }

    private void initView(View view) {
        recyclerView=view.findViewById(R.id.commit_recycleview);
    }
}