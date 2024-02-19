package com.point.mancala;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import static com.point.mancala.GameType.*;

//import static com.point.mancala.Game.startPVP;

public class GameController extends CPU_core_logic implements Initializable {

    private static final char BALLS_START_INDEX = 3; // The index of the first ball in hole (before this index are the other components like Rectangle and Label)
    private static final char START_BALL_COUNT = 4; // The amount of balls in each hole in the start of the game
    final static int MAX_ROWS_AND_COLS_IN_GRID = 15; // the max rows and columns in every grid (16-1)
    private static  final char GRID_ROWS_NUM = 4; // nums of rows for each grid hols (except the player main hole grid) - min 2 rows
    private static  final char GRID_COLUMNS_NUM = 4; // nums of columns for each grid hols (except the player main hole grid) - min 2 columns
    protected int[][] gridArr = {{2,1},{2,2},{1,2},{1,1}, {1,0}, {2,0}, {3,0},{3, 1}, {3, 2}, {3, 3}, {2, 3}, {1, 3}, {0,3},{0, 2}, {0, 1}, {0,0}};// new int[GRID_ROWS_NUM*GRID_COLUMNS_NUM][2]; // this arr store the location of each of the ball in the grid, for example, the first ball should be located in 1,1 (in the center of the grid)
    private static final char BALLS_START_INDEX_IN_PLAYER_HOLE = 1; // The index of the first ball in player hole (before this index are the other components like Rectangle)
    public static boolean inTransition = false;
    private final Animations animation = new Animations();
    private static boolean gameTurn = true; // Player 1 turn = true, player 2 false
    protected GridPane lastHole; // The last hole that a ball got into
    private final Animations playerSwing = new Animations();
    protected AnchorPane[] holes;
    protected GridPane[] holesGrid;
//    private short p1_balls_holes_sum = 24; // sum all the balls of player 1 (not includes the main one) in order to get this info fast
//    private short p2_balls_holes_sum = 24; // sum all the balls of player 2 (not includes the main one) in order to get this info fast
    private boolean canPlay = true; // Indicate if any player can play, when loading time is over it will be true

    // A dictionary with hole id as key and hole-AnchorPane object as the value
    //private HashMap<Character, Hole> holesDict;

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



    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        ObservableList<Node> row1_Children = holes_row1.getChildren();
        ObservableList<Node> row2_Children = holes_row2.getChildren();
        //fade buttons
        holes = new AnchorPane[]{(AnchorPane) row1_Children.get(0), (AnchorPane) row1_Children.get(1), (AnchorPane) row1_Children.get(2), (AnchorPane) row1_Children.get(3), (AnchorPane) row1_Children.get(4), (AnchorPane) row1_Children.get(5), (AnchorPane) row2_Children.get(0), (AnchorPane) row2_Children.get(1), (AnchorPane) row2_Children.get(2), (AnchorPane) row2_Children.get(3), (AnchorPane) row2_Children.get(4), (AnchorPane) row2_Children.get(5)};
        holesGrid = new GridPane[]{(GridPane)holes[0].getChildren().getLast(),(GridPane)holes[1].getChildren().getLast(),(GridPane)holes[2].getChildren().getLast(),(GridPane)holes[3].getChildren().getLast(),(GridPane)holes[4].getChildren().getLast(),(GridPane)holes[5].getChildren().getLast(),(GridPane)holes[6].getChildren().getLast(),(GridPane)holes[7].getChildren().getLast(),(GridPane)holes[8].getChildren().getLast(),(GridPane)holes[9].getChildren().getLast(),(GridPane)holes[10].getChildren().getLast(),(GridPane)holes[11].getChildren().getLast()};
        game_type.setText("Player VS Player");

        // create the balls for each hole
        int i;
        //int i2;
        //int num = GRID_ROWS_NUM*GRID_COLUMNS_NUM;
        Ball b;

       // boolean direction = true; // true = move the j, false = move the i
/*
        for (i = 0; i<GRID_COLUMNS_NUM; i++) {

            if (direction) {
                for (j = 0; j < grid_rows; j++) {
                    gridArr[--num] = new int[]{i, j};
                    if (j == grid_rows - 1) {
                        direction = false;
                        grid_rows--;
                    }
                }
                j--;
            }
            else {
                for (i2 = i; i2 < GRID_COLUMNS_NUM; i2++) {
                    gridArr[--num] = new int[]{i2, j};
                if (i2 == GRID_COLUMNS_NUM - 1) {
                    direction = true;
                    grid_columns--;

                }
            }
            }
        }



        for ( i = 0; i<GRID_ROWS_NUM*GRID_COLUMNS_NUM; i++) {
            System.out.println( i+1 + "= " +gridArr[i][0] + ", " + gridArr[i][1] + "\n");
        }*/

