package com.forever.zhb.controller.annotation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/htgl/errorController")
public class ErrorController {
    
    protected Log log = LogFactory.getLog(ErrorController.class);
    
    /*toError*/
    @RequestMapping("/toError")
    public String toError(HttpServletRequest request,HttpServletResponse response){
        return "error.index";
    }
    

}
