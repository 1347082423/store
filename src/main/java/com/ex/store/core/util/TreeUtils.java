package com.ex.store.core.util;

import com.ex.store.core.dto.MenuDto;
import com.ex.store.core.dto.TreeDto;
import com.ex.store.core.pojo.ExSysMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author wex
 * @Date 2021-1-5 11:10
 * @Desc
 **/
public class TreeUtils {

    //
    public static List<MenuDto> coverTree (List<MenuDto> zoneList){
        Map<Long, List<MenuDto>> zoneByParentIdMap = zoneList.stream().collect(Collectors.groupingBy(MenuDto::getPid));
        zoneList.forEach(zone -> {
            zone.setList(zoneByParentIdMap.get(zone.getId()));
        });
        return zoneList.stream().filter(menuDto -> 0 == menuDto.getPid()).collect(Collectors.toList());
    }

    public static List<TreeDto> coverTreeWithChildren(List<TreeDto> treeDtos) {
        Map<Long, List<TreeDto>> zoneByParentIdMap = treeDtos.stream().collect(Collectors.groupingBy(TreeDto::getPid));
        treeDtos.forEach(zone -> {
            zone.setChildren(zoneByParentIdMap.get(zone.getId()));
        });
        return treeDtos.stream().filter(menuDto -> 0 == menuDto.getPid()).collect(Collectors.toList());
    }
}
