package com.point.mancala;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.point.mancala.GameType.*;

public class General extends UIUX {
    protected static GameType GAME_TYPE;
    // The name of the Game
    protected static String GAME_TYPE_NAME;
    protected final boolean developmentMode = true;
    protected static final List<Color> COLORS = List.of(Color.GREEN, Color.RED, Color.BLUE, Color.ORANGE);
    //ArrayList<Short> holesBallsCount = new ArrayList<Short>(12); // a list that contain the amount of balls in each hole (include the mains)
    HashMap<Short, ArrayList<Object>> HOLES = new HashMap<>(14); // hasMap that contain the index of the hole in the key and a list of the hole data
    //example: holeIndex: {(the amount of balls), (gridHole object), (AnchorPane object) (Rectangle shape) (ball count label object) }
    // index -1 = P1 main hole, 12 = P2 main hole
    protected final short P1_MAIN_HOLE_KEY = -1;
    protected final short P2_MAIN_HOLE_KEY = 12;
    protected final short BALLS_COUNT_INDEX = 0;
    protected final short GRID_INDEX = 1;
    protected final short ANCHORPANE_INDEX = 2;
    protected final short RECTANGLE_INDEX = 3;
    protected final short LABLE_INDEX = 4;

    // The range of player1 holeKeys that can be selected
    protected static final short[] P1_holesRange = {0,5};
    // The range of player2 holeKeys that can be selected
    protected static final short[] P2_holesRange = {6 ,11};


    public static void startPVP(Stage primaryStage) throws IOException {
        GAME_TYPE = PVP;
        GAME_TYPE_NAME = "Player VS Player";
        logAction("start PVP game", 2);
        startGame(primaryStage);
    }
    public static void startPVC(Stage primaryStage) throws IOException {
        GAME_TYPE = PVC;
        GAME_TYPE_NAME = "Player VS CPU";
        logAction("start PVC game", 2);
        startGame(primaryStage);
    }
    public static void startCVC(Stage primaryStage) throws IOException {
        GAME_TYPE = CVC;
        GAME_TYPE_NAME = "CPU VS CPU";
        logAction("start CVC game", 2);
        startGame(primaryStage);
    }

    private static void startGame(Stage primaryStage) throws IOException {
        playSound("src/main/resources/assets/sound effects/start game sound.wav");
        FXMLLoader fxmlLoader = new FXMLLoader(Game.class.getResource("game.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle(GAME_TYPE_NAME);
        primaryStage.setScene(scene);
        primaryStage.show();
    }



    //return the amount of balls int the provided hole
    protected short getHoleBallCount(short holeKey)
    {
        //return hole.getChildren().size() - BALLS_START_INDEX;
        return (short) HOLES.get(holeKey).getFirst(); // BALLS_COUNT_INDEX -> firstIndex
    }




    // print log message
    // importance: the level of importance of this log.
    // if it not that important to print this log (like show that a hole is highlighted) it will be a higher number
    // 1 = the most important and 3 = is not that important
    public static void logAction(String message, int importance)
    {
        if(importance < 3)
        {
            // print just yhe important log
            System.out.println(message);
        }
    }
    public static void logAction(int message, int importance)
    {
        if(importance < 3)
        {
            // print just yhe important log
            System.out.println(message);
        }
    }
    public static void logAction(String message)
    {
        logAction(message, 1);
    }
    public static void logAction(int message)
    {
        logAction(String.valueOf(message), 1);
    }
    public static void logAction(boolean message)
    {
        logAction(String.valueOf(message), 2);
    }
}
