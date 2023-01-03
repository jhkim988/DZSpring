package com.dzspring.app.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.dzspring.app.service.EmailService;

@Service("emailServiceImpl")
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender mailSender;
	
	@Override
	public void sendInitPwd(String to, String auth) {
		MimeMessage message = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
			helper.setFrom("DZSpring@gmail.com", "DZSpring");
			helper.setTo(to);
			helper.setSubject("[DZSpring] 비밀번호 초기화 안내");
			
			StringBuilder sb = new StringBuilder();
			try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/initPwd.html")));) {
				String read = null;
				while ((read = br.readLine()) != null) {
					sb.append(read);
				}
			}
			String body = sb.toString();
			body = body.replace("${USER_AUTHENTICATE}", auth);
			System.out.println(body);
			helper.setText(body, true);
			mailSender.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
