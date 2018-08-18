package Tracker.Panes;

import Tracker.Alert.Alerts;
import Tracker.Buttons.SwitchButton;
import Tracker.Buttons.UpdateButton;
import Tracker.Config.NodeReader;
import Tracker.Config.Planet;
import Tracker.Downloaders.EventDownloader;
import Tracker.Main;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;


public class ConfigPane extends ScrollPane {

    private Pane root;

    private SwitchButton switcher;

    public ConfigPane(Stage stage) {
        super();
        setStyle("-fx-background: grey");
        setVbarPolicy(ScrollBarPolicy.NEVER);
        setHbarPolicy(ScrollBarPolicy.ALWAYS);

        root = new Pane();
        root.setStyle("-fx-background-color: grey");


        switcher = new SwitchButton(Main.WIDTH, stage);

        root.getChildren().addAll(switcher);


        ArrayList<Planet> planets = null;
        try {
            planets = NodeReader.getPlanets();
        } catch (IOException e) {
            System.err.println("THERE WAS AN ERROR WHILE READING " + NodeReader.NODE_FILE + " FILE");
        }
        if (planets != null) {
            int startX = 20;
            int startY = 120;
            double width = 0;
            for (Planet p:planets){
                p.addToPane(startX+width,startY,root);
                width+=p.getWidth();
            }
            root.setMinWidth(width+20);
        }
        setContent(root);
    }

    public void setAlertPane(AlertPane a) {
        switcher.setAlert(a);
    }

    public Pane getRoot() {
        return root;
    }
}
