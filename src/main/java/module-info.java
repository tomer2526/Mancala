module com.point.mancala.mancala {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.point.mancala to javafx.fxml;
    exports com.point.mancala;


    exports com.point.mancala.Animations;
    opens com.point.mancala.Animations to javafx.fxml;
}