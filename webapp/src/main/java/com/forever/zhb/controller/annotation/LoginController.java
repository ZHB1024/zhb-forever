package com.forever.zhb.controller.annotation;

import com.forever.zhb.Constants;
import com.forever.zhb.dic.DeleteFlagEnum;
import com.forever.zhb.dic.RoleTypeEnum;
import com.forever.zhb.pojo.LoginInfoData;
import com.forever.zhb.pojo.RoleInfoData;
import com.forever.zhb.pojo.UserInfoData;
import com.forever.zhb.service.IForeverManager;
import com.forever.zhb.util.WebAppUtil;
import com.forever.zhb.utils.PasswordUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;

@Controller
@RequestMapping("/loginController")
public class LoginController {
    
    protected Log log = LogFactory.getLog(this.getClass());
    
    @Resource(name="foreverManager")
    private IForeverManager foreverManager;
    
    /*to登录*/
    @RequestMapping("/toLogin")
    public String toLogin(HttpServletRequest request,HttpServletResponse response){
        return "login.home";
    }
    
    /*登录*/
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(HttpServletRequest request,HttpServletResponse response) throws IOException{
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String redirectUrl = request.getParameter("redirectUrl");
        if (StringUtils.isBlank(name) || StringUtils.isBlank(password)) {
            request.setAttribute(Constants.REQUEST_ERROR, "用户名或密码错误不能为空");
            request.setAttribute("name", name);
            request.setAttribute("password", password);
            return "login.home";
        }
        UserInfoData userInfo = foreverManager.getLoginInfo(name, PasswordUtil.encrypt(name,password,PasswordUtil.getStaticSalt()));
        if (null == userInfo) {
            request.setAttribute(Constants.REQUEST_ERROR, "用户名或密码错误");
            request.setAttribute("name", name);
            request.setAttribute("password", password);
            return "login.home";
        }
        
        /*初始化session*/
        WebAppUtil.setIP(request);
        WebAppUtil.setUserInfoData(request, userInfo);
        if (StringUtils.isNotBlank(redirectUrl)) {
			response.sendRedirect(redirectUrl);
			return "";
		}
        return "htgl.main.index";
    }
    
    /*登录*/
    @RequestMapping(value = "/loginWithUrl",method = RequestMethod.POST)
    public void loginWithUrl(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException{
    	String ctxPath = request.getContextPath();
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String redirectUrl = request.getParameter("redirectUrl");
        if (StringUtils.isBlank(name) || StringUtils.isBlank(password)) {
            request.setAttribute(Constants.REQUEST_ERROR, "用户名或密码错误不能为空");
            request.setAttribute("name", name);
            request.setAttribute("password", password);
            request.setAttribute("redirectUrl", redirectUrl);
            request.getRequestDispatcher(ctxPath + "/login/login.jsp").forward(request, response);
            return;
        }
        UserInfoData userInfo = foreverManager.getLoginInfo(name, PasswordUtil.encrypt(name,password,PasswordUtil.getStaticSalt()));
        if (null == userInfo) {
            request.setAttribute(Constants.REQUEST_ERROR, "用户名或密码错误");
            request.setAttribute("name", name);
            request.setAttribute("password", password);
            request.setAttribute("redirectUrl", redirectUrl);
            request.getRequestDispatcher(ctxPath + "/login/login.jsp").forward(request, response);
            return ;
        }
        
        /*初始化session*/
        WebAppUtil.setIP(request);
        WebAppUtil.setUserInfoData(request, userInfo);
        if (StringUtils.isNotBlank(redirectUrl)) {
			response.sendRedirect(redirectUrl);
			return ;
		}
    }
    
    /*to注册*/
    @RequestMapping("/toRegister")
    public String toRegister(HttpServletRequest request,HttpServletResponse response){
        return "register.home";
    }
    
    /*注册*/
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String register(HttpServletRequest request,HttpServletResponse response, UserInfoData userInfoData, LoginInfoData loginInfoData){
        String confirmPassword = request.getParameter("confirmPass");
        if (null == loginInfoData || null == userInfoData || StringUtils.isBlank(confirmPassword)) {
            request.setAttribute(Constants.REQUEST_ERROR, "带*项都必须填写");
            return "register.home";
        }
        if (!confirmPassword.equals(loginInfoData.getPassword())) {
            request.setAttribute(Constants.REQUEST_ERROR, "两次输入的密码不一致，请重新输入");
            return "register.home";
        }
        RoleInfoData roleInfoData = foreverManager.getRoleInfoByName(RoleTypeEnum.PHOTO.getName());
        userInfoData.setRoleInfoData(roleInfoData);
        userInfoData.setCreateTime(Calendar.getInstance());
        userInfoData.setDeleteFlag(DeleteFlagEnum.UDEL.getIndex());
        foreverManager.addUserInfo(userInfoData);
        
        loginInfoData.setUserInfoData(userInfoData);
        loginInfoData.setPassword(PasswordUtil.encrypt(userInfoData.getName(), loginInfoData.getPassword(), PasswordUtil.getStaticSalt()));
        loginInfoData.setCreateTime(Calendar.getInstance());
        loginInfoData.setDeleteFlag(DeleteFlagEnum.UDEL.getIndex());
        foreverManager.addLoginInfo(loginInfoData);
        
        return "login.home";
    }
    
    /*初始化默认账号root*/
    @RequestMapping("/initRoot")
    public String initRoot(HttpServletRequest request,HttpServletResponse response){
        UserInfoData user = foreverManager.getUserInfoByName("root");
        if (null != user) {
            request.setAttribute(Constants.REQUEST_ERROR, "默认账号已被初始化过，请直接登录");
            return "login.home";
        }else{
            user = new UserInfoData();
            user.setName("root");
            RoleInfoData roleInfoData = foreverManager.getRoleInfoByName(RoleTypeEnum.ADMIN.getName());
            user.setRoleInfoData(roleInfoData);
            user.setCreateTime(Calendar.getInstance());
            user.setDeleteFlag(DeleteFlagEnum.UDEL.getIndex());
            foreverManager.addUserInfo(user);
            
            LoginInfoData loginInfo = new LoginInfoData();
            loginInfo.setUserInfoData(user);
            loginInfo.setPassword(PasswordUtil.encrypt(user.getName(), "123456", PasswordUtil.getStaticSalt()));
            loginInfo.setCreateTime(Calendar.getInstance());
            loginInfo.setDeleteFlag(DeleteFlagEnum.UDEL.getIndex());
            foreverManager.addLoginInfo(loginInfo);
            return "login.home";
        }
    }

}
