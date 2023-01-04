package com.dzspring.app.service.admin_member_search;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.dzspring.app.service.Command;

public enum SearchCommandMap {
	MAP(new ConcurrentHashMap<>());
	
	private Map<String, Method> map;
	
	SearchCommandMap(Map<String, Method> map) {
		try {
			Class<?> cls = Class.forName("com.dzspring.app.service.admin_member_search.MemberSearchCommand");
			Arrays.asList(cls.getDeclaredMethods()).forEach(method -> {
				Command command = method.getDeclaredAnnotation(Command.class);
				if (command == null) return;
				map.put(command.value(), method);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.map = map;
	}
	
	public Map<String, Method> getMap() {
		return map;
	}
}
