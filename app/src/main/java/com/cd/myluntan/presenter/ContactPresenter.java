package com.cd.myluntan.presenter;

import android.util.Log;

import com.cd.myluntan.Model.ContactModel;
import com.cd.myluntan.contract.ContactContract;
import com.cd.myluntan.entrty.User;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMClient;

import java.util.ArrayList;
import java.util.List;

public class ContactPresenter implements ContactContract.Presenter {
    private static final String TAG = ContactPresenter.class.getCanonicalName();

    public ArrayList<User> contacts = new ArrayList<>();
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
                ArrayList<String> arrayList = new ArrayList<>(value);
                for (String s : arrayList) {
                    User user = new User();
                    user.setName(s);
                    contacts.add(user);
                }
                view.onLoadContactSuccess(value.size());
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
                Log.d(TAG, "onSuccess=========="+value);
                contacts.clear();
                ArrayList<String> arrayList = new ArrayList<>(value);
                for (String s : arrayList) {
                    User user = new User();
                    user.setName(s);
                    contacts.add(user);
                }
                view.onRefreshSuccess(value.size());
            }

            @Override
            public void onError(int error, String errorMsg) {
                Log.d(TAG, error + "======" + errorMsg + "======" + contacts.size());
                view.onLoadContactFailed(errorMsg);
            }
        });
    }
}
