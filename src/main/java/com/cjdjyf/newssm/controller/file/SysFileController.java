package com.cjdjyf.newssm.controller.file;

import com.cjdjyf.newssm.base.ResultBean;
import com.cjdjyf.newssm.pojo.file.SysFile;
import com.cjdjyf.newssm.service.file.SysFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * @Author : cjd
 * @Description : 文件控制器
 * @Date : 22:39 2018/3/7
 */
@Controller
@RequestMapping("/sys/sysFile")
public class SysFileController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private SysFileService sysFileService;


    /**
     * @return : void
     * @author : cjd
     * @description : 根据文件路径下载文件
     * @params : [response, path]
     * @date : 10:44 2018/5/16
     */
    @GetMapping(value = "download", produces = "text/html;charset=UTF-8")
    public void download(HttpServletResponse response, String path) throws Exception {
        //中文乱码
        path = java.net.URLDecoder.decode(path, "utf-8");
        sysFileService.download(response, path);
    }


    /**
     * @return : com.cjdjyf.newssm.base.ResultBean<com.cjdjyf.newssm.pojo.file.SysFile>
     * @author : cjd
     * @description : 文件上传 IE的会出现JSON下载 所以这里手动置顶response
     * @params : [file, session]
     * @date : 12:28 2018/5/17
     */
    @PostMapping(value = "upload", produces = {"text/html;charset=UTF-8"})
    public void fileUpload(@RequestParam("myFiles") CommonsMultipartFile file,
                                          HttpSession session, HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=utf-8");
        String curProjectPath = session.getServletContext().getRealPath("/");
        SysFile sysFile = sysFileService.upload(file, curProjectPath);
        response.getWriter().write("{\"id\":\""+sysFile.getId()+"\",\"fileName\":\""+sysFile.getFileName()
                +"\",\"fileSize\":\""+sysFile.getFileSize()+"\",\"filePath\":\""+sysFile.getFilePath()+"\",\"createPeople\":\""+sysFile.getCreatePeople()+"\"}");
      /*  return new ResultBean<>();*/
    }

    /**
     * @return : com.cjdjyf.newssm.base.ResultBean<java.lang.String>
     * @author : cjd
     * @description : 删除文件
     * @params : [sysPermission]
     * @date : 12:28 2018/5/17
     */
    @PostMapping("del")
    @ResponseBody
    public ResultBean<String> del(SysFile sysFile) {
        return new ResultBean<>(sysFileService.del(sysFile));
    }

}
