package com.dzspring.app.service.admin_member_search;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dzspring.app.entity.Member;
import com.dzspring.app.repository.MemberRepository;

@Component
public class MemberSearchCommand {
	
	@Autowired
	private MemberRepository memberRepository;
	
	@SearchCommandName("all")
	public List<Member> allSearch(String value, String last) {
		return memberRepository.findAllLimit(value, last);
	}
	
	@SearchCommandName("createdAt")
	public List<Member> createdAtSearch(String value, String last) {
		return memberRepository.findByCreatedAtLimit10(value, last);
	}
	
	@SearchCommandName("email")
	public List<Member> emailSearch(String value, String last) {
		return memberRepository.findByEmailLimit10After(value, last);
	}
	
	@SearchCommandName("id")
	public List<Member> idSearch(String value, String last) {
		return memberRepository.findByIdLimit10(value, last);
	}
	
	@SearchCommandName("name")
	public List<Member> nameSearch(String value, String last) {
		return memberRepository.findByNameLimit10(value, last);
	}
	
	@SearchCommandName("phone")
	public List<Member> phoneSearch(String value, String last) {
		return memberRepository.findByPhoneLiit10(value, last);
	}
	
	@SearchCommandName("updatedAt")
	public List<Member> updatedAt(String value, String last) {
		return memberRepository.findByUpdatedAtLimit10(value, last);
	}
	
	@SearchCommandName("authority")
	public List<Member> authority(String value, String last) {
		return memberRepository.findByAuthorityAtLimit(value, last);
	}
	
	public boolean hasMethod(String method) {
		return SearchCommandMap.MAP.getMap().containsKey(method);
	}
	
	@SuppressWarnings("unchecked")
	public List<Member> invoke(String method, String value, String last) {
		try {
			return (List<Member>) SearchCommandMap.MAP.getMap().get(method).invoke(this, value, last);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}
}
