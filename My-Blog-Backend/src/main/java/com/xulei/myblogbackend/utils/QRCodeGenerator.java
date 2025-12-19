package com.xulei.myblogbackend.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class QRCodeGenerator {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
//        File file = QrCodeUtil.generate("https://xuleii.cn/", 300, 300, FileUtil.file("src/main/resources/xuleii.jpg"));
//        System.out.println("file.getAbsolutePath() = " + file.getAbsolutePath());
        System.out.println();
        Class<?> loadClass = QRCodeGenerator.class.getClassLoader().loadClass("com.xulei.myblogbackend.utils.demo01");
        //Class<?> loadClass = ClassLoader.getSystemClassLoader().loadClass("com.xulei.myblogbackend.utils.demo01");
        System.out.println("loadClass.getClassLoader() = " + loadClass.getClassLoader());
        Class<?> aClass = Class.forName("com.xulei.myblogbackend.utils.demo01");
        Constructor<?> constructor = aClass.getDeclaredConstructor();
        constructor.setAccessible(true);
        Object o = constructor.newInstance();
    }
}
