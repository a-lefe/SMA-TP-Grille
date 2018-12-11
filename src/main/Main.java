package main;

import javafx.application.Application;
import javafx.scene.Scene;
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
		primaryStage.setTitle("LE JEU PUTAIN");

		Board board = new Board();
		board.draw();

		Scene scene = new Scene(board.gridPane, 500, 500);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

}
