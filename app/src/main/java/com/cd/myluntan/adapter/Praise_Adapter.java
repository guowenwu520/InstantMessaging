package com.cd.myluntan.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.cd.myluntan.R;
import com.cd.myluntan.entrty.Dynamic;
import com.cd.myluntan.entrty.Praise;
import com.cd.myluntan.entrty.User;
import com.cd.myluntan.ui.activity.Dynamic_Details_Activity;
import com.cd.myluntan.utils.Singletion;

import java.util.ArrayList;

public class Praise_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<Praise> praises=new ArrayList<>();
    User myUser;
    boolean fign=true;
    public Praise_Adapter(Context context, ArrayList<Praise> praises) {
        this.praises=praises;
        this.context=context;
        myUser= Singletion.getInstance().getUser();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.praise_item_view,parent,false);
        return new myViewHolderClass(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final Praise praise=praises.get(position);
        final myViewHolderClass myViewHolderClass= (myViewHolderClass) holder;
        updateList(myViewHolderClass,praise);
    }

    @Override
    public int getItemCount() {
        return praises.size();
    }

    public void flishData(ArrayList<Praise> praises) {
        this.praises=praises;
        notifyDataSetChanged();
    }

    class  myViewHolderClass extends RecyclerView.ViewHolder{

        ImageView imghead;
        TextView name,follow,time,fans;

        public myViewHolderClass(View itemView) {
            super(itemView);
            follow=itemView.findViewById(R.id.follow);
            fans=itemView.findViewById(R.id.fans);
          imghead=itemView.findViewById(R.id.head);
            name=itemView.findViewById(R.id.name);
            time=itemView.findViewById(R.id.time);
        }
    }

    private void updateList(myViewHolderClass myViewHolderClass, final Praise praise) {
        //用户消息
        User user=praise.getUser();
        myViewHolderClass.name.setText(user.getName());
        myViewHolderClass.time.setText(user.getSignaturnre());
        Glide.with(context).load(user.getHead_url()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(myViewHolderClass.imghead);
        //判断是否关注
        if(isFollow(user)) {
            myViewHolderClass.follow.setText("已关注");
            myViewHolderClass.follow.setTextColor(context.getResources().getColor(R.color.content_grey));
            myViewHolderClass.follow.setBackgroundResource(R.drawable.is_follow_button_bg);
        }else {
            myViewHolderClass.follow.setText("关注");
            myViewHolderClass.follow.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            myViewHolderClass.follow.setBackgroundResource(R.drawable.no_follow_button_bg);
        }
        //点击关注
        myViewHolderClass.follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(!isFollow(myUser)){
                   myViewHolderClass.follow.setText("已关注");
                   myViewHolderClass.follow.setTextColor(context.getResources().getColor(R.color.content_grey));
                   myViewHolderClass.follow.setBackgroundResource(R.drawable.is_follow_button_bg);
               }else {
                   myViewHolderClass.follow.setText("关注");
                   myViewHolderClass.follow.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                   myViewHolderClass.follow.setBackgroundResource(R.drawable.no_follow_button_bg);

               }
            }
        });
        //点击头像
        myViewHolderClass. imghead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"点击了头像",Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean isFollow(User user) {
        //关注逻辑
        fign=!fign;
        return fign;
    }
    
}
