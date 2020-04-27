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
    public ArrayList<EMConversation> conversations=new ArrayList<>();

    public InformationPresenter(InformationContract.View view) {
        this.view = view;
    }

    /**
     * 上拉刷新
     */
    @Override
    public void loadConversations() {
        ArrayList<EMConversation> data=informationModel.loadConversations();
        Log.d(TAG,"loadConversations========="+data.size()+"========"+data.toString());
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
}
