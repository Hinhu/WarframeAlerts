package Tracker;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.Date;
import java.util.Scanner;

public class Alert {
    public final String FONT_FAMILY = "https://fonts.googleapis.com/css?family=Do+Hyeon";

    public int width = 700;
    public static int height = 280;
    public int offset = 5;
    public int imageW = 200;
    public int imageH = 230;
    public int imageOff = 10;
    public int timeLeft;
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

    private void parseTime(Scanner s) {
        s.nextLong();
        s.next();
        long endTime = s.nextLong();
        long currentTime = System.currentTimeMillis() / 1000;
        timeLeft = (int) (endTime - currentTime);
        System.out.println(currentTime + "\t" + endTime + "\t" + (endTime - currentTime));
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

    public void addToRoot(Pane root, int index) {
        double x = (Main.WIDTH - width) / 2;
        double y = (offset + (height + offset) * index) + 100;

        Rectangle background = new Rectangle(x, y, width, height);
        background.setFill(Color.DARKGRAY);

        Text missionType = new Text(x + 0.15 * height, y + 0.15 * height, type.toUpperCase());
        missionType.setFont(Font.font(FONT_FAMILY, FontWeight.BOLD, 20));
        missionType.setFill(Color.WHITE);

        Text missionLoc = new Text(missionType.getX(), missionType.getY() + 0.15 * height, localization);
        missionLoc.setFont(Font.font(FONT_FAMILY, 16));
        missionLoc.setFill(Color.WHITE);

        Text missionEnemy = new Text(missionLoc.getX(), missionLoc.getY() + 0.1 * height, enemy);
        missionEnemy.setFont(Font.font(FONT_FAMILY, 16));
        missionEnemy.setFill(Color.WHITE);

        Text missionLevel = new Text(missionEnemy.getX(), missionEnemy.getY() + 0.15 * height, level);
        missionLevel.setFont(Font.font(FONT_FAMILY, FontWeight.BOLD, 18));
        missionLevel.setFill(Color.WHITE);

        Text missionTime = new Text(missionLevel.getX(), missionLevel.getY() + 0.2 * height, getFormattedTime());
        missionTime.setFont(Font.font(FONT_FAMILY, FontWeight.BOLD, 20));
        missionTime.setFill(Color.WHITE);

        Text missionCash = new Text(x + width / 2 - 50, missionType.getY(), "CREDITS:\n" + cash);
        missionCash.setFont(Font.font(FONT_FAMILY, FontWeight.BOLD, 20));
        missionCash.setFill(Color.WHITE);

        root.getChildren().addAll(background, missionType, missionLoc, missionEnemy, missionLevel, missionTime, missionCash);

        if (prizeName != null) {
            Rectangle frame = new Rectangle(x + 0.9 * width - imageW, y + height * 0.5 - imageH * 0.5, imageW, imageH);
            frame.setFill(Color.TRANSPARENT);
            frame.setArcHeight(15);
            frame.setArcWidth(15);
            frame.setStroke(Color.BLACK);
            frame.setStrokeWidth(3);

            ImageView prizeImage = ImageDownloader.getImage(prizeName, imageW - imageOff, imageH - imageOff).getAlertImage();

            prizeImage.setTranslateX(frame.getX() + frame.getWidth() / 2 - prizeImage.getImage().getWidth() / 2);
            prizeImage.setTranslateY(frame.getY() + frame.getHeight() / 2 - prizeImage.getImage().getHeight() / 2);

            Text prizeTitle = new Text(frame.getX(), frame.getY() - frame.getStrokeWidth() - 3, prizeName);
            prizeTitle.setFont(Font.font(FONT_FAMILY, 14));
            prizeTitle.setFill(Color.WHITE);

            root.getChildren().addAll(frame, prizeImage, prizeTitle);

            if (prizeNumber != 0) {
                Text prizeQuantity = new Text(prizeNumber + "x");
                prizeQuantity.setFont(Font.font(FONT_FAMILY, FontWeight.BOLD, 18));
                prizeQuantity.setFill(Color.WHITE);
                prizeQuantity.setX(frame.getX() - prizeQuantity.getLayoutBounds().getWidth() - 5);
                prizeQuantity.setY(frame.getY() + frame.getHeight() / 2 - 9);

                root.getChildren().add(prizeQuantity);
            }
        }
    }

    private String getFormattedTime() {
        int hours = timeLeft / 3600;
        int minutes = (timeLeft % 3600) / 60;
        int seconds = (timeLeft % 3600) % 60;

        return hours + "h " + minutes + "m " + seconds + "s";
    }
}
