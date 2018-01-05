package com.forever.zhb.controller.annotation;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.forever.zhb.utils.FaceUtil;

@Controller
@RequestMapping("/htgl/openCVController")
public class OpenCVController {
	
	protected Logger logger = LoggerFactory.getLogger(OpenCVController.class);
	
	@RequestMapping(value="testFace",method=RequestMethod.GET)
	public void testFace(HttpServletRequest request,HttpServletResponse response){
		logger.info( OpenCVController.class.getResource("/").toString());
		String realPath = request.getSession().getServletContext().getRealPath("/");
		logger.info(realPath);
		File file = new File(realPath + "WEB-INF/classes/openCV/images/timg.jpg");
		FaceUtil.detectFace(file,realPath);
	}

}
