package com.point.mancala;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static com.point.mancala.General.*;

public class MainController {
    Stage primaryStage;
    @FXML
    protected void pvpClick() throws Exception {
        //open new game window
        startPVP(new Stage());
    }
    @FXML
    protected void pvcClick() throws Exception {
        //open new game window
        startPVC(new Stage());
    }
    @FXML
    protected void cvcClick() throws Exception {
        //open new game window
        startCVC(new Stage());
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
}