package com.ex.store.sys.controller;

import com.ex.store.core.cache.DictionaryCache;
import com.ex.store.core.dto.DictionaryDto;
import com.ex.store.core.exception.BusinessException;
import com.ex.store.core.pojo.ExSysDictionary;
import com.ex.store.core.util.LongUtils;
import com.ex.store.core.vo.AjaxResponse;
import com.ex.store.sys.service.SysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author wex
 * @Date 2021-1-29 11:43
 * @Desc
 **/
@RestController
@RequestMapping("dictionary")
public class DictionaryController {


    @Autowired
    private SysService sysService;

    @RequestMapping("index")
    public AjaxResponse index(DictionaryDto dictionaryDto){
        List<DictionaryDto> list = sysService.findDictionaryByCondition(dictionaryDto);
        return AjaxResponse.success(list);
    }

    /**
     * 删除为逻辑删除
     * @param exSysDictionaries
     * @return
     */
    @PostMapping("updateDictionary")
    public AjaxResponse updateGroup(@RequestBody ArrayList<DictionaryDto> exSysDictionaries){
        AjaxResponse ajaxResponse = sysService.updateDictionary(exSysDictionaries);
        return ajaxResponse;
    }

    /**
     * 单行编辑按钮与单行添加与添加
     * @param
     * @return
     */
    @PostMapping("insertDictionary")
    public AjaxResponse insertGroup(@RequestBody ArrayList<DictionaryDto> exSysDictionaries){
        String msg = "";
        try {
            msg = sysService.insertDictionary(exSysDictionaries.get(0));
        }catch (BusinessException e){
            msg = e.getMessage();
        }
        return AjaxResponse.success(msg);
    }

    @PostMapping("getChilds")
    public AjaxResponse getChilds(DictionaryDto dictionaryDto){
        List<DictionaryDto> list = sysService.getChilds(dictionaryDto);
        return AjaxResponse.success(list);
    }

    @RequestMapping("getValueByKeys")
    public AjaxResponse getValueByKeys(String keys){
        DictionaryDto valueByKeys = DictionaryCache.getValueByKeys("","");
        return AjaxResponse.success(valueByKeys);
    }
    @RequestMapping("getListByCode")
    public AjaxResponse getListByCode(String code){
        List<DictionaryDto> listByCode = DictionaryCache.getListByCode(code);
        return AjaxResponse.success(listByCode);
    }

    @RequestMapping("reloadDictionary")
    public AjaxResponse reloadDictionary(){
        sysService.reloadDictionary();
        return AjaxResponse.success("字典重载成功");
    }

    @RequestMapping("getLaytpl")
    public AjaxResponse getLaytpl(){
        Map<String, List<DictionaryDto>> laytpl = sysService.getLaytpl();
        return AjaxResponse.success(laytpl);
    }
}
