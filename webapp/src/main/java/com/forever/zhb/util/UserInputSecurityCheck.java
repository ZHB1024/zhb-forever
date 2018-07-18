package com.forever.zhb.util;

import com.forever.zhb.utils.IOUtils;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;
import ognl.Node;
import ognl.Ognl;
import org.apache.commons.lang.StringUtils;
import org.owasp.validator.html.AntiSamy;
import org.owasp.validator.html.CleanResults;
import org.owasp.validator.html.Policy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

public class UserInputSecurityCheck {

    private Logger log = LoggerFactory.getLogger(super.getClass());
    private AntiSamy antisamy = null;
    private int maxParamNameLength = 45;

    private String acceptedParamNames = "[a-zA-Z0-9\\._:']+";
    private Pattern acceptedPattern = Pattern.compile(this.acceptedParamNames);
    private Pattern springI18NLocal = Pattern
        .compile("^org.springframework\\.web\\.servlet\\.i18n\\.CookieLocaleResolver\\.LOCALE$");

    private Pattern struts1TokenParam = Pattern.compile("^org\\.apache\\.struts\\.taglib\\.html\\.TOKEN$");
    private Pattern struts2TokenParam = Pattern.compile("^struts\\.token\\.name$");

    private Pattern acceptedCookieNamesPattern = Pattern.compile("[a-zA-Z0-9\\._:\\-']+");

    private Pattern fileContentType = Pattern.compile("[a-zA-Z0-9\\.\\-/_]+");

    private String TW_COOKIE_NAME_PREFIX = "twcookie{";
    private String TW_COOKIE_NAME_END = "}";

    private List<Pattern> whiteParamNames = new ArrayList();

    public UserInputSecurityCheck(String maxParamLen, String whiteParamNamesConfig) {
        initBasicWhiteParamNames();

        boolean initFromProp = initUserInputSecurityProperties();
        if (!(initFromProp)) {
            initMaxParamLength(maxParamLen);
            initPatterns(this.whiteParamNames, whiteParamNamesConfig, 0);
        }

        inintAntisamy();
    }

    public UserInputSecurityCheck() {
        initBasicWhiteParamNames();
        initUserInputSecurityProperties();
        inintAntisamy();
    }

    protected Pattern getAcceptedPattern() {
        return this.acceptedPattern;
    }

    public boolean isWhiteParamName(String paramName) {
        for (Pattern p : this.whiteParamNames) {
            if (p.matcher(paramName).matches()) {
                return true;
            }
        }
        return false;
    }

    public boolean isParamValuesHasIllegalInput(String[] values) {
        for (String value : values) {
            if ((StringUtils.deleteWhitespace(value).contains("./"))
                || (StringUtils.deleteWhitespace(value).contains("/."))) {
                return true;
            }
            if ((value.contains("allowStaticMethodAccess")) || (value.contains("denyMethodExecution"))) {
                return true;
            }
            try {
                Node node = (Node) Ognl.parseExpression(value);
                String parseValue = node.toString();
                if ((parseValue.contains("allowStaticMethodAccess")) || (parseValue.contains("denyMethodExecution"))) {
                    this.log.info("Ognl parsed expression:{}", parseValue);
                    return true;
                }
            } catch (Exception e) {
            }
        }
        return false;
    }

    public boolean isParamNameHasIllegalInput(String paramName) {
        boolean isIllegal = isNameHasIllegalInput(this.acceptedPattern, paramName);
        if (isIllegal) {
            this.log.info("Illegal Param Name :{}", paramName);
        }
        return isIllegal;
    }

    public boolean isFileContentTypeHasIllegalInput(String contentType) {
        if (StringUtils.isBlank(contentType)) {
            this.log.warn("FileContentType is UnKnown.");
            return false;
        }

        boolean matchRs = !(this.fileContentType.matcher(contentType).matches());
        if (matchRs) {
            this.log.error("Illegal FileContentType:{}", contentType);
        }
        return matchRs;
    }

    public boolean isCookieNameHasIllegalInput(String cookieName) {
        cookieName = filterCookieName(cookieName);
        boolean isIllegal = isNameHasIllegalInput(this.acceptedCookieNamesPattern, cookieName);

        if (isIllegal) {
            this.log.info("Illegal Cookie Name :{}", cookieName);
        }
        return isIllegal;
    }

