package com.forever.zhb.config;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.forever.zhb.util.StringUtil;

public class ConfigurablePropertyPlaceholder extends PropertyPlaceholderConfigurer{
	
	private String propertyLocationSystemProperty;
    private String defaultPropertyFileName;

    public String getPropertyLocationSystemProperty() {
        return this.propertyLocationSystemProperty;
    }

    public void setPropertyLocationSystemProperty(String propertyLocationSystemProperty) {
        this.propertyLocationSystemProperty = propertyLocationSystemProperty;
    }

    public String getDefaultPropertyFileName() {
        return this.defaultPropertyFileName;
    }

    public void setDefaultPropertyFileName(String defaultPropertyFileName) {
        this.defaultPropertyFileName = defaultPropertyFileName;
    }

    protected void loadProperties(Properties props) throws IOException {
        Resource location = null;
        if (StringUtils.isNotEmpty(this.propertyLocationSystemProperty)) {
            location = new FileSystemResource(
                    StringUtil.getPropertyPath(this.propertyLocationSystemProperty, this.defaultPropertyFileName));
        }

        setLocation(location);
        super.loadProperties(props);
    }

}
