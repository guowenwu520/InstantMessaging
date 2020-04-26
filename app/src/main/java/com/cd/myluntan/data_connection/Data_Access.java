package com.cd.myluntan.data_connection;

import android.database.Observable;
import android.util.Log;

import com.cd.myluntan.interfaceo.NetworkCallback;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Data_Access {

   static  final   String STRINGTYPE="application/x-www-form-urlencoded";
    static  final   String JSONTYPE="application/json; charset=utf-8";
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

    public  static  void AccessStringDate(String url, Map<String,String> data, final NetworkCallback networkCallback){
        OkHttpUtils.post()
                .addHeader("Content-Type",STRINGTYPE)
                .url(url)
                .params(data)
                .build()
                .execute(new com.zhy.http.okhttp.callback.Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response) throws Exception {
                        if(networkCallback!=null)
                        return networkCallback.parseNetworkResponse(response);
                        else return  null;
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        if(networkCallback!=null)
                            networkCallback.onError(call,e);
                    }

                    @Override
                    public void onResponse(Object response) {
                        if(networkCallback!=null)
                            networkCallback.onResponse(response);
                    }
                });
    }
    public  static  void AccessJSONDate(String url, String jsons, final NetworkCallback networkCallback){
        OkHttpUtils.postString()
                .mediaType(MediaType.parse(JSONTYPE))
                .url(url)
                .content(jsons)
                .build()
                .execute(new com.zhy.http.okhttp.callback.Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response) throws Exception {
                        if(networkCallback!=null)
                            return networkCallback.parseNetworkResponse(response);
                        else return  null;
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        if(networkCallback!=null)
                            networkCallback.onError(call,e);
                    }

                    @Override
                    public void onResponse(Object response) {
                        if(networkCallback!=null)
                            networkCallback.onResponse(response);
                    }
                });
    }


    /**
     * 上传多张图片及参数
     * @param reqUrl URL地址
     * @param params 参数
     * @param pic_key 上传图片的关键字
     */
    public static void sendMultipart(String reqUrl, Map<String, String> params, String pic_key, List<File> files,final NetworkCallback networkCallback){
        OkHttpClient okHttpClient=new OkHttpClient();
                MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder();
                multipartBodyBuilder.setType(MultipartBody.FORM);
                //遍历map中所有参数到builder
                if (params != null){
                    for (String key : params.keySet()) {
                        multipartBodyBuilder.addFormDataPart(key, params.get(key));
                    }
                }
                //遍历paths中所有图片绝对路径到builder，并约定key如“upload”作为后台接受多张图片的key
                if (files != null){
                    for (File file : files) {
                        multipartBodyBuilder.addFormDataPart(pic_key, file.getName(), RequestBody.create(MEDIA_TYPE_PNG, file));
                    }
                }
                //构建请求体
                RequestBody requestBody = multipartBodyBuilder.build();

                Request.Builder RequestBuilder = new Request.Builder();
                RequestBuilder.url(reqUrl);// 添加URL地址
                RequestBuilder.post(requestBody);
                Request request = RequestBuilder.build();
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {e.printStackTrace();
                        if(networkCallback!=null)
                            networkCallback.onError(call,e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if(networkCallback!=null)
                            networkCallback.onResponse(response);
                    }
                });
    }

}
