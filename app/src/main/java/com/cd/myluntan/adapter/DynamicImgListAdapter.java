package com.cd.myluntan.adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DynamicImgListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int LAYOUT_FORMAT_ONE = 1;//一到九张
    private final int LAYOUT_FORMAT_TWO = 2;//10张及以上

    private Context context;

    public DynamicImgListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (getItemCount() < 9) {
            return LAYOUT_FORMAT_ONE;
        } else {
            return LAYOUT_FORMAT_TWO;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
