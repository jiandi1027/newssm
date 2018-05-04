package com.cjdjyf.newssm.utils;

import java.io.File;
import java.net.URLDecoder;

/**
 * @author : cjd
 * @description : 获取项目根路径
 * @date : 20:01 2018/5/4
 */
public class ProjectPath {

    private static String rootPath = "";

    private ProjectPath() {
        init();
    }

    @SuppressWarnings("deprecation")
    private static void init() {
        String path = ProjectPath.class.getResource("ProjectPath.class").toString();
        path = URLDecoder.decode(path);
        path.replaceAll("\\\\", "/");
        int index = path.indexOf("WEB-INF");
        if (index == -1) {
            index = path.indexOf("bin");
        }
        if (index == -1) {
            index = path.indexOf("lib");
        }
        if (index == -1) {
            int index2 = path.indexOf("jar!");
            if (index2 != -1) {
                path = path.substring(0, index2);
                System.out.println(path);
                System.out.println(File.separator);
                index = path.lastIndexOf("/");
                System.out.println(index);
            }
        }
        path = path.substring(0, index);
        if (path.startsWith("jar")) {
            path = path.substring(9);
        }
        if (path.startsWith("file")) {
            path = path.substring(5);
        }
        if (path.endsWith("/") || path.endsWith("\\")) {
            path = path.substring(0, path.length() - 1);
        }
        // linux环境下第一个/是需要的  
        String os = System.getProperty("os.name").toLowerCase();
        if (os.startsWith("win")) {
            path = path.substring(1);
        }
        rootPath = path;
    }

    /**
     * 获取应用的根目录，路径分隔符为/，路径结尾无/
     *
     * @return
     */
    public static String getProjectPath() {
        if ("".equals(rootPath)) {
            init();
        }
        return rootPath;
    }

}  