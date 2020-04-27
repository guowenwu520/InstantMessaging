package com.cd.myluntan.ui.widget;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.cd.myluntan.R;
import com.cd.myluntan.data_connection.Data_Access;
import com.cd.myluntan.entrty.Dynamic;
import com.cd.myluntan.entrty.Home;
import com.cd.myluntan.entrty.Imgs;
import com.cd.myluntan.entrty.Praise;
import com.cd.myluntan.entrty.User;
import com.cd.myluntan.interfaceo.BottomUpdateCallback;
import com.cd.myluntan.interfaceo.OnClicktitem;
import com.cd.myluntan.ui.activity.PersonalActivity;
import com.cd.myluntan.utils.Singletion;

import java.util.ArrayList;

import static com.cd.myluntan.data_connection.Global_Url_Parameters.PLAYMPO;
import static com.cd.myluntan.data_connection.Global_Url_Parameters.SHOWIMGS;
import static com.cd.myluntan.data_connection.Global_Url_Parameters.URL;

public class HomeItemView extends RelativeLayout {
    private Context context;
    private ImageView videoThumbnail, avatarImg, like;
    private TextView title, avatar, likeNum;
   private User myUser;
   private  Dynamic dynamic;
   private  int position;
    public HomeItemView(Context context) {
        this(context, null);
    }

    public HomeItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        myUser= Singletion.getInstance().getUser();
        initView();
    }

    private void initView() {
        LayoutInflater.from(context).inflate(R.layout.view_home_item, this, true);
        videoThumbnail = findViewById(R.id.videoThumbnail);
        avatarImg = findViewById(R.id.avatarImg);
        like = findViewById(R.id.like);
        title = findViewById(R.id.title);
        avatar = findViewById(R.id.avatar);
        likeNum = findViewById(R.id.likeNum);
        avatarImg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Singletion.getInstance().setOtherUser(dynamic.getUser());
                context.startActivity(new Intent(context, PersonalActivity.class));
            }
        });
    }

    public void bindData(Dynamic home, int position, OnClicktitem bottomUpdateCallback) {
//        videoThumbnail//视频缩略图
        dynamic=home;
        this.position=position;
        title.setText(home.getMag());
        avatar.setText(home.getUser().getNick()!=null?home.getUser().getNick():home.getUser().getName());
        if(home.getUser().getHeadurl().length()<24){
            Glide.with(context).load(URL + SHOWIMGS+"?name=" +home.getUser().getHeadurl()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(avatarImg);
        }else {
            Glide.with(context).load(home.getUser().getHeadurl()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(avatarImg);
        }
        if(home.getImgs()!=null&&home.getImgs().size()>0){
            Imgs imgs=home.getImgs().get(0);
            Glide.with(context).load(URL + PLAYMPO+"?name=" +imgs.getImgurl()).into(videoThumbnail);
        }
      //  Glide.with(context).load(home.getUser().getHeadurl()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(avatarImg);
        likeNum.setText(String.valueOf(home.getPraises() == null ? 0 : home.getPraises().size()));
        like.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPraise(dynamic.getPraises())) {
                    like.setImageResource(R.drawable.praise_normal);
                     bottomUpdateCallback.OnLongClick(null,position);

                }else {
                    like.setImageResource(R.drawable.praise_checked);
                    bottomUpdateCallback.OnClick(null,position);

                }
            }
        });
    }
    private boolean isPraise(ArrayList<Praise> praises) {
        for (int i=0;i<praises.size();i++){
            if(praises.get(i).getUser().getId().equals(myUser.getId())){
                return true;
            }
        }
        return false;
    }


}
