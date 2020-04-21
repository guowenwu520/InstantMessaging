package com.cd.myluntan.Model;

import com.cd.myluntan.contract.ContactContract;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;
import java.util.List;

public class ContactModel implements ContactContract.Model {
    @Override
    public ArrayList<String> getAttention() {
        return null;
    }

    @Override
    public ArrayList<String> getFan() {
        return null;
    }
}
