package view;

import java.util.Observable;
import java.util.Observer;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;
import model.Mailbox;

public class Board implements Observer {

	private GridPane gridPane;

	public static int length, height;

	private String[][] positions;
	private Integer nbAgent;
	private Mailbox mailbox;

	public Board(Integer nbAgent) {
		this.nbAgent = nbAgent;
		this.mailbox = new Mailbox(nbAgent);
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

	public synchronized boolean isFree(Pair<Integer, Integer> pos) {
		Integer row = pos.getKey();
		Integer column = pos.getValue();
		Label r = (Label) ((StackPane) gridPane.getChildren().get(row * height + column)).getChildren().get(1);
		if (r.getText().equals(""))
			return true;
		return false;

	}

	public GridPane getGridPane() {
		return gridPane;
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}

	public Integer getNbAgent() {
		return nbAgent;
	}

	public Mailbox getMailbox() {
		return mailbox;
	}
}
