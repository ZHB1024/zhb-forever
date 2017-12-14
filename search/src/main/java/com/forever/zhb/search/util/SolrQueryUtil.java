package com.forever.zhb.search.util;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class SolrQueryUtil {
	
	 public static String generateFilterOrQuery(String field, Object[] strs) {
	        String format = "%s:(%s)";
	        return String.format(format, new Object[] { field, StringUtils.join(strs, " OR ") });
	    }

	    public static String generateFilterOrQuery(String field, List<String> list) {
	        return generateFilterOrQuery(field, list.toArray());
	    }

	    public static String keywordFilter(String keyword) {
	        String result = "*";
	        if (keyword != null) {
	            result = keyword.replaceAll("\"|\\+|\\-|\\&|\\||\\!|\\(|\\)|\\{|\\}|\\[|\\]|\\^|\\~|\\*|\\?|\\:|\\\\", "");
	        }
	        return result;
	    }

}
