package com.cd.myluntan.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;

import com.cd.myluntan.R;

public class ToolAnimation {
    private static final String TAG = ToolAnimation.class.getCanonicalName();

    public boolean isTranslationShowAnimation = true; //上次平移动画是否执行完成

    /**
     * Y平移动画
     *
     * @param view 添加动画的视图
     */
    public static ObjectAnimator translationHideViewY(View view) {
        return translationHideViewY(view, 400L);
    }

    /**
     * Y平移动画
     *
     * @param view     添加动画的视图
     * @param duration 设置动画运行的时长
     */
    public static ObjectAnimator translationHideViewY(View view, Long duration) {
        return translationHideViewY(view, duration, 0L);
    }

    /**
     * Y平移动画
     *
     * @param view       添加动画的视图
     * @param duration   设置动画运行的时长
     * @param startDelay 设置动画延迟播放时间
     */
    public static ObjectAnimator translationHideViewY(View view, Long duration, Long startDelay) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "translationY", 0f, view.getHeight());
        objectAnimator.setDuration(duration);
        objectAnimator.setStartDelay(startDelay);
        return objectAnimator;
    }

    /**
     * Y平移动画
     *
     * @param viewGroup 添加动画的视图
     */
    public static ObjectAnimator translationHideViewY(ViewGroup viewGroup) {
        return translationHideViewY(viewGroup, 400L);
    }

    /**
     * Y平移动画
     *
     * @param viewGroup 添加动画的视图
     * @param duration  设置动画运行的时长
     */
    public static ObjectAnimator translationHideViewY(ViewGroup viewGroup, Long duration) {
        return translationHideViewY(viewGroup, duration, 0L);
    }

    /**
     * Y平移动画
     *
     * @param viewGroup  添加动画的视图
     * @param duration   设置动画运行的时长
     * @param startDelay 设置动画延迟播放时间
     */
    public static ObjectAnimator translationHideViewY(ViewGroup viewGroup, Long duration, Long startDelay) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(viewGroup, "translationY", 0f, viewGroup.getHeight());
        objectAnimator.setDuration(duration);
        objectAnimator.setStartDelay(startDelay);
        return objectAnimator;
    }

    /**
     * Y平移动画
     *
     * @param view 添加动画的视图
     */
    public static ObjectAnimator translationShowViewY(View view) {
        return translationShowViewY(view, 400L);
    }

    /**
     * Y平移动画
     *
     * @param view     添加动画的视图
     * @param duration 设置动画运行的时长
     */
    public static ObjectAnimator translationShowViewY(View view, Long duration) {
        return translationShowViewY(view, duration, 0L);
    }

    /**
     * Y平移动画
     *  @param view       添加动画的视图
     * @param duration   设置动画运行的时长
     * @param startDelay 设置动画延迟播放时间
     */
    public static ObjectAnimator translationShowViewY(View view, Long duration, Long startDelay) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "translationY", (long) view.getHeight(), 0L);
        objectAnimator.setDuration(duration);
        objectAnimator.setStartDelay(startDelay);
        return objectAnimator;
    }

    /**
     * Y平移动画
     *
     * @param viewGroup 添加动画的视图
     */
    public static ObjectAnimator translationShowViewY(ViewGroup viewGroup) {
        return translationShowViewY(viewGroup, 400L);
    }

    /**
     * Y平移动画
     *
     * @param viewGroup 添加动画的视图
     * @param duration  设置动画运行的时长
     */
    public static ObjectAnimator translationShowViewY(ViewGroup viewGroup, Long duration) {
        return translationShowViewY(viewGroup, duration, 0L);
    }

    /**
     * Y平移动画
     *
     * @param viewGroup  添加动画的视图
     * @param duration   设置动画运行的时长
     * @param startDelay 设置动画延迟播放时间
     */
    public static ObjectAnimator translationShowViewY(ViewGroup viewGroup, Long duration, Long startDelay) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(viewGroup, "translationY", (long) viewGroup.getHeight(), 0L);
        objectAnimator.setDuration(duration);
        objectAnimator.setStartDelay(startDelay);
        return objectAnimator;
    }

    /**
     * 旋转
     */
    public static void MyRotateAnimation(View view, int index, Context context) {
        Animation animation=null;
        if(index==0){
            animation= AnimationUtils.loadAnimation(context, R.anim.cheang_dt);
        }else {
            animation= AnimationUtils.loadAnimation(context, R.anim.cheang_dtt);
        }
        animation.setFillAfter(true);
        LinearInterpolator linearInterpolator=new LinearInterpolator();
        animation.setInterpolator(linearInterpolator);
        view.startAnimation(animation);
    }
    /**
     * 扩大
     *
     * @param view
     */
    public static AnimatorSet scaleEnlargeView(View view) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 1.3f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 1.3f);
        //组合动画
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleX, scaleY);
        animatorSet.setDuration(400);
        return animatorSet;
    }

    /**
     * 扩大
     */
    public static AnimatorSet scaleEnlargeView(ViewGroup viewGroup) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(viewGroup, "scaleX", 0f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(viewGroup, "scaleY", 0f, 1f);
        //组合动画
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleX, scaleY);
        animatorSet.setDuration(400);
        return animatorSet;
    }
    /**
     * 扩大
     */
    public static AnimatorSet scaleEnlargeViewSplasAct(ViewGroup viewGroup) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(viewGroup, "scaleX", 1f, 1.3f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(viewGroup, "scaleY", 1f, 1.3f);
        //组合动画
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleX, scaleY);
        animatorSet.setDuration(2000);
        return animatorSet;
    }

    /**
     * 缩小组件
     *
     * @param view
     */
    public static AnimatorSet scaleNarrowView(View view) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 0f);
        //组合动画
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleX, scaleY);
        animatorSet.setDuration(400);
        return animatorSet;
    }

    /**
     * 缩小
     * @param viewGroup
     * @return
     */
    public static AnimatorSet scaleNarrowView(ViewGroup viewGroup) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(viewGroup, "scaleX", 1f, 0f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(viewGroup, "scaleY", 1f, 0f);
        //组合动画
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleX, scaleY);
        animatorSet.setDuration(400);
        return animatorSet;
    }

}
