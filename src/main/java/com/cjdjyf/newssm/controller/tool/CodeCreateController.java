package com.cjdjyf.newssm.controller.tool;

import com.cjdjyf.newssm.base.PageBean;
import com.cjdjyf.newssm.base.ResultBean;
import com.cjdjyf.newssm.pojo.tool.CodeCreate;
import com.cjdjyf.newssm.pojo.tool.MapperCreate;
import com.cjdjyf.newssm.service.tool.CodeCreateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/tool/codeCreate")
public class CodeCreateController {
    @Autowired
    private CodeCreateService codeCreateService;

    /**
     * @return : java.lang.String
     * @author : cjd
     * @description : 代码生成页面
     * @date : 13:16 2018/5/4
     */
    @GetMapping("/list")
    public String list() {
        return "tool/codeCreate/codeCreateList";
    }

    /**
     * @return : java.lang.String
     * @author : cjd
     * @description : 代码生成dialog页面
     * @date : 13:17 2018/5/4
     */
    @GetMapping("/dataList")
    public String dataList(String tableName, HttpServletRequest request) {
        request.setAttribute("tableName",tableName);
        return "tool/codeCreate/codeCreateDataList";
    }

    /**
     * @return : com.cjdjyf.newssm.base.PageBean<com.cjdjyf.newssm.pojo.tool.CodeCreate>
     * @author : cjd
     * @description : 代码生成页面数据 -获取所有表格
     * @params : [codeCreate, request]
     * @date : 13:17 2018/5/4
     */
    @PostMapping("/list")
    @ResponseBody
    public PageBean<CodeCreate> forList(CodeCreate codeCreate, HttpServletRequest request) {
        return codeCreateService.findPageBean(codeCreate);
    }


    @PostMapping("/codeExport")
    @ResponseBody
    public ResultBean<String> codeExport(CodeCreate codeCreate) {
        MapperCreate mapperCreate = new MapperCreate(codeCreate.getTableName(), codeCreate.getMapperURL(), codeCreate.getPojoURL());
        try {
            mapperCreate.generator();
        } catch (Exception e) {
            return new ResultBean<>("生成失败");
        }
        return new ResultBean<>("导出成功");
    }

}
