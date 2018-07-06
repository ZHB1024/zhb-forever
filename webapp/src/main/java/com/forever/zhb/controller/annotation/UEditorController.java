package com.forever.zhb.controller.annotation;

import com.baidu.ueditor.ActionEnter;
import com.forever.zhb.Constants_Web;
import com.forever.zhb.utils.StringUtil;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UEditorController {

    public void uploadConfig(HttpServletRequest request, HttpServletResponse response){
        String exc = Constants_Web.UEditor_exc;
        if (StringUtil.isBlank(exc)) {
            String rootPath = request.getSession().getServletContext().getRealPath("/") +
                "js" + File.separator + "ueditor" + File.separator + "jsp";
            exc = new ActionEnter(request, rootPath).exec();
            Constants_Web.UEditor_exc = exc;
        }
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Content-Type", "text/html");
        PrintWriter pw;
        try {
            pw = response.getWriter();
            pw.write(exc);
            pw.flush();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
