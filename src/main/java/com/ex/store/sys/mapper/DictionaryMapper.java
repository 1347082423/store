package com.ex.store.sys.mapper;

import com.ex.store.core.dto.DictionaryDto;
import com.ex.store.core.pojo.ExSysDictionary;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author wex
 * @Date 2021-1-29 11:47
 * @Desc
 **/
@Mapper
public interface DictionaryMapper {

    List<DictionaryDto> findDictionaryByCondition(DictionaryDto dictionaryDto);

    int insertDictonary(List<ExSysDictionary> list);

    int updateDictonary(List<ExSysDictionary> mainList);

    int delDictonary(ExSysDictionary exSysDictionary);
}
