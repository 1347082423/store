package com.ex.store.core.config.load;

import com.ex.store.core.config.access.SecurityAuthorityAccess;
import com.ex.store.core.pojo.ExSysResource;
import com.ex.store.sys.mapper.ResourceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

/**
 * @Author wex
 * @Date 2021-1-28 9:40
 * @Desc
 **/
@Component
@Order(1)
public class LoadFreeAuthority implements ApplicationRunner {

    @Autowired
    private ResourceMapper resourceMapper;

    @Autowired
    private SecurityAuthorityAccess securityAuthorityAccess;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("开始加载自由权限");
        ExSysResource exSysResource = new ExSysResource();
        exSysResource.setCategory("2");
        List<ExSysResource> list = resourceMapper.findListByCondition(exSysResource);
        securityAuthorityAccess.setSkipUrls(list);
        System.out.println("加载完毕");
    }
}
