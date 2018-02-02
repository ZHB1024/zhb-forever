package com.forever.zhb.controller.annotation;

import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.forever.zhb.dic.DeleteFlagEnum;
import com.forever.zhb.dic.FunctionTypeEnum;
import com.forever.zhb.dic.RoleTypeEnum;
import com.forever.zhb.pojo.FunctionInfoData;
import com.forever.zhb.pojo.RoleInfoData;
import com.forever.zhb.service.IForeverManager;

@Controller
@RequestMapping("/htgl/initController")
public class InitController {
    
    @Resource(name="foreverManager")
    private IForeverManager foreverManager;
    
    @RequestMapping("/initFunctionInfo")
    public String initFunctionInfo(HttpServletRequest request,HttpServletResponse response){
    	request.setAttribute("active1", true);
        for (FunctionTypeEnum f : FunctionTypeEnum.values()) {
            FunctionInfoData fun = foreverManager.getFunctionInfoByName(f.getName());
            if (null == fun) {
                FunctionInfoData functionInfoData = new FunctionInfoData();
                functionInfoData.setName(f.getName());
                functionInfoData.setDescription(f.getDescription());
                functionInfoData.setCreateTime(Calendar.getInstance());
                functionInfoData.setDeleteFlag(DeleteFlagEnum.UDEL.getIndex());
                foreverManager.addFunctionInfo(functionInfoData);
            }
        }
        List<FunctionInfoData> functions = foreverManager.getFunctionInfos();
        request.setAttribute("functions", functions);
        return "init.function.index";
    }
    
    @RequestMapping("/initRoleInfo")
    public String initRoleInfo(HttpServletRequest request,HttpServletResponse response){
    	request.setAttribute("active2", true);
        for (RoleTypeEnum r : RoleTypeEnum.values()) {
            RoleInfoData role = foreverManager.getRoleInfoByName(r.getName());
            if (null == role) {
                RoleInfoData roleInfoData = new RoleInfoData();
                roleInfoData.setName(r.getName());
                roleInfoData.setDescription(r.getDescription());
                roleInfoData.setCreateTime(Calendar.getInstance());
                roleInfoData.setDeleteFlag(DeleteFlagEnum.UDEL.getIndex());
                foreverManager.addRole(roleInfoData);
            }
        }
        List<RoleInfoData> roles = foreverManager.getRoleInfos();
        request.setAttribute("roles", roles);
        return "init.role.index";
    }
    
}
