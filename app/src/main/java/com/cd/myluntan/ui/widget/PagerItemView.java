package com.cd.myluntan.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cd.myluntan.R;

public class PagerItemView extends LinearLayout {
    private final String TAG = PagerItemView.class.getCanonicalName();
    private TextView title, text;
    private Context context;

    public PagerItemView(Context context) {
        this(context, null);
    }

    public PagerItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    private void initView() {
        LayoutInflater.from(context).inflate(R.layout.view_pager_item, this, true);
        title = findViewById(R.id.title);
        text = findViewById(R.id.text);
    }

    public void bindView(String s) {
        title.setText(s);
        text.setText(s);
    }
}