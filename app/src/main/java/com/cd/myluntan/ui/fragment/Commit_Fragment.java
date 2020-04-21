package com.cd.myluntan.ui.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cd.myluntan.R;
import com.cd.myluntan.adapter.Commit_Adapter;
import com.cd.myluntan.adapter.Praise_Adapter;
import com.cd.myluntan.entrty.Comment;
import com.cd.myluntan.entrty.Praise;
import com.cd.myluntan.interfaceo.OnClicktitem;
import com.cd.myluntan.ui.activity.Dynamic_Details_Activity;
import com.cd.myluntan.utils.Singletion;
import com.cd.myluntan.utils.WindowUitls;

import java.util.ArrayList;

import static com.cd.myluntan.data_connection.Global_Url_Parameters.ISCOMMIT;
import static com.cd.myluntan.data_connection.Global_Url_Parameters.NOCOMMIT;

public class Commit_Fragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<Comment> comments=new ArrayList<>();
    Commit_Adapter commit_adapter;
    EditText textView;
   public static  Comment publiccomment;
    public  static  int publick;
    public  static   boolean ISCOMMITBACK=false;
    public Commit_Fragment(ArrayList<Comment> comments, EditText text_mssg) {
        this.comments=comments;
        textView=text_mssg;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.commit_fragment, container, false);
        initView(view);initData();
        return view;
    }

    private void initData() {
        commit_adapter=new Commit_Adapter(getContext(),comments);
        recyclerView.setAdapter(commit_adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        commit_adapter.setOnClisterItem(new OnClicktitem() {
            @Override
            public void OnClick(View.OnClickListener onClickListener, int k) {
                  Comment comment=comments.get(k);
                  textView.setHint("回复 "+comment.getUsered().getName());
               UpDataSTATUS();
                 publick=k;
                  publiccomment=comment;

                WindowUitls.setOpenInput(textView,getContext());
            }

            @Override
            public void OnLongClick(View.OnLongClickListener onLongClickListener, int k) {
                AlertDialog.Builder alertDialog=new AlertDialog.Builder(getContext());
                alertDialog.setTitle("提示");
                alertDialog.setMessage("确认删除？").setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        comments.remove(k);
                        setDataAndFinal(comments);
                        dialog.dismiss();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
            }
        });
    }

    private void UpDataSTATUS() {
        ISCOMMITBACK=true;
    }

    private void initView(View view) {
        recyclerView=view.findViewById(R.id.commit_recycleview);
    }


    public void setDataAndFinal(ArrayList<Comment> comments) {
        this.comments=comments;
        commit_adapter.setDataAndFinal(comments);
    }
}