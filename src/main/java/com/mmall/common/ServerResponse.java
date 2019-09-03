package com.mmall.common;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.schema.JsonSerializableSchema;

import java.io.Serializable;

@JsonSerialize(include =  JsonSerialize.Inclusion.NON_NULL)
// 只对非空的数据进行初始化，如果某些成员是空例如 msg、data，就不对其进行序列化
public class ServerResponse<T> implements Serializable {    // 泛型 T 代表要响应的数据类型

    private int status;
    private String msg;
    private T data;

    /*  定义了多种构造器，可以按各种需求与情况与创建对象！！ */

    private ServerResponse(int status) {
        this.status = status;
    }

    private ServerResponse(int status, String msg) {    // 第二个参数是 string，当传参就是 string 时可以完全匹配，若是其他类型对象则进入泛型构造方法
        this.status = status;
        this.msg = msg;
    }

    private ServerResponse(int status, T data) {    // 第二个参数是泛型，当传参不是 string 时，才会调用该构造器
        this.status = status;
        this.data = data;
    }

    private ServerResponse(int status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    @JsonIgnore     // 该方法在序列化后不会显示在 json序列化结果中
    public boolean isSuccess(){
        return this.status == ResponseCode.SUCCESS.getCode();
    }

    public int getStatus(){
        return status;
    }

    public T getData(){
        return data;
    }

    public String getMsg(){
        return msg;
    }

    public static <T> ServerResponse<T> createBySuccess(){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode());
    }

    public static <T> ServerResponse<T> createBySuccessMessage(String msg){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(), msg);
    }

    public static <T> ServerResponse<T> createBySuccess(T data){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(), data);
    }

    public static <T> ServerResponse<T> createBySuccess(String msg, T data){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(), msg, data);
    }

    public static <T> ServerResponse<T> createByError(){
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDesc());
    }

    public static <T> ServerResponse<T> createByErrorMessage(String errorMessage){
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(), errorMessage);
    }

    public static <T> ServerResponse<T> createByErrorCodeMessage(int errorCode,  String errorMessage){
        return new ServerResponse<T>(errorCode, errorMessage);
    }


}
