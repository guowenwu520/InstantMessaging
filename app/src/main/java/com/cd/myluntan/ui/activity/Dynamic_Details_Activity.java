package com.cd.myluntan.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.cd.myluntan.R;
import com.cd.myluntan.adapter.Dynamic_Top_FragmentAdapter;
import com.cd.myluntan.adapter.Dynamic_show_Adapter;
import com.cd.myluntan.adapter.Photograph_Adapater;
import com.cd.myluntan.entrty.Dynamic;
import com.cd.myluntan.entrty.Praise;
import com.cd.myluntan.entrty.User;
import com.cd.myluntan.ui.fragment.Commit_Fragment;
import com.cd.myluntan.ui.fragment.Dynamic_New_Fragment;
import com.cd.myluntan.ui.fragment.Dynsmic_Hot_Fragment;
import com.cd.myluntan.ui.fragment.Praise_Fragment;
import com.cd.myluntan.utils.DisplayUtils;
import com.cd.myluntan.utils.Singletion;
import com.cd.myluntan.utils.WindowUitls;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class Dynamic_Details_Activity extends AppCompatActivity {
    //top指示
    int index=0;
    //数据位置
    int postion=0;
  Dynamic dynamic;
  ImageView back;
  TextView topTitle;
    User myUser;

    ImageView imghead,praise,more;
    TextView commitNum,praisenNum,name,time,content,follow,fans;
    LinearLayout rl_share,rl_commit;
    RecyclerView img_recycleview,label_recycle;


    private ViewPager viewPager;
    private TabLayout toolbar;
   private  Praise_Fragment praise_fragment;
   private  Commit_Fragment commit_fragment;

    private LinearLayout sendmsg_linearlayout;
    private EditText text_mssg;
    private  TextView send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic__details);
        dynamic= Singletion.getInstance().getDynamic();
        myUser= Singletion.getInstance().getUser();
        index=getIntent().getIntExtra("index",0);
        postion=getIntent().getIntExtra("postion",0);
        Singletion.getInstance().setIndex(index);
        Singletion.getInstance().setPostion(postion);
        WindowUitls.setColorTopBar(this,R.color.white);
        WindowUitls.setColorTextTopBarBlack(this);
        initView();
        initShow();
    }

    private void initShow() {
        //顶部
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //动态消息
        updateList();
        //点赞评论
        updateCommit_Praise();
        //评论发送
      //  Expand_input();
        Contract_input();
//        text_mssg.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if(hasFocus){
//                    Expand_input();
//                }else {
//                    Contract_input();
//                }
//            }
//        });
      send.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

          }
      });
    }

    private void Expand_input() {
        RelativeLayout.LayoutParams layoutParams= (RelativeLayout.LayoutParams) sendmsg_linearlayout.getLayoutParams();
        layoutParams.height=DisplayUtils.dp2px(this,140);
        LinearLayout.LayoutParams layoutParams2= (LinearLayout.LayoutParams) text_mssg.getLayoutParams();
        layoutParams2.height=DisplayUtils.dp2px(this,100);
        sendmsg_linearlayout.setLayoutParams(layoutParams);
        text_mssg.setLayoutParams(layoutParams2);
        send.setVisibility(View.VISIBLE);
        //打开软件盘
//       text_mssg.setFocusable(true);
//        text_mssg.setFocusableInTouchMode(true);
//        text_mssg.requestFocus();
        InputMethodManager imm = (InputMethodManager)text_mssg.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(text_mssg, 0);
       // imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

    }

    private void Contract_input() {
        RelativeLayout.LayoutParams layoutParams= (RelativeLayout.LayoutParams) sendmsg_linearlayout.getLayoutParams();
        layoutParams.height= DisplayUtils.dp2px(this,48);
        LinearLayout.LayoutParams layoutParams2= (LinearLayout.LayoutParams) text_mssg.getLayoutParams();
        layoutParams2.height=DisplayUtils.dp2px(this,30);
        sendmsg_linearlayout.setLayoutParams(layoutParams);
        text_mssg.setLayoutParams(layoutParams2);
        send.setVisibility(View.GONE);
    }

    private void updateCommit_Praise() {
        ArrayList<Fragment> list=new ArrayList<>();
        praise_fragment=new Praise_Fragment(dynamic.getPraises());
        commit_fragment=new Commit_Fragment(dynamic.getComments());
        list.add(praise_fragment);
        list.add(commit_fragment);
        ArrayList<String> names=new ArrayList<>();
        names.add("点赞");
        names.add("评论");
        Dynamic_Top_FragmentAdapter dynamic_top_fragmentAdapter=new Dynamic_Top_FragmentAdapter(getSupportFragmentManager(),list,names);
        viewPager.setAdapter(dynamic_top_fragmentAdapter);
        toolbar.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(1);
    }

    private void initView() {
        //顶部栏
        back=findViewById(R.id.backimg);
        topTitle=findViewById(R.id.toptitle);
        //动态消息
        more=findViewById(R.id.more);
        praise=findViewById(R.id.praise);
        commitNum=findViewById(R.id.commitNum);
        praisenNum=findViewById(R.id.praiseNum);
        content=findViewById(R.id.content);
        follow=findViewById(R.id.follow);
        fans=findViewById(R.id.fans);
        rl_commit=findViewById(R.id.commit);
        rl_share=findViewById(R.id.share);
        img_recycleview=findViewById(R.id.img_recyclerView);
        label_recycle=findViewById(R.id.label_recycle);
        imghead=findViewById(R.id.head);
        name=findViewById(R.id.name);
        time=findViewById(R.id.time);
        //评论点赞
        toolbar=findViewById(R.id.dynamic_topbar);
        viewPager=findViewById(R.id.dynamic_viewpage);
        //评论发送
        sendmsg_linearlayout=findViewById(R.id.input_liner);
        text_mssg=findViewById(R.id.input_text_msg);
        send=findViewById(R.id.send);
    }

    private void updateList() {
        //用户消息
        User user=dynamic.getUser();
        name.setText(user.getName());
        Glide.with(Dynamic_Details_Activity.this).load(user.getHead_url()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(imghead);
        //评论
        time.setText(dynamic.getTime());
        content.setText(dynamic.getMag());
        praisenNum.setText(dynamic.getPraises().size()+"");
        commitNum.setText(dynamic.getComments().size()+"");
        //判断是否点过赞
        if(isPraise(dynamic.getPraises())) {
            praise.setImageResource(R.drawable.praise_checked);
        }else {
            praise.setImageResource(R.drawable.praise_normal);
        }
        //判断是否关注
        if(isFollow(user)) {
            follow.setText("已关注");
            follow.setTextColor(Dynamic_Details_Activity.this.getResources().getColor(R.color.content_grey));
        }else {
            follow.setText("关注");
            follow.setTextColor(Dynamic_Details_Activity.this.getResources().getColor(R.color.colorPrimary));
        }
        //点击关注
        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Dynamic_Details_Activity.this,"点击了关注",Toast.LENGTH_LONG).show();
            }
        });
        //点赞
        praise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPraise(dynamic.getPraises())) {
                    rmPraise(dynamic);
                    praise.setImageResource(R.drawable.praise_normal);
                }else {
                    addPraise(dynamic);
                    praise.setImageResource(R.drawable.praise_checked);
                }
            }
        });
        //点击评论
        rl_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Expand_input();
              }
        });
        //点击更多
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Dynamic_Details_Activity.this,"点击了更多",Toast.LENGTH_LONG).show();
            }
        });
        //点击分享
        rl_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Dynamic_Details_Activity.this,"点击了分享",Toast.LENGTH_LONG).show();
            }
        });
        //点击头像
        imghead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Dynamic_Details_Activity.this,"点击了头像",Toast.LENGTH_LONG).show();
            }
        });
        //图片布局
        Photograph_Adapater photograph_adapater=new Photograph_Adapater(dynamic.getImgs(),Dynamic_Details_Activity.this);
        img_recycleview.setAdapter(photograph_adapater);
        img_recycleview.setLayoutManager(new GridLayoutManager(Dynamic_Details_Activity.this,3));
        //标签布局
//        Label_Adapter label_adapter=new Label_Adapter(dynamic.getLabels(),Dynamic_Details_Activity.this);
//        label_recycle.setAdapter(photograph_adapater);
//        label_recycle.setLayoutManager(new GridLayoutManager(Dynamic_Details_Activity.this,3));
    }

    private void rmPraise(Dynamic dynamic) {
        ArrayList<Praise> praises=dynamic.getPraises();
        for (int i=0;i<praises.size();i++){
            if(myUser.getId().equals(praises.get(i).getUser().getId())){
                praises.remove(i);
                break;
            }
        }
        praise_fragment.setPraisesAndFlsh(praises);
        dynamic.setPraises(praises);
        updateList();
    }

    private void addPraise(Dynamic dynamic) {
        Praise praise=new Praise();
        praise.setUser(myUser);
        praise.setDynamic_id(dynamic.getId());
        praise.setId("1231231");
        dynamic.getPraises().add(0,praise);
        praise_fragment.setPraisesAndFlsh(dynamic.getPraises());
        updateList();
    }

    private boolean isFollow(User user) {
        //关注逻辑
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

}
