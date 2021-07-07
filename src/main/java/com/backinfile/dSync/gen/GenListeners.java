package com.backinfile.dSync.gen;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GenListeners extends GenBase {
	public GenListeners() {
		super();
		setTemplateFileName("ListenerBinder.ftl");
		setTargetPackage("com.backinfile.gen.proxy");
		setFileName("ListenerBinder.java");
	}

	@Override
	public int genFile() {
		List<Map<String, Object>> functions = new ArrayList<>();
		Set<String> imports = new HashSet<>();
//		for (var clazz : ReflectionUtils.getClasses()) {
//			for (var method : clazz.getMethods()) {
//				var listener = method.getAnnotation(Listener.class);
//				if (listener == null)
//					continue;
//				for (var key : listener.value()) {
//					Map<String, Object> func = new HashMap<>();
//					func.put("eventKey", String.valueOf(key));
//					func.put("func", clazz.getSimpleName() + "::" + method.getName());
//					imports.add(getFullPackageName(clazz));
//					functions.add(func);
//				}
//			}
//		}

		rootMap.put("imports", imports);
		rootMap.put("functions", functions);
		return super.genFile();
	}

	private String getFullPackageName(Class<?> clazz) {
		if (clazz.isPrimitive()) {
			return "";
		}
		if (clazz.isArray()) {
			return getFullPackageName(clazz.getComponentType());
		}
		return clazz.getPackageName() + "." + clazz.getSimpleName();
	}
}
