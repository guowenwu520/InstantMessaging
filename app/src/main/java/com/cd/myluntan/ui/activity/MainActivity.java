package com.cd.myluntan.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.cd.myluntan.contract.MainContract;
import com.cd.myluntan.ui.fragment.BaseFragment;
import com.cd.myluntan.utils.DisplayUtils;
import com.cd.myluntan.FragmentFactory;
import com.cd.myluntan.R;
import com.cd.myluntan.utils.ToolAnimation;
import com.cd.myluntan.interfaceo.BottomUpdateCallback;
import com.cd.myluntan.ui.fragment.HomeTabFragment;
import com.cd.myluntan.utils.WindowUitls;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.jaeger.library.StatusBarUtil;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = MainActivity.class.getName();

    private int index = 0;
    private ImageView dynamic_img;
    private RelativeLayout bottomBar;
    private RadioGroup radioGroup;
    private RadioButton radioButton1, radioButton2, radioButton3, radioButton4, radioButton5;
    private FrameLayout mainFrameLayout;

    private Boolean isBottomBarShow = true;//展示底部bar
    private Boolean isAnimationEnd = true;//展示底部bar动画是否执行完

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initRadioGroup();
        initDynamicImg();
        initBottomBarAnimation();
    }

    /**
     * 动态图标初始化
     */
    private void initDynamicImg() {
        dynamic_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButton3.setChecked(true);
                ChangIndex(0, 1);
            }
        });
    }

    /**
     * BottomBar动画初始化
     */
    private void initBottomBarAnimation() {
        BaseFragment.setBottomUpdateCallback(new BottomUpdateCallback() {
            @Override
            public void bottomBarShow(int dy) {
                bottomBarAnimation(dy);
            }

            /**
             * 动画处理
             * @param dy
             */
            private void bottomBarAnimation(int dy) {
                ObjectAnimator objectAnimator;
                if (dy > 10 && isBottomBarShow && isAnimationEnd) {
                    objectAnimator = ToolAnimation.translationHideViewY(bottomBar);
                    objectAnimator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            super.onAnimationStart(animation);
                            isAnimationEnd = false;
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            isAnimationEnd = true;
                        }
                    });
                    objectAnimator.start();
                    isBottomBarShow = false;
                } else if (dy < -10 && !isBottomBarShow && isAnimationEnd) {
                    objectAnimator = ToolAnimation.translationShowViewY(bottomBar);
                    objectAnimator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            super.onAnimationStart(animation);
                            isAnimationEnd = false;
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            isAnimationEnd = true;
                        }
                    });
                    objectAnimator.start();
                    isBottomBarShow = true;
                }
            }
        });
    }

    /**
     * BottomBar事件处理
     */
    private void initRadioGroup() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                switch (checkedId) {
                    case R.id.radioButton1:
                        fragmentManager.beginTransaction().replace(R.id.mainFrameLayout, FragmentFactory.getInstance().getFragment(R.id.radioButton1)).commit();
                        ChangIndex(1, 0);
                        break;
                    case R.id.radioButton2:
                        fragmentManager.beginTransaction().replace(R.id.mainFrameLayout, FragmentFactory.getInstance().getFragment(R.id.radioButton2)).commit();
                        ChangIndex(1, 0);
                        break;
                    case R.id.radioButton3:
                        fragmentManager.beginTransaction().replace(R.id.mainFrameLayout, FragmentFactory.getInstance().getFragment(R.id.radioButton3)).commit();
                        ChangIndex(0, 1);
                        break;
                    case R.id.radioButton4:
                        fragmentManager.beginTransaction().replace(R.id.mainFrameLayout, FragmentFactory.getInstance().getFragment(R.id.radioButton4)).commit();
                        ChangIndex(1, 0);
                        break;
                    case R.id.radioButton5:
                        fragmentManager.beginTransaction().replace(R.id.mainFrameLayout, FragmentFactory.getInstance().getFragment(R.id.radioButton5)).commit();
                        ChangIndex(1, 0);
                        break;
                }
            }
        });
        radioButton1.setChecked(true);
    }

    private void ChangIndex(int i, int i1) {
        if (i == index) {
            index = i1;
            ToolAnimation.MyRotateAnimation(dynamic_img, index, this);
        }

    }


    /**
     * 初始化视图
     */
    private void initView() {
        dynamic_img = findViewById(R.id.dynamiv_img);
        bottomBar = findViewById(R.id.bottomBar);
        mainFrameLayout = findViewById(R.id.mainFrameLayout);
        radioGroup = findViewById(R.id.radioGroup);
        radioButton1 = findViewById(R.id.radioButton1);
        radioButton2 = findViewById(R.id.radioButton2);
        radioButton3 = findViewById(R.id.radioButton3);
        radioButton4 = findViewById(R.id.radioButton4);
        radioButton5 = findViewById(R.id.radioButton5);
        initDrawable(radioButton1);
        initDrawable(radioButton2);
        initDrawable(radioButton4);
        initDrawable(radioButton5);
    }

    /**
     * 重新计算 RadioButton背景大小
     * @param v
     */
    public void initDrawable(RadioButton v) {
        Drawable drawable = v.getCompoundDrawables()[1];
        drawable.setBounds(0, 0, DisplayUtils.dp2px(this, 30), DisplayUtils.dp2px(this, 30));
        v.setCompoundDrawables(null, drawable, null, null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
