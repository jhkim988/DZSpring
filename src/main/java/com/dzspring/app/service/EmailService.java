package com.dzspring.app.service;

import org.springframework.stereotype.Service;

@Service("emailService")
public interface EmailService {
	void sendInitPwd(String to, String initPwd);
	void sendWelcome(String to, String userName);
}
