package Tracker;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class EventDownloader {
    private static final String address = "https://deathsnacks.com/wf/";
    private StringBuilder informations;
    private ArrayList<String> alertsInfo;

    public EventDownloader() {
        update();
    }

    public void update() {
        URL url = null;
        Scanner s = null;
        try {
            url = new URL(address);
            s = new Scanner(url.openStream());
        } catch (MalformedURLException e) {
            System.err.println("PROBLEM WITH CONNECTION TO THE TRACKER SITE");
        } catch (IOException e) {
            System.err.println("PROBLEM WITH READING THE INFO FROM TRACKER SITE");
        }
        readAlerts(s);
    }

    public void printInformations() {
        if (informations != null) {
            System.out.println(informations);
        }
    }

    public ArrayList<Alert> getAlerts() {
        ArrayList<Alert> alerts = new ArrayList<>();
        for (String a : alertsInfo) {
            alerts.add(new Alert(a));
        }
        return alerts;
    }

    public void printAlerts() {
        for (String alert : alertsInfo) {
            System.out.println(alert);
        }
    }

    private void readAlerts(Scanner s) {
        if (s != null) {
            informations = new StringBuilder();
            while (s.hasNextLine()) {
                informations.append(s.nextLine().replace('"', ' ') + '\n');
            }
            String ul = "<ul class= list-group alerts-container >";
            String alerts;
            int alertsBegin = informations.indexOf(ul);
            alerts = informations.substring(alertsBegin);
            int alertsEnd = alerts.indexOf("</ul>");
            alerts = alerts.substring(ul.length() + 1, alertsEnd);
            alertsInfo = new ArrayList<>();
            int index;
            while ((index = alerts.indexOf("</li>")) > 0) {
                alertsInfo.add(alerts.substring(53, index));
                alerts = alerts.substring(index + 5);
            }
        }
    }

}
