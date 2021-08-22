package com.backinfile.dSync.tmp;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.backinfile.dSync.model.DSyncBaseHandler;
import com.backinfile.dSync.model.DSyncException;
import com.backinfile.dSync.model.Mode;

public class Handler extends DSyncBaseHandler {
	private Board root;

	public Handler(Mode mode) {
		super(mode);
		root = new Board();
		root.init();
		put(root);
		root.sync();
	}

	public Board getRoot() {
		return root;
	}

	@Override
	protected DSyncBase newDSyncInstance(String typeName) {
		switch (typeName) {
		case Human.TypeName:
			return new Human();
		case CardPile.TypeName:
			return new CardPile();
		case Board.TypeName:
			return new Board();
		case Card.TypeName:
			return new Card();
		default:
			return null;
		}
	}

	public static class Human extends DSyncBase {
		public static final String TypeName = "Human";
		
		private long id;
		private String name;
		private CardPile handPile;
		private List<String> cards;

		public static class K {
			public static final String id = "id";
			public static final String name = "name";
			public static final String handPile = "handPile";
			public static final String cards = "cards";
		}

		private Human() {
		}

		public static Human newInstance(Handler _handler) {
			if (_handler.mode == Mode.Client) {
				throw new DSyncException("Client模式下，不能创建DSync数据对象");
			}
			Human _struct = new Human();
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
			handPile = (CardPile) handler.get(jsonObject.getLongValue(K.handPile));
			cards = JSONObject.parseArray(jsonObject.getString(K.cards), String.class);
		}
	}
	public static class CardPile extends DSyncBase {
		public static final String TypeName = "CardPile";
		
		private List<Card> cards;

		public static class K {
			public static final String cards = "cards";
		}

		private CardPile() {
		}

		public static CardPile newInstance(Handler _handler) {
			if (_handler.mode == Mode.Client) {
				throw new DSyncException("Client模式下，不能创建DSync数据对象");
			}
			CardPile _struct = new CardPile();
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
	public static class Board extends DSyncBase {
		public static final String TypeName = "Board";
		
		private List<Human> humans;

		public static class K {
			public static final String humans = "humans";
		}

		private Board() {
		}

		public static Board newInstance(Handler _handler) {
			if (_handler.mode == Mode.Client) {
				throw new DSyncException("Client模式下，不能创建DSync数据对象");
			}
			Board _struct = new Board();
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
	public static class Card extends DSyncBase {
		public static final String TypeName = "Card";
		
		private long id;

		public static class K {
			public static final String id = "id";
		}

		private Card() {
		}

		public static Card newInstance(Handler _handler) {
			if (_handler.mode == Mode.Client) {
				throw new DSyncException("Client模式下，不能创建DSync数据对象");
			}
			Card _struct = new Card();
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
}

