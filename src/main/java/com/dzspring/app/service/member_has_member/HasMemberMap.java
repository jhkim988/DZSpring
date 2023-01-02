package com.dzspring.app.service.member_has_member;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum HasMemberMap {
	MAP(new ConcurrentHashMap<>());
	
	private Map<String, Method> map;
	
	HasMemberMap(Map<String, Method> map) {
		try {
			Class<?> cls = Class.forName("com.dzspring.app.service.member_has_member.HasMemberCommand");
			Arrays.asList(cls.getDeclaredMethods()).forEach(method -> {
				HasMemberType type = method.getDeclaredAnnotation(HasMemberType.class);
				if (type == null) return;
				map.put(type.value(), method);
			});
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		this.map = map;
	}
	
	Map<String, Method> getMap() {
		return map;
	}
}
