package com.dzspring.app.service.admin_member_search;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dzspring.app.entity.Member;
import com.dzspring.app.repository.MemberRepository;

@Component
public class MemberSearchCommand {
	
	@Autowired
	private MemberRepository memberRepository;
	
	@SearchCommandName("all")
	public List<Member> allSearch(Map<String, String> map) {
		return memberRepository.findAllLimit10(map);
	}
	
	@SearchCommandName("email")
	public List<Member> emailSearch(Map<String, String> map) {
		return memberRepository.findByEmailLimit10(map);
	}
	
	@SearchCommandName("id")
	public List<Member> idSearch(Map<String, String> map) {
		return memberRepository.findByIdLimit10(map);
	}
	
	@SearchCommandName("name")
	public List<Member> nameSearch(Map<String, String> map) {
		return memberRepository.findByNameLimit10(map);
	}
	
	@SearchCommandName("phone")
	public List<Member> phoneSearch(Map<String, String> map) {
		return memberRepository.findByPhoneLimit10(map);
	}
	
	@SearchCommandName("authority")
	public List<Member> authority(Map<String, String> map) {
		return memberRepository.findByAuthorityLimit10(map);
	}
	
	@SearchCommandName("createdAt")
	public List<Member> createdAtSearch(Map<String, String> map) {
		return memberRepository.findByCreatedAtLimit10(map);
	}
	
	@SearchCommandName("updatedAt")
	public List<Member> updatedAt(Map<String, String> map) {
		return memberRepository.findByUpdatedAtLimit10(map);
	}
	
	public boolean hasMethod(String method) {
		return SearchCommandMap.MAP.getMap().containsKey(method);
	}
	
	@SuppressWarnings("unchecked")
	public List<Member> invoke(Map<String, String> map) {
		try {
			return (List<Member>) SearchCommandMap.MAP.getMap().get(map.get("method")).invoke(this, map);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}
}
