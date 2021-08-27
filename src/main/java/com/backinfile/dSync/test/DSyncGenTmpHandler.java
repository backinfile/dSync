package com.backinfile.dSync.test;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.backinfile.dSync.model.DSyncBaseHandler;

public class DSyncGenTmpHandler extends DSyncBaseHandler {
	private Board root;

	public Board getRoot() {
		return root;
	}

	protected static DSyncBase newDSyncInstance(String typeName) {
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
			jsonObject.put(K.humans, getJSONArray(humans));
		}

		@Override
		protected void applyRecord(JSONObject jsonObject) {
			id = jsonObject.getLongValue(K.id);
			name = jsonObject.getString(K.name);
			humans = fromJSONString(jsonObject.getString(K.humans));
		}

		public void setId(long id) {
			this.id = id;

		}

		public long getId() {
			return id;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void addHumans(Human human) {
			this.humans.add(human);
		}

		public void addAllHumans(Iterable<Human> humans) {
			for (var _ele : humans) {
				this.humans.add(_ele);
			}
		}

		public void removeHumans(Human human) {
			this.humans.remove(human);
		}

		public void clearHumans() {
			this.humans.clear();
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
		}

		public List<String> getAllCards() {
			return new ArrayList<>(cards);
		}

		public void addCards(String cards) {
			this.cards.add(cards);
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
