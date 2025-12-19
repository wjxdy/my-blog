package com.xulei.myblogbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Result<T> {

    private String code;
    private String message;
    private T data;


    public static <T> Result<T> success(T data){
        Result result = new Result();
        result.setCode("200");
        result.setMessage("成功获取");
        result.setData(data);
        return result;
    }


    public static Result ok(){

        Result result = new Result();
        result.setMessage("请求成功");
        result.setCode("200");
        result.setData(null);

        return result;
    }


    public static Result ok(String message){

        Result result = new Result();
        result.setMessage(message);
        result.setCode("200");
        result.setData(null);
        return result;
    }


    public static Result fail(){
        Result result = new Result();
        result.setMessage("请求失败");
        result.setCode("401");
        result.setData(null);
        return result;
    }


    public static Result fail(String message){
        Result result = new Result();
        result.setMessage(message);
        result.setCode("401");
        result.setData(null);
        return result;
    }


}
