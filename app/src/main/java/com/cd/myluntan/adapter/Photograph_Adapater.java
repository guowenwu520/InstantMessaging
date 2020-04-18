package com.cd.myluntan.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cd.myluntan.R;
import com.cd.myluntan.entrty.Imgs;

import java.util.ArrayList;

public class Photograph_Adapater extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<Imgs> imgs=new ArrayList<>();
   Context context;
    public Photograph_Adapater(ArrayList<Imgs> imgs, Context context) {
        this.imgs=imgs;
        this.context=context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.img_sing_view,parent,false);
        return new myViewHolderClassImg(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Imgs devices=imgs.get(position);
      myViewHolderClassImg myViewHolderClass= (myViewHolderClassImg) holder;
        Glide.with(context).load(devices.getIMGS_COLUMN_NAME_IMGURL()).into(myViewHolderClass.imgss);

    }

    @Override
    public int getItemCount() {
        return imgs.size();
    }



    class  myViewHolderClassImg extends RecyclerView.ViewHolder {
        ImageView imgss;


        public myViewHolderClassImg(View itemView) {
            super(itemView);
            imgss=itemView.findViewById(R.id.imgsing);
        }
    }
}
