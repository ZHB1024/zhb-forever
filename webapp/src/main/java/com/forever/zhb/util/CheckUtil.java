package com.forever.zhb.util;

import com.forever.zhb.Constants;
import com.forever.zhb.pojo.UserInfoData;
import com.forever.zhb.utils.StringUtil;
import javax.servlet.http.HttpServletRequest;

public class CheckUtil {


    public static boolean checkUserInfo(UserInfoData data,HttpServletRequest request){
        if (null == data){
            request.setAttribute(Constants.REQUEST_ERROR,"请初始化用户信息");
            return true;
        }
        if (StringUtil.isBlank(data.getName())){
            request.setAttribute(Constants.REQUEST_ERROR,"用户名不能为空");
            return true;
        }
        if (StringUtil.isBlank(data.getRealName())){
            request.setAttribute(Constants.REQUEST_ERROR,"真实姓名不能为空");
            return true;
        }
        if (StringUtil.isBlank(data.getSex())){
            request.setAttribute(Constants.REQUEST_ERROR,"性别不能为空");
            return true;
        }
        if (StringUtil.isBlank(data.getPhone())){
            request.setAttribute(Constants.REQUEST_ERROR,"电话号码不能为空");
            return true;
        }
        if (StringUtil.isBlank(data.getEmail())){
            request.setAttribute(Constants.REQUEST_ERROR,"邮箱不能为空");
            return true;
        }

        return false;
    }

}
