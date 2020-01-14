package com.boss.demo.commons.data;

import com.github.pagehelper.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：hwj
 * @version 版本号：V1.0
 * @Description ：参数转换工具类
 */
public class ParamsConversion {
    public static Map<String, Object> httpToMap(HttpServletRequest requestParam){
        Map<String, Object> param = new HashMap<>();
        //获取请求参数将其转为map
        Map<String, String[]> rawParam = requestParam.getParameterMap();
        for(Map.Entry<String, String[]> entry : rawParam.entrySet()){
            String key = entry.getKey();
            String value[] = entry.getValue();
            //如果值数组只有一个参数
            if(value.length == 1 && StringUtil.isNotEmpty(value[0])){
                if(value[0].startsWith("[") && value[0].endsWith("]")){
                    if(!value[0].equals("[]")){
                        String[] arrayParam = value[0].replace("[","").replace("]","").replace("\"","").split(",");
                        param.put(key,arrayParam);
                    }
                }else {
                    param.put(key,value[0]);
                }
            }
            //如果值数组有多个参数
            if(value.length > 1){
                param.put(key,value);
            }
        }
        return param;
    }

}
