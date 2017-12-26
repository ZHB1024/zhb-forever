package com.forever.zhb.tag;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;

import com.forever.zhb.dic.DeleteFlagEnum;

public class ZhbForeverTag extends TagSupport {
	
	private String delete;
	private String delete_;

	public ZhbForeverTag(){
		super();
	}
	
	public void release() {
        super.release();
    }
	
	public int doEndTag() throws JspException {
		String name = DeleteFlagEnum.getName(Integer.parseInt(delete));
        try {
            pageContext.getOut().print(name);
        } catch (IOException e) {
            throw new JspException("DeleteFlagTag error !", e);
        }
        return EVAL_PAGE;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
     */
    public int doStartTag() throws JspException {
        this.delete = (String) ExpressionEvaluatorManager.evaluate("delete",delete_, String.class, this, pageContext);
        //date = (Date) ExpressionEvaluatorManager.evaluate("date",date_, Date.class, this, pageContext);
        return super.doStartTag();
    }

	public void setDelete(String delete_) {
		this.delete_ = delete_;
	}
	
}
