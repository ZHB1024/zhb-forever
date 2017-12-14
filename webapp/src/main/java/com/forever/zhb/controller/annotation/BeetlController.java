package com.forever.zhb.controller.annotation;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.StringTemplateResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.forever.zhb.Constants;
import com.forever.zhb.service.IForeverManager;

@Controller
@RequestMapping("/htgl/beetlController")
public class BeetlController {
	
	@Resource(name="foreverManager")
	private IForeverManager foreverManager;
	
	@RequestMapping("/beetl")
    public String beetl(HttpServletRequest request,HttpServletResponse response) throws IOException{
        String template = Constants.TEMPLATE;
        StringTemplateResourceLoader resourceLoader = new StringTemplateResourceLoader();
        Configuration cfg = Configuration.defaultConfiguration();
        GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
        Template t = gt.getTemplate(template);
        t.binding("flag", true);
        String content = t.render();
        request.setAttribute("content", content);
        return "htgl.beetl.index";
    }

}
