package com.point.mancala;

import com.point.mancala.Animations.FadeAnimation;
import com.point.mancala.Animations.Scale;
import com.point.mancala.Animations.Translate;
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

import static com.point.mancala.AlgorithmType.*;
import static com.point.mancala.GameType.*;


public class GameController extends General implements Initializable {

    private static final short START_BALL_COUNT = 4; // The amount of balls in each hole in the start of the game
    final static int MAX_ROWS_AND_COLS_IN_GRID = 15; // the max rows and columns in every grid (16-1)
    private static  final char GRID_ROWS_NUM = 4; // nums of rows for each grid hols (except the player main hole grid) - min 2 rows
    private static  final char GRID_COLUMNS_NUM = 4; // nums of columns for each grid hols (except the player main hole grid) - min 2 columns
    protected int[][] gridArr = {{2,1},{2,2},{1,2},{1,1}, {1,0}, {2,0}, {3,0},{3, 1}, {3, 2}, {3, 3}, {2, 3}, {1, 3}, {0,3},{0, 2}, {0, 1}, {0,0}}; // this arr store the location of each of the ball in the grid, for example, the first ball should be located in 1,1 (in the center of the grid)
    protected AnchorPane[] holes;
    protected boolean canPlay = true; // Indicate if any player can play, when loading time is over it will be trues
    protected boolean isGameOver = false; // if a player win this var will be true;
    protected boolean pause = false; // if the user pause the game;
    protected boolean gameTurn = true; // Player 1 turn = true, player 2 false
    protected short lastHoleKey; // The last holeKey that a ball got into
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
    @FXML
    private Button soundToggle;



    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        if(gameSound) {
            soundToggle.setId("sound_btn");
        }
        else {
            soundToggle.setId("sound_off_btn");
        }

        ObservableList<Node> row1_Children = holes_row1.getChildren();
        ObservableList<Node> row2_Children = holes_row2.getChildren();
        //fade buttons
        holes = new AnchorPane[]{(AnchorPane) row1_Children.get(0), (AnchorPane) row1_Children.get(1), (AnchorPane) row1_Children.get(2), (AnchorPane) row1_Children.get(3), (AnchorPane) row1_Children.get(4), (AnchorPane) row1_Children.get(5), (AnchorPane) row2_Children.get(0), (AnchorPane) row2_Children.get(1), (AnchorPane) row2_Children.get(2), (AnchorPane) row2_Children.get(3), (AnchorPane) row2_Children.get(4), (AnchorPane) row2_Children.get(5)};
        game_type.setText(GAME_TYPE_NAME);

        // create the balls for each hole
        short i;
        Ball b;

        // add P1 and P2 main hole to the hashMap
        P1_MAIN_HOLE = new Hole(P1_MAIN_HOLE_KEY, (short) 0, ballGridP1, player1_hole, (Rectangle)player1_hole.getChildren().getFirst(), p1_score);
        P2_MAIN_HOLE = new Hole(P2_MAIN_HOLE_KEY, (short) 0, ballGridP2, player2_hole, (Rectangle)player2_hole.getChildren().getFirst(), p2_score);

        HOLES.put(P1_MAIN_HOLE_KEY, P1_MAIN_HOLE);
        HOLES.put(P2_MAIN_HOLE_KEY, P2_MAIN_HOLE);

        GridPane grid;
        Label label;

        if (true) {
            for (short holeKey = 0; holeKey < 12; holeKey++) {
                AnchorPane holeAnchorPane = holes[holeKey];
                grid = (GridPane) holeAnchorPane.getChildren().getLast();
                label = (Label) holeAnchorPane.getChildren().get(1);


                Hole hole = new Hole(holeKey, START_BALL_COUNT, grid, holeAnchorPane, (Rectangle)holeAnchorPane.getChildren().getFirst(), label);
                HOLES.put(holeKey, hole);

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

                Hole hole = new Hole(holeKey, ballsAmount, grid, holeAnchorPane, (Rectangle)holeAnchorPane.getChildren().getFirst(), label);
                HOLES.put(holeKey, hole);

                // update the label score (in UI)
                hole.updateBallCountLabel(false);

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
            CPUAction(gameTurn, true);
        }
        else {
            pausePlay.setVisible(false);
        }
    }

