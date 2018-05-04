package com.cjdjyf.newssm.utils;

import com.cjdjyf.newssm.pojo.tool.MapperCreate;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
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

    /**
     * @return : boolean
     * @author : cjd
     * @description :  删除文件夹
     * @params : [path]
     * @date : 20:28 2018/5/4
     */
    public static boolean deleteDir(String path) {
        File file = new File(path);
        if (!file.exists()) {//判断是否待删除目录是否存在
            System.err.println("The dir are not exists!");
            return false;
        }

        String[] content = file.list();//取得当前目录下所有文件和文件夹
        for (String name : content) {
            File temp = new File(path, name);
            if (temp.isDirectory()) {//判断是否是目录
                deleteDir(temp.getAbsolutePath());//递归调用，删除目录里的内容
                temp.delete();//删除空目录
            } else {
                if (!temp.delete()) {//直接删除文件
                    System.err.println("Failed to delete " + name);
                }
            }
        }
        return true;
    }

    /**
     * @author : cjd
     * @description : 首字母大写 用于数据库表转换实体类
     * @params : [strings]
     * @return : java.lang.String
     * @date : 20:52 2018/5/4
     */
    public static String toUpperCaseFirst(String[] strings) {
        StringBuilder upper = new StringBuilder("");
        for (String s : strings) {
            upper.append(s.substring(0, 1).toUpperCase().concat(s.substring(1).toLowerCase()));
        }
        return upper.toString();
    }

    /*
  * 修改文件中的指定内容
  * */
    public static void propertiesChange(String filePath, String srcStr, String desStr) {
        //字符流
        FileReader fr = null;
        FileWriter fw = null;
        //缓冲流
        BufferedReader br = null;
        BufferedWriter bw = null;

        List list = new ArrayList<>();
        //读取文件内容保证在list中
        try {
            fr = new FileReader(new File(filePath));
            br = new BufferedReader(fr);   //扩容，类似加水管
            String line = br.readLine();    //逐行复制
            while (line != null) {
                //修改指定内容
                if (line.contains(srcStr)) {
                    line = line.replace(srcStr, desStr);
                }
                list.add(line);
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭流，顺序与打开相反
                br.close();
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //将list中内容输出到原文件中
        try {
            fw = new FileWriter(filePath);
            bw = new BufferedWriter(fw);
            for (Object s : list) {
                bw.write((String) s);
                bw.newLine();  //换行输出
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭流，顺序与打开相反
                bw.close();
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * @return : void
     * @author : cjd
     * @description : 防止弄错了密码 直接修改数据库0.0
     * @params : []
     * @date : 17:03 2018/5/3
     */
    @Test
    public void test() {
        System.out.println(GetMD5("池剑迪", "1"));
    }

    @Test
    public void tes1t() throws Exception {
        MapperCreate generatorSqlmap = new MapperCreate("sys_group", "com.cjdjyf.newssm.sys.mapper", "com.cjdjyf.newssm.sys.pojo");
        generatorSqlmap.generator();
    }
}
