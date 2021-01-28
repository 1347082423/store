package com.ex.store.core.config.load;

import com.ex.store.core.config.access.SecurityAuthorityAccess;
import com.ex.store.sys.mapper.ResourceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @Author wex
 * @Date 2021-1-28 16:40
 * @Desc
 **/
@Component
@Order(2)
public class LoadDictionary implements ApplicationRunner {
    @Autowired
    private ResourceMapper resourceMapper;

    @Autowired
    private SecurityAuthorityAccess securityAuthorityAccess;

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}
