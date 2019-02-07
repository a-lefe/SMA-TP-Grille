package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import controller.BoardController;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;
import model.Agent;
import model.MailBoxV2;
import model.Mailbox;

public class Board {

	private GridPane gridPane;

	public static int length, height;

	private Integer nbAgent;
	private Mailbox mailbox;
	private MailBoxV2 mailBoxV2;
	private BoardController boardController;

	public Board(Integer nbAgent) {
		this.nbAgent = nbAgent;
		this.mailbox = new Mailbox(nbAgent);
		this.mailBoxV2 = new MailBoxV2(nbAgent);
		length = 5;
		height = 5;
		gridPane = new GridPane();
	}

	public void draw() {
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < height; j++) {
				StackPane pane = getRectangle("");
				gridPane.add(pane, i, j);
			}
		}
	}

	private StackPane getRectangle(String content) {
		Label label = new Label(content);
		Rectangle rectangle = new Rectangle();
		rectangle.setWidth(50);
		rectangle.setHeight(50);
		rectangle.setStroke(Color.BLACK);
		rectangle.setFill(Color.WHITE);
		return new StackPane(rectangle, label);
	}
	
	public StackPane getRectangle(String content, Color color) {
		Label label = new Label(content);
		Rectangle rectangle = new Rectangle();
		rectangle.setWidth(50);
		rectangle.setHeight(50);
		rectangle.setStroke(Color.BLACK);
		rectangle.setFill(color);
		return new StackPane(rectangle, label);
	}

	public synchronized String fecthAgentIdInPos(Pair<Integer, Integer> pos) {
		Integer row = pos.getKey();
		Integer column = pos.getValue();
		Label r = (Label) ((StackPane) gridPane.getChildren().get(row * height + column)).getChildren().get(1);
		return r.getText();

	}
	
	public Agent getAgentAround(Pair<Integer, Integer> pos){
		Random r = new Random();
		List<Agent> agents = new ArrayList<>();
		String agent = fecthAgentIdInPos(new Pair<Integer, Integer>(pos.getKey() - 1, pos.getValue()));
		if (!agent.isEmpty() && !boardController.getAgents().get(Integer.valueOf(agent)).getBlocked())
			agents.add(boardController.getAgents().get(Integer.valueOf(agent)));
		agent = fecthAgentIdInPos(new Pair<Integer, Integer>(pos.getKey() + 1, pos.getValue()));
		if (!agent.isEmpty() && !boardController.getAgents().get(Integer.valueOf(agent)).getBlocked())
			agents.add(boardController.getAgents().get(Integer.valueOf(agent)));
		agent = fecthAgentIdInPos(new Pair<Integer, Integer>(pos.getKey(), pos.getValue() - 1));
		if (!agent.isEmpty() && !boardController.getAgents().get(Integer.valueOf(agent)).getBlocked())
			agents.add(boardController.getAgents().get(Integer.valueOf(agent)));
		agent = fecthAgentIdInPos(new Pair<Integer, Integer>(pos.getKey(), pos.getValue() + 1));
		if (!agent.isEmpty() && !boardController.getAgents().get(Integer.valueOf(agent)).getBlocked())
			agents.add(boardController.getAgents().get(Integer.valueOf(agent)));
		return agents.get(r.nextInt(agents.size()));
	}

	public GridPane getGridPane() {
		return gridPane;
	}

	public Integer getNbAgent() {
		return nbAgent;
	}

	public Mailbox getMailbox() {
		return mailbox;
	}

	public MailBoxV2 getMailBoxV2() {
		return mailBoxV2;
	}

	public void setBoardController(BoardController boardController) {
		this.boardController = boardController;
	}
}
