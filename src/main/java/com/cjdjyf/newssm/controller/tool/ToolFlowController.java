package com.cjdjyf.newssm.controller.tool;

import com.cjdjyf.newssm.base.ResultBean;
import com.cjdjyf.newssm.pojo.tool.toolFlow.ToolFlow;
import com.cjdjyf.newssm.service.tool.ToolFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author : cjd
 * @description : 流程管理控制器
 * @date : 2018/5/22 14:02
 */
@Controller
@RequestMapping("/tool/toolFlow")
public class ToolFlowController {
    @Autowired
    private ToolFlowService toolFlowService;

    @GetMapping("/list")
    public String list() {
        return "tool/toolFlow/toolFlowList";
    }

    @PostMapping("/list")
    @ResponseBody
    public ResultBean<List<ToolFlow>> forList() {
        return new ResultBean<>(toolFlowService.getToolFlow());
    }

    @PostMapping("/deploy")
    @ResponseBody
    public ResultBean<String> deploy(MultipartFile[] files) {
        return new ResultBean<>(toolFlowService.deploy(files));
    }

    @PostMapping("del")
    @ResponseBody
    public ResultBean<String> del(String key) {
        return new ResultBean<>(toolFlowService.del(key));
    }
}