        if(true) {
            GridPane grid;
            for (char holeID = 0; holeID <holesGrid.length; holeID++) {
                grid =  holesGrid[holeID];
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
        }
        else {
            // test with 3 balls
            try {
                /*
                AnchorPane hole;
                for (char holeID = 0; holeID <holes.length; holeID++) {
                    hole = holes[holeID];
                    holesDict.put(holeID, new Hole(hole, (char) 0));
                }*/

//                b = new Ball(holes[0], colors.get(0), 0);
//                holes[0].getChildren().add(b);

                b = new Ball(holes[1], COLORS.get(2), 1);
                holes[1].getChildren().add(b);

                b = new Ball(holes[11], COLORS.get(1), 3);
                holes[11].getChildren().add(b);
                b = new Ball(holes[10], COLORS.get(1), 1);
                holes[10].getChildren().add(b);
                b = new Ball(holes[10], COLORS.get(2), 2);
                holes[10].getChildren().add(b);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private void addBallToGrid(GridPane grid, Ball ball, int ballIndex){
        int ballsCount = getHoleBallCount(grid);
        if( ((double)ballIndex/MAX_ROWS_AND_COLS_IN_GRID) > 1)
            ballIndex = (int) (ballIndex - MAX_ROWS_AND_COLS_IN_GRID*(ballIndex/MAX_ROWS_AND_COLS_IN_GRID)); // if the index number is more than the MAX_ROWS_AND_COLS_IN_GRID, then add the ball to an existing index(start the index count again)
            //ballIndex = (int) (ballIndex - 16*(Math.floor(ballIndex/16)));

        int col = gridArr[ballIndex][0];
        int row = gridArr[ballIndex][1];
        grid.add(ball, col, row);
       logAction( ballIndex + "= " +col + ", " + row + "\n", 3);
    }
    @FXML
    protected void new_game_button() throws Exception {

       logAction("new_game_button", 3);
        //fade animation
        animation.fadeAnimation((Node) new_game_button, 500, false);
        //open new game window
        new Main().start(new Stage());
        //showWindow(30, 20);

//        move_all_player_balls(true, holesGrid[2]);
//        move_all_player_balls(false, holesGrid[2]);
    }
    @FXML
    protected void new_game_button_hover(MouseEvent event) throws Exception {
        Label label = (Label) event.getSource();
        label.setTextFill(Color.web("rgb(255, 255, 255)"));
    }
    @FXML
    protected void new_game_button_not_hover(MouseEvent event) throws Exception {
        Label label = (Label) event.getSource();
        label.setTextFill(Color.web("#ffffffc2"));
    }
    @FXML
    protected void holeMouseHover(MouseEvent event) throws Exception {
        //System.out.println("holePressedDown()");
        AnchorPane hole = (AnchorPane) event.getSource();
        int holeId = getHoleId(hole);

        if(gameTurn) {
            if (holeId >= 0 && holeId < 6) {
                highlightHole((AnchorPane) event.getSource(),Color.WHEAT, true);
            }
            else {
                highlightHole((AnchorPane) event.getSource(),Color.rgb(255,255,255,0.21), true);
            }
        }
        else {
            if (holeId >= 6 && holeId < 12) {
                highlightHole((AnchorPane) event.getSource(),Color.WHEAT, true);
            }
            else {
                highlightHole((AnchorPane) event.getSource(),Color.rgb(255,255,255,0.21), true);
            }
        }
    }
    @FXML
    protected void holeMouseNotHover(MouseEvent event) throws Exception {

        //System.out.println("holePressedUp()");
        highlightHole((AnchorPane) event.getSource(),Color.WHEAT, false);
    }
    @FXML
    protected void holeClick(MouseEvent event) throws Exception {

        AnchorPane hole = (AnchorPane) event.getSource();
        holeClicked(hole);

    }

    public void holeClicked(AnchorPane hole) throws IOException {

        selectHole(getHoleId(hole));
        /*
        GridPane grid = getGridFromAnchorPane(hole);
        if (canPlay) {
            //the first item is - Rectangle hole_style we want just the balls witch is from index 1
            int ballsCount = getHoleBallCount(grid);
            int holeId = getHoleId(hole);
            int holeIndex = holeId; //get the hole index (index of the holes list), it obtained from the Node id
            int i;
           logAction("Hole" + holeIndex + " clicked", 2);
            boolean canClick = true; // if the hole can be pressed (if is the player turn)

            // check if is the player turn (if the player's row can be clicked new)
            if (gameTurn) {
                canClick = (holeId >= 0 && holeId < 6);
            } else {
                canClick = (holeId >= 6 && holeId < 12);
            }

            if (canClick && ballsCount > 0) {
                canPlay = false;
                //move every ball in the hole to the next holes
                if (holeIndex == 0) {
                    holeIndex = -1; // the next hole after p1 hole
                } else if (holeIndex == 11) {
                    holeIndex = 12; // the next hole after p2 hole
                } else {
                    //if the holeIndex is in row2 we need to move forward (increase the index by 1) in the holes list
                    if (holeIndex >= 6) {
                        holeIndex++;
                    } else {
                        //if the holeIndex is in row1 we need to move backwards (reduce the index by 1) in the holes list
                        holeIndex--;
                    }
                }

                for (i = 0; i < ballsCount; i++) {
                    if (holeIndex == -1) {
                        moveBallTo(grid, ballGridP1);
                        this.lastHole = ballGridP1;
                        holeIndex = 6; // the next hole after p1 hole
                    } else if (holeIndex == 12) {
                        moveBallTo(grid, ballGridP2);
                        this.lastHole = ballGridP2;
                        holeIndex = 5;//hole.getId(); // the next hole after p2 hole
                    } else {
                        moveBallTo(grid, holesGrid[holeIndex]);
                        this.lastHole = holesGrid[holeIndex];
                        //if the holeIndex is in row2 we need to move forward (increase the index by 1) in the holes list
                        if (holeIndex >= 6 && holeIndex <= 11) {
                            holeIndex++;
                        } else {
                            //if the holeIndex is in row1 we need to move backwards (reduce the index by 1) in the holes list
                            holeIndex--;
                        }
                    }
                   logAction("Last hole: " + this.lastHole, 2);
                    //make delay
                }


                //if the last ball get into the player's hole, he will get another turn
                if (gameTurn && (this.lastHole == ballGridP1))
                {
                    if(isAllPlayerHolesEmpty(true)){
                        changeTurn(true);
                    }else {
                        // player 1 get another turn
                        Animations sa = new Animations();
                        sa.scale(game_turn_label, 300, 1.1, false);
                    }
                }
                else {
                    if (!gameTurn && (this.lastHole == ballGridP2)) {
                        if(isAllPlayerHolesEmpty(false)){
                            changeTurn(false);
                        }else {
                            // Player 2 get another turn
                            Animations sa = new Animations();
                            sa.scale(game_turn_label, 300, 1.1, false);
                        }
                    } else {
                        int lastHoleBallCount = getHoleBallCount(this.lastHole);

                        // If the last ball get into an empty hole of
                        if(gameTurn && (lastHoleBallCount == 1) && hole != player1_hole && hole != player2_hole)
                        {
                           int parallelHoleId = getHoleId(this.lastHole) + 6;

                            // Take the parallel hole balls just if the last ball of player 1 end in his own hole and the parallel hole have at least 1 ball
                            // So if the parallelHoleId is grater then 11 it means that the last ball is not landed in the player 1 era
                           if(parallelHoleId <= 11) {
                               // get the parallel hole
                               GridPane parallelHole = getHoleFromId(parallelHoleId);

                               //check if the parallel hole have at least 1 ball
                               if(getHoleBallCount(parallelHole) > 0)
                               {
                                   logAction("Player 1 last ball got into an empty hole", 1);

                                   // Move all the balls from the parallel hole to player 1 hole
                                   int parallelHoleBallsCount = getHoleBallCount(parallelHole);
                                   for (int j = 0; j < parallelHoleBallsCount; j++) {
                                       moveBallTo(parallelHole, ballGridP1);
                                   }
                                   // Move the last ball from the last hole to player 1 hole
                                   moveBallTo(lastHole, ballGridP1);

                                   // Update parallelHole and lastHole hole score
                                   updateScore((AnchorPane) parallelHole.getParent(), true);
                                   updateScore((AnchorPane) lastHole.getParent(), true);
                                   changeTurn(false);
                           }
                           }

                            // If all player 2 holes are empty player 1 get another turn in order to finish the game
                            if(isAllPlayerHolesEmpty(true))
                                changeTurn(true);
                            else // If the ball end in an empty hole of player 1, player 2 should get the next turn
                                changeTurn(false);

                        }
                        else if(!gameTurn && (lastHoleBallCount == 1) && hole != player1_hole && hole != player2_hole) {

                            int parallelHoleId = getHoleId(this.lastHole) - 6;

                            // take the parallel hole balls to the player's main hole just if the last ball of player 2 end in his own hole and the parallel hole have at least 1 ball
                            // so if the parallelHoleId is smaller than 0 it means that the last ball is not landed in the player 2 era
                            if (parallelHoleId >= 0) {
                                // get the parallel hole
                                GridPane parallelHole = getHoleFromId(parallelHoleId);

                                // check if the parallel hole have at least 1 ball
                                if (getHoleBallCount(parallelHole) > 0) {
                                    logAction("Player 2 last ball got into an empty hole", 1);

                                    // move the balls from the parallel hole and the last hole to player 2 hole
                                    int parallelHoleBallsCount = getHoleBallCount(parallelHole);
                                    for (int j = 0; j < parallelHoleBallsCount; j++) {
                                        moveBallTo(parallelHole, ballGridP2);
                                    }
                                    // move the last ball from the last hole to player 1 hole
                                    moveBallTo(lastHole, ballGridP2);

                                    //update parallelHole and lastHole hole score
                                    updateScore((AnchorPane)parallelHole.getParent(), true);
                                    updateScore((AnchorPane) this.lastHole.getParent(), true);
                                }
                            }

                            // If all player1 holes are empty player2 get another turn in order to finish the game
                            if(isAllPlayerHolesEmpty(true))
                                changeTurn(false);
                            else // If the ball end in an empty hole of player 2, player 1 should get the next turn
                                changeTurn(true);

                        }
                        else
                            changeTurn(!gameTurn);
                    }
                }

                // turn off the highlight of the hole
                highlightHole(hole, Color.WHEAT, false);

                //update the hole score
                Label hole_score = (Label) hole.getChildren().get(1);
                hole_score.setText("0");
                canPlay = true;
            }
        }*/
    }



    protected void selectHole(short holeIndex) throws IOException {
        GridPane grid = (GridPane) hols.get(holeIndex).get(1);
        if (canPlay) {
            //the first item is - Rectangle hole_style we want just the balls witch is from index 1
            int ballsCount = getHoleBallCount(grid);

            logAction("Hole" + holeIndex + " clicked", 2);
            boolean canClick = true; // if the hole can be pressed (if is the player turn)

            // check if is the player turn (if the player's row can be clicked new)
            if (gameTurn) {
                canClick = (holeIndex >= 0 && holeIndex < 6);
            } else {
                canClick = (holeIndex >= 6 && holeIndex < 12);
            }

            if (canClick && ballsCount > 0) {
                canPlay = false;
                //move every ball in the hole to the next holes
                if (holeIndex == 0) {
                    holeIndex = -1; // the next hole after p1 hole
                } else if (holeIndex == 11) {
                    holeIndex = 12; // the next hole after p2 hole
                } else {
                    //if the holeIndex is in row2 we need to move forward (increase the index by 1) in the holes list
                    if (holeIndex >= 6) {
                        holeIndex++;
                    } else {
                        //if the holeIndex is in row1 we need to move backwards (reduce the index by 1) in the holes list
                        holeIndex--;
                    }
                }

                for (int i = 0; i < ballsCount; i++) {
                    if (holeIndex == -1) {
                        moveBallTo(grid, ballGridP1);
                        this.lastHole = ballGridP1;
                        holeIndex = 6; // the next hole after p1 hole
                    } else if (holeIndex == 12) {
                        moveBallTo(grid, ballGridP2);
                        this.lastHole = ballGridP2;
                        holeIndex = 5;//hole.getId(); // the next hole after p2 hole
                    } else {
                        moveBallTo(grid, holesGrid[holeIndex]);
                        this.lastHole = holesGrid[holeIndex];
                        //if the holeIndex is in row2 we need to move forward (increase the index by 1) in the holes list
                        if (holeIndex >= 6 && holeIndex <= 11) {
                            holeIndex++;
                        } else {
                            //if the holeIndex is in row1 we need to move backwards (reduce the index by 1) in the holes list
                            holeIndex--;
                        }
                    }
                    logAction("Last hole: " + this.lastHole, 2);
                    //make delay
                }


                //if the last ball get into the player's hole, he will get another turn
                if (gameTurn && (this.lastHole == ballGridP1))
                {
                    if(isAllPlayerHolesEmpty(true)){
                        changeTurn(true);
                    }else {
                        // player 1 get another turn
                        Animations sa = new Animations();
                        sa.scale(game_turn_label, 300, 1.1, false);
                    }
                }
                else {
                    if (!gameTurn && (this.lastHole == ballGridP2)) {
                        if(isAllPlayerHolesEmpty(false)){
                            changeTurn(false);
                        }else {
                            // Player 2 get another turn
                            Animations sa = new Animations();
                            sa.scale(game_turn_label, 300, 1.1, false);
                        }
                    } else {
                        int lastHoleBallCount = getHoleBallCount(this.lastHole);

                        // If the last ball get into an empty hole of
                        if(gameTurn && (lastHoleBallCount == 1) && holeIndex != -1 && holeIndex != 13)
                        {
                            int parallelHoleId = getHoleId(this.lastHole) + 6;

                            // Take the parallel hole balls just if the last ball of player 1 end in his own hole and the parallel hole have at least 1 ball
                            // So if the parallelHoleId is grater then 11 it means that the last ball is not landed in the player 1 era
                            if(parallelHoleId <= 11) {
                                // get the parallel hole
                                GridPane parallelHole = getHoleFromId(parallelHoleId);

                                //check if the parallel hole have at least 1 ball
                                if(getHoleBallCount(parallelHole) > 0)
                                {
                                    logAction("Player 1 last ball got into an empty hole", 1);

                                    // Move all the balls from the parallel hole to player 1 hole
                                    int parallelHoleBallsCount = getHoleBallCount(parallelHole);
                                    for (int j = 0; j < parallelHoleBallsCount; j++) {
                                        moveBallTo(parallelHole, ballGridP1);
                                    }
                                    // Move the last ball from the last hole to player 1 hole
                                    moveBallTo(lastHole, ballGridP1);

                                    // Update parallelHole and lastHole hole score
                                    updateScore((AnchorPane) parallelHole.getParent(), true);
                                    updateScore((AnchorPane) lastHole.getParent(), true);
                                    changeTurn(false);
                                }
                            }

                            // If all player 2 holes are empty player 1 get another turn in order to finish the game
                            if(isAllPlayerHolesEmpty(true))
                                changeTurn(true);
                            else // If the ball end in an empty hole of player 1, player 2 should get the next turn
                                changeTurn(false);

                        }
                        else if(!gameTurn && (lastHoleBallCount == 1) && holeIndex != -1 && holeIndex != 13) {

                            int parallelHoleId = getHoleId(this.lastHole) - 6;

                            // take the parallel hole balls to the player's main hole just if the last ball of player 2 end in his own hole and the parallel hole have at least 1 ball
                            // so if the parallelHoleId is smaller than 0 it means that the last ball is not landed in the player 2 era
                            if (parallelHoleId >= 0) {
                                // get the parallel hole
                                GridPane parallelHole = getHoleFromId(parallelHoleId);

                                // check if the parallel hole have at least 1 ball
                                if (getHoleBallCount(parallelHole) > 0) {
                                    logAction("Player 2 last ball got into an empty hole", 1);

                                    // move the balls from the parallel hole and the last hole to player 2 hole
                                    int parallelHoleBallsCount = getHoleBallCount(parallelHole);
                                    for (int j = 0; j < parallelHoleBallsCount; j++) {
                                        moveBallTo(parallelHole, ballGridP2);
                                    }
                                    // move the last ball from the last hole to player 1 hole
                                    moveBallTo(lastHole, ballGridP2);

                                    //update parallelHole and lastHole hole score
                                    updateScore((AnchorPane)parallelHole.getParent(), true);
                                    updateScore((AnchorPane) this.lastHole.getParent(), true);
                                }
                            }

                            // If all player1 holes are empty player2 get another turn in order to finish the game
                            if(isAllPlayerHolesEmpty(true))
                                changeTurn(false);
                            else // If the ball end in an empty hole of player 2, player 1 should get the next turn
                                changeTurn(true);

                        }
                        else
                            changeTurn(!gameTurn);
                    }
                }

                // turn off the highlight of the hole
                highlightHole(hole, Color.WHEAT, false);

                //update the hole score
                Label hole_score = (Label) hole.getChildren().get(1);
                hole_score.setText("0");
                canPlay = true;
            }
        }
    }

    //get Grid From AnchorPane hole node
    private static GridPane getGridFromAnchorPane(AnchorPane hole){
        return (GridPane)hole.getChildren().getLast();
    }

    // Set the player turn (how can play - player 1 or player 2)
    public void changeTurn(boolean turn) throws IOException {
        logAction("Game turn = " + turn, 2);
        gameTurn = turn;

        if(gameTurn)
        {
            if(isAllPlayerHolesEmpty(true))
            {
                // if all the holes empty
                logAction("changeTurn: FOUND that all the holes empty in P1", 1);
                move_all_player_balls(false, ballGridP1);
                int player1_score = Integer.parseInt(p1_score.getText());
                int player2_score = Integer.parseInt(p2_score.getText());
                showWindow(player1_score, player2_score);
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
                // if all the holes empty
                logAction("changeTurn: FOUND that all the holes empty in P2", 1);
                move_all_player_balls(true, ballGridP2);

                int player1_score = Integer.parseInt(p1_score.getText());
                int player2_score = Integer.parseInt(p2_score.getText());
                showWindow(player1_score, player2_score);
            }
            else {
                game_turn_label.setText("Player 2 turn");
                //playerSwing.translate(player2_name);
                playerSwing.fadeAnimation(player2_name, 300, false);
                if(GAME_TYPE == PVC || GAME_TYPE == CVC)
                    CPUAction(false);
            }
        }
        animation.fadeAnimation(game_turn_label, 300, false);
    }

   /* public ArrayList<Short> getListOfBallsCount(){

        for (GridPane grid : holesGrid)
        {
            holesBallsCount.add((short)getHoleBallCount(grid));
        }
        System.out.println("getListOfBallsCount: " + holesBallsCount);
        return holesBallsCount;
    }*/

    // return true if all the player's hols are empty
    private boolean isAllPlayerHolesEmpty(boolean player)
    {
        if(player)
            for (int i = 0; i < 6; i++) {
                if (getHoleBallCount(holesGrid[i]) > 0)
                    return false;
            }
        else
            for (int i = 6; i < 12; i++) {
                if (getHoleBallCount(holesGrid[i]) > 0)
                    return false;
            }
        return true;
    }

    //Move all the balls of the source player to the target hole
    private void move_all_player_balls(boolean source_player, GridPane target_hole) {

        int startHoleId;
        int endHoleId;
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
        logAction("Move all the balls of the source player: " + (source_player? "P1":"P2") + ", to the target hole: " + target_hole.getId(), 1);
        int j, holeBallCount;
        for (int i = startHoleId; i <= endHoleId; i++) {
            holeBallCount = getHoleBallCount(holesGrid[i]);
            if(holeBallCount > 0){
                for (j = 0; j <= holeBallCount; j++) {
                    //logAction("-----"+ i +", count " + getHoleBallCount(holes[i]), 1);
                    moveBallTo(holesGrid[i], target_hole);
                    //moveAllBallsInHoleWithTransition(holesGrid[i], target_hole);
                }
                updateScore(holes[i], true);
            }
        }
    }
    //return the amount of balls int the provided hole
    private int getHoleBallCount(GridPane hole)
    {
        if(hole == ballGridP1)
            //return this.player1_hole.getChildren().size() - BALLS_START_INDEX_IN_PLAYER_HOLE;
            return ballGridP1.getChildren().size();
        else if(hole == ballGridP2)
            //return this.player2_hole.getChildren().size() - BALLS_START_INDEX_IN_PLAYER_HOLE;
            return ballGridP2.getChildren().size();
        else{
            //return hole.getChildren().size() - BALLS_START_INDEX;
            return hole.getChildren().size();
        }
    }
    //move one ball from the source hole to destination hole
    // with no animation, to move with animation use moveBallToWithTransition, but it is not work
    public void moveBallTo(GridPane source, GridPane dest)
    {
        if(source == dest)
        {
            logAction("The source is equal to the destination! (it could be ok in case of hole with 14 or more balls)", 1);
        }
        else if(getHoleBallCount(source) > 0) { // If there is at least 1 ball in the hole
           logAction("Move ball from source: " + getHoleId(source) + " (Ball count: " + getHoleBallCount(source)+ ")" + ", to the destination: " + getHoleId(dest) + " (Ball count: " + getHoleBallCount(dest)+ ") ", 2);
            // Move the ball to the destination hole

            Ball ball = (Ball)source.getChildren().getLast();
            addBallToGrid(dest, ball, getHoleBallCount(dest));

            // Update the destination hole score
            updateScore((AnchorPane) dest.getParent(), true);
        }
        else {
           logAction("There is no balls in source!", 1);
        }
    }
    /*public void moveBallToWithTransition(AnchorPane sourceParent, AnchorPane newParent)
    {
        if(!inTransition || true)
        {
            if(sourceParent == newParent)
            {
                logAction("The source is equal to the destination! (it could be ok in case of hole with 14 or more balls)", 1);
            }
            else if(getHoleBallCount(sourceParent) > 0) { // If there is at least 1 ball in the hole
                logAction("Move ball from source: " + getHoleId(sourceParent) + " (Ball count: " + getHoleBallCount(sourceParent)+ ")" + ", to the destination: " + getHoleId(newParent) + " (Ball count: " + getHoleBallCount(newParent)+ ") ", 2);
                // Move the ball to the destination hole
                Ball ball = (Ball)sourceParent.getChildren().get(BALLS_START_INDEX); // Get the first ball

                TranslateTransition transition = new TranslateTransition(Duration.millis(1000), ball);
                double nodeXLocation = ball.getBoundsInParent().getMinX(); // get the node X location in its parent
                double destNodeXLocation = newParent.getChildren().getLast().getBoundsInParent().getMaxX(); // get the destination of node X location in its new parent

                logAction("nodeXLocation: " + nodeXLocation + ", destNodeXLocation: " + destNodeXLocation);
                // decrease or increase the x location of the node (from 0 - the x location of the node)
                double xdest = (Math.max(nodeXLocation, destNodeXLocation) - Math.min(nodeXLocation, destNodeXLocation)) * ((nodeXLocation > destNodeXLocation) ? -1 : 1);


                double nodeYLocation = ball.getParent().getBoundsInParent().getMinY(); // get the node Y location in its parent
                double destNodeYLocation = newParent.getBoundsInParent().getMinY(); // get the destination of node Y location in its new parent
                logAction("nodeYLocation: " + nodeYLocation + ", destNodeYLocation: " + destNodeYLocation);

                // decrease or increase the y location of the node (from 0 - the y location of the node)
                double ydest = (Math.max(nodeYLocation, destNodeYLocation) - Math.min(nodeYLocation, destNodeYLocation)) * ((nodeYLocation > destNodeYLocation) ? -1 : 1);

                logAction("X: " + xdest + ", dest Y: " + ydest);

                transition.setToX(xdest); // set the X transition
                transition.setToY(ydest); // set the Y transition
                transition.playFromStart(); // start the transition animation


                // action to be performed after the transition ends
                // add the node to the new parent (witch also remove it from its previous parent)
                transition.setOnFinished(e -> {
                    logAction("Transition ended! add the node to the new parent: " + newParent.getId(), 2);
                    newParent.getChildren().add(ball);
                    ball.setTranslateX(0);
                    ball.setTranslateY(0);
                    // Update the destination hole score
                    updateScore(newParent, true);
                    inTransition = false;
                });
            }
            else {
                logAction("There is no balls in source!", 1);
            }
        }
    }*/

    // not in use- it problematic...
    public void moveAllBallsInHoleWithTransition(GridPane source, GridPane newParent){

        if(!inTransition) {
            if (source == newParent) {
                logAction("The source is equal to the destination! (it could be ok in case of hole with 14 or more balls)", 1);
            }
            else if (getHoleBallCount(source) > 0) { // If there is at least 1 ball in the hole
                inTransition = true;
                logAction("Move ball from source: " + getHoleId(source) + " (Ball count: " + getHoleBallCount(source) + ")" + ", to the destination: " + getHoleId(newParent) + " (Ball count: " + getHoleBallCount(newParent) + ") ", 2);
                // Move the ball to the destination hole
                Ball ball = (Ball) source.getChildren().getLast(); // Get the first ball
                //ball.setLayoutX(ball.getLayoutX() - 5);
                //ball.setLayoutY(ball.getLayoutY() - 5);
                newParent.getChildren().add(ball);

                //transformation:
                /*
                TranslateTransition transition = new TranslateTransition(Duration.millis(1000), ball);
                    int destID = getHoleId(newParent);
                    double nodeXLocation = ball.getBoundsInParent().getMinX(); // get the node X location in its parent
                    double destNodeXLocation = gridArr[destID][0]; //newParent.getChildren().getLast().getBoundsInParent().getMaxX(); // get the destination of node X location in its new parent

                    logAction("nodeXLocation: " + nodeXLocation + ", destNodeXLocation: " + destNodeXLocation);
                    // decrease or increase the x location of the node (from 0 - the x location of the node)
                    double xdest = (Math.max(nodeXLocation, destNodeXLocation) - Math.min(nodeXLocation, destNodeXLocation)) * ((nodeXLocation > destNodeXLocation) ? -1 : 1);


                    double nodeYLocation = ball.getParent().getBoundsInParent().getMinY(); // get the node Y location in its parent
                    double destNodeYLocation = gridArr[destID][1];//newParent.getBoundsInParent().getMinY(); // get the destination of node Y location in its new parent
                    logAction("nodeYLocation: " + nodeYLocation + ", destNodeYLocation: " + destNodeYLocation);

                    // decrease or increase the y location of the node (from 0 - the y location of the node)
                    double ydest = (Math.max(nodeYLocation, destNodeYLocation) - Math.min(nodeYLocation, destNodeYLocation)) * ((nodeYLocation > destNodeYLocation) ? -1 : 1);

                    logAction("X: " + xdest + ", dest Y: " + ydest);

                    transition.setToX(xdest); // set the X transition
                    transition.setToY(ydest); // set the Y transition
                    transition.playFromStart(); // start the transition animation


                    // action to be performed after the transition ends
                    // add the node to the new parent (witch also remove it from its previous parent)
                    transition.setOnFinished(e -> {
                        logAction("Transition ended! add the node to the new parent: " + newParent.getId(), 2);
                        newParent.getChildren().add(ball);
//                        ball.setTranslateX(0);
//                        ball.setTranslateY(0);
                        // Update the destination hole score
                        updateScore((AnchorPane) newParent.getParent(), true);
                        inTransition = false;
                    });
*/
                newParent.getChildren().add(ball);
                // Update the destination hole score
                updateScore((AnchorPane) newParent.getParent(), true);
            } else {
                logAction("There is no balls in source!", 1);
            }
        }
        else {
            logAction("Can't make another transition, there is active transition now!");
        }

    }
    private short getHoleId(AnchorPane hole)
    {
        short id;
        if(hole == player1_hole)
            id = -1;
        else if(hole == player2_hole)
            id = 12;
        else {
            id = Short.parseShort(hole.getId().replace("hole", ""));
        }
       logAction("GetHoleId func return: " + id, 3);
        return id;
    }
    private int getHoleId(GridPane grid)
    {
        int id;
        if(grid == ballGridP1)
            id = -1;
        else if(grid == ballGridP2)
            id = 12;
        else {
            id = Integer.parseInt(grid.getId().replace("ballGrid", ""));
        }
        logAction("GetHoleId func return: " + id, 3);
        return id;
    }
    private GridPane getHoleFromId(int holeID)
    {
        logAction("GetHoleFromId func, holeID: " + holeID, 3);
        if(holeID == -1)
            return ballGridP1;
        else if(holeID == 12)
            return ballGridP2;
        else {
            return holesGrid[holeID];
        }
    }
    //update the hole Label score
    public void updateScore(AnchorPane hole, boolean withAnimation){
        Label hole_score;
        if(hole == player1_hole)
        {
            hole_score = p1_score; //1 is the score label index in hole
            hole_score.setText(String.valueOf(getHoleBallCount(ballGridP1)));
        }
        else if(hole == player2_hole)
        {
            hole_score =  p2_score; //1 is the score label index in hole
            hole_score.setText(String.valueOf(getHoleBallCount(ballGridP2)));
        }
        else {
            hole_score = (Label) hole.getChildren().get(1); //1 is the score label index in hole
            hole_score.setText(String.valueOf(getHoleBallCount(getGridFromAnchorPane(hole))));
        }

        if(withAnimation)
        {
            Animations fa = new Animations();
            fa.fadeAnimation(hole_score, 300, false);
        }
    }
    public void highlightHole(AnchorPane hole, Color color, boolean state){
        Rectangle hole_style = (Rectangle) hole.getChildren().getFirst();
        if(state)
        {
            hole_style.setStroke(color);
            hole_style.setStrokeWidth(5);
        }
        else {
            hole_style.setStrokeWidth(0);
        }

    }
/*
    // this function will display a message when one of the players win
    // lastPlayerMove = the last player that make a move in the game
    private void checkForWinner(boolean lastPlayerMove) throws IOException {
        // when all the hols are empty, check witch player have the most balls
        // the player with the most balls is the winner

        logAction("checkForWinner, lastPlayerMove = " + lastPlayerMove, 3);
        // go over every hole
        boolean allHolsEmpty = true;
        for (AnchorPane hole:holes) {
            if(getHoleBallCount(hole) > 0) {
                allHolsEmpty = false;
                break;
            }
        }

        // if all the holes empty
        if(allHolsEmpty)
        {
            logAction("checkForWinner FOUND that all the holes empty", 1);

            int player1_score = Integer.parseInt(p1_score.getText());
            int player2_score = Integer.parseInt(p2_score.getText());
            showWindow(player1_score >= player2_score, player2_score>=player1_score);
        }

    }
    */

    private void showWindow(int player1_score, int player2_score) throws IOException {
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
            swing.translate(player1_name);
        }
        else
        {
            winner_player.setText("Player 2 win!");
            //swing the winner name
            Animations swing = new Animations();
            swing.translate(player2_name);
        }


//        Lighting lighting = new Lighting();
//        game_board.setEffect(lighting);
        win_window.setVisible(true);

        Animations pop = new Animations();
        pop.scale(win_window, 500, 0.5, false);

       //pop.rotate(win_window, 500,false);


    }

    // Close the win window in order to see the board
    @FXML
    protected void see_board() throws Exception{
        game_board.setOpacity(1);
        game_board.setDisable(false);
        win_window.setVisible(false);
    }

    // Start a new game with the same setting (PVP or PVC or CVC)
    @FXML
    protected void start_new_game() throws Exception{
        startPVP(new Stage());
    }


}