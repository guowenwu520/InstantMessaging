package com.cd.myluntan;

import androidx.fragment.app.Fragment;

import com.cd.myluntan.ui.fragment.DynamicFragment;
import com.cd.myluntan.ui.fragment.HomeFragment;
import com.cd.myluntan.ui.fragment.InformationFragment;
import com.cd.myluntan.ui.fragment.LearnFragment;
import com.cd.myluntan.ui.fragment.MineFragment;

public class FragmentFactory {
    private HomeFragment homeFragment=null;
    private LearnFragment learnFragment=null;
    private DynamicFragment dynamicFragment=null;
    private InformationFragment informationFragment=null;
    private MineFragment mineFragment=null;
    

    private static final FragmentFactory instance=new FragmentFactory();
    private FragmentFactory(){}
    public static FragmentFactory getInstance()
    {
        return instance;
    }

    public Fragment getFragment(int tabId){
        switch (tabId){
            case R.id.radioButton1:
                if (homeFragment==null){
                    homeFragment=new HomeFragment();
                }
                return homeFragment;
            case R.id.radioButton2:
                if (learnFragment==null){
                    learnFragment=new LearnFragment();
                }
                return learnFragment;
            case R.id.radioButton3:
                if (dynamicFragment==null){
                    dynamicFragment=new DynamicFragment();
                }
                return dynamicFragment;
            case R.id.radioButton4:
                if (informationFragment==null){
                    informationFragment=new InformationFragment();
                }
                return informationFragment;
            case R.id.radioButton5:
                if (mineFragment==null){
                    mineFragment=new MineFragment();
                }
                return mineFragment;
        }
        return null;
    }
}
