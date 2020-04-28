package com.cd.myluntan.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.cd.myluntan.R;
import com.cd.myluntan.entrty.User;
import com.cd.myluntan.utils.Constant;
import com.cd.myluntan.utils.Singletion;
import com.cd.myluntan.utils.WindowUitls;

import java.util.ArrayList;

import static com.cd.myluntan.data_connection.Global_Url_Parameters.SHOWIMGS;
import static com.cd.myluntan.data_connection.Global_Url_Parameters.URL;

public class PersonalActivity extends BaseActivity  implements View.OnClickListener {
    ImageView back;
    TextView topTitle;
    User user;
    User myUser;
    ImageView head_url;
    TextView chat,follor;
    TextView name,myid,sign,follor_number,fans_number,fangwen_number,rank_number,dynimic_number,discuss_number,record_number;
    LinearLayout follor_rl,fans_rl,fangwen_rl,rank_rl,dynimic_rl,discuss_rl,record_rl;
    private boolean fign=false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        WindowUitls.setColorTopBar(this,R.color.white);
        WindowUitls.setColorTextTopBarBlack(this);
        user= Singletion.getInstance().getOtherUser();
        myUser=Singletion.getInstance().getUser();
        initView();
        initShow();
    }

    private void initShow() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        topTitle.setText("主页");
        name.setText(user.getNick()!=null?user.getNick():user.getName());
        if(user.getHeadurl().length()<24){
            Glide.with(this).load(URL + SHOWIMGS+"?name=" +user.getHeadurl()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(head_url);
        }else {
            Glide.with(this).load(user.getHeadurl()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(head_url);
        }
        myid.setText(user.getId());
        sign.setText(user.getSignaturnre());
        //关注人数
        follor_number.setText("34");
        follor_rl.setOnClickListener(this);
        //粉丝人数
        fans_number.setText("99+");
        fans_rl.setOnClickListener(this);
        //访问人数
        fangwen_number.setText("99+");
        fangwen_rl.setOnClickListener(this);
        //排名
        rank_number.setText("99+");
        rank_rl.setOnClickListener(this);
        //动态数量
        dynimic_number.setText("99+");
        dynimic_rl.setOnClickListener(this);
        //话题数量
        discuss_number.setText("99+");
        discuss_rl.setOnClickListener(this);
        //记录数量
        record_number.setText("99+");
        record_rl.setOnClickListener(this);
        chat.setOnClickListener(this);
        follor.setOnClickListener(this);
        //是否是自己
        if(myUser.getId().equals(user.getId())){
            chat.setVisibility(View.GONE);
            follor.setVisibility(View.GONE);
        }else {
            ArrayList<User> follows = Singletion.getInstance().getFollow();
            for (int i = 0; i < follows.size(); i++) {
                if (follows.get(i).getName().equals(user.getName())){
                    follor.setText("已关注");
                    follor.setTextColor(getResources().getColor(R.color.content_grey));
                }
            }
            chat.setVisibility(View.VISIBLE);
            follor.setVisibility(View.VISIBLE);
        }
    }

    private void initView() {
        //顶部栏
        back=findViewById(R.id.backimg);
        topTitle=findViewById(R.id.toptitle);
        head_url=findViewById(R.id.imgurl_head);
        name=findViewById(R.id.name);
        myid=findViewById(R.id.myid);
        sign=findViewById(R.id.signature);
        fangwen_number=findViewById(R.id.fangwei_number);
        fangwen_rl=findViewById(R.id.fangwei_rl);
        fans_number=findViewById(R.id.fans_number);
        fans_rl=findViewById(R.id.fans_rl);
        follor_number=findViewById(R.id.follor_number);
        follor_rl=findViewById(R.id.follor_rl);
        rank_number=findViewById(R.id.rank_number);
        rank_rl=findViewById(R.id.rank_rl);
        dynimic_rl=findViewById(R.id.dynmiac_rl);
        dynimic_number=findViewById(R.id.dynimic_number);
        discuss_number=findViewById(R.id.discuss_number);
        discuss_rl=findViewById(R.id.discuss_rl);
        record_number=findViewById(R.id.record_number);
        record_rl=findViewById(R.id.record_rl);
        chat=findViewById(R.id.chat);
        follor=findViewById(R.id.follor);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
               //话题
            case R.id.discuss_rl:
                break;
                //动态
            case R.id.dynmiac_rl:
                break;
                //关注
            case R.id.follor_rl:
                break;
                //记录
            case R.id.record_rl:
                break;
                //排名
            case R.id.rank_rl:
                break;
                //访问
            case R.id.fangwei_rl:
                break;
                //粉丝
            case R.id.fans_rl:
                break;
            //关注
            case R.id.follor:
                if(!isFollow(user)){
                   follor.setText("已关注");
                   follor.setTextColor(getResources().getColor(R.color.content_grey));
                   follor.setBackgroundResource(R.drawable.is_follow_button_bg);
                }else {
                   follor.setText("关注");
                   follor.setTextColor(getResources().getColor(R.color.colorPrimary));
                   follor.setBackgroundResource(R.drawable.no_follow_button_bg);

                }
                break;
            //私信
            case R.id.chat:
                startActivity(new Intent(this,ChatActivity.class).putExtra("chatType", Constant.CHATTYPE_SINGLE).putExtra("userId", user.getName()).putExtra("imgurl",user.getHeadurl()));
                break;
        }
    }

    private boolean isFollow(User myUser) {
        //关注逻辑
        ArrayList<User> follows = Singletion.getInstance().getFollow();
        for (int i = 0; i < follows.size(); i++) {
            if (follows.get(i).getName().equals(user.getName())){
                return true;
            }
        }
        return false;
    }
}
