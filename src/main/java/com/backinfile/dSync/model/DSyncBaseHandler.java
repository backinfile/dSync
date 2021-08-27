package com.backinfile.dSync.model;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public abstract class DSyncBaseHandler {

	public static abstract class DSyncBase {
		public static class K {
			public static final String TypeName = "_TypeName";
		}

		protected void init() {
		}

		public final <T extends DSyncBase> List<T> fromJSONString(String string) {
			return null;
		}

		protected void getRecord(JSONObject jsonObject) {
		}

		protected void applyRecord(JSONObject jsonObject) {
		}

		public static final <T extends DSyncBase> JSONArray getJSONArray(List<T> objs) {
			var array = new JSONArray();
			for (var obj : objs) {
				var jsonObj = new JSONObject();
				if (obj != null) {
					obj.getRecord(jsonObj);
				}
				array.add(jsonObj);
			}
			return array;
		}

		public static final JSONObject getJSONObject(DSyncBase base) {
			var jsonObj = new JSONObject();
			if (base != null) {
				base.getRecord(jsonObj);
			}
			return jsonObj;
		}

		public final String toMessage() {
			var json = new JSONObject();
			getRecord(json);
			return json.toJSONString();
		}

		public String getTypeName() {
			return "_Base";
		}
	}

}
