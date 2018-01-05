package com.forever.zhb.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.MatchResult;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternCompiler;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.PatternMatcherInput;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;
import org.apache.oro.text.regex.Perl5Substitution;
import org.apache.oro.text.regex.Util;
import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringCutTag extends TagSupport {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger log;
    String valueEL;
    String lengthEL;

    public StringCutTag() {
        this.log = LoggerFactory.getLogger(super.getClass());
    }

    public int doEndTag() throws JspException {
        return 1;
    }

    public int doStartTag() throws JspException {
        String value = (String) ExpressionEvaluatorManager.evaluate("value", this.valueEL, String.class, this,
                this.pageContext);

        String length = (String) ExpressionEvaluatorManager.evaluate("length", this.lengthEL, String.class, this,
                this.pageContext);

        String reg = "<[^>]*>";
        String reg2 = ">([^<]*)<";
        PatternCompiler compiler = new Perl5Compiler();
        Pattern patternForHtml = null;
        Pattern patternForText = null;
        try {
            patternForHtml = compiler.compile(reg, 1);
            patternForText = compiler.compile(reg2, 1);
        } catch (MalformedPatternException e) {
            this.log.error("表达式错误", e);
        }
        PatternMatcher matcher = new Perl5Matcher();
        String noHtmlResult = Util.substitute(matcher, patternForHtml, new Perl5Substitution(""), value, -1);

        int len = noHtmlResult.replaceAll("[^\\x00-\\xff]", "00").length();
        int trimLen = len - Integer.parseInt(length);
        if (trimLen > 0) {
            if (matcher.contains(new PatternMatcherInput(value), patternForText)) {
                MatchResult result = matcher.getMatch();
                String str = result.group(1);
                str = getTrimStr(str, trimLen);
                value = Util.substitute(new Perl5Matcher(), patternForText,
                        new Perl5Substitution(String.format(">%s...<", new Object[] { str })), value);
            } else {
                value = String.format("%s...", new Object[] { getTrimStr(value, trimLen) });
            }
        }
        try {
            this.pageContext.getOut().print(value);
        } catch (IOException e) {
            this.log.error("Tag error!", e);
        }
        return 6;
    }

    private static String getTrimStr(String str, int trimLen) {
        int len2 = str.length();
        int k = 0;
        int m = 0;
        for (int i = len2 - 1; i >= 0; --i) {
            ++k;
            ++m;
            if ((str.charAt(i) < 0) || (str.charAt(i) > 255)) {
                ++k;
            }
            if (trimLen <= k) {
                break;
            }
        }

        str = str.substring(0, len2 - m);
        return str;
    }

    public void setValue(String valueEL) {
        this.valueEL = valueEL;
    }

    public void setLength(String lengthEL) {
        this.lengthEL = lengthEL;
    }

}
