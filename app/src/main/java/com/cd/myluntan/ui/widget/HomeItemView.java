package com.cd.myluntan.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.cd.myluntan.R;
import com.cd.myluntan.entrty.Home;

public class HomeItemView extends RelativeLayout {
    private Context context;
    private ImageView videoThumbnail, avatarImg, like;
    private TextView title, avatar, likeNum;

    public HomeItemView(Context context) {
        this(context, null);
    }

    public HomeItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
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
        like.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "点赞", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void bindData(Home home) {
//        videoThumbnail//视频缩略图
        title.setText(home.getTitle());
        avatar.setText(home.getAuthor().getName());
        Glide.with(context).load(home.getAuthor().getHead_url()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(avatarImg);
        likeNum.setText(String.valueOf(home.getPraises() == null ? 0 : home.getPraises().size()));
    }
}
