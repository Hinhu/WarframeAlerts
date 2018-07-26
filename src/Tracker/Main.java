package Tracker;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane root = new Pane();
        Scene scene = new Scene(root, 800, 800);

        primaryStage.setTitle("Warframe Tracker");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        EventDownloader e = new EventDownloader();
        e.printAlerts();
        e.getAlerts();
        launch(args);
    }
}
