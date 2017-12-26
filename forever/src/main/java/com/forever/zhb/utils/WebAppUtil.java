package com.forever.zhb.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.forever.zhb.Constants;
import com.forever.zhb.pojo.UserInfoData;

public class WebAppUtil {
    
    
    /**
     * 获取 UserInfoData 对象
     */
    public static UserInfoData getUserInfoData(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (UserInfoData) session.getAttribute(Constants.SESSION_LOGINFODATA);
    }
    
    /**
     * 获取用户UserId
     * @return
     */
    public static String getUserId(HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserInfoData user = (UserInfoData) session.getAttribute(Constants.SESSION_LOGINFODATA);
        if (null == user) {
            return null;
        }
        return user.getId();
    }

    /**
     * 设置 UserInfoData 对象到 session
     */
    public static void setUserInfoData(HttpServletRequest request, UserInfoData userInfoData) {
        HttpSession session = request.getSession();
        session.setAttribute(Constants.SESSION_LOGINFODATA, userInfoData);
    }

    /**
     * 判断当前用户是否登录
     */
    public static boolean isLogined(HttpServletRequest request) {
        return null == getUserInfoData(request) ? false : true;
    }
    
    public static void exit(HttpServletRequest request){
        request.getSession().removeAttribute(Constants.SESSION_LOGINFODATA);
    }
   
}
