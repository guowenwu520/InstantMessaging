package com.cd.myluntan.presenter;

import com.cd.myluntan.Model.InformationModel;
import com.cd.myluntan.contract.InformationContract;
import com.hyphenate.chat.EMConversation;

import java.util.ArrayList;

public class InformationPresenter implements InformationContract.Presenter {
    private InformationContract.View view;
    private InformationModel informationModel=new InformationModel();
    public ArrayList<EMConversation> conversations=new ArrayList<>();

    public InformationPresenter(InformationContract.View view) {
        this.view = view;
    }

    @Override
    public void loadConversations() {
        conversations.clear();
        conversations.addAll(informationModel.loadConversations());
        view.loadConversationSuccess();
    }
}
