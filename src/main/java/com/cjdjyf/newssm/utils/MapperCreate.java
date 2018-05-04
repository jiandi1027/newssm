package com.cjdjyf.newssm.utils;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : cjd
 * @description : 代码生成工具类
 * @date : 2018/5/4 10:30
 */
public class MapperCreate {
    private String tableName;
    private String mapperURL;
    private String POJOURL;
    private String baseDao;
    //生成代码存放目录
    private final String SAVE_ADDRESS = ".\\src\\code";

    public MapperCreate(String tableName, String mapperURL, String POJOURL, String baseDao) {
        this.tableName = tableName;
        this.mapperURL = mapperURL;
        this.POJOURL = POJOURL;
        this.baseDao = baseDao;
    }

    public void generator() throws Exception {
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        //指定 逆向工程配置文件
        URL url = getClass().getClassLoader().getResource("generatorConfig.xml");

        File configFile = new File(url.getFile());
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        for (Context context : config.getContexts()) {
            //mapper地址
            context.getSqlMapGeneratorConfiguration().setTargetProject(SAVE_ADDRESS);
            context.getSqlMapGeneratorConfiguration().setTargetPackage(mapperURL);
            context.getJavaClientGeneratorConfiguration().setTargetProject(SAVE_ADDRESS);
            context.getJavaClientGeneratorConfiguration().setTargetPackage(mapperURL);
            //实体类地址
            context.getJavaModelGeneratorConfiguration().setTargetProject(SAVE_ADDRESS);
            context.getJavaModelGeneratorConfiguration().setTargetPackage(POJOURL);
            //设置表名
            context.getTableConfigurations().get(0).setTableName(tableName);
            String[] split = tableName.split("_");
            StringBuilder upper = new StringBuilder("");
            for (String s : split) {
                upper.append(s.substring(0, 1).toUpperCase().concat(s.substring(1).toLowerCase()));
            }
            context.getTableConfigurations().get(0).setDomainObjectName(tableName);
            context.getTableConfigurations().get(0).setDomainObjectName(upper.toString());

        }

        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
    }


}
