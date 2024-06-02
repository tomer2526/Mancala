package com.point.mancala.Animations;

import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class Scale extends Animations{
    private ScaleTransition scale = new ScaleTransition();

    public void scale(Node node, int duration, double scaleBy, int setCycleCount) {
        // to repate set setCycleCount to -1 (TranslateTransition.INDEFINITE = -1)
        logAction("scale animation", 3);
        // scale
        scale.setNode(node);
        scale.setDuration(Duration.millis(duration));
        scale.setCycleCount(setCycleCount);
        scale.setInterpolator(Interpolator.EASE_BOTH);
        scale.setFromY(0.85);
        scale.setFromX(0.85);
        scale.setByX(scaleBy);
        scale.setByY(scaleBy);
        scale.setAutoReverse(true);
        scale.playFromStart();

    }
}
