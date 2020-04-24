package com.cd.myluntan.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.cd.myluntan.R;
import com.cd.myluntan.utils.DisplayUtils;

import java.util.ArrayList;

public class CarouselItemView extends RelativeLayout {
    private Context context;
    private ViewPager viewPager;
    private LinearLayout ll_point_group;

    private ArrayList<String> pagerListItems;
    /**
     * 上次高亮显示的位置
     */
    private int prePosition = 0;
    /**
     * 是否已经滚动
     */
    private boolean isDragging = false;

    public CarouselItemView(Context context) {
        this(context, null);
    }

    public CarouselItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
        initViewPager();
    }

    private void initView() {
        LayoutInflater.from(context).inflate(R.layout.view_carousel_item, this, true);
        viewPager = findViewById(R.id.viewPager);
        ll_point_group = findViewById(R.id.ll_point_group);
    }

    private void initViewPager() {
        //ViewPager的使用
        //1.在布局文件中定义ViewPager
        //2.在代码中实例化ViewPager
        //3.准备数据
        pagerListItems = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            //添加到集合中
            pagerListItems.add(i + "");
            //添加点
            ImageView point = new ImageView(context);
            point.setBackgroundResource(R.drawable.point_selector);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DisplayUtils.dp2px(context, 8), DisplayUtils.dp2px(context, 8));
            if (i == prePosition) {
                point.setEnabled(false);
            } else {
                point.setEnabled(true);
                params.leftMargin = DisplayUtils.dp2px(context, 20);
            }
            point.setLayoutParams(params);
            ll_point_group.addView(point);
        }
        //4.设置适配器(PagerAdapter)-item布局-
        viewPager.setAdapter(new MyPagerAdapter());
        //设置监听viewPager的页面改变
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());
        //设置中间位置
        int item = Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % pagerListItems.size();//要保证集合整数倍
        viewPager.setCurrentItem(item);

        //发消息
        handler.sendEmptyMessageDelayed(0, 3000);
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            int item = viewPager.getCurrentItem() + 1;
            viewPager.setCurrentItem(item);

            //延迟发消息
            handler.sendEmptyMessageDelayed(0, 3000);
        }
    };

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        /**
         * 当页面滚动了的时候回调这个方法
         *
         * @param position             当前页面的位置
         * @param positionOffset       滑动页面的百分比
         * @param positionOffsetPixels 在屏幕上滑动的像素
         */
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        /**
         * 当某个页面被选中了的时候回调
         *
         * @param position 被选中页面的位置
         */
        @Override
        public void onPageSelected(int position) {
            //正真位置
            int realPosition = position % pagerListItems.size();
            //把上个高亮设置未默认
            ll_point_group.getChildAt(prePosition).setEnabled(true);
            //当前位置设置为高亮
            ll_point_group.getChildAt(realPosition).setEnabled(false);
            prePosition = realPosition;
        }

        /**
         * 当页面滚动状态变化的时候回调这个方法
         *
         * @param state 静止-->滑动-->静止-->拖拽
         */
        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_DRAGGING) {//拖拽
                isDragging = true;
                handler.removeCallbacksAndMessages(null);//移出handler消息队列
            } else if (state == ViewPager.SCROLL_STATE_SETTLING) {//选中

            } else if (state == ViewPager.SCROLL_STATE_IDLE && isDragging) {//静止
                isDragging = false;
                setHandler();
            }
        }
    }

    private void setHandler() {
        handler.removeCallbacksAndMessages(null);//移出handler消息队列
        handler.sendEmptyMessageDelayed(0, 3000);
    }

    /**
     * viewPager适配器
     */
    class MyPagerAdapter extends PagerAdapter {

        /**
         * 得到数据总数
         *
         * @return 数据总数
         */
        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        /**
         * 相当于getView方法
         *
         * @param container viewPager自身
         * @param position  当前实例化页面的位置
         * @return
         */
        @SuppressLint("ClickableViewAccessibility")
        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            //正真位置
            int realPosition = position % pagerListItems.size();
            //1.实例化
            PagerItemView pagerItemView = new PagerItemView(container.getContext());
            pagerItemView.bindView(pagerListItems.get(realPosition));
            //2.添加到ViewPager中
            container.addView(pagerItemView);

            //设置触摸时件
            pagerItemView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN://手指按下
                            handler.removeCallbacksAndMessages(null);//移出handler消息队列
                            break;
                        case MotionEvent.ACTION_MOVE://手指移动
                            break;
                        case MotionEvent.ACTION_CANCEL://事件取消
//                            handler.removeCallbacksAndMessages(null);//移出handler消息队列
//                            handler.sendEmptyMessageDelayed(0, delayMillis);
                            break;
                        case MotionEvent.ACTION_UP://手指移开
                            setHandler();
                            break;
                    }
                    return true;
                }
            });

            return pagerItemView;
        }

        /**
         * 比较view和object是否是同一个实例
         *
         * @param view   页面
         * @param object instantiateItem（）返回的结果
         * @return
         */
        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        /**
         * 释放资源
         *
         * @param container viewPager
         * @param position  要释放的位置
         * @param object    要释放的页面
         */
        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//            super.destroyItem(container, position, object);
            container.removeView((View) object);
        }
    }
}
