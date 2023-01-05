package com.dzspring.app.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dzspring.app.entity.Member;
import com.dzspring.app.repository.MemberRepository;
import com.dzspring.app.service.Command;

@Component
public class AdminMemberSearchService {

	private final MemberRepository memberRepository;
	private final Map<String, Method> searchCommandMap;

	@Autowired
	public AdminMemberSearchService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
		this.searchCommandMap = new ConcurrentHashMap<>();
		Arrays.asList(getClass().getDeclaredMethods()).forEach(method -> {
			Command command = method.getDeclaredAnnotation(Command.class);
			if (command == null)
				return;
			searchCommandMap.put(command.value(), method);
		});
	}

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
		return searchCommandMap.containsKey(method);
	}

	@SuppressWarnings("unchecked")
	public List<Member> list(Map<String, Object> map) {
		try {
			String method = (String) map.get("method");
			Map<String, Object> value = (Map<String, Object>) map.get("value");
			return (List<Member>) searchCommandMap.get(method).invoke(this, value);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}
}
