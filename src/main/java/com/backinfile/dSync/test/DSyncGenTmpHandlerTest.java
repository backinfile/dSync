package com.backinfile.dSync.test;

import com.backinfile.dSync.tmp.Handler;
import com.backinfile.dSync.tmp.Handler.DBoard;
import com.backinfile.dSync.tmp.Handler.DHuman;
import com.backinfile.dSync.tmp.Handler.DSyncListener;
import com.backinfile.dSync.tmp.Handler.EBoardState;

public class DSyncGenTmpHandlerTest {
	public static void main(String[] args) {
		DBoard board = new DBoard();
		board.setState(EBoardState.Run);
		DHuman human = new DHuman();
		human.addId(123L);
		human.setPercent(0.5f);
		human.setName("name234");
		board.addHumans(human);

		String message = board.toMessage();

		Handler handler = new Handler();
		handler.addListener(new DSyncListener() {
			@Override
			public void onMessage(DHuman data) {
				super.onMessage(data);
			}

			@Override
			public void onMessage(DBoard data) {
				super.onMessage(data);
			}
		});
		handler.onMessage(message);

		DBoard deepCopy = board.deepCopy();

		System.out.println(deepCopy);
	}
}
