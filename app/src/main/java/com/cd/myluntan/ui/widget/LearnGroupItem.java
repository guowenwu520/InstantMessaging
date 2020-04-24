package com.cd.myluntan.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cd.myluntan.R;
import com.cd.myluntan.entrty.LearnGroup;

public class LearnGroupItem extends RelativeLayout {
    private Context context;
    private TextView title, more, noMore;
    private RecyclerView recyclerView;

    private Boolean isScroll = false;
    private static Onclitkt onclitkt;

    private LearnGroup learnGroup;

    public LearnGroupItem(Context context) {
        this(context, null);
    }

    public LearnGroupItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    private void initView() {
        LayoutInflater.from(context).inflate(R.layout.view_learn_group_item, this, true);
        title = findViewById(R.id.title);
        more = findViewById(R.id.more);
        noMore = findViewById(R.id.noMore);
        recyclerView = findViewById(R.id.recyclerView);
        more.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (learnGroup!=null){
                    Toast.makeText(context,learnGroup.getTitle(),Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context,"没有更多信息",Toast.LENGTH_SHORT).show();
                }
//                onclitkt.onitem();
            }
        });
    }


    public void bindData(LearnGroup learnGroup) {
        this.learnGroup=learnGroup;
        title.setText(learnGroup.getTitle());
    }

    public void bindRecyclerView(RecyclerView.Adapter adapter) {
        recyclerView.setNestedScrollingEnabled(isScroll);
        recyclerView.setVisibility(VISIBLE);
        noMore.setVisibility(GONE);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new GroupAdapter(adapter));
    }


    public Boolean getScroll() {
        return isScroll;
    }

    public void setScroll(Boolean scroll) {
        isScroll = scroll;
    }

    class GroupAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private RecyclerView.Adapter adapter;

        public GroupAdapter(RecyclerView.Adapter adapter) {
            this.adapter = adapter;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return adapter.onCreateViewHolder(parent, viewType);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            adapter.onBindViewHolder(holder, position);
        }

        @Override
        public int getItemCount() {
            return adapter.getItemCount();
        }
    }

    public static void setOnclitkt(Onclitkt onclitkt2) {
        onclitkt = onclitkt2;
    }

    public static interface Onclitkt {
        public void onitem();
    }
}
