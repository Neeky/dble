package io.mycat.server.handler;

import io.mycat.server.ServerConnection;

public final class CommitHandler {
	public static void handle(String stmt, ServerConnection c) {
		c.commit(stmt);
	}
}