package Tracker.Alert;

import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class Alerts {
    private static ArrayList<Alert> alerts;

    public Alerts(ArrayList<Alert> a) {
        setAlerts(a);
    }

    public static ArrayList<Alert> getAlerts() {
        return alerts;
    }

    public static void setAlerts(ArrayList<Alert> alerts) {
        Alerts.alerts = alerts;
    }

    public void addToPane(Pane root) {
        for (int i = 0; i < getAlerts().size(); i++) {
            getAlerts().get(i).addToRoot(root, i);
        }
    }

    public boolean updateTime() {
        ArrayList<Integer> toRemove = new ArrayList<>();
        for (int i = 0; i < getAlerts().size(); i++) {
            getAlerts().get(i).updateTime();
            if (getAlerts().get(i).getTimeLeft() < -60) {
                toRemove.add(i);
            }
        }
        for (int i : toRemove) {
            getAlerts().remove(i);
        }
        return !toRemove.isEmpty();
    }


}