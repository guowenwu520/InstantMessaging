package com.cd.myluntan.contract;

import com.hyphenate.chat.EMConversation;

import java.util.ArrayList;

public interface InformationContract {
    interface Model{
        ArrayList<EMConversation> loadConversations();
    }
    interface View{
        void loadConversationSuccess(int size);
        void loadConversationFailed();
        void refreshConversationsSuccess(int size);
    }
    interface Presenter{
        void loadConversations();
        void refreshConversations();
    }
}
