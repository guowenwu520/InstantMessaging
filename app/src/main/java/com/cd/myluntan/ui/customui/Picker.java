package com.cd.myluntan.ui.customui;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cd.myluntan.R;


/**
 * Created by zhangda on 2015/9/4.
 * 自定义组件：数据选择器
 */
public class Picker{
    private Button cancelBtn;
    private ListView picker_listview;
    private String[] mDatas = {};
    private Activity mContext;
    private int selectedItemPosition = 0;
    private OnSelectDoneListener mListner;
    private Picker me;
    private RelativeLayout picker;
    public static PopupWindowFromBottom menuWindow;
    private View mParentView;

    /***
     * 构造方法
     * @param context
     * @param parentView 父view
     */
    public Picker(Activity context, View parentView, String[] datas) {
        me = this;
        mContext = context;
        mParentView = parentView;
        picker = (RelativeLayout)RelativeLayout.inflate(context,R.layout.picker_content, null);
        cancelBtn = (Button)picker.findViewById(R.id.picker_cancel);
        picker_listview = (ListView)picker.findViewById(R.id.picker_listview);
        menuWindow = new PopupWindowFromBottom(context, picker);
        bindBtnsEvent();
        refreshData(datas);
    }

    private void bindBtnsEvent() {
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuWindow.backgroundAlpha(mContext,1.0f);
                menuWindow.dismiss();
            }
        });
    }

    /***
     * 供外部调用
     * @param listener
     */
    public void setOnSelectDoneListener(OnSelectDoneListener listener){
        mListner = listener;
    }

    private void refreshData(String[] datas){
        mDatas = datas;
        final String[] dataForuse = mDatas;
        BaseAdapter pickerAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return dataForuse.length;
            }

            @Override
            public Object getItem(int i) {
                return null;
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public View getView(int i, View convertView, ViewGroup viewGroup) {
                viewHolder viewHoder = null;
                if(convertView == null){
                    viewHoder = new viewHolder();
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.item_picker_listview, null);
                    viewHoder.textView = (TextView)convertView.findViewById(R.id.company_name);
                    convertView.setTag(viewHoder);
                }else{
                    viewHoder = (viewHolder)convertView.getTag();
                }
                viewHoder.textView.setText(dataForuse[i]);
                viewHoder.textView.setTag(dataForuse[i]);

                return convertView;
            }
        };

        picker_listview.setAdapter(pickerAdapter);
        picker_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mListner.onSelectDone(i);
                menuWindow.backgroundAlpha(mContext,1.0f);
                menuWindow.dismiss();
            }
        });
    }

    private class viewHolder{
        private TextView textView;
    }

    public void show(){
        if(!menuWindow.isShowing()){
            refreshData(mDatas);
            menuWindow.showAtLocation(mParentView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
        }
//        if (menuWindow.isShowing()) {
//            menuWindow.dismiss();
//        } else {
//            selectedItemPosition = 0;
//            refreshData(mDatas);
//            menuWindow.showAtLocation(mParentView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
//        }
    }

    /***
     *
     * 选择完毕调用的监听器
     * @author zhangda
     */
    public interface OnSelectDoneListener{
        void onSelectDone(int i);
    }
}