package com.forever.zhb.controller.annotation;

import com.forever.zhb.Constants;
import com.forever.zhb.dic.DeleteFlagEnum;
import com.forever.zhb.dic.FileTypeEnum;
import com.forever.zhb.pojo.FileInfoData;
import com.forever.zhb.service.IForeverManager;
import com.forever.zhb.utils.PropertyUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;

@Controller
@RequestMapping("/htgl/uploadController")
public class UploadController {
    
    protected Log log = LogFactory.getLog(UploadController.class);
    
    @Resource(name="foreverManager")
    private IForeverManager foreverManager;
	
	@RequestMapping("/toUpload")
    public String toUpload(HttpServletRequest request,HttpServletResponse response){
		request.setAttribute("active5", true);
        return "htgl.upload.index";
    }
	
	@RequestMapping("/upload")
    public void upload(HttpServletRequest request,HttpServletResponse response){
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
            /*try {
				request.getRequestDispatcher(ctxPath + "/htgl/errorController/toError").forward(request, response);
			} catch (ServletException | IOException e2) {
				e2.printStackTrace();
			}
            return;*/
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
           foreverManager.addFileInfoData(fileInfoData);
           //查询
           /*List<ForeverCriteria> conditions = new ArrayList<ForeverCriteria>();
           conditions.add(ForeverCriteria.eq("fileName", ""));
           List<FileInfoData> fileInfoDatas = foreverManager.getFileInfoDataByIdOrName(conditions);*/
        }catch(Exception e){
            e.printStackTrace();
            request.setAttribute(Constants.REQUEST_ERROR, "上传出错");
            try {
				request.getRequestDispatcher(ctxPath + "/htgl/errorController/toError").forward(request, response);
			} catch (ServletException | IOException e2) {
				e2.printStackTrace();
			}
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
                e.printStackTrace();
            } 
        }
        try {
			response.sendRedirect(ctxPath + "/htgl/fileDownloadController/toDownload");
		} catch (IOException e) {
			e.printStackTrace();
		}
        return ;
    }

}
