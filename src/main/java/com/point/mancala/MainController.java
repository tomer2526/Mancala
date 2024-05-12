package com.point.mancala;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.point.mancala.GameType.*;
import static com.point.mancala.General.*;

public class MainController extends UIUX implements Initializable {
    public static Stage primaryStage;
    private GameType GT;
    private GameDifficulty CPU1_diff;
    private GameDifficulty CPU2_diff;

    @FXML
    private VBox gameMode_layout;


    @FXML
    private VBox difficulty_layout;
    //Stage stage = (Stage) difficulty_layout.getScene().getWindow();
    @FXML
    private VBox cpu2_difficulty_layout;

    @FXML
    private Button CPU1_Eazy;
    @FXML
    private Button CPU1_Normal;
    @FXML
    private Button CPU1_Hard;
    @FXML
    private Button CPU2_Eazy;
    @FXML
    private Button CPU2_Normal;
    @FXML
    private Button CPU2_Hard;
    @FXML
    private Button back;



    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        difficulty_layout.setVisible(false);
        difficulty_layout.managedProperty().bind(difficulty_layout.visibleProperty());
//
//        gameMode_layout.setVisible(true);
    }

    @FXML
    protected void pvpClick() throws Exception {
        //open a new game window
        start_game_sound();
        new General().startGame(new Stage(), PVP);
    }
    @FXML
    protected void pvcClick() throws Exception {
        //open a new game window
        GT = PVC;
        //new General().startGame(new Stage(), PVC, GameDifficulty.hard);
        ChooseDifficulty(GT);
    }
    @FXML
    protected void cvcClick() throws Exception {
        GT = CVC;
        ChooseDifficulty(GT);
    }

    @FXML
    protected void instructions()throws Exception {
        logAction("Open Instructions page", 2);
        FXMLLoader fxmlLoader = new FXMLLoader(Game.class.getResource("Instructions.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        //if (primaryStage == null) {
            primaryStage = new Stage();
            primaryStage.setTitle("Instructions");
            primaryStage.setScene(scene);
            primaryStage.show();
       /* }
        else {
            primaryStage.toFront();
        }*/

    }

    private void ChooseDifficulty(GameType gt){
        try {
            small_btn_in_sound();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        cpu2_difficulty_layout.setDisable(gt == PVC);

        gameMode_layout.setVisible(false);
        gameMode_layout.managedProperty().bind(gameMode_layout.visibleProperty());

        difficulty_layout.setVisible(true);
        difficulty_layout.managedProperty().bind(difficulty_layout.visibleProperty());
        //stage.minHeightProperty().bind(stage.heightProperty());

        back.setVisible(true);

    }

    @FXML
    protected void back()throws Exception {
        small_btn_in_sound();
        back.setVisible(false);
        difficulty_layout.setVisible(false);
        difficulty_layout.managedProperty().bind(difficulty_layout.visibleProperty());

        gameMode_layout.setVisible(true);
        gameMode_layout.managedProperty().bind(gameMode_layout.visibleProperty());
    }

    private void checkBtn(boolean player, GameDifficulty gd) {
        try {
            small_btn_in_sound();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if(player) {
            CPU1_diff = gd;
            CPU1_Eazy.setId("button");
            CPU1_Normal.setId("button");
            CPU1_Hard.setId("button");

            if(gd == GameDifficulty.easy)
                CPU1_Eazy.setId("checked_button");
            else if (gd == GameDifficulty.normal)
                CPU1_Normal.setId("checked_button");
            else
                CPU1_Hard.setId("checked_button");
        }
        else
        {
            CPU2_diff = gd;
            CPU2_Eazy.setId("button");
            CPU2_Normal.setId("button");
            CPU2_Hard.setId("button");

            if(gd == GameDifficulty.easy){
                CPU2_Eazy.setId("checked_button");
            }
            else if (gd == GameDifficulty.normal)
                CPU2_Normal.setId("checked_button");
            else
                CPU2_Hard.setId("checked_button");
        }
    }

    public void start_game(ActionEvent event) throws IOException {

        if(GT == PVC)
            if(CPU1_diff == null)
                disable_btn_sound();
            else
            {
                start_game_sound();
                new General().startGame(new Stage(), GT, CPU1_diff);
            }
        else if (GT == PVP) {
            new General().startGame(new Stage(), GT, GameDifficulty.hard);
        } else
            if(CPU1_diff == null || CPU2_diff == null)
                disable_btn_sound();
            else{
                start_game_sound();
                new General().startGame(new Stage(), GT, CPU1_diff, CPU2_diff);
            }
    }

    public void CPU1_EazyClick(ActionEvent event) throws IOException {
        checkBtn(true, GameDifficulty.easy);

    }
    public void CPU1_NormalClick(ActionEvent event) throws IOException {
        checkBtn(true, GameDifficulty.normal);

    }
    public void CPU1_hardClick(ActionEvent event) {
        checkBtn(true, GameDifficulty.hard);
    }


    public void CPU2_EazyClick(ActionEvent event) {
        checkBtn(false, GameDifficulty.easy);
    }
    public void CPU2_NormalClick(ActionEvent event) {
        checkBtn(false, GameDifficulty.normal);
    }

    public void CPU2_hardClick(ActionEvent event) {
        checkBtn(false, GameDifficulty.hard);
    }
}