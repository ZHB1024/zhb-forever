package com.forever.zhb.controller.annotation;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.forever.zhb.Constants;
import com.forever.zhb.proto.NewsProto;
import com.forever.zhb.proto.NewsProto.News;
import com.forever.zhb.proto.ProtoResult;
import com.forever.zhb.proto.support.ProtoConverter;
import com.forever.zhb.protobuf.PersonProbuf;
import com.forever.zhb.protobuf.PersonProbuf.Person;
import com.forever.zhb.protobuf.PersonProbuf.Person.PhoneNumber;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;

@Controller
@RequestMapping("/protobufController")
public class ProtoBufController{
	
	private Logger log = LoggerFactory.getLogger(ProtoBufController.class);
	
	@RequestMapping("/testProto")
	public void testProto(HttpServletRequest request,HttpServletResponse response){
		testNewsProto();
	}
	
	private void testPersonProto(){
		// 创建builder对象
		PersonProbuf.Person.Builder builder = PersonProbuf.Person.newBuilder();
		builder.setEmail("ggggggg@email.com");
		builder.setId(1);
		builder.setName("TestName");
		builder.addPhone(PersonProbuf.Person.PhoneNumber.newBuilder().setNumber("13664262866")
		.setType(PersonProbuf.Person.PhoneType.MOBILE));
		builder.addPhone(PersonProbuf.Person.PhoneNumber.newBuilder().setNumber("02869387891")
		.setType(PersonProbuf.Person.PhoneType.HOME));
		// 通过builder转化为Person对象
		Person person = builder.build();
		// 将person对象转化为字节流
		byte[] buf = person.toByteArray();
		System.out.println(buf);

		// 通过字节流转化 为Person对象
		try {
			Person person2 = PersonProbuf.Person.parseFrom(buf);
			log.info(person2.getName() + ", " + person2.getEmail());
			List<PhoneNumber> lstPhones = person2.getPhoneList();
			for (PhoneNumber phoneNumber : lstPhones) {
				log.info(phoneNumber.getNumber());
			}
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
	}
	
	public void testNewsProto(){
		NewsProto.News.Builder newsBuilder = NewsProto.News.newBuilder(); 
		newsBuilder.setId("123");
		newsBuilder.setTitle("测试");
		newsBuilder.setContent("测试一下不行呀");
		newsBuilder.setCreateTime(Calendar.getInstance().getTimeInMillis());
		News news = newsBuilder.build();
		byte[] newsByte = news.toByteArray();
		ProtoResult pr = new ProtoResult();
		pr.setProtoBytes(newsByte);
		
		ProtoConverter pci = new ProtoConverter();
		try {
			Message m = pci.converFromProto(Constants.NEWSPROTO_PATH, pr);
			if (null != m) {
				NewsProto.News news2 = (NewsProto.News)m;
				log.info(news2.getContent());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
