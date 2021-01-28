package com.ex.store.sys.mapper;

import com.ex.store.core.pojo.ExSysResource;
import com.ex.store.core.vo.PageParameter;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author wex
 * @Date 2021-1-25 13:21
 * @Desc
 **/
@Mapper
public interface ResourceMapper {

    List<ExSysResource> findResourceByCondition(PageParameter<ExSysResource> pageParameter);

    int insertResource(ExSysResource exSysResource);

    int findCoyntByCondition(PageParameter<ExSysResource> pageParameter);

    int updateResourceByLit(ArrayList<ExSysResource> exSysResources);

    List<ExSysResource> findListByCondition(ExSysResource exSysResource);
}
