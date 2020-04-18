package com.cd.myluntan.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JsonUitl {
     public  static  Object jsonToObgect(String str, TypeToken<? extends Object> typeToken){
         Gson gson=new Gson();
         return gson.fromJson(str,typeToken.getType());
     }
}
