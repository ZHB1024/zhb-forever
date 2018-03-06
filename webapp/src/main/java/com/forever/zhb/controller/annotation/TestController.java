package com.forever.zhb.controller.annotation;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.forever.zhb.utils.AESUtil;
import com.forever.zhb.utils.MessageUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/testController")
public class TestController {

	private Logger logger = LoggerFactory.getLogger(TestController.class);

	/*
	 * @RequestMapping("/contentTest") public void
	 * contentTest(HttpServletRequest request,HttpServletResponse response)
	 * throws Exception{ String groupId = request.getParameter("groupId");
	 * String decGroupId = AESUtil.decrypt(groupId, AESUtil.findKeyById(""));
	 * JSONObject jo = new JSONObject();
	 * 
	 * List<String> students = new ArrayList<String>(); students.add("张会彬");
	 * students.add(decGroupId); jo.put("students", students);
	 * 
	 * String encryRes = AESUtil.encrypt(jo.toString(),
	 * AESUtil.findKeyById(""));
	 * 
	 * response.setHeader("Content-Type", "application/json; charset=utf-8");//
	 * 中文显示 PrintWriter pw = null; try { pw = response.getWriter(); } catch
	 * (IOException e) { e.printStackTrace(); } pw.append(encryRes); pw.close();
	 * }
	 */

	@RequestMapping("/contentTest")
	public String contentTest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String title = "总共<span class=\"mod_fillblank\" data-nostyle=\"true\">________</span>人，其它<span class=\"mod_fillblank\" data-nostyle=\"true\">________</span>人";
        int countNumber = countNumber(title, "</span>");
        ArrayList<String> blankTitles = new ArrayList<String>();
        String[] temp1 = title.split("<span");
        if (temp1 != null) {
        	blankTitles.add(temp1[0]);
            for(int i=1;i<temp1.length;i++){
                if (i != temp1.length-1) {
					String[] temp2 = temp1[i].split("</span>");
					if (temp2.length == 1) {
						blankTitles.add("");
					}else{
						blankTitles.add(temp2[1]);
					}
					
				}else{
					String[] temp2 = temp1[i].split("</span>");
					if (temp2.length > 1) {
						blankTitles.add(temp2[1]);
					}
				}
            }
        }
		return "test.body.index";
	}
	
	@RequestMapping("/test")
	public String test(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String message = MessageUtil.getMessage("2001", new Object[]{"张会彬","你好"});
		logger.info(message);
		return "test.body.index";
	}

	public static void main(String[] args) {
		String title = "总共<span class=\"mod_fillblank\" data-nostyle=\"true\">________</span>人，其它<span class=\"mod_fillblank\" data-nostyle=\"true\">________</span>人<span class=\"mod_fillblank\" data-nostyle=\"true\">________</span>";
        int countNumber = countNumber(title, "</span>");
        ArrayList<String> blankTitles = new ArrayList<String>();
        String[] temp1 = title.split("<span");
        if (temp1 != null) {
        	blankTitles.add(temp1[0]);
            for(int i=1;i<temp1.length;i++){
                if (i != temp1.length-1) {
					String[] temp2 = temp1[i].split("</span>");
					blankTitles.add(temp2[1]);
				}else{
					String[] temp2 = temp1[i].split("</span>");
					if (StringUtils.isNotBlank(temp2[1])) {
						blankTitles.add(temp2[1]);
					}
				}
            }
        }
		
		
		
		/*TestController inc = new TestController();
		int i = 0;
		inc.fermin(i);
		i = i++;
		System.out.println(i);*/

		// System.out.println("main:" + returnFinally());
		// printPrimeNumber(100);
	}
	
	 private static int countNumber(String srcText, String findText){
	        int count = 0;
	        Pattern p = Pattern.compile(findText);
	        Matcher m = p.matcher(srcText);
	        while (m.find()) {
	            count++;
	        }
	        return count;
	    }


	private static int returnFinally() {
		int value = 0;
		try {
			return value += 1;
		} finally {
			if (value > 0) {
				value += 10;
				System.out.println("value:" + value);
			}
		}

		/*
		 * try { value += 1 ; return value; } finally { value += 2 ; return
		 * value; }
		 */
	}

	private static void printPrimeNumber(int number) {
		for (int i = 2; i < number; i++) {
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

	void fermin(int i) {
		i++;
	}

}
