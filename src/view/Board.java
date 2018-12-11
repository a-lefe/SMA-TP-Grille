package view;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Board {

	public GridPane gridPane;
	public int length, height;

	public Board() {
		gridPane = new GridPane();
	}

	public void draw() {
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				StackPane pane = getRectangle("");
				gridPane.add(pane, i, j);
			}
		}
	}

	private StackPane getRectangle(String content) {
		Label label = new Label(content);
		Rectangle rectangle = new Rectangle();
		rectangle.setWidth(length);
		rectangle.setHeight(height);
		rectangle.setStroke(Color.BLACK);
		rectangle.setFill(Color.WHITE);
		return new StackPane(rectangle, label);
	}

}
