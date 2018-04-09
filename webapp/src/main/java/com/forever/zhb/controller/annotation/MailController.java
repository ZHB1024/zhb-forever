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
		//MailVo 包含 接收方的邮箱地址、邮件标题、邮件内容
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
		properties.put("mail.smtp.socketFactory.fallback", "false");
		properties.put("mail.smtp.timeout", PropertyUtil.getMailSmtpTimeOut());
		sender.setJavaMailProperties(properties);
		
		MimeMessage mimeMessage = sender.createMimeMessage();
		
		//可以对邮件内容加自定义样式，我这里没有加
		String content_start = "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf8\"/></head><body>";
        String content_end = "</body></html>";
        StringBuilder str = new StringBuilder();
        str.append(content_start);
        str.append(mailVo.getContent());
        str.append(content_end);
        String content = str.toString();
        
        //mimeMessage.setContent(text, "text/html;charset=GB18030");
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(mimeMessage, true,"GBK");
            helper.setFrom(sender.getUsername());
            helper.setTo(mailVo.getToMail());
            helper.setSubject(mailVo.getTitle());
            helper.setText(content, true);
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
