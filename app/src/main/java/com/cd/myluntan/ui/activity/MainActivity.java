package com.cd.myluntan.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.cd.myluntan.contract.MainContract;
import com.cd.myluntan.ui.fragment.BaseFragment;
import com.cd.myluntan.utils.DisplayUtils;
import com.cd.myluntan.FragmentFactory;
import com.cd.myluntan.R;
import com.cd.myluntan.utils.ToolAnimation;
import com.cd.myluntan.interfaceo.InterfaceCall;
import com.cd.myluntan.ui.fragment.HomeTabFragment;
import com.jaeger.library.StatusBarUtil;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = MainActivity.class.getName();

    private CardView bottomBar;
    private RadioGroup radioGroup;
    private RadioButton radioButton1, radioButton2, radioButton3, radioButton4, radioButton5;
    private FrameLayout mainFrameLayout;
    private Boolean isBottomBarVisibility=true;
    private ToolAnimation toolAnimation = new ToolAnimation();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initRadioGroup();
        BaseFragment.setInterfaceCall(new InterfaceCall() {
            @Override
            public void bottomBarShow(int dy) {
                if (dy > 5 && isBottomBarVisibility && toolAnimation.isTranslationShowAnimation) {
                    toolAnimation.translationHideViewY(bottomBar);
                    isBottomBarVisibility = false;
                } else if (dy < -5 && !isBottomBarVisibility && toolAnimation.isTranslationShowAnimation) {
                    toolAnimation.translationShowViewY(bottomBar);
                    isBottomBarVisibility = true;
                }
            }
        });
    }

    private void initRadioGroup() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                switch (checkedId) {
                    case R.id.radioButton1:
                        fragmentManager.beginTransaction().replace(R.id.mainFrameLayout, FragmentFactory.getInstance().getFragment(R.id.radioButton1)).commit();
                        break;
                    case R.id.radioButton2:
                        fragmentManager.beginTransaction().replace(R.id.mainFrameLayout, FragmentFactory.getInstance().getFragment(R.id.radioButton2)).commit();
                        break;
                    case R.id.radioButton3:
                        fragmentManager.beginTransaction().replace(R.id.mainFrameLayout, FragmentFactory.getInstance().getFragment(R.id.radioButton3)).commit();
                        break;
                    case R.id.radioButton4:
                        fragmentManager.beginTransaction().replace(R.id.mainFrameLayout, FragmentFactory.getInstance().getFragment(R.id.radioButton4)).commit();
                        break;
                    case R.id.radioButton5:
                        fragmentManager.beginTransaction().replace(R.id.mainFrameLayout, FragmentFactory.getInstance().getFragment(R.id.radioButton5)).commit();
                        break;
                }
            }
        });
        radioButton1.setChecked(true);
    }


    private void initView() {
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
        initDrawable(radioButton3);
        initDrawable(radioButton4);
        initDrawable(radioButton5);
    }

    public void initDrawable(RadioButton v) {
        Drawable drawable = v.getCompoundDrawables()[1];
        drawable.setBounds(0, 0, DisplayUtils.dp2px(this, 30), DisplayUtils.dp2px(this, 30));
        v.setCompoundDrawables(null, drawable, null, null);
    }

}
