package Tracker;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
    public static final int WIDTH=800;
    public static final int HEIGHT=800;

    Timeline timer;

    EventDownloader eventDownloader;
    private Button upadater;

    private static Alerts alerts;

    @Override
    public void start(Stage primaryStage) throws Exception {
        ScrollPane scroll = new ScrollPane();
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        Pane root=new Pane();
        root.setMinWidth(WIDTH);
        root.setStyle("-fx-background-color: grey");
        createUpadater(root);
        root.getChildren().add(upadater);
        scroll.setContent(root);
        Scene scene = new Scene(scroll, WIDTH, HEIGHT);
        KeyFrame frame = new KeyFrame(Duration.millis(1000), e -> {
            if(alerts.updateTime()){
                root.getChildren().clear();
                root.getChildren().add(upadater);
                alerts.addToPane(root);
            }
        });
        primaryStage.setTitle("Warframe Tracker");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
        eventDownloader = new EventDownloader();
        alerts= eventDownloader.getAlerts();
        alerts.addToPane(root);
        timer = new Timeline();
        timer.getKeyFrames().add(frame);
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }

    private void createUpadater(Pane root) {
        upadater=new Button("REFRESH");
        upadater.setFont(Font.font("Arial",15));
        upadater.setTranslateX(WIDTH/2-upadater.getWidth()/2);
        upadater.setTranslateY(50-upadater.getHeight()/2);
        upadater.setStyle("-fx-background-color: #babdc4;" +
                            "-fx-background-radius: 0,0;");
        upadater.setOnMouseClicked(event -> {
            root.getChildren().clear();
            root.getChildren().addAll(upadater);
            eventDownloader.update();
            alerts=eventDownloader.getAlerts();
            alerts.addToPane(root);
        });


        upadater.setOnMouseEntered(event -> {
            upadater.setFont(Font.font("Arial", FontWeight.BOLD,15));
        });

        upadater.setOnMouseExited(event -> {
            upadater.setFont(Font.font("Arial",15));
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}
