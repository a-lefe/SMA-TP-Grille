package model;

public class Message {
	public Integer idAgent;
	public Direction direction;

	public Message(Integer idAgent, Direction direction) {
		super();
		this.idAgent = idAgent;
		this.direction = direction;
	}
}
