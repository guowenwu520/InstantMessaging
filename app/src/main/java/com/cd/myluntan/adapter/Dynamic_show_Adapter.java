package com.cd.myluntan.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
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
import com.cd.myluntan.data_connection.Data_Access;
import com.cd.myluntan.entrty.Dynamic;
import com.cd.myluntan.entrty.Praise;
import com.cd.myluntan.entrty.User;
import com.cd.myluntan.interfaceo.BottomUpdateCallback;
import com.cd.myluntan.interfaceo.OnClicktitem;
import com.cd.myluntan.ui.activity.Dynamic_Details_Activity;
import com.cd.myluntan.ui.activity.PersonalActivity;
import com.cd.myluntan.ui.activity.Show_Sing_images_Activity;
import com.cd.myluntan.utils.Singletion;
import com.cd.myluntan.utils.WindowUitls;
import com.google.gson.Gson;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.cd.myluntan.data_connection.Global_Url_Parameters.ADDPRAISE;
import static com.cd.myluntan.data_connection.Global_Url_Parameters.DELETEPRAISE;
import static com.cd.myluntan.data_connection.Global_Url_Parameters.SHOWIMGS;
import static com.cd.myluntan.data_connection.Global_Url_Parameters.URL;

public class Dynamic_show_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = Dynamic_show_Adapter.class.getCanonicalName();
    Activity context;
    private int index = 0;
    ArrayList<Dynamic> dynamics = new ArrayList<>();
    ArrayList<User> users = new ArrayList<>();
    User myUser;
    View view;

    public Dynamic_show_Adapter(Activity context, ArrayList<Dynamic> dynamics, int i, View activity) {
        this.dynamics = dynamics;
        this.context = context;
        view = activity;
        myUser = Singletion.getInstance().getUser();
        index = i;
        //  getUserNames();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dynamic_item_view, parent, false);
        return new myViewHolderClass(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final Dynamic dynamic = dynamics.get(position);
        final myViewHolderClass myViewHolderClass = (Dynamic_show_Adapter.myViewHolderClass) holder;
        updateList(myViewHolderClass, dynamic, position);
    }

    @Override
    public int getItemCount() {
        return dynamics.size();
    }

    public void setDataDynamicandFinsh(ArrayList<Dynamic> dynamics) {
        this.dynamics = dynamics;
        notifyDataSetChanged();
    }

    class myViewHolderClass extends RecyclerView.ViewHolder {
        ImageView imghead, praise, more;
        TextView commitNum, praisenNum, name, time, content, follow, fans;
        LinearLayout rl_share, rl_commit;
        RecyclerView img_recycleview, label_recycle;

        public myViewHolderClass(View itemView) {
            super(itemView);
            more = itemView.findViewById(R.id.more);
            praise = itemView.findViewById(R.id.praise);
            commitNum = itemView.findViewById(R.id.commitNum);
            praisenNum = itemView.findViewById(R.id.praiseNum);
            content = itemView.findViewById(R.id.content);
            follow = itemView.findViewById(R.id.follow);
            fans = itemView.findViewById(R.id.fans);
            rl_commit = itemView.findViewById(R.id.commit);
            rl_share = itemView.findViewById(R.id.share);
            img_recycleview = itemView.findViewById(R.id.img_recyclerView);
            label_recycle = itemView.findViewById(R.id.label_recycle);
            imghead = itemView.findViewById(R.id.head);
            name = itemView.findViewById(R.id.name);
            time = itemView.findViewById(R.id.time);
        }
    }

    private void updateList(final myViewHolderClass myViewHolderClass, final Dynamic dynamic, int position) {
        //用户消息
        User user = dynamic.getUser();
        myViewHolderClass.name.setText(user.getNick() != null ? user.getNick() : user.getName());
        if (user.getHeadurl().length() < 24) {
            Glide.with(context).load(URL + SHOWIMGS + "?name=" + user.getHeadurl()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(myViewHolderClass.imghead);
        } else {
            Glide.with(context).load(user.getHeadurl()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(myViewHolderClass.imghead);
        }
        //评论
        myViewHolderClass.time.setText(dynamic.getTime());
        myViewHolderClass.content.setText(dynamic.getMag());
        myViewHolderClass.praisenNum.setText(dynamic.getPraises().size() + "");
        myViewHolderClass.commitNum.setText(dynamic.getComments().size() + "");
        //判断是否为自己
        if (user.getName().equals(myUser.getName())) {
            myViewHolderClass.follow.setVisibility(View.GONE);
        }
        //判断是否点过赞
        if (isPraise(dynamic.getPraises())) {
            myViewHolderClass.praise.setImageResource(R.drawable.praise_checked);
        } else {
            myViewHolderClass.praise.setImageResource(R.drawable.praise_normal);
        }
        //判断是否关注
        if (user.getIsFollow() == User.TYPE_IS_FOLLOW) {
            Log.d(TAG, "=============isFollow=====已关注" + users.size());
            myViewHolderClass.follow.setText("已关注");
            myViewHolderClass.follow.setTextColor(context.getResources().getColor(R.color.content_grey));
        } else {
            Log.d(TAG, "=============isFollow=====关注" + users.size());
            myViewHolderClass.follow.setText("关注");
            myViewHolderClass.follow.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        }
        //点击关注
        myViewHolderClass.follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                followed();
            }

            private void followed() {
                if (user.getIsFollow() == 0) {
                    //参数为要添加的好友的username和添加理由
                    try {
                        Log.d(TAG, "=============followed=====已关注" + users.size());
                        myViewHolderClass.follow.setText("已关注");
                        myViewHolderClass.follow.setTextColor(context.getResources().getColor(R.color.content_grey));
                        EMClient.getInstance().contactManager().addContact(user.getName(), null);
                        ArrayList<User> follows =Singletion.getInstance().getFollow();
                        follows.add(user);
                        Singletion.getInstance().setFollow(follows);
                        for (int i = 0; i < dynamics.size(); i++) {
                            if (dynamics.get(i).getUser().getName().equals(user.getName())){
                                dynamics.get(i).getUser().setIsFollow(User.TYPE_IS_FOLLOW);
                            }
                        }
                        notifyDataSetChanged();
//                        dynamics.get(position).getUser().setIsFollow(User.TYPE_IS_FOLLOW);
                        Toast.makeText(context, "关注成功", Toast.LENGTH_SHORT).show();
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                        myViewHolderClass.follow.setText("关注");
                        myViewHolderClass.follow.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                        Toast.makeText(context, "关注失败", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //参数为要添加的好友的username和添加理由
                    try {
                        Log.d(TAG, "=============followed=====关注" + users.size());
                        myViewHolderClass.follow.setText("关注");
                        myViewHolderClass.follow.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                        EMClient.getInstance().contactManager().deleteContact(user.getName());
                        ArrayList<User> follows =Singletion.getInstance().getFollow();
                        for (int i = 0; i < follows.size(); i++) {
                            if (follows.get(i).getName().equals(user.getName())) {
                                follows.remove(i);
                                i--;
                            }
                        }
                        Singletion.getInstance().setFollow(follows);
                        for (int i = 0; i < dynamics.size(); i++) {
                            if (dynamics.get(i).getUser().getName().equals(user.getName())){
                                dynamics.get(i).getUser().setIsFollow(User.TYPE_NOT_IS_FOLLOW);
                                notifyItemChanged(i);
                            }
                        }
//                        dynamics.get(position).getUser().setIsFollow(User.TYPE_NOT_IS_FOLLOW);
                        Toast.makeText(context, "取消关注成功", Toast.LENGTH_SHORT).show();
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                        myViewHolderClass.follow.setText("已关注");
                        myViewHolderClass.follow.setTextColor(context.getResources().getColor(R.color.content_grey));
                        Toast.makeText(context, "取消关注失败", Toast.LENGTH_SHORT).show();
                    }
                }
                notifyItemChanged(position);
            }
        });
        //点赞
        myViewHolderClass.praise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPraise(dynamic.getPraises())) {
                    rmPraise(dynamic, position);
                    myViewHolderClass.praise.setImageResource(R.drawable.praise_normal);
                } else {
                    addPraise(dynamic, position);
                    myViewHolderClass.praise.setImageResource(R.drawable.praise_checked);
                }
            }
        });
        //点击评论
        myViewHolderClass.rl_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Singletion.getInstance().setDynamic(dynamic);
                context.startActivity(new Intent(context, Dynamic_Details_Activity.class).putExtra("index", index).putExtra("postion", position));
            }
        });
        //点击更多
        myViewHolderClass.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] wuran_headers;
                if (dynamic.getUser().getId().equals(myUser.getId())) {
                    wuran_headers = context.getResources().getStringArray(R.array.my_more_string);
                } else {
                    wuran_headers = context.getResources().getStringArray(R.array.out_more_string);
                }
                String id = dynamic.getId();
                WindowUitls.ShowBottomBarSelect(context, wuran_headers, myViewHolderClass.rl_share, dynamic, new BottomUpdateCallback() {
                    @Override
                    public void bottomBarShow(int dy) {
                        context.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (dy == 0) {
                                    Toast.makeText(context, "删除成功", Toast.LENGTH_LONG).show();
                                    for (int i = 0; i < dynamics.size(); i++) {
                                        if (id.equals(dynamics.get(i).getId())) {
                                            dynamics.remove(i);
                                        }
                                    }
                                    setDataDynamicandFinsh(dynamics);
                                }
                            }
                        });

                    }
                });
            }
        });
        //点击分享
        myViewHolderClass.rl_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] wuran_headers = context.getResources().getStringArray(R.array.share_string);
                String id = dynamic.getId();
                WindowUitls.ShowBottomBarSelect(context, wuran_headers, myViewHolderClass.rl_share, dynamic, new BottomUpdateCallback() {
                    @Override
                    public void bottomBarShow(int dy) {
                        context.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (dy == 0) {
                                    Toast.makeText(context, "删除成功", Toast.LENGTH_LONG).show();
                                    for (int i = 0; i < dynamics.size(); i++) {
                                        if (id.equals(dynamics.get(i).getId())) {
                                            dynamics.remove(i);
                                        }
                                    }
                                    setDataDynamicandFinsh(dynamics);
                                }
                            }
                        });

                    }
                });
            }
        });
        //点击内容
        myViewHolderClass.content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Singletion.getInstance().setDynamic(dynamic);
                context.startActivity(new Intent(context, Dynamic_Details_Activity.class).putExtra("index", index).putExtra("postion", position));
            }
        });
        //点击头像
        myViewHolderClass.imghead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Singletion.getInstance().setOtherUser(dynamic.getUser());
                context.startActivity(new Intent(context, PersonalActivity.class));
            }
        });
        //图片布局
        Photograph_Adapater photograph_adapater = new Photograph_Adapater(dynamic.getImgs(), context);
        myViewHolderClass.img_recycleview.setAdapter(photograph_adapater);
        myViewHolderClass.img_recycleview.setLayoutManager(new GridLayoutManager(context, 3));
        photograph_adapater.setOnClicktitem(new OnClicktitem() {
            @Override
            public void OnClick(View.OnClickListener onClickListener, int k) {
                Singletion.getInstance().setImgs(dynamic.getImgs());
                context.startActivity(new Intent(context, Show_Sing_images_Activity.class).putExtra("index", k));
            }

            @Override
            public void OnLongClick(View.OnLongClickListener onLongClickListener, int k) {

            }
        });
        //标签布局
