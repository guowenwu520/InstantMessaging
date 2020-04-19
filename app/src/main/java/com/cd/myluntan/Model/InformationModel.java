package com.cd.myluntan.Model;

import com.cd.myluntan.contract.InformationContract;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;

import java.util.ArrayList;
import java.util.Map;

public class InformationModel implements InformationContract.Model {

    /**
     * 获取会话列表
     * @return 会话列表
     */
    @Override
    public ArrayList<EMConversation> loadConversations() {
        Map<String, EMConversation> allConversations = EMClient.getInstance().chatManager().getAllConversations();
        return new ArrayList<>(allConversations.values());
    }
}
