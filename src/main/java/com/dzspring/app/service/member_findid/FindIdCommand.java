package com.dzspring.app.service.member_findid;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dzspring.app.entity.Member;
import com.dzspring.app.repository.MemberRepository;

@Component
public class FindIdCommand {
	
	@Autowired
	private MemberRepository memberRepository;
	
	@FindIdMethod("phone")
	public Optional<Member> findIdByPhone(String value) {
		return Optional.ofNullable(memberRepository.findOneByPhone(value));
	}
	
	@FindIdMethod("email")
	public Optional<Member> findIdByEmail(String value) {
		return Optional.ofNullable(memberRepository.findOneByEmail(value));
	}
	
	public boolean hasMethod(String method) {
		return FindIdCommandMap.MAP.getMap().containsKey(method);
	}
	
	@SuppressWarnings("unchecked")
	public Optional<Member> invoke(String method, String value) {
		try {
			return (Optional<Member>) FindIdCommandMap.MAP.getMap().get(method).invoke(this, value);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return Optional.ofNullable(null);
	}
}
