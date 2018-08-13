package Tracker;

import Tracker.Alert.Alerts;
import Tracker.Buttons.UpdateButton;
import Tracker.Downloaders.EventDownloader;
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

    Scene alertScreen;
    Pane alertPane=new Pane();

    Scene configScreen;
    Pane configPane;

    Timeline timer;

    EventDownloader eventDownloader;
    private UpdateButton upadater;

    private static Alerts alerts;

    @Override
    public void start(Stage primaryStage) {
        eventDownloader = new EventDownloader();
        alerts= eventDownloader.getAlerts();
        alerts.addToPane(alertPane);
        upadater=new UpdateButton(WIDTH);
        createAlertScreen();
        createConfigScreen();
        createTimer();
        initStage(primaryStage);
        timer.play();
        upadater.update(alertPane,eventDownloader,alerts);
    }

    private void createConfigScreen() {
        ScrollPane scroll = createScroller();
        configPane=new Pane();
        configPane.setMinWidth(WIDTH);
        configPane.setStyle("-fx-background-color: grey");
        scroll.setContent(configPane);
        configScreen = new Scene(scroll, WIDTH, HEIGHT);
    }

    private ScrollPane createScroller() {
        ScrollPane scroll = new ScrollPane();
        scroll.setStyle("-fx-background: grey");
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        return scroll;
    }


    private void initStage(Stage primaryStage) {
        primaryStage.setTitle("Warframe Tracker");
        primaryStage.setResizable(false);
        primaryStage.setScene(alertScreen);
        primaryStage.show();
    }

    private void createTimer() {
        KeyFrame frame = new KeyFrame(Duration.millis(1000), e -> {
            if(alerts.updateTime()){
                alertPane.getChildren().clear();
                alertPane.getChildren().add(upadater);
                alerts.addToPane(alertPane);
            }
        });
        timer = new Timeline();
        timer.getKeyFrames().add(frame);
        timer.setCycleCount(Timeline.INDEFINITE);
    }

    private void createAlertScreen() {
        ScrollPane scroll = createScroller();
        alertPane=new Pane();
        alertPane.setMinWidth(WIDTH);
        alertPane.setStyle("-fx-background-color: grey");
        upadater.setUpdatesToAlerts(alertPane,eventDownloader,alerts);
        alertPane.getChildren().add(upadater);
        scroll.setContent(alertPane);
        alertScreen = new Scene(scroll, WIDTH, HEIGHT);
    }




    public static void main(String[] args) {
        launch(args);
    }
}
