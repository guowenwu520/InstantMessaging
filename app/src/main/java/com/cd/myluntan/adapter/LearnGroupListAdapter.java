package com.cd.myluntan.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cd.myluntan.entrty.LearnGroup;
import com.cd.myluntan.ui.widget.CarouselItemView;
import com.cd.myluntan.ui.widget.LearnGroupItem;

import java.util.ArrayList;

public class LearnGroupListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = LearnGroupListAdapter.class.getCanonicalName();
    private ArrayList<LearnGroup> learnGroups;
    private Context context;

    public LearnGroupListAdapter(Context context) {
        this.context = context;
        learnGroups = new ArrayList<>();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new CarouselViewHolder(new CarouselItemView(context));
        } else {
            return new LearnGroupViewHolder(new LearnGroupItem(context));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof LearnGroupViewHolder) {
            LearnGroupItem learnGroupItem = (LearnGroupItem) holder.itemView;
            learnGroupItem.bindData(learnGroups.get(position));
            learnGroupItem.setScroll(true);
            if (learnGroups.get(position).getLearnMaterials().size() > 0) {
                learnGroupItem.bindRecyclerView(new LearnListAdapter(context, learnGroups.get(position).getLearnMaterials()));
            }
        } else if (holder instanceof CarouselViewHolder) {
            CarouselItemView carouselItemView = (CarouselItemView) holder.itemView;
        }

    }

    @Override
    public int getItemCount() {
        return learnGroups.size();
    }

    public void setData(ArrayList<LearnGroup> data) {
        learnGroups.addAll(data);
        notifyDataSetChanged();
    }

    class CarouselViewHolder extends RecyclerView.ViewHolder {

        public CarouselViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    class LearnGroupViewHolder extends RecyclerView.ViewHolder {

        public LearnGroupViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
