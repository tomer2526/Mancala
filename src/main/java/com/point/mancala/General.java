package com.point.mancala;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static com.point.mancala.GameType.*;

public class General extends UIUX {
    protected static GameType GAME_TYPE;
    // The name of the Game
    static protected String GAME_TYPE_NAME;

    static protected GameDifficulty CPU1_GD = GameDifficulty.hard;
    static protected GameDifficulty CPU2_GD = GameDifficulty.hard;
    //protected final boolean developmentMode = true;
    protected static final List<Color> COLORS = List.of(Color.GREEN, Color.RED, Color.BLUE, Color.ORANGE);
    HashMap<Short, Hole> HOLES = new HashMap<>(14); // hasMap that contain the index of the hole in the key and a list of the hole data

    protected final short P1_MAIN_HOLE_KEY = -1;
    protected final short P2_MAIN_HOLE_KEY = 12;
    protected Hole P1_MAIN_HOLE;
    protected Hole P2_MAIN_HOLE;

    // The range of player1 holeKeys that can be selected
    protected static final short[] P1_holesRange = {0,5};
    // The range of player2 holeKeys that can be selected
    protected static final short[] P2_holesRange = {6 ,11};


    public void startGame(Stage primaryStage, GameType type) throws IOException {
        GAME_TYPE = type;
        if(type == PVP)
            GAME_TYPE_NAME = "Player VS Player";
        else if (type == PVC)
            GAME_TYPE_NAME = "Player VS CPU";
        else
            GAME_TYPE_NAME = "CPU VS CPU";

        logAction("start game type: " + type, 2);

        start_game_sound();
        FXMLLoader fxmlLoader = new FXMLLoader(Game.class.getResource("game.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle(GAME_TYPE_NAME);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public void startGame(Stage primaryStage, GameType type, GameDifficulty CPU2_gd) throws IOException {
        CPU1_GD = GameDifficulty.hard;
        CPU2_GD = CPU2_gd;
        startGame(primaryStage, type);
    }
    public void startGame(Stage primaryStage, GameType type, GameDifficulty CPU1_gd, GameDifficulty CPU2_gd) throws IOException {
        CPU1_GD = CPU1_gd;
        CPU2_GD = CPU2_gd;
        startGame(primaryStage, type);
    }

    public static HashMap<Short, BasicHole> deepCopyHoleHashMap(HashMap<Short, Hole> original) {
        HashMap<Short, BasicHole> copy = new HashMap<>(original.size());

        for (short key = -1; key <= 12; key++) {
            Hole originalHole = original.get(key);
            //BasicHole copiedHole = ; // Using copy constructor
            copy.put(key, new BasicHole(originalHole));
        }
        return copy;
    }

    // O(1) - alwaze 12 values
    public static HashMap<Short, BasicHole> deepCopyBasicHoleHashMap(HashMap<Short, BasicHole> original) {
        HashMap<Short, BasicHole> copy = new HashMap<>(original.size());

        for (short key = -1; key <= 12; key++) {
            BasicHole originalHole = original.get(key);
            //BasicHole copiedHole = ; // Using copy constructor
            copy.put(key, new BasicHole(originalHole));
        }
        return copy;
    }


    /**return the number of balls in the provided hole
     */
    protected short getHoleBallCount(short holeKey)
    {
        return HOLES.get(holeKey).ballCount;
    }

    /**return the number of balls in the provided hole from the emulatedHoles
     */
    protected short getHoleBallCount(short holeKey, HashMap<Short, BasicHole> emulatedHoles)
    {
        return emulatedHoles.get(holeKey).ballCount;
    }


    protected GridPane getGridFromHoleKey(short holeKey){
            return HOLES.get(holeKey).grid;
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
