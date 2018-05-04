package com.cjdjyf.newssm.controller.tool;

import com.cjdjyf.newssm.base.PageBean;
import com.cjdjyf.newssm.base.ResultBean;
import com.cjdjyf.newssm.pojo.tool.CodeCreate;
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

    @GetMapping("/list")
    public String list() {
        return "tool/codeCreate/codeCreateList";
    }

    @PostMapping("/list")
    @ResponseBody
    public PageBean<CodeCreate> forList(CodeCreate codeCreate, HttpServletRequest request) {
        return codeCreateService.findPageBean(codeCreate);
    }

    @PostMapping("/create")
    @ResponseBody
    public ResultBean<String > create(CodeCreate codeCreate, HttpServletRequest request) {
        return new ResultBean<>("1111");
    }

}
