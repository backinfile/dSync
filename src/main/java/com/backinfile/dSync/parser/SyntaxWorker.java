package com.backinfile.dSync.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SyntaxWorker {
	private Map<String, DSyncStruct> userDefineStructMap = new HashMap<String, DSyncStruct>();
	private List<List<Token>> tokensList = new ArrayList<List<Token>>();

	public SyntaxWorker() {
	}

	public void addTokens(List<Token> tokens) {
		if (!tokens.isEmpty()) {
			tokensList.add(tokens);
		}
	}

	public DSyncPath getRootPath() {
		return null;
	}
}
