package com.forever.zhb.controller.annotation;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.forever.zhb.utils.AESUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/testController")
public class TestController {
	
	private Logger logger = LoggerFactory.getLogger(TestController.class);
	
	@RequestMapping("/contentTest")
	public void contentTest(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String groupId = request.getParameter("groupId");
		String decGroupId = AESUtil.decrypt(groupId, AESUtil.findKeyById(""));
		JSONObject jo = new JSONObject();
		JSONObject students = new JSONObject();
		students.put("12345", "zhanghb");
		students.put("67890", "zhanghuibin");
		students.put("groupId", decGroupId);
		jo.put("students", students);
        response.setHeader("Content-Type", "application/json; charset=utf-8");// 中文显示
        PrintWriter pw = null;
        try {
            pw = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        pw.append(jo.toString());
        pw.close();
    }

}
