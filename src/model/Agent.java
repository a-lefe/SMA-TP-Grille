package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javafx.util.Pair;
import view.Board;

public class Agent extends Observable implements Runnable {
	Board board;
	Integer id;
	List<Agent> agents;
	// Pair<Colonne, Ligne>
	Pair<Integer, Integer> finalPos;
	Pair<Integer, Integer> actualPos;
	Boolean blocked;

	public Agent(Integer id, Board grid, Pair<Integer, Integer> initialPos, Pair<Integer, Integer> finalPos) {
		this.id = id;
		this.agents = new ArrayList<>();
		this.board = grid;
		this.finalPos = finalPos;
		this.actualPos = initialPos;
		this.blocked = false;
	}

	public void addAgent(Agent a) {
		this.agents.add(a);
	}

	public synchronized void setPosition(Pair<Integer, Integer> pos) {
		this.actualPos = pos;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Pair<Integer, Integer> getActualPos() {
		return actualPos;
	}

	public void setActualPos(Pair<Integer, Integer> actualPos) {
		this.actualPos = actualPos;
	}

	public void dumbMove() {
		// actualPos.getKey() = colonne
		// acutalPos.getValue() = ligne
		Pair<Integer, Integer> nextStep = null;
		if (actualPos.getKey() < finalPos.getKey()) {
			nextStep = new Pair<Integer, Integer>(actualPos.getKey() + 1, actualPos.getValue());
		} else if (actualPos.getKey() > finalPos.getKey()) {
			nextStep = new Pair<Integer, Integer>(actualPos.getKey() - 1, actualPos.getValue());
		} else if (actualPos.getValue() < finalPos.getValue()) {
			nextStep = new Pair<Integer, Integer>(actualPos.getKey(), actualPos.getValue() + 1);
		} else if (actualPos.getValue() > finalPos.getValue()) {
			nextStep = new Pair<Integer, Integer>(actualPos.getKey(), actualPos.getValue() - 1);
		} else
			return;

		if (board.isFree(nextStep)) {
			this.actualPos = nextStep;
		} else {

		}
	}

	public List<Pair<Integer, Integer>> findAvalaibleMoves() {
		List<Pair<Integer, Integer>> moves = new ArrayList<>();
		if (actualPos.getKey() > 0) {
			moves.add(new Pair<Integer, Integer>(actualPos.getKey() - 1, actualPos.getValue()));
		}
		if (actualPos.getKey() < Board.height) {
			moves.add(new Pair<Integer, Integer>(actualPos.getKey() + 1, actualPos.getValue()));
		}
		if (actualPos.getValue() > 0) {
			moves.add(new Pair<Integer, Integer>(actualPos.getKey(), actualPos.getValue() - 1));
		}
		if (actualPos.getValue() < Board.length) {
			moves.add(new Pair<Integer, Integer>(actualPos.getKey(), actualPos.getValue() + 1));
		}
		return moves;
	}

	@Override
	public void run() {
		try {
			String message;
			List<Pair<Integer, Integer>> moves;
			Pair<Integer, Integer> move;
			while (true) {
				Thread.sleep(1000);
				message = board.getMailbox().retriveMessage(id);
				if (message != null && !message.equals("")) {
					// Try to find an available place
					moves = findAvalaibleMoves();
					// move =
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Vérifier messages

		// Deplacement Request

		// Dépalacements

	}

}
