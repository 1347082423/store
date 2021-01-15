package com.ex.store.core.util;

/**
 * @Author wex
 * @Date 2021-1-13 10:35
 * @Desc
 **/
public class StringUtils {

    public static boolean isNull(String source){
        return !isNotNull(source);
    }

    public static boolean isNotNull(String source){

        return source != null && source.length() > 0;
    }
}
