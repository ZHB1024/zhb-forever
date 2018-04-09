package com.forever.zhb.controller.annotation;

import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.forever.zhb.basic.BasicController;
import com.forever.zhb.utils.PropertyUtil;
import com.forever.zhb.vo.MailVo;

@Controller
@RequestMapping("/htgl/mailController")
public class MailController extends BasicController {

	private Logger logger = LoggerFactory.getLogger(MailController.class);

	@RequestMapping(value = "/toMail", method = RequestMethod.GET)
	public String toMail(HttpServletRequest request, HttpServletResponse response) {

		return "htgl.mail.index";
	}

	@RequestMapping(value = "/sendMail", method = RequestMethod.POST)
	public String sendMail(HttpServletRequest request, HttpServletResponse response, MailVo mailVo) {
		if (null == mailVo) {
			request.setAttribute("errorMsg", "信息必须填写！");
			return "htgl.mail.index";
		}
		if (StringUtils.isBlank(mailVo.getToMail()) ) {
			request.setAttribute("errorMsg", "邮件地址必须填写！");
			request.setAttribute("mailVo", mailVo);
			return "htgl.mail.index";
		}
		if (StringUtils.isBlank(mailVo.getTitle())) {
			request.setAttribute("errorMsg", "邮件标题必须填写！");
			request.setAttribute("mailVo", mailVo);
			return "htgl.mail.index";
		}
		if (StringUtils.isBlank(mailVo.getContent())) {
			request.setAttribute("errorMsg", "邮件内容必须填写！");
			request.setAttribute("mailVo", mailVo);
			return "htgl.mail.index";
		}
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setHost(PropertyUtil.getMailHost());
		sender.setUsername(PropertyUtil.getMailUserName());
		sender.setPassword(PropertyUtil.getMailPassword());
		sender.setPort(Integer.parseInt(PropertyUtil.getMailPort()));

		Properties properties = new Properties();
		properties.put("mail.smtp.auth", PropertyUtil.getMailSmtpAuth());
		// properties.put("mail.smtp.socketFactory.port", MAIL_PORT);
		properties.put("mail.smtp.socketFactory.fallback", "false");
		properties.put("mail.smtp.timeout", PropertyUtil.getMailSmtpTimeOut());
		sender.setJavaMailProperties(properties);
		
		MimeMessage mimeMessage = sender.createMimeMessage();
		
		/*String template_start = "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf8\"/></head><body>";
        String template_end = "</body></html>";
        StringBuilder str = new StringBuilder();
        str.append(template_start);
        str.append("老师 ，您好："
                + "<BR><BR>"
                + "您正在申请学信网数字证书解锁或初始化PIN码业务，您的验证码为：<strong>"  + "</strong>。<BR>"
                + "请点击<A href=" + ">学信网数字证书在线解锁系统</A>，登录并输入动态验证码进行解锁，解锁后数字证书PIN码将重置为1234。" 
                + "<BR><BR>"
                + "提示："
                + "<BR>"
                + "&nbsp;&nbsp;&nbsp;&nbsp;1、请小心保存您的验证码及电子钥匙，以确保您的电子钥匙安全。"
                + "<BR>"
                + "&nbsp;&nbsp;&nbsp;&nbsp;2、为了保证您的账户安全，您需要重置"  + "平台密码。"
                + "<BR>"
                + "&nbsp;&nbsp;&nbsp;&nbsp;3、此邮件为审核通过后系统通知邮件，请不要直接回复此邮件，如有其他疑问，请点击<A href='http://cert.chsi.com.cn/help/questions.jsp'>联系我们</A>获取咨询方式。"
                );
        str.append(template_end);
        String text = str.toString();*/
        
        //mimeMessage.setContent(text, "text/html;charset=GB18030");
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(mimeMessage, true,"GBK");
            helper.setFrom(sender.getUsername());
            helper.setTo(mailVo.getToMail());
            helper.setSubject(mailVo.getTitle());
            helper.setText(mailVo.getContent(), true);
            sender.send(mimeMessage);
        } catch (MessagingException e1) {
        	logger.info("发送邮件时，发送失败");
            logger.info(e1.getMessage());
            e1.printStackTrace();
        }
        request.setAttribute("result", "发送成功！");
		return "htgl.mail.sendMail";
	}

}
