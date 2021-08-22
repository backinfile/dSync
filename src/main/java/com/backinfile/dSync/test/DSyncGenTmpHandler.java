package com.backinfile.dSync.test;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.backinfile.dSync.model.DSyncBaseHandler;
import com.backinfile.dSync.model.DSyncException;
import com.backinfile.dSync.model.Mode;

public class DSyncGenTmpHandler extends DSyncBaseHandler {
	private Board root;

	public DSyncGenTmpHandler(Mode mode) {
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
		case Board.TypeName:
			return new Board();
		case Human.TypeName:
			return new Human();
		default:
			return null;
		}
	}

	public static class Board extends DSyncBase {
		public static final String TypeName = "Board";
		private long id;
		private String name;
		private List<Human> humans;

		public static class K {
			public static final String id = "id";
			public static final String name = "name";
			public static final String humans = "humans";
		}

		private Board() {
		}

		public static Board newInstance(DSyncGenTmpHandler handler) {
			if (handler.mode == Mode.Client) {
				throw new DSyncException("Client模式下，不能创建DSync数据对象");
			}
			Board board = new Board();
			board.init();
			if (handler.mode == Mode.Server) {
				handler.put(board);
			}
			return board;
		}

		@Override
		protected void init() {
			id = 0;
			name = "";
			humans = new ArrayList<>();
		}

		@Override
		protected void getRecord(JSONObject jsonObject) {
			jsonObject.put(DSyncBase.K.TypeName, TypeName);
			jsonObject.put(K.id, id);
			jsonObject.put(K.name, name);
			jsonObject.put(K.humans, toJSONString(humans));
		}

		@Override
		protected void applyRecord(JSONObject jsonObject) {
			id = jsonObject.getLongValue(K.id);
			name = jsonObject.getString(K.name);
			humans = fromJSONString(jsonObject.getString(K.humans));
		}

		public void setId(long id) {
			this.id = id;
			onChanged();
		}

		public long getId() {
			return id;
		}

		public void setName(String name) {
			this.name = name;
			onChanged();
		}

		public String getName() {
			return name;
		}

		public void addHumans(Human human) {
			this.humans.add(human);
			onChanged();
		}

		public void addAllHumans(Iterable<Human> humans) {
			for (var _ele : humans) {
				this.humans.add(_ele);
			}
			onChanged();
		}

		public void removeHumans(Human human) {
			this.humans.remove(human);
			onChanged();
		}

		public void clearHumans() {
			this.humans.clear();
			onChanged();
		}

		public boolean containsHumans(Human human) {
			return this.humans.contains(human);
		}

		public List<Human> getAllHumans() {
			return new ArrayList<>(humans);
		}
	}

	public static class Human extends DSyncBase {
		public static final String TypeName = "Human";
		private String name;
		private List<String> cards;

		public static class K {
			public static final String name = "name";
		}

		private Human() {
		}

		public static Human newInstance(DSyncGenTmpHandler dSyncGenTmp) {
			Human human = new Human();
			human.init();
			dSyncGenTmp.put(human);
			human.onChanged();
			return human;
		}

		@Override
		protected void init() {
			this.name = "";
			this.cards = new ArrayList<>();
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
			onChanged();
		}

		public List<String> getAllCards() {
			return new ArrayList<>(cards);
		}

		public void addCards(String cards) {
			this.cards.add(cards);
			onChanged();
		}

		public boolean containsCards(String cards) {
			return this.cards.contains(cards);
		}

		@Override
		protected void getRecord(JSONObject jsonObject) {
			jsonObject.put(DSyncBase.K.TypeName, TypeName);
			jsonObject.put(K.name, name);
		}

		@Override
		protected void applyRecord(JSONObject jsonObject) {
			name = jsonObject.getString(K.name);
		}
	}
}
