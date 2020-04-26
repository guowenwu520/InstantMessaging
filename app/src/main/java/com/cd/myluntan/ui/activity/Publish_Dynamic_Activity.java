package com.cd.myluntan.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cd.myluntan.R;
import com.cd.myluntan.adapter.Photograph_Adapater;
import com.cd.myluntan.data_connection.Data_Access;
import com.cd.myluntan.entrty.Dynamic;
import com.cd.myluntan.entrty.Imgs;
import com.cd.myluntan.interfaceo.NetworkCallback;
import com.cd.myluntan.interfaceo.OnClicktitem;
import com.cd.myluntan.utils.Singletion;
import com.cd.myluntan.utils.TimeUitl;
import com.cd.myluntan.utils.WindowUitls;
import com.donkingliang.imageselector.utils.ImageSelector;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

import static com.cd.myluntan.data_connection.Global_Url_Parameters.ADDDYNAMIC;
import static com.cd.myluntan.data_connection.Global_Url_Parameters.ADDIMGHS;
import static com.cd.myluntan.data_connection.Global_Url_Parameters.NEW;
import static com.cd.myluntan.data_connection.Global_Url_Parameters.URL;

public class Publish_Dynamic_Activity extends AppCompatActivity {
    private static final int REQUEST_CODE = 12;
    private  int maxNum=1024;
    ImageView back;
    Photograph_Adapater photograph_adapater;
    TextView topTitle,sendDynamic,Dyamic_msg_twxt,textnumber,label_select;
    RecyclerView recycle_img;
    ArrayList<Imgs> imgs=new ArrayList<>();
    ArrayList<String> selectimg=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish__dynamic);
        WindowUitls.setColorTopBar(this,R.color.white);
        WindowUitls.setColorTextTopBarBlack(this);
        initview();
        initEvent();
    }

    private void initEvent() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        topTitle.setText("发布动态");
        sendDynamic.setVisibility(View.VISIBLE);
        //发布动态
        sendDynamic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg=Dyamic_msg_twxt.getText().toString().trim();
                if(!msg.equals("")) {
                    String dynamicid=System.currentTimeMillis()+"";
                    imgs.remove(imgs.size()-1);
                    Dynamic dynamic = new Dynamic();
                    dynamic.setUserid(Singletion.getInstance().getUser().getId());
                    dynamic.setPraises(new ArrayList<>());
                    dynamic.setComments(new ArrayList<>());
                    dynamic.setLabels(new ArrayList<>());
                    dynamic.setMag(msg);
                    dynamic.setId(dynamicid);
                    dynamic.setTime(TimeUitl.DataToString(new Date()));
                    dynamic.setType(NEW);
                    Data_Access.AccessJSONDate(URL + ADDDYNAMIC, new Gson().toJson(dynamic), new NetworkCallback() {
                        @Override
                        public Object parseNetworkResponse(Response response) {
                            return null;
                        }

                        @Override
                        public void onError(Call call, Exception e) {

                        }

                        @Override
                        public void onResponse(Object response) {

                        }
                    });
                    ArrayList<File>  files=new ArrayList<>();
                    for (int i=0;i<imgs.size();i++){
                        Imgs img=imgs.get(i);
                        files.add(new File(img.imgurl));
                    }
                    Map<String,String> map=new HashMap<>();
                    map.put("id",dynamicid);
                    Data_Access.sendMultipart(URL+ADDIMGHS,map,"file",files,null);
                    Toast.makeText(Publish_Dynamic_Activity.this,"发布成功",Toast.LENGTH_LONG).show();
                    finish();
                }else {
                    Toast.makeText(Publish_Dynamic_Activity.this,"请输入",Toast.LENGTH_LONG).show();

                }
            }
        });
        Dyamic_msg_twxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String strs=s.toString();
                if(strs.length()<=maxNum){
                    textnumber.setText(strs.length()+"/"+maxNum);
                }else {

                    Toast.makeText(Publish_Dynamic_Activity.this,"超出字数限制",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()>maxNum)
                    s.delete(maxNum,s.length());
            }
        });
        //选择话题
        label_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        Imgs imgss=new Imgs();
        imgss.setImgurl("0");
        imgs.add(imgss);
        photograph_adapater=new Photograph_Adapater(imgs,Publish_Dynamic_Activity.this);
        recycle_img.setAdapter(photograph_adapater);
        recycle_img.setLayoutManager(new GridLayoutManager(Publish_Dynamic_Activity.this,3));
        photograph_adapater.setOnClicktitem(new OnClicktitem() {
            @Override
            public void OnClick(View.OnClickListener onClickListener, int k) {
                Imgs imga=imgs.get(k);
                if(imga.getImgurl().equals("0")){
                    ImageSelector.builder()
                            .useCamera(true) // 设置是否使用拍照
                            .setSingle(false)  //设置是否单选
                            .setMaxSelectCount(9) // 图片的最大选择数量，小于等于0时，不限数量。
                            .setSelected(selectimg) // 把已选的图片传入默认选中。
                            .canPreview(true) //是否可以预览图片，默认为true
                            .start(Publish_Dynamic_Activity.this, REQUEST_CODE); // 打开相册
                }else {
                    Singletion.getInstance().setImgs(imgs);
                    startActivity(new Intent(Publish_Dynamic_Activity.this, Show_Sing_images_Activity.class).putExtra("index",k));
                }
            }

            @Override
            public void OnLongClick(View.OnLongClickListener onLongClickListener, int k) {
                AlertDialog.Builder alertDialog=new AlertDialog.Builder(Publish_Dynamic_Activity.this);
                alertDialog.setTitle("提示");
                alertDialog.setMessage("确认删除？").setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        imgs.remove(k);
                        photograph_adapater.setImgsandfilsh(imgs);
                        dialog.dismiss();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && data != null) {
            //获取选择器返回的数据
            selectimg = data.getStringArrayListExtra(
                    ImageSelector.SELECT_RESULT);
              imgs.clear();
              for (int i=0;i<selectimg.size();i++){
                   Imgs img=new Imgs();
                   img.setImgurl(selectimg.get(i));
                   imgs.add(img);
              }
            Imgs img=new Imgs();
            img.setImgurl("0");
            imgs.add(img);
            photograph_adapater.setImgsandfilsh(imgs);
        }
    }
    private void initview() {
        //顶部栏
        back=findViewById(R.id.backimg);
        topTitle=findViewById(R.id.toptitle);
        sendDynamic=findViewById(R.id.event);
        //动态发布
        recycle_img=findViewById(R.id.recycle_img);
        Dyamic_msg_twxt=findViewById(R.id.Dyamic_msg_twxt);
        textnumber=findViewById(R.id.textnumber);
        label_select=findViewById(R.id.label_select);
    }
}
