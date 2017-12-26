package com.forever.zhb.filter;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang3.StringUtils;

public class SquidEnabledRequest extends HttpServletRequestWrapper {

    public SquidEnabledRequest(HttpServletRequest request) {
        super(request);
    }
    
    public String getRemoteAddr() {
        String ip = StringUtils.deleteWhitespace(getHeader("X-Forwarded-For"));
        if (StringUtils.isEmpty(ip)) {
            return super.getRemoteAddr();
        }
        String[] ips = StringUtils.split(ip, ",");
        List ipList = new ArrayList();
        for (String p : ips) {
            if (!(ipList.contains(p))) {
                ipList.add(p);
            }
        }
        return StringUtils.join(ipList, ", ");
    }

}
