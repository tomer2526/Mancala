package com.point.mancala;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.point.mancala.GameType.*;

public class General {
    protected static GameType GAME_TYPE;
    protected final boolean developmentMode = true;
    protected static final List<Color> COLORS = List.of(Color.GREEN, Color.RED, Color.BLUE, Color.ORANGE);
    //ArrayList<Short> holesBallsCount = new ArrayList<Short>(12); // a list that contain the amount of balls in each hole (include the mains)
    HashMap<Short, ArrayList<Object>> hols; // hasMap that contain the index of the hole in the key and a list of the hole data
    //example: holeIndex: {(the amount of balls), (gridHole object), (AnchorPane object) (ball count label object) }
    // index -1 = P1 main hole, 13 = P2 main hole
    protected short p1_balls_holes = 0; // the amount of balls in P1 main hole
    protected short p2_balls_holes = 0; // the amount of balls in P2 main hole

    public static void startPVP(Stage primaryStage) throws IOException {
        GAME_TYPE = PVP;
        logAction("start PVP game", 2);
        FXMLLoader fxmlLoader = new FXMLLoader(Game.class.getResource("game.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("PVP Game");
        //primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void startPVC(Stage primaryStage) throws IOException {
        GAME_TYPE = PVC;
        logAction("start PVC game", 2);
        FXMLLoader fxmlLoader = new FXMLLoader(Game.class.getResource("game.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("PVC Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void startCVC(Stage primaryStage) throws IOException {
        GAME_TYPE = CVC;
        logAction("start CVC game", 2);
        FXMLLoader fxmlLoader = new FXMLLoader(Game.class.getResource("game.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("CVC Game");
        primaryStage.setScene(scene);
        primaryStage.show();
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
