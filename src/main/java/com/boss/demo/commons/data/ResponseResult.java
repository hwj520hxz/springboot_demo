package com.boss.demo.commons.data;

import com.boss.demo.commons.businessEnum.ErrorStatus;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author ：hwj
 * @version 版本号：V1.0
 * @Description ：
 */
public class ResponseResult<T> {
    @ApiModelProperty(value = "是否成功，true：成功，false：失败",position = 1)
    private boolean success;
    @ApiModelProperty(value = "返回码",position = 2, example = "200")
    private String code;
    @ApiModelProperty(value = "数据结果",position = 3)
    private T data;
    @ApiModelProperty(value = "提示信息",position = 4)
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResponseResult(boolean success, ErrorStatus code) {
        this.success = success;
        this.code = code.getCode();
        this.message = code.getDescription();
    }

    public ResponseResult(boolean success, ErrorStatus code, T data, String message) {
        this.success = success;
        this.code = code.getCode();
        this.data = data;
        this.message = message;
    }

    public ResponseResult(boolean success, ErrorStatus code, T data) {
        this.success = success;
        this.code = code.getCode();
        this.data = data;
        this.message = code.getDescription();
    }



    public ResponseResult(ErrorStatus code, String message) {
        this.code = code.getCode();
        this.message = message;
    }
    public static ResponseResult success(){
        ResponseResult resp = new ResponseResult(true, ErrorStatus.SUCCESS);
        return resp;
    }


    public static <T> ResponseResult success(T data){
        ResponseResult resp = new ResponseResult(true, ErrorStatus.SUCCESS,data);
        return resp;
    }

    public static <T> ResponseResult success(T data,String message){
        ResponseResult resp = new ResponseResult(true, ErrorStatus.SUCCESS,data,message);
        return resp;
    }

    public static ResponseResult success(ErrorStatus code,String message){
        ResponseResult resp = new ResponseResult(code,message);
        return resp;
    }

    public static ResponseResult failure(ErrorStatus errorCode) {
        ResponseResult resp = new ResponseResult(
                false,
                errorCode);
        return resp;
    }

    public static <T> ResponseResult error(T data,String message){
        ResponseResult resp = new ResponseResult(false, ErrorStatus.ERROR,data,message);
        return resp;
    }

    public static ResponseResult error(ErrorStatus code,String message){
        ResponseResult resp = new ResponseResult(code,message);
        return resp;
    }


}
