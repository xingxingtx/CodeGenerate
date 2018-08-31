package code.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by weipeng on 2018-04-03.
 */
public class JsonResponseData {
    private static Gson gson=new GsonBuilder().create();

    //空构造方法
    public JsonResponseData(){}

    //构造方法
    public JsonResponseData(Boolean ret, String msg, int status, String message, Object data){
        this.ret = ret;
        this.msg = msg;
        this.code = status;
        this.message = message;
        this.data = data==null?new Object():data;
    }


    //是否成功
    private Boolean ret = false;

    //返回信息
    private String msg = null;

    //返回状态
    private int code = -100;

    //返回状态对应消息
    private String message = null;

    //返回数据
    private Object data = null;

    //重写toString方法
    public String toString(){
        return gson.toJson(this);
    }
}
