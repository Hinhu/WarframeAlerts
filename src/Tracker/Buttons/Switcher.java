package Tracker.Buttons;

import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Switcher extends Button {

    private boolean isInAlerts;

    ScrollPane alert;
    ScrollPane config;

    public Switcher(double screenW, Stage stage){
        super("CONFIG");
        isInAlerts=true;
        setFont(Font.font("Arial",15));
        setStyle("-fx-background-color: #babdc4;" +
                "-fx-background-radius: 0,0;");

        setOnMouseClicked(event -> {
            isInAlerts=!isInAlerts;
            if(isInAlerts){
                setText("CONFIG");
                stage.getScene().setRoot(alert);
            }else{
                setText("ALERTS");
                stage.getScene().setRoot(config);
            }
        });

        setOnMouseEntered(event -> {
            setFont(Font.font("Arial", FontWeight.BOLD,15));
        });

        setOnMouseExited(event -> {
            setFont(Font.font("Arial",15));
        });
        setWidth(100);
        setTranslateX(screenW/2+getWidth()+5);
        setTranslateY(50-getHeight()/2);
    }

    public void setPanes(ScrollPane alertPane, ScrollPane configPane) {
        alert=alertPane;
        config=configPane;
    }
}
