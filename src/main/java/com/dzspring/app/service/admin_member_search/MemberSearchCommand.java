package com.dzspring.app.service.admin_member_search;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dzspring.app.entity.Member;
import com.dzspring.app.repository.MemberRepository;
import com.dzspring.app.service.Command;

@Component
public class MemberSearchCommand {
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Command("all")
	public List<Member> allSearch(Map<String, Object> map) {
		return memberRepository.findAllLimit10(map);
	}
	
	@Command("email")
	public List<Member> emailSearch(Map<String, Object> map) {
		return memberRepository.findByEmailLimit10(map);
	}
	
	@Command("id")
	public List<Member> idSearch(Map<String, Object> map) {
		return memberRepository.findByIdLimit10(map);
	}
	
	@Command("name")
	public List<Member> nameSearch(Map<String, Object> map) {
		return memberRepository.findByNameLimit10(map);
	}
	
	@Command("phone")
	public List<Member> phoneSearch(Map<String, Object> map) {
		return memberRepository.findByPhoneLimit10(map);
	}
	
	@Command("authority")
	public List<Member> authority(Map<String, Object> map) {
		return memberRepository.findByAuthorityLimit10(map);
	}
	
	@Command("createdAt")
	public List<Member> createdAtSearch(Map<String, Object> map) {
		String lastId = (String) map.get("lastId");
		if (lastId != null) {
			Member member = memberRepository.findOneById(lastId);
			map.put("lastCreatedAt", member.getCreatedAt());
		}
		return memberRepository.findByCreatedAtLimit10(map);
	}
	
	@Command("updatedAt")
	public List<Member> updatedAt(Map<String, Object> map) {
		String lastId = (String) map.get("lastId");
		if (lastId != null) {
			Member member = memberRepository.findOneById(lastId);
			map.put("lastUpdatedAt", member.getUpdatedAt());
		}
		return memberRepository.findByUpdatedAtLimit10(map);
	}
	
	public boolean hasMethod(String method) {
		return SearchCommandMap.MAP.getMap().containsKey(method);
	}
	
	@SuppressWarnings("unchecked")
	public List<Member> invoke(String method, Map<String, Object> map) {
		try {
			return (List<Member>) SearchCommandMap.MAP.getMap().get(method).invoke(this, map);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}
}
