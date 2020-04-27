package com.cd.myluntan.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.cd.myluntan.FragmentFactory;
import com.cd.myluntan.R;
import com.cd.myluntan.data_connection.Data_Access;
import com.cd.myluntan.entrty.Comment;
import com.cd.myluntan.entrty.Dynamic;
import com.cd.myluntan.entrty.Praise;
import com.cd.myluntan.entrty.User;
import com.cd.myluntan.ui.fragment.Commit_Fragment;
import com.cd.myluntan.utils.Singletion;
import com.cd.myluntan.utils.TimeUitl;
import com.cd.myluntan.utils.ToolAnimation;
import com.cd.myluntan.utils.WindowUitls;
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

public class PlayMp4_Activity extends AppCompatActivity {
   Dynamic dynamic;
   View viewtop;
   VideoView videoView;
    User myUser;
    int Index;
    ArrayList<Dynamic> dynamics;
   ImageView back,share,head,praise,collect,commm,errrer,playSHiping;
   TextView toptitile,name,follor,msg,praiseNum,collectNum,commmNum,commitNum;
    Commit_Fragment commityframe;
   RelativeLayout befusd,mainlayuo;
    private EditText text_mssg;
    private  TextView send;
    final Handler handler = new Handler();
//    Runnable runnable = new Runnable() {
//        public void run() {
//            int duration = videoView.getCurrentPosition();
//            if(videoView.isPlaying()){
//                if (old_duration == duration) {
//                    mProgressBar.setVisibility(View.VISIBLE);
//                } else {
//                    mProgressBar.setVisibility(View.GONE);
//                }
//            }
//            old_duration = duration;
//            handler.postDelayed(runnable, 500);
//        }
//    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_mp4_);
        dynamics= Singletion.getInstance().getDynamics();
        Index=Singletion.getInstance().getIndex();
        dynamic=dynamics.get(Index);
        myUser= Singletion.getInstance().getUser();
        WindowUitls.setColorTopBar(this,R.color.white);
        WindowUitls.setColorTextTopBarBlack(this);
        commityframe=new Commit_Fragment(dynamic.getComments(),text_mssg,commitNum);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.commityframe, commityframe, "f1")        //.addToBackStack("fname")
                .commit();
        initView();
        initShow();
        playDivio(dynamic.getImgs().get(0).getImgurl());
    }

    private void playDivio(String imgurl) {
      String url= URL + PLAYMPO+"?name=" +imgurl;
      videoView.setVideoURI(Uri.parse(url));
      videoView.start();
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
        {
            @Override
            public void onCompletion(MediaPlayer mp)
            {
                playSHiping.setVisibility(View.VISIBLE);
            }
        });
        videoView.setOnTouchListener(new View.OnTouchListener() {
            float x,y;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        x=event.getX();
                        y=event.getY();
                        break;
                     case MotionEvent.ACTION_UP:
                        float x2=event.getX();
                        float y2=event.getY();
                        //上滑
                        if(y-y2>=40){
                           if(dynamics.size()-1>Index){
                               Index++;
                               videoView.stopPlayback();
                               playSHiping.setVisibility(View.GONE);
                               dynamic=dynamics.get(Index);
                               commityframe.setDataAndFinal(dynamic.getComments());
                               initShow();
                               playDivio(dynamics.get(Index).getImgs().get(0).getImgurl());
                           }else {
                               Toast.makeText(PlayMp4_Activity.this,"没有更多",Toast.LENGTH_LONG).show();
                           }
                            //下滑
                        }else  if(y2-y>=40){
                            if(0<Index){
                                Index--;
                                videoView.stopPlayback();
                                playSHiping.setVisibility(View.GONE);
                                dynamic=dynamics.get(Index);
                                initShow();
                                commityframe.setDataAndFinal(dynamic.getComments());
                                ToolAnimation.translationShowViewY(mainlayuo);
                                playDivio(dynamics.get(Index).getImgs().get(0).getImgurl());
                            } else {
                                Toast.makeText(PlayMp4_Activity.this,"没有更多",Toast.LENGTH_LONG).show();
                            }
                        }
                        //点击
                        if(Math.abs(x-x2)<10&&Math.abs(y2-y)<10){
                            if(videoView.isPlaying()){
                                playSHiping.setVisibility(View.VISIBLE);
                                videoView.pause();
                            }
                        }
                                break;
                }
                return true;
            }
        });
        playSHiping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSHiping.setVisibility(View.GONE);
                videoView.start();
            }
        });
    }
    @Override
    protected void onDestroy() {
//        handler.removeCallbacks(runnable);
        super.onDestroy();
    }
    private void initShow() {
        User user=dynamic.getUser();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //分享
            }
        });
        if(dynamic.getComments().size()>0) {
            commmNum.setText(dynamic.getComments().size()+"");
            commitNum.setText("总共"+dynamic.getComments().size()+"条评论");
        }else {
            commmNum.setText("评论");
            commitNum.setText("还没有评论哦");
        }
        //点击评论
        commm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { befusd.setVisibility(View.VISIBLE);
                ToolAnimation.translationShowViewY(befusd);

            }
        });
        //取消
        errrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToolAnimation.translationHideViewY(befusd);befusd.setVisibility(View.GONE);
            }
        });
        if(dynamic.getPraises().size()>0) {
            praiseNum.setText(dynamic.getPraises().size()+"");
        }else {
            praiseNum.setText("点赞");
        }
