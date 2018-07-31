package Tracker;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.util.Scanner;

public class Alert {
    public final String FONT_FAMILY = "https://fonts.googleapis.com/css?family=Do+Hyeon";

    public int width = 700;
    public int height = 280;
    public int offset = 5;
    public int imageW = 200;
    public int imageH = 240;
    public int imageOff= 10;
    public long startTime;
    public long endTime;
    public String cash;
    public int prizeNumber;
    public String prizeName;
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

    public void draw(GraphicsContext g, int index) {
        g.setFill(Color.DARKGRAY);
        double x = (Main.WIDTH - width) / 2;
        double y = (offset + (height + offset) * index) + 30;
        g.fillRect(x, y, width, height);

        g.setFill(Color.WHITE);

        g.setFont(Font.font(FONT_FAMILY, FontWeight.BOLD, 20));
        g.fillText(type, x + 20, y + 40);

        g.setFont(Font.font(FONT_FAMILY, 16));
        g.fillText(localization, x + 30, y + 70);

        g.setFont(Font.font(FONT_FAMILY, 14));
        g.fillText(enemy, x + 30, y + 90);

        g.setFont(Font.font(FONT_FAMILY, FontWeight.BOLD, 25));
        g.fillText(level, x + 20, y + 130);

        g.setFont(Font.font(FONT_FAMILY, 20));
        g.fillText("CREDITS", x + 300, y + 40);

        g.setFont(Font.font(FONT_FAMILY, FontWeight.BOLD, 20));
        g.fillText(cash, x + 300, y + 70);
        if (prizeName != null) {
            Image prizeIm = ImageDownloader.getImage(prizeName);
            double h = prizeIm.getHeight();
            double w = prizeIm.getWidth();
            if (h > (imageH-imageOff)) {
                double scale = (imageH-imageOff) / h;
                h *= scale;
                w *= scale;
            }
            if (w > (imageW-imageOff)) {
                double scale = (imageW-imageOff) / w;
                h *= scale;
                w *= scale;
            }
            double strokeX = x + width - imageW - 50;
            double strokeY = y + height / 2 - imageH / 2;
            g.setFont(Font.font(FONT_FAMILY, 15));
            if (prizeNumber != 0) {
                g.fillText(prizeNumber + "x " + prizeName, strokeX, strokeY - 5);
            } else {
                g.fillText(prizeName, strokeX, strokeY - 5);
            }
            g.setLineWidth(3);
            g.strokeRoundRect(strokeX, strokeY, imageW, imageH,15,15);
            g.drawImage(prizeIm, strokeX + imageW / 2 - w / 2, strokeY + imageH / 2 - h / 2, w, h);
        }
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
        cash = cash.replace("cr", "");
    }

    private void parsePrize(Scanner s) {
        s.skip("  style= background-color:blue; >");
        StringBuilder p = new StringBuilder();
        String n;
        if (s.hasNextInt()) {
            prizeNumber = s.nextInt();
        }
        while (!(n = s.next()).contains("</span>")) {
            p.append(n + " ");
        }
        p.append(n.substring(0, n.indexOf("</span>")));
        prizeName = new String(p);
    }

    private void parseLocalization(Scanner s) {
        StringBuilder l = new StringBuilder();
        if (prizeName == null) {
            l.append(s.next().substring(1) + " ");
        } else {
            s.skip(" class= alert-node >");
            l.append(s.next());
        }
        String n;
        while (!(n = s.next()).contains("</span>")) {
            l.append(n + " ");
        }
        l.append(n.substring(0, n.indexOf("</span>")));
        if (l.charAt(l.indexOf("(") - 1) != ' ') {
            l.insert(l.indexOf("("), ' ');
        }
        localization = new String(l);
    }

    private void parseType(Scanner s) {
        s.next();
        s.skip(" <span class= alert-type >");
        StringBuilder t = new StringBuilder();
        String n;
        while (!(n = s.next()).contains("</span>")) {
            t.append(n + " ");
        }
        t.append(n.substring(0, n.indexOf("</span>")));
        type = new String(t);
    }

    private void parseEnemy(Scanner s) {
        s.next();
        s.next();
        s.next();
        String e = s.next();
        enemy = e.substring(1, e.indexOf("</span>"));
    }


    private void parseLevel(Scanner s) {
        s.next();
        level = s.nextLine().substring(1);
    }
}
