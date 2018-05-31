package com.forever.zhb.search.service.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseService implements ServiceWithLifestyle {
	
	protected Logger log;

    public BaseService() {
        this.log = LoggerFactory.getLogger(super.getClass());
    }

    public void create() {
        if (this.log.isDebugEnabled())
            this.log.debug("Create service:" + super.getClass().getName());
    }

    public void remove() {
        if (this.log.isDebugEnabled())
            this.log.debug("Remove service:" + super.getClass().getName());
    }

}
