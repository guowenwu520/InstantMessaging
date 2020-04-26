package com.cd.myluntan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cd.myluntan.R;
import com.cd.myluntan.data_connection.Data_Access;
import com.cd.myluntan.entrty.Dynamic;
import com.cd.myluntan.entrty.Home;
import com.cd.myluntan.entrty.Praise;
import com.cd.myluntan.entrty.User;
import com.cd.myluntan.interfaceo.BottomUpdateCallback;
import com.cd.myluntan.interfaceo.OnClicktitem;
import com.cd.myluntan.ui.widget.HomeItemView;
import com.cd.myluntan.utils.Singletion;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.cd.myluntan.data_connection.Global_Url_Parameters.ADDPRAISE;
import static com.cd.myluntan.data_connection.Global_Url_Parameters.DELETEPRAISE;
import static com.cd.myluntan.data_connection.Global_Url_Parameters.URL;

public class HomeTabListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = HomeTabListAdapter.class.getCanonicalName();
    private ArrayList<Dynamic> homes = new ArrayList<>();
    private Context context;
    private User myUser;

    public HomeTabListAdapter(Context context) {
        this.context = context;
        myUser= Singletion.getInstance().getUser();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.view_home_item, parent, false);
        return new HomeTabListViewHolder(new HomeItemView(context));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HomeTabListViewHolder){
            HomeItemView homeItemView= (HomeItemView) holder.itemView;
            homeItemView.bindData(homes.get(position), position, new OnClicktitem() {
                @Override
                public void OnClick(View.OnClickListener onClickListener, int k) {
                    addPraise(homes.get(k),k);
                }

                @Override
                public void OnLongClick(View.OnLongClickListener onLongClickListener, int k) {
                    rmPraise(homes.get(k),k);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return homes.size();
    }

    class HomeTabListViewHolder extends RecyclerView.ViewHolder {

        public HomeTabListViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,homes.get(getLayoutPosition()).getMag(),Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    private void rmPraise(Dynamic dynamic,int pstin) {
        ArrayList<Praise> praises=dynamic.getPraises();
        for (int i=0;i<praises.size();i++){
            if(myUser.getId().equals(praises.get(i).getUser().getId())){
                Map<String,String> map=new HashMap<>();
                map.put("id",praises.get(i).getId());
                Data_Access.AccessStringDate(URL+DELETEPRAISE,map,null);
                praises.remove(i);
                break;
            }
        }
        //dynamic.setPraises(praises);
        // Singletion.getInstance().setDynamic(dynamic);
        homes.get(pstin).setPraises(praises);
        notifyItemChanged(pstin);
    }

    private void addPraise(Dynamic dynamic,int psin) {
        Praise praise=new Praise();
        praise.setUser(myUser);
        praise.setDynamicid(dynamic.getId());
        praise.setId(System.currentTimeMillis()+"");
        praise.setUserid(myUser.getId());
        // dynamic.getPraises().add(0,praise);
        // Singletion.getInstance().setDynamic(dynamic);
        Data_Access.AccessJSONDate(URL+ADDPRAISE,new Gson().toJson(praise),null);
        homes.get(psin).getPraises().add(0,praise);
        notifyItemChanged(psin);
    }
    //刷新数据
    public void replaceAll(ArrayList<Dynamic> list) {
        homes.clear();
        if (list != null && list.size() > 0) {
            homes.addAll(list);
        }
        notifyDataSetChanged();
    }

    //添加数据
    public void addData(int position, ArrayList<Dynamic> list) {
        homes.addAll(position, list);
        notifyItemInserted(position);
    }

    //移除数据使用notifyItemRemoved
    public void removeData(int position) {
        homes.remove(position);
        notifyItemRemoved(position);
    }

    public void setData(ArrayList<Dynamic> datas) {
        homes.addAll(datas);
        notifyDataSetChanged();
    }
}
