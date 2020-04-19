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
import com.cd.myluntan.adapter.Praise_Adapter;
import com.cd.myluntan.entrty.Praise;
import com.cd.myluntan.utils.WindowUitls;

import java.util.ArrayList;

public class Praise_Fragment extends Fragment {
    RecyclerView recyclerView;
   ArrayList<Praise> praises=new ArrayList<>();
    Praise_Adapter praise_adapter;
    public Praise_Fragment(ArrayList<Praise> praises) {
        this.praises=praises;
    }


    public void setPraisesAndFlsh(ArrayList<Praise> praises) {
        this.praises = praises;
        if(praise_adapter!=null){
            praise_adapter.flishData(this.praises);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      View view=inflater.inflate(R.layout.praise_fragment, container, false);
        initView(view);
        initData();
         return view;
    }

    private void initData() {
       praise_adapter=new Praise_Adapter(getContext(),praises);
        recyclerView.setAdapter(praise_adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void initView(View view) {
         recyclerView=view.findViewById(R.id.praise_recycle);
    }
}