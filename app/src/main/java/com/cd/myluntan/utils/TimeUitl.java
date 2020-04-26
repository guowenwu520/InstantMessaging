package com.cd.myluntan.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUitl {
    public static String  DataToString(Date date){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return  simpleDateFormat.format(date);
    }
    public static String  DataToStringyear(Date date){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        return  simpleDateFormat.format(date);
    }
}
