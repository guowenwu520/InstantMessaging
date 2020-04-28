package com.cd.myluntan.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.cd.myluntan.R;
import com.cd.myluntan.data_connection.Data_Access;
import com.cd.myluntan.entrty.Comment;
import com.cd.myluntan.entrty.Dynamic;
import com.cd.myluntan.entrty.Praise;
import com.cd.myluntan.entrty.User;
import com.cd.myluntan.interfaceo.BottomUpdateCallback;
import com.cd.myluntan.ui.activity.PersonalActivity;
import com.cd.myluntan.ui.activity.PlayMp4_Activity;
import com.cd.myluntan.ui.fragment.Commit_Fragment;
import com.cd.myluntan.utils.Singletion;
import com.cd.myluntan.utils.TimeUitl;
import com.cd.myluntan.utils.ToolAnimation;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.cd.myluntan.data_connection.Global_Url_Parameters.ADDCOMMENT;
import static com.cd.myluntan.data_connection.Global_Url_Parameters.ADDPRAISE;
import static com.cd.myluntan.data_connection.Global_Url_Parameters.DELETEPRAISE;
import static com.cd.myluntan.data_connection.Global_Url_Parameters.ISCOMMIT;
import static com.cd.myluntan.data_connection.Global_Url_Parameters.NOCOMMIT;
import static com.cd.myluntan.data_connection.Global_Url_Parameters.PLAYMPO;
import static com.cd.myluntan.data_connection.Global_Url_Parameters.SHOWIMGS;
import static com.cd.myluntan.data_connection.Global_Url_Parameters.URL;
import static com.cd.myluntan.ui.fragment.Commit_Fragment.ISCOMMITBACK;
import static com.cd.myluntan.ui.fragment.Commit_Fragment.publiccomment;
import static com.cd.myluntan.ui.fragment.Commit_Fragment.publick;

public class Play_mp4_Adapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    PlayMp4_Activity context;
    ArrayList<Dynamic> dynamics;
    User myUser;
    BottomUpdateCallback bottomUpdateCallback;
    int index;
    public Play_mp4_Adapter(PlayMp4_Activity playMp4_activity, ArrayList<Dynamic> dynamics, int index,BottomUpdateCallback bottomUpdateCallback) {
        context=playMp4_activity;
        myUser= Singletion.getInstance().getUser();
        this.dynamics=dynamics;
       this.index= index;
       this.bottomUpdateCallback=bottomUpdateCallback;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.play_mp4_item_view,parent,false);
        return new myViewHolderClasPlay(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Dynamic dynamic=dynamics.get(position);
        myViewHolderClasPlay myViewHolderClasPlay= (Play_mp4_Adapter.myViewHolderClasPlay) holder;
      Commit_Fragment  commit_fragment=new Commit_Fragment(dynamic.getComments(),myViewHolderClasPlay.text_mssg,myViewHolderClasPlay.commitNum);
        context.getSupportFragmentManager().beginTransaction()
                .add(R.id.commityframe, commit_fragment, "f1")        //.addToBackStack("fname")
                .commit();
     //  myViewHolderClasPlay.commit_fragment.setDataAndFinalAndView();
        playDivio(dynamic.getImgs().get(0).getImgurl(),myViewHolderClasPlay,position);
            showlist(dynamic,myViewHolderClasPlay);
    }

    private void showlist(Dynamic dynamic, myViewHolderClasPlay myViewHolderClasPlay) {
        User user=dynamic.getUser();
        if(dynamic.getComments().size()>0) {
            myViewHolderClasPlay.commmNum.setText(dynamic.getComments().size()+"");
            myViewHolderClasPlay. commitNum.setText("总共"+dynamic.getComments().size()+"条评论");
        }else {
            myViewHolderClasPlay. commmNum.setText("评论");
            myViewHolderClasPlay. commitNum.setText("还没有评论哦");
        }
        if(!isPraise(dynamic.getPraises())) {
             myViewHolderClasPlay.praise.setImageResource(R.drawable.lave_normal);
        }else {
            myViewHolderClasPlay.praise.setImageResource(R.drawable.lave_selected);
        }
        //点击评论
        myViewHolderClasPlay. commm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myViewHolderClasPlay.befusd.setVisibility(View.VISIBLE);
                bottomUpdateCallback.bottomBarShow(0);
               // ToolAnimation.translationShowViewY(myViewHolderClasPlay.befusd);

            }
        });
        //取消
        myViewHolderClasPlay.errrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomUpdateCallback.bottomBarShow(1);
                myViewHolderClasPlay.befusd.setVisibility(View.GONE);
            }
        });
        if(dynamic.getPraises().size()>0) {
            myViewHolderClasPlay.praiseNum.setText(dynamic.getPraises().size()+"");
        }else {
            myViewHolderClasPlay.praiseNum.setText("点赞");
        }
