package Tracker.Panes;

import Tracker.Alert.Alerts;
import Tracker.Buttons.SwitchButton;
import Tracker.Buttons.UpdateButton;
import Tracker.Downloaders.EventDownloader;
import Tracker.Main;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class AlertPane extends ScrollPane {

    private Pane root;

    private UpdateButton updater;
    private SwitchButton switcher;

    public AlertPane(Alerts alerts, EventDownloader eventDownloader, Stage stage){
        super();
        setStyle("-fx-background: grey");
        setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        root=new Pane();
        getRoot().setMinWidth(Main.WIDTH);
        getRoot().setStyle("-fx-background-color: grey");
        alerts.addToPane(root);

        updater=new UpdateButton(Main.WIDTH);
        updater.setUpdatesToAlerts(this,eventDownloader,alerts);
        switcher=new SwitchButton(Main.WIDTH,stage);

        root.getChildren().addAll(updater, switcher);
        setContent(getRoot());
    }

    public void refresh(Alerts alerts){
        root.getChildren().clear();
        root.getChildren().addAll(updater, switcher);
        alerts.addToPane(root);
    }

    public void setConfigPane(ConfigPane c){
        switcher.setConfig(c);
    }

    public Pane getRoot() {
        return root;
    }
}
