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

    public boolean updateTime() {
        ArrayList<Integer> toRemove = new ArrayList<>();
        for (int i = 0; i < alerts.size(); i++) {
            alerts.get(i).updateTime();
            if (alerts.get(i).getTimeLeft() < -60) {
                toRemove.add(i);
            }
        }
        for (int i : toRemove) {
            alerts.remove(i);
        }
        return !toRemove.isEmpty();
    }
}