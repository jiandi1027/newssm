package com.cjdjyf.newssm.utils;

import com.cjdjyf.newssm.pojo.sys.SysAccount;
import com.lowagie.text.pdf.BaseFont;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.junit.Test;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.util.*;

/**
 * @author : cjd
 * @description : 工具类
 * @date : 22:39 2018/3/7
 */
public class MyUtils {
    /**
     * @return :java.lang.String
     * @Author : cjd
     * @Description : 获取登录账号名
     * @params :[]
     * @Date : 10:54 2018/4/19
     */
    public static String getUserName() {
        return (String) SecurityUtils.getSubject().getPrincipal();
    }

    /**
     * @return :java.lang.String
     * @Author : cjd
     * @Description : 获取UUID
     * @params :[]
     * @Date : 10:54 2018/4/19
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * @return : java.lang.String
     * @author : cjd
     * @description : 获取登录账号角色ID 多角色按,分割
     * @date : 15:24 2018/5/8
     */
    public static String getRoleId() {
        return (String) SecurityUtils.getSubject().getSession().getAttribute("roleId");
    }

    /**
     * @return :java.lang.String
     * @Author : cjd
     * @Description : 获取登录账号权限ID 按，分割
     * @Date : 9:23 2018/5/28
     */
    public static String getPermissionId() {
        return (String) SecurityUtils.getSubject().getSession().getAttribute("permissionId");
    }

    public static String getPermissionName() {
        return (String) SecurityUtils.getSubject().getSession().getAttribute("permissionName");
    }

    /**
     * @return : java.lang.String
     * @author : cjd
     * @description : 获取登录账号角色名
     * @date : 13:59 2018/5/10
     */
    public static String getRoleName() {
        return (String) SecurityUtils.getSubject().getSession().getAttribute("roleName");
    }

