package com.cd.myluntan.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.cd.myluntan.R;
import com.cd.myluntan.entrty.Comment;
import com.cd.myluntan.interfaceo.OnClicktitem;

import java.util.ArrayList;

public class Comment_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    OnClicktitem onClisterItem;
    ArrayList<Comment> pingls=new ArrayList<>();
   Context contextl;
    private String NOKJIAN;

    public Comment_Adapter(Context context, ArrayList<Comment> pingLuns, String NOKJIAN) {
         this.contextl=context;
         this.NOKJIAN=NOKJIAN;
        this.pingls=pingLuns;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(contextl).inflate(R.layout.ping_lun_view_item,parent,false);
        return new myViewHolderClass(view,onClisterItem);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return pingls.size();
    }

    public void setOnClisterItem(OnClicktitem onClisterItem) {
        this.onClisterItem = onClisterItem;
    }

    class  myViewHolderClass extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
        OnClicktitem onClisterItem;
        TextView nameed_name,name_name,textmsg;
        LinearLayout isadleb;
        @Override
        public void onClick(View v) {
            onClisterItem.OnClick(this,getPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            onClisterItem.OnLongClick(this,getPosition());
            return false;
        }

        public myViewHolderClass(View itemView, OnClicktitem onClisterItem) {
            super(itemView);
            this.onClisterItem=onClisterItem;
            itemView.setOnClickListener(this);
            name_name=itemView.findViewById(R.id.name_name);
            nameed_name=itemView.findViewById(R.id.nameed_name);
            textmsg=itemView.findViewById(R.id.textmesg);
            isadleb=itemView.findViewById(R.id.isadlesee);
        }
    }
}
