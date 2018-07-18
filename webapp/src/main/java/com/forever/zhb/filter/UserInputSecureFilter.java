package com.forever.zhb.filter;

import com.forever.zhb.util.UserInputSecurityCheck;
import com.forever.zhb.utils.CookieUtil;
import com.forever.zhb.utils.UrlUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ognl.MethodAccessor;
import ognl.MethodFailedException;
import ognl.OgnlRuntime;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserInputSecureFilter implements Filter {

    protected Logger log;
    private UserInputSecurityCheck reqSecCheck;
    private Pattern acceptedContentTpInHeader;
    private ForbiddenMethodAccessor forbiddenMethodAccessor;
    private boolean enableXssMonitor;
    private List<Pattern> xssExcludeUrls;
    private boolean enableCookieMonitor;
    private boolean enableSystemLayerMethodForbidden;
    String reqCtA;
    String reqCtB;
    String reqCtC;
    String reqCtD;

    public UserInputSecureFilter() {
        this.log = LoggerFactory.getLogger(super.getClass());
        this.reqSecCheck = null;

        this.acceptedContentTpInHeader = Pattern.compile("[a-zA-Z0-9\\-]+");

        this.forbiddenMethodAccessor = new ForbiddenMethodAccessor();

        this.enableXssMonitor = false;

        this.xssExcludeUrls = new ArrayList();

        this.enableCookieMonitor = true;
        this.enableSystemLayerMethodForbidden = true;

        this.reqCtA = "multipart/form-data";
        this.reqCtB = "text/plain";
        this.reqCtC = "application/x-www-form-urlencoded";
        this.reqCtD = "application/x-java-serialized-object";
    }

    public void init(FilterConfig config) throws ServletException {
        String maxParamLen = config.getInitParameter("maxParamNameLength");
        String whiteParamNames = config.getInitParameter("whiteParamNames");

        this.enableCookieMonitor = trueIfUnSetedConfig(config.getInitParameter("monitorCookie"));
        this.enableXssMonitor = falseIfUnSetedConfig(config.getInitParameter("monitorXss"));
        this.enableSystemLayerMethodForbidden = trueIfUnSetedConfig(config.getInitParameter("sysMethodForbidden"));

        this.reqSecCheck = new UserInputSecurityCheck(maxParamLen, whiteParamNames);

        applicationLayerForbidden(config.getInitParameter("forbiddenTypeName"));

        initXssExcludeUrls(config.getInitParameter("xssExcludeUrls"));
    }

    private boolean trueIfUnSetedConfig(String value) {
        value = (null == value) ? "true" : value.trim().toLowerCase();
        return "true".equals(value);
    }

    private boolean falseIfUnSetedConfig(String value) {
        value = (null == value) ? "false" : value.trim().toLowerCase();
        return "true".equals(value);
    }

    void initXssExcludeUrls(String initParamValue) {
        initPatterns(this.xssExcludeUrls, initParamValue, 2);
    }

    private void initPatterns(List<Pattern> patterns, String initParamValue, int flag) {
        if (StringUtils.isNotBlank(initParamValue)) {
            String[] regExps = StringUtils.split(StringUtils.trimToEmpty(initParamValue), ",");
            for (String regExp : regExps)
                patterns.add(Pattern.compile(regExp, flag));
        }
    }

    public void destroy() {
    }

    protected boolean isBadUrl(String url) {
        if (url == null) {
            url = "";
        }
        String[] badStr = { "{", "}", "java.lang." };
        for (String item : badStr) {
            if (url.toLowerCase().contains(item)) {
                return true;
            }
        }
        return false;
    }

    protected boolean isXssExcludeUrl(String url) {
        for (Pattern ptn : this.xssExcludeUrls) {
            if (ptn.matcher(url).matches()) {
                return true;
            }
        }
        return false;
    }

    protected boolean isValidContentTypeInHeader(String contentType) {
        if (StringUtils.isBlank(contentType))
            return true;
        if ((StringUtils.equalsIgnoreCase(this.reqCtA, contentType))
            || (StringUtils.equalsIgnoreCase(this.reqCtB, contentType))
            || (StringUtils.equalsIgnoreCase(this.reqCtC, contentType))
            || (StringUtils.equalsIgnoreCase(this.reqCtD, contentType))) {
            return true;
        }
        if (contentType.startsWith(this.reqCtA)) {
            int semIndex = contentType.indexOf(";");
            int eqIndex = contentType.indexOf("=");
            if ((semIndex == -1) || (eqIndex == -1) || (semIndex >= eqIndex)) {
                return false;
            }
            String boundary = contentType.substring(semIndex + 1, eqIndex);
            if (null != boundary) {
                boundary = boundary.trim();
            }
            String boundaryV = contentType.substring(eqIndex + 1);

            return (("boundary".equalsIgnoreCase(boundary))
                && (this.acceptedContentTpInHeader.matcher(boundaryV).matches()));
        }

        if (contentType.startsWith(this.reqCtC)) {
            int semIndex = contentType.indexOf(";");
            int eqIndex = contentType.indexOf("=");
            if ((semIndex == -1) || (eqIndex == -1) || (semIndex >= eqIndex)) {
                return false;
            }
            String boundary = contentType.substring(semIndex + 1, eqIndex);
            if (null != boundary) {
                boundary = boundary.trim();
            }
            String boundaryV = contentType.substring(eqIndex + 1);

            return (("charset".equalsIgnoreCase(boundary))
                && (this.acceptedContentTpInHeader.matcher(boundaryV).matches()));
        }

        return false;
    }

    public void doFilter(ServletRequest srequest, ServletResponse sresponse, FilterChain chain)
        throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) srequest;
        HttpServletResponse response = (HttpServletResponse) sresponse;

        String currentUrl = UrlUtil.getRequestURL(request);
        if (isBadUrl(currentUrl)) {
            response.setStatus(403);
            this.log.info("访问被拒绝:{},{}", request.getRemoteAddr(), currentUrl);
            return;
        }

        String contenType = request.getHeader("content-type");
        if ((StringUtils.isNotBlank(contenType)) && (!(contenType.equalsIgnoreCase(this.reqCtC)))
            && (!(contenType.equalsIgnoreCase(this.reqCtA))) && (!(contenType.equalsIgnoreCase(this.reqCtB)))
            && (!(isValidContentTypeInHeader(contenType)))) {
            response.setStatus(403);
            this.log.info("访问被拒绝:{},{},{}", new String[] { request.getRemoteAddr(), currentUrl, contenType });
            return;
        }

        boolean isXssExcludeUrl = isXssExcludeUrl(currentUrl);
        boolean continueToProcess = true;

        for (Iterator itr = request.getParameterMap().entrySet().iterator(); itr.hasNext();) {
            Map.Entry element = (Map.Entry) itr.next();
            String paramName = (String) element.getKey();
            if (this.reqSecCheck.isParamNameHasIllegalInput(paramName)) {
                continueToProcess = false;
                break;
            }

            String[] values = (String[]) element.getValue();
            if ((!(isXssExcludeUrl)) && (this.reqSecCheck.isParamValuesHasIllegalInput(values))) {
                continueToProcess = false;
                break;
            }

            if ((!(isXssExcludeUrl)) && (!(this.reqSecCheck.isWhiteParamName(paramName)))) {
                boolean xssOccur = this.reqSecCheck.containsXssScript(values);
                if (xssOccur) {
                    this.log.info("XssMonitor-Occur:[{},{},{},{}]",
                        new Object[] { request.getRemoteAddr(), currentUrl, paramName, Arrays.toString(values) });

                    if (this.enableXssMonitor) {
                        continueToProcess = false;
                        break;
                    }
                    printParameterMap(request.getParameterMap());
                }
            }

        }

        if ((this.enableCookieMonitor) && (continueToProcess)) {
            continueToProcess = monitorCookieInfo(request, response);
        }

        if (continueToProcess)
            chain.doFilter(srequest, sresponse);
        else
            proccessIlegalRequest(request, (HttpServletResponse) sresponse);
    }

    private void proccessIlegalRequest(HttpServletRequest request, HttpServletResponse response) {
        response.setStatus(403);
        this.log.info("访问被拒绝:{}", request.getRemoteAddr());
        printParameterMap(request.getParameterMap());
    }

    private void printParameterMap(Map map) {
        StringBuilder strBuilder = new StringBuilder();
        int size = map.entrySet().size();
        int j = 0;
        for (Iterator itr = map.entrySet().iterator(); itr.hasNext();) {
            Map.Entry element = (Map.Entry) itr.next();
            String paramName = (String) element.getKey();
            String[] values = (String[]) element.getValue();
            strBuilder.append(paramName).append("=");
            int i = 0;
            for (String value : values) {
                strBuilder.append(value);
                if (i != values.length - 1) {
                    strBuilder.append(",");
                }
                ++i;
            }

            if (j != size - 1) {
                strBuilder.append("&");
            }
            ++j;
        }

        this.log.info(strBuilder.toString());
    }

    protected void applicationLayerForbidden(String forbiddenList) {
        if (null == forbiddenList) {
            return;
        }
        List<String> classForbidden = Arrays.asList(forbiddenList.split("\\s*,\\s*"));
        for (String className : classForbidden)
            try {
                Class clazz = Class.forName(className);
                OgnlRuntime.setMethodAccessor(clazz, this.forbiddenMethodAccessor);
                if (this.log.isDebugEnabled())
                    this.log.debug(String.format("MethodAccessor forbidden for[%s]", new Object[] { clazz }));
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
    }


    private boolean isCookieNameHasIllegalInput(Cookie cookie, HttpServletRequest request,
        HttpServletResponse response) {
        if (StringUtils.isBlank(cookie.getName())) {
            return false;
        }
        boolean result = this.reqSecCheck.isCookieNameHasIllegalInput(cookie.getName());

        if (result) {
            printCookieInfo(cookie, request);
        }

        return result;
    }

    private void removeCookie(HttpServletResponse response, Cookie cookie) {
        CookieUtil
            .setCookieValue(response, cookie.getName(), cookie.getValue(), cookie.getPath(), Integer.valueOf(0));
    }

    private boolean isCookieValueHasIllegalInput(Cookie cookie, HttpServletRequest request,
        HttpServletResponse response) {
        String cookieValue = cookie.getValue();
        if (StringUtils.isBlank(cookieValue)) {
            return false;
        }
        cookieValue = cookieValue.trim();
        boolean result = this.reqSecCheck.isParamValuesHasIllegalInput(new String[] { cookieValue });
        if (result) {
            printCookieInfo(cookie, request);
        }
        return result;
    }

    private void printCookieInfo(Cookie cookie, HttpServletRequest request) {
        StringBuilder info = new StringBuilder();
        info.append("Cookie invalid.{'url':'").append(request.getRequestURL()).append("'").append(
            String.format(",'name':'%s','value':'%s'", new Object[] { cookie.getName(), cookie.getValue() }))
            .append(",cookies':[");

        Cookie[] cookies = request.getCookies();
        for (int i = 0; i < cookies.length; ++i) {
            if (i > 0) {
                info.append(",");
            }
            info.append(String.format("{'name':'%s','value':'%s','path':'%s','domain':'%s','comment':'%s'}",
                new Object[] { cookies[i].getName(), cookies[i].getValue(), cookies[i].getPath(),
                    cookies[i].getDomain(), cookies[i].getComment() }));
        }

        info.append("]}");
        this.log.info(info.toString());
    }

    protected boolean monitorCookieInfo(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookieArray = request.getCookies();
        if (null == cookieArray) {
            return true;
        }
        for (Cookie cookie : cookieArray) {
            if ((!(isCookieNameHasIllegalInput(cookie, request, response)))
                && (!(isCookieValueHasIllegalInput(cookie, request, response)))) {
                continue;
            }
            removeCookie(response, cookie);
            return false;
        }

        return true;
    }

    class ForbiddenMethodAccessor implements MethodAccessor {
        protected Logger log;

        ForbiddenMethodAccessor() {
            this.log = LoggerFactory.getLogger(super.getClass());
        }

        public Object callMethod(Map arg0, Object arg1, String arg2, Object[] arg3) throws MethodFailedException {
            if (this.log.isErrorEnabled()) {
                this.log.error(
                    String.format("Forbidden class's method called.%s.%s(%s)", new Object[] { arg1, arg2, arg3 }));
            }
            return new UnsupportedOperationException();
        }

        public Object callStaticMethod(Map arg0, Class arg1, String arg2, Object[] arg3) throws MethodFailedException {
            if (this.log.isErrorEnabled()) {
                this.log.error(
                    String.format("Forbidden class's method called.%s.%s(%s)", new Object[] { arg1, arg2, arg3 }));
            }
            return new UnsupportedOperationException();
        }
    }

}
