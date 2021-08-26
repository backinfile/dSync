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
		root.init();
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
	}
	
	/**
	 * 玩家信息
	 * 玩家信息2
	 */
	public static class DBoard extends DSyncBase {
		public static final String TypeName = "DBoard";
		
		private List<DHuman> humans;

		public static class K {
			public static final String humans = "humans";
		}

		public DBoard() {
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
		
		public int getHumansCount() {
			return this.humans.size();
		}
		
		public List<DHuman> getAllHumans() {
			return new ArrayList<>(humans);
		}
		
		public void setAllHumans(List<DHuman> _value) {
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

		public DCardPile() {
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
		
		public int getCardsCount() {
			return this.cards.size();
		}
		
		public List<DCard> getAllCards() {
			return new ArrayList<>(cards);
		}
		
		public void setAllCards(List<DCard> _value) {
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
	}
	
	/**
	 * 玩家信息
	 * 玩家信息2
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

		public DHuman() {
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
		
		public long getId() {
			return id;
		}
		
		public void setId(long id) {
			this.id = id;
			onChanged();
		}
		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
			onChanged();
		}
		/** 手牌 */
		public DCardPile getHandPile() {
			return handPile;
		}
		
		/** 手牌 */
		public void setHandPile(DCardPile handPile) {
			this.handPile = handPile;
			onChanged();
		}
		public int getCardsCount() {
			return this.cards.size();
		}
		
		public List<String> getAllCards() {
			return new ArrayList<>(cards);
		}
		
		public void setAllCards(List<String> _value) {
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

