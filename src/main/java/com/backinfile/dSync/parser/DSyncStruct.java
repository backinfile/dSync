package com.backinfile.dSync.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

public class DSyncStruct {
	private DSyncStructType type;
	private String typeName;
	private List<DSyncVariable> children = new ArrayList<>();
	private List<String> comments = new ArrayList<>();

	public DSyncStruct(DSyncStructType type) {
		this.type = type;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeName() {
		return typeName;
	}

	public DSyncStructType getType() {
		return type;
	}

	public void addVariable(DSyncVariable variable) {
		children.add(variable);
	}

	public List<DSyncVariable> getChildren() {
		return children;
	}

	public void addComments(List<String> comments) {
		this.comments.addAll(comments);
	}

	public List<String> getComments() {
		return comments;
	}

	public static enum DSyncStructType {
		Int("Int", "int", "int32", "Integer"), // int
		Long("Long", "long", "int64"), // long
		String("String", "string", "str"), // string
		Boolen("Boolean", "boolean", "bool"), // boolean
		UserDefine;

		private List<String> names = new ArrayList<>();

		private DSyncStructType(String... names) {
			this.names.addAll(Arrays.asList(names));
		}

		public static DSyncStructType match(String matchName) {
			for (var type : DSyncStructType.values()) {
				for (var typeName : type.names) {
					if (typeName.equals(matchName)) {
						return type;
					}
				}
			}
			return UserDefine;
		}
	}

	public static class DSyncVariable {
		public String name;
		public DSyncStructType type;
		public String typeName;
		public boolean isArray = false;
		public String comment;

		public DSyncVariable() {
		}

		public DSyncVariable(String name, DSyncStructType type, boolean isArray) {
			this.name = name;
			this.type = type;
			this.isArray = isArray;
		}

		public void setTypeName(String typeName) {
			this.typeName = typeName;
		}

		public String getTypeName() {
			String typeName = "";
			switch (type) {
			case Boolen:
				typeName = "boolean";
				break;
			case Int:
				typeName = "int";
				break;
			case Long:
				typeName = "long";
				break;
			case String:
				typeName = "String";
				break;
			case UserDefine:
				typeName = this.typeName;
				break;
			default:
				break;
			}
			if (isArray) {
				return "List<" + typeName + ">";
			}
			return typeName;
		}

		public String getLargeTypeName() {
			String typeName = "";
			switch (type) {
			case Boolen:
				typeName = "Boolean";
				break;
			case Int:
				typeName = "Integer";
				break;
			case Long:
				typeName = "Long";
				break;
			case String:
				typeName = "String";
				break;
			case UserDefine:
				typeName = this.typeName;
				break;
			default:
				break;
			}
			return typeName;
		}

		public String getLongTypeName() {
			String typeName = "";
			switch (type) {
			case Boolen:
				typeName = "BooleanValue";
				break;
			case Int:
				typeName = "IntegerValue";
				break;
			case Long:
				typeName = "LongValue";
				break;
			case String:
				typeName = "String";
				break;
			case UserDefine:
				typeName = this.typeName;
				break;
			default:
				break;
			}
			return typeName;
		}

		public String getDefaultValue() {
			if (isArray) {
				return "new ArrayList<>()";
			}
			switch (type) {
			case Boolen:
				return "false";
			case Int:
				return "0";
			case Long:
				return "0";
			case String:
				return "\"\"";
			case UserDefine:
				return "null";
			default:
				break;
			}
			return "null";
		}
	}

}
