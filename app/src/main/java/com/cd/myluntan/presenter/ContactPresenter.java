package com.cd.myluntan.presenter;

import android.util.Log;

import com.cd.myluntan.Model.ContactModel;
import com.cd.myluntan.contract.ContactContract;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMClient;

import java.util.ArrayList;
import java.util.List;

public class ContactPresenter implements ContactContract.Presenter {
    private static final String TAG = ContactPresenter.class.getCanonicalName();
    public static final int PULL_DOWN_TO_REFRESH = 0;
    public static final int PULL_TO_REFRESH = 1;

    public List<String> contacts = new ArrayList<>();
    private ContactContract.View view;
    private ContactModel contactModel = new ContactModel();

    public ContactPresenter(ContactContract.View view) {
        this.view = view;
    }

    @Override
    public void loadAttention() {
        EMClient.getInstance().contactManager().aysncGetAllContactsFromServer(new EMValueCallBack<List<String>>() {
            @Override
            public void onSuccess(List<String> value) {
                Log.d(TAG, "======" + value + "======" + value.size());
                contacts.clear();
                contacts.addAll(value);
                view.onLoadContactSuccess(PULL_TO_REFRESH);
            }

            @Override
            public void onError(int error, String errorMsg) {
                Log.d(TAG, error + "======" + errorMsg + "======" + contacts.size());
                view.onLoadContactFailed(errorMsg);
            }
        });
    }

    @Override
    public void loadFan() {
    }

    @Override
    public void onRefresh() {
        EMClient.getInstance().contactManager().aysncGetAllContactsFromServer(new EMValueCallBack<List<String>>() {
            @Override
            public void onSuccess(List<String> value) {
                contacts.clear();
                contacts.addAll(value);
                view.onLoadContactSuccess(PULL_DOWN_TO_REFRESH);
            }

            @Override
            public void onError(int error, String errorMsg) {
                Log.d(TAG, error + "======" + errorMsg + "======" + contacts.size());
                view.onLoadContactFailed(errorMsg);
            }
        });
    }
}
