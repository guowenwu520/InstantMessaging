package com.cd.myluntan.contract;

import com.hyphenate.chat.EMConversation;

import java.util.ArrayList;

public interface InformationContract {
    interface Model{
        ArrayList<EMConversation> loadConversations();
    }
    interface View{
        void loadConversationSuccess();
        void loadConversationFailed();
    }
    interface Presenter{
        void loadConversations();
    }
}
