package model;

public class Message {
	public Integer from;
	public TypeMessage typeMessage;

	public Message(Integer from, TypeMessage type) {
		super();
		this.from = from;
		this.typeMessage = type;
	}

	public Integer getFrom() {
		return from;
	}

	public TypeMessage getTypeMessage() {
		return typeMessage;
	}
}