package com.point.mancala;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import static com.point.mancala.GameType.*;

//import static com.point.mancala.Game.startPVP;

public class GameController extends General implements Initializable {

    private static final short START_BALL_COUNT = 4; // The amount of balls in each hole in the start of the game
    final static int MAX_ROWS_AND_COLS_IN_GRID = 15; // the max rows and columns in every grid (16-1)
    private static  final char GRID_ROWS_NUM = 4; // nums of rows for each grid hols (except the player main hole grid) - min 2 rows
    private static  final char GRID_COLUMNS_NUM = 4; // nums of columns for each grid hols (except the player main hole grid) - min 2 columns
    protected int[][] gridArr = {{2,1},{2,2},{1,2},{1,1}, {1,0}, {2,0}, {3,0},{3, 1}, {3, 2}, {3, 3}, {2, 3}, {1, 3}, {0,3},{0, 2}, {0, 1}, {0,0}}; // this arr store the location of each of the ball in the grid, for example, the first ball should be located in 1,1 (in the center of the grid)
    private final Animations animation = new Animations();
    private final Animations playerSwing = new Animations();
    protected AnchorPane[] holes;
    protected boolean canPlay = true; // Indicate if any player can play, when loading time is over it will be trues
    protected boolean isGameOver = false; // if a player win this var will be true;
    protected boolean pause = false; // if the user pause the game;

    protected static boolean gameTurn = true; // Player 1 turn = true, player 2 false
    protected short lastHole; // The last hole that a ball got into

    protected final Duration dellay = Duration.millis(500);

    // if the user pause the game and the cpu select hole after the pause, it will get into the waitingQueue
    // and will be selected  and will be selected after the user resume the game
    // if waitingQueue = -2 it means that there is nothing in the queue
    protected short waitingQueue = -2;

    @FXML
    private BorderPane win_window;
    @FXML
    private Label game_type;
    @FXML
    private Label new_game_button;
    @FXML
    private VBox game_board;
    @FXML
    private HBox holes_row1;
    @FXML
    private HBox holes_row2;
    @FXML
    private AnchorPane player1_hole;
    @FXML
    private AnchorPane player2_hole;
    @FXML
    private GridPane ballGridP1;
    @FXML
    private GridPane ballGridP2;
    @FXML
    private Label p1_score;
    @FXML
    private Label p2_score;
    @FXML
    private Label player1_name;
    @FXML
    private Label player2_name;
    @FXML
    private Label game_turn_label;
    @FXML
    private Label winner_player;
    @FXML
    private Button pausePlay;



    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        ObservableList<Node> row1_Children = holes_row1.getChildren();
        ObservableList<Node> row2_Children = holes_row2.getChildren();
        //fade buttons
        holes = new AnchorPane[]{(AnchorPane) row1_Children.get(0), (AnchorPane) row1_Children.get(1), (AnchorPane) row1_Children.get(2), (AnchorPane) row1_Children.get(3), (AnchorPane) row1_Children.get(4), (AnchorPane) row1_Children.get(5), (AnchorPane) row2_Children.get(0), (AnchorPane) row2_Children.get(1), (AnchorPane) row2_Children.get(2), (AnchorPane) row2_Children.get(3), (AnchorPane) row2_Children.get(4), (AnchorPane) row2_Children.get(5)};
        game_type.setText(GAME_TYPE_NAME);

        //gridArr = spiralIndoxing();

        // create the balls for each hole
        short i;
        Ball b;

        // add P1 and P2 main hole to the hashMap
        HOLES.put((short) -1, new ArrayList<>(List.of((short) 0, ballGridP1, player1_hole, player1_hole.getChildren().getFirst(), p1_score)));
        HOLES.put((short) 12, new ArrayList<>(List.of((short) 0, ballGridP2, player2_hole, player2_hole.getChildren().getFirst(), p2_score)));
        GridPane grid;
        Label label;

