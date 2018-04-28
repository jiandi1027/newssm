package com.cjdjyf.newssm.controller.file;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;


/**
 * @Author : cjd
 * @Description : 文件下载控制器
 * @Date : 22:39 2018/3/7
 */
@Controller
@RequestMapping("/sys/file")
public class FileDownloadController {
    @RequestMapping(value = "download", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public void download(HttpServletResponse response, String path) throws Exception {
        response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode("xxxx.xls", "UTF-8"));
        FileInputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(path);
            out = response.getOutputStream();
            byte buffer[] = new byte[1024];
            int len;
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
        } catch (Exception ignored) {
        } finally {
            assert in != null;
            in.close();
            assert out != null;
            out.close();
        }
    }
}
