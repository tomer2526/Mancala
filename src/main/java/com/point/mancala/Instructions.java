package com.point.mancala;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Instructions extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Instructions.class.getResource("Instructions.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Mancala Instructions");
        stage.setScene(scene);
        stage.show();
    }

}
