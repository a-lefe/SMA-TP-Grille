package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.util.Pair;
import model.Agent;
import view.Board;

public class BoardController {

	public String[][] initialState, finalState;
	Board board;
	private Map<Integer, Agent> agents;

	public BoardController(Board board) {
		this.board = board;
		agents = new HashMap<>();
	}

	public void initAgents(Integer nbAgents) {
		if (nbAgents > Board.length * Board.height - 1) {
			System.out.println("Can't initialize - Cause : Number of agents > Number of tiles");
			return;
		}
		Label r;
		List<Pair<Integer, Integer>> startPositions = new ArrayList<>();
		List<Pair<Integer, Integer>> finalPositions = new ArrayList<>();
		Pair<Integer, Integer> pStart, pFinal;
		for (int i = 0; i < nbAgents; i++) {
			// Start position

			pStart = generateRandomPos(startPositions);
			r = (Label) ((StackPane) board.getGridPane().getChildren()
					.get(pStart.getKey() * Board.length + pStart.getValue())).getChildren().get(1);
			r.setText("X" + i);

			// Final position
			pFinal = generateRandomPos(finalPositions);
			finalPositions.add(pFinal);

			agents.put(i, new Agent(i, board, pStart, pFinal));
		}
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

}
