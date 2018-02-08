package com.forever.zhb.controller.annotation;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

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
	
	/*@RequestMapping("/contentTest")
	public void contentTest(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String groupId = request.getParameter("groupId");
		String decGroupId = AESUtil.decrypt(groupId, AESUtil.findKeyById(""));
		JSONObject jo = new JSONObject();
		
		List<String> students = new ArrayList<String>();
		students.add("张会彬");
		students.add(decGroupId);
		jo.put("students", students);
		
		String encryRes = AESUtil.encrypt(jo.toString(), AESUtil.findKeyById(""));
		
        response.setHeader("Content-Type", "application/json; charset=utf-8");// 中文显示
        PrintWriter pw = null;
        try {
            pw = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        pw.append(encryRes);
        pw.close();
    }*/
	
	@RequestMapping("/contentTest")
	public String contentTest(HttpServletRequest request,HttpServletResponse response) throws Exception{
		return "test.body.index";
    }
	
	public static void main(String[] args){
		int number = 100;
		for(int i = 2;i < number;i++){
			boolean flag = true;
			for (int j = 2; j < i; j++) {
				if (i % j == 0) {
					flag = false;
					break;
				}
			}
			if (flag) {
				System.out.println(i + " 是素数");
			}
		}
	}

}
