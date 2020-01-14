package com.boss.demo.commons.exception;

import com.boss.demo.commons.businessEnum.ErrorStatus;
import lombok.Data;

/**
 * @author ：hwj
 * @version 版本号：V1.0
 * @Description ：自定义业务异常类
 */

@Data
public class BusinessException extends RuntimeException{

    private static final long serialVersionUID = -7864604160297181941L;

    /** 错误码 */
    protected final ErrorCode errorCode;

    /** 无参构造函数 */
    public BusinessException() {
        super(ErrorStatus.UNSPECIFIED.getDescription());
        this.errorCode = ErrorStatus.UNSPECIFIED;
    }

    /** 指定错误码构造通用异常 */
    public BusinessException(final ErrorStatus errorStatus){
        super(ErrorStatus.UNSPECIFIED.getDescription());
        this.errorCode = errorStatus;
    }

    /** 指定描述构造通用异常 */
    public BusinessException(final String message){
        super(message);
        this.errorCode = ErrorStatus.UNSPECIFIED;
    }

    /** 构造通用异常 */
    public BusinessException(final ErrorStatus errorStatus,final String message){
        super(message);
        this.errorCode = errorStatus;
    }


}
