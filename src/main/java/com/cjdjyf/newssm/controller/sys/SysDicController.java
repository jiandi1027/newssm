package com.cjdjyf.newssm.controller.sys;

import com.cjdjyf.newssm.base.PageBean;
import com.cjdjyf.newssm.base.ResultBean;
import com.cjdjyf.newssm.pojo.sys.SysAccount;
import com.cjdjyf.newssm.pojo.sys.SysDic;
import com.cjdjyf.newssm.service.sys.SysDicService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author : cjd
 * @description : 数据字典控制器
 * @date : 2018/5/2 14:06
 */
@Controller
@RequestMapping("/sys/sysDic")
public class SysDicController {
    @Autowired
    private SysDicService sysDicService;

    @GetMapping("/list")
    public String list() {
        return "sys/sysDic/sysDicList";
    }

    @GetMapping("/addList")
    public String sysDicAddList(String id, HttpServletRequest request) {
        SysDic sysDic = sysDicService.findById(id);
        request.setAttribute("sysDic", sysDic);
        return "sys/sysDic/sysDicAddList";
    }

    @PostMapping("/list")
    @ResponseBody
    public PageBean<SysDic> forList(SysDic sysDic, HttpServletRequest request) {
        //如果传入部门为空 就查自己部门下属的数据
        if (StringUtils.isEmpty(sysDic.getLoginGroupId())) {
            SysAccount user = (SysAccount) request.getSession().getAttribute("user");
            sysDic.setLoginGroupId(user.getGroupId());
        }
        return sysDicService.findPageBean(sysDic);
    }

    @PostMapping("save")
    @ResponseBody
    public ResultBean<String> save(SysDic sysDic) {
        return new ResultBean<>(sysDicService.save(sysDic));
    }

    @PostMapping("del")
    @ResponseBody
    public ResultBean<String> del(SysDic sysDic) {
        return new ResultBean<>(sysDicService.del(sysDic));
    }


    @PostMapping("getValue")
    @ResponseBody
    public List<SysDic> getValue(SysDic sysDic) {
        return sysDicService.findAll(sysDic);
    }
}
