package main;

import java.util.Map.Entry;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import controller.BoardController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Pair;
import model.Agent;
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
		Scanner scan = new Scanner(System.in);
		System.out.println("Nombre d'agent :");
		Integer nbAgent = Integer.valueOf(scan.nextLine());
		board = new Board(nbAgent);
		board.draw();

		controller = new BoardController(board);
		initAgents(nbAgent);
		//initTest();
		
		Scene scene = new Scene(board.getGridPane(), 500, 500);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		Thread t;
		for(Entry<Integer, Agent> entry : controller.getAgents().entrySet()){
			t = new Thread(entry.getValue());
			t.start();
		}
	}
	
	@Override
	public void update(Observable o, Object arg) {
		Agent a = (Agent) o;
		Pair<Integer, Integer> posTo = (Pair<Integer, Integer>) arg;
		String color;
		if(a.getActualPos().equals(a.getFinalPos()))
			color = "#00FF00";
		else
			color = "#000000";
		System.out.println("Update agent " + a.getId());
		Platform.runLater(() -> {controller.updateLabel(a, color, posTo);});
	}

	private void initTest() {
		Agent agent1 = new Agent(0, board, new Pair<Integer, Integer>(0, 0), new Pair<Integer, Integer>(2, 0));
		Agent agent2 = new Agent(1, board, new Pair<Integer, Integer>(1, 0), null);
		agent1.addObserver(this);
		agent2.addObserver(this);
		controller.addAgent(agent1);
		controller.addAgent(agent2);
		controller.applyLabel(agent1, "#000000");
		controller.applyLabel(agent2, "#000000");
	}
	
	public void initAgents(Integer nbAgents) {
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
			a = new Agent(i, board, pStart, pFinal);
			controller.applyLabel(a, "#ffffff");
			a.addObserver(this);
			controller.addAgent(a);
		}
	}
}
