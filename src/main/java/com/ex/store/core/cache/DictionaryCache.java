package com.ex.store.core.cache;

import com.ex.store.core.dto.DictionaryDto;
import com.ex.store.core.util.CollectionUtils;
import com.ex.store.core.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author wex
 * @Date 2021-1-29 11:12
 * @Desc
 * 根据dic_is_stair map
 **/
public class DictionaryCache {

    public static final Map<String, List<DictionaryDto>> DICTIONARY_CACHE = new HashMap<String, List<DictionaryDto>>();

    public static void initCache(List<DictionaryDto> dictionaryByCondition){
        if (CollectionUtils.isNotNull(DICTIONARY_CACHE)){
            DICTIONARY_CACHE.clear();
        }
        dictionaryByCondition = dictionaryByCondition.stream().filter(dictionaryDto1 -> StringUtils.isNotNull(dictionaryDto1.getDicCode())).collect(Collectors.toList());
        Map<String, List<DictionaryDto>> listMap = dictionaryByCondition.stream().collect(Collectors.groupingBy(DictionaryDto::getDicCode));
        DICTIONARY_CACHE.putAll(listMap);
    }

    public static DictionaryDto getValueByKeys(String code,String key){
        Map<String, List<DictionaryDto>> dictionaryDtos = DICTIONARY_CACHE;
        if (!dictionaryDtos.containsKey(code)){
            return null;
        }
        List<DictionaryDto> list = dictionaryDtos.get(code);
        List<DictionaryDto> collect = list.stream().filter(dictionaryDto -> !key.equals(dictionaryDto.getDicKey())).collect(Collectors.toList());
        return CollectionUtils.isNull(collect) ? new DictionaryDto() : collect.get(0);
    }


    public static List<DictionaryDto> getListByCode(String code){
        Map<String, List<DictionaryDto>> dictionaryDtos = DICTIONARY_CACHE;
        if (!dictionaryDtos.containsKey(code)){
            return null;
        }
        List<DictionaryDto> list = dictionaryDtos.get(code).stream().filter(dictionaryDto -> StringUtils.isNotNull(dictionaryDto.getDicKey())).collect(Collectors.toList());
        return CollectionUtils.isNull(list) ? new ArrayList<>() : list;
    }

    public static Map<String, List<DictionaryDto>> getAllDictionary(){
        return DICTIONARY_CACHE;
    }
}
