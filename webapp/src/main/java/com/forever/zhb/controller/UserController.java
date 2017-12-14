package com.forever.zhb.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.forever.zhb.Constants;
import com.forever.zhb.criteria.ForeverCriteria;
import com.forever.zhb.dic.DeleteFlagEnum;
import com.forever.zhb.pojo.User;
import com.forever.zhb.service.IForeverManager;
import com.forever.zhb.utils.ImageUtils;
import com.forever.zhb.utils.PropertyUtil;
import com.forever.zhb.vo.FileVo;

public class UserController extends MultiActionController {
    
	private IForeverManager foreverManager;

    protected Log log = LogFactory.getLog(this.getClass());
    
    public String toAddUser(HttpServletRequest request,HttpServletResponse response){
        return "htgl.user.addUser";
    }
    
    public String addUser(HttpServletRequest request,HttpServletResponse response,User user){
        user.setDeleteFlag(DeleteFlagEnum.UDEL.getIndex());
        foreverManager.addUser(user);
        List<User> users = foreverManager.getAllUser();
        request.setAttribute("users", users);
        return "htgl.user.users";
    }
    
    public String getAllUser(HttpServletRequest request,HttpServletResponse response){
        List<User> users = foreverManager.getAllUser();
        request.setAttribute("users", users);
        return "htgl.user.users";
    }
    
    public String getUsersByConditions(HttpServletRequest request,HttpServletResponse response){
        List<ForeverCriteria> conditions = new ArrayList<ForeverCriteria>();
        conditions.add(ForeverCriteria.eq("age", "25"));
        List<User> users = foreverManager.getUsersByConditions(conditions);
        request.setAttribute("users", users);
        return "htgl.user.users";
    }
    
    public void delUser(String id,HttpServletResponse response){
        String result = "{\"result\":\"error\"}";
        if(foreverManager.delUser(id)){
            result = "{\"result\":\"success\"}";
        }
        PrintWriter out = null;
        response.setContentType("application/json");
        
        try {
            out = response.getWriter();
            out.write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public String toUpload(HttpServletRequest request,HttpServletResponse response){
        return "/htgl/upload";
    } 
    
    public void upload(HttpServletRequest request,HttpServletResponse response, User user) throws IOException{
        if(null != user){
            log.info("userName: " + user.getUserName());
        }
        String ctxPath = request.getContextPath();
        String uploadPath = PropertyUtil.getUploadPath() + File.separator + Constants.TARGET_NAME;
        File filePath = new File(uploadPath);
        if (!filePath.exists()) {
            filePath.mkdirs();
        }
        InputStream licInput=null;
        OutputStream licOutput=null;
        // 转型为MultipartHttpRequest：   
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;   
        // 获得文件：   
        MultipartFile license = multipartRequest.getFile("firstFile");
        if(license.getSize()>1024*1024){
            log.info("附件大小不符合");
            //response.sendRedirect(ctxPath + "/busCertUnlockRequestController.do?toUploadError");
            //return;
        }
        try{
            // 获得文件名：   
            String licFileExtName = license.getOriginalFilename();
            String uploadPathFile = uploadPath + File.separator + licFileExtName;
            // 获得输入流：   
            licInput = license.getInputStream(); 
            File licName = new File(uploadPathFile);
            if (licName.exists()) {
                licName.delete();
            }
            licOutput = new FileOutputStream(licName);
            byte[] b = new byte[1024 * 5]; 
            int len; 
            while ( (len = licInput.read(b)) != -1) { 
                licOutput.write(b, 0, len); 
            } 
            licOutput.flush(); 
        }catch(Exception e){
            log.info("上传附件出错");
            e.printStackTrace();
            request.setAttribute("errorMsg", "上传出错");
            response.sendRedirect(ctxPath + "/htgl/user?action=toError");
            return ;
        }finally{
            try {
                if (null != licOutput) {
                    licOutput.close();
                }
                if (null != licInput) {
                    licInput.close();
                }
            } catch (IOException e) {
                log.info("关闭流出错");
                e.printStackTrace();
            } 
        }
        response.sendRedirect(ctxPath + "/htgl/user?action=toDownload");
        return ;
    }
    
    public String toDownload(HttpServletRequest request,HttpServletResponse response){
        List<FileVo> fileVos = new ArrayList<FileVo>();
        String filePath = PropertyUtil.getUploadPath() + File.separator 
                + Constants.TARGET_NAME + File.separator;
        File pictures = new File(filePath);
        String[] files = pictures.list();
        if (null != files) {
            for (String fileName : files) {
                FileVo vo = new FileVo();
                vo.setName(fileName);
                fileVos.add(vo);
            }
        }
        request.setAttribute("fileVos", fileVos);
        return "htgl/download";
    }
    
    public void download(HttpServletRequest request,HttpServletResponse response,String fileName){
        if (StringUtils.isBlank(fileName)) {
            //return;
        }
        String temp = request.getParameter("fileName");
        String filePath = PropertyUtil.getUploadPath() + File.separator 
                + Constants.TARGET_NAME + File.separator ;
        File pictures = new File(filePath);
        String[] files = pictures.list();
        if (null != files) {
            for (String name : files) {
                if (name.equals(fileName)) {
                    fileName = name;
                }
            }
        }
        filePath += fileName;
        response.setContentType("image/jpeg");
        FileInputStream fis = null;
        ServletOutputStream sos = null;
        File file = new File(filePath);
        try {
            fis = new FileInputStream(file);
            sos = response.getOutputStream();
            /*int lenght;
            byte[] buf = new byte[1024];
            while((lenght = fis.read(buf, 0, 1024)) != -1){
                sos.write(buf,0,lenght);
            }*/
            ImageUtils.pressText(fis, sos, 0.3f, 3, 3, new String[]{"旭宝宝"});
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (null != sos) {
                try {
                    sos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != fis) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    /*public void exportExcel(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	List<List<Object[]>> users = userManager.exportExcel();
        response.setHeader("Cache-Control", "no-cache");
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", String.format("attachment;filename=\"%s.xls\"", 20170929));
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");
        ExcelJxlUtil xls = ExcelJxlUtil.getInstance(response.getOutputStream(), null);
        xls.generate(users);
        response.getOutputStream().flush();   
    }*/
    	
    public String toError(HttpServletRequest request,HttpServletResponse response){
        
        return "common/error";
    }
    
    //操作MongoDB数据库
    public void operateMongoDB(HttpServletRequest request,HttpServletResponse response){
        foreverManager.operateMongoDB();
    }

    public IForeverManager getForeverManager() {
        return foreverManager;
    }

    public void setForeverManager(IForeverManager foreverManager) {
        this.foreverManager = foreverManager;
    }
    
}
