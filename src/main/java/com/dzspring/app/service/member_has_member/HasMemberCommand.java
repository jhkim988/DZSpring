package com.dzspring.app.service.member_has_member;

import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dzspring.app.entity.Member;
import com.dzspring.app.repository.MemberRepository;

@Component
public class HasMemberCommand {
	
	@Autowired
	private MemberRepository memberRepository;
	
	@HasMemberType("id")
	public Member hasMemberById(String value) {
		return memberRepository.findOneById(value);
	}
	
	@HasMemberType("phone")
	public Member hasMemberByPhone(String value) {
		return memberRepository.findOneByPhone(value);
	}
	
	@HasMemberType("email")
	public Member hasMemberByEmail(String value) {
		return memberRepository.findOneByEmail(value);
	}
	
	public boolean hasType(String type) {
		return HasMemberMap.MAP.getMap().containsKey(type);
	}
	
	public Member invoke(String type, String value) {
		try {
			return (Member) HasMemberMap.MAP.getMap().get(type).invoke(this, value);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
}
