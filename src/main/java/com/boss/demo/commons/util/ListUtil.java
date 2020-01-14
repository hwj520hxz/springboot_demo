package com.boss.demo.commons.util;

import java.lang.reflect.Field;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author ：hwj
 * @version 版本号：V1.0
 * @Description ：
 */
public class ListUtil {


    /**
     * @Description: 对list的元素按照多个属性名称排序
     * @param list 元素
     * @param orderMode asc 升序 desc 降序
     * @param fieldName 属性名称
     * @return
     * @throws
     **/
    public static <E> void sort(List<E> list, final String orderMode, String... fieldName){
        Collections.sort(list, (obj1, obj2) -> {
            int ret = 0;
            try {
                for(int i = 0; i < fieldName.length; i++){
                    ret = compareObject(fieldName[i], orderMode, obj1, obj2);
                    //如果ret不为0，表示比较的两个对象已经排序完毕
                    if(0 != ret){
                        break;
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
            }
            return ret;
        });
    }

    public static <E> void sort(List<E> list, final List<String> fieldNames, List<String> orderModes){
        if(fieldNames.size() != orderModes.size()){
            throw new RuntimeException("属性数组元素个数和升降序数组元素个数不相等");
        }
        Collections.sort(list, (obj1, obj2) -> {
            int ret = 0;
            try {
                for(int i = 0; i<fieldNames.size(); i++){
                    ret = compareObject(fieldNames.get(i), orderModes.get(i), obj1, obj2);
                    //如果ret不为0，表示比较的两个对象已经排序完毕
                    if(0 != ret){
                        break;
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
            }
            return ret;
        });
    }


    /**
     * @Description: 对两个对象按指定属性名称进行排序
     * @param fieldName 属性名称
     * @param orderMode true 升序 false 降序
     * @param a 待排序的对象
     * @param b 待排序的对象
     * @return
     * @throws Exception
     **/
    public static <E> int compareObject(final String fieldName, final String orderMode, E a, E b) throws Exception{
        Object value1 = forceGetFieldValue(a, fieldName);
        Object value2 = forceGetFieldValue(b, fieldName);
        String str1 = value1.toString();
        String str2 = value2.toString();
        if(value1 instanceof Number && value2 instanceof Number){
            int maxLen = Math.max(str1.length(), str2.length());
            str1 = addZeroToStr((Number) value1, maxLen);
            str2 = addZeroToStr((Number) value2, maxLen);
        } else if(value1 instanceof Date && value2 instanceof Date){
            long time1 = ((Date) value1).getTime();
            long time2 = ((Date) value2).getTime();
            int maxLen = Long.toString(Math.max(time1,time2)).length();
            str1 = addZeroToStr((Number) value1, maxLen);
            str2 = addZeroToStr((Number) value2, maxLen);
        }
        if("asc".equals(orderMode)){
            return str1.compareTo(str2);
        }else {
            return str2.compareTo(str1);
        }
    }

    /**
     * @Description: 利用反射的原理获取指定对象的指定属性值
     * @param obj 属性所在的对象
     * @param fieldName 属性名称
     * @return
     * @throws
     **/
    public static Object forceGetFieldValue(Object obj, String fieldName) throws Exception {
        Field field = obj.getClass().getDeclaredField(fieldName);
        Object object;
        boolean accessible = field.isAccessible();
        if(!accessible){
            //如果该对象是protect或者private，需要将其修改为可以访问
            field.setAccessible(true);
            object = field.get(obj);
            //获取完对象信息后需要还原其性质
            field.setAccessible(accessible);
            return object;
        }
        object = field.get(obj);
        return object;
    }

    /**
     * @Description: 给数字对象按指定长度在左侧补0 如：addZero2Str(11,4) 返回 "0011", addZero2Str(-18,6)返回 "-000018"
     * @param numObj 数字对象
     * @param length 指定长度
     * @return
     **/
    public static String addZeroToStr(Number numObj, int length){
        NumberFormat nf = NumberFormat.getInstance();
        //设置是否开启分组,开启的话格式为:10,000,000,不开启的话格式为:10000000
        nf.setGroupingUsed(false);
        //设置最大整数位数
        nf.setMaximumIntegerDigits(length);
        //设置最小整数位数
        nf.setMinimumIntegerDigits(length);
        return nf.format(numObj);
    }
}
