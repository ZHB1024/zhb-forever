package com.forever.zhb.spring.bean.locator;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public final class SpringBeanLocator {
	
	private static Map<String, SpringBeanLocator> beanFactoryMap = new HashMap();
    private ClassPathXmlApplicationContext beanFac;
    protected Logger log = LoggerFactory.getLogger(SpringBeanLocator.class);

    private SpringBeanLocator(ClassPathXmlApplicationContext beanFac) {
        this.beanFac = beanFac;
    }

    public static synchronized SpringBeanLocator getInstance(String confFileClasspath) {
        SpringBeanLocator l = (SpringBeanLocator) beanFactoryMap.get(confFileClasspath);
        if (null == l) {
            ClassPathXmlApplicationContext beanFac = new ClassPathXmlApplicationContext(confFileClasspath);
            l = new SpringBeanLocator(beanFac);
            beanFactoryMap.put(confFileClasspath, l);
        }
        return l;
    }

    public <T> T getBean(String beanName) {
        Object bean = this.beanFac.getBean(beanName);
        try {
            Object castBean = bean;
            return (T) castBean;
        } catch (ClassCastException e) {
            this.log.error("Class cast error,beanName[]" + beanName, e);
        }
        return null;
    }

    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return this.beanFac.getBeansOfType(type);
    }

}