    /**
     * @return : java.lang.Boolean
     * @author : cjd
     * @description : 判断两字符串按,分割后有无交集
     * @params : [stringOne, stringTwo]
     * @date : 13:59 2018/5/10
     */
    public static Boolean stringContain(String stringOne, String stringTwo) {
        HashSet<String> hashSet1 = new HashSet<>();
        HashSet<String> hashSet2 = new HashSet<>();

        for (String s : stringOne.split(",")) {
            hashSet1.add(s);
        }
        for (String s : stringTwo.split(",")) {
            hashSet2.add(s);
        }
        Iterator<String> iterator = hashSet2.iterator();
        while (iterator.hasNext()) {
            if (!hashSet1.add(iterator.next())) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return : java.lang.String
     * @author : cjd
     * @description : 获取当前账号部门ID
     * @date : 15:24 2018/5/8
     */
    public static String getGroupId() {
        SysAccount user = (SysAccount) SecurityUtils.getSubject().getSession().getAttribute("user");
        return user.getGroupId();
    }

    public static SysAccount getUser() {
        return (SysAccount) SecurityUtils.getSubject().getSession().getAttribute("user");
    }

    /**
     * @return : java.lang.String
     * @author : cjd
     * @description : 获取当前账号的部门 如XX省AA部  得到AA部
     * @date : 14:01 2018/5/10
     */
    public static String getGroupName() {
        SysAccount user = (SysAccount) SecurityUtils.getSubject().getSession().getAttribute("user");
        String[] split = user.getGroupName().split("-");
        return split[split.length - 1];
    }

    /**
     * @return : java.lang.String
     * @author : cjd
     * @description : 获取完整部门名
     * @date : 10:18 2018/5/11
     */
    public static String getFullGroupName() {
        SysAccount user = (SysAccount) SecurityUtils.getSubject().getSession().getAttribute("user");
        return user.getGroupName();
    }


    /**
     * @return : java.lang.String
     * @author : cjd
     * @description : 获取当前账号部门级别
     * @params : []
     * @date : 13:43 2018/5/10
     */
    public static String getGroupLeave() {
        SysAccount user = (SysAccount) SecurityUtils.getSubject().getSession().getAttribute("user");
        return user.getGroupLevel();
    }

    /**
     * @return : java.lang.String
     * @author : cjd
     * @description : 获取MD5加密字符串 根据string生成盐 加密source
     * @params : [string, source]
     * @date : 12:03 2018/4/26
     */
    public static String GetMD5(String string, String source) {
        String hashAlgorithmName = "MD5";
        ByteSource salt = ByteSource.Util.bytes(string);
        int hashIterations = 1024;
        Object obj = new SimpleHash(hashAlgorithmName, source, salt, hashIterations);
        return obj.toString();
    }

    /**
     * @return : java.lang.String
     * @author : cjd
     * @description : 首字母大写 用于数据库表转换实体类
     * @params : [strings]
     * @date : 20:52 2018/5/4
     */
    public static String toUpperCaseFirst(String[] strings) {
        StringBuilder upper = new StringBuilder("");
        for (String s : strings) {
            upper.append(s.substring(0, 1).toUpperCase().concat(s.substring(1).toLowerCase()));
        }
        return upper.toString();
    }

    /**
     * @return : boolean
     * @author : cjd
     * @description :  删除文件夹
     * @params : [path]
     * @date : 20:28 2018/5/4
     */
    public static boolean deleteDir(String path) {
        File file = new File(path);
        if (!file.exists()) {//判断是否待删除目录是否存在
            System.err.println("The dir are not exists!");
            return false;
        }

        String[] content = file.list();//取得当前目录下所有文件和文件夹
        for (String name : content) {
            File temp = new File(path, name);
            if (temp.isDirectory()) {//判断是否是目录
                deleteDir(temp.getAbsolutePath());//递归调用，删除目录里的内容
                temp.delete();//删除空目录
            } else {
                if (!temp.delete()) {//直接删除文件
                    System.err.println("Failed to delete " + name);
                }
            }
        }
        return true;
    }

    /**
     * @return : void
     * @author : cjd
     * @description : 替换文件中的指定内容
     * @params : [filePath, srcStr, desStr]
     * @date : 9:47 2018/5/21
     */
    public static void propertiesChange(String filePath, String srcStr, String desStr) {
        //字符流
        FileReader fr = null;
        FileWriter fw = null;
        //缓冲流
        BufferedReader br = null;
        BufferedWriter bw = null;

        List list = new ArrayList<>();
        //读取文件内容保证在list中
        try {
            fr = new FileReader(new File(filePath));
            br = new BufferedReader(fr);
            String line = br.readLine();    //逐行复制
            while (line != null) {
                //修改指定内容
                if (line.contains(srcStr)) {
                    line = line.replace(srcStr, desStr);
                }
                list.add(line);
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭流，顺序与打开相反
                br.close();
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //将list中内容输出到原文件中
        try {
            fw = new FileWriter(filePath);
            bw = new BufferedWriter(fw);
            for (Object s : list) {
                bw.write((String) s);
                bw.newLine();  //换行输出
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭流，顺序与打开相反
                bw.close();
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * @return :void
     * @Author : cjd
     * @Description : 递归生成目录
     * @params :[file]
     * @Date : 9:59 2018/5/22
     */
    public static void mkDir(File file) {
        if (file.getParentFile().exists()) {
            file.mkdir();
        } else {
            mkDir(file.getParentFile());
            file.mkdir();
        }
    }

    /**
     * @return : void
     * @author : cjd
     * @description : 防止弄错了密码 直接修改数据库0.0
     * @params : []
     * @date : 17:03 2018/5/3
     */
    @Test
    public void test() {
        System.out.println(GetMD5("admin", "1"));
    }


    @Test
    public void testHtml2Pdf() throws Exception {
        //指定PDF的存放路径
        String outputFile = "11111.pdf";
        OutputStream os = new FileOutputStream(outputFile);
        ITextRenderer renderer = new ITextRenderer();
        ITextFontResolver fontResolver = renderer.getFontResolver();
        //指定字体。为了支持中文字体
        fontResolver.addFont("C:/WINDOWS/Fonts/simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        StringBuffer html = new StringBuffer();
        html.append("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta></meta>\n" +
                "    <title>Issue Payment Receipt</title>\n" +
                "    <style>\n" +
                "        /* @page {\n" +
                "             size: 4.18in 6.88in;\n" +
                "             margin: 0.25in;\n" +
                "             -fs-flow-top: \"header\";\n" +
                "             -fs-flow-bottom: \"footer\";\n" +
                "             -fs-flow-left: \"left\";\n" +
                "             -fs-flow-right: \"right\";\n" +
                "             border: thin solid black;\n" +
                "             padding: 1em;\n" +
                "         }*/\n" +
                "        body {\n" +
                "            font-family: SimSun;\n" +
                "            font-size: 25px;\n" +
                "        }\n" +
                "\n" +
                "        td {\n" +
                "            border: solid #add9c0;\n" +
                "            border-width: 0px 1px 1px 0px;\n" +
                "            width: 500px;\n" +
                "            word-wrap: break-word;\n" +
                "            word-break: break-all;\n" +
                "            text-align: center;\n" +
                "            height: 30px;\n" +
                "        }\n" +
                "\n" +
                "        table {\n" +
                "            border: solid #add9c0;\n" +
                "            border-width: 1px 0px 0px 1px;\n" +
                "        }\n" +
                "        .title td{\n" +
                "            background: green;\n" +
                "            font-size: 30px;\n" +
                "            color: #ffffff;\n" +
                "            font-weight: 900;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div style=\"text-align: center;font-size: 22px;color: red;font-weight:bold;}\">房产租赁合同</div>\n" +
                "<div style=\"height: 25px\"></div>\n" +
                "<table>\n" +
                "    <tr>\n" +
                "        <td>房产名称：</td>\n" +
                "        <td>北京天安门故宫</td>\n" +
                "        <td>房产地段：</td>\n" +
                "        <td>北京市天安门广场一号大街一号啊编不出来了</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td>房产地段：</td>\n" +
                "        <td>城区</td>\n" +
                "        <td>房产类型：</td>\n" +
                "        <td>你猜</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td>房产权属单位：</td>\n" +
                "        <td>我的</td>\n" +
                "        <td>租赁方式：</td>\n" +
                "        <td>整体租赁</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td>房产楼层：</td>\n" +
                "        <td>1层楼</td>\n" +
                "        <td>租赁类型：</td>\n" +
                "        <td>公开租赁</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td>房产面积(m²)：</td>\n" +
                "        <td>10000</td>\n" +
                "        <td>租赁年限(年)：</td>\n" +
                "        <td>10</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td>原年租金(元)：</td>\n" +
                "        <td>0</td>\n" +
                "        <td>新年租金(元)：</td>\n" +
                "        <td>1000000000</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td>使用情况说明：</td>\n" +
                "        <td>买来自己用</td>\n" +
                "        <td>市场调查价格说明：</td>\n" +
                "        <td>很划算</td>\n" +
                "    </tr>\n" +
                "</table>\n" +
                "\n" +
                "\n" +
                "<div style=\"height: 25px\"></div>\n" +
                "<div style=\"text-align: center;font-size: 22px;color: #000000;font-weight:bold;}\">审批记录</div>\n" +
                "<div style=\"height: 25px\"></div>\n" +
                "\n" +
                "<table style=\"margin-top: -3px;\">\n" +
                "    <tr class=\"title\">\n" +
                "        <td style=\"width: 20%\">操作人</td>\n" +
                "        <td style=\"width: 15%\">操作</td>\n" +
                "        <td style=\"width: 25%\">操作时间</td>\n" +
                "        <td style=\"width: 20%\">任务名称</td>\n" +
                "        <td style=\"width: 20%\">意见</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td>fgfz</td>\n" +
                "        <td>同意</td>\n" +
                "        <td>2018-06-05 15:57:18</td>\n" +
                "        <td>部门审批</td>\n" +
                "        <td>好的</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td>fgfz</td>\n" +
                "        <td>同意</td>\n" +
                "        <td>2018-06-05 15:57:18</td>\n" +
                "        <td>部门审批</td>\n" +
                "        <td>好的</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td>fgfz</td>\n" +
                "        <td>同意</td>\n" +
                "        <td>2018-06-05 15:57:18</td>\n" +
                "        <td>部门审批</td>\n" +
                "        <td>好的</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td>fgfz</td>\n" +
                "        <td>同意</td>\n" +
                "        <td>2018-06-05 15:57:18</td>\n" +
                "        <td>部门审批</td>\n" +
                "        <td>好的</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td>fgfz</td>\n" +
                "        <td>同意</td>\n" +
                "        <td>2018-06-05 15:57:18</td>\n" +
                "        <td>部门审批</td>\n" +
                "        <td>好的</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td>fgfz</td>\n" +
                "        <td>同意</td>\n" +
                "        <td>2018-06-05 15:57:18</td>\n" +
                "        <td>部门审批</td>\n" +
                "        <td>好的</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td>fgfz</td>\n" +
                "        <td>同意</td>\n" +
                "        <td>2018-06-05 15:57:18</td>\n" +
                "        <td>部门审批</td>\n" +
                "        <td>好的</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td>fgfz</td>\n" +
                "        <td>同意</td>\n" +
                "        <td>2018-06-05 15:57:18</td>\n" +
                "        <td>部门审批</td>\n" +
                "        <td>好的</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td>fgfz</td>\n" +
                "        <td>同意</td>\n" +
                "        <td>2018-06-05 15:57:18</td>\n" +
                "        <td>部门审批</td>\n" +
                "        <td>好的</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td>fgfz</td>\n" +
                "        <td>同意</td>\n" +
                "        <td>2018-06-05 15:57:18</td>\n" +
                "        <td>部门审批</td>\n" +
                "        <td>好的</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td>fgfz</td>\n" +
                "        <td>同意</td>\n" +
                "        <td>2018-06-05 15:57:18</td>\n" +
                "        <td>部门审批</td>\n" +
                "        <td>好的</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td>fgfz</td>\n" +
                "        <td>同意</td>\n" +
                "        <td>2018-06-05 15:57:18</td>\n" +
                "        <td>部门审批</td>\n" +
                "        <td>好的</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td>fgfz</td>\n" +
                "        <td>同意</td>\n" +
                "        <td>2018-06-05 15:57:18</td>\n" +
                "        <td>部门审批</td>\n" +
                "        <td>好的</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td>fgfz</td>\n" +
                "        <td>同意</td>\n" +
                "        <td>2018-06-05 15:57:18</td>\n" +
                "        <td>部门审批</td>\n" +
                "        <td>好的</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td>fgfz</td>\n" +
                "        <td>同意</td>\n" +
                "        <td>2018-06-05 15:57:18</td>\n" +
                "        <td>部门审批</td>\n" +
                "        <td>好的</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td>fgfz</td>\n" +
                "        <td>同意</td>\n" +
                "        <td>2018-06-05 15:57:18</td>\n" +
                "        <td>部门审批</td>\n" +
                "        <td>好的</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td>fgfz</td>\n" +
                "        <td>同意</td>\n" +
                "        <td>2018-06-05 15:57:18</td>\n" +
                "        <td>部门审批</td>\n" +
                "        <td>好的</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td>fgfz</td>\n" +
                "        <td>同意</td>\n" +
                "        <td>2018-06-05 15:57:18</td>\n" +
                "        <td>部门审批</td>\n" +
                "        <td>好的</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td>fgfz</td>\n" +
                "        <td>同意</td>\n" +
                "        <td>2018-06-05 15:57:18</td>\n" +
                "        <td>部门审批</td>\n" +
                "        <td>好的</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td>fgfz</td>\n" +
                "        <td>同意</td>\n" +
                "        <td>2018-06-05 15:57:18</td>\n" +
                "        <td>部门审批</td>\n" +
                "        <td>好的</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td>fgfz</td>\n" +
                "        <td>同意</td>\n" +
                "        <td>2018-06-05 15:57:18</td>\n" +
                "        <td>部门审批</td>\n" +
                "        <td>好的</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td>fgfz</td>\n" +
                "        <td>同意</td>\n" +
                "        <td>2018-06-05 15:57:18</td>\n" +
                "        <td>部门审批</td>\n" +
                "        <td>好的</td>\n" +
                "    </tr>\n" +
                "</table>\n" +
                "\n" +
                "</body>\n" +
                "</html>");
        renderer.setDocumentFromString(html.toString());
        // 解决图片的相对路径问题
        renderer.layout();
        renderer.createPDF(os);
        renderer.finishPDF();
        renderer = null;
        os.close();
    }

}
