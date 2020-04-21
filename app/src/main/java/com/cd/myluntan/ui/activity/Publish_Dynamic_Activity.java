package com.cd.myluntan.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cd.myluntan.R;
import com.cd.myluntan.utils.WindowUitls;

public class Publish_Dynamic_Activity extends AppCompatActivity {
     private  int maxNum=1024;
    ImageView back;
    TextView topTitle,sendDynamic,Dyamic_msg_twxt,textnumber,label_select;
    RecyclerView recycle_img;
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
        sendDynamic.setVisibility(View.VISIBLE);
        //发布动态
        sendDynamic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
