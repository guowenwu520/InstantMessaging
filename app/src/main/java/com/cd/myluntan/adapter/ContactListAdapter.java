package com.cd.myluntan.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cd.myluntan.R;

import java.util.ArrayList;

public class ContactListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = ContactListAdapter.class.getCanonicalName();
    private Context context;
    private ArrayList<String> tests=new ArrayList<>();

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
        if (holder instanceof ContactViewHolder){
            ((ContactViewHolder) holder).onBindDate(tests.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return tests.size();
    }

    public void onBindData(ArrayList<String> strings) {
        tests.addAll(strings);
    }

    public void onRefreshData() {
        tests.clear();
    }

    class ContactViewHolder extends RecyclerView.ViewHolder {
        private TextView username;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            username=itemView.findViewById(R.id.username);
        }

        public void onBindDate(String username){
            this.username.setText(username);
        }
    }
}
