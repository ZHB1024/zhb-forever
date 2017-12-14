package com.forever.zhb.page;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PageUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(PageUtil.class);

    public static <T> Page<T> getPage(Iterator<T> itr, long start, int pageCount, long totalCount) {
        List list = new ArrayList((int) pageCount);
        while (itr.hasNext()) {
            Object pageObject = itr.next();
            if (null != pageObject)
                list.add(pageObject);
            else {
                logger.error("Null object founded!");
            }
        }
        if ((0 == list.size()) || (0 == pageCount)) {
            return new Page(new ArrayList(0), 0, 0, pageCount, false);
        }
        boolean hasNext = start + pageCount < totalCount;
        return new Page(list, (int) start, (int) totalCount, pageCount, hasNext);
    }

    public static int getValidateStart(int start, int pageSize, int totalCount) {
        if ((start <= 0) || (totalCount <= 0)) {
            return 0;
        }
        if (start < totalCount) {
            return start;
        }
        boolean flag = totalCount % pageSize == 0;
        int lastPageCount = (flag) ? pageSize : totalCount % pageSize;
        return (totalCount - lastPageCount);
    }

}
