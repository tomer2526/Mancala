package com.point.mancala;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static com.point.mancala.General.logAction;

public class Game extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    //start pvp game
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Game.class.getResource("game.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setResizable(false);
        primaryStage.setTitle("PVP Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