//        收藏
//        if(dynamic.().size()>0) {
//            commmNum.setText(dynamic.getComments().size());
//        }else {
//            commmNum.setText("评论");
//        }
        //判断是否关注
        if(isFollow(user)) {
            follor.setText("已关注");
            follor.setTextColor(getResources().getColor(R.color.content_grey));
        }else {
            follor.setText("关注");
            follor.setTextColor(getResources().getColor(R.color.colorPrimary));
        }
        if(user.getHeadurl().length()<24){
            Glide.with(this).load(URL + SHOWIMGS+"?name=" +user.getHeadurl()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(head);
        }else {
            Glide.with(this).load(user.getHeadurl()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(head);
        }
        head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Singletion.getInstance().setOtherUser(dynamic.getUser());
                startActivity(new Intent(PlayMp4_Activity.this,PersonalActivity.class));
            }
        });
        name.setText(user.getNick()!=null?user.getNick():user.getName());
        msg.setText(dynamic.getMag());
        praise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPraise(dynamic.getPraises())) {
                    rmPraise(dynamic);
                   praise.setImageResource(R.drawable.lave_normal);
                }else {
                    addPraise(dynamic);
                    praise.setImageResource(R.drawable.lave_selected);
                }
            }
        });


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputtext=text_mssg.getText().toString().trim();
                if(!inputtext.equals("")){
                    if(!ISCOMMITBACK) {
                        sendCommit(inputtext);
                    }else {
                        sendCommit(inputtext,publiccomment,publick);
                    }
                }else {
                    Toast.makeText(PlayMp4_Activity.this,"输入",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    private void sendCommit(String inputtext) {
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
        commityframe.setDataAndFinal(dynamic.getComments());
        text_mssg.setText("");
        Data_Access.AccessJSONDate(URL+ADDCOMMENT,new Gson().toJson(comment),null);
        commitNum.setText("总共"+dynamic.getComments().size()+"条评论");
      //  initShow();
        // Contract_input();
    }

    private void sendCommit(String inputtext, Comment comments2, int k) {
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
        commityframe.setDataAndFinal(dynamic.getComments());
        text_mssg.setText("");
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
    private void rmPraise(Dynamic dynamic) {
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
       initShow();
    }

    private void addPraise(Dynamic dynamic) {
        Praise praise=new Praise();
        praise.setUser(myUser);
        praise.setDynamicid(dynamic.getId());
        praise.setId(System.currentTimeMillis()+"");
        praise.setUserid(myUser.getId());
        // dynamic.getPraises().add(0,praise);
        // Singletion.getInstance().setDynamic(dynamic);
        Data_Access.AccessJSONDate(URL+ADDPRAISE,new Gson().toJson(praise),null);
        dynamic.getPraises().add(0,praise);
        initShow();
    }
    private void initView() {
       back=findViewById(R.id.backimg);
       back.setImageResource(R.drawable.backwrite);
       share=findViewById(R.id.shareimg);
       share.setVisibility(View.VISIBLE);
       toptitile=findViewById(R.id.toptitle);
       toptitile.setVisibility(View.GONE);
       viewtop=findViewById(R.id.top);
       viewtop.setBackgroundColor(0x00000000);
        name=findViewById(R.id.name);
        follor=findViewById(R.id.follor);
        msg=findViewById(R.id.msg);
        head=findViewById(R.id.head);
        praise=findViewById(R.id.praise);
        collect=findViewById(R.id.collect);
        commm=findViewById(R.id.commm);
        praiseNum=findViewById(R.id.praiseNum);
        collectNum=findViewById(R.id.collectNum);
        commmNum=findViewById(R.id.commmNum2);
        errrer=findViewById(R.id.errrer);
        commitNum=findViewById(R.id.commitNum);
        befusd=findViewById(R.id.befusd);
        befusd.setVisibility(View.GONE);
        text_mssg=findViewById(R.id.input_text_msg);
        send=findViewById(R.id.send);
        videoView=findViewById(R.id.videoView);
        playSHiping=findViewById(R.id.playSHiping);
        mainlayuo=findViewById(R.id.mainlayuo);
    }
}
