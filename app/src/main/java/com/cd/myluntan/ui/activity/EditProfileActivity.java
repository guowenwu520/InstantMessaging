package com.cd.myluntan.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.cd.myluntan.R;
import com.cd.myluntan.data_connection.Data_Access;
import com.cd.myluntan.entrty.Imgs;
import com.cd.myluntan.entrty.User;
import com.cd.myluntan.interfaceo.NetworkCallback;
import com.cd.myluntan.utils.Singletion;
import com.cd.myluntan.utils.TimeUitl;
import com.cd.myluntan.utils.WindowUitls;
import com.donkingliang.imageselector.utils.ImageSelector;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

import static com.cd.myluntan.data_connection.Global_Url_Parameters.ADDIMGHS;
import static com.cd.myluntan.data_connection.Global_Url_Parameters.GETUSERBYID;
import static com.cd.myluntan.data_connection.Global_Url_Parameters.SHOWIMGS;
import static com.cd.myluntan.data_connection.Global_Url_Parameters.UPSATEUSER;
import static com.cd.myluntan.data_connection.Global_Url_Parameters.URL;

public class EditProfileActivity extends BaseActivity implements View.OnClickListener {
    private static final int REQUEST_CODE = 12;
    private ImageView avatar,back;
    private LinearLayout nicknameLL, sexLL, birthdayLL, signatureLL, passwordLL;
    private TextView nickname, sex, birthday, signature, password;

