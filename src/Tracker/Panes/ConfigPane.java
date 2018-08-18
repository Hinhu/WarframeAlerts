package Tracker.Panes;

import Tracker.Alert.Alerts;
import Tracker.Buttons.SwitchButton;
import Tracker.Buttons.UpdateButton;
import Tracker.Downloaders.EventDownloader;
import Tracker.Main;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class ConfigPane extends ScrollPane {

    private Pane root;

    private SwitchButton switcher;

    public ConfigPane(Stage stage){
        super();
        setStyle("-fx-background: grey");
        setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        root=new Pane();
        getRoot().setMinWidth(Main.WIDTH);
        getRoot().setStyle("-fx-background-color: grey");


        switcher=new SwitchButton(Main.WIDTH,stage);

        root.getChildren().addAll(switcher);
        setContent(root);
    }

    public void setAlertPane(AlertPane a){
        switcher.setAlert(a);
    }

    public Pane getRoot() {
        return root;
    }
}
