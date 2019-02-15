package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Random;

import javafx.scene.paint.Color;
import javafx.util.Pair;
import view.Board;

public class Agent extends Observable implements Runnable {
	Board board;
	Integer id;
	Color color;
	// Pair<Colonne, Ligne>
	Pair<Integer, Integer> finalPos;
	Pair<Integer, Integer> actualPos;
	Boolean blocked;
	Boolean allMoved;

	public Agent(Integer id, Board grid, Pair<Integer, Integer> initialPos, Pair<Integer, Integer> finalPos,
			Boolean allMoves) {
		this.id = id;
		this.board = grid;
		this.finalPos = finalPos;
		this.actualPos = initialPos;
		this.color = Color.color(Math.random(), Math.random(), Math.random());
		this.blocked = false;
		this.allMoved = allMoves;
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

	public synchronized void setActualPos(Pair<Integer, Integer> actualPos) {
		this.actualPos = actualPos;
	}

	public synchronized Pair<Integer, Integer> dumbMove() {
		// actualPos.getKey() = colonne
		// acutalPos.getValue() = ligne
		System.out.println(this);
		Pair<Integer, Integer> nextStep = null;
		if (finalPos == null || actualPos == null)
			return null;
		if (actualPos.getKey() < finalPos.getKey()) {
			nextStep = new Pair<Integer, Integer>(actualPos.getKey() + 1, actualPos.getValue());
		} else if (actualPos.getKey() > finalPos.getKey()) {
			nextStep = new Pair<Integer, Integer>(actualPos.getKey() - 1, actualPos.getValue());
		} else if (actualPos.getValue() < finalPos.getValue()) {
			nextStep = new Pair<Integer, Integer>(actualPos.getKey(), actualPos.getValue() + 1);
		} else if (actualPos.getValue() > finalPos.getValue()) {
			nextStep = new Pair<Integer, Integer>(actualPos.getKey(), actualPos.getValue() - 1);
		} else
			return null;

		String agentId = board.fecthAgentIdInPos(nextStep);
		if (agentId.isEmpty()) {
			return nextStep;
		} else {
			System.out.println("Ask " + agentId + " to move");
			this.board.getMailbox().postMessage(Integer.valueOf(agentId), "Please move");
			return null;
		}
	}

	public synchronized Pair<Integer, Integer> dumbMoveV2() {
		// actualPos.getKey() = colonne
		// acutalPos.getValue() = ligne
		Message message;
		Pair<Integer, Integer> nextStep = null;
		if (finalPos == null || actualPos == null)
			return null;
		if (actualPos.getKey() < finalPos.getKey()) {
			nextStep = new Pair<Integer, Integer>(actualPos.getKey() + 1, actualPos.getValue());
		} else if (actualPos.getKey() > finalPos.getKey()) {
			nextStep = new Pair<Integer, Integer>(actualPos.getKey() - 1, actualPos.getValue());
		} else if (actualPos.getValue() < finalPos.getValue()) {
			nextStep = new Pair<Integer, Integer>(actualPos.getKey(), actualPos.getValue() + 1);
		} else if (actualPos.getValue() > finalPos.getValue()) {
			nextStep = new Pair<Integer, Integer>(actualPos.getKey(), actualPos.getValue() - 1);
		} else
			return null;
		return nextStep;
	}

	public synchronized List<Pair<Integer, Integer>> findAvalaibleMoves() {
		List<Pair<Integer, Integer>> moves = new ArrayList<>();
		if (actualPos.getKey() > 0
				&& board.fecthAgentIdInPos(new Pair<Integer, Integer>(actualPos.getKey() - 1, actualPos.getValue()))
						.isEmpty()) {
			moves.add(new Pair<Integer, Integer>(actualPos.getKey() - 1, actualPos.getValue()));
		}
		if (actualPos.getKey() < Board.height - 1
				&& board.fecthAgentIdInPos(new Pair<Integer, Integer>(actualPos.getKey() + 1, actualPos.getValue()))
						.isEmpty()) {
			moves.add(new Pair<Integer, Integer>(actualPos.getKey() + 1, actualPos.getValue()));
		}
		if (actualPos.getValue() > 0
				&& board.fecthAgentIdInPos(new Pair<Integer, Integer>(actualPos.getKey(), actualPos.getValue() - 1))
						.isEmpty()) {
			moves.add(new Pair<Integer, Integer>(actualPos.getKey(), actualPos.getValue() - 1));
		}
		if (actualPos.getValue() < Board.length - 1
				&& board.fecthAgentIdInPos(new Pair<Integer, Integer>(actualPos.getKey(), actualPos.getValue() + 1))
						.isEmpty()) {
			moves.add(new Pair<Integer, Integer>(actualPos.getKey(), actualPos.getValue() + 1));
		}
		return moves;
	}

	@Override
	public void run() {
		if (allMoved) {
			allMove();
		} else {
			resolveOnePerOne();
		}
	}

	private void resolveOnePerOne() {
		try {
			Message message;
			List<Pair<Integer, Integer>> moves;
			Pair<Integer, Integer> move = null;
			Agent agent = null;
			Integer agentFrom = 0;
			Random r = new Random();
			Boolean messageReceived;
			List<Integer> askedAgent = new ArrayList<>();
			while (true) {
				Thread.sleep(2000);
				if (!this.blocked) {
					move = null;
					message = board.getMailBoxV2().retriveMessage(id);
					moves = findAvalaibleMoves();
					if (message != null) {
						if (message.getTypeMessage() == TypeMessage.MOVE) {
							agentFrom = message.getFrom();
							System.out.println("Agent " + id + ": im aksed to move");
							moves = findAvalaibleMoves();
							if (moves.isEmpty()) {
								agent = board.getAgentAround(actualPos);
								move = agent.getActualPos();
								message = new Message(this.id, TypeMessage.MOVE);
								board.getMailBoxV2().postMessage(agent.getId(), message);
								messageReceived = false;
								while (!messageReceived) {
									Thread.sleep(1000);
									message = board.getMailBoxV2().retriveMessage(this.id);
									if (message != null && message.getTypeMessage() == TypeMessage.MOVED) {
										messageReceived = true;
									}
								}
							} else {
								move = moves.get(r.nextInt(moves.size()));
							}

							if (move != null) {
								setChanged();
								notifyObservers(move);
								board.getMailBoxV2().postMessage(agentFrom, new Message(this.id, TypeMessage.MOVED));
							}
						} else {
							while (!actualPos.equals(finalPos)) {
								move = dumbMoveV2();
								System.out.println("AGENT " + id + " MOVED");
								String agentId = board.fecthAgentIdInPos(move);
								if (!agentId.isEmpty()) {
									System.out.println("AGENT " + agentId + "asked to move");
									this.board.getMailBoxV2().postMessage(Integer.valueOf(agentId),
											new Message(this.id, TypeMessage.MOVE));
									messageReceived = false;
									askedAgent.add(Integer.valueOf(agentId));
									while (!messageReceived) {
										Thread.sleep(1000);
										message = board.getMailBoxV2().retriveMessage(this.id);
										if (message != null && message.getTypeMessage() == TypeMessage.MOVED) {
											messageReceived = true;
										}
									}
								}
								setChanged();
								notifyObservers(move);
								Thread.sleep(1000);
							}
							System.out.println("Agent " + this.id + " arrived, ask next to move");
							System.out.println(askedAgent);
							if (askedAgent.isEmpty()) {
								board.getMailBoxV2().postMessage(this.id + 1,
										new Message(this.id, TypeMessage.RESOLVE));
							} else {
								for (Integer agentId : new ArrayList<Integer>(askedAgent)) {
									board.getMailBoxV2().postMessage(agentId,
											new Message(this.id, TypeMessage.RESOLVE));
									askedAgent.remove(agentId);
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void allMove() {
		try {
			String message;
			List<Pair<Integer, Integer>> moves;
			Pair<Integer, Integer> move = null;
			Integer count = 0;
			Random r = new Random();
			while (count < 30) {
				Thread.sleep(2000);
				if (!this.blocked) {
					System.out.println(this);
					move = null;
					message = board.getMailbox().retriveMessage(id);
					moves = findAvalaibleMoves();
					if (message != null && !message.equals("") && !moves.isEmpty()) {
						move = moves.get(r.nextInt(moves.size()));
					}

					if (move == null && !actualPos.equals(finalPos) && finalPos != null) {
						move = dumbMove();
					} else if (move == null) {
						count += 1;
					}

					if (move != null) {
						System.out.println("Notify Observers for " + this);
						setChanged();
						notifyObservers(move);
					}

					if (actualPos.equals(finalPos)) {
						System.out.println("Agent " + id + " arrived");
						this.blocked = true;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Pair<Integer, Integer> getFinalPos() {
		return finalPos;
	}

	@Override
	public String toString() {
		return "Agent [id=" + id + ", finalPosX=" + (finalPos == null ? null : finalPos.getKey()) + ",finalPosY="
				+ (finalPos == null ? 0 : finalPos.getValue()) + ", actualPosX=" + actualPos.getKey() + ", actualPosY="
				+ actualPos.getValue() + "]";
	}

	public Color getColor() {
		return color;
	}

	public Boolean getBlocked() {
		return blocked;
	}

	public void draw() {
		setChanged();
		Pair<Integer, Integer> move = new Pair<Integer, Integer>(this.getActualPos().getKey(),
				this.getActualPos().getValue());
		notifyObservers(move);
	}
}
