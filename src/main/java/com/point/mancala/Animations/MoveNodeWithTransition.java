package com.point.mancala.Animations;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
public class MoveNodeWithTransition extends Animations{
    public static void moveNodeWithTransition(Node node, Pane newParent, Duration duration){

        if(!inTransition) {
            inTransition = true;
            TranslateTransition transition = new TranslateTransition(duration, node);

            double nodeXLocation = node.getBoundsInParent().getMinX(); // get the node X location in its parent
            double destNodeXLocation = newParent.getChildren().getLast().getBoundsInParent().getMaxX(); // get the destination of node X location in its new parent

            logAction("nodeXLocation: " + nodeXLocation + ", destNodeXLocation: " + destNodeXLocation, 2);
            // decrease or increase the x location of the node (from 0 - the x location of the node)
            double xdest = (Math.max(nodeXLocation, destNodeXLocation) - Math.min(nodeXLocation, destNodeXLocation)) * ((nodeXLocation > destNodeXLocation) ? -1 : 1);


            double nodeYLocation = node.getParent().getBoundsInParent().getMinY(); // get the node Y location in its parent
            double destNodeYLocation = newParent.getBoundsInParent().getMinY(); // get the destination of node Y location in its new parent
            logAction("nodeYLocation: " + nodeYLocation + ", destNodeYLocation: " + destNodeYLocation, 2);

            // decrease or increase the y location of the node (from 0 - the y location of the node)
            double ydest = (Math.max(nodeYLocation, destNodeYLocation) - Math.min(nodeYLocation, destNodeYLocation)) * ((nodeYLocation > destNodeYLocation) ? -1 : 1);

            logAction("X: " + xdest + ", dest Y: " + ydest, 2);

            transition.setToX(xdest); // set the X transition
            transition.setToY(ydest); // set the Y transition
            transition.playFromStart(); // start the transition animation


            // action to be performed after the transition ends
            // add the node to the new parent (witch also remove it from its previous parent)
            transition.setOnFinished(e -> {
                logAction("Transition ended! add the node to the new parent: " + newParent.getId(), 2);
                newParent.getChildren().add(node);
                node.setTranslateX(0);
                node.setTranslateY(0);
                inTransition = false;
            });
        }
        else {
            logAction("Can't make another transition, there is active transition now!", 2);
        }

    }
}
