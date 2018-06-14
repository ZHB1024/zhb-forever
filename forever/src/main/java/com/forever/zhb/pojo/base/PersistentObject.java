package com.forever.zhb.pojo.base;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.BooleanConverter;
import org.apache.commons.beanutils.converters.DoubleConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.beanutils.converters.LongConverter;
import org.hibernate.annotations.GenericGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class PersistentObject implements Serializable, Cloneable{
	
	protected static transient Logger log = LoggerFactory.getLogger(PersistentObject.class);
	
	protected String id;
	protected Calendar createTime;
	protected Calendar updateTime;
	protected Integer deleteFlag;

    public void setData(PersistentObject vo) {
        copyProperties(vo);
    }
    
    public abstract String getId();
    
    public abstract void setId(String id);

    public void copyProperties(PersistentObject origin) {
        try {
            ConvertUtils.register(new DoubleConverter(null), Double.class);
            ConvertUtils.register(new IntegerConverter(null), Integer.class);
            ConvertUtils.register(new LongConverter(null), Long.class);
            ConvertUtils.register(new BooleanConverter(null), Boolean.class);

            BeanUtils.copyProperties(this, origin);
        } catch (IllegalAccessException e) {
            log.error("copyProperties failed", e);
            throw new IllegalArgumentException("拷贝属性失败");
        } catch (InvocationTargetException e) {
            log.error("copyProperties failed", e);
            throw new IllegalArgumentException("拷贝属性失败");
        }
    }

    public Object clone() throws CloneNotSupportedException {
        try {
            PersistentObject clone = (PersistentObject) super.getClass().newInstance();
            clone.copyProperties(this);
            return clone;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
