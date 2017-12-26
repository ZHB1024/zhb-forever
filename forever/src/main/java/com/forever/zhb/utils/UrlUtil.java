package com.forever.zhb.utils;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.util.ServerInfo;


public class UrlUtil {
	
	private static Boolean isTomcatBefore5_5 = null;

    public static final String getRequestURL(HttpServletRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Cannot take null parameters.");
        }

        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();

        String requestUri = (String) request.getAttribute("javax.servlet.forward.request_uri");

        if (null == isTomcatBefore5_5) {
            String serverInfo = ServerInfo.getServerInfo();
            isTomcatBefore5_5 = Boolean.valueOf(false);
            if ((null != serverInfo) && (serverInfo.startsWith("Apache Tomcat/5.0"))) {
                isTomcatBefore5_5 = Boolean.valueOf(true);
            }
        }

        if (isTomcatBefore5_5.booleanValue()) {
            return request.getRequestURL().toString();
        }
        requestUri = (requestUri == null) ? request.getRequestURI() : requestUri;

        StringBuffer buffer = new StringBuffer();
        buffer.append(scheme);
        buffer.append("://");
        buffer.append(serverName);

        if ((((!(scheme.equalsIgnoreCase("http"))) || (serverPort != 80)))
                && (((!(scheme.equalsIgnoreCase("https"))) || (serverPort != 443)))) {
            buffer.append(":");
            buffer.append(String.valueOf(serverPort));
        }

        buffer.append(requestUri);

        return buffer.toString();
    }
    
    public static StringBuffer getActionURL(HttpServletRequest request) {
        String strActionURL = getRequestURL(request);
        StringBuffer actionURL = new StringBuffer(strActionURL); 
        return actionURL;
    }

}
