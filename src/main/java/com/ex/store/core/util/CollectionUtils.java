package com.ex.store.core.util;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author wex
 * @Date 2021-1-12 13:35
 * @Desc
 **/
public class CollectionUtils {

    /**
     * 将List<Map<String,Object>>结构的数据更具filterKey进行转换为以‘，’号分割的字符串
     * @List<Map<String,Object>>  source
     * @String filterKey
     * @return
     */
    public static String ListMapToString(List<Map<String,Object>> source,String filterKey){
        final String values = source.stream()
                .map(item -> item.get(filterKey))
                .filter(Objects::nonNull)
                .map(item -> item.toString())
                .collect(Collectors.joining(","));
        return values;
    }
}
