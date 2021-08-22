package com.backinfile.dSync.parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.backinfile.dSync.parser.Token.TokenType;

public class TokenWorker {
	private int lineno = 0;
	private int lineIndex = 0;
	private List<String> content;
	private List<Token> tokenCollection = new ArrayList<>();
	private List<Token> tokenCollectionCache = new ArrayList<>();
	private Result result = new Result();

	private TokenWorker(List<String> content) {
		this.content = content;
	}

	public static class Result {
		public boolean hasError = false;
		public String errorStr = "";
		public int errorLineno = -1;
		public List<Token> tokens = new ArrayList<>();
	}

	public static Result getTokens(List<String> allLine) {
		TokenWorker tokenWorker = new TokenWorker(allLine);
		tokenWorker.parse();
		return tokenWorker.result;
	}

	public static Result getTokens(String content) {
		String[] split = content.split("\n");
		return getTokens(Arrays.asList(split));
	}

	private void parse() {
		lineno = 0;
		while (lineno < content.size()) {
			lineno++;
			parseLine();
			if (result.hasError) {
				return;
			}
		}
		result.tokens.addAll(tokenCollection);
	}

	private static enum ParseMode {
		None, Name, Comment
	}

	private void parseLine() {
		clearTokenCache();
		lineIndex = -1;
		String str = content.get(lineno - 1);
		ParseMode mode = ParseMode.None;
		int modeStartIndex = -1;
		while (lineIndex < str.length() - 1) {
			lineIndex++;
			char character = str.charAt(lineIndex);
			switch (mode) {
			case None: {
				if (character == ' ' || character == '\t') {
					// pass
				} else if (Character.isLetter(character)) {
					mode = ParseMode.Name;
					modeStartIndex = lineIndex;
				} else if (character == '/') {
					mode = ParseMode.Comment;
					modeStartIndex = lineIndex;
					break;
				} else if (character == '[') {
					pushToken(TokenType.LSquareBracket);
				} else if (character == ']') {
					pushToken(TokenType.RSquareBracket);
				} else if (character == '{') {
					pushToken(TokenType.LBrace);
				} else if (character == '}') {
					pushToken(TokenType.RBrace);
				} else if (character == ';') {
					pushToken(TokenType.Semicolon);
				}
				break;
			}
			case Name: {
				if (Character.isLetterOrDigit(character)) {
					// pass
				} else {
					pushToken(TokenType.Name, str.substring(modeStartIndex, lineIndex));
					lineIndex--;
					mode = ParseMode.None;
					modeStartIndex = -1;
				}
				break;
			}
			default:
				break;
			}
		}
		if (mode == ParseMode.Comment) {
			int startIndex = modeStartIndex + 2;
			int endIndex = str.length();
			if (endIndex > startIndex) {
				String comment = str.substring(startIndex, endIndex).trim();
				if (!comment.isEmpty()) {
					pushToken(TokenType.Comment, comment);
				}
			}
		}
		flushToken();
	}

	private void pushToken(TokenType type) {
		pushToken(type, "");
	}

	private void pushToken(TokenType type, String value) {
		Token token = new Token();
		token.type = type;
		token.lineno = lineno;
		token.value = value;
		tokenCollectionCache.add(token);
	}

	private void flushToken() {
		tokenCollection.addAll(tokenCollectionCache);
	}

	private void clearTokenCache() {
		tokenCollectionCache.clear();
	}

	public static void main(String[] args) throws IOException {
		String resourcePath = TokenWorker.class.getClassLoader().getResource("demo.ds").getPath();
		Path path = Paths.get(resourcePath.substring(1));
		List<String> lines = Files.readAllLines(path);
		var tokenResult = TokenWorker.getTokens(lines);
		var result = SyntaxWorker.parse(tokenResult.tokens);
		System.out.println();
	}

}
