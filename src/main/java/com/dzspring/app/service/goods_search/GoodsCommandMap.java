package com.dzspring.app.service.goods_search;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.dzspring.app.service.Command;

public enum GoodsCommandMap {
	MAP(new ConcurrentHashMap<>());
	
	private Map<String, Method> map;
	
	GoodsCommandMap(Map<String, Method> map) {
		try {
			Class<?> cls = Class.forName("com.dzspring.app.service.goods_search.GoodsCommand");
			Arrays.asList(cls.getDeclaredMethods()).forEach(method -> {
				Command command = method.getDeclaredAnnotation(Command.class);
				if (command == null) return;
				map.put(command.value(), method);
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
