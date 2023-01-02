package com.dzspring.app.service.admin_member_search;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum SearchCommandMap {
	MAP(new ConcurrentHashMap<>());
	
	private Map<String, Method> map;
	
	SearchCommandMap(Map<String, Method> map) {
		try {
			Class<?> cls = Class.forName("com.dzSpring.app.service.admin_member_search_command.MemberSearchCommand");
			Arrays.asList(cls.getDeclaredMethods()).forEach(method -> {
				SearchCommandName commandName = method.getDeclaredAnnotation(SearchCommandName.class);
				if (commandName == null) return;
				map.put(commandName.value(), method);
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
