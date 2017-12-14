package com.forever.zhb.controller.annotation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.forever.zhb.Constants;
import com.forever.zhb.service.IForeverManager;
import com.forever.zhb.utils.ImageUtils;
import com.forever.zhb.utils.PropertyUtil;
import com.forever.zhb.vo.FileVo;

@Controller
@RequestMapping("/htgl/fileDownloadController")
public class DownloadContoller {
    
protected Log log = LogFactory.getLog(UploadController.class);
    
    @Resource(name="foreverManager")
    private IForeverManager foreverManager;
    
    /*toDownload*/
    @RequestMapping("/toDownload")
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
        return "htgl.download.index";
    }
    
    /*download*/
    @RequestMapping("/download")
    public void download(HttpServletRequest request,HttpServletResponse response,String id){
        if (StringUtils.isBlank(id)) {
            //return;
        }
        String filePath = PropertyUtil.getUploadPath() + File.separator 
                + Constants.TARGET_NAME + File.separator +Constants.IMAGE_PATH + File.separator ;
        File file = new File(filePath);
        if(!file.exists()){
            file.mkdir();
        }
        filePath += "";
        response.setContentType("image/jpeg");
        FileInputStream fis = null;
        ServletOutputStream sos = null;
        File image = new File(filePath);
        try {
            fis = new FileInputStream(image);
            sos = response.getOutputStream();
            /*int lenght;
            byte[] buf = new byte[1024];
            while((lenght = fis.read(buf, 0, 1024)) != -1){
                sos.write(buf,0,lenght);
            }*/
            ImageUtils.pressText(fis, sos, 0.3f, 3, 3, new String[]{"zhb_forever"});
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

}