    protected void selectHole(short holeKey) {
        logAction("log hole 5 amount: " + getHoleBallCount((short) 5));
        HashMap<Short, Hole> emuHoles = deepCopyHashMap(HOLES);
        boolean emu_turn = gameTurn;
        short emuKey = holeKey;
        Object[] test = emulateSelectHole(emuKey, emuHoles, emu_turn);

       // logAction("**test emu - added score: " + (int)test[0] + " algorithm " + (int)test[1]);
        logAction("log hole 5 amount: " + getHoleBallCount((short) 5));

        if (canPlay || !isGameOver) {
            Hole hole = HOLES.get(holeKey);
            if (pause) {
                waitingQueue = holeKey;
            } else {

                short ballsCount = hole.ballCount;
                short i;
                logAction(("Hole" + holeKey + " selected"), 2);
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
                    // move every ball in the hole to the next holes
                    if (holeKey == 0) {
                        nextHoleKey = P1_MAIN_HOLE_KEY; // the next hole after p1 hole
                    } else if (holeKey == 11) {
                        nextHoleKey = P2_MAIN_HOLE_KEY; // the next hole after p2 hole
                    } else {
                        // if the holeIndex is in row2 we need to move forward (increase the index by 1) in the holes list
                        if (holeKey >= 6) {
                            nextHoleKey++;
                        } else {
                            // if the holeIndex is in row1 we need to move backwards (reduce the index by 1) in the holes list
                            nextHoleKey--;
                        }
                    }

                    for (i = 0; i < ballsCount; i++) {
/*                       Timeline moveDelay = new Timeline(new KeyFrame(Duration.millis(150), event -> {
                           //Delay...
                        }));
                         moveDelay.play();
*/
                        MoveBallTo(hole, HOLES.get(nextHoleKey));
                        this.lastHoleKey = nextHoleKey;

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
                        logAction("Last hole: " + this.lastHoleKey, 2);
                        //make delay
                    }


                    // if the last ball get into the player's hole, he will get another turn
                    if (gameTurn && (this.lastHoleKey == P1_MAIN_HOLE_KEY)) {
                        if (isAllPlayerHolesEmpty(true)) {
                            changeTurn(true);
                        } else {
                            // player 1 get another turn
                            bonus_turn_sound();
                            new Scale().scale(game_turn_label, 300, 1.1, 2);
                        }
                    } else {
                        if (!gameTurn && (this.lastHoleKey == P2_MAIN_HOLE_KEY)) {
                            if (isAllPlayerHolesEmpty(false)) {
                                changeTurn(false);
                            } else {
                                // Player 2 get another turn
                                bonus_turn_sound();
                                new Scale().scale(game_turn_label, 300, 1.1, 2);
                            }
                        } else {
                            Hole lastHole = HOLES.get(this.lastHoleKey);
                            short lastHoleBallCount = lastHole.ballCount;

                            // If the last ball get into an empty hole of
                            if (gameTurn && lastHoleBallCount == 1 && holeKey != P1_MAIN_HOLE_KEY && holeKey != P2_MAIN_HOLE_KEY) {
                                short parallelHoleKey = (short) (this.lastHoleKey + 6);

                                // Take the parallel hole balls just if the last ball of player 1 end in his own hole and the parallel hole have at least 1 ball
                                // So if the parallelHoleId is grater then 11 it means that the last ball is not landed in the player 1 era
                                if (parallelHoleKey <= 11) {
                                    // get the parallel hole
                                    Hole parallelHole = HOLES.get(parallelHoleKey);

                                    // Check if the parallel hole has at least 1 ball
                                    if (parallelHole.ballCount > 0) {
                                        logAction("Player 1 last ball got into an empty hole", 1);

                                        // Move all the balls from the parallel hole to player 1 hole
                                        if(parallelHole.ballCount > 0) {
                                            achievement_sound();
                                            for (short j = 0; j < parallelHole.ballCount; j++) {
                                                MoveBallTo(parallelHole, P1_MAIN_HOLE);
                                            }
                                        }
                                        // Move the last ball from the last hole to player 1 main hole
                                        MoveBallTo(lastHole, P1_MAIN_HOLE);

                                        // Update parallelHole and lastHole hole score
                                        parallelHole.updateBallCountLabel(true);
                                        HOLES.get(lastHoleKey).updateBallCountLabel(true);
                                        changeTurn(false);
                                    }
                                }

                                // If all player2 holes are empty player1 get another turn in order to finish the game
                                // If the ball end in an empty hole of player1, player2 should get the next turn
                                changeTurn(isAllPlayerHolesEmpty(true));

                            } else if (!gameTurn && (lastHoleBallCount == 1) && holeKey != P1_MAIN_HOLE_KEY && holeKey != P2_MAIN_HOLE_KEY) {
                                short parallelHoleKey = (short) (this.lastHoleKey - 6);

                                // take the parallel hole balls to the player's main hole just if the last ball of player 2 end in his own hole and the parallel hole have at least 1 ball
                                // so if the parallelHoleId is smaller than 0 it means that the last ball is not landed in the player 2 era
                                if (parallelHoleKey >= 0) {
                                    // get the parallel hole
                                    //GridPane parallelHole = getHoleFromId(parallelHoleId);

                                    // get the parallel hole
                                    Hole parallelHole = HOLES.get(parallelHoleKey);

                                    // Check if the parallel hole has at least 1 ball
                                    if (parallelHole.ballCount > 0) {
                                        logAction("Player 2 last ball got into an empty hole", 1);

                                        // move the balls from the parallel hole and the last hole to player 2 hole
                                        if(parallelHole.ballCount > 0) {
                                            achievement_sound();
                                            for (int j = 0; j < parallelHole.ballCount; j++) {
                                                MoveBallTo(parallelHole, P2_MAIN_HOLE);
                                            }
                                        }

                                        // move the last ball from the last hole to player 1 hole
                                        MoveBallTo(lastHole, P2_MAIN_HOLE);

                                        //update parallelHole and lastHole hole score
                                        parallelHole.updateBallCountLabel(true);
                                        lastHole.updateBallCountLabel(true);
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
                    highlightHole(hole, Color.WHEAT, false);

                    //update the hole score
                    hole.setBallCount((short) 0, true, false);
                    select_hole_sound();
                    canPlay = true;
                }
                else {
                    disable_hole_sound();
                }
                // CPU action (if the CPU turn and not PVP mode)
                if ((GAME_TYPE == PVC && !gameTurn) || GAME_TYPE == CVC) {
                    CPUAction(gameTurn, true);
                }
            }
        }
        else{
                logAction("canPlay variable is: " + canPlay + "isGameOver variable is: " + isGameOver + ".", 2);
            }
    }


    /**
     *  Emulates hole selection
     * @return
     * Object[] with two values:
     * index 0 = the number of balls earned,
     * index 1 = the AlgorithmType of the algorithm that take effect
     * if the provided holeKey can't be selected, it will return -1 in index 0 and index 1.
     * @param holeKey
     * the holeKey to be select
     * @param emulatedHoles
     * the emulated Holes (as the General -> HOLES structure)
     * @param gameTurn
     * the player turn: 1 = player 1,
     * 2 = player 2
     *
     */
    protected Object[] emulateSelectHole(short holeKey, HashMap<Short, Hole> emulatedHoles, boolean gameTurn) {
        logAction("**emulate** Hole" + holeKey + " selected", 2);

        Hole src = emulatedHoles.get(holeKey);
        short ballsCount = src.ballCount;
        short addedScore = 0;
        AlgorithmType algorithm = nun;
        Hole mainHole = emulatedHoles.get(gameTurn? P1_MAIN_HOLE_KEY:P2_MAIN_HOLE_KEY);
        short prevMainHoleBallCount = mainHole.ballCount; // get the amount of balls that in the player's main hole

        boolean canClick; // if the hole can be select (if is the player turn)

        // check if is the player turn (if the player's row can be clicked new)
        if (gameTurn) {
            canClick = (holeKey >= 0 && holeKey < 6);
        } else {
            canClick = (holeKey >= 6 && holeKey < 12);
        }

        short nextHoleKey = holeKey;
        if (canClick && ballsCount > 0) {

            // move every ball in the hole to the next holes
            if (holeKey == 0) {
                nextHoleKey = P1_MAIN_HOLE_KEY; // the next hole after p1 hole
            } else if (holeKey == 11) {
                nextHoleKey = P2_MAIN_HOLE_KEY; // the next hole after p2 hole
            } else {
                // if the holeIndex is in row2 we need to move forward (increase the index by 1) in the holes list
                if (holeKey >= 6) {
                    nextHoleKey++;
                } else {
                    // if the holeIndex is in row1 we need to move backwards (reduce the index by 1) in the holes list
                    nextHoleKey--;
                }
            }
            short i;
            short lastHole = holeKey;


            Hole dest;

            for (i = ballsCount; i > 0; i--) {

                if(holeKey != nextHoleKey) {

                    //update source ball count
                    src.setBallCount(i);

                    // update dest ball count
                    dest = emulatedHoles.get(nextHoleKey);
                    dest.incBallCount();

                }
                lastHole = nextHoleKey;

                if (nextHoleKey == P1_MAIN_HOLE_KEY) {
                    nextHoleKey = 6; // the next hole after p1 hole
                } else if (nextHoleKey == P2_MAIN_HOLE_KEY) {
                    nextHoleKey = 5; // the next hole after p2 hole
                } else {
                    //if the holeIndex is in row2, we need to move forward (increase the index by 1) in the holes list
                    if (nextHoleKey >= 6 && nextHoleKey <= 11) {
                        nextHoleKey++;
                    } else {
                        //if the holeIndex is in row1, we need to move backwards (reduce the index by 1) in the holes list
                        nextHoleKey--;
                    }
                }
                logAction("**emulate** Last hole: " + lastHole, 2);
            }


            // if the last ball gets into the player's hole, he will get another turn
            if (gameTurn && (lastHole == P1_MAIN_HOLE_KEY)) {
                if (!isAllPlayerHolesEmpty(true, emulatedHoles)) {
                    // player 1 get another turn
                    algorithm = extraTurn;
                }
            }
            else {
                if (!gameTurn && (lastHole == P2_MAIN_HOLE_KEY)) {
                    if (!isAllPlayerHolesEmpty(false, emulatedHoles)) {
                        // Player 2 get another turn
                        algorithm = extraTurn;
                    }
                } else {
                    short lastHoleBallCount = emulatedHoles.get(nextHoleKey).ballCount;

                    // If the last ball get into an empty hole
                    if (gameTurn && lastHoleBallCount == 1 && holeKey != P1_MAIN_HOLE_KEY && holeKey != P2_MAIN_HOLE_KEY) {
                        short parallelHoleKey = (short) (lastHole + 6);

                        // Take the parallel hole balls just if the last ball of player 1 ends in his own hole and the parallel hole has at least 1 ball
                        // So if the parallelHoleId is grater then 11 it means that the last ball is not landed in the player 1 era
                        if (parallelHoleKey <= 11) {
                            // get the parallel hole


                            // Check if the parallel hole has at least 1 ball
                            short parallelHoleBallsCount = emulatedHoles.get(parallelHoleKey).ballCount;
                            if (parallelHoleBallsCount > 0) { // getHoleBallCount but more efficient
                                algorithm = Parallel;
                                logAction(" **emulate** Player 1 last ball got into an empty hole", 1);

                                // Move all the balls from the parallel hole to player 1 main hole
                                    Hole parallelHole = emulatedHoles.get(parallelHoleKey);


                                    // add the amount of balls that in the parallel hole plus the player's last ball (that get into the empty hole)
                                    mainHole.setBallCount((short) (mainHole.ballCount + parallelHole.ballCount + 1));

                                    // set the parallel hole ball count to 0
                                    parallelHole.setBallCount((short) 0);


                                // end turn
                                //changeTurn(false);
                            }
                        }

                        // If all player2 holes are empty, player1 get another turn in order to finish the game
                        // If the ball end in an empty hole of player1, player2 should get the next turn
                        if(isAllPlayerHolesEmpty(true, emulatedHoles))
                            algorithm = extraTurn;


                    } else if (!gameTurn && (lastHoleBallCount == 1) && holeKey != P1_MAIN_HOLE_KEY && holeKey != P2_MAIN_HOLE_KEY) {

                        short parallelHoleKey = (short) (lastHole - 6);

                        // take the parallel hole balls to the player's main hole just if the last ball of player 2 end in his own hole and the parallel hole have at least 1 ball
                        // so if the parallelHoleId is smaller than 0 it means that the last ball is not landed in the player 2 era

                        if (parallelHoleKey >= 0) {
                            // get the parallel hole

                            // Check if the parallel hole has at least 1 ball
                            short parallelHoleBallsCount = (short)emulatedHoles.get(parallelHoleKey).ballCount;
                            if (parallelHoleBallsCount > 0) { // getHoleBallCount but more efficient

                                logAction(" **emulate** Player 2 last ball got into an empty hole", 1);
                                algorithm = Parallel;
                                Hole parallelHole = emulatedHoles.get(parallelHoleKey);
                                // add the amount of balls that in the parallel hole plus the player's last ball (that get into the empty hole)
                                mainHole.setBallCount((short) (mainHole.ballCount + parallelHole.ballCount + 1));

                                // set the parallel hole ball count to 0
                                parallelHole.setBallCount((short) 0);
                            }
                        }

                        // If all player1 holes are empty, player2 get another turn in order to finish the game
                        // If the ball end in an empty hole of player 2, player 1 should get the next turn
                        if(!isAllPlayerHolesEmpty(true, emulatedHoles))
                            algorithm = extraTurn;

                    } else
                        algorithm = MaxAmount;
                }
            }
            addedScore = (short) (mainHole.ballCount - prevMainHoleBallCount);
        } else {
            logAction("**emulate** cant select hole- canClick: " + canClick + ", balls count = " + ballsCount);
            return new Object[]{-1, nun};
        }

        return new Object[] {addedScore, algorithm};

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

        Rectangle hole_style = HOLES.get(holeKey).rect;
        if(state)
        {
            hole_style.setStroke(color);
            hole_style.setStrokeWidth(5);
        }
        else {
            hole_style.setStrokeWidth(0);
        }

    }
    public void highlightHole(Hole hole, Color color, boolean state){

        Rectangle hole_style = hole.rect;
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

        // Open new game window
        new Main().start(new Stage());

        //startGame(new Stage(), GAME_TYPE);

    }
    @FXML
    protected void holeMouseHover(MouseEvent event) {
        //System.out.println("holePressedDown()");
        short holeKey = getHoleKey((AnchorPane) event.getSource());
        if(gameTurn) {
            if (holeKey >= 0 && holeKey < 6 && (GAME_TYPE != CVC)) {
                // If clickable
                hover_sound();
                highlightHole(holeKey,Color.WHEAT, true);
            }
            else {
                // If not clickable
                disable_hover_sound();
                highlightHole(holeKey,Color.rgb(255,255,255,0.21), true);
            }
        }
        else {
            if (holeKey >= 6 && holeKey < 12 && (GAME_TYPE == PVP)) {
                // If clickable
                hover_sound();
                highlightHole(holeKey,Color.WHEAT, true);
            }
            else {
                // If not clickable
                disable_hover_sound();
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
        if(canPlay && (GAME_TYPE == PVC && gameTurn) || GAME_TYPE == PVP){
            selectHole(holeIndex);}
        else if (GAME_TYPE == CVC){
            disable_btn_sound();
            logAction("Hole can't be selected in CVC mode.");
        }
        else{
            disable_btn_sound();
            logAction("Hole can't be selected - it is not the user turn.");
        }
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
                move_all_player_balls(false, P1_MAIN_HOLE);
                showWindow(P1_MAIN_HOLE.ballCount, P2_MAIN_HOLE.ballCount);
            }
            else {
                game_turn_label.setText("Player 1 turn");
                //playerSwing.translate(player1_name);
                new FadeAnimation().fadeAnimation(player1_name, 300, false);
            }
        }
        else {
            if(isAllPlayerHolesEmpty(false))
            {
                // If all the holes empty
                logAction("changeTurn: FOUND that all the holes empty in P2", 1);
                move_all_player_balls(true, P2_MAIN_HOLE);
                showWindow(P1_MAIN_HOLE.ballCount, P2_MAIN_HOLE.ballCount);
            }
            else {
                game_turn_label.setText("Player 2 turn");
                //playerSwing.translate(player2_name);
                new FadeAnimation().fadeAnimation(player2_name, 300, false);
            }
        }
        new FadeAnimation().fadeAnimation(game_turn_label, 300, false);
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

    /** return true if all the player's hols are empty - with the provided emulatedHoles
     */
    protected boolean isAllPlayerHolesEmpty(boolean player, HashMap<Short, Hole> emulatedHoles)
    {

        short test;
        //System.out.println("******"+emulatedHoles);
        if(player)
            for (short i = 0; i < 6; i++) {
                test = emulatedHoles.get(i).ballCount;
                logAction("+++=====::: " + test);
                if (emulatedHoles.get(i).ballCount > 0)
                    return false;
            }
        else
            for (short i = 6; i < 12; i++) {
                if (emulatedHoles.get(i).ballCount > 0)
                    return false;
            }
        return true;
    }


    //Move all the balls of the source player to the target hole
    private void move_all_player_balls(boolean source_player, Hole target_hole) {

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
        logAction("Move all the balls of the source player: " + (source_player? "P1":"P2") + ", to the target hole: " + target_hole.holeKey, 1);
        short j, holeBallCount;
        Hole hole;
        for (short i = startHoleId; i <= endHoleId; i++) {
            holeBallCount = getHoleBallCount(i);
            if(holeBallCount > 0){
                hole = HOLES.get(i);

                for (j = 0; j <= holeBallCount; j++) {
                    //logAction("-----"+ i +", count " + getHoleBallCount(holes[i]), 1);
                    MoveBallTo(hole, target_hole);
                }
                hole.updateBallCountLabel(true);
            }
        }
    }


    public void MoveBallTo(Hole source, Hole dest)
    {

        if(source == dest)
        {
            logAction("The source is equal to the destination! (it could be ok in case of hole with 14 or more balls)", 1);
        }
        else if(source.ballCount > 0) {
            // If there is at least 1 ball in the hole

           logAction("Move ball from source: " + source + ", to the destination: " + dest, 2);

            GridPane sourceGrid = source.grid;
            GridPane destGrid = dest.grid;

            // Move the ball to the destination hole
            Ball ball = (Ball) sourceGrid.getChildren().getLast();

            // Move the ball to the destination
            addBallToGrid(destGrid, ball, dest.ballCount);

            // Update the ball amount on the source
            source.decBallCount(true);


            // Update the destination hole score
            dest.incBallCount(true);

            // Make pop animation
            Scale scale = new Scale();
            scale.scale(ball, 150, -0.1, 1);
            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(150), e -> {
                scale.scale(ball, 150, 0.1, 1);
            }));
            timeline.play();
        }
        else {
           logAction("There is no balls in source!", 1);
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
            Translate swing = new Translate();
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

            try {
                 new Translate().translate(player2_name);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        win_window.setVisible(true);
        new Scale().scale(win_window, 500, 0.5, 2);

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
        new General().startGame(new Stage(), GAME_TYPE);
    }



    @FXML
    protected void pausePlay(){
        if(pause) {
            pausePlay.setId("pause");
            pause = false;
            if (waitingQueue != -2)
            {
                logAction("hole "+ waitingQueue + " has selected from the waiting queue.", 2);
                selectHole(waitingQueue);
                waitingQueue = -2;
            }
            else if(GAME_TYPE == CVC){
                CPUAction(gameTurn, true);
            }
        }
        else {
            pausePlay.setId("play");
            pause = true;
        }
    }
    @FXML
    protected void soundToggle(){
        if(gameSound) {
            soundToggle.setId("sound_off_btn");
            gameSound = false;
        }
        else {
            soundToggle.setId("sound_btn");
            gameSound = true;
        }

    }
    @FXML
    protected void hole_in(MouseEvent mouseEvent){
        if(mouseEvent.getSource() instanceof Pane)
            new Scale().scale(((Pane)mouseEvent.getSource()).getChildren().getLast(), 150, -0.1, 1);
        else {
            new Scale().scale((Node) mouseEvent.getSource(), 150, -0.1, 1);
        }
    }
    @FXML
    protected void hole_out(MouseEvent mouseEvent){
        if(mouseEvent.getSource() instanceof Pane)
            new Scale().scale(((Pane)mouseEvent.getSource()).getChildren().getLast(), 150, 0.1, 1);
        else
            new Scale().scale((Node)mouseEvent.getSource(), 150, 0.1, 1);
    }


    // Highlight the hole the CPU would choose
    @FXML
    protected void cpuHint(){
        if(GAME_TYPE != CVC)
            CPUAction(gameTurn, false);
    }


    /** this function get the CPU player (P1 = ture, P2 = false) and return the hole index to be select by the CPU algorithm.
        * if select set to true the hole will be selected, if false, the hole will only be highlighted
     **/
    public void CPUAction(boolean CPU_player, boolean select)  {

        if(!isGameOver) {
            logAction("Wait for CPU action");

            short holeKey = Look_ahead_algorithm(CPU_player);

            GridPane holeGrid = getGridFromHoleKey(holeKey);

            logAction("CPU choice: " + holeKey);
            highlightHole(holeKey, Color.RED, true);
            hover_sound();

            if(select) {
                // Set delay for select
                short finalHoleKey = holeKey;

                Timeline timeline = new Timeline(new KeyFrame(dellay, e -> {
                    // Code that interacts with UI elements
                    logAction("Delay done, now select the hole: " + finalHoleKey);

                    new Scale().scale(holeGrid, 150, -0.1, 1);
                    Timeline animationDelay = new Timeline(new KeyFrame(Duration.millis(150), event -> {
                        new Scale().scale(holeGrid, 1, 0.1, 1);

                        // Select the hole
                        selectHole(finalHoleKey);
                    }));
                    animationDelay.play();
                }));
                timeline.play();
            }
            else {
                    // Code that interacts with UI elements
                new Scale().scale(holeGrid, 300, 0.15, 2);
            }
        }
    }


    /** This function get the CPU player (true = player1 false = player 2)
     *  run the cpu algorithm (it select the hole only by looking the current state - not run the- Look-Ahead Algorithm) and return the result
     */
    protected short CPU_hole_selector(boolean CPU_player, short[] holesRange){

        short holeKey = bestHoleForParallelRol(CPU_player, holesRange);
        logAction("CPUAction: Best Hole For Parallel Rol: "+ holeKey);

        if(holeKey == -1){
            holeKey = bestHoleToGetAnotherTurn(CPU_player, holesRange);
            logAction("CPUAction: Best Hole To Get Another Turn: "+ holeKey);
        }
        if(holeKey == -1)
        {
            holeKey = getMaxBallsHole(CPU_player, holesRange);
            logAction("CPUAction: Get Max Balls Hole: "+ holeKey);
        }

        return holeKey;
    }

    /**
     * Look-Ahead Algorithm
     *
     * Going over each hole of the player and checking how many balls each choice earns the player.
     * If another turn is received, we will run the algorithm again on all the holes and add the balls we earned in this step to the previous run.
     */
    protected short Look_ahead_algorithm(boolean CPU_player){
        HashMap<Short, Hole> holesCopy = (HashMap<Short, Hole>) HOLES.clone();
        boolean extraTurnRole = false;
        short score = 0;


        //list with 2 parameters - index 0 = the start holeKey of the player and index 1 = the last holeKey of the player.
        short[] holesRange = CPU_player ? P1_holesRange : P2_holesRange;
        short holeKey = CPU_hole_selector(CPU_player, holesRange);

        // Find the hole that can get the most score
        for (short i = (short) (holesRange[0] + 1); i<= holesRange[1]; i++){


        }

        return holeKey;
    }


    /** This function returns the holeKey that you can use to apply the law of parallelism
     * if there is no hole that can apply the rol- return -1
     **/
    protected short bestHoleForParallelRol(boolean player, short[] holesRange) {
        short parallelHoleBallsCount;
        short maxHoleKey = -1;
        short maxParallelHoleBalls = 0;
        short endHole;


        for (short holeKey = holesRange[0]; holeKey <= holesRange[1]; holeKey++) {
            endHole = getEndHole(holeKey);
            if (endHole != -2)
            {
                parallelHoleBallsCount = getHoleBallCount(getParallelHole(endHole));
                /** If the parallel hole can be used on the endHole
                 * if the there is no balls in the end hole and the end hole is of this player and the amount of balls in the parallel hole if more then 0
                 * the parallel law can be used
                 * */
                logAction("get parallel hole for: " + holeKey + "if: " + (endHole != P1_MAIN_HOLE_KEY && endHole != P2_MAIN_HOLE_KEY && getHoleBallCount(endHole) == 0 && (PlayerOfHoleKey(endHole) == player) && parallelHoleBallsCount > 0), 2);
                if (endHole != P1_MAIN_HOLE_KEY && endHole != P2_MAIN_HOLE_KEY && getHoleBallCount(endHole) == 0 && (PlayerOfHoleKey(endHole) == player) && parallelHoleBallsCount > 0) {
                    if (maxParallelHoleBalls < parallelHoleBallsCount) {
                        maxParallelHoleBalls = parallelHoleBallsCount;
                        maxHoleKey = holeKey;
                    }
                }
            }
    }
        return maxHoleKey;

    }

    /**
     * This function return true if the provided holeKey is one of the Player1 hols and false if one of the Player2 holes
    **/
    protected boolean PlayerOfHoleKey(short holeKey){
        return (holeKey >= P1_holesRange[0]-1) && (holeKey <= P1_holesRange[1]);
    }

    /**
     * This function return the parallel hole for the given holeKey
     **/
    protected short getParallelHole(short holeKey){
        if(PlayerOfHoleKey(holeKey)){
            return (short) (holeKey+6);
        }
        else
            return (short) (holeKey-6);
    }


    /** This function returns the holeKey that the last ball of the provided holeKey will end
     * if there is no balls on the provided hole it will return -2.
     **/
    public short getEndHole(short holeKey) {
        short ballCount = getHoleBallCount(holeKey);
        if (ballCount == 0)
            return -2;
        else {
            short endHoleKey = holeKey;
//        logAction("*******************");
//        logAction("for: "+ holeKey);

            while (ballCount > 0) {
                if (PlayerOfHoleKey(endHoleKey)) {
                    if (endHoleKey == P1_MAIN_HOLE_KEY)
                        endHoleKey = 6;
                    else
                        endHoleKey--;
                    //logAction(endHoleKey + "++++");
                } else {
                    if (endHoleKey == P2_MAIN_HOLE_KEY)
                        endHoleKey = 5;
                    else
                        endHoleKey++;
                    //logAction(endHoleKey + "-----");
                }
                ballCount--;
            }
            //logAction(endHoleKey);
            return endHoleKey;
        }
    }


    // This function returns the holeKey that can give you another turn
    // -1 = hole not found.
    public short bestHoleToGetAnotherTurn(boolean player, short[] holesRange)  {
        logAction(player+"///");
        short holeKey = -1;
        short ballCount;
        if(player)
        {

            for (short key = holesRange[0]; key <= holesRange[1]; key++)
            {
                ballCount = getHoleBallCount(key);
                if(ballCount > 0 && (key - ballCount == P1_MAIN_HOLE_KEY))
                {
                    holeKey = key;
                    break;
                }
            }
        }
        else {
            // The order for player2 is from the end to start hole because it better to select the hole that is closer to the main hole
            // This approach can get an extra turn in case the last hole has one ball and the hole in the start has two balls
            // (in this case you can get 3 turns compared to scanning from start to finish)
            for (short key = holesRange[1]; key >= holesRange[0]; key--)
            {
                ballCount = getHoleBallCount(key);
                if(ballCount > 0 && (key + getHoleBallCount(key) == P2_MAIN_HOLE_KEY))
                {
                    holeKey = key;
                    break;
                }
            }
        }
        return holeKey;
    }

    // This function returns the holeKey with the most balls
    public short getMaxBallsHole(boolean player, short[] holesRange)  {

        short holeKey = holesRange[0];
        short maxBallCount = getHoleBallCount(holeKey);
        short temp;
        for (short i = (short) (holesRange[0] + 1); i <= holesRange[1]; i++) {
            temp = getHoleBallCount(i);
            if (maxBallCount < temp) {
                maxBallCount = temp;
                holeKey = i;
            }
        }
        return holeKey;
    }



}