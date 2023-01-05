package com.dzspring.app.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dzspring.app.entity.Member;
import com.dzspring.app.repository.MemberRepository;
import com.dzspring.app.service.Command;

@Component
public class HasMemberService {
	
	private final MemberRepository memberRepository;
	private final Map<String, Method> hasMemberCommandMap;
	
	@Autowired
	public HasMemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
		this.hasMemberCommandMap = new ConcurrentHashMap<>();
		Arrays.asList(getClass().getDeclaredMethods()).forEach(method -> {
			Command command = method.getDeclaredAnnotation(Command.class);
			if (command == null) return;
			hasMemberCommandMap.put(command.value(), method);
		});
	}
	
	@Command("id")
	public Member hasMemberById(String value) {
		return memberRepository.findOneById(value);
	}
	
	@Command("phone")
	public Member hasMemberByPhone(String value) {
		return memberRepository.findOneByPhone(value);
	}
	
	@Command("email")
	public Member hasMemberByEmail(String value) {
		return memberRepository.findOneByEmail(value);
	}
	
	public boolean invoke(String type, String value) {
		if (!hasMemberCommandMap.containsKey(type)) throw new UnsupportedOperationException();
		try {
			return null != hasMemberCommandMap.get(type).invoke(this, value);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return false;
	}
}
