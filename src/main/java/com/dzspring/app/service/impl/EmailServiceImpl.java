package com.dzspring.app.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import com.dzspring.app.service.EmailService;

@EnableAsync
@Async
@Service("emailServiceImpl")
public class EmailServiceImpl implements EmailService {

	
	private final JavaMailSender mailSender;
	
	@Autowired
	public EmailServiceImpl(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	private String htmlToString(String path) {
		StringBuilder sb = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(path)));) {
			String read = null;
			while ((read = br.readLine()) != null) {
				sb.append(read);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	private MimeMessageHelper getInitMimeMessageHelper(MimeMessage message, String to, String subject, String body, boolean html) {
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
			helper.setFrom("DZSpring@gmail.com", "DZSpring");
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(body, html);
			return helper;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private void sendTemplate(String to, String subject, String body, boolean html) {
		MimeMessage message = mailSender.createMimeMessage();
		getInitMimeMessageHelper(message, to, subject, body, true);
		mailSender.send(message);
	}
	
	@Override
	public void sendInitPwd(String to, String auth) {
		String body = htmlToString("/initPwd.html").toString();
		body = body.replace("${USER_AUTHENTICATE}", auth);
		sendTemplate(to, "[DZSpring] 비밀번호 초기화 안내", body, true);
	}
	
	@Override
	public void sendWelcome(String to, String userName) {
		String body = htmlToString("/welcome.html").toString();
		body = body.replace("${USER_NAME}", userName);
		sendTemplate(to, "[DZSpring] 회원가입을 환영합니다!", body, true);
	}
}