        if (true) {
            for (short holeKey = 0; holeKey < 12; holeKey++) {
                AnchorPane holeAnchorPane = holes[holeKey];
                grid = (GridPane) holeAnchorPane.getChildren().getLast();
                label = (Label) holeAnchorPane.getChildren().get(1);

                // holeIndex: {(the amount of balls), (gridHole object), (AnchorPane object), (Rectangle shape), (ball count label object) }
                ArrayList<Object> values = new ArrayList<>(List.of(START_BALL_COUNT, grid, holeAnchorPane, holeAnchorPane.getChildren().getFirst(), label));
                HOLES.put(holeKey, values);

                //holesDict.put(holeID, new Hole(grid, START_BALL_COUNT));
                for (i = 0; i < START_BALL_COUNT; i++) {
                    try {
                        b = new Ball(grid, COLORS.get(i), i);
                        b.setId("ball-" + grid.getId() + "-" + i);
                        addBallToGrid(grid, b, i);

                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        } else {
            // test with balls in hols that are in the list: insertBalls_holes
            ArrayList<Integer> insertBalls_holes = new ArrayList<>(List.of(1, 3, 8));

            for (short holeKey = 0; holeKey < 12; holeKey++) {
                AnchorPane holeAnchorPane = holes[holeKey];
                grid = (GridPane) holeAnchorPane.getChildren().getLast();
                label = (Label) holeAnchorPane.getChildren().get(1);

                short ballsAmount = (short) (insertBalls_holes.contains((int) holeKey) ? 4 : 0);

                // holeIndex: {(the amount of balls), (gridHole object), (AnchorPane object), (Rectangle shape), (ball count label object) }
                ArrayList<Object> values = new ArrayList<>(List.of(ballsAmount, grid, holeAnchorPane, holeAnchorPane.getChildren().getFirst(), label));
                HOLES.put(holeKey, values);

                // update the label score (in UI)
                updateScore(holeKey, false);

                //holesDict.put(holeID, new Hole(grid, START_BALL_COUNT));
                for (i = 0; i < ballsAmount; i++) {
                    try {
                        b = new Ball(grid, COLORS.get(i), i);
                        b.setId("ball-" + grid.getId() + "-" + i);
                        addBallToGrid(grid, b, i);

                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

        }

        // CPU action (if the CPU turn and not PVP mode)
        if (GAME_TYPE == CVC) {
            CPUAction(gameTurn);
        }
    }

    protected void selectHole(short holeKey) {

        if (canPlay || !isGameOver) {
            if (pause) {
                waitingQueue = holeKey;
            } else {
                // The first item is - Rectangle hole_style we want just the balls witch is from index 1
                short ballsCount = getHoleBallCount(holeKey);
                short i;
                logAction("Hole" + holeKey + " selected", 2);
                boolean canClick; // if the hole can be pressed (if is the player turn)

                // check if is the player turn (if the player's row can be clicked new)
                if (gameTurn) {
                    canClick = (holeKey >= 0 && holeKey < 6);
                } else {
                    canClick = (holeKey >= 6 && holeKey < 12);
                }

                short nextHoleKey = holeKey;
                if (canClick && ballsCount > 0) {


                    canPlay = false;
                    //move every ball in the hole to the next holes
                    if (holeKey == 0) {
                        nextHoleKey = P1_MAIN_HOLE_KEY; // the next hole after p1 hole
                    } else if (holeKey == 11) {
                        nextHoleKey = P2_MAIN_HOLE_KEY; // the next hole after p2 hole
                    } else {
                        //if the holeIndex is in row2 we need to move forward (increase the index by 1) in the holes list
                        if (holeKey >= 6) {
                            nextHoleKey++;
                        } else {
                            //if the holeIndex is in row1 we need to move backwards (reduce the index by 1) in the holes list
                            nextHoleKey--;
                        }
                    }

                    for (i = 0; i < ballsCount; i++) {
                        moveBallTo(holeKey, nextHoleKey);
                        this.lastHole = nextHoleKey;

                        if (nextHoleKey == P1_MAIN_HOLE_KEY) {
                            nextHoleKey = 6; // the next hole after p1 hole
                        } else if (nextHoleKey == P2_MAIN_HOLE_KEY) {
                            nextHoleKey = 5; // the next hole after p2 hole
                        } else {
                            //if the holeIndex is in row2 we need to move forward (increase the index by 1) in the holes list
                            if (nextHoleKey >= 6 && nextHoleKey <= 11) {
                                nextHoleKey++;
                            } else {
                                //if the holeIndex is in row1 we need to move backwards (reduce the index by 1) in the holes list
                                nextHoleKey--;
                            }
                        }
                        logAction("Last hole: " + this.lastHole, 2);
                        //make delay
                    }


                    //if the last ball get into the player's hole, he will get another turn
                    if (gameTurn && (this.lastHole == P1_MAIN_HOLE_KEY)) {
                        if (isAllPlayerHolesEmpty(true)) {
                            changeTurn(true);
                        } else {
                            // player 1 get another turn
                            Animations sa = new Animations();
                            sa.scale(game_turn_label, 300, 1.1, false);
                        }
                    } else {
                        if (!gameTurn && (this.lastHole == P2_MAIN_HOLE_KEY)) {
                            if (isAllPlayerHolesEmpty(false)) {
                                changeTurn(false);
                            } else {
                                // Player 2 get another turn
                                Animations sa = new Animations();
                                sa.scale(game_turn_label, 300, 1.1, false);
                            }
                        } else {
                            int lastHoleBallCount = getHoleBallCount(this.lastHole);

                            // If the last ball get into an empty hole of
                            if (gameTurn && lastHoleBallCount == 1 && holeKey != P1_MAIN_HOLE_KEY && holeKey != P2_MAIN_HOLE_KEY) {
                                short parallelHoleKey = (short) (this.lastHole + 6);

                                // Take the parallel hole balls just if the last ball of player 1 end in his own hole and the parallel hole have at least 1 ball
                                // So if the parallelHoleId is grater then 11 it means that the last ball is not landed in the player 1 era
                                if (parallelHoleKey <= 11) {
                                    // get the parallel hole


                                    // Check if the parallel hole have at least 1 ball
                                    if (getHoleBallCount(parallelHoleKey) > 0) {
                                        logAction("Player 1 last ball got into an empty hole", 1);

                                        // Move all the balls from the parallel hole to player 1 hole
                                        short parallelHoleBallsCount = getHoleBallCount(parallelHoleKey);
                                        for (short j = 0; j < parallelHoleBallsCount; j++) {
                                            moveBallTo(parallelHoleKey, P1_MAIN_HOLE_KEY);
                                        }
                                        // Move the last ball from the last hole to player 1 hole
                                        moveBallTo(lastHole, P1_MAIN_HOLE_KEY);

                                        // Update parallelHole and lastHole hole score
                                        updateScore(parallelHoleKey, true);
                                        updateScore(lastHole, true);
                                        changeTurn(false);
                                    }
                                }

                                // If all player2 holes are empty player1 get another turn in order to finish the game
                                // If the ball end in an empty hole of player1, player2 should get the next turn
                                changeTurn(isAllPlayerHolesEmpty(true));

                            } else if (!gameTurn && (lastHoleBallCount == 1) && holeKey != P1_MAIN_HOLE_KEY && holeKey != P2_MAIN_HOLE_KEY) {

                                short parallelHoleId = (short) (this.lastHole - 6);

                                // take the parallel hole balls to the player's main hole just if the last ball of player 2 end in his own hole and the parallel hole have at least 1 ball
                                // so if the parallelHoleId is smaller than 0 it means that the last ball is not landed in the player 2 era
                                if (parallelHoleId >= 0) {
                                    // get the parallel hole
                                    //GridPane parallelHole = getHoleFromId(parallelHoleId);

                                    // check if the parallel hole have at least 1 ball
                                    if (getHoleBallCount(parallelHoleId) > 0) {
                                        logAction("Player 2 last ball got into an empty hole", 1);

                                        // move the balls from the parallel hole and the last hole to player 2 hole
                                        int parallelHoleBallsCount = getHoleBallCount(parallelHoleId);
                                        for (int j = 0; j < parallelHoleBallsCount; j++) {
                                            moveBallTo(parallelHoleId, P2_MAIN_HOLE_KEY);
                                        }
                                        // move the last ball from the last hole to player 1 hole
                                        moveBallTo(lastHole, P2_MAIN_HOLE_KEY);

                                        //update parallelHole and lastHole hole score
                                        updateScore(parallelHoleId, true);
                                        updateScore(this.lastHole, true);
                                    }
                                }

                                // If all player1 holes are empty, player2 get another turn in order to finish the game
                                // If the ball end in an empty hole of player 2, player 1 should get the next turn
                                changeTurn(!isAllPlayerHolesEmpty(true));

                            } else
                                changeTurn(!gameTurn);
                        }
                    }

                    // turn off the highlight of the hole
                    highlightHole(holeKey, Color.WHEAT, false);

                    //update the hole score
                    setBallAmountToHole(holeKey, (short) 0, true, false);

                    canPlay = true;
                }
                // CPU action (if the CPU turn and not PVP mode)
                if ((GAME_TYPE == PVC && !gameTurn) || GAME_TYPE == CVC) {
                    CPUAction(gameTurn);
                }
            }
        }
        else{
                logAction("canPlay variable is: " + canPlay + "isGameOver variable is: " + isGameOver + ".", 2);
            }
    }


    private void addBallToGrid(GridPane grid, Ball ball, int ballIndex){
        //int ballsCount = getHoleBallCount(grid);
        if( ((double)ballIndex/MAX_ROWS_AND_COLS_IN_GRID) > 1)
            ballIndex = (ballIndex - MAX_ROWS_AND_COLS_IN_GRID*(ballIndex/MAX_ROWS_AND_COLS_IN_GRID)); // if the index number is more than the MAX_ROWS_AND_COLS_IN_GRID, then add the ball to an existing index(start the index count again)
            //ballIndex = (int) (ballIndex - 16*(Math.floor(ballIndex/16)));

        int col = gridArr[ballIndex][0];
        int row = gridArr[ballIndex][1];
        grid.add(ball, col, row);

       logAction( ballIndex + "= " +col + ", " + row + "\n", 3);
    }

    public void highlightHole(short holeKey, Color color, boolean state){

        Rectangle hole_style = (Rectangle) HOLES.get(holeKey).get(RECTANGLE_INDEX);
        if(state)
        {
            hole_style.setStroke(color);
            hole_style.setStrokeWidth(5);
        }
        else {
            hole_style.setStrokeWidth(0);
        }

    }
    @FXML
    protected void new_game_button() throws Exception {

       logAction("new_game_button", 3);
        // Fade animation
        animation.fadeAnimation(new_game_button, 500, false);
        // Open new game window
        new Main().start(new Stage());

    }
    @FXML
    protected void new_game_button_hover(MouseEvent event) {
        Label label = (Label) event.getSource();
        label.setTextFill(Color.web("rgb(255, 255, 255)"));
    }
    @FXML
    protected void new_game_button_not_hover(MouseEvent event) {
        Label label = (Label) event.getSource();
        label.setTextFill(Color.web("#ffffffc2"));
    }
    @FXML
    protected void holeMouseHover(MouseEvent event) {
        //System.out.println("holePressedDown()");
        short holeKey = getHoleKey((AnchorPane) event.getSource());

        if(gameTurn) {
            if (holeKey >= 0 && holeKey < 6 && (GAME_TYPE != CVC)) {
                // If clickable
                highlightHole(holeKey,Color.WHEAT, true);
            }
            else {
                // If not clickable
                highlightHole(holeKey,Color.rgb(255,255,255,0.21), true);
            }
        }
        else {
            if (holeKey >= 6 && holeKey < 12 && (GAME_TYPE == PVP)) {
                // If not clickable
                highlightHole(holeKey,Color.WHEAT, true);
            }
            else {
                // If not clickable
                highlightHole(holeKey,Color.rgb(255,255,255,0.21), true);
            }
        }
    }
    @FXML
    protected void holeMouseNotHover(MouseEvent event) {
        highlightHole(getHoleKey((AnchorPane) event.getSource()),Color.WHEAT, false);
    }
    @FXML
    protected void holeClick(MouseEvent event) throws Exception {
        holeClicked(getHoleKey((AnchorPane) event.getSource()));

    }

    public void holeClicked(short holeIndex) throws IOException {
        if((GAME_TYPE == PVC && gameTurn) || GAME_TYPE == PVP)
            selectHole(holeIndex);
        else if (GAME_TYPE == CVC)
            logAction("Hole can't be selected in CVC mode.");
        else
            logAction("Hole can't be selected - it is not the user turn.");
    }



    // Set the player turn (how can play - player 1 or player 2)
    public void changeTurn(boolean turn) {
        logAction("Game turn = " + turn, 2);
        gameTurn = turn;

        if(gameTurn)
        {
            if(isAllPlayerHolesEmpty(true))
            {
                // If all the holes empty
                logAction("changeTurn: FOUND that all the holes empty in P1", 1);
                move_all_player_balls(false, P1_MAIN_HOLE_KEY);
                showWindow(getHoleBallCount(P1_MAIN_HOLE_KEY), getHoleBallCount(P2_MAIN_HOLE_KEY));
            }
            else {
                game_turn_label.setText("Player 1 turn");
                //playerSwing.translate(player1_name);
                playerSwing.fadeAnimation(player1_name, 300, false);
            }
        }
        else {
            if(isAllPlayerHolesEmpty(false))
            {
                // If all the holes empty
                logAction("changeTurn: FOUND that all the holes empty in P2", 1);
                move_all_player_balls(true, P2_MAIN_HOLE_KEY);
                showWindow(getHoleBallCount(P1_MAIN_HOLE_KEY), getHoleBallCount(P2_MAIN_HOLE_KEY));
            }
            else {
                game_turn_label.setText("Player 2 turn");
                //playerSwing.translate(player2_name);
                playerSwing.fadeAnimation(player2_name, 300, false);
            }
        }
        animation.fadeAnimation(game_turn_label, 300, false);
    }

    // return true if all the player's hols are empty
    protected boolean isAllPlayerHolesEmpty(boolean player)
    {
        if(player)
            for (short i = 0; i < 6; i++) {
                if (getHoleBallCount(i) > 0)
                    return false;
            }
        else
            for (short i = 6; i < 12; i++) {
                if (getHoleBallCount(i) > 0)
                    return false;
            }
        return true;
    }
    //Move all the balls of the source player to the target hole
    private void move_all_player_balls(boolean source_player, short target_hole) {

        short startHoleId;
        short endHoleId;

        if (source_player) {
            startHoleId = 0;
            endHoleId = 5;
        }
        else
        {
            startHoleId = 6;
            endHoleId = 11;
        }

        // Move all the balls of the source player to the target hole
        logAction("Move all the balls of the source player: " + (source_player? "P1":"P2") + ", to the target hole: " + target_hole, 1);
        short j, holeBallCount;
        for (short i = startHoleId; i <= endHoleId; i++) {
            holeBallCount = getHoleBallCount(i);
            if(holeBallCount > 0){
                for (j = 0; j <= holeBallCount; j++) {
                    //logAction("-----"+ i +", count " + getHoleBallCount(holes[i]), 1);
                    moveBallTo(i, target_hole);
                    //moveAllBallsInHoleWithTransition(holesGrid[i], target_hole);
                }
                updateScore(i, true);
            }
        }
    }
    //move one ball from the source hole to destination hole
    // with no animation, to move with animation use moveBallToWithTransition, but it is not work
    public void moveBallTo(short source, short dest)
    {
        short sourceBallCount = getHoleBallCount(source);
        short destBallCount = getHoleBallCount(dest);
        if(source == dest)
        {
            logAction("The source is equal to the destination! (it could be ok in case of hole with 14 or more balls)", 1);
        }
        else if(sourceBallCount > 0) { // If there is at least 1 ball in the hole

           logAction("Move ball from source: " + source + " (Ball count: " + sourceBallCount + ")" + ", to the destination: " + dest + " (Ball count: " + destBallCount+ ") ", 2);

            GridPane sourceGrid = (GridPane)HOLES.get(source).get(GRID_INDEX);
            GridPane destGrid = (GridPane)HOLES.get(dest).get(GRID_INDEX);

            // Move the ball to the destination hole
            Ball ball = (Ball) sourceGrid.getChildren().getLast();

            // Move the ball to the destination
            addBallToGrid(destGrid, ball, destBallCount);
            // Update the balls amount on the source
            setBallAmountToHole(source, (short)(sourceBallCount-1), true, false);

            // Update the destination hole score
            destBallCount++;
            setBallAmountToHole(dest, destBallCount,true, false);
        }
        else {
           logAction("There is no balls in source!", 1);
        }
    }

    // Set a new ball amount to holeKey
    protected void setBallAmountToHole(short holeKey,short ballsAmount , boolean updateUI, boolean updateUIWithAnimation){
        HOLES.get(holeKey).set(BALLS_COUNT_INDEX, ballsAmount);
        if(updateUI)
        {
         updateScore(holeKey, updateUIWithAnimation);
        }
    }
    private short getHoleKey(AnchorPane hole)
    {
        short id;
        if(hole == player1_hole)
            id = P1_MAIN_HOLE_KEY;
        else if(hole == player2_hole)
            id = P2_MAIN_HOLE_KEY;
        else {
            id = Short.parseShort(hole.getId().replace("hole", ""));
        }
       logAction("GetHoleKey (from Grid) func return: " + id, 3);
        return id;
    }
    private short getHoleKey(GridPane grid)
    {
        short id;
        if(grid == ballGridP1)
            id = P1_MAIN_HOLE_KEY;
        else if(grid == ballGridP2)
            id = P2_MAIN_HOLE_KEY;
        else {
            id = Short.parseShort(grid.getId().replace("ballGrid", ""));
        }
        logAction("GetHoleKey (from Grid) func return: " + id, 3);
        return id;
    }

    //update the hole Label score (UI only)
    public void updateScore(short holeKey, boolean withAnimation){
        Label hole_score = (Label) HOLES.get(holeKey).get(LABLE_INDEX);
        hole_score.setText(String.valueOf((short) HOLES.get(holeKey).getFirst()));

        if(withAnimation)
        {
            Animations fa = new Animations();
            fa.fadeAnimation(hole_score, 300, false);
        }
    }

    private void showWindow(short player1_score, short player2_score) {
        isGameOver = true;
        canPlay = false;
        game_board.setOpacity(0.5);
        game_board.setDisable(true);
        boolean is_player1_win = player1_score >= player2_score;
        boolean is_player2_win = player2_score>=player1_score;

        if(is_player1_win && is_player2_win)
        {
            winner_player.setText("Draw!");
        }
        else if(is_player1_win) {
            winner_player.setText("Player 1 win!");
            //swing the winner name
            Animations swing = new Animations();
            try {
                swing.translate(player1_name);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else
        {
            winner_player.setText("Player 2 win!");
            //swing the winner name
            Animations swing = new Animations();
            try {
                swing.translate(player2_name);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        win_window.setVisible(true);

        Animations pop = new Animations();
        pop.scale(win_window, 500, 0.5, false);

       //pop.rotate(win_window, 500,false);


    }

    // Close the win window in order to see the board
    @FXML
    protected void see_board(){
        game_board.setOpacity(1);
        game_board.setDisable(false);
        win_window.setVisible(false);
    }

    // Start a new game with the same setting (PVP or PVC or CVC)
    @FXML
    protected void start_new_game() throws Exception{
        startPVP(new Stage());
    }

    @FXML
    protected void pausePlay(){
        if(pause) {
            pausePlay.setId("pause");
            pause = false;
            if (waitingQueue != -2)
            {
                selectHole(waitingQueue);
                waitingQueue = -2;
            }
            else if(GAME_TYPE == CVC){
                CPUAction(gameTurn);
            }
        }
        else {
            pausePlay.setId("play");
            pause = true;
        }

    }



    // this function get the amount of balls in each hole and the CPU player (P1 = ture, P2 = false)
    // and return the hole index to be clicked.
    public void CPUAction(boolean CPU_player)  {

        if(!isGameOver) {
            logAction("Wait for CPU action");
            short[] holesRange = CPU_player ? P1_holesRange : P2_holesRange;
            short holeKey = holesRange[0];


            // get the holeKey with the max num of balls.
            short maxBallCount = getHoleBallCount(holeKey);
            short temp;
            for (short i = (short) (holesRange[0] + 1); i <= holesRange[1]; i++) {
                temp = getHoleBallCount(i);
                if (maxBallCount < temp) {
                    maxBallCount = temp;
                    holeKey = i;
                }

            }


            logAction("CPU selected: " + holeKey);
            highlightHole(holeKey, Color.RED, true);



            // Set delay for select
            short finalHoleKey = holeKey;
            Timeline timeline = new Timeline(new KeyFrame(dellay, e -> {
                // Code that interacts with UI elements
                logAction("Delay done, now select the hole: " + finalHoleKey);
                selectHole(finalHoleKey);
            }));
            timeline.play();
        }
    }


}