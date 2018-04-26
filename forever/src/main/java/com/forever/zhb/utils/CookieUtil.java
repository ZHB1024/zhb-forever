package com.forever.zhb.utils;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

public class CookieUtil {

	public static final int MAXCOUNT = 200;
    public static final String SEPARATOR = "_";
    static final int MAXAGE = 43200;

    public static int count(HttpServletRequest request, String namePrefix) {
        Cookie[] cookies = request.getCookies();
        int count = 0;
        if (null != cookies) {
            for (Cookie c : cookies) {
                if (c.getName().startsWith(namePrefix)) {
                    count += count(c);
                }
            }
        }
        return count;
    }

    public static void add(HttpServletRequest request, HttpServletResponse response, String name, List<String> values) {
        if ((null == values) || (values.size() == 0)) {
            return;
        }
        String value = getCookieValue(request, name);
        List tmp = splitToList(value);
        if (null == tmp) {
            tmp = new ArrayList();
        }
        for (String v : values) {
            if (!(tmp.contains(v))) {
                tmp.add(v);
            }
        }
        setCookie(request, response, name, tmp);
    }

    public static int addCookie(HttpServletRequest request, HttpServletResponse response, String name,
            List<String> values, String prefix) {
        int count = 0;
        int orgCount = 0;
        orgCount = count(request, prefix);
        if (orgCount + values.size() > 200) {
            return -1;
        }
        String value = getCookieValue(request, name);
        List tmp = splitToList(value);
        if (null == tmp) {
            tmp = new ArrayList();
        }
        for (String v : values) {
            if (!(tmp.contains(v))) {
                tmp.add(v);
                ++count;
            }
        }
        setCookie(request, response, name, tmp);
        return count;
    }

    public static void remove(HttpServletRequest request, HttpServletResponse response, String name,
            List<String> values) {
        if ((null == values) || (values.size() == 0)) {
            return;
        }
        String value = getCookieValue(request, name);
        List tmp = splitToList(value);
        if (null != tmp) {
            for (String v : values) {
                tmp.remove(v);
            }
            setCookie(request, response, name, tmp);
        }
    }

    public static void remove(HttpServletRequest request, HttpServletResponse response, String name, String[] values) {
        if ((null == values) || (values.length == 0)) {
            return;
        }
        String value = getCookieValue(request, name);
        List tmp = splitToList(value);
        if (null != tmp) {
            for (String v : values) {
                tmp.remove(v);
            }
            setCookie(request, response, name, tmp);
        }
    }

    public static void clear(HttpServletRequest request, HttpServletResponse response, String name) {
        setCookie(request, response, name, new ArrayList());
    }

    public static List<String> get(HttpServletRequest request, String name) {
        String value = getCookieValue(request, name);
        return splitToList(value);
    }

    public static String getCookieValue(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie c : cookies) {
                if (c.getName().equals(name)) {
                    return c.getValue();
                }
            }
        }
        return null;
    }

    public static void setCookieValue(HttpServletResponse response, String name, String value, String path,
            Integer maxAgeSeconds) {
        Cookie cookie = new Cookie(name, value);
        if (null == maxAgeSeconds)
            cookie.setMaxAge(43200);
        else {
            cookie.setMaxAge(maxAgeSeconds.intValue());
        }
        cookie.setPath(path);
        response.addCookie(cookie);
    }

    private static void setCookie(HttpServletRequest request, HttpServletResponse response, String name,
            List<String> values) {
        if (null != values) {
            String value = "";
            for (int i = 0; i < values.size(); ++i) {
                if (i != 0) {
                    value = value + "_";
                }
                value = value + ((String) values.get(i));
            }
            String ctxPath = request.getContextPath();
            setCookieValue(response, name, value, ctxPath, Integer.valueOf(43200));
        }
    }

    private static int count(Cookie cookie) {
        if (null == cookie) {
            return 0;
        }
        String value = cookie.getValue();
        List tmp = splitToList(value);
        if (null == tmp) {
            return 0;
        }
        return tmp.size();
    }

    private static List<String> splitToList(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        String[] tmp = value.split("_");
        List tmpL = new ArrayList();
        for (String v : tmp) {
            tmpL.add(v);
        }
        return tmpL;
    }
}
