package com.cjdjyf.newssm.service.file;

import com.cjdjyf.newssm.base.BaseService;
import com.cjdjyf.newssm.mapper.file.SysFileMapper;
import com.cjdjyf.newssm.pojo.file.SysFile;
import com.cjdjyf.newssm.utils.MyUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.DecimalFormat;

/**
 * @author : cjd
 * @description : 文件处理Service
 * @date : 2018/5/17 10:52
 */
@Service
public class SysFileService extends BaseService<SysFileMapper, SysFile> {

    /**
     * 允许上传的扩展名
     */
    private static String[] extensionPermit = {"txt", "jpg", "xls", "zip", "doc","png"};

    public void download(HttpServletResponse response, String path) throws IOException {
        String fileName = path.substring(path.lastIndexOf('/') + 1);
        logger.info("用户{}下载文件{}", MyUtils.getUserName(), fileName);
        response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
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

    @Transactional()
    public SysFile upload(CommonsMultipartFile file, String curProjectPath) throws Exception {
        String saveDirectoryPath = curProjectPath + "/" + "upload";
        File saveDirectory = new File(saveDirectoryPath);
        logger.info(saveDirectoryPath);
        logger.info(file.getOriginalFilename());
        logger.info(String.valueOf(file.isEmpty()));

        String name;
        String fileName = file.getOriginalFilename();

        Double size = Double.valueOf(file.getSize()) / 1024 / 1024;
        DecimalFormat dFormat = new DecimalFormat("#.00");
        dFormat.format(size);

        //获取扩展名
        String fileExtension = FilenameUtils.getExtension(fileName);
  /*      if (!Arrays.asList(extensionPermit).contains(fileExtension)) {
            return new SysFile();
        }*/
        //存的文件名为时间加文件名
        name = System.currentTimeMillis() + file.getOriginalFilename();
        FileUtils.copyInputStreamToFile(file.getInputStream(), new File(saveDirectoryPath, name));
        logger.info("UploadController#fileUpload() end");
        //替换路径 download传参用
        String filePath = saveDirectory.getAbsolutePath().toString().replace("\\", "/") + "/" + name;
        SysFile sysFile = new SysFile(dFormat.format(size), filePath, file.getOriginalFilename());
        this.insert(sysFile);
        return sysFile;
    }


    /**
     * @return : void
     * @author : cjd
     * @description : 批量删除
     * @params : [delFiles]
     * @date : 15:46 2018/5/17
     */
    public void delFile(String delFiles) {
        String[] split = delFiles.split(",");
        for (String s : split) {
            SysFile sysFile = new SysFile();
            sysFile.setId(s);
            this.del(sysFile);
        }
    }

    /**
     * @return : void
     * @author : cjd
     * @description : 将id和文件名绑定
     * @params : [id, files]
     * @date : 11:12 2018/5/17
     */
    public void bindFile(String tableId, String files) {
        String[] split = files.split(",");
        for (String s : split) {
            SysFile sysFile = new SysFile();
            sysFile.setId(s);
            sysFile.setTableId(tableId);
            this.updateByIdSelective(sysFile);
        }
    }
}
