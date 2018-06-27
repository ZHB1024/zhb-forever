package com.forever.zhb.basic;

import com.forever.zhb.util.AjaxMessage;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletResponse;


public class BasicController {
	protected AjaxMessage ajaxMessage = new AjaxMessage();
	
	protected void setResponse(HttpServletResponse response){
		response.setHeader("P3P","CP='IDC DSP COR ADM DEVi TAIi PSA PSD IVAi IVDi CONi HIS OUR IND CNT'");
        response.setContentType("text/plain;charset=UTF-8");
	}
	
	protected void writeJSON(AjaxMessage ajaxMessage,HttpServletResponse response) {
        try {
            response.getWriter().print(JSONObject.fromObject(ajaxMessage));
            response.getWriter().flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
		
	}

}
