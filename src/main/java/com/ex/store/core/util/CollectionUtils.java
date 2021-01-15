package com.ex.store.core.util;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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

    public static List<Long> StringToList(String source){
        if (StringUtils.isNull(source)){
            return null;
        }
        return Stream.of(source.split(",")).mapToLong(s->Long.parseLong(s)).boxed().collect(Collectors.toList());
    }

    public static boolean isNull(Collection collection){
        return !isNotNull(collection);
    }
    public static boolean isNotNull(Collection collection){
        return collection != null && collection.size() > 0;
    }
}
