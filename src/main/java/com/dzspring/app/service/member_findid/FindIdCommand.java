package com.dzspring.app.service.member_findid;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dzspring.app.entity.Member;
import com.dzspring.app.repository.MemberRepository;
import com.dzspring.app.service.Command;

@Component
public class FindIdCommand {
	
	private final MemberRepository memberRepository;
	
	@Autowired
	public FindIdCommand(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
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
		return FindIdCommandMap.MAP.getMap().containsKey(method);
	}
	
	@SuppressWarnings("unchecked")
	public Optional<Member> invoke(String method, String name, String value) {
		try {
			return (Optional<Member>) FindIdCommandMap.MAP.getMap().get(method).invoke(this, name, value);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return Optional.ofNullable(null);
	}
}
