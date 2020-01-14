package com.boss.demo.commons.businessEnum;

import com.alibaba.druid.util.StringUtils;
import com.boss.demo.commons.exception.ErrorCode;

/**
 * @author ：hwj
 * @version 版本号：V1.0
 * @Description ：
 */

public enum ErrorStatus implements ErrorCode {

    /** 未指明的异常 */
    UNSPECIFIED("500", "网络异常，请稍后再试"),
    NO_SERVICE("404", "网络异常, 服务器熔断"),
    /** 成功 */
    SUCCESS("200","成功"),
    /** 错误 */
    ERROR("201","失败")
    ;

    /** 错误码 */
    private final String code;

    /** 错误信息 */
    private final String description;

    ErrorStatus(final String code, final String description) {
        this.code = code;
        this.description = description;
    }


    public String getCode() {
        return code;
    }


    public String getDescription() {
        return description;
    }

    /** 根据编码查询枚举 */
    public static ErrorStatus getByCode(String code) {
        for (ErrorStatus value : ErrorStatus.values()) {
            if (StringUtils.equals(code, value.getCode())) {
                return value;
            }
        }
        return UNSPECIFIED;
    }

    /** 枚举是否存在 */
    public static Boolean contains(String code){
        for (ErrorStatus value : ErrorStatus.values()) {
            if (StringUtils.equals(code, value.getCode())) {
                return true;
            }
        }
        return false;
    }


}
