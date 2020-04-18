package com.cd.myluntan.adapter;

import android.content.Context;
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
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.cd.myluntan.R;
import com.cd.myluntan.entrty.Dynamic;
import com.cd.myluntan.entrty.Praise;
import com.cd.myluntan.entrty.User;
import com.cd.myluntan.utils.Singletion;

import java.util.ArrayList;

public class Dynamic_show_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
   boolean[] fing;
    boolean[] fign;
    Context context;
    private String NOKJIAN="0";
    ArrayList<Dynamic> dynamics=new ArrayList<>();
    User myUser;

    public Dynamic_show_Adapter(Context context, ArrayList<Dynamic> dynamics) {
        this.dynamics=dynamics;
        this.context=context;
        myUser= Singletion.getInstance().getUser();
        fign=new boolean[dynamics.size()+1];
        fing=new boolean[dynamics.size()+1];
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.dynamic_item_view,parent,false);
        return new myViewHolderClass(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final Dynamic dynamic=dynamics.get(position);
        final myViewHolderClass myViewHolderClass= (Dynamic_show_Adapter.myViewHolderClass) holder;
        updateList(myViewHolderClass,dynamic);
    }

    @Override
    public int getItemCount() {
        return dynamics.size();
    }

    class  myViewHolderClass extends RecyclerView.ViewHolder{
        ImageView imghead,praise,more;
        TextView commitNum,praisenNum,name,time,content,follow,fans;
        LinearLayout rl_share,rl_commit;
        RecyclerView img_recycleview,label_recycle;

        public myViewHolderClass(View itemView) {
            super(itemView);
            more=itemView.findViewById(R.id.more);
            praise=itemView.findViewById(R.id.praise);
            commitNum=itemView.findViewById(R.id.commitNum);
            praisenNum=itemView.findViewById(R.id.praiseNum);
            content=itemView.findViewById(R.id.content);
            follow=itemView.findViewById(R.id.follow);
            fans=itemView.findViewById(R.id.fans);
            rl_commit=itemView.findViewById(R.id.commit);
            rl_share=itemView.findViewById(R.id.share);
            img_recycleview=itemView.findViewById(R.id.img_recyclerView);
            label_recycle=itemView.findViewById(R.id.label_recycle);
            imghead=itemView.findViewById(R.id.head);
            name=itemView.findViewById(R.id.name);
            time=itemView.findViewById(R.id.time);
        }
    }

    private void updateList(final myViewHolderClass myViewHolderClass, final Dynamic dynamic) {
        //用户消息
        User user=dynamic.getUser();
        myViewHolderClass.name.setText(user.getName());
        Glide.with(context).load(user.getHead_url()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(myViewHolderClass.imghead);
        //评论
        myViewHolderClass.time.setText(dynamic.getTime());
        myViewHolderClass.content.setText(dynamic.getMag());
        myViewHolderClass.praisenNum.setText(dynamic.getPraises().size()+"");
        myViewHolderClass.commitNum.setText(dynamic.getComments().size()+"");
        //判断是否点过赞
        if(isPraise(dynamic.getPraises())) {
            myViewHolderClass.praise.setImageResource(R.drawable.praise_checked);
        }else {
            myViewHolderClass.praise.setImageResource(R.drawable.praise_normal);
        }
        //判断是否关注
        if(isFollow(user)) {
            myViewHolderClass.follow.setText("已关注");
            myViewHolderClass.follow.setTextColor(context.getResources().getColor(R.color.content_grey));
        }else {
            myViewHolderClass.follow.setText("关注");
            myViewHolderClass.follow.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        }
        //点击关注
        myViewHolderClass.follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"点击了关注",Toast.LENGTH_LONG).show();
            }
        });
        //点赞
        myViewHolderClass.praise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPraise(dynamic.getPraises())) {
                    myViewHolderClass.praise.setImageResource(R.drawable.praise_normal);
                }else {
                    myViewHolderClass.praise.setImageResource(R.drawable.praise_checked);
                }
            }
        });
        //点击评论
        myViewHolderClass.rl_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"点击了评论",Toast.LENGTH_LONG).show();
            }
        });
        //点击更多
        myViewHolderClass.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"点击了更多",Toast.LENGTH_LONG).show();
            }
        });
        //点击分享
        myViewHolderClass.rl_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"点击了分享",Toast.LENGTH_LONG).show();
            }
        });
        //点击内容
        myViewHolderClass.content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"点击了内容",Toast.LENGTH_LONG).show();
            }
        });
        //点击头像
        myViewHolderClass.imghead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"点击了头像",Toast.LENGTH_LONG).show();
            }
        });
        //图片布局
        Photograph_Adapater photograph_adapater=new Photograph_Adapater(dynamic.getImgs(),context);
        myViewHolderClass.img_recycleview.setAdapter(photograph_adapater);
        myViewHolderClass.img_recycleview.setLayoutManager(new GridLayoutManager(context,3));
        //标签布局
//        Label_Adapter label_adapter=new Label_Adapter(dynamic.getLabels(),context);
//        myViewHolderClass.label_recycle.setAdapter(photograph_adapater);
//        myViewHolderClass.label_recycle.setLayoutManager(new GridLayoutManager(context,3));
    }

    private boolean isFollow(User user) {
        //关注逻辑
        return false;
    }

    private boolean isPraise(ArrayList<Praise> praises) {
        for (int i=0;i<praises.size();i++){
            if(praises.get(i).getId().equals(myUser.getId())){
                return true;
            }
        }
        return false;
    }

}