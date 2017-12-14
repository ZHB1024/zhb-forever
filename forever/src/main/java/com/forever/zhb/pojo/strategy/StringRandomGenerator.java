package com.forever.zhb.pojo.strategy;

import java.io.Serializable;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.type.Type;

import com.forever.zhb.utils.RandomUtil;

public class StringRandomGenerator implements IdentifierGenerator, Configurable {
	
	public Serializable generate(SessionImplementor session, Object object) throws HibernateException {
        return RandomUtil.getRandomString(16);
    }

    public void configure(Type type, Properties params, Dialect d) throws MappingException {
    }

}
