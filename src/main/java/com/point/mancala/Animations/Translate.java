package com.point.mancala.Animations;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

import java.io.IOException;

public class Translate extends Animations{
    public TranslateTransition translate = new TranslateTransition();

    public void translate(Node node) throws IOException {
        logAction("translate animation", 3);
        //stopAnimation();
        translate.stop();
        translate.setNode(node);
        translate.setDuration(Duration.millis(1000));
        translate.setCycleCount(TranslateTransition.INDEFINITE);
        translate.setByX(0);
        translate.setByY(-15);
        translate.setAutoReverse(true);
        translate.playFromStart();
    }
    public void stopTranslate(){
        this.translate.setFromY(0);
        this.translate.stop();

    }
}
