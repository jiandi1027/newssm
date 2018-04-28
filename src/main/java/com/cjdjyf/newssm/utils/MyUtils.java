package com.cjdjyf.newssm.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.junit.Test;

import java.util.UUID;

/**
 * @author : cjd
 * @description : 工具类
 * @date : 22:39 2018/3/7
 */
public class MyUtils {
    /**
     * @return :java.lang.String
     * @Author : cjd
     * @Description : 获取登录账号名
     * @params :[]
     * @Date : 10:54 2018/4/19
     */
    public static String getSysAccount() {
        return (String) SecurityUtils.getSubject().getPrincipal();
    }

    /**
     * @return :java.lang.String
     * @Author : cjd
     * @Description : 获取UUID
     * @params :[]
     * @Date : 10:54 2018/4/19
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }


    /**
     * @return : java.lang.String
     * @author : cjd
     * @description : 获取MD5加密字符串 根据string生成盐 加密source
     * @params : [string, source]
     * @date : 12:03 2018/4/26
     */
    public static String GetMD5(String string, String source) {
        String hashAlgorithmName = "MD5";
        ByteSource salt = ByteSource.Util.bytes(string);
        int hashIterations = 1024;
        Object obj = new SimpleHash(hashAlgorithmName, source, salt, hashIterations);
        return obj.toString();
    }

    @Test
    public void test() {
        System.out.println(GetMD5("池剑迪", "1"));
    }
}
