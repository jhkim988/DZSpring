package com.dzspring.app.service.member_findid;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum FindIdCommandMap {
	MAP(new ConcurrentHashMap<>());
	
	private Map<String, Method> map;
	
	FindIdCommandMap(Map<String, Method> map) {
		try {
			Class<?> cls = Class.forName("com.dzspring.app.service.member_findid.FindIdCommand");
			Arrays.asList(cls.getDeclaredMethods()).forEach(method -> {
				FindIdMethod anno = method.getDeclaredAnnotation(FindIdMethod.class);
				if (anno == null) return;
				map.put(anno.value(), method);
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