//        收藏
//        if(dynamic.().size()>0) {
//            commmNum.setText(dynamic.getComments().size());
//        }else {
//            commmNum.setText("评论");
//        }
        //判断是否关注
        if(isFollow(user)) {
            myViewHolderClasPlay.follor.setText("已关注");
            myViewHolderClasPlay.follor.setTextColor(context.getResources().getColor(R.color.content_grey));
        }else {
            myViewHolderClasPlay. follor.setText("关注");
            myViewHolderClasPlay. follor.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        }
        if(user.getHeadurl().length()<24){
            Glide.with(context).load(URL + SHOWIMGS+"?name=" +user.getHeadurl()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(myViewHolderClasPlay.head);
        }else {
            Glide.with(context).load(user.getHeadurl()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(myViewHolderClasPlay.head);
        }
        myViewHolderClasPlay.head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Singletion.getInstance().setOtherUser(dynamic.getUser());
                context.startActivity(new Intent(context, PersonalActivity.class));
            }
        });
        myViewHolderClasPlay.name.setText(user.getNick()!=null?user.getNick():user.getName());
        myViewHolderClasPlay. msg.setText(dynamic.getMag());
        myViewHolderClasPlay.praise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPraise(dynamic.getPraises())) {
                    rmPraise(dynamic,myViewHolderClasPlay);
                    myViewHolderClasPlay.praise.setImageResource(R.drawable.lave_normal);
                }else {
                    addPraise(dynamic,myViewHolderClasPlay);
                    myViewHolderClasPlay.praise.setImageResource(R.drawable.lave_selected);
                }
            }
        });


        myViewHolderClasPlay. send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputtext=myViewHolderClasPlay.text_mssg.getText().toString().trim();
                if(!inputtext.equals("")){
                    if(!ISCOMMITBACK) {
                        sendCommit(inputtext,dynamic,myViewHolderClasPlay);
                    }else {
                        sendCommit(inputtext,publiccomment,publick,dynamic,myViewHolderClasPlay);
                    }
                }else {
                    Toast.makeText(context,"输入",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    private void sendCommit(String inputtext, Dynamic dynamic,myViewHolderClasPlay myViewHolderClasPlay) {
        Comment comment=new Comment();
        comment.setUsered(myUser);
        comment.setUser(dynamic.getUser());
        comment.setUseredid(myUser.getId());
        comment.setUserid(dynamic.getUser().getId());
        comment.setType(NOCOMMIT);
        comment.setCommittime(TimeUitl.DataToString(new Date()));
        comment.setDynamicid(dynamic.getId());
        comment.setCommitmag(inputtext);
        comment.setId(System.currentTimeMillis()+"");
        dynamic.getComments().add(0,comment);
     //   myViewHolderClasPlay.commit_fragment.setDataAndFinal(dynamic.getComments());
        myViewHolderClasPlay.text_mssg.setText("");
        Data_Access.AccessJSONDate(URL+ADDCOMMENT,new Gson().toJson(comment),null);
        myViewHolderClasPlay. commitNum.setText("总共"+dynamic.getComments().size()+"条评论");
        //  initShow();
        // Contract_input();
    }

    private void sendCommit(String inputtext, Comment comments2, int k, Dynamic dynamic,myViewHolderClasPlay myViewHolderClasPlay) {
        Comment comment=new Comment();
        comment.setUsered(Singletion.getInstance().getUser());
        comment.setUser(comments2.getUsered());
        comment.setType(ISCOMMIT);
        comment.setCommittime("2323:34");
        comment.setDynamicid(comments2.getDynamicid());
        comment.setCommitmag(inputtext);
        comment.setId("23232");
        comments2.setType(ISCOMMIT);
        //如果还没有回复则直接添加
        if(comments2.getComments()==null){
            ArrayList<Comment> comments=new ArrayList<>();
            comments.add(comment);
            comments2.setComments(comments);
        }else {
            comments2.getComments().add(0, comment);
        }
        dynamic.getComments().set(k,comments2);
    //    myViewHolderClasPlay.commit_fragment.setDataAndFinal(dynamic.getComments());
        myViewHolderClasPlay.text_mssg.setText("");
        //  updateList();
        // Contract_input();
    }
    private boolean isFollow(User user) {
        return false;
    }
    private boolean isPraise(ArrayList<Praise> praises) {
        for (int i=0;i<praises.size();i++){
            if(praises.get(i).getUser().getId().equals(myUser.getId())){
                return true;
            }
        }
        return false;
    }
    private void rmPraise(Dynamic dynamic, myViewHolderClasPlay myViewHolderClasPlay) {
        ArrayList<Praise> praises=dynamic.getPraises();
        for (int i=0;i<praises.size();i++){
            if(myUser.getId().equals(praises.get(i).getUser().getId())){
                Map<String,String> map=new HashMap<>();
                map.put("id",praises.get(i).getId());
                Data_Access.AccessStringDate(URL+DELETEPRAISE,map,null);
                praises.remove(i);
                break;
            }
        }
        //dynamic.setPraises(praises);
        // Singletion.getInstance().setDynamic(dynamic);
        dynamic.setPraises(praises);
        if(dynamic.getPraises().size()>0) {
            myViewHolderClasPlay.praiseNum.setText(dynamic.getPraises().size()+"");
        }else {
            myViewHolderClasPlay.praiseNum.setText("点赞");
        }
    }

    private void addPraise(Dynamic dynamic, myViewHolderClasPlay myViewHolderClasPlay) {
        Praise praise=new Praise();
        praise.setUser(myUser);
        praise.setDynamicid(dynamic.getId());
        praise.setId(System.currentTimeMillis()+"");
        praise.setUserid(myUser.getId());
        // dynamic.getPraises().add(0,praise);
        // Singletion.getInstance().setDynamic(dynamic);
        Data_Access.AccessJSONDate(URL+ADDPRAISE,new Gson().toJson(praise),null);
        dynamic.getPraises().add(0,praise);
        if(dynamic.getPraises().size()>0) {
            myViewHolderClasPlay.praiseNum.setText(dynamic.getPraises().size()+"");
        }else {
            myViewHolderClasPlay.praiseNum.setText("点赞");
        }
    }
    @Override
    public int getItemCount() {
        return dynamics.size();
    }

    private class myViewHolderClasPlay extends RecyclerView.ViewHolder {

        ImageView head,praise,collect,commm,errrer,playSHiping;
     //    Commit_Fragment commit_fragment;
        TextView name,follor,msg,praiseNum,collectNum,commmNum,commitNum;
         RelativeLayout befusd,mainlayuo;
        VideoView videoView;
        private EditText text_mssg;
        private  TextView send;
        public myViewHolderClasPlay(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            follor=itemView.findViewById(R.id.follor);
            msg=itemView.findViewById(R.id.msg);
            head=itemView.findViewById(R.id.head);
            praise=itemView.findViewById(R.id.praise);
            collect=itemView.findViewById(R.id.collect);
            commm=itemView.findViewById(R.id.commm);
            praiseNum=itemView.findViewById(R.id.praiseNum);
            collectNum=itemView.findViewById(R.id.collectNum);
            commmNum=itemView.findViewById(R.id.commmNum2);
            errrer=itemView.findViewById(R.id.errrer);
            commitNum=itemView.findViewById(R.id.commitNum);
            befusd=itemView.findViewById(R.id.befusd);
            befusd.setVisibility(View.GONE);
            text_mssg=itemView.findViewById(R.id.input_text_msg);
            send=itemView.findViewById(R.id.send);
            videoView=itemView.findViewById(R.id.videoView);
            playSHiping=itemView.findViewById(R.id.playSHiping);
            mainlayuo=itemView.findViewById(R.id.mainlayuo);
            ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;

        }
    }
    private void playDivio(String imgurl,myViewHolderClasPlay myViewHolderClasPlay,int postion) {
        String url= URL + PLAYMPO+"?name=" +imgurl;
        if(postion==index) {
            myViewHolderClasPlay.videoView.stopPlayback();
            myViewHolderClasPlay.videoView.setVideoURI(Uri.parse(url));
            myViewHolderClasPlay.videoView.start();
        }else {
            myViewHolderClasPlay.videoView.stopPlayback();
            myViewHolderClasPlay.videoView.setVideoURI(Uri.parse(url));
        }
        myViewHolderClasPlay.videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
        {
            @Override
            public void onCompletion(MediaPlayer mp)
            {
                myViewHolderClasPlay.playSHiping.setVisibility(View.VISIBLE);
            }
        });
        myViewHolderClasPlay.videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myViewHolderClasPlay.videoView.isPlaying()){
                    myViewHolderClasPlay.playSHiping.setVisibility(View.VISIBLE);
                    myViewHolderClasPlay.videoView.pause();
                }
            }
        });
        myViewHolderClasPlay.playSHiping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myViewHolderClasPlay.playSHiping.setVisibility(View.GONE);
                myViewHolderClasPlay.videoView.start();
            }
        });
        index=postion;
    }
    public static void stopDivio(String imgurl,myViewHolderClasPlay myViewHolderClasPlay,int postion) {

    }
}
