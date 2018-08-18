package Tracker;

import Tracker.Alert.Alerts;
import Tracker.Buttons.SwitchButton;
import Tracker.Buttons.UpdateButton;
import Tracker.Downloaders.EventDownloader;
import Tracker.Panes.AlertPane;
import Tracker.Panes.ConfigPane;
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

    Scene scene;

    AlertPane alertPane;
    ConfigPane configPane;

    Timeline timer;

    EventDownloader eventDownloader;

    private static Alerts alerts;

    @Override
    public void start(Stage primaryStage) {
        eventDownloader = new EventDownloader();
        alerts= eventDownloader.getAlerts();
        alertPane =new AlertPane(alerts,eventDownloader,primaryStage);
        configPane=new ConfigPane(primaryStage);
        alertPane.setConfigPane(configPane);
        configPane.setAlertPane(alertPane);
        createTimer();
        initStage(primaryStage);
        timer.play();
    }






    private void initStage(Stage primaryStage) {
        scene=new Scene(alertPane,WIDTH,HEIGHT);
        primaryStage.setTitle("Warframe Tracker");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void createTimer() {
        KeyFrame frame = new KeyFrame(Duration.millis(1000), e -> {
            if(alerts.updateTime()){
                alertPane.refresh(alerts);
            }
        });
        timer = new Timeline();
        timer.getKeyFrames().add(frame);
        timer.setCycleCount(Timeline.INDEFINITE);
    }





    public static void main(String[] args) {
        launch(args);
    }
}
