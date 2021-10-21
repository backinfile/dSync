package com.backinfile.dSync.tmp;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.backinfile.dSync.model.DSyncBaseHandler;

public class Handler extends DSyncBaseHandler {
	private List<DSyncListener> listeners = new ArrayList<>();

	public void addListener(DSyncListener listener) {
		listeners.add(listener);
	}
	
	public void removeListener(DSyncListener listener) {
		listeners.remove(listener);
	}
	
	public static abstract class DSyncListener {
		public void onMessage(SCGameStart data) {
		}
		
		public void onMessage(DCard data) {
		}
		
		public void onMessage(DBoard data) {
		}
		
		public void onMessage(DCardPile data) {
		}
		
		public void onMessage(DHuman data) {
		}
		
	}
	
	public void onMessage(String string) {
		var jsonObject = JSONObject.parseObject(string);
		String typeName = jsonObject.getString(DSyncBase.K.TypeName);
		switch (typeName) {
		case SCGameStart.TypeName:
			for (var listener : listeners) {
				listener.onMessage(SCGameStart.parseJSONObject(jsonObject));
			}
			break;
		case DCard.TypeName:
			for (var listener : listeners) {
				listener.onMessage(DCard.parseJSONObject(jsonObject));
			}
			break;
		case DBoard.TypeName:
			for (var listener : listeners) {
				listener.onMessage(DBoard.parseJSONObject(jsonObject));
			}
			break;
		case DCardPile.TypeName:
			for (var listener : listeners) {
				listener.onMessage(DCardPile.parseJSONObject(jsonObject));
			}
			break;
		case DHuman.TypeName:
			for (var listener : listeners) {
				listener.onMessage(DHuman.parseJSONObject(jsonObject));
			}
			break;
		}
	}

