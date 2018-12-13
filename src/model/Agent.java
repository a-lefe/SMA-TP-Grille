package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Stack;

import javafx.util.Pair;
import view.Board;

public class Agent extends Observable {
	Board grid;
	Integer id;
	List<Agent> agents;
	Stack<String> mailbox;
	//Pair<Ligne, Colonne>
	Pair<Integer, Integer> finalPos;
	Pair<Integer, Integer> actualPos;
	Boolean blocked;
	
	public Agent(Integer id, Board grid, Pair<Integer, Integer> initialPos, Pair<Integer, Integer> finalPos){
		this.id = id;
		this.agents = new ArrayList<>();
		this.mailbox = new Stack<>();
		this.grid = grid;
		this.finalPos = finalPos;
		this.actualPos = initialPos;
		this.blocked = false;
	}

	public void addAgent(Agent a) {
		this.agents.add(a);
	}

	public void addMessage(String message) {
		this.mailbox.add(message);
	}

	public synchronized void setPosition(Pair<Integer, Integer> pos) {
		this.actualPos = pos;
	}

}
