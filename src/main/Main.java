package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import controller.BoardController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Pair;
import model.Agent;
import model.Message;
import model.TypeMessage;
import view.Board;

public class Main extends Application implements Observer {

	private Board board;
	private BoardController controller;

	public Main() {
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Jeu");
		HBox hBox = new HBox();
		System.out.println("Type de programme :");
		System.out.println("1 - Résolution basique 2 agents");
		System.out.println("2 - Déplacement simultané de n agent(s)");
		System.out.println("3 - Déplacement agent par agent");
		Scanner scan = new Scanner(System.in);
		String mode = scan.nextLine();
		Boolean supportedOption = false;
		Integer nbAgent = 2;
		while (!supportedOption) {
			if ("1".equals(mode)) {
				board = new Board(nbAgent);
				board.draw();
				controller = new BoardController(board);
				supportedOption = true;
				initTest();
			} else if ("2".equals(mode)) {
				System.out.println("Nombre d'agent :");
				nbAgent = Integer.valueOf(scan.nextLine());
				board = new Board(nbAgent);
				board.draw();
				controller = new BoardController(board);
				supportedOption = true;
				initAgents(nbAgent, true);
			} else if ("3".equals(mode)) {
				System.out.println("Nombre d'agent :");
				nbAgent = Integer.valueOf(scan.nextLine());
				board = new Board(nbAgent);
				board.draw();
				controller = new BoardController(board);
				supportedOption = true;
				initAgents(nbAgent, false);
				board.getMailBoxV2().postMessage(1, new Message(0, TypeMessage.RESOLVE));
			} else {
				System.out.println("Option non supportée");
				System.out.println("Type de programme :");
				System.out.println("1 - Résolution basique 2 agents");
				System.out.println("2 - Déplacement simultané de n agent(s)");
				System.out.println("3 - Déplacement agent par agent");
			}
		}

		GridPane result = drawFinishGrid();

		hBox.getChildren().addAll(board.getGridPane(), result);
		HBox.setMargin(result, new Insets(0d, 4d, 0d, 4d));
		HBox.setMargin(board.getGridPane(), new Insets(0d, 4d, 0d, 4d));
		Scene scene = new Scene(hBox, 600, 250);
		primaryStage.setScene(scene);
		primaryStage.show();

		Thread t;
		for (Entry<Integer, Agent> entry : controller.getAgents().entrySet()) {
			controller.updateLabel(entry.getValue(), "#000000", entry.getValue().getActualPos());
			entry.getValue().draw();
			t = new Thread(entry.getValue());
			t.start();
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		Agent a = (Agent) o;
		Pair<Integer, Integer> posTo = (Pair<Integer, Integer>) arg;
		String color;
		if (a.getActualPos().equals(a.getFinalPos()))
			color = "#00FF00";
		else
			color = "#000000";
		Platform.runLater(() -> {
			controller.updateLabel(a, color, posTo);
		});
	}

	private void initTest() {
		Agent agent1 = new Agent(0, board, new Pair<Integer, Integer>(0, 0), new Pair<Integer, Integer>(2, 0), true);
		Agent agent2 = new Agent(1, board, new Pair<Integer, Integer>(1, 0), null, true);
		agent1.addObserver(this);
		agent2.addObserver(this);
		controller.addAgent(agent1);
		controller.addAgent(agent2);
		controller.applyLabel(agent1, "#000000");
		controller.applyLabel(agent2, "#000000");
	}

	public void initAgents(Integer nbAgents, Boolean allMoved) {
		if (nbAgents > Board.length * Board.height - 1) {
			System.out.println("Can't initialize - Cause : Number of agents > Number of tiles");
			return;
		}
		Agent a;
		List<Pair<Integer, Integer>> startPositions = new ArrayList<>();
		List<Pair<Integer, Integer>> finalPositions = new ArrayList<>();
		Pair<Integer, Integer> pStart, pFinal;
		for (int i = 0; i < nbAgents; i++) {
			// Start position
			pStart = controller.generateRandomPos(startPositions);

			// Final position
			pFinal = controller.generateRandomPos(finalPositions);
			finalPositions.add(pFinal);
			a = new Agent(i, board, pStart, pFinal, allMoved);
			controller.applyLabel(a, "#ffffff");
			a.addObserver(this);
			controller.addAgent(a);
		}
	}

	public GridPane drawFinishGrid() {
		GridPane result = new GridPane();
		for (int i = 0; i < Board.length; i++) {
			for (int j = 0; j < Board.height; j++) {
				StackPane pane = board.getRectangle("", Color.WHITE);
				result.add(pane, i, j);
			}
		}
		Agent a;
		Label r;
		Rectangle rec;
		for (Entry<Integer, Agent> entry : controller.getAgents().entrySet()) {
			if (entry.getValue().getFinalPos() == null)
				continue;
			a = entry.getValue();
			r = (Label) ((StackPane) result.getChildren()
					.get(a.getFinalPos().getKey() * Board.length + a.getFinalPos().getValue())).getChildren().get(1);
			r.setText("" + a.getId());
			r.setTextFill(Color.web("#000000"));
			rec = (Rectangle) ((StackPane) result.getChildren()
					.get(a.getFinalPos().getKey() * Board.length + a.getFinalPos().getValue())).getChildren().get(0);
			rec.setFill(a.getColor());
		}
		return result;
	}
}
