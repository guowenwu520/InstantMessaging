package com.cd.myluntan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cd.myluntan.R;

import java.util.ArrayList;

public class DynamicListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<String> tests=new ArrayList<>();
    private Context context;

    public DynamicListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.dynamic_item_view,parent,false);
        return new DynamicListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return tests.size();
    }

    public void setData(ArrayList<String> datas) {
        tests.addAll(datas);
        notifyDataSetChanged();
    }

    class DynamicListViewHolder extends RecyclerView.ViewHolder{
        private RecyclerView recyclerView;
        private TextView content;

        public DynamicListViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(@NonNull View itemView) {
            recyclerView=itemView.findViewById(R.id.recyclerView);
            content = itemView.findViewById(R.id.content);
            content.setText("手动阀手动阀");

        }
    }
}
