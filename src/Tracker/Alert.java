package Tracker;

import java.util.Scanner;

public class Alert {
    public long startTime;
    public long endTime;
    public String cash;
    public String prize;
    public String localization;
    public String type;
    public String enemy;
    public String level;


    public Alert(String a) {
        Scanner s = new Scanner(a.substring(a.indexOf("data-starttime=") + 16));
        parseTime(s);
        parseCash(s);
        s.next();
        String next = s.next();
        if (next.equals("badge")) {
            parsePrize(s);
        }
        parseLocalization(s);
        parseType(s);
        parseEnemy(s);
        parseLevel(s);
    }

    private void parseTime(Scanner s) {
        startTime = s.nextLong();
        s.next();
        endTime = s.nextLong();
    }

    private void parseCash(Scanner s) {
        s.skip(" ></span><span class= badge >");
        cash = s.next();
        cash = cash.substring(0, cash.indexOf('<'));
        cash = cash.replace("cr", " Credits");
    }

    private void parsePrize(Scanner s) {
        s.skip("  style= background-color:blue; >");
        StringBuilder p = new StringBuilder();
        String n;
        while (!(n = s.next()).contains("</span>")) {
            p.append(n + " ");
        }
        p.append(n.substring(0, n.indexOf("</span>")));
        prize = new String(p);
    }

    private void parseLocalization(Scanner s) {
        StringBuilder l = new StringBuilder();
        if (prize == null) {
            l.append(s.next().substring(1));
        } else {
            s.skip(" class= alert-node >");
            l.append(s.next());
        }
        String planet = s.next();
        l.append(" " + planet.substring(0, planet.indexOf("</span>")));
        localization = new String(l);
    }

    private void parseType(Scanner s){
        s.next();
        s.skip(" <span class= alert-type >");
        StringBuilder t = new StringBuilder();
        String n;
        while (!(n = s.next()).contains("</span>")) {
            t.append(n + " ");
        }
        t.append(n.substring(0, n.indexOf("</span>")));
        type=new String(t);
    }

    private void parseEnemy(Scanner s) {
        s.next();
        s.next();
        s.next();
        String e=s.next();
        enemy=e.substring(1,e.indexOf("</span>"));
    }


    private void parseLevel(Scanner s) {
        s.next();
        level=s.nextLine().substring(1);
    }
}
