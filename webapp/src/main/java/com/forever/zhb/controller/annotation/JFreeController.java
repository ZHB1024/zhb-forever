package com.forever.zhb.controller.annotation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.forever.zhb.basic.BasicController;
import com.forever.zhb.dic.JFreeDrawTypeEnum;
import com.forever.zhb.utils.ImageUtils;
import com.forever.zhb.utils.JFreeUtil;
import com.forever.zhb.utils.attachment.MimeTypeUtils;
import com.forever.zhb.utils.jfree.bean.JFreeDraw;
import com.forever.zhb.vo.NameValueVO;

@Controller
@RequestMapping("/htgl/jfreeController")
public class JFreeController extends BasicController{
	
	private Logger logger = LoggerFactory.getLogger(JFreeController.class);
	
	@RequestMapping(value="/toJFree",method=RequestMethod.GET)
	public String toJFree(HttpServletRequest request,HttpServletResponse response){
		request.setAttribute("jfreeType", getJFreeType());
		return "htgl.jfree.index";
	}
	
	@RequestMapping(value="/jfreeDraw",method=RequestMethod.POST)
	public String jfreeDraw(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String type = request.getParameter("drawType");
		List<JFreeDraw> draws = JFreeUtil.getJFreeDraw(type);
		if (null != draws ) {
			String title = "选项占比统计图";
			List<NameValueVO> vos = new ArrayList<NameValueVO>();
			vos.add(new NameValueVO("张三", "100"));
			vos.add(new NameValueVO("李四", "90000"));
			vos.add(new NameValueVO("王二", "6000"));
			vos.add(new NameValueVO("网五", "200000"));
			vos.add(new NameValueVO("码子", "343"));
			
			for (int i = 0; i < draws.size(); i++) {
				int drawType = draws.get(i).draw(vos, title, "", "");
				String context = request.getContextPath();
				request.setAttribute("draw" + i, context + "/htgl/jfreeController/downloadFile?drawType=" + drawType);
			}
		}
		request.setAttribute("drawType", type);
		request.setAttribute("jfreeType", getJFreeType());
		return "htgl.jfree.index";
	}
	
	@RequestMapping(value="/downloadFile",method=RequestMethod.GET)
	public void downloadFile(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String drawType = request.getParameter("drawType");
		String filePath = JFreeUtil.getFilePath(drawType);
		File file = new File(filePath);
		if (file.isFile()) {
			response.setContentType(MimeTypeUtils.getMimeTypeByFormat("jpeg"));
			FileInputStream fis = null;
			ServletOutputStream sos = null;
			try {
				fis = new FileInputStream(file);
				sos = response.getOutputStream();
				ImageUtils.pressText(fis, sos, 0.3f, 3, 3, new String[] { "zhb_forever" });
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
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
			file.delete();
		}
	}
	
	private List<NameValueVO> getJFreeType(){
		List<NameValueVO> vos = new ArrayList<NameValueVO>();
		for (JFreeDrawTypeEnum type : JFreeDrawTypeEnum.values()) {
			NameValueVO vo = new NameValueVO();
			vo.setName(type.getName());
			vo.setValue(String.valueOf(type.getIndex()));
			vos.add(vo);
		}
		return vos;
	}
			

}
