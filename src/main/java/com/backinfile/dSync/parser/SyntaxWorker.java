package com.backinfile.dSync.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.backinfile.dSync.parser.DSyncStruct.DSyncStructType;
import com.backinfile.dSync.parser.DSyncStruct.DSyncVariable;
import com.backinfile.dSync.parser.Token.TokenType;

public class SyntaxWorker {
	private Map<String, DSyncStruct> userDefineStructMap = new HashMap<String, DSyncStruct>();
	private List<Token> tokens = new ArrayList<Token>();
	private static final String DS_ROOT = "root";
	private static final String DS_STRUCT = "struct";
	private DSyncStruct rootStruct = null;
	private List<String> lastComments = new ArrayList<>();

	public static class Result {
		public boolean hasError = false;
		public String errorStr = "";
		public Map<String, DSyncStruct> userDefineStructMap = new HashMap<String, DSyncStruct>();
		public DSyncStruct rootStruct = null;
	}

	private int index = 0;

	private SyntaxWorker() {
	}

	public static Result parse(List<Token> tokens) {
		Result result = new Result();
		try {
			SyntaxWorker worker = new SyntaxWorker();
			worker.tokens.addAll(tokens);
			worker.parseRoot();
			result.rootStruct = worker.rootStruct;
			result.userDefineStructMap.putAll(worker.getUserDefineStructMap());
		} catch (Exception e) {
			result.hasError = true;
			result.errorStr = e.getMessage();
		}
		return result;

	}

	public DSyncStruct getRootStruct() {
		return rootStruct;
	}

	public Map<String, DSyncStruct> getUserDefineStructMap() {
		return userDefineStructMap;
	}

	private void parseRoot() {
		// 第一遍，找到所有自定义struct
		index = 0;
		while (index < tokens.size()) {
			if (test(TokenType.Comment)) {
				var token = match(TokenType.Comment);
				lastComments.add(token.value);
			}
			if (test(TokenType.Name, DS_ROOT)) {
				next();
				parseStruct(true);
			} else {
				parseStruct(false);
			}
		}
	}

	private void parseStruct(boolean root) {
		match(TokenType.Name, DS_STRUCT);
		var nameToken = match(TokenType.Name);
		String typeName = nameToken.value;
		match(TokenType.LBrace);
		var struct = new DSyncStruct(DSyncStructType.UserDefine);
		struct.setTypeName(typeName);
		struct.addComments(lastComments);
		lastComments.clear();

		while (!test(TokenType.RBrace)) {
			parseFiled(struct);
		}
		match(TokenType.RBrace);
		userDefineStructMap.put(typeName, struct);

		if (root) {
			rootStruct = struct;
		}
	}

	private void parseFiled(DSyncStruct struct) {
		boolean isArray = false;
		var typeToken = match(TokenType.Name);
		var varType = DSyncStructType.match(typeToken.value);
		if (test(TokenType.LSquareBracket)) {
			match(TokenType.LSquareBracket);
			match(TokenType.RSquareBracket);
			isArray = true;
		}
		var nameToken = match(TokenType.Name);
		var variable = new DSyncVariable(nameToken.value, varType, isArray);
		if (varType == DSyncStructType.UserDefine) {
			variable.setTypeName(typeToken.value);
		}
		struct.addVariable(variable);
		var semToken = match(TokenType.Semicolon);

		if (test(TokenType.Comment)) {
			var commentToken = getToken();
			if (semToken.lineno == commentToken.lineno) {
				variable.comment = commentToken.value;
				next();
			}
		}
	}

	private Token getToken() {
		if (index >= tokens.size()) {
			var token = tokens.get(tokens.size() - 1);
			throw new ParserError("语法错误  第" + token.lineno + "行。");
		}
		return tokens.get(index);
	}

	private void next() {
		index++;
	}

	private boolean test(TokenType tokenType) {
		if (index >= tokens.size()) {
			return false;
		}
		return getToken().type == tokenType;
	}

	private boolean test(TokenType tokenType, String name) {
		if (index >= tokens.size()) {
			return false;
		}
		Token token = getToken();
		return token.type == tokenType && token.value.equals(name);
	}

	private Token match(TokenType tokenType) {
		if (index >= tokens.size()) {
			var token = tokens.get(tokens.size() - 1);
			throw new ParserError("语法错误 不能匹配" + tokenType.name() + " 第" + token.lineno + "行。");
		}
		var token = tokens.get(index);
		if (token.type != tokenType) {
			throw new ParserError("语法错误 不能匹配" + tokenType.name() + " 第" + token.lineno + "行。");
		}
		index++;
		return token;
	}

	private Token match(TokenType tokenType, String name) {
		if (index >= tokens.size()) {
			var token = tokens.get(tokens.size() - 1);
			throw new ParserError("语法错误 不能匹配" + tokenType.name() + " 第" + token.lineno + "行。");
		}
		var token = tokens.get(index);
		if (token.type != tokenType || !token.value.equals(name)) {
			throw new ParserError("语法错误 不能匹配" + tokenType.name() + " 第" + token.lineno + "行。");
		}
		index++;
		return token;
	}

}
