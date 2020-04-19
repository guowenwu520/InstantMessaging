package com.cd.myluntan.presenter;

import com.cd.myluntan.R;

class BasePresenter {
    int error(int code) {
        if (code == 304) {//304==无法访问服务器
            return R.string.can_not_get_server;
        } else if (code == 204) {//204== 用户不存在
            return R.string.user_not_exist;
        }  else if (code==203){//203 用户已存在
            return R.string.user_exist;
        }else if (code == 202) {//202 用户名或密码错误
            return R.string.username_or_password_is_wrong;
        }else if (code == 200) {//200 用户已经登录
            return R.string.user_is_already_login;
        } else return R.string.username_or_password_is_null;
    }
}
