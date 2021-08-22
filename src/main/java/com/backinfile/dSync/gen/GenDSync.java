package com.backinfile.dSync.gen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.backinfile.dSync.parser.SyntaxWorker;
import com.backinfile.dSync.parser.SyntaxWorker.Result;
import com.backinfile.dSync.parser.TokenWorker;
import com.backinfile.dSync.parser.DSyncStruct.DSyncStructType;

public class GenDSync extends GenBase {
	private String dsSource;

	public GenDSync() {
		super();
//		setTemplateFileName("proxy.ftl");
//		setTargetPackage("com.backinfile.dSync.tmp");
//		setFileName("Handler.java");
	}

	public void setDsSource(String dsSource) {
		this.dsSource = dsSource;
	}

	@Override
	public int genFile() {
		var result = getResult();
		var structs = new ArrayList<Map<String, Object>>();

		rootMap.put("packagePath", targetPackagePathHead);
		rootMap.put("handlerClassName", className);
		rootMap.put("rootClassName", result.rootStruct.getTypeName());
		rootMap.put("structs", structs);
		for (var struct : result.userDefineStructMap.values()) {
			var structMap = new HashMap<String, Object>();
			structs.add(structMap);
			var fields = new ArrayList<Map<String, Object>>();
			structMap.put("className", struct.getTypeName());
			structMap.put("defaultValue", struct.getTypeName());
			structMap.put("fields", fields);
			structMap.put("comments", struct.getComments());
			structMap.put("hasComment", !struct.getComments().isEmpty());
			for (var field : struct.getChildren()) {
				var fieldMap = new HashMap<String, Object>();
				fields.add(fieldMap);
				fieldMap.put("typeName", field.getTypeName());
				fieldMap.put("name", field.name);
				fieldMap.put("array", field.isArray);
				fieldMap.put("baseType", field.type != DSyncStructType.UserDefine);
				fieldMap.put("largeTypeName", field.getLargeTypeName());
				fieldMap.put("longTypeName", field.getLongTypeName());
				fieldMap.put("defaultValue", field.getDefaultValue());
				fieldMap.put("hasComment", !field.comment.isEmpty());
				fieldMap.put("comment", field.comment);
			}
		}

		return super.genFile();
	}

	private Result getResult() {
		var tokenResult = TokenWorker.getTokens(dsSource);
		var result = SyntaxWorker.parse(tokenResult.tokens);
		return result;
	}

	public static void main(String[] args) {
		GenDSync gen = new GenDSync();
		gen.genFile();
	}
}
