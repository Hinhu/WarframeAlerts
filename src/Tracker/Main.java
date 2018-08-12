package Tracker;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
    public static final int WIDTH=800;
    public static final int HEIGHT=800;

    Timeline timer;

    private static Alerts alerts;

    @Override
    public void start(Stage primaryStage) throws Exception {
        ScrollPane scroll = new ScrollPane();
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        Pane root=new Pane();
        root.setMinWidth(WIDTH);
        root.setStyle("-fx-background-color: grey");;
        scroll.setContent(root);
        Scene scene = new Scene(scroll, WIDTH, HEIGHT);
        KeyFrame frame = new KeyFrame(Duration.millis(1000), e -> {
            alerts.updateTime();
        });
        timer = new Timeline();
        timer.getKeyFrames().add(frame);
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
        primaryStage.setTitle("Warframe Tracker");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
        EventDownloader e = new EventDownloader();
        alerts= e.getAlerts();
        alerts.addToPane(root);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
