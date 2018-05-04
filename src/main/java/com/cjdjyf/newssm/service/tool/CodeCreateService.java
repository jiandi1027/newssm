package com.cjdjyf.newssm.service.tool;

import com.cjdjyf.newssm.base.BaseService;
import com.cjdjyf.newssm.mapper.tool.CodeCreateMapper;
import com.cjdjyf.newssm.pojo.tool.CodeCreate;
import com.cjdjyf.newssm.pojo.tool.MapperCreate;
import com.cjdjyf.newssm.utils.MyUtils;
import com.cjdjyf.newssm.utils.ProjectPathUtil;
import com.cjdjyf.newssm.utils.ZipUtil;
import org.springframework.stereotype.Service;

/**
 * @author : cjd
 * @description : 代码生成Service
 * @date : 2018/5/3 17:23
 */
@Service
public class CodeCreateService extends BaseService<CodeCreateMapper,CodeCreate>{

    public String codeExport(CodeCreate codeCreate) {
        MapperCreate mapperCreate = new MapperCreate(codeCreate.getTableName(), codeCreate.getMapperURL(), codeCreate.getPojoURL());
        //项目根目录下的代码生成文件夹
        String sourcePath = ProjectPathUtil.getProjectPath() + "/codeCreate";
        try {
            mapperCreate.generator();
        } catch (Exception e) {
            return "生成失败";
        }
        String replaceAllMapper = codeCreate.getMapperURL().replaceAll("\\.", "/");
        String mapperJava = sourcePath + "/" + replaceAllMapper + "/" + MyUtils.toUpperCaseFirst(codeCreate.getTableName().split("_")) + "Mapper.java";

        String replaceAllPojo = codeCreate.getPojoURL().replaceAll("\\.", "/");
        String pojoJava = sourcePath + "/" + replaceAllPojo + "/" + MyUtils.toUpperCaseFirst(codeCreate.getTableName().split("_")) + ".java";

        //替换指定字符
        MyUtils.propertiesChange(mapperJava, "Copyright(C)", "Copyright(C) " + codeCreate.getAuthor());
        MyUtils.propertiesChange(pojoJava, "Copyright(C)", "Copyright(C) " + codeCreate.getAuthor());
        MyUtils.propertiesChange(pojoJava, "@author", "@author " + codeCreate.getAuthor());

        //压缩文件
        ZipUtil.zip(sourcePath);
        return "导出成功";
    }
}
