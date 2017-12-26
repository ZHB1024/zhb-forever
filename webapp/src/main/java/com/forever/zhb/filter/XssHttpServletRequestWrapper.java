package com.forever.zhb.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.HtmlUtils;

public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
	
	protected Logger logger = LoggerFactory.getLogger(XssHttpServletRequestWrapper.class);

	public XssHttpServletRequestWrapper(HttpServletRequest request) {
		super(request);
	}

	@Override
	public String getParameter(String name) {
		String parame = super.getParameter(name);
		String vlaue = StringEscapeUtils.escapeHtml4(parame);
		String vlaue2 = HtmlUtils.htmlEscape(parame);
		logger.info("XSSUtils: {}",vlaue);
		return vlaue;
	}
	
	/*
	 * @Override public String getHeader(String name) { return
	 * StringEscapeUtils.escapeHtml4(super.getHeader(name)); }
	 */

	/*
	 * @Override public String getQueryString() { return
	 * StringEscapeUtils.escapeHtml4(super.getQueryString()); }
	 */

	/*
	 * @Override public String[] getParameterValues(String name) { String[]
	 * values = super.getParameterValues(name); if(values != null) { int length
	 * = values.length; String[] escapseValues = new String[length]; for(int i =
	 * 0; i < length; i++){ escapseValues[i] =
	 * StringEscapeUtils.escapeHtml4(values[i]); } return escapseValues; }
	 * return super.getParameterValues(name); }
	 */

}
