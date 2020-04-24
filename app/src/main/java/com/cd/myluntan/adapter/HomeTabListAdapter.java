package com.cd.myluntan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cd.myluntan.R;
import com.cd.myluntan.entrty.Home;
import com.cd.myluntan.ui.widget.HomeItemView;

import java.util.ArrayList;

public class HomeTabListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = HomeTabListAdapter.class.getCanonicalName();
    private ArrayList<Home> homes = new ArrayList<>();
    private Context context;

    public HomeTabListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.view_home_item, parent, false);
        return new HomeTabListViewHolder(new HomeItemView(context));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HomeTabListViewHolder){
            HomeItemView homeItemView= (HomeItemView) holder.itemView;
            homeItemView.bindData(homes.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return homes.size();
    }

    class HomeTabListViewHolder extends RecyclerView.ViewHolder {

        public HomeTabListViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,homes.get(getLayoutPosition()).getTitle(),Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    //刷新数据
    public void replaceAll(ArrayList<Home> list) {
        homes.clear();
        if (list != null && list.size() > 0) {
            homes.addAll(list);
        }
        notifyDataSetChanged();
    }

    //添加数据
    public void addData(int position, ArrayList<Home> list) {
        homes.addAll(position, list);
        notifyItemInserted(position);
    }

    //移除数据使用notifyItemRemoved
    public void removeData(int position) {
        homes.remove(position);
        notifyItemRemoved(position);
    }

    public void setData(ArrayList<Home> datas) {
        homes.addAll(datas);
        notifyDataSetChanged();
    }
}
