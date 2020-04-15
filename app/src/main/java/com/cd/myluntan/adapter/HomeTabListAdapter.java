package com.cd.myluntan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cd.myluntan.R;

import java.util.ArrayList;

public class HomeTabListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<String> strings=new ArrayList<>();
    private Context context;

    public HomeTabListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.test_item_view,parent,false);
        return new HomeTabListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return strings.size();
    }

    class HomeTabListViewHolder extends RecyclerView.ViewHolder{

        public HomeTabListViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
    public void setData(ArrayList<String> datas){
        strings.addAll(datas);
        notifyDataSetChanged();
    }
}
