package com.cd.myluntan.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.cd.myluntan.R;
import com.cd.myluntan.entrty.Comment;
import com.cd.myluntan.entrty.Praise;
import com.cd.myluntan.entrty.User;
import com.cd.myluntan.interfaceo.OnClicktitem;
import com.cd.myluntan.utils.Singletion;

import java.util.ArrayList;

import static com.cd.myluntan.data_connection.Global_Url_Parameters.ISCOMMIT;
import static com.cd.myluntan.data_connection.Global_Url_Parameters.NOCOMMIT;

public class Commit_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    OnClicktitem onClisterItem;
    ArrayList<Comment> comments=new ArrayList<>();
   Context context;
    boolean fign=true;
    User myUser;

    public Commit_Adapter(Context context, ArrayList<Comment> comments) {
        this.context=context;
           this.comments=comments;
        myUser= Singletion.getInstance().getUser();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.commit_view_item,parent,false);
        return new myViewHolderClass(view,onClisterItem);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
           Comment comment=comments.get(position);
           myViewHolderClass myViewHolderClass= (Commit_Adapter.myViewHolderClass) holder;
        updateList(myViewHolderClass,comment);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public void setOnClisterItem(OnClicktitem onClisterItem) {
        this.onClisterItem = onClisterItem;
    }

    public void setDataAndFinal(ArrayList<Comment> comments) {
        this.comments=comments;
        notifyDataSetChanged();
    }

    class  myViewHolderClass extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
        OnClicktitem onClisterItem;
        ImageView imghead;
        TextView name,follow,time,fans,conternt;
        RelativeLayout back_commit_rl;
        TextView commit_name,commit_nr,commit_more;
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
            itemView.setOnLongClickListener(this);
            follow=itemView.findViewById(R.id.follow);
            conternt=itemView.findViewById(R.id.conternt);
            back_commit_rl=itemView.findViewById(R.id.back_commit_rl);
            commit_more=itemView.findViewById(R.id.commit_more);
            commit_name=itemView.findViewById(R.id.commit_name);
            commit_nr=itemView.findViewById(R.id.commit_nr);
            fans=itemView.findViewById(R.id.fans);
            imghead=itemView.findViewById(R.id.head);
            name=itemView.findViewById(R.id.name);
            time=itemView.findViewById(R.id.time);
        }
    }
    private void updateList(myViewHolderClass myViewHolderClass, final Comment comment) {
        //用户消息
        User user=comment.getUsered();
        myViewHolderClass.name.setText(user.getName());
        myViewHolderClass.time.setText(comment.getCommit_time());
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
        //评论内容
        myViewHolderClass.conternt.setText(comment.getCommit_mag());
        //是否有回复
        if(comment.getType().equals(NOCOMMIT)){
            myViewHolderClass.back_commit_rl.setVisibility(View.GONE);
        }else if(comment.getType().equals(ISCOMMIT)){
            myViewHolderClass.back_commit_rl.setVisibility(View.VISIBLE);
            ArrayList<Comment> comments=comment.getComments();
            myViewHolderClass.commit_name.setText(comments.get(comments.size()-1).getUsered().getName());
            myViewHolderClass.commit_nr.setText(comments.get(comments.size()-1).getCommit_mag());
            myViewHolderClass.commit_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,"点击栏码字",Toast.LENGTH_LONG).show();
                }
            });
            if(comments.size()<=1){
                myViewHolderClass.commit_more.setVisibility(View.GONE);
            }else {
                myViewHolderClass.commit_more.setVisibility(View.VISIBLE);
                myViewHolderClass.commit_more.setText("查看" + comments.size() + "条评论");
                myViewHolderClass.commit_more.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context,"显示更多评论",Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
    }
    private boolean isFollow(User user) {
        //关注逻辑
        fign=!fign;
        return fign;
    }
}
