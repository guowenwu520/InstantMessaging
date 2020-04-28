package com.cd.myluntan.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.cd.myluntan.R;
import com.cd.myluntan.entrty.User;
import com.cd.myluntan.ui.activity.PersonalActivity;
import com.cd.myluntan.utils.Singletion;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;

public class ContactListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = ContactListAdapter.class.getCanonicalName();
    private Context context;
    private ArrayList<User> contacts = new ArrayList<>();

    public ContactListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_contact_item, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ContactViewHolder) {
            ((ContactViewHolder) holder).onBindDate(contacts.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public void replaceAll(ArrayList<User> data) {
        if (data != null && data.size() > 0) {
            this.contacts.clear();
            this.contacts.addAll(data);
        }
        notifyDataSetChanged();
    }

    public void addData(int position, ArrayList<User> data) {
        this.contacts.addAll(position, data);
        notifyItemInserted(position);
    }

    public void removeData(int position) {
        this.contacts.remove(position);
        notifyItemRemoved(position);
    }

    class ContactViewHolder extends RecyclerView.ViewHolder {
        private ImageView avatarImg;
        private TextView username, signature, attention;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            avatarImg = itemView.findViewById(R.id.avatarImg);
            username = itemView.findViewById(R.id.username);
            signature = itemView.findViewById(R.id.signature);
            attention = itemView.findViewById(R.id.attention);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Log.d(TAG, "itemView======onClick=======contacts  " + contacts.size() + "===" + position);
                    Singletion.getInstance().setOtherUser(contacts.get(position));
                    context.startActivity(new Intent(context, PersonalActivity.class));
                }
            });
            attention.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Toast.makeText(context, "点击了关注！", Toast.LENGTH_SHORT).show();
                    if (contacts.get(position).getIsFollow() == User.TYPE_NOT_IS_FOLLOW) {
                        try {
                            EMClient.getInstance().contactManager().addContact(contacts.get(position).getName(), null);
                            contacts.get(position).setIsFollow(User.TYPE_IS_FOLLOW);
                            onBindDate(contacts.get(position));
                            notifyItemChanged(position);
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                        }
                    } else if (contacts.get(position).getIsFollow() == User.TYPE_IS_FOLLOW) {
                        try {
                            EMClient.getInstance().contactManager().deleteContact(contacts.get(position).getName());
                            contacts.get(position).setIsFollow(User.TYPE_NOT_IS_FOLLOW);
                            onBindDate(contacts.get(position));
                            notifyItemChanged(position);
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }

        public void onBindDate(User user) {
            Log.d(TAG,"onBindDate==="+user.toString());
            Glide.with(context).load(user.getHeadurl()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(this.avatarImg);
            this.username.setText(user.getName());
            this.signature.setText(user.getSignaturnre());
            if (user.getIsFollow()== User.TYPE_IS_FOLLOW){
                attention.setText("已关注");
                attention.setTextColor(context.getResources().getColor(R.color.content_grey));
            }else {
                attention.setText("关注");
                attention.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            }
        }
    }
}
