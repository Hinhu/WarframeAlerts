package Tracker.Alert;

import Tracker.Config.NodeReader;
import Tracker.Config.Planet;
import javafx.scene.layout.Pane;

import java.io.IOException;
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
        ArrayList<Planet> planets = new ArrayList<>();
        try {
            planets= NodeReader.getConfig();
        } catch (IOException e) {
            System.err.println("THERE WAS A PROBLEM IN READING CONFIG FILE");
        }
        if(!planets.isEmpty()){
            ArrayList<Alert> tmp=new ArrayList<>();
            for(Alert a:alerts){
                for(Planet p:planets){
                    if(a.getPlanet().equals(p.getName()) && p.getNodes().contains(a.getNode())){
                        tmp.add(a);
                    }
                }
            }
            alerts=tmp;
        }
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