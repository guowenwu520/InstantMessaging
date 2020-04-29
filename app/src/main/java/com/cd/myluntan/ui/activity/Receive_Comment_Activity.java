package com.cd.myluntan.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cd.myluntan.R;
import com.cd.myluntan.utils.WindowUitls;

public class Receive_Comment_Activity extends AppCompatActivity {

    private ImageView backimg;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive__comment);
        WindowUitls.setColorTopBar(this, R.color.white);
        WindowUitls.setColorTextTopBarBlack(this);
        initview();
    }

    private void initview() {
        backimg=findViewById(R.id.backimg);
        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        textView=findViewById(R.id.toptitle);
        textView.setText("收到点赞和收藏");
    }
}
