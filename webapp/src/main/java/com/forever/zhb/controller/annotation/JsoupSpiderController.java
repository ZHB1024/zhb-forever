package com.forever.zhb.controller.annotation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.forever.zhb.basic.BasicController;
import com.forever.zhb.utils.JsoupUtil;
import com.forever.zhb.utils.StringUtil;

@Controller
@RequestMapping("/htgl/jsoupSpiderController")
public class JsoupSpiderController extends BasicController {
	
	private Logger logger = LoggerFactory.getLogger(JsoupSpiderController.class);
	
	@RequestMapping(value="/toSpider",method=RequestMethod.GET)
	public String toSpider(HttpServletRequest request,HttpServletResponse response){
		
		return "htgl.spider.index";
	}

	@RequestMapping(value="/spider",method=RequestMethod.POST)
	public String spider(HttpServletRequest request,HttpServletResponse response){
		String url = request.getParameter("url");
		if (StringUtil.isBlank(url)) {
			return "htgl.spider.index";
		}
		Document doc = JsoupUtil.getDocumentByUrl(url);
		if (null == doc) {
			return "htgl.spider.index";
		}
		Elements links = doc.select("a[href]");
        Elements media = doc.select("[src]");
        Elements imports = doc.select("link[href]");

        print("\nMedia: (%d)", media.size());
        for (Element src : media) {
            if (src.tagName().equals("img"))
                print(" * %s: <%s> %sx%s (%s)",
                        src.tagName(), src.attr("abs:src"), src.attr("width"), src.attr("height"),
                        trim(src.attr("alt"), 20));
            else
                print(" * %s: <%s>", src.tagName(), src.attr("abs:src"));
        }

        print("\nImports: (%d)", imports.size());
        for (Element link : imports) {
            print(" * %s <%s> (%s)", link.tagName(),link.attr("abs:href"), link.attr("rel"));
        }

        print("\nLinks: (%d)", links.size());
        for (Element link : links) {
            print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 35));
        }
		return "htgl.spider.index";
	}
	
	private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }

    private static String trim(String s, int width) {
        if (s.length() > width)
            return s.substring(0, width-1) + ".";
        else
            return s;
    }

}
