package com.cd.myluntan.data_connection;

import android.util.Log;

import com.cd.myluntan.interfaceo.NetworkCallback;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

public class Data_Access {

   static  final   String STRINGTYPE="application/x-www-form-urlencoded";
    static  final   String FILETYPE="multipart/form-data";

    public  static  void AccessStringDate(String url, Map<String,String> data, final NetworkCallback networkCallback){
        OkHttpUtils.post()
                .addHeader("Content-Type",STRINGTYPE)
                .url(url)
                .params(data)
                .build()
                .execute(new com.zhy.http.okhttp.callback.Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response) throws Exception {
                        return networkCallback.parseNetworkResponse(response);
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                      networkCallback.onError(call,e);
                    }

                    @Override
                    public void onResponse(Object response) {
                       networkCallback.onResponse(response);
                    }
                });
    }

}
