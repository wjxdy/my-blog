package com.xulei.myblogbackend.utils;

import com.xulei.myblogbackend.entity.LoginInfo;
import com.xulei.myblogbackend.vo.UserLoginInfoVo;

public class UserHolder {

    private static final ThreadLocal<LoginInfo> tl = new ThreadLocal<>();

    public static void saveUser(LoginInfo user){
        tl.set(user);
    }

    public static LoginInfo getUser(){
        return tl.get();
    }

    public static void removeUser(){
        tl.remove();
    }
}
