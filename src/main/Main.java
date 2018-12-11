package main;

import javafx.application.Application;
import javafx.stage.Stage;
import view.Board;

public class Main extends Application {

	public Main() {
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Board board = new Board();
		board.draw();

	}

}
