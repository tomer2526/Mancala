package com.point.mancala.Animations;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class FadeAnimation extends Animations{
    private final FadeTransition fade = new FadeTransition();

    public void fadeAnimation(Node node, int duration, boolean repeat) {
        logAction("fade animation", 3);
        // fade

        fade.setNode(node);
        fade.setDuration(Duration.millis(duration));
        if(repeat)
            fade.setCycleCount(TranslateTransition.INDEFINITE);
        fade.setInterpolator(Interpolator.EASE_IN);
        fade.setFromValue(0);
        fade.setToValue(1);
        //fade.play();
        fade.playFromStart();
    }
}
