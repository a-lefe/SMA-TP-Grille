package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class MailBoxV2 {
	private Map<Integer, Stack<Message>> mailbox;

	public MailBoxV2(Integer nbAgent) {
		mailbox = new HashMap<>();
		for (int i = 0; i <= nbAgent; ++i) {
			mailbox.put(i, new Stack<>());
		}
	}

	public synchronized void postMessage(Integer id, Message message) {
		mailbox.get(id).add(message);
	}

	public synchronized Message retriveMessage(Integer id) {
		Stack<Message> s = mailbox.get(id);
		if (s.isEmpty())
			return null;
		return s.pop();
	}
}
