package com.cd.myluntan.ui.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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
import com.cd.myluntan.adapter.Play_mp4_Adapter;
import com.cd.myluntan.data_connection.Data_Access;
import com.cd.myluntan.entrty.Comment;
import com.cd.myluntan.entrty.Dynamic;
import com.cd.myluntan.entrty.Praise;
import com.cd.myluntan.entrty.User;
import com.cd.myluntan.interfaceo.BottomUpdateCallback;
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
    int Index;
    ArrayList<Dynamic> dynamics;
   ImageView back,share;
   TextView toptitile;
      private RecyclerView play_recycle;
    private int currentPosition;
    PagerSnapHelper snapHelper;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_mp4_);
        dynamics= Singletion.getInstance().getDynamics();
        currentPosition=Singletion.getInstance().getIndex();
        dynamic=dynamics.get(currentPosition);
        Dynamic dynamic3=dynamics.get(0);
        dynamics.set(0,dynamic);
        dynamics.set(currentPosition,dynamic3);
        WindowUitls.setColorTopBar(this,R.color.white);
        WindowUitls.setColorTextTopBarBlack(this);
        initView();
        initShow();
    }


    @Override
    protected void onDestroy() {
//        handler.removeCallbacks(runnable);
        super.onDestroy();
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initShow() {
        Play_mp4_Adapter play_mp4_adapter=new Play_mp4_Adapter(this, dynamics, Index, new BottomUpdateCallback() {
            @Override
            public void bottomBarShow(int dy) {
                Log.e("fdyf",dy+"");
                if(dy==0){
                    play_recycle.setNestedScrollingEnabled(false);
                }else {
                    play_recycle.setNestedScrollingEnabled(true);
                }
            }
        });
        snapHelper=  new PagerSnapHelper();
        snapHelper.attachToRecyclerView(play_recycle);
         play_recycle.setAdapter(play_mp4_adapter);
         play_recycle.setLayoutManager(new LinearLayoutManager(this));
       //  play_recycle.smoothScrollToPosition(Index);
        RecyclerView.LayoutManager layoutManager = play_recycle.getLayoutManager();
         play_recycle.addOnScrollListener(new RecyclerView.OnScrollListener() {
             @Override
             public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                 super.onScrollStateChanged(recyclerView, newState);
                 switch (newState) {
                     case RecyclerView.SCROLL_STATE_IDLE://停止滚动
                         View view = snapHelper.findSnapView(layoutManager);
                         //当前固定后的item position
                         int position = play_recycle.getChildAdapterPosition(view);
                         if (currentPosition != position) {
                             play_recycle.scrollToPosition(position);
//                             RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(view);
//                             if (viewHolder != null && viewHolder instanceof VideoViewHolder) {
//                                 ((VideoViewHolder) viewHolder).mp_video.startVideo();
//                             }
                         }
                         currentPosition = position;

                         break;
                     case RecyclerView.SCROLL_STATE_DRAGGING://拖动
                         break;
                     case RecyclerView.SCROLL_STATE_SETTLING://惯性滑动
                         break;
                 }
             }

             @Override
             public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                 super.onScrolled(recyclerView, dx, dy);
             }
         });
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
        play_recycle=findViewById(R.id.paymp4_reycycle);
    }
}
