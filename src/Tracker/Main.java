package Tracker;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {
    public static final int WIDTH=800;
    public static final int HEIGHT=1000;

    private static ArrayList<Alert> alerts;

    @Override
    public void start(Stage primaryStage) throws Exception {
        ScrollPane root = new ScrollPane();
        root.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        root.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        Canvas canvas = new Canvas(WIDTH, HEIGHT*1.5);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        root.setContent(canvas);
        gc.setFill(Color.GRAY);
        gc.fillRect(0,0,WIDTH-1,HEIGHT*1.5);
        for(int i=0;i<alerts.size();i++){
            alerts.get(i).draw(gc,i);
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
