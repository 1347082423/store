package com.ex.store.core.cache;

import com.ex.store.core.dto.DictionaryDto;

import java.util.ArrayList;
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

    public static final List<DictionaryDto> DICTIONARY_CACHE = new ArrayList<DictionaryDto>();

    public static void initCache(List<DictionaryDto> source){
        DICTIONARY_CACHE.addAll(source);
    }

    public static DictionaryDto getValueByKeys(String[] keys){
        List<DictionaryDto> dictionaryDtos = DICTIONARY_CACHE;
        DictionaryDto dictionaryDto = new DictionaryDto();
        for (String key : keys) {
            dictionaryDtos = getListByKey(dictionaryDtos,key);
        }
        if (dictionaryDtos!=null && dictionaryDtos.size() > 0){
            dictionaryDto = dictionaryDtos.get(0);
        }
       return dictionaryDto;
    }


    public static List<DictionaryDto> getListByKey(List<DictionaryDto> cache,String key){
        List<DictionaryDto> collect = DICTIONARY_CACHE.stream().filter(dictionaryDto -> key.equals(dictionaryDto.getDicKey())).collect(Collectors.toList());
        return collect;
    }
}
