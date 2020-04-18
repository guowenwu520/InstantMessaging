package com.cd.myluntan.interfaceo;

import okhttp3.Call;
import okhttp3.Response;

public interface NetworkCallback {
    Object parseNetworkResponse(Response response);
    void onError(Call call, Exception e);
    void  onResponse(Object response);
}