    User user;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        WindowUitls.setColorTopBar(this,R.color.white);
        WindowUitls.setColorTextTopBarBlack(this);

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
        user= Singletion.getInstance().getUser();
        if(user.getHeadurl().length()<24){
            Glide.with(this).load(URL + SHOWIMGS+"?name=" +user.getHeadurl()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(avatar);
        }else {
            Glide.with(this).load(user.getHeadurl()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(avatar);
        }
        if(user.getNick()!=null)
        nickname.setText(user.getNick());
        if(user.getAge()!=null)
            birthday.setText(user.getAge());
        if(user.getSex()!=null)
            sex.setText(user.getSex());
        if(user.getSignaturnre()!=null)
            signature.setText(user.getSignaturnre());
        if(user.getPass()!=null)
            password.setText(user.getPass());
    }

    private void initView() {
        back=findViewById(R.id.backimg);
        avatar = findViewById(R.id.avatar);
        nicknameLL = findViewById(R.id.nicknameLL);
        sexLL = findViewById(R.id.sexLL);
        birthdayLL = findViewById(R.id.birthdayLL);
        signatureLL = findViewById(R.id.signatureLL);
        passwordLL = findViewById(R.id.passwordLL);
        nickname = findViewById(R.id.nickname);
        sex = findViewById(R.id.sex);
        birthday = findViewById(R.id.birthday);
        signature = findViewById(R.id.signature);
        password = findViewById(R.id.password);
        avatar.setOnClickListener(this);
        nicknameLL.setOnClickListener(this);
        sexLL.setOnClickListener(this);
        birthdayLL.setOnClickListener(this);
        signatureLL.setOnClickListener(this);
        passwordLL.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.avatar:
                ImageSelector.builder()
                        .useCamera(true) // 设置是否使用拍照
                        .setSingle(true)  //设置是否单选
                        .canPreview(true) //是否可以预览图片，默认为true
                        .start(EditProfileActivity.this, REQUEST_CODE); // 打开相册
                break;
            case R.id.nicknameLL:
                showDuialog("输入昵称",user.getNick(),0);
                break;
            case R.id.sexLL:
                showDuialog("选择性别",user.getSex(),1);
                break;
            case R.id.birthdayLL:
                showDuialog("选择年龄",user.getAge(),2);
                break;
            case R.id.signatureLL:
                showDuialog("输入签名",user.getSignaturnre(),3);
                break;
            case R.id.passwordLL:
                showDuialog("输入密码",user.getPass(),4);
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && data != null) {
            //获取选择器返回的数据
           ArrayList<String> selectimg = data.getStringArrayListExtra(
                    ImageSelector.SELECT_RESULT);
           if(selectimg.size()>0) {
               Map<String,String> map=new HashMap<>();
               map.put("id",user.getId());
               map.put("key","234243");
               ArrayList<File> files=new ArrayList<>();
               files.add(new File(selectimg.get(0)));
               Data_Access.sendMultipart(URL + ADDIMGHS, map, "file", files, new NetworkCallback() {
                   @Override
                   public Object parseNetworkResponse(Response response) {
                       return null;
                   }

                   @Override
                   public void onError(Call call, Exception e) {

                   }

                   @Override
                   public void onResponse(Object response) {
                       Map<String,String> map=new HashMap<>();
                       map.put("id",user.getId());
                       Data_Access.AccessStringDate(URL + GETUSERBYID, map, new NetworkCallback() {
                           @Override
                           public Object parseNetworkResponse(Response response) {
                               TypeToken<User> userTypeToken=new TypeToken<User>(){};
                               Gson gson=new Gson();
                               User user2= null;
                               try {
                                   user2 = gson.fromJson(response.body().string(),userTypeToken.getType());
                               } catch (IOException e) {
                                   e.printStackTrace();
                               }
                               Singletion.getInstance().setUser(user2);
                               user=user2;
                               runOnUiThread(new Runnable() {
                                   @Override
                                   public void run() {
                                       initShow();
                                   }
                               });
                               return null;
                           }

                           @Override
                           public void onError(Call call, Exception e) {

                           }

                           @Override
                           public void onResponse(Object response) {

                           }
                       });
                   }
               });
           }
        }
    }
    private void showDuialog(String strtitle, String nick, int i) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle(strtitle);
        EditText editText=new EditText(this);
        EditText editText2=new EditText(this);
        LinearLayout linearLayout=new LinearLayout(this);
        switch (i){
            case 0:
                if(nick!=null)
                editText.setHint(nick);
                builder.setView(editText).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String nickd=editText.getText().toString().trim();
                        if(!nickd.equals("")){
                            user.setNick(nickd);
                            updateUser();
                            dialog.dismiss();
                        }
                    }
                }).show();
                break;
            case 1:
                String[] str=getResources().getStringArray(R.array.sex_string);
                ArrayList<String> strings=new ArrayList<>();
                for (int k=0;k<str.length;k++){
                    strings.add(str[k]);
                }
                OptionsPickerView pvOptions = new OptionsPickerBuilder(EditProfileActivity.this, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                        //返回的分别是三个级别的选中位置
                        String tx = str[options1];
                        user.setSex(tx);
                        updateUser();
                    }
                }).build();
                pvOptions.setPicker(strings);
                pvOptions.show();
                break;
            case 2:
                TimePickerView pvTime = new TimePickerBuilder(EditProfileActivity.this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        user.setAge(TimeUitl.DataToStringyear(date));
                        updateUser();
                    }
                }).build();
                pvTime.show();
                break;
            case 3:
                if(nick!=null)
                    editText.setHint(nick);
                builder.setView(editText).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String nickd=editText.getText().toString().trim();
                        if(!nickd.equals("")){
                            user.setSignaturnre(nickd);
                            updateUser();
                            dialog.dismiss();
                        }
                    }
                }).show();
                break;
            case 4:
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                editText.setHint("输入新密码");
                editText2.setHint("确认新密码");
                editText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                editText2.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                linearLayout.addView(editText);
                linearLayout.addView(editText2);
                builder.setView(linearLayout).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String nickd=editText.getText().toString().trim();
                        String nickd2=editText2.getText().toString().trim();
                        if(!nickd.equals("")&&!nickd2.equals("")){
                            if(nickd.equals(nickd2)){
                                user.setPass(nickd);
                                updateUser();
                                dialog.dismiss();
                            }else {
                                Toast.makeText(EditProfileActivity.this,"修改失败密码不一致",Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }).show();
                break;
        }
    }

    private void updateUser() {
        Data_Access.AccessJSONDate(URL + UPSATEUSER, new Gson().toJson(user), new NetworkCallback() {
            @Override
            public Object parseNetworkResponse(Response response) {
                TypeToken<User> userTypeToken=new TypeToken<User>(){};
                Gson gson=new Gson();
                User user2= null;
                try {
                    user2 = gson.fromJson(response.body().string(),userTypeToken.getType());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Singletion.getInstance().setUser(user2);
                user=user2;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initShow();
                    }
                });
                return null;
            }

            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(Object response) {

            }
        });
    }
}
