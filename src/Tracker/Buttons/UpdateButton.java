package Tracker.Buttons;

import Tracker.Alert.Alerts;
import Tracker.Downloaders.EventDownloader;
import Tracker.Panes.AlertPane;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class UpdateButton extends Button {


    public UpdateButton(double x,double y){
        super("REFRESH");
        setFont(Font.font("Arial",15));
        setStyle("-fx-background-color: #babdc4;" +
                "-fx-background-radius: 0,0;");

        setOnMouseEntered(event -> {
            setFont(Font.font("Arial", FontWeight.BOLD,15));
        });

        setOnMouseExited(event -> {
            setFont(Font.font("Arial",15));
        });
        setMinWidth(150);
        setTranslateX(x);
        setTranslateY(y);
    }


    public void setUpdatesToAlerts(AlertPane alertPane, EventDownloader e, Alerts alerts){
        setOnMouseClicked(event -> {
            update(alertPane,e,alerts);
        });
    }

    public void update(AlertPane alertPane, EventDownloader e, Alerts alerts){
        e.update();
        alerts.setAlerts(e.getAlerts().getAlerts());
        alertPane.refresh(alerts);
    }
}
