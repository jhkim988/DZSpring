package com.dzspring.app.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dzspring.app.entity.Member;
import com.dzspring.app.repository.MemberRepository;
import com.dzspring.app.service.Command;

@Component
public class FindMemberIdService {
	
	private final MemberRepository memberRepository;
	private final Map<String, Method> findIdCommandMap;
	
	@Autowired
	public FindMemberIdService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
		this.findIdCommandMap = new ConcurrentHashMap<>();
		Arrays.asList(getClass().getDeclaredMethods()).forEach(method -> {
			Command command = method.getDeclaredAnnotation(Command.class);
			if (command == null) return;
			findIdCommandMap.put(command.value(), method);
		});
	}
	
	@Command("phone")
	public Optional<Member> findIdByPhone(String name, String value) {
		Member member = memberRepository.findOneByPhone(value);
		if (member == null || !member.getName().equals(name)) member = null;
		return Optional.ofNullable(member);
	}
	
	@Command("email")
	public Optional<Member> findIdByEmail(String name, String value) {
		Member member = memberRepository.findOneByEmail(value);
		if (member == null || !member.getName().equals(name)) member = null;
		return Optional.ofNullable(member);
	}
	
	public boolean hasMethod(String method) {
		return findIdCommandMap.containsKey(method);
	}
	
	@SuppressWarnings("unchecked")
	public Optional<Member> invoke(String method, String name, String value) {
		try {
			return (Optional<Member>) findIdCommandMap.get(method).invoke(this, name, value);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return Optional.ofNullable(null);
	}
}	
