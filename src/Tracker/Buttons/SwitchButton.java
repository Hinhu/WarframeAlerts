package Tracker.Buttons;

import Tracker.Panes.AlertPane;
import Tracker.Panes.ConfigPane;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class SwitchButton extends Button {

    private AlertPane alert;
    private ConfigPane config;

    public SwitchButton(double x, double y, Stage stage) {
        super();

        setFont(Font.font("Arial", 15));
        setStyle("-fx-background-color: #babdc4;" +
                "-fx-background-radius: 0,0;");

        setOnMouseClicked(event -> {
            if (alert != null) {
                stage.getScene().setRoot(alert);
            } else {
                stage.getScene().setRoot(config);
            }
        });

        setOnMouseEntered(event -> {
            setFont(Font.font("Arial", FontWeight.BOLD, 15));
        });

        setOnMouseExited(event -> {
            setFont(Font.font("Arial", 15));
        });
        setMinWidth(150);
        setTranslateX(x);
        setTranslateY(y);
    }

    public void setAlert(AlertPane alert) {
        this.alert = alert;
        setText("ALERTS");
    }

    public void setConfig(ConfigPane config) {
        this.config = config;
        setText("CONFIG");
    }
}
