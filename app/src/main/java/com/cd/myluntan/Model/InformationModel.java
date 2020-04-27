package com.cd.myluntan.Model;

import android.util.Log;

import com.cd.myluntan.contract.InformationContract;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;

import java.util.ArrayList;
import java.util.Map;

public class InformationModel implements InformationContract.Model {
    private static final String TAG = InformationModel.class.getCanonicalName();
    /**
     * 获取会话列表
     * @return 会话列表
     */
    @Override
    public ArrayList<EMConversation> loadConversations() {
        Map<String, EMConversation> allConversations = EMClient.getInstance().chatManager().getAllConversations();
        Log.d(TAG,allConversations.toString());
        return new ArrayList<>(allConversations.values());
    }
}
