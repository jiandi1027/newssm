package com.cjdjyf.newssm.controller.sys;

import com.cjdjyf.newssm.base.PageBean;
import com.cjdjyf.newssm.base.ResultBean;
import com.cjdjyf.newssm.pojo.sys.SysDic;
import com.cjdjyf.newssm.service.sys.SysDicService;
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

    /**
     * @author : cjd
     * @description : 数据字典页面
     * @return : java.lang.String
     * @date : 13:37 2018/5/7
     */
    @GetMapping("/list")
    public String list() {
        return "sys/sysDic/sysDicList";
    }

    /**
     * @author : cjd
     * @description : 数据字典新增页面
     * @params : [id, request]
     * @return : java.lang.String
     * @date : 13:37 2018/5/7
     */
    @GetMapping("/addList")
    public String sysDicAddList(String id, HttpServletRequest request) {
        SysDic sysDic = sysDicService.findById(id);
        request.setAttribute("sysDic", sysDic);
        return "sys/sysDic/sysDicAddList";
    }

    /**
     * @author : cjd
     * @description : 数据字典列表数据
     * @params : [sysDic, request]
     * @return : com.cjdjyf.newssm.base.PageBean<com.cjdjyf.newssm.pojo.sys.SysDic>
     * @date : 13:37 2018/5/7
     */

    @PostMapping("/list")
    @ResponseBody
    public PageBean<SysDic> forList(SysDic sysDic, HttpServletRequest request) {
        //如果传入部门为空 就查自己部门下属的数据
     /*   if (StringUtils.isEmpty(sysDic.getLoginGroupId())) {
            sysDic.setLoginGroupId(MyUtils.getGroupId());
        }*/
        return sysDicService.findPageBean(sysDic);
    }

    /**
     * @author : cjd
     * @description : 数据字典编辑
     * @params : [sysDic]
     * @return : com.cjdjyf.newssm.base.ResultBean<java.lang.String>
     * @date : 13:37 2018/5/7
     */
    @PostMapping("save")
    @ResponseBody
    public ResultBean<String> save(SysDic sysDic) {
        return new ResultBean<>(sysDicService.save(sysDic));
    }

    /**
     * @author : cjd
     * @description : 数据字典删除
     * @params : [sysDic]
     * @return : com.cjdjyf.newssm.base.ResultBean<java.lang.String>
     * @date : 13:37 2018/5/7
     */
    @PostMapping("del")
    @ResponseBody
    public ResultBean<String> del(SysDic sysDic) {
        return new ResultBean<>(sysDicService.del(sysDic));
    }

    /**
     * @author : cjd
     * @description : 获取数据字典值
     * @params : [sysDic]
     * @return : com.cjdjyf.newssm.base.ResultBean<java.util.List<com.cjdjyf.newssm.pojo.sys.SysDic>>
     * @date : 13:37 2018/5/7
     */
    @PostMapping("getValues")
    @ResponseBody
    public ResultBean<List<SysDic>> getValues(SysDic sysDic) {
        return new ResultBean<>(sysDicService.findAll(sysDic));
    }

    /**
     * @Author : cjd
     * @Description : 根据父ID和KEY找VALUE
     * @params :[sysDic]
     * @return :com.cjdjyf.newssm.base.ResultBean<java.lang.String>
     * @Date : 13:03 2018/5/22
     */
    @PostMapping("getValue")
    @ResponseBody
    public ResultBean<String> getValue(SysDic sysDic) {
        return new ResultBean<>(sysDicService.getValueByParentKeyAndKey(sysDic.getParentKey(),sysDic.getKey()));
    }
}
