package com.cd.myluntan.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cd.myluntan.R;
import com.hyphenate.chat.EMConversation;

import java.util.ArrayList;

public class InformationListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG =InformationListAdapter.class.getCanonicalName();

    private static final int TYPE_INFORMATION_FUNCTION = 0;
    private static final int TYPE_INFORMATION_CHAT = 1;

    private Context context;
    private ArrayList<EMConversation> tests=new ArrayList<>();

    public InformationListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        Log.d(TAG,"position=="+position);
        return position == 0 ? TYPE_INFORMATION_FUNCTION : TYPE_INFORMATION_CHAT;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        Log.d(TAG,"viewType=="+viewType);
        if (viewType == TYPE_INFORMATION_FUNCTION) {
            Log.d(TAG,"=======");
            view = LayoutInflater.from(context).inflate(R.layout.information_function_item_view, parent, false);
            return new InformationFunctionViewHolder(view);
        } else if (viewType == TYPE_INFORMATION_CHAT) {
            view = LayoutInflater.from(context).inflate(R.layout.information_chat_item_view, parent, false);
            return new InformationChatViewHolder(view);
        }
        return null;
    }

    public void setData(ArrayList<EMConversation> tests){
        this.tests.addAll(tests);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class InformationFunctionViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout likeFavorite,addAttention,comments;

        public InformationFunctionViewHolder(@NonNull View itemView) {
            super(itemView);
            likeFavorite =itemView.findViewById(R.id.likeFavorite);
            addAttention = itemView.findViewById(R.id.addAttention);
            comments = itemView.findViewById(R.id.comments);
            likeFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(context,);
//                    context.startActivity();
                }
            });
            addAttention.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            comments.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    class InformationChatViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView username,lastMessage,time,number;

        public InformationChatViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.img);
            username=itemView.findViewById(R.id.username);
            lastMessage=itemView.findViewById(R.id.lastMessage);
            time=itemView.findViewById(R.id.times);
            number=itemView.findViewById(R.id.number);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        public void onBindData(String s){

        }
    }
}
