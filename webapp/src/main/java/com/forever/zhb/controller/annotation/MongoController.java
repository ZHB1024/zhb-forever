package com.forever.zhb.controller.annotation;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.forever.zhb.mongo.model.UserModel;
import com.forever.zhb.service.IMongoDBManager;

@Controller
@RequestMapping("/htgl/mongoController")
public class MongoController {
	
	@Resource(name="mongoDBManager")
	private IMongoDBManager mongoDBManager;
	
	@RequestMapping("/findAllMongoDB")
	public String findAllMongoDB(HttpServletRequest request,HttpServletResponse response){
		request.setAttribute("active4", true);
        List<UserModel> users = mongoDBManager.findAll();
        request.setAttribute("users", users);
        return "htgl.mongodb.query";
    }

}
