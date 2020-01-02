package com.pagoda.hdtt.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/29.
 * @author dell
 */
@Slf4j
public class BeanUtil {


    /**
     * 检查对象是否为空
     * @param obj
     * @return
     */
    public static boolean checkIsEmpty(Object obj){
        if (obj == null) {
            return true;
        }else if ((obj instanceof List)) {
            return ((List) obj).size() == 0;
        }else if ((obj instanceof String)) {
            return "".equals(((String) obj).trim());
        }else if (obj instanceof Boolean && (!(Boolean) obj)) {
            return true;
        } else if (obj instanceof Collection && ((Collection) obj).isEmpty()) {
            return true;
        } else if (obj instanceof Map && ((Map) obj).isEmpty()) {
            return true;
        } else if (obj instanceof Object[] && ((Object[]) obj).length == 0) {
            return true;
        } else if (obj instanceof StringBuffer && ((StringBuffer) obj).length() == 0) {
            return true;
        }
        return false;
    }

    /**
     * 检查是否为空(可同时检查多个)
     * @param obj
     * @return
     */
    public static boolean checkIsEmpty(Object... obj){
        boolean flag = false;
        for (Object object : obj){
            flag = checkIsEmpty(object);
            if(!flag){
                break;
            }
        }
        return flag;
    }

    /**
     * 检查对象是否不为空
     * @param obj
     * @return
     */
    public static boolean checkIsNotEmpty(Object obj){
        if (obj == null) {
            return false;
        }else if ((obj instanceof List)) {
            return ((List) obj).size() != 0;
        }else if ((obj instanceof String)) {
            return !"".equals(((String) obj).trim());
        }else if (obj instanceof Boolean && (!(Boolean) obj)) {
            return false;
        } else if (obj instanceof Collection && ((Collection) obj).isEmpty()) {
            return false;
        } else if (obj instanceof Map && ((Map) obj).isEmpty()) {
            return false;
        } else if (obj instanceof Object[] && ((Object[]) obj).length == 0) {
            return false;
        } else if (obj instanceof StringBuffer && ((StringBuffer) obj).length() == 0) {
            return false;
        }
        return true;
    }
    /**
     * 检查是否不为空(可同时检查多个)
     * @param obj
     * @return
     */
    public static boolean checkIsNotEmpty(Object... obj){
        boolean flag = true;
        for (Object object : obj){
            flag = checkIsEmpty(object);
            if(!flag){
                break;
            }
        }
        return flag;
    }


    /**
     * 检查当前值是否在List中
     * @param original
     * @param list
     * @return
     */
    public static boolean checkExistInList(Integer original, List<Integer> list){
        if (checkIsEmpty(list)){
            return false;
        }
        boolean okFlag = false;
        for (Integer i : list){
            if(i.intValue() == original.intValue()){
                okFlag = true;
                break;
            }
        }
        return okFlag;
    }


    private static boolean isEmojiCharacter(char codePoint) {
        return codePoint == 0x0 || codePoint == 0x9 || codePoint == 0xA || codePoint == 0xD || codePoint >= 0x20 && codePoint <= 0xD7FF || codePoint >= 0xE000 && codePoint <= 0xFFFD;
    }


    /**
     * 判断对象中属性值是否全为空
     *
     * @param object 实体
     * @return boolean
     * @author Alex
     * @date 10/20/2019 5:20 PM
     */
    public static boolean checkObjAllFieldsIsNull(Object object) {
        if (null == object) {
            return true;
        }

        try {
            for (Field f : object.getClass().getDeclaredFields()) {
                f.setAccessible(true);

                System.out.print(f.getName() + ":");
                System.out.println(f.get(object));

                if (f.get(object) != null && StringUtils.isNotBlank(f.get(object).toString())) {
                    return false;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

}
