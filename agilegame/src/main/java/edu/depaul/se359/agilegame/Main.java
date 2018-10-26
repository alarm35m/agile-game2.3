package edu.depaul.se359.agilegame;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Random;

public class Main extends Application {

    final int xDimension = 8; // creating a 6x6 maps
    final int yDimension = 4;
    final int scale = 100; // Scale everything by 90. You can experiment here.
    final int playerScale = 50;
    final int diceScale = 75;
    Pane root;
    Scene scene;
    GameBoard gameBoard = GameBoard.getInstance(xDimension, yDimension);
    int[][] gameMap;
    Image pOneImage, pTwoImage;
    ImageView pOneImageView, pTwoImageView;
    
    Image diceImage;
    ImageView diceImageView;
    
    Player playerOne, playerTwo;
    Button diceRoll;
    Label diceLabel, playerOneScore, playerTwoScore;
    String[] team1questions;
    String[] team2questions;
    //temporary buttons and variables
    Button tmpT1Button;
    Button tmpT2Button;
    Boolean questionDisplay = false;
    int t1count = 0;
    int t2count = 0;
    //display question stack pane
    StackPane stack;


    @Override
    public void start(Stage primaryStage) throws Exception{
        root = new AnchorPane();
        scene = new Scene(root, 1000, 450);
        //Parent root = FXMLLoader.load(getClass().getResource("game.fxml"));
        primaryStage.setTitle("Agile Game");
        primaryStage.setScene(scene);
        primaryStage.show();

        gameMap = gameBoard.getMap();
        drawMap();
        gameBoard.displayMap();

        // adds a button to dice roll player 1
        diceRoll = new Button("Roll");
        diceRoll.setTranslateX(850);
        diceRoll.setTranslateY(100);
        root.getChildren().add(diceRoll);

        // adds a text box for dice player 1
        diceLabel = new Label("Roll: ");
        diceLabel.setTranslateX(850);
        diceLabel.setTranslateY(150);
        root.getChildren().add(diceLabel);

        playerOneScore = new Label("Player One: ");
        playerOneScore.setTranslateX(10);
        playerOneScore.setTranslateY(415);
        root.getChildren().add(playerOneScore);

        playerTwoScore = new Label("Player Two: ");
        playerTwoScore.setTranslateX(710);
        playerTwoScore.setTranslateY(415);
        root.getChildren().add(playerTwoScore);


        // initialize arrays with questions
        team1questions = new String[] {"p1", "p2", "p3", "p4", "p5"};
        team2questions = new String[] { "2p1", "2p2", "2p3", "2p4", "2p5"};

        // temporary testing for printing questions to console
        for(String x : team1questions){
            System.out.println("Team 1 Question: " + x);
        }
        for(String y : team2questions){
            System.out.println("Team 2 Question: " + y);
        }

        // adds two temporary buttons to display questions for player 1 and player 2
        tmpT1Button = new Button("Team 1 Button");
        tmpT1Button.setTranslateX(850);
        tmpT1Button.setTranslateY(200);
        root.getChildren().add(tmpT1Button);
        tmpT2Button = new Button("Team 2 Button");
        tmpT2Button.setTranslateX(850);
        tmpT2Button.setTranslateY(250);
        root.getChildren().add(tmpT2Button);

        // places the players images
        placePlayerOne();
        placePlayerTwo();

        // loads images
        loadPlayerOneImage();
        loadPlayerTwoImage();

        // set current images (ImageView) to the Pane
        root.getChildren().add(pOneImageView);
        root.getChildren().add(pTwoImageView);
        
        //rolls dice if the button is pushed
        diceRoll.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e) {
                rollDice();
            }
        });

        //handler for t1button
        tmpT1Button.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){displayQuestion(team1questions, t1count); }
        });

        //handler for t2button
        tmpT2Button.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){ displayQuestion(team2questions, t2count); }
        });


    }

    public void displayQuestion(String[] questions, int count){
        if(questionDisplay == false){
            Rectangle rect = new Rectangle(xDimension, yDimension, 8*scale, 4*scale); //messed with scale of rect a bit
            rect.setStroke(Color.BLACK); //black outline
            rect.setFill(Color.WHITE);
            String question;
            if(count >= questions.length){
                question = "Error, not enough questions";
            }
            else {
                question = questions[count];
            }
            if(t1count == count){
                t1count++;
            }
            else if(t2count == count){
                t2count++;
            }
            Text text = new Text(10, 50, question);
            text.setFont(new Font(30));
            stack = new StackPane();
            stack.getChildren().addAll(rect, text);
            root.getChildren().add(stack);
            questionDisplay = true;
        }
        else{
            root.getChildren().remove(stack);
            questionDisplay = false;
        }
    }

    public int rollDice(){
        Random rand = new Random();
        int randInt = rand.nextInt(6) + 1;

        diceLabel.setText("Roll: " + randInt);
        loadDiceRollImage(randInt);
        
        root.getChildren().add(diceImageView);

        return randInt;

    }

    public void drawMap() {

        for(int x = 0; x < xDimension; x++){
            for(int y = 0; y < yDimension; y++){
                Rectangle rect = new Rectangle(x*scale,y*scale,scale,scale);
                rect.setStroke(Color.BLACK); // We want the black outline
                if(x == 0 && y == 0){
                    rect.setFill(Color.GREEN); // fills the board with random colors
                }
                else if(x == 7 && y == 3){
                    rect.setFill(Color.RED); // fills the board with random colors
                }
                else{
                    rect.setFill(Color.color(Math.random(), Math.random(), Math.random())); // fills the board with random colors
                }
                root.getChildren().add(rect); // Add to the node tree in the pane
            }
        }
    }

    public void placePlayerOne(){
        playerOne = new Player();
    }
    public void placePlayerTwo(){
        playerTwo = new Player();
    }

    public void loadPlayerOneImage(){
        pOneImage = new Image("/img/coolEmoji.png", playerScale, playerScale, true, true);
        pOneImageView = new ImageView(pOneImage);
        pOneImageView.setX(playerOne.getPlayerLocation().x + playerScale/2);
        pOneImageView.setY(playerOne.getPlayerLocation().y + 0);
    }

    public void loadPlayerTwoImage(){
        pTwoImage = new Image("/img/hungryEmoji.png", playerScale, playerScale, true, true);
        pTwoImageView = new ImageView(pTwoImage);
        pTwoImageView.setX(playerTwo.getPlayerLocation().x + playerScale/2);
        pTwoImageView.setY(playerTwo.getPlayerLocation().y + playerScale);
    }
    
    public void loadDiceRollImage(int rollNumber){
        diceImage = new Image("/img/" + rollNumber + ".png", diceScale, diceScale, true, true);
        diceImageView = new ImageView(diceImage);
        diceImageView.setX(900);
        diceImageView.setY(100);

    }


    public static void main(String[] args) {
        launch(args);
    }
}