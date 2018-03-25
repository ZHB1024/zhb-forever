package com.forever.zhb.controller.annotation;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/collectionController")
public class CollectionController {

    private Logger logger = LoggerFactory.getLogger(CollectionController.class);

    @RequestMapping("/test")
    public String test(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return "test.body.index";
    }

    public static void main(String[] args) {
        HashMap<String, String> hashMap = new HashMap<String,String>();
        hashMap.put(null, null);
        
        Hashtable<String, String> hashtable = new Hashtable<String, String>();
        hashtable.put(null, "");
        
        ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<String, String>();
        concurrentHashMap.put(null, null);

    }

}
