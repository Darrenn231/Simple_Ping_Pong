package application;

import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
	
	private static final int height = 600, width = 1000, PLAYER_HEIGHT = 80, PLAYER_WIDTH = 15;
	private static final double BALL_R = 15;
	private double ballYSpeed = 1;
	private double ballXSpeed = 1, P1YPos = height / 2, ballXPos = width / 2, ballYPos = height / 2,P2XPos = width - PLAYER_WIDTH, P2YPos = height / 2;
	private int P1XPos = 0, scoreP1 = 0, scoreP2 = 0;
	private boolean start;
	
	public void start(Stage stage) throws Exception {
		stage.setTitle("Simple PingPong");
		
		Canvas canvas = new Canvas(width, height);
		GraphicsContext graphCon = canvas.getGraphicsContext2D();
	
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10), e -> StartGame(graphCon)));
		timeline.setCycleCount(Timeline.INDEFINITE);
		
		canvas.setOnMouseClicked(e ->  start = true);
		canvas.setOnMouseMoved(e ->  P1YPos  = e.getY());
		timeline.play();
		stage.setScene(new Scene(new StackPane(canvas)));
		stage.show();
		
	}

	private void StartGame(GraphicsContext graphCon) {
		
		graphCon.setFill(Color.DARKBLUE);
		graphCon.fillRect(0, 0, width, height);
		graphCon.setFill(Color.WHITE);
		graphCon.setFont(Font.font(25));
		
		if(start == true) {
			ballXPos+=ballXSpeed;
			ballYPos+=ballYSpeed;
			if(ballXPos < width - width  / 4) {
				P2YPos = ballYPos - PLAYER_HEIGHT / 2;
			}  else {
				P2YPos =  ballYPos > P2YPos + PLAYER_HEIGHT / 2 ?P2YPos += 6: P2YPos - 6;
			}
			graphCon.fillOval(ballXPos, ballYPos, BALL_R, BALL_R);
			
		} else {
			
			graphCon.setStroke(Color.WHITE);
			graphCon.setTextAlign(TextAlignment.CENTER);
			graphCon.strokeText("Click to Start", width / 2, height / 2);
			
			ballXPos = width / 2;
			ballYPos = height / 2;
			
			ballXSpeed = new Random().nextInt() == 0 ? 1: -1;
			ballYSpeed = new Random().nextInt() == 0 ? 1: -1;
		}
		
		if(ballYPos < 0 || ballYPos > height) {
			ballYSpeed *=-1;
		}
		
		if(ballXPos < P1XPos - PLAYER_WIDTH) {
			scoreP2++;
			start = false;
		}
		
		if(ballXPos > P2XPos + PLAYER_WIDTH) {  
			scoreP1++;
			start = false;
		}
	
		if( ((ballXPos + BALL_R > P2XPos) && ballYPos >= P2YPos && ballYPos <= P2YPos + PLAYER_HEIGHT) || ((ballXPos < P1XPos + PLAYER_WIDTH) && ballYPos >= P1YPos && ballYPos <= P1YPos + PLAYER_HEIGHT)) {
				ballXSpeed += 1 * Math.signum(ballXSpeed);
				ballYSpeed += 1 * Math.signum(ballYSpeed);
				ballXSpeed *= -1;
				ballYSpeed *= -1;
			}
		
		if(scoreP1 >= 5 || scoreP2 >= 5) {
			start = false;
			scoreP1 = 0;
			scoreP2 = 0;
		
		}
		graphCon.fillText(scoreP1 + "\t\t\t\t\t" + scoreP2, width / 2, 100);
		graphCon.fillRect(P2XPos, P2YPos, PLAYER_WIDTH, PLAYER_HEIGHT);
		graphCon.fillRect(P1XPos, P1YPos, PLAYER_WIDTH, PLAYER_HEIGHT);
	}
		public static void main(String[] args) {
		launch(args);
		}
}