package Tracker;

import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class Alerts {
    private static ArrayList<Alert> alerts;

    public Alerts(ArrayList<Alert> a) {
        alerts = a;
    }

    public void addToPane(Pane root) {
        for (int i = 0; i < alerts.size(); i++) {
            alerts.get(i).addToRoot(root, i);
        }
    }

    public void updateTime() {
        for (Alert a : alerts) {
            a.updateTime();
        }
    }
}
