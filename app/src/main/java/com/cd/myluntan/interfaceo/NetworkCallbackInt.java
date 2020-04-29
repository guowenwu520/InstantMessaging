package com.cd.myluntan.interfaceo;

import okhttp3.Call;
import okhttp3.Response;

public interface NetworkCallbackInt {
    Object parseNetworkResponse(Response response,int key);
    void onError(Call call, Exception e,int key);
    void  onResponse(Object response,int key);
}
