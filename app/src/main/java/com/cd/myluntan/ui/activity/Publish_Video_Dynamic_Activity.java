package com.cd.myluntan.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.cd.myluntan.R;
import com.cd.myluntan.adapter.Photograph_Adapater;
import com.cd.myluntan.data_connection.Data_Access;
import com.cd.myluntan.entrty.Dynamic;
import com.cd.myluntan.entrty.Imgs;
import com.cd.myluntan.interfaceo.BottomUpdateCallback;
import com.cd.myluntan.interfaceo.NetworkCallback;
import com.cd.myluntan.interfaceo.OnClicktitem;
import com.cd.myluntan.utils.ContentUriUtil;
import com.cd.myluntan.utils.Singletion;
import com.cd.myluntan.utils.TimeUitl;
import com.cd.myluntan.utils.WindowUitls;
import com.donkingliang.imageselector.utils.ImageSelector;
import com.google.gson.Gson;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;
import pub.devrel.easypermissions.EasyPermissions;

import static com.cd.myluntan.data_connection.Global_Url_Parameters.ADDDYNAMIC;
import static com.cd.myluntan.data_connection.Global_Url_Parameters.ADDIMGHS;
import static com.cd.myluntan.data_connection.Global_Url_Parameters.NEW;
import static com.cd.myluntan.data_connection.Global_Url_Parameters.SHIPING;
import static com.cd.myluntan.data_connection.Global_Url_Parameters.SHOWIMGS;
import static com.cd.myluntan.data_connection.Global_Url_Parameters.URL;

public class Publish_Video_Dynamic_Activity extends AppCompatActivity  implements  EasyPermissions.PermissionCallbacks {
    private static final int REQUEST_CODE = 12;
    private  int maxNum=1024;
    ImageView back;

    private static final int RC_STORAGE = 1001;
    Photograph_Adapater photograph_adapater;
    TextView topTitle,sendDynamic,Dyamic_msg_twxt,textnumber,label_select;
    RecyclerView recycle_img;
    ArrayList<Imgs> imgs=new ArrayList<>();
    ArrayList<String> selectimg=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish__video__dynamic);
        WindowUitls.setColorTopBar(this,R.color.white);
        WindowUitls.setColorTextTopBarBlack(this);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
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
        topTitle.setText("发布视频");
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
                    dynamic.setType(SHIPING);
                    String classs=label_select.getText().toString().trim();
                    if(classs.contains("选择话题")) {
                        dynamic.setClasss("无");
                    }else {
                        String str=classs.substring(1,classs.length()-1);
                        dynamic.setClasss(str);
                    }
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
                    map.put("key","3");
                    Data_Access.sendMultipart(URL+ADDIMGHS,map,"file",files,null);
                    Toast.makeText(Publish_Video_Dynamic_Activity.this,"发布成功",Toast.LENGTH_LONG).show();
                    finish();
                }else {
                    Toast.makeText(Publish_Video_Dynamic_Activity.this,"请输入",Toast.LENGTH_LONG).show();

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

                    Toast.makeText(Publish_Video_Dynamic_Activity.this,"超出字数限制",Toast.LENGTH_LONG).show();
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
                String[] str=getResources().getStringArray(R.array.toplan_string);
                ArrayList<String> strings=new ArrayList<>();
                for (int k=0;k<str.length;k++){
                    strings.add(str[k]);
                }
                OptionsPickerView pvOptions = new OptionsPickerBuilder(Publish_Video_Dynamic_Activity.this, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                        //返回的分别是三个级别的选中位置
                        String tx = str[options1];
                        label_select.setText("#"+tx+"#");
                    }
                }).build();
                pvOptions.setPicker(strings);
                pvOptions.show();
            }
        });
        Imgs imgss=new Imgs();
        imgss.setImgurl("0");
        imgs.add(imgss);
        photograph_adapater=new Photograph_Adapater(imgs,Publish_Video_Dynamic_Activity.this);
        recycle_img.setAdapter(photograph_adapater);
        recycle_img.setLayoutManager(new GridLayoutManager(Publish_Video_Dynamic_Activity.this,3));
        photograph_adapater.setOnClicktitem(new OnClicktitem() {
            @Override
            public void OnClick(View.OnClickListener onClickListener, int k) {
                Imgs imga=imgs.get(k);
                if(imga.getImgurl().equals("0")){
                    final String[] wuran_headers = getResources().getStringArray(R.array.shiping_bidi_array);
                    WindowUitls.ShowBottomBarSelect(Publish_Video_Dynamic_Activity.this, wuran_headers,topTitle , new BottomUpdateCallback() {
                        @Override
                        public void bottomBarShow(int dy) {
                            if (dy == 222) {
                                Intent intent = new Intent();
                                intent.setType("video/*");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(intent, 1);
                            }else if(dy==333){
                                requestPermision();

                            }
                        }
                    });

                }else {
                    Singletion.getInstance().setImgs(imgs);
                    startActivity(new Intent(Publish_Video_Dynamic_Activity.this, Show_Sing_images_Activity.class).putExtra("index",k));
                }
            }

            @Override
            public void OnLongClick(View.OnLongClickListener onLongClickListener, int k) {
                AlertDialog.Builder alertDialog=new AlertDialog.Builder(Publish_Video_Dynamic_Activity.this);
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
    private static Uri getOutputMediaFileUri(){
        //get the mobile Pictures directory
        File picDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        //get the current time
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        File videoFile = new File(picDir.getPath() + File.separator + "VIDEO_"+ timeStamp + ".mp4");

        return Uri.fromFile(videoFile);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == 1||requestCode == 3) && data != null) {
            //获取选择器返回的数据
            Uri uri = data.getData();
            String path = ContentUriUtil.getPath(this, uri);
            Log.i("filepath", " = " + path);
            imgs.clear();
            Imgs img = new Imgs();
            img.setImgurl(path);
            imgs.add(img);
            Imgs img2 = new Imgs();
            img2.setImgurl("0");
            imgs.add(img2);
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
    private void requestPermision() {
        String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // Already have permission, do the thing
            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            Uri  fileUri = getOutputMediaFileUri(); // create a file Uri to save the video

            // set the video file name
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

            // set the video quality high
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
            startActivityForResult(intent, 3);
            // ...
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, "我们的app需要以下权限",
                    RC_STORAGE, perms);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

    }
}
