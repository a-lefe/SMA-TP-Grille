package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Stack;

import javafx.util.Pair;
import view.Board;

public class Agent extends Observable{
	Board grid;
	List<Agent> agents;
	Stack<String> mailbox;
	Pair<Integer, Integer> finalPos;
	Pair<Integer, Integer> actualPos;
	
	public Agent(Board grid, Pair<Integer, Integer> initialPos, Pair<Integer, Integer> finalPos){
		this.agents = new ArrayList<>();
		this.mailbox = new Stack<>();
		this.grid = grid;
		this.finalPos = finalPos;
		this.actualPos = initialPos;
	}
	
	public void addAgent(Agent a){
		this.agents.add(a);
	}
	
	public void addMessage(String message){
		this.mailbox.add(message);
	}
	
	public synchronized void setPosition(Pair<Integer, Integer> pos){
		
	}
}
