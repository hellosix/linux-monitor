package com.lzz.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * Created by gl49 on 2017/11/7.
 */
public class Result {
    public static final String RESULT_TAG = "result";
    private int errorCode;
    private String message;

    public Result(int errorCode, String message){
        setErrorCode( errorCode );
        setMessage( message );
    }

    public static JSONObject common(JSONArray resArr){
        JSONObject result = new JSONObject();
        result.put(RESULT_TAG, resArr);
        return result;
    }
    public static JSONObject common(List list){
        JSONObject result = new JSONObject();
        result.put(RESULT_TAG, list);
        return result;
    }
    public static JSONObject common(JSONObject resObj){
        JSONObject result = new JSONObject();
        result.put(RESULT_TAG, resObj);
        return result;
    }
    public static JSONObject common(String resStr){
        JSONObject result = new JSONObject();
        result.put(RESULT_TAG, resStr);
        return result;
    }
    public static JSONObject OK(){
        return JSONObject.fromObject( (new Result.OK()) );
    }
    public static JSONObject Fail(){
        return JSONObject.fromObject( (new Result.Fail()) );
    }
    public static JSONObject Res(boolean res){
        if( res ){
            return OK();
        }else{
            return Fail();
        }
    }
    public static JSONObject Res(int errorCode, String message){
        return JSONObject.fromObject( (new Result(errorCode, message)));
    }

    private static class OK extends Result{
        public OK(){
            super(0, "OK");
        }
    }
    private static class Fail extends Result{
        public Fail(){
            super(1, "Fail");
        }
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Result{" +
                "errorCode=" + errorCode +
                ", message='" + message + '\'' +
                '}';
    }
}
