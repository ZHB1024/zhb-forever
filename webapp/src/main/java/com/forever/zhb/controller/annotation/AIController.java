package com.forever.zhb.controller.annotation;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.forever.zhb.Constants;
import com.forever.zhb.utils.Base64Util;
import com.forever.zhb.utils.HttpUtil;
import com.forever.zhb.utils.ImageUtils;
import com.forever.zhb.utils.ai.face.FaceUtil;

@Controller
@RequestMapping("/htgl/aiController")
public class AIController {

	protected Logger logger = LoggerFactory.getLogger(AIController.class);

	@RequestMapping("/toUpload")
	public String toUpload(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("active8", true);
		return "htgl.ai.face.index";
	}

	@RequestMapping(value = "/detectFace", method = RequestMethod.POST)
	public void detectFace(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("active8", true);
		String ctxPath = request.getContextPath();
		InputStream faceInput = null;
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile license = multipartRequest.getFile("faceFile");
		try {
			faceInput = license.getInputStream();
			byte[] imgData = ImageUtils.readFileAsBytes(faceInput);
			String imgStr = Base64Util.encode(imgData);
			String imgParam = URLEncoder.encode(imgStr, "UTF-8");

			String param = "max_face_num=" + 5 + "&face_fields="
					+ "age,beauty,expression,faceshape,gender,glasses,landmark,race,qualities" + "&image=" + imgParam;

			// 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间，
			// 客户端可自行缓存，过期后重新获取。
			String accessToken = FaceUtil.getAuth();
			String result = HttpUtil.post(FaceUtil.FACE_URL, accessToken, param);
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(Constants.REQUEST_ERROR, "上传出错");
			try {
				request.getRequestDispatcher(ctxPath + "/htgl/errorController/toError").forward(request, response);
			} catch (ServletException | IOException e2) {
				e2.printStackTrace();
			}
			return;
		}
	}

	/**
	 * 重要提示代码中所需工具类 FileUtil,Base64Util,HttpUtil,GsonUtils请从
	 * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
	 * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
	 * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
	 * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3 下载
	 */
	public static String detect(String facePath) {
		try {
			// 本地文件路径
			byte[] imgData = ImageUtils.readFileAsBytes(facePath);
			String imgStr = Base64Util.encode(imgData);
			String imgParam = URLEncoder.encode(imgStr, "UTF-8");

			String param = "max_face_num=" + 5 + "&face_fields="
					+ "age,beauty,expression,faceshape,gender,glasses,landmark,race,qualities" + "&image=" + imgParam;

			// 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间，
			// 客户端可自行缓存，过期后重新获取。
			String accessToken = FaceUtil.getAuth();

			String result = HttpUtil.post(FaceUtil.FACE_URL, accessToken, param);
			System.out.println(result);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
