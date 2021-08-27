package com.backinfile.dSync.tmp;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.backinfile.dSync.model.DSyncBaseHandler;
import com.backinfile.dSync.model.DSyncException;
import com.backinfile.dSync.model.Mode;

public class Handler extends DSyncBaseHandler {
	private DBoard root;
	private List<DSyncListener> listeners = new ArrayList<>();

	public Handler(Mode mode) {
		super(mode);
		root = new DBoard();
		put(root);
		root.sync();
	}

	public DBoard getRoot() {
		return root;
	}
	
	@Override
	protected void onReceiveChangeLog(long id) {
		var obj = get(id);
		if (obj == null) {
			return;
		}
		if (obj instanceof DCard) {
			for (var listenr : listeners) {
				listenr.onDataChange((DCard) (obj));
			}
			return;
		}
		if (obj instanceof DBoard) {
			for (var listenr : listeners) {
				listenr.onDataChange((DBoard) (obj));
			}
			return;
		}
		if (obj instanceof DCardPile) {
			for (var listenr : listeners) {
				listenr.onDataChange((DCardPile) (obj));
			}
			return;
		}
		if (obj instanceof DHuman) {
			for (var listenr : listeners) {
				listenr.onDataChange((DHuman) (obj));
			}
			return;
		}
	}
	
	public void addListener(DSyncListener listener) {
		if (mode != Mode.Client) {
			throw new DSyncException("非Client模式下，不能监听数据对象");
		}
		listeners.add(listener);
	}
	
	
	public static abstract class DSyncListener {
		public void onDataChange(DCard data) {
		}
		
		public void onDataChange(DBoard data) {
		}
		
		public void onDataChange(DCardPile data) {
		}
		
		public void onDataChange(DHuman data) {
		}
		
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

		public DCard() {
			init();
		}

