package com.cd.myluntan.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cd.myluntan.entrty.LearnMaterials;
import com.cd.myluntan.ui.widget.LearnItemView;

import java.util.ArrayList;

public class LearnListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<LearnMaterials> materials;

    public LearnListAdapter(Context context) {
        this(context, new ArrayList<>());
    }

    public LearnListAdapter(Context context, ArrayList<LearnMaterials> materials) {
        this.context = context;
        this.materials = materials;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LearnViewHolder(new LearnItemView(context));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof LearnViewHolder) {
            LearnItemView learnItemView = (LearnItemView) holder.itemView;
            learnItemView.bindData(materials.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return materials.size();
    }

    public void setData(ArrayList<LearnMaterials> materials) {
        this.materials.addAll(materials);
    }

    class LearnViewHolder extends RecyclerView.ViewHolder {

        public LearnViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,materials.get(getLayoutPosition()).getTitle(),Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
