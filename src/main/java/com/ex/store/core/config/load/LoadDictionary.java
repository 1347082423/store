package com.ex.store.core.config.load;

import com.ex.store.core.cache.DictionaryCache;
import com.ex.store.core.config.access.SecurityAuthorityAccess;
import com.ex.store.core.dto.DictionaryDto;
import com.ex.store.sys.mapper.DictionaryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @Author wex
 * @Date 2021-1-28 16:40
 * @Desc
 **/
@Component
@Order(2)
public class LoadDictionary implements ApplicationRunner {

    @Autowired
    private DictionaryMapper dictionaryMapper;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        DictionaryDto dictionaryDto = new DictionaryDto();
        dictionaryDto.setDicForbid(1L);
        List<DictionaryDto> dictionaryByCondition = dictionaryMapper.findDictionaryByCondition(dictionaryDto);
        Map<String, List<DictionaryDto>> listMap = dictionaryByCondition.stream().collect(Collectors.groupingBy(DictionaryDto::getDicIsStair));
        dictionaryByCondition.forEach(zone -> {
            zone.setChilds(listMap.get(zone.getDicId()));
        });
        dictionaryByCondition = dictionaryByCondition.stream().filter(menuDto -> 0 == menuDto.getDicParentid()).collect(Collectors.toList());
        DictionaryCache.initCache(dictionaryByCondition);
    }
}
