package com.ex.store.sys.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @Author wex
 * @Date 2021-1-25 11:22
 * @Desc
 **/
public class Test {

    public static void main(String[] args) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode("111111");
        System.out.println(encode);
    }
}
