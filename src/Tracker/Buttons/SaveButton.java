package Tracker.Buttons;

import Tracker.Config.ConfigSaver;
import Tracker.Config.Planet;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.ArrayList;

public class SaveButton extends Button {


    public SaveButton(double x, double y, ArrayList<Planet>planets) {
        super("SAVE");

        setFont(Font.font("Arial", 15));
        setStyle("-fx-background-color: #babdc4;" +
                "-fx-background-radius: 0,0;");

        setOnMouseClicked(event -> {
            ConfigSaver.saveConfig(planets);
        });

        setOnMouseEntered(event -> {
            setFont(Font.font("Arial", FontWeight.BOLD, 15));
        });

        setOnMouseExited(event -> {
            setFont(Font.font("Arial", 15));
        });
        setWidth(100);
        setTranslateX(x);
        setTranslateY(y);
    }

}
