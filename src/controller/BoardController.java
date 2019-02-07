package controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;
import model.Agent;
import view.Board;

public class BoardController {

	private Board board;
	private Map<Integer, Agent> agents;
	private Integer nbAgent;

	public BoardController(Board board) {
		this.board = board;
		this.board.setBoardController(this);
		this.nbAgent = board.getNbAgent();
		this.agents = new HashMap<>();
	}

	public Pair<Integer, Integer> generateRandomPos(List<Pair<Integer, Integer>> positions) {
		Random rand = new Random();
		Pair<Integer, Integer> pos = new Pair<Integer, Integer>(rand.nextInt(Board.height), rand.nextInt(Board.length));
		while (positions.contains(pos)) {
			pos = new Pair<Integer, Integer>(rand.nextInt(Board.height), rand.nextInt(Board.length));
		}
		positions.add(pos);
		return pos;
	}

	public void addAgent(Agent agent) {
		agents.put(agent.getId(), agent);
	}

	public void removeAgent(Agent agent) {
		agents.remove(agent.getId());
	}

	public void applyLabel(Agent a, String colorHexa){
		Label r = (Label) ((StackPane) board.getGridPane().getChildren()
				.get(a.getActualPos().getKey() * Board.length + a.getActualPos().getValue())).getChildren().get(1);
		r.setText("" + a.getId());
		r.setTextFill(Color.web(colorHexa));
	}

	public Map<Integer, Agent> getAgents() {
		return agents;
	}

	public void updateLabel(Agent a, String color, Pair<Integer, Integer> posTo) {
		StackPane actual = (StackPane) board.getGridPane().getChildren()
				.get(a.getActualPos().getKey() * Board.length + a.getActualPos().getValue());
		StackPane to = (StackPane) board.getGridPane().getChildren()
				.get(posTo.getKey() * Board.length + posTo.getValue());
		((Label)to.getChildren().get(1)).setText(a.getId().toString());
		((Label)actual.getChildren().get(1)).setText("");
		((Rectangle)to.getChildren().get(0)).setFill(a.getColor());
		((Rectangle)actual.getChildren().get(0)).setFill(Color.WHITE);
		a.setActualPos(posTo);
		if(a.getActualPos().equals(a.getFinalPos())){
			((Label)to.getChildren().get(1)).setTextFill(Color.WHITE);
		}
		else{
			((Label)to.getChildren().get(1)).setTextFill(Color.BLACK);
		}
	}
}
