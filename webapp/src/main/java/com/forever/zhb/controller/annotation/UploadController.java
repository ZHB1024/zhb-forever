package com.forever.zhb.controller.annotation;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.forever.zhb.Constants;
import com.forever.zhb.criteria.ForeverCriteria;
import com.forever.zhb.dic.DeleteFlagEnum;
import com.forever.zhb.dic.FileTypeEnum;
import com.forever.zhb.pojo.FileInfoData;
import com.forever.zhb.pojo.User;
import com.forever.zhb.service.IForeverManager;
import com.forever.zhb.utils.PropertyUtil;

@Controller
@RequestMapping("/htgl/uploadController")
public class UploadController {
    
    protected Log log = LogFactory.getLog(UploadController.class);
    
    @Resource(name="foreverManager")
    private IForeverManager foreverManager;
    
    /*toUpload*/
    @RequestMapping("/toUpload")
    public String toUpload(HttpServletRequest request,HttpServletResponse response){
        return "htgl.upload.index";
    }
    
    /*upload*/
    public void upload(HttpServletRequest request,HttpServletResponse response) throws IOException{
        String ctxPath = request.getContextPath();
        String filePath = PropertyUtil.getUploadPath() + File.separator 
                + Constants.TARGET_NAME + File.separator +Constants.IMAGE_PATH ;
        File fileUpload = new File(filePath);
        if (!fileUpload.exists()) {
            fileUpload.mkdirs();
        }
        InputStream licInput=null;
        OutputStream licOutput=null;
        // 转型为MultipartHttpRequest：   
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;   
        // 获得文件：   
        MultipartFile license = multipartRequest.getFile("firstFile");
        Long fileSize = license.getSize();
        String fileType = license.getContentType();
        String fileName = "";
        if(license.getSize()>1024*1024*5){
            log.info("文件大小不能超过5M");
            response.sendRedirect(ctxPath + "/busCertUnlockRequestController.do?toUploadError");
            return;
        }
        try{
            // 获得文件名：   
            fileName = license.getOriginalFilename();
            String uploadPathFile = filePath + File.separator + fileName;
            // 获得输入流：   
            licInput = license.getInputStream(); 
            File licName = new File(uploadPathFile);
            /*if (licName.exists()) {
                licName.delete();
            }*/
            licOutput = new FileOutputStream(licName);
            byte[] b = new byte[1024]; 
            int len; 
            while ( (len = licInput.read(b)) != -1) { 
                licOutput.write(b, 0, len); 
            } 
            licOutput.flush(); 
           FileInfoData fileInfoData = new FileInfoData();
           fileInfoData.setCreateTime(Calendar.getInstance());
           fileInfoData.setDeleteFlag(DeleteFlagEnum.UDEL.getIndex());
           fileInfoData.setFilePath(filePath);
           fileInfoData.setFileSize(fileSize);
           fileInfoData.setFileType(fileType);
           fileInfoData.setFileName(fileName);
           fileInfoData.setType(FileTypeEnum.IMAGE.getIndex());
           foreverManager.saveFileInfoData(fileInfoData);
           //查询
           List<ForeverCriteria> conditions = new ArrayList<ForeverCriteria>();
           conditions.add(ForeverCriteria.eq("fileName", ""));
           List<FileInfoData> fileInfoDatas = foreverManager.getFileInfoDataByIdOrName(conditions);
        }catch(Exception e){
            log.info("上传附件出错");
            e.printStackTrace();
            request.setAttribute(Constants.REQUEST_ERROR, "上传出错");
            response.sendRedirect(ctxPath + "/htgl/errorController/toError");
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

}