		public static DCard newInstance(Handler _handler) {
			if (_handler.mode == Mode.Client) {
				throw new DSyncException("Client模式下，不能创建DSync数据对象");
			}
			DCard _struct = new DCard();
			if (_handler.mode == Mode.Server) {
				_handler.put(_struct);
			}
			return _struct;
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
			onChanged();
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
			_value._dSync_id = -1;
			_value.id = this.id;
			return _value;
		}
		
		
		public DCard deepCopy() {
			var _value = new DCard();
			_value._dSync_id = -1;
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

		public static DBoard newInstance(Handler _handler) {
			if (_handler.mode == Mode.Client) {
				throw new DSyncException("Client模式下，不能创建DSync数据对象");
			}
			DBoard _struct = new DBoard();
			if (_handler.mode == Mode.Server) {
				_handler.put(_struct);
			}
			return _struct;
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
			onChanged();
		}
		
		public void addHumans(DHuman _value) {
			this.humans.add(_value);
			onChanged();
		}
		
		public void removeHumans(DHuman _value) {
			this.humans.remove(_value);
			onChanged();
		}
		
		public void addAllHumans(List<DHuman> _value) {
			this.humans.addAll(_value);
			onChanged();
		}
		
		public void clearHumans() {
			this.humans.clear();
			onChanged();
		}
		
		public EBoardState getState() {
			return state;
		}
		
		public void setState(EBoardState state) {
			this.state = state;
			onChanged();
		}

		@Override
		protected void getRecord(JSONObject jsonObject) {
			jsonObject.put(DSyncBase.K.TypeName, TypeName);
			jsonObject.put(K.humans, toJSONString(humans));
			jsonObject.put(K.state, state.ordinal());
		}

		@Override
		protected void applyRecord(JSONObject jsonObject) {
			humans = fromJSONString(jsonObject.getString(K.humans));
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
			_value._dSync_id = -1;
			_value.humans = new ArrayList<>(this.humans);
			_value.state = this.state;
			return _value;
		}
		
		
		public DBoard deepCopy() {
			var _value = new DBoard();
			_value._dSync_id = -1;
			_value.humans = new ArrayList<>();
			for(var _f: this.humans) {
				_value.humans.add(_f.deepCopy());
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

		public static DCardPile newInstance(Handler _handler) {
			if (_handler.mode == Mode.Client) {
				throw new DSyncException("Client模式下，不能创建DSync数据对象");
			}
			DCardPile _struct = new DCardPile();
			if (_handler.mode == Mode.Server) {
				_handler.put(_struct);
			}
			return _struct;
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
			onChanged();
		}
		
		public void addCards(DCard _value) {
			this.cards.add(_value);
			onChanged();
		}
		
		public void removeCards(DCard _value) {
			this.cards.remove(_value);
			onChanged();
		}
		
		public void addAllCards(List<DCard> _value) {
			this.cards.addAll(_value);
			onChanged();
		}
		
		public void clearCards() {
			this.cards.clear();
			onChanged();
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
			_value._dSync_id = -1;
			_value.cards = new ArrayList<>(this.cards);
			return _value;
		}
		
		
		public DCardPile deepCopy() {
			var _value = new DCardPile();
			_value._dSync_id = -1;
			_value.cards = new ArrayList<>();
			for(var _f: this.cards) {
				_value.cards.add(_f.deepCopy());
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

		public static DHuman newInstance(Handler _handler) {
			if (_handler.mode == Mode.Client) {
				throw new DSyncException("Client模式下，不能创建DSync数据对象");
			}
			DHuman _struct = new DHuman();
			if (_handler.mode == Mode.Server) {
				_handler.put(_struct);
			}
			return _struct;
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
			onChanged();
		}
		
		public void addId(Long _value) {
			this.id.add(_value);
			onChanged();
		}
		
		public void removeId(Long _value) {
			this.id.remove(_value);
			onChanged();
		}
		
		public void addAllId(List<Long> _value) {
			this.id.addAll(_value);
			onChanged();
		}
		
		public void clearId() {
			this.id.clear();
			onChanged();
		}
		
		public double getPercent() {
			return percent;
		}
		
		public void setPercent(double percent) {
			this.percent = percent;
			onChanged();
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
			onChanged();
		}
		
		public void addPercents(Double _value) {
			this.percents.add(_value);
			onChanged();
		}
		
		public void removePercents(Double _value) {
			this.percents.remove(_value);
			onChanged();
		}
		
		public void addAllPercents(List<Double> _value) {
			this.percents.addAll(_value);
			onChanged();
		}
		
		public void clearPercents() {
			this.percents.clear();
			onChanged();
		}
		
		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
			onChanged();
		}
		/** field comment */
		public DCardPile getHandPile() {
			return handPile;
		}
		
		/** field comment */
		public void setHandPile(DCardPile handPile) {
			this.handPile = handPile;
			onChanged();
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
			onChanged();
		}
		
		public void addCards(String _value) {
			this.cards.add(_value);
			onChanged();
		}
		
		public void removeCards(String _value) {
			this.cards.remove(_value);
			onChanged();
		}
		
		public void addAllCards(List<String> _value) {
			this.cards.addAll(_value);
			onChanged();
		}
		
		public void clearCards() {
			this.cards.clear();
			onChanged();
		}
		

		@Override
		protected void getRecord(JSONObject jsonObject) {
			jsonObject.put(DSyncBase.K.TypeName, TypeName);
			jsonObject.put(K.id, JSONObject.toJSONString(id));
			jsonObject.put(K.percent, percent);
			jsonObject.put(K.percents, JSONObject.toJSONString(percents));
			jsonObject.put(K.name, name);
			jsonObject.put(K.handPile, handPile.get_dSync_id());
			jsonObject.put(K.cards, JSONObject.toJSONString(cards));
		}

		@Override
		protected void applyRecord(JSONObject jsonObject) {
			id = JSONObject.parseArray(jsonObject.getString(K.id), Long.class);
			percent = jsonObject.getDoubleValue(K.percent);
			percents = JSONObject.parseArray(jsonObject.getString(K.percents), Double.class);
			name = jsonObject.getString(K.name);
			handPile = (DCardPile) handler.get(jsonObject.getLongValue(K.handPile));
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
			_value._dSync_id = -1;
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
			_value._dSync_id = -1;
			_value.id = new ArrayList<>(this.id);
			_value.percent = this.percent;
			_value.percents = new ArrayList<>(this.percents);
			_value.name = this.name;
			_value.handPile = this.handPile.deepCopy();
			_value.cards = new ArrayList<>(this.cards);
			return _value;
		}
	}
	


	public static enum EBoardState {
		/** normal */
		Normal,
		/** run */
		Run,
	}
}