//        Label_Adapter label_adapter=new Label_Adapter(dynamic.getLabels(),context);
//        myViewHolderClass.label_recycle.setAdapter(photograph_adapater);
//        myViewHolderClass.label_recycle.setLayoutManager(new GridLayoutManager(context,3));
    }

    private void rmPraise(Dynamic dynamic, int pstin) {
        ArrayList<Praise> praises = dynamic.getPraises();
        for (int i = 0; i < praises.size(); i++) {
            if (myUser.getId().equals(praises.get(i).getUser().getId())) {
                Map<String, String> map = new HashMap<>();
                map.put("id", praises.get(i).getId());
                Data_Access.AccessStringDate(URL + DELETEPRAISE, map, null);
                praises.remove(i);
                break;
            }
        }
        //dynamic.setPraises(praises);
        // Singletion.getInstance().setDynamic(dynamic);
        dynamics.get(pstin).setPraises(praises);
        notifyDataSetChanged();
    }

    private void addPraise(Dynamic dynamic, int psin) {
        Praise praise = new Praise();
        praise.setUser(myUser);
        praise.setDynamicid(dynamic.getId());
        praise.setId(System.currentTimeMillis() + "");
        praise.setUserid(myUser.getId());
        // dynamic.getPraises().add(0,praise);
        // Singletion.getInstance().setDynamic(dynamic);
        Data_Access.AccessJSONDate(URL + ADDPRAISE, new Gson().toJson(praise), null);
        dynamics.get(psin).getPraises().add(0, praise);
        notifyDataSetChanged();
    }

    private boolean isPraise(ArrayList<Praise> praises) {
        for (int i = 0; i < praises.size(); i++) {
            if (praises.get(i).getUser().getId().equals(myUser.getId())) {
                return true;
            }
        }
        return false;
    }


}
