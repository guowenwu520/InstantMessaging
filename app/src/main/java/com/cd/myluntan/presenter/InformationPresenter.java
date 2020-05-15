package com.cd.myluntan.presenter;

import android.util.Log;

import com.cd.myluntan.Model.InformationModel;
import com.cd.myluntan.contract.InformationContract;
import com.hyphenate.chat.EMConversation;

import java.util.ArrayList;

public class InformationPresenter implements InformationContract.Presenter {
    private static final String TAG = InformationPresenter.class.getCanonicalName();
    private InformationContract.View view;
    private InformationModel informationModel=new InformationModel();

    private ArrayList<EMConversation> conversations;
    private int currentPage = 1;//当前页，默认第一页
    private int pages = 1;//总页数，获取服务端数据
    private int rows = 20;//每页显示行数
    private int satrt = 0;//当前下标
    private int end = 20;//结束下标

    public InformationPresenter(InformationContract.View view) {
        this.view = view;
        this.conversations=new ArrayList<>();
    }

    /**
     * 上拉刷新
     */
    @Override
    public void loadConversations() {
        ArrayList<EMConversation> data=informationModel.loadConversations();
        Log.d(TAG,"loadConversations========="+data.size()+"========"+data.toString());
        conversations.clear();
        conversations.addAll(data);
        view.loadConversationSuccess(data.size());
    }

    /**
     * 下拉刷新
     */
    @Override
    public void refreshConversations() {
        conversations.clear();
        ArrayList<EMConversation> data=informationModel.loadConversations();
        Log.d(TAG,"refreshConversations========="+data.size()+"========"+data.toString());
        conversations.addAll(data);
        view.refreshConversationsSuccess(data.size());
    }

    public ArrayList<EMConversation> getConversations() {
        return conversations;
    }

    public int getCurrentPage() {
        if (currentPage <= 1) {
            currentPage = 1;
        }
        if (currentPage >= pages) {
            currentPage = pages;
        }
        return currentPage;
    }

    public int getPages() {
        return pages;
    }

    public int getSatrt() {
        return (currentPage - 1) * rows;
    }

    public int getEnd() {
        return currentPage * rows;
    }
}
