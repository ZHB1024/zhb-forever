package com.forever.zhb.controller.annotation;

import com.forever.zhb.dic.DeleteFlagEnum;
import com.forever.zhb.page.Page;
import com.forever.zhb.service.AccountManager;
import com.forever.zhb.service.UserManager;
import com.forever.zhb.util.CheckUtil;
import java.util.Calendar;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.forever.zhb.Constants;
import com.forever.zhb.pojo.LoginInfoData;
import com.forever.zhb.pojo.UserInfoData;
import com.forever.zhb.service.IForeverManager;
import com.forever.zhb.util.WebAppUtil;
import com.forever.zhb.utils.PasswordUtil;

@Controller
@RequestMapping("/htgl/account")
public class AccountController {
    
    @Resource(name="foreverManager")
    private IForeverManager foreverManager;

    @Resource(name="userManager")
    private UserManager userManager;

    @Resource(name="accountManager")
    private AccountManager accountManager;

    /*to修改账号*/
    @RequestMapping("/toAccount")
    public String toAccount(HttpServletRequest request,HttpServletResponse response){
        UserInfoData user = WebAppUtil.getUserInfoData(request);
        request.setAttribute("user", user);
        return "htgl.update.account";
    }

    /*查询用户信息*/
    @RequestMapping("/queryAccount")
    public String queryAccount(HttpServletRequest request,HttpServletResponse response){
        String userId = WebAppUtil.getUserId(request);
        if (StringUtils.isBlank(userId)) {
            request.setAttribute(Constants.REQUEST_ERROR, "请重新登录");
            return "login.home";
        }
        String start = request.getParameter("start");
        if (StringUtils.isBlank(start)) {
            start = "0";
        }
        Page<UserInfoData> users = userManager.getUserInfos(Integer.parseInt(start),1);
        request.setAttribute("page",users);
        return "htgl.query.users";
    }

    /*to新增账号*/
    @RequestMapping("/toAddUser")
    public String toAddUser(HttpServletRequest request,HttpServletResponse response){
        UserInfoData user = WebAppUtil.getUserInfoData(request);
        if (null == user) {
            request.setAttribute(Constants.REQUEST_ERROR, "请重新登录");
            return "login.home";
        }
        return "htgl.toAdd.user";
    }

    /*新增账号信息*/
    @RequestMapping("/addUser")
    public String addUser(HttpServletRequest request,HttpServletResponse response, UserInfoData userInfo){
        String userId = WebAppUtil.getUserId(request);
        if (StringUtils.isBlank(userId)) {
            request.setAttribute(Constants.REQUEST_ERROR, "请重新登录");
            return "login.home";
        }

        if (null == userInfo) {
            request.setAttribute(Constants.REQUEST_ERROR, "请补全账号信息");
            return "htgl.update.account";
        }

        //验证userinfo
        if (CheckUtil.checkUserInfo(userInfo,request)){
            return "login.home";
        }

        userInfo.setCreateTime(Calendar.getInstance());
        userInfo.setDeleteFlag(DeleteFlagEnum.UDEL.getIndex());
        userManager.saveOrUpdate(userInfo);
        //初始化密码
        accountManager.init(userInfo);

        return "htgl.main.index";
    }

    /*修改账号信息*/
    @RequestMapping("/upAccount")
    public String upAccount(HttpServletRequest request,HttpServletResponse response, UserInfoData userInfo){
        if (null == userInfo) {
            request.setAttribute(Constants.REQUEST_ERROR, "请补全账号信息");
            return "htgl.update.account";
        }
        if (StringUtils.isBlank(userInfo.getId())) {
            request.setAttribute(Constants.REQUEST_ERROR, "账号异常，请重新登录");
            return "login.home";
        }
        String userId = WebAppUtil.getUserId(request);
        if (StringUtils.isBlank(userId) || !userId.equals(userInfo.getId())) {
            request.setAttribute(Constants.REQUEST_ERROR, "账号异常，请重新登录");
            return "login.home";
        }
        UserInfoData user = userManager.getUserInfoById(userInfo.getId());
        if (null == user) {
            request.setAttribute(Constants.REQUEST_ERROR, "账号异常，请重新登录");
            return "login.home";
        }
        user.setRealName(userInfo.getRealName());
        user.setSex(userInfo.getSex());
        user.setPhone(userInfo.getPhone());
        user.setEmail(userInfo.getEmail());
        user.setUpdateTime(Calendar.getInstance());
        userManager.saveOrUpdate(user);
        return "htgl.main.index";
    }
    
    /*to修改密码*/
    @RequestMapping("/toModifyPassword")
    public String toModifyPassword(HttpServletRequest request,HttpServletResponse response,String id){
        if (StringUtils.isBlank(id)) {
            request.setAttribute(Constants.REQUEST_ERROR, "账号异常，请重新登录");
            return "login.home";
        }
        UserInfoData user = WebAppUtil.getUserInfoData(request);
        if (null == user || !id.equals(user.getId())) {
            request.setAttribute(Constants.REQUEST_ERROR, "账号异常，请重新登录");
            return "login.home";
        }
        request.setAttribute("user", user);
        return "htgl.update.password";
    }
    
    /*修改密码*/
    @RequestMapping("/modifyPassword")
    public String modifyPassword(HttpServletRequest request,HttpServletResponse response,UserInfoData userInfo){
        if (null == userInfo || StringUtils.isBlank(userInfo.getId())) {
            request.setAttribute(Constants.REQUEST_ERROR, "账号异常，请重新登录");
            return "login.home";
        }
        String userId = WebAppUtil.getUserId(request);
        if (StringUtils.isBlank(userId) || !userId.equals(userInfo.getId())) {
            request.setAttribute(Constants.REQUEST_ERROR, "账号异常，请重新登录");
            return "login.home";
        }
        String password = request.getParameter("password");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");
        if (!confirmPassword.equals(newPassword)) {
            request.setAttribute("user", userInfo);
            request.setAttribute(Constants.REQUEST_ERROR, "两次输入的密码不一致，请重新输入");
            return "htgl.update.password";
        }
        UserInfoData user = foreverManager.getLoginInfo(userInfo.getName(), PasswordUtil.encrypt(userInfo.getName(),password,PasswordUtil.getStaticSalt()));
        if (null == user) {
            request.setAttribute(Constants.REQUEST_ERROR, "原始密码输入错误");
            return "htgl.update.password";
        }
        LoginInfoData login = foreverManager.getLoginInfoByName(user.getName());
        login.setUserInfoData(user);
        login.setPassword(PasswordUtil.encrypt(user.getName(),newPassword,PasswordUtil.getStaticSalt()));
        login.setUpdateTime(Calendar.getInstance());
        foreverManager.addLoginInfo(login);
        WebAppUtil.exit(request);
        return "login.home";
    }
    
    /*to退出系统*/
    @RequestMapping("/toExit")
    public String exit(HttpServletRequest request,HttpServletResponse response,String id){
        WebAppUtil.exit(request);
        return "login.home";
    }
    
}
