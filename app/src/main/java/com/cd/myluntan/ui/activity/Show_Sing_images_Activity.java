package com.cd.myluntan.ui.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cd.myluntan.R;
import com.cd.myluntan.adapter.MyFragmentAdapter;
import com.cd.myluntan.entrty.Imgs;
import com.cd.myluntan.ui.fragment.Show_Sing_Image_Fragment;
import com.cd.myluntan.utils.Singletion;

import java.util.ArrayList;

public class Show_Sing_images_Activity extends AppCompatActivity {
 ViewPager viewPager;
 TextView showpage;
 int maxnumber=0;
 int index=0;
 ArrayList<Imgs> imgs=new ArrayList<>();
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__sing_images);
        imgs= Singletion.getInstance().getImgs();
        index=getIntent().getIntExtra("index",0);
        initView();
        initShow();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initShow() {
        ArrayList<Fragment> fragments=new ArrayList<>();

        for (int i=0;i<imgs.size();i++){
            if(!imgs.get(i).getImgurl().equals("0")) {
                maxnumber++;
                fragments.add(new Show_Sing_Image_Fragment(imgs.get(i)));
            }
        }
        viewPager.setAdapter(new MyFragmentAdapter(getSupportFragmentManager(),fragments));
        if(maxnumber<=1){
            showpage.setVisibility(View.GONE);
        }else {
            showpage.setVisibility(View.VISIBLE);
            showpage.setText((index+1)+"/"+maxnumber);
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    showpage.setText((position+1)+"/"+maxnumber);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }
    }

    private void initView() {
        viewPager=findViewById(R.id.show_img_frament);
        showpage=findViewById(R.id.showpage);
    }
}
