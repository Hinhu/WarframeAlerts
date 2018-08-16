package Tracker;

import Tracker.Alert.Alerts;
import Tracker.Buttons.Switcher;
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

    Scene scene;

    ScrollPane alertScroll;
    ScrollPane configScroll;

    Pane alertPane=new Pane();

    Pane configPane;

    Timeline timer;

    EventDownloader eventDownloader;
    private UpdateButton upadater;
    private Switcher switcher;

    private static Alerts alerts;

    @Override
    public void start(Stage primaryStage) {
        eventDownloader = new EventDownloader();
        alerts= eventDownloader.getAlerts();
        alerts.addToPane(alertPane);
        upadater=new UpdateButton(WIDTH);
        switcher=new Switcher(WIDTH,primaryStage);
        createAlertScreen();
        createConfigScreen();
        createTimer();
        initStage(primaryStage);
        //timer.play();
        switcher.setPanes(alertScroll,configScroll);
        upadater.update(alertPane,eventDownloader,alerts);
        alertPane.getChildren().addAll(upadater,switcher);
    }

    private void createConfigScreen() {
        configScroll=createScroller();
        configPane=new Pane();
        configPane.setMinWidth(WIDTH);
        configPane.setStyle("-fx-background-color: grey");
        configPane.getChildren().add(switcher);
        configScroll.setContent(configPane);
    }

    private ScrollPane createScroller() {
        ScrollPane scroll = new ScrollPane();
        scroll.setStyle("-fx-background: black");
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        return scroll;
    }


    private void initStage(Stage primaryStage) {
        scene=new Scene(alertScroll,WIDTH,HEIGHT);
        primaryStage.setTitle("Warframe Tracker");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void createTimer() {
        KeyFrame frame = new KeyFrame(Duration.millis(1000), e -> {
            if(alerts.updateTime()){
                alertPane.getChildren().clear();
                alertPane.getChildren().addAll(upadater,switcher);
                alerts.addToPane(alertPane);
            }
        });
        timer = new Timeline();
        timer.getKeyFrames().add(frame);
        timer.setCycleCount(Timeline.INDEFINITE);
    }

    private void createAlertScreen() {
        alertScroll=createScroller();
        alertPane.setMinWidth(WIDTH);
        alertPane.setStyle("-fx-background-color: grey");
        upadater.setUpdatesToAlerts(alertPane,eventDownloader,alerts);
        alertPane.getChildren().addAll(upadater,switcher);
        alertScroll.setContent(alertPane);
    }




    public static void main(String[] args) {
        launch(args);
    }
}
