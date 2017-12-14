package com.forever.zhb.utils;

import java.util.regex.Matcher;

import org.apache.commons.lang3.StringUtils;
import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.PatternCompiler;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidatorUtil {
	
	private static Logger log = LoggerFactory.getLogger(ValidatorUtil.class);
    private static final String IDENTITY_CARD_PATTERN = "(^\\d{15}$)|(^(\\d){17}([\\d|X|x|ｘ|Ｘ])$)";
    private static final String EMAIL_ORO_PATTERN = "^([\\w]+)(.[\\w]+)*@([\\w-]+\\.){1,5}([A-Za-z]){2,4}$";
    private static final String MPHONE_ORO_PATTERN = "^((13\\d{9})|(\\+8613\\d{9})|(8613\\d{9})|(158\\d{8})|(\\+86158\\d{8})|(86158\\d{8})|(15\\d{9})|(159\\d{8})|(\\+86159\\d{8})|(86159\\d{8})|(156\\d{8})|(\\+86156\\d{8})|(86156\\d{8}))$";
    private static final String PASSWORD_PATTERN = "^[^\\s]{6,14}$";
    private static final String DOUBLEBYTE_CHARACTERS_PATTERN = "[^\\x00-\\xff]";

    public static boolean isNull(String str) {
        return StringUtils.isBlank(str);
    }

    public static boolean isNull(Object[] object) {
        return ((null == object) || (object.length == 0));
    }

    public static boolean isNumber(String number) {
        if (isNull(number)) {
            return false;
        }
        return StringUtils.isNumeric(number);
    }

    @Deprecated
    public static boolean isDigit(String digit) {
        return isNumber(digit);
    }

    public static boolean isNumber(String number, int minLength) {
        if (!(isNumber(number))) {
            return false;
        }

        return (number.length() >= minLength);
    }

    public static boolean isDigit(String digit, int minLength) {
        if (!(isDigit(digit))) {
            return false;
        }

        return (digit.length() >= minLength);
    }

    public static boolean isNumber(String number, int minLength, int maxLength) {
        if (!(isNumber(number))) {
            return false;
        }

        int length = number.length();

        return ((length >= minLength) && (length <= maxLength));
    }

    public static boolean isDigit(String digit, int minLength, int maxLength) {
        if (!(isDigit(digit))) {
            return false;
        }

        int length = digit.length();

        return ((length >= minLength) && (length <= maxLength));
    }

    public static boolean isFloat(String str) {
        if (isNull(str)) {
            return false;
        }
        try {
            Float.parseFloat(str);

            return true;
        } catch (NumberFormatException e) {
        }
        return false;
    }

    public static boolean notContainsBlank(String str) {
        return (!(contains(str, "[\\s]+")));
    }

    public static boolean isChinese(String chinese) {
        if (isNull(chinese)) {
            return false;
        }
        return hasDoubleByteCharacters(chinese);
    }

    public static boolean isChinese(String chinese, int minLength) {
        if (!(isChinese(chinese))) {
            return false;
        }

        return (chinese.length() >= minLength);
    }

    public static boolean isLetter(String letter) {
        if (isNull(letter)) {
            return false;
        }

        String regExp = "[a-zA-Z]+";

        return match(letter, regExp);
    }

    public static boolean isLetterOrNumber(String str) {
        if (isNull(str)) {
            return false;
        }

        String regExp = "[0-9a-zA-Z]+";

        return match(str, regExp);
    }

    public static boolean isLetterOrDigit(String str) {
        if (isNull(str)) {
            return false;
        }

        String regExp = "[0-9a-zA-Z]+";

        return match(str, regExp);
    }

    public static boolean isLetterOrNumber(String str, int minLength) {
        if (!(isLetterOrNumber(str))) {
            return false;
        }

        return (str.length() >= minLength);
    }

    public static boolean isLetterOrDigit(String str, int minLength) {
        if (!(isLetterOrDigit(str))) {
            return false;
        }

        return (str.length() >= minLength);
    }

    public static boolean isLetterOrNumber(String str, int minLength, int maxLength) {
        if (!(isLetterOrNumber(str))) {
            return false;
        }

        int length = str.length();

        return ((length >= minLength) && (length <= maxLength));
    }

    public static boolean isLetterOrDigit(String str, int minLength, int maxLength) {
        if (!(isLetterOrDigit(str))) {
            return false;
        }

        int length = str.length();

        return ((length >= minLength) && (length <= maxLength));
    }

    public static boolean isXm(String xm) {
        if (isNull(xm)) {
            return false;
        }

        if (xm.indexOf("*") == 0) {
            return false;
        }

        if (xm.indexOf(".") != -1) {
            if (xm.indexOf(".") != xm.lastIndexOf(".")) {
                return false;
            }

            if ((xm.indexOf(".") == 0) || (xm.indexOf(".") == xm.length() - 1)) {
                return false;
            }
        }

        for (int i = 0; i < xm.length(); ++i) {
            char c = xm.charAt(i);
            String strc = Character.toString(c);

            if ((!(isChinese(strc))) && (!(strc.equals("*"))) && (!(strc.equals(".")))) {
                return false;
            }
        }

        return true;
    }

    public static boolean isValidXm(String str) {
        return false;
    }

    public static boolean isTelephone(String telephone) {
        if (isNull(telephone)) {
            return false;
        }

        String regExp = "[^0-9;\\+\\-\\(\\)]+";

        return (!(find(telephone, regExp)));
    }

    public static boolean isXz(String xz) {
        if (isNull(xz)) {
            return false;
        }

        String regExp = "([1-7](\\.5)?)|8";

        return match(xz, regExp);
    }

    public static boolean isSfzh(String sfzh) {
        if (StringUtils.isBlank(sfzh)) {
            return false;
        }
        return contains(sfzh, "(^\\d{15}$)|(^(\\d){17}([\\d|X|x|ｘ|Ｘ])$)");
    }

    public static boolean isDate(String date) {
        if (!(isDigit(date, 8, 8))) {
            return false;
        }

        String mm = date.substring(4, 6);
        String dd = date.substring(6, 8);

        if ((Integer.parseInt(mm) > 12) || (Integer.parseInt(mm) == 0)) {
            return false;
        }

        return ((Integer.parseInt(dd) <= 31) && (Integer.parseInt(dd) != 0));
    }

    public static boolean isYear(String year, int minYear, int maxYear) {
        if (!(isDigit(year, 4, 4))) {
            return false;
        }

        int tmp = Integer.parseInt(year);

        return ((tmp >= minYear) && (tmp <= maxYear));
    }

    public static boolean isWeb(String web) {
        if (isNull(web)) {
            return false;
        }

        String regExp = "^http[s]?://";

        if (!(match(web, regExp))) {
            return false;
        }

        regExp = "[^A-Za-z:/0-9\\-_\\.\\?=\\+%#&]+";

        return (!(find(web, regExp)));
    }

    public static boolean isEmail(String email) {
        if (StringUtils.isBlank(email)) {
            return false;
        }
        return contains(email, "^([\\w]+)(.[\\w]+)*@([\\w-]+\\.){1,5}([A-Za-z]){2,4}$");
    }

    public static boolean isBaseSsdm(String ssdm) {
        if (!(isNumber(ssdm, 2, 2))) {
            return false;
        }

        String[] ssdmArray = { "11", "12", "13", "14", "15", "21", "22", "23", "31", "32", "33", "34", "35", "36", "37",
                "41", "42", "43", "44", "45", "46", "50", "51", "52", "53", "54", "61", "62", "63", "64", "65" };

        for (int i = 0; i < ssdmArray.length; ++i) {
            String tmp = ssdmArray[i];

            if (tmp.equals(ssdm)) {
                return true;
            }
        }

        return false;
    }

    public static boolean isYzbm(String str) {
        return isDigit(str, 6, 6);
    }

    public static boolean isEndStr(String fileName, String fileExtendName) {
        if ((isNull(fileName)) || (isNull(fileExtendName))) {
            return false;
        }

        String regExp = ".+\\." + fileExtendName + "$";

        return match(fileName, regExp);
    }

    public static boolean isEndStrIgnoreCase(String fileName, String fileExtendName) {
        if ((isNull(fileName)) || (isNull(fileExtendName))) {
            return false;
        }

        fileName = fileName.toLowerCase();
        fileExtendName = fileExtendName.toLowerCase();

        String regExp = ".+\\." + fileExtendName + "$";

        return match(fileName, regExp);
    }

    public static boolean containsIgnoreCase(String str1, String str2) {
        if ((isNull(str1)) || (isNull(str2))) {
            return false;
        }

        str1 = str1.toLowerCase();
        str2 = str2.toLowerCase();

        return (str1.indexOf(str2) != -1);
    }

    public static int lengthISO(String str) {
        if (isNull(str)) {
            return 0;
        }
        try {
            str = new String(str.getBytes("GBK"), "ISO-8859-1");

            return str.length();
        } catch (Exception e) {
        }
        return -1;
    }

    public static boolean match(String str, String regExp) {
        if ((isNull(str)) || (isNull(regExp))) {
            return false;
        }

        java.util.regex.Pattern p = java.util.regex.Pattern.compile(regExp);
        Matcher m = p.matcher(str);

        return m.matches();
    }

    public static boolean find(String str, String regExp) {
        if ((isNull(str)) || (isNull(regExp))) {
            return false;
        }

        java.util.regex.Pattern p = java.util.regex.Pattern.compile(regExp);
        Matcher m = p.matcher(str);

        return m.find();
    }

    public static boolean isValidMobilePhone(String mobilePhone) {
        if (StringUtils.isBlank(mobilePhone)) {
            return false;
        }
        return contains(mobilePhone,
                "^((13\\d{9})|(\\+8613\\d{9})|(8613\\d{9})|(158\\d{8})|(\\+86158\\d{8})|(86158\\d{8})|(15\\d{9})|(159\\d{8})|(\\+86159\\d{8})|(86159\\d{8})|(156\\d{8})|(\\+86156\\d{8})|(86156\\d{8}))$");
    }

    public static boolean isValidUserName(String loginName) {
        if (StringUtils.isBlank(loginName)) {
            return false;
        }
        boolean isEmail = isEmail(loginName);
        if (!(isEmail)) {
            return false;
        }
        int length = loginName.length();

        return ((length >= 6) && (length <= 50));
    }

    public static boolean isValidPassword(String password) {
        if (StringUtils.isBlank(password)) {
            return false;
        }
        return contains(password, "^[^\\s]{6,14}$");
    }

    protected static boolean hasDoubleByteCharacters(String str) {
        if (StringUtils.isBlank(str)) {
            return false;
        }
        return contains(str, "[^\\x00-\\xff]");
    }

    private static boolean contains(String str, String pattern) {
        PatternCompiler compiler = new Perl5Compiler();
        boolean hasIt = false;
        try {
            org.apache.oro.text.regex.Pattern chinesePattern = compiler.compile(pattern, 1);

            PatternMatcher matcher = new Perl5Matcher();
            hasIt = matcher.contains(str, chinesePattern);
        } catch (MalformedPatternException e) {
            log.error("StringUtil.contains异常", e);
            if (log.isDebugEnabled()) {
                log.debug(String.format("方法的调用形式, StringUtil.contains(%s, %s)", new Object[] { str, pattern }));
            }
        }
        return hasIt;
    }

}
