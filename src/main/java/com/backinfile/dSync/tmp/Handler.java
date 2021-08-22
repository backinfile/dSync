package com.backinfile.dSync.tmp;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.backinfile.dSync.model.DSyncBaseHandler;
import com.backinfile.dSync.model.DSyncException;
import com.backinfile.dSync.model.Mode;

public class Handler extends DSyncBaseHandler {
	private DBoard root;

	public Handler(Mode mode) {
		super(mode);
		root = new DBoard();
		root.init();
		put(root);
		root.sync();
	}

	public DBoard getRoot() {
		return root;
	}

	@Override
	protected DSyncBase newDSyncInstance(String typeName) {
		switch (typeName) {
		case DCard.TypeName:
			return new DCard();
		case DBoard.TypeName:
			return new DBoard();
		case DCardPile.TypeName:
			return new DCardPile();
		case DHuman.TypeName:
			return new DHuman();
		default:
			return null;
		}
	}

	public static class DCard extends DSyncBase {
		public static final String TypeName = "DCard";
		
		private long id;

		public static class K {
			public static final String id = "id";
		}

		private DCard() {
		}

		public static DCard newInstance(Handler _handler) {
			if (_handler.mode == Mode.Client) {
				throw new DSyncException("Client模式下，不能创建DSync数据对象");
			}
			DCard _struct = new DCard();
			_struct.init();
			if (_handler.mode == Mode.Server) {
				_handler.put(_struct);
			}
			return _struct;
		}

		@Override
		protected void init() {
			id = 0;
		}

		@Override
		protected void getRecord(JSONObject jsonObject) {
			jsonObject.put(DSyncBase.K.TypeName, TypeName);
			jsonObject.put(K.id, id);
		}

		@Override
		protected void applyRecord(JSONObject jsonObject) {
			id = jsonObject.getLongValue(K.id);
		}
	}
	
	public static class DBoard extends DSyncBase {
		public static final String TypeName = "DBoard";
		
		private List<DHuman> humans;

		public static class K {
			public static final String humans = "humans";
		}

		private DBoard() {
		}

		public static DBoard newInstance(Handler _handler) {
			if (_handler.mode == Mode.Client) {
				throw new DSyncException("Client模式下，不能创建DSync数据对象");
			}
			DBoard _struct = new DBoard();
			_struct.init();
			if (_handler.mode == Mode.Server) {
				_handler.put(_struct);
			}
			return _struct;
		}

		@Override
		protected void init() {
			humans = new ArrayList<>();
		}

		@Override
		protected void getRecord(JSONObject jsonObject) {
			jsonObject.put(DSyncBase.K.TypeName, TypeName);
			jsonObject.put(K.humans, toJSONString(humans));
		}

		@Override
		protected void applyRecord(JSONObject jsonObject) {
			humans = fromJSONString(jsonObject.getString(K.humans));
		}
	}
	
	public static class DCardPile extends DSyncBase {
		public static final String TypeName = "DCardPile";
		
		private List<DCard> cards;

		public static class K {
			public static final String cards = "cards";
		}

		private DCardPile() {
		}

		public static DCardPile newInstance(Handler _handler) {
			if (_handler.mode == Mode.Client) {
				throw new DSyncException("Client模式下，不能创建DSync数据对象");
			}
			DCardPile _struct = new DCardPile();
			_struct.init();
			if (_handler.mode == Mode.Server) {
				_handler.put(_struct);
			}
			return _struct;
		}

		@Override
		protected void init() {
			cards = new ArrayList<>();
		}

		@Override
		protected void getRecord(JSONObject jsonObject) {
			jsonObject.put(DSyncBase.K.TypeName, TypeName);
			jsonObject.put(K.cards, toJSONString(cards));
		}

		@Override
		protected void applyRecord(JSONObject jsonObject) {
			cards = fromJSONString(jsonObject.getString(K.cards));
		}
	}
	
	/**
	 * 玩家信息
	 */
	public static class DHuman extends DSyncBase {
		public static final String TypeName = "DHuman";
		
		private long id;
		private String name;
		/** 手牌 */
		private DCardPile handPile;
		private List<String> cards;

		public static class K {
			public static final String id = "id";
			public static final String name = "name";
			public static final String handPile = "handPile";
			public static final String cards = "cards";
		}

		private DHuman() {
		}

		public static DHuman newInstance(Handler _handler) {
			if (_handler.mode == Mode.Client) {
				throw new DSyncException("Client模式下，不能创建DSync数据对象");
			}
			DHuman _struct = new DHuman();
			_struct.init();
			if (_handler.mode == Mode.Server) {
				_handler.put(_struct);
			}
			return _struct;
		}

		@Override
		protected void init() {
			id = 0;
			name = "";
			handPile = null;
			cards = new ArrayList<>();
		}

		@Override
		protected void getRecord(JSONObject jsonObject) {
			jsonObject.put(DSyncBase.K.TypeName, TypeName);
			jsonObject.put(K.id, id);
			jsonObject.put(K.name, name);
			jsonObject.put(K.handPile, handPile.get_dSync_id());
			jsonObject.put(K.cards, JSONObject.toJSONString(cards));
		}

		@Override
		protected void applyRecord(JSONObject jsonObject) {
			id = jsonObject.getLongValue(K.id);
			name = jsonObject.getString(K.name);
			handPile = (DCardPile) handler.get(jsonObject.getLongValue(K.handPile));
			cards = JSONObject.parseArray(jsonObject.getString(K.cards), String.class);
		}
	}
	
}

