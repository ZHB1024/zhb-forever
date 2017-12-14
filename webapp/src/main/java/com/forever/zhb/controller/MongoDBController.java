package com.forever.zhb.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.forever.zhb.mongo.model.UserModel;
import com.forever.zhb.service.IMongoDBManager;

public class MongoDBController extends MultiActionController {
    
     protected Log log = LogFactory.getLog(this.getClass());
     
     private IMongoDBManager mongoDBManager;
     
     public String findAllMongoDB(HttpServletRequest request,HttpServletResponse response){
         List<UserModel> users = mongoDBManager.findAll();
         request.setAttribute("users", users);
         return "htgl.mongodb.query";
     }
     
     public String insertMongoDB(HttpServletRequest request,HttpServletResponse response){
         UserModel userModel = new UserModel("习近平", "男");
         mongoDBManager.insertUser(userModel);
         
         List<UserModel> users = mongoDBManager.findAll();
         request.setAttribute("users", users);
         return "htgl.mongodb.add";
     }
     

    public IMongoDBManager getMongoDBManager() {
        return mongoDBManager;
    }

    public void setMongoDBManager(IMongoDBManager mongoDBManager) {
        this.mongoDBManager = mongoDBManager;
    }
}
