package com.backinfile.dSync.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.backinfile.dSync.Log;

@SuppressWarnings({ "unchecked" })
public class DSyncBaseHandler {
	protected long idMax = 0;
	protected Map<Long, DSyncBase> dSyncObjs = new HashMap<>();
	private List<String> changeLogs = new ArrayList<>();
	protected Mode mode;

	public DSyncBaseHandler(Mode mode) {
		this.mode = mode;
	}

	protected final void put(DSyncBase base) {
		put(idMax++, base);
	}

	protected final void put(long id, DSyncBase base) {
		base.handler = this;
		base._dSync_id = id;
		dSyncObjs.put(base._dSync_id, base);
	}

	public final DSyncBase get(long id) {
		return dSyncObjs.get(id);
	}

	public final void release(DSyncBase base) {
		dSyncObjs.remove(base._dSync_id);
	}

	public final void receiveChangeLog(String logString) {
		if (mode != Mode.Client) {
			throw new DSyncException("非Client模式，不能接受变更日志");
		}
		var logs = JSONObject.parseArray(logString, String.class);
		// 预先创建需要的对象
		for (var log : logs) {
			var json = JSONObject.parseObject(log);
			var id = json.getLongValue(DSyncBase.K._dSync_id);
			if (get(id) == null) { // 是新创建的对象
				String typeName = json.getString(DSyncBase.K.TypeName);
				var base = newDSyncInstance(typeName);
				if (base == null) {
					Log.Runtime.error("不能创建对象:{}", typeName);
				} else {
					put(id, base);
				}
			}
			// 更新对象
			DSyncBase base = get(id);
			if (base == null) {
				Log.Runtime.error("找不到对象:{} log:{}", id, log);
			} else {
				base.applyRecord(json);
			}
			onReceiveChangeLog(id);
		}
	}

	protected void onReceiveChangeLog(long id) {

	}

	public final String getChangeLog() {
		if (mode != Mode.Server) {
			throw new DSyncException("非Server模式，不能获取变更日志");
		}
		String jsonString = JSONObject.toJSONString(changeLogs);
		changeLogs.clear();
		return jsonString;
	}

	public final String getInitLog() {
		var logs = new HashMap<Long, String>();
		for (var obj : dSyncObjs.values()) {
			logs.put(obj._dSync_id, obj.getLog());
		}
		return JSONObject.toJSONString(logs.values());
	}

	protected DSyncBase newDSyncInstance(String typeName) {
		return null;
	}

	public static abstract class DSyncBase {
		protected DSyncBaseHandler handler;
		protected long _dSync_id;

		public static class K {
			public static final String _dSync_id = "_dSync_id";
			public static final String TypeName = "_TypeName";
		}

		protected final <T extends DSyncBase> String toJSONString(List<T> values) {
			List<Long> ids = new ArrayList<>();
			for (var value : values) {
				ids.add(((DSyncBase) value)._dSync_id);
			}
			return JSON.toJSONString(ids);
		}

		protected final <T extends DSyncBase> List<T> fromJSONString(String str) {
			var list = new ArrayList<T>();
			for (var id : JSON.parseArray(str, Long.class)) {
				list.add((T) handler.get(id));
			}
			return list;
		}

		protected void init() {

		}

		protected void getRecord(JSONObject jsonObject) {

		}

		protected void applyRecord(JSONObject jsonObject) {

		}

		protected final void onChanged() {
			if (handler.mode == Mode.Server) {
				handler.changeLogs.add(getLog());
			}
		}

		protected final String getLog() {
			var json = new JSONObject();
			json.put(K._dSync_id, _dSync_id);
			getRecord(json);
			return json.toJSONString();
		}

		public final void sync() {
			onChanged();
		}

		public long get_dSync_id() {
			return _dSync_id;
		}
	}

}
