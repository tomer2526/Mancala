module com.point.mancala.mancala {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.point.mancala to javafx.fxml;
    exports com.point.mancala;
    exports com.point.mancala.notInUse;
    opens com.point.mancala.notInUse to javafx.fxml;
}