	protected static DSyncBase newDSyncInstance(String typeName) {
		switch (typeName) {
		case SCGameStart.TypeName:
			return new SCGameStart();
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

	public static class SCGameStart extends DSyncBase {
		public static final String TypeName = "SCGameStart";
		
		private long seed;

		public static class K {
			public static final String seed = "seed";
		}

		public SCGameStart() {
			init();
		}

		@Override
		protected void init() {
			seed = 0;
		}
		
		public long getSeed() {
			return seed;
		}
		
		public void setSeed(long seed) {
			this.seed = seed;
		}
		

		static SCGameStart parseJSONObject(JSONObject jsonObject) {
			var _value = new SCGameStart();
			if (!jsonObject.isEmpty()) {
				_value.applyRecord(jsonObject);
			}
			return _value;
		}
		
		static List<SCGameStart> parseJSONArray(JSONArray jsonArray) {
			var list = new ArrayList<SCGameStart>();
			for (int i = 0; i < jsonArray.size(); i++) {
				var _value = new SCGameStart();
				var jsonObject = jsonArray.getJSONObject(i);
				if (!jsonObject.isEmpty()) {
					_value.applyRecord(jsonObject);
				}
				list.add(_value);
			}
			return list;
		}

		@Override
		public void getRecord(JSONObject jsonObject) {
			jsonObject.put(DSyncBase.K.TypeName, TypeName);
			jsonObject.put(K.seed, seed);
		}

		@Override
		public void applyRecord(JSONObject jsonObject) {
			seed = jsonObject.getLongValue(K.seed);
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (!(obj instanceof SCGameStart)) {
				return false;
			}
			var _value = (SCGameStart) obj;
			if (this.seed != _value.seed) {
				return false;
			}
			return true;
		}
		
		public SCGameStart copy() {
			var _value = new SCGameStart();
			_value.seed = this.seed;
			return _value;
		}
		
		public SCGameStart deepCopy() {
			var _value = new SCGameStart();
			_value.seed = this.seed;
			return _value;
		}
	}
	
	public static class DCard extends DSyncBase {
		public static final String TypeName = "DCard";
		
		private long id;

		public static class K {
			public static final String id = "id";
		}

		public DCard() {
			init();
		}

		@Override
		protected void init() {
			id = 0;
		}
		
		public long getId() {
			return id;
		}
		
		public void setId(long id) {
			this.id = id;
		}
		

		static DCard parseJSONObject(JSONObject jsonObject) {
			var _value = new DCard();
			if (!jsonObject.isEmpty()) {
				_value.applyRecord(jsonObject);
			}
			return _value;
		}
		
		static List<DCard> parseJSONArray(JSONArray jsonArray) {
			var list = new ArrayList<DCard>();
			for (int i = 0; i < jsonArray.size(); i++) {
				var _value = new DCard();
				var jsonObject = jsonArray.getJSONObject(i);
				if (!jsonObject.isEmpty()) {
					_value.applyRecord(jsonObject);
				}
				list.add(_value);
			}
			return list;
		}

		@Override
		public void getRecord(JSONObject jsonObject) {
			jsonObject.put(DSyncBase.K.TypeName, TypeName);
			jsonObject.put(K.id, id);
		}

		@Override
		public void applyRecord(JSONObject jsonObject) {
			id = jsonObject.getLongValue(K.id);
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (!(obj instanceof DCard)) {
				return false;
			}
			var _value = (DCard) obj;
			if (this.id != _value.id) {
				return false;
			}
			return true;
		}
		
		public DCard copy() {
			var _value = new DCard();
			_value.id = this.id;
			return _value;
		}
		
		public DCard deepCopy() {
			var _value = new DCard();
			_value.id = this.id;
			return _value;
		}
	}
	
	public static class DBoard extends DSyncBase {
		public static final String TypeName = "DBoard";
		
		private List<DHuman> humans;
		private EBoardState state;

		public static class K {
			public static final String humans = "humans";
			public static final String state = "state";
		}

		public DBoard() {
			init();
		}

		@Override
		protected void init() {
			humans = new ArrayList<>();
			state = EBoardState.Normal;
		}
		
		public int getHumansCount() {
			return this.humans.size();
		}
		
		public List<DHuman> getHumansList() {
			return new ArrayList<>(humans);
		}
		
		public void setHumansList(List<DHuman> _value) {
			this.humans.clear();
			this.humans.addAll(_value);
		}

		public void addHumans(DHuman _value) {
			this.humans.add(_value);
		}
		
		public void addAllHumans(List<DHuman> _value) {
			this.humans.addAll(_value);
		}
		
		public void clearHumans() {
			this.humans.clear();
		}
		
		public EBoardState getState() {
			return state;
		}
		
		public void setState(EBoardState state) {
			this.state = state;
		}
		

		static DBoard parseJSONObject(JSONObject jsonObject) {
			var _value = new DBoard();
			if (!jsonObject.isEmpty()) {
				_value.applyRecord(jsonObject);
			}
			return _value;
		}
		
		static List<DBoard> parseJSONArray(JSONArray jsonArray) {
			var list = new ArrayList<DBoard>();
			for (int i = 0; i < jsonArray.size(); i++) {
				var _value = new DBoard();
				var jsonObject = jsonArray.getJSONObject(i);
				if (!jsonObject.isEmpty()) {
					_value.applyRecord(jsonObject);
				}
				list.add(_value);
			}
			return list;
		}

		@Override
		public void getRecord(JSONObject jsonObject) {
			jsonObject.put(DSyncBase.K.TypeName, TypeName);
			jsonObject.put(K.humans, getJSONArray(humans));
			jsonObject.put(K.state, state.ordinal());
		}

		@Override
		public void applyRecord(JSONObject jsonObject) {
			humans = DHuman.parseJSONArray(jsonObject.getJSONArray(K.humans));
			state = EBoardState.values()[(jsonObject.getIntValue(K.state))];
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (!(obj instanceof DBoard)) {
				return false;
			}
			var _value = (DBoard) obj;
			if (!this.humans.equals(_value.humans)) {
				return false;
			}
			if (!this.state.equals(_value.state)) {
				return false;
			}
			return true;
		}
		
		public DBoard copy() {
			var _value = new DBoard();
			_value.humans = new ArrayList<>(this.humans);
			_value.state = this.state;
			return _value;
		}
		
		public DBoard deepCopy() {
			var _value = new DBoard();
			_value.humans = new ArrayList<>();
			for(var _f: this.humans) {
				if (_f != null) {
					_value.humans.add(_f.deepCopy());
				} else {
					_value.humans.add(null);
				}
			}
			_value.state = this.state;
			return _value;
		}
	}
	
	public static class DCardPile extends DSyncBase {
		public static final String TypeName = "DCardPile";
		
		private List<DCard> cards;

		public static class K {
			public static final String cards = "cards";
		}

		public DCardPile() {
			init();
		}

		@Override
		protected void init() {
			cards = new ArrayList<>();
		}
		
		public int getCardsCount() {
			return this.cards.size();
		}
		
		public List<DCard> getCardsList() {
			return new ArrayList<>(cards);
		}
		
		public void setCardsList(List<DCard> _value) {
			this.cards.clear();
			this.cards.addAll(_value);
		}

		public void addCards(DCard _value) {
			this.cards.add(_value);
		}
		
		public void addAllCards(List<DCard> _value) {
			this.cards.addAll(_value);
		}
		
		public void clearCards() {
			this.cards.clear();
		}
		

		static DCardPile parseJSONObject(JSONObject jsonObject) {
			var _value = new DCardPile();
			if (!jsonObject.isEmpty()) {
				_value.applyRecord(jsonObject);
			}
			return _value;
		}
		
		static List<DCardPile> parseJSONArray(JSONArray jsonArray) {
			var list = new ArrayList<DCardPile>();
			for (int i = 0; i < jsonArray.size(); i++) {
				var _value = new DCardPile();
				var jsonObject = jsonArray.getJSONObject(i);
				if (!jsonObject.isEmpty()) {
					_value.applyRecord(jsonObject);
				}
				list.add(_value);
			}
			return list;
		}

		@Override
		public void getRecord(JSONObject jsonObject) {
			jsonObject.put(DSyncBase.K.TypeName, TypeName);
			jsonObject.put(K.cards, getJSONArray(cards));
		}

		@Override
		public void applyRecord(JSONObject jsonObject) {
			cards = DCard.parseJSONArray(jsonObject.getJSONArray(K.cards));
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (!(obj instanceof DCardPile)) {
				return false;
			}
			var _value = (DCardPile) obj;
			if (!this.cards.equals(_value.cards)) {
				return false;
			}
			return true;
		}
		
		public DCardPile copy() {
			var _value = new DCardPile();
			_value.cards = new ArrayList<>(this.cards);
			return _value;
		}
		
		public DCardPile deepCopy() {
			var _value = new DCardPile();
			_value.cards = new ArrayList<>();
			for(var _f: this.cards) {
				if (_f != null) {
					_value.cards.add(_f.deepCopy());
				} else {
					_value.cards.add(null);
				}
			}
			return _value;
		}
	}
	
	/**
	 * comment test1
	 * comment test2
	 */
	public static class DHuman extends DSyncBase {
		public static final String TypeName = "DHuman";
		
		private List<Long> id;
		private double percent;
		private List<Double> percents;
		private String name;
		/** field comment */
		private DCardPile handPile;
		private List<String> cards;

		public static class K {
			public static final String id = "id";
			public static final String percent = "percent";
			public static final String percents = "percents";
			public static final String name = "name";
			public static final String handPile = "handPile";
			public static final String cards = "cards";
		}

		public DHuman() {
			init();
		}

		@Override
		protected void init() {
			id = new ArrayList<>();
			percent = 0f;
			percents = new ArrayList<>();
			name = "";
			handPile = null;
			cards = new ArrayList<>();
		}
		
		public int getIdCount() {
			return this.id.size();
		}
		
		public List<Long> getIdList() {
			return new ArrayList<>(id);
		}
		
		public void setIdList(List<Long> _value) {
			this.id.clear();
			this.id.addAll(_value);
		}

		public void addId(long _value) {
			this.id.add(_value);
		}
		
		public void addAllId(List<Long> _value) {
			this.id.addAll(_value);
		}
		
		public void clearId() {
			this.id.clear();
		}
		
		public double getPercent() {
			return percent;
		}
		
		public void setPercent(double percent) {
			this.percent = percent;
		}
		
		public int getPercentsCount() {
			return this.percents.size();
		}
		
		public List<Double> getPercentsList() {
			return new ArrayList<>(percents);
		}
		
		public void setPercentsList(List<Double> _value) {
			this.percents.clear();
			this.percents.addAll(_value);
		}

		public void addPercents(double _value) {
			this.percents.add(_value);
		}
		
		public void addAllPercents(List<Double> _value) {
			this.percents.addAll(_value);
		}
		
		public void clearPercents() {
			this.percents.clear();
		}
		
		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
		}
		
		/** field comment */
		public DCardPile getHandPile() {
			return handPile;
		}
		
		/** field comment */
		public void setHandPile(DCardPile handPile) {
			this.handPile = handPile;
		}
		
		public int getCardsCount() {
			return this.cards.size();
		}
		
		public List<String> getCardsList() {
			return new ArrayList<>(cards);
		}
		
		public void setCardsList(List<String> _value) {
			this.cards.clear();
			this.cards.addAll(_value);
		}

		public void addCards(String _value) {
			this.cards.add(_value);
		}
		
		public void addAllCards(List<String> _value) {
			this.cards.addAll(_value);
		}
		
		public void clearCards() {
			this.cards.clear();
		}
		

		static DHuman parseJSONObject(JSONObject jsonObject) {
			var _value = new DHuman();
			if (!jsonObject.isEmpty()) {
				_value.applyRecord(jsonObject);
			}
			return _value;
		}
		
		static List<DHuman> parseJSONArray(JSONArray jsonArray) {
			var list = new ArrayList<DHuman>();
			for (int i = 0; i < jsonArray.size(); i++) {
				var _value = new DHuman();
				var jsonObject = jsonArray.getJSONObject(i);
				if (!jsonObject.isEmpty()) {
					_value.applyRecord(jsonObject);
				}
				list.add(_value);
			}
			return list;
		}

		@Override
		public void getRecord(JSONObject jsonObject) {
			jsonObject.put(DSyncBase.K.TypeName, TypeName);
			jsonObject.put(K.id, JSONObject.toJSONString(id));
			jsonObject.put(K.percent, percent);
			jsonObject.put(K.percents, JSONObject.toJSONString(percents));
			jsonObject.put(K.name, name);
			jsonObject.put(K.handPile, getJSONObject(handPile));
			jsonObject.put(K.cards, JSONObject.toJSONString(cards));
		}

		@Override
		public void applyRecord(JSONObject jsonObject) {
			id = JSONObject.parseArray(jsonObject.getString(K.id), Long.class);
			percent = jsonObject.getDoubleValue(K.percent);
			percents = JSONObject.parseArray(jsonObject.getString(K.percents), Double.class);
			name = jsonObject.getString(K.name);
			handPile = DCardPile.parseJSONObject(jsonObject.getJSONObject(K.handPile));
			cards = JSONObject.parseArray(jsonObject.getString(K.cards), String.class);
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (!(obj instanceof DHuman)) {
				return false;
			}
			var _value = (DHuman) obj;
			if (!this.id.equals(_value.id)) {
				return false;
			}
			if (this.percent != _value.percent) {
				return false;
			}
			if (!this.percents.equals(_value.percents)) {
				return false;
			}
			if (!this.name.equals(_value.name)) {
				return false;
			}
			if (!this.handPile.equals(_value.handPile)) {
				return false;
			}
			if (!this.cards.equals(_value.cards)) {
				return false;
			}
			return true;
		}
		
		public DHuman copy() {
			var _value = new DHuman();
			_value.id = new ArrayList<>(this.id);
			_value.percent = this.percent;
			_value.percents = new ArrayList<>(this.percents);
			_value.name = this.name;
			_value.handPile = this.handPile;
			_value.cards = new ArrayList<>(this.cards);
			return _value;
		}
		
		public DHuman deepCopy() {
			var _value = new DHuman();
			_value.id = new ArrayList<>(this.id);
			_value.percent = this.percent;
			_value.percents = new ArrayList<>(this.percents);
			_value.name = this.name;
			if (this.handPile != null) {
				_value.handPile = this.handPile.deepCopy();
			}
			_value.cards = new ArrayList<>(this.cards);
			return _value;
		}
	}
	

	public static enum EBoardState {
		/** normal */
		Normal,
		/** run */
		Run,
		/** close */
		Close,
	}
}

