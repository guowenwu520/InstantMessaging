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
import com.cd.myluntan.interfaceo.OnClicktitem;

import java.util.ArrayList;

public class Photograph_Adapater extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<Imgs> imgs=new ArrayList<>();
   Context context;
    OnClicktitem onClicktitem;
    public Photograph_Adapater(ArrayList<Imgs> imgs, Context context) {
        this.imgs=imgs;
        this.context=context;
    }

    public void setOnClicktitem(OnClicktitem onClicktitem) {
        this.onClicktitem = onClicktitem;
    }
    public  void  setImgsandfilsh(ArrayList<Imgs> imgsandfilsh){
        imgs=imgsandfilsh;
        notifyDataSetChanged();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.img_sing_view,parent,false);
        return new myViewHolderClassImg(view,onClicktitem);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Imgs devices=imgs.get(position);
      myViewHolderClassImg myViewHolderClass= (myViewHolderClassImg) holder;
      if(devices.getIMGS_COLUMN_NAME_IMGURL().equals("0")){
          Glide.with(context).load(R.drawable.addimg).into(myViewHolderClass.imgss);
      }else {
          Glide.with(context).load(devices.getIMGS_COLUMN_NAME_IMGURL()).into(myViewHolderClass.imgss);
      }
    }

    @Override
    public int getItemCount() {
        return imgs.size();
    }



    class  myViewHolderClassImg extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        ImageView imgss;
      OnClicktitem onClicktitem;
        @Override
        public void onClick(View v) {
            onClicktitem.OnClick(this,getPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            onClicktitem.OnLongClick(this,getPosition());
            return false;
        }

        public myViewHolderClassImg(View itemView, OnClicktitem onClicktitem) {
            super(itemView);
            imgss=itemView.findViewById(R.id.imgsing);
            itemView.setOnLongClickListener(this);
            itemView.setOnClickListener(this);
        }
    }
}
