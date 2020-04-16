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
    public void translationHideViewY(View view) {
        translationHideViewY(view, 400L);
    }

    /**
     * Y平移动画
     *
     * @param view     添加动画的视图
     * @param duration 设置动画运行的时长
     */
    public void translationHideViewY(View view, Long duration) {
        translationHideViewY(view, duration, 0L);
    }

    /**
     * Y平移动画
     *
     * @param view       添加动画的视图
     * @param duration   设置动画运行的时长
     * @param startDelay 设置动画延迟播放时间
     */
    public void translationHideViewY(View view, Long duration, Long startDelay) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "translationY", 0f, view.getHeight());
        objectAnimator.setDuration(duration);
        objectAnimator.setStartDelay(startDelay);
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                isTranslationShowAnimation = false;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isTranslationShowAnimation = true;
                Log.d(TAG, "动画结束");
            }
        });

        objectAnimator.start();
    }

    /**
     * Y平移动画
     *
     * @param viewGroup 添加动画的视图
     */
    public void translationHideViewY(ViewGroup viewGroup) {
        translationHideViewY(viewGroup, 400L);
    }

    /**
     * Y平移动画
     *
     * @param viewGroup 添加动画的视图
     * @param duration  设置动画运行的时长
     */
    public void translationHideViewY(ViewGroup viewGroup, Long duration) {
        translationHideViewY(viewGroup, duration, 0L);
    }

    /**
     * Y平移动画
     *
     * @param viewGroup  添加动画的视图
     * @param duration   设置动画运行的时长
     * @param startDelay 设置动画延迟播放时间
     */
    public void translationHideViewY(ViewGroup viewGroup, Long duration, Long startDelay) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(viewGroup, "translationY", 0f, viewGroup.getHeight());
        objectAnimator.setDuration(duration);
        objectAnimator.setStartDelay(startDelay);
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                isTranslationShowAnimation = false;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isTranslationShowAnimation = true;
                Log.d(TAG, "动画结束");
            }
        });

        objectAnimator.start();
    }

    /**
     * Y平移动画
     *
     * @param view 添加动画的视图
     */
    public void translationShowViewY(View view) {
        translationShowViewY(view, 400L);
    }

    /**
     * Y平移动画
     *
     * @param view     添加动画的视图
     * @param duration 设置动画运行的时长
     */
    public void translationShowViewY(View view, Long duration) {
        translationShowViewY(view, duration, 0L);
    }

    /**
     * Y平移动画
     *
     * @param view       添加动画的视图
     * @param duration   设置动画运行的时长
     * @param startDelay 设置动画延迟播放时间
     */
    public void translationShowViewY(View view, Long duration, Long startDelay) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "translationY", (long) view.getHeight(), 0L);
        objectAnimator.setDuration(duration);
        objectAnimator.setStartDelay(startDelay);
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                isTranslationShowAnimation = false;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isTranslationShowAnimation = true;
                Log.d(TAG, "动画结束");
            }
        });
        objectAnimator.start();
    }

    /**
     * Y平移动画
     *
     * @param viewGroup 添加动画的视图
     */
    public void translationShowViewY(ViewGroup viewGroup) {
        translationShowViewY(viewGroup, 400L);
    }

    /**
     * Y平移动画
     *
     * @param viewGroup 添加动画的视图
     * @param duration  设置动画运行的时长
     */
    public void translationShowViewY(ViewGroup viewGroup, Long duration) {
        translationShowViewY(viewGroup, duration, 0L);
    }

    /**
     * Y平移动画
     *
     * @param viewGroup  添加动画的视图
     * @param duration   设置动画运行的时长
     * @param startDelay 设置动画延迟播放时间
     */
    public void translationShowViewY(ViewGroup viewGroup, Long duration, Long startDelay) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(viewGroup, "translationY", (long) viewGroup.getHeight(), 0L);
        objectAnimator.setDuration(duration);
        objectAnimator.setStartDelay(startDelay);
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                isTranslationShowAnimation = false;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isTranslationShowAnimation = true;
                Log.d(TAG, "动画结束");
            }
        });
        objectAnimator.start();
    }

    /**
     * 缩小组件
     *
     * @param view
     */
    public void scaleNarrowView(View view) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 0f);
        //组合动画
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleX, scaleY);
        animatorSet.setDuration(400);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                isTranslationShowAnimation = false;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isTranslationShowAnimation = true;
                Log.d(TAG, "scaleViewView== 动画结束");
            }
        });
        animatorSet.start();
    }

    /**
     * 缩小组件
     *
     * @param viewGroup
     */
    public void scaleNarrowView(final ViewGroup viewGroup) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(viewGroup, "scaleX", 1f, 0f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(viewGroup, "scaleY", 1f, 0f);
        //组合动画
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleX, scaleY);
        animatorSet.setDuration(400);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                isTranslationShowAnimation = false;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                viewGroup.setVisibility(View.GONE);
                isTranslationShowAnimation = true;
                Log.d(TAG, "scaleViewView== 动画结束");
            }
        });
        animatorSet.start();
    }

    /**
     * 扩大
     *
     * @param view
     */
    public void scaleEnlargeView(View view) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 0f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 0f, 1f);
        //组合动画
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleX, scaleY);
        animatorSet.setDuration(400);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                isTranslationShowAnimation = false;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isTranslationShowAnimation = true;
                Log.d(TAG, "scaleViewView== 动画结束");
            }
        });
        animatorSet.start();
    }

    /**
     * 扩大
     *
     * @param viewGroup
     */
    public void scaleEnlargeView(final ViewGroup viewGroup) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(viewGroup, "scaleX", 0f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(viewGroup, "scaleY", 0f, 1f);
        //组合动画
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleX, scaleY);
        animatorSet.setDuration(400);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                viewGroup.setVisibility(View.VISIBLE);
                isTranslationShowAnimation = false;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isTranslationShowAnimation = true;
                Log.d(TAG, "scaleViewView== 动画结束");
            }
        });
        animatorSet.start();
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


}
