module com.playing.maze {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.playing.maze to javafx.fxml;
    exports com.playing.maze;
}