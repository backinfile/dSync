package com.backinfile.dSync.test;

import com.backinfile.dSync.model.Mode;
import com.backinfile.dSync.test.DSyncGenTmpHandler.Human;

public class DSyncGenTmpHandlerTest {
	public static void main(String[] args) {
		var server = new DSyncGenTmpHandler(Mode.Server);
		var human = Human.newInstance(server);
		server.getRoot().addHumans(human);

		human.setName("ssss");

		String changeLog = server.getChangeLog();

		var client = new DSyncGenTmpHandler(Mode.Client);
		client.receiveChangeLog(changeLog);

		System.out.println();
	}
}
