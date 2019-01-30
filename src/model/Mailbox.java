package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Mailbox {

	private Map<Integer, Stack<String>> mailbox;

	public Mailbox(Integer nbAgent) {
		mailbox = new HashMap<>();
		for (int i = 0; i < nbAgent; ++i) {
			mailbox.put(i, new Stack<>());
		}
	}

	public synchronized void postMessage(Integer id, String message) {
		mailbox.get(id).add(message);
	}

	public synchronized String retriveMessage(Integer id) {
		Stack<String> s = mailbox.get(id);
		if(s.isEmpty()) return null;
		return s.pop();
	}

}
