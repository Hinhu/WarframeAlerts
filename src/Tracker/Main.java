package Tracker;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {
    public static final int WIDTH=800;
    public static final int HEIGHT=800;

    private static ArrayList<Alert> alerts;

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
        for(int i=0;i<alerts.size();i++){
            alerts.get(i).addToRoot(root,i);
        }
        primaryStage.setTitle("Warframe Tracker");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        EventDownloader e = new EventDownloader();
        alerts= e.getAlerts();
        launch(args);
    }
}
