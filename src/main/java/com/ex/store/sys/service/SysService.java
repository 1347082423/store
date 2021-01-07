package com.ex.store.sys.service;

import com.ex.store.core.dto.MenuDto;
import com.ex.store.core.pojo.ExSysMenu;
import com.ex.store.core.vo.AjaxResponse;
import com.ex.store.core.vo.PageAjaxResponse;
import com.ex.store.core.vo.PageParameter;

import java.util.List;

/**
 * @Author wex
 * @Date 2021-1-4 16:53
 * @Desc
 **/
public interface SysService {
    List<MenuDto> obtainMenu();

    PageAjaxResponse getAllMenu(PageParameter<ExSysMenu> pageParameter);

    AjaxResponse getAllMenu();
}
