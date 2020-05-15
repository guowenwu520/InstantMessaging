package com.cd.myluntan.presenter;

import android.util.Log;

import com.cd.myluntan.Model.ContactModel;
import com.cd.myluntan.contract.ContactContract;
import com.cd.myluntan.data_connection.Data_Access;
import com.cd.myluntan.entrty.User;
import com.cd.myluntan.interfaceo.NetworkCallback;
import com.cd.myluntan.interfaceo.NetworkCallbackInt;
import com.cd.myluntan.utils.Singletion;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

import static com.cd.myluntan.data_connection.Global_Url_Parameters.GETUSERBYdNAME;
import static com.cd.myluntan.data_connection.Global_Url_Parameters.URL;

public class ContactPresenter implements ContactContract.Presenter {
    private static final String TAG = ContactPresenter.class.getCanonicalName();

    private ContactContract.View view;
    private ContactModel contactModel = new ContactModel();

    private ArrayList<User> contacts = new ArrayList<>();
    private int currentPage = 1;//当前页，默认第一页
    private int pages = 1;//总页数，获取服务端数据
    private int rows = 20;//每页显示行数
    private int satrt = 0;//当前下标
    private int end = 20;//结束下标

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
                contacts.clear();
                for (int i=0;i<arrayList.size();i++) {
                    String s=arrayList.get(i);
                    User user = new User();
                    user.setName(s);
                    user.setIsFollow(User.TYPE_IS_FOLLOW);
                    Map<String,String> map=new HashMap<>();
                    map.put("name",s);
                    int finalI = i;
                    Data_Access.AccessStringDateInt(URL + GETUSERBYdNAME, map,finalI, new NetworkCallbackInt(){
                        @Override
                        public Object parseNetworkResponse(Response response,int key) {
                            TypeToken<User> userTypeToken=new TypeToken<User>(){};
                            Gson gson=new Gson();
                            User user2= null;
                            try {
                                user2 = gson.fromJson(response.body().string(),userTypeToken.getType());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            user2.setIsFollow(User.TYPE_IS_FOLLOW);
                              contacts.add(user2);
                            if(key ==arrayList.size()-1){
                                view.onLoadContactSuccess();
                            }
                            return null;
                        }

                        @Override
                        public void onError(Call call, Exception e,int key) {

                        }

                        @Override
                        public void onResponse(Object response,int key) {

                        }
                    });
                }
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
                Log.d(TAG, "onSuccess==========" + value);
                contacts.clear();
                ArrayList<String> arrayList = new ArrayList<>(value);
                for (int i = 0; i < arrayList.size(); i++) {
                    String s = arrayList.get(i);
                    User user = new User();
                    user.setName(s);
                    user.setIsFollow(User.TYPE_IS_FOLLOW);
                    Map<String, String> map = new HashMap<>();
                    map.put("name", s);
                    int finalI = i;
                    Data_Access.AccessStringDateInt(URL + GETUSERBYdNAME, map, finalI, new NetworkCallbackInt() {
                        @Override
                        public Object parseNetworkResponse(Response response, int key) {
                            TypeToken<User> userTypeToken = new TypeToken<User>() {
                            };
                            Gson gson = new Gson();
                            User user2 = null;
                            try {
                                user2 = gson.fromJson(response.body().string(), userTypeToken.getType());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            user2.setIsFollow(User.TYPE_IS_FOLLOW);
                            contacts.add(user2);
                            if (key == arrayList.size() - 1) {
                                view.onRefreshSuccess();
                            }
                            return null;
                        }

                        @Override
                        public void onError(Call call, Exception e, int key) {

                        }

                        @Override
                        public void onResponse(Object response, int key) {

                        }
                    });
                }
            }

            @Override
            public void onError(int error, String errorMsg) {
                Log.d(TAG, error + "======" + errorMsg + "======" + contacts.size());
                view.onLoadContactFailed(errorMsg);
            }
        });
    }

    public ArrayList<User> getContacts() {
        return contacts;
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
