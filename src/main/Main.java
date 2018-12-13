package main;

import controller.BoardController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import model.Agent;
import view.Board;

public class Main extends Application {

	public Main() {
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Jeu");
		Integer nbAgent = 2;
		Board board = new Board(nbAgent);
		board.draw();

		Scene scene = new Scene(board.getGridPane(), 500, 500);
		primaryStage.setScene(scene);
		primaryStage.show();

		BoardController controller = new BoardController(board, nbAgent);
		// controller.initAgents(2);

		Agent agent1 = new Agent(1, board, new Pair<Integer, Integer>(0, 0), new Pair<Integer, Integer>(0, 2));
		Agent agent2 = new Agent(2, board, new Pair<Integer, Integer>(0, 1), new Pair<Integer, Integer>(4, 4));
		agent1.addObserver(board);
		agent2.addObserver(board);
		controller.addAgent(agent1);
		controller.addAgent(agent2);
		Label r = (Label) ((StackPane) board.getGridPane().getChildren().get(0)).getChildren().get(1);
		r.setText("1");
		Label r2 = (Label) ((StackPane) board.getGridPane().getChildren().get(1)).getChildren().get(1);
		r2.setText("2");

	}
}
