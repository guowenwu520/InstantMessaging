package com.cd.myluntan.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cd.myluntan.data_connection.Data_Access;
import com.cd.myluntan.entrty.Dynamic;
import com.cd.myluntan.entrty.Praise;
import com.cd.myluntan.entrty.User;
import com.cd.myluntan.interfaceo.OnClicktitem;
import com.cd.myluntan.ui.activity.PlayMp4_Activity;
import com.cd.myluntan.ui.widget.HomeItemView;
import com.cd.myluntan.utils.Singletion;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.cd.myluntan.data_connection.Global_Url_Parameters.ADDPRAISE;
import static com.cd.myluntan.data_connection.Global_Url_Parameters.DELETEPRAISE;
import static com.cd.myluntan.data_connection.Global_Url_Parameters.URL;

public class SearchListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<Dynamic> searchs;
    private User myUser;
    public SearchListAdapter(Context context) {
        this.context = context;
        this.searchs = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeViewHolder(new HomeItemView(context));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        HomeItemView homeItemView = (HomeItemView) holder.itemView;
        homeItemView.bindData(searchs.get(position), position, new OnClicktitem() {
            @Override
            public void OnClick(View.OnClickListener onClickListener, int k) {
                addPraise(searchs.get(k), k);
            }

            @Override
            public void OnLongClick(View.OnLongClickListener onLongClickListener, int k) {
                rmPraise(searchs.get(k), k);
            }
        });
    }

    @Override
    public int getItemCount() {
        return searchs.size();
    }

    public void replaceAll(ArrayList<Dynamic> data) {
        this.searchs.clear();
        if (data != null && data.size() > 0) {
            this.searchs.addAll(data);
        }
        notifyDataSetChanged();
    }

    public void addData(int position, ArrayList<Dynamic> data) {
        if (data != null && data.size() > 0) {
            this.searchs.addAll(position, data);
            notifyItemInserted(position);
        }
    }

    private void rmPraise(Dynamic dynamic, int pstin) {
        ArrayList<Praise> praises = dynamic.getPraises();
        for (int i = 0; i < praises.size(); i++) {
            if (myUser.getId().equals(praises.get(i).getUser().getId())) {
                Map<String, String> map = new HashMap<>();
                map.put("id", praises.get(i).getId());
                Data_Access.AccessStringDate(URL + DELETEPRAISE, map, null);
                praises.remove(i);
                break;
            }
        }
        searchs.get(pstin).setPraises(praises);
        notifyItemChanged(pstin);
    }

    private void addPraise(Dynamic dynamic, int psin) {
        Praise praise = new Praise();
        praise.setUser(myUser);
        praise.setDynamicid(dynamic.getId());
        praise.setId(System.currentTimeMillis() + "");
        praise.setUserid(myUser.getId());
        Data_Access.AccessJSONDate(URL + ADDPRAISE, new Gson().toJson(praise), null);
        searchs.get(psin).getPraises().add(0, praise);
        notifyItemChanged(psin);
    }

    class HomeViewHolder extends RecyclerView.ViewHolder {

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Singletion.getInstance().setIndex(getLayoutPosition());
                    Singletion.getInstance().setDynamics(searchs);
                    Intent intent = new Intent(context, PlayMp4_Activity.class);
                    context.startActivity(intent);
                }
            });
        }
    }
}
