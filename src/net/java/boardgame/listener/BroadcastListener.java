package net.java.boardgame.listener;

import net.java.boardgame.enums.EnumBroadcast;

public interface BroadcastListener {
	public void broadcast(EnumBroadcast event, Object ... args);
}