    private boolean isNameHasIllegalInput(Pattern acceptedPattern, String paramName) {
        if (isWhiteParamName(paramName)) {
            return false;
        }

        if (!(acceptedPattern.matcher(paramName).matches())) {
            return true;
        }

        if (paramName.length() > this.maxParamNameLength) {
            return true;
        }

        int i = paramName.indexOf(46);
        if ((i > -1) && (paramName.indexOf(46, i + 1) > -1)) {
            return true;
        }

        if ((paramName.contains("classLoader")) || (paramName.contains("class."))) {
            return true;
        }

        return ((paramName.contains("allowStaticMethodAccess")) || (paramName.contains("denyMethodExecution")));
    }

    public boolean containsXssScript(String[] textArray) {
        for (String item : textArray) {
            if (StringUtils.isBlank(item)) {
                continue;
            }

            if (StringUtils.containsIgnoreCase(item, "document.write")) {
                return true;
            }

            if (StringUtils.containsIgnoreCase(StringUtils.deleteWhitespace(item), "alert(")) {
                return true;
            }

            if (StringUtils.containsIgnoreCase(item, "=")) {
                return true;
            }

            if (containsIllegelChar(item)) {
                this.log.warn("XssMonitor:[{}]", item);
                return true;
            }
        }
        return false;
    }

    private boolean containsIllegelChar(String context) {
        try {
            CleanResults result = this.antisamy.scan(context);
            context = result.getCleanHTML();
            List errorList = result.getErrorMessages();
            if ((errorList != null) && (errorList.size() > 0))
                return true;
        } catch (Exception e) {
            if (this.log.isInfoEnabled())
                this.log.info(e.toString());
        }
        return false;
    }

    private void inintAntisamy() {
        ClassPathResource classpathResource = new ClassPathResource("META-INF/antisamy.xml");
        InputStream is = null;
        try {
            is = classpathResource.getInputStream();
            Policy policy = Policy.getInstance(is);
            this.antisamy = new AntiSamy(policy);
        }catch (org.owasp.validator.html.PolicyException e) {
            if (this.log.isErrorEnabled()) {
                this.log.info(e.toString());
            }
            throw new RuntimeException(e);
        } catch (IOException e) {
            if (this.log.isErrorEnabled()) {
                this.log.info(e.toString());
            }
            throw new RuntimeException(e);
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean initUserInputSecurityProperties() {
        ClassPathResource classpathResource = new ClassPathResource("META-INF/conf/userInputSecurityConfig.properties");
        if (!(classpathResource.exists())) {
            this.log.warn("找不到META-INF/conf/userInputSecurityConfig.properties");
            classpathResource = new ClassPathResource("classpath*:conf/userInputSecurityConfig.properties");
            if (!(classpathResource.exists())) {
                classpathResource = new ClassPathResource("userInputSecurityConfig.properties");
                if (!(classpathResource.exists())) {
                    return false;
                }
            }
        }
        InputStream is = null;
        String whiteParamNamesConfig;
        try {
            is = classpathResource.getInputStream();
            Properties prop = new Properties();
            prop.load(is);
            whiteParamNamesConfig = prop.getProperty("com.chsi.antisamy.config.whiteParamNames");
            initPatterns(this.whiteParamNames, whiteParamNamesConfig, 0);
            String maxParamNameLength = prop.getProperty("com.chsi.antisamy.config.maxParamNameLength");
            initMaxParamLength(maxParamNameLength);
            return true;
        } catch (IOException e) {
            if (this.log.isErrorEnabled()) {
                this.log.error("XssFilterInitError", e);
            }
            return false;
        } finally {
            IOUtils.closeQuietly(is);
        }
    }

    private void initMaxParamLength(String maxParamLen) {
        if ((null == maxParamLen) || (maxParamLen.length() <= 0))
            return;
        try {
            this.maxParamNameLength = Integer.parseInt(maxParamLen);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void initBasicWhiteParamNames() {
        this.whiteParamNames.add(this.struts1TokenParam);
        this.whiteParamNames.add(this.struts2TokenParam);
        this.whiteParamNames.add(this.springI18NLocal);
    }

    private void initPatterns(List<Pattern> patterns, String initParamValue, int flag) {
        if (StringUtils.isNotBlank(initParamValue)) {
            String[] regExps = StringUtils.split(StringUtils.trimToEmpty(initParamValue), ",");
            for (String regExp : regExps)
                patterns.add(Pattern.compile(regExp, flag));
        }
    }

    private String filterCookieName(String cookieName) {
        if (null == cookieName) {
            return cookieName;
        }
        if ((cookieName.startsWith(this.TW_COOKIE_NAME_PREFIX)) && (cookieName.endsWith(this.TW_COOKIE_NAME_END))) {
            cookieName = cookieName.substring(this.TW_COOKIE_NAME_PREFIX.length(), cookieName.length() - 1);
        }
        return cookieName;
    }

}
