package com.cd.myluntan.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.cd.myluntan.R;
import com.cd.myluntan.entrty.LearnMaterials;

public class LearnItemView extends RelativeLayout {
    private Context context;
    private ImageView img;
    private TextView title, author, classHour, userNum, price;

    public LearnItemView(Context context) {
        this(context, null);
    }

    public LearnItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    private void initView() {
        LayoutInflater.from(context).inflate(R.layout.view_learn_item, this, true);
        img = findViewById(R.id.img);
        title = findViewById(R.id.title);
        author = findViewById(R.id.author);
        classHour = findViewById(R.id.classHour);
        userNum = findViewById(R.id.userNum);
        price = findViewById(R.id.price);
    }

    public void bindData(LearnMaterials learnMaterials) {
        Glide.with(context).load(learnMaterials.getImage()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(img);
        title.setText(learnMaterials.getTitle());
        author.setText(learnMaterials.getAuthor());
        classHour.setText(String.valueOf(learnMaterials.getClassHour()));
        userNum.setText(String.valueOf(learnMaterials.getUserNum()));
        if (learnMaterials.getPrice()<0){
            price.setText("免费");
        }else {
            price.setText(String.valueOf(learnMaterials.getPrice()));
        }
    }
}
