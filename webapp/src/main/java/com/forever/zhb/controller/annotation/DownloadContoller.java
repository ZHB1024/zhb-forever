package com.forever.zhb.controller.annotation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
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
import com.forever.zhb.criteria.ForeverCriteria;
import com.forever.zhb.criteria.ForeverCriteriaUtil;
import com.forever.zhb.page.Page;
import com.forever.zhb.pojo.FileInfoData;
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
    	request.setAttribute("active6", true);
    	String start = request.getParameter("start");
    	if (StringUtils.isBlank(start)) {
            start = "0";
        }
        //查询
        List<ForeverCriteria> conditions = new ArrayList<ForeverCriteria>();
        ForeverCriteriaUtil<FileInfoData> util = ForeverCriteriaUtil.getInstance(FileInfoData.class);
        util.setPageProperties(null, Integer.parseInt(start), Constants.PAGE_SIZE, conditions, null);
        Page filePage = util.getPage(foreverManager.getFileInfoPage(conditions));
        request.setAttribute("page", filePage);
        return "htgl.download.index";
    }
    
    /*download*/
    @RequestMapping("/download")
    public void download(HttpServletRequest request,HttpServletResponse response,String id){
    	String ctxPath = request.getContextPath();
        if (StringUtils.isBlank(id)) {
            try {
            	String msg = URLEncoder.encode("id不能为空！", "UTF-8");
				response.sendRedirect(ctxPath + "/htgl/errorController/toError?errorMsg="+msg);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
            return;
        }
        //查询
        List<ForeverCriteria> conditions = new ArrayList<ForeverCriteria>();
        conditions.add(ForeverCriteria.eq("id", id));
        List<FileInfoData> fileInfoDatas = foreverManager.getFileInfo(conditions);
        
        if (null == fileInfoDatas || fileInfoDatas.size()==0) {
			return;
		}
        FileInfoData fileInfo = fileInfoDatas.get(0);
        
        String filePath = fileInfo.getFilePath() + File.separator ;
        File file = new File(filePath);
        if(!file.exists()){
            file.mkdir();
        }
        filePath += fileInfo.getFileName();
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
