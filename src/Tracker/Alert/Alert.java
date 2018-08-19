package Tracker.Alert;

import Tracker.Downloaders.ImageDownloader;
import Tracker.Main;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.Scanner;

public class Alert {
    private final String FONT_FAMILY = "https://fonts.googleapis.com/css?family=Do+Hyeon";

    private int width = 700;
    public static int height = 280;
    private int offset = 5;
    private int imageW = 200;
    private int imageH = 230;
    private int imageOff = 10;
    private long startTime;
    private long endTime;
    private int timeLeft;
    private int timeToStart;
    private Text missionTime;
    private String cash;
    private int prizeNumber;
    private String prizeName;
    private String localization;
    private String type;
    private String enemy;
    private String level;



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

    public void updateTime() {
        long currentTime = System.currentTimeMillis() / 1000;
        timeLeft = (int) (endTime - currentTime);
        timeToStart = (int) (startTime - currentTime);
        missionTime.setText(getFormattedTime());
        if (timeLeft < 300) {
            missionTime.setFill(Color.RED);
        }else if(timeToStart>0){
            missionTime.setFill(Color.ORANGE);
        }
    }

    private void parseTime(Scanner s) {
        startTime=s.nextLong();
        s.next();
        endTime = s.nextLong();
        long currentTime = System.currentTimeMillis() / 1000;
        timeLeft = (int) (endTime - currentTime);
        timeToStart = (int) (startTime - currentTime);
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

        Rectangle background = createBackground(x, y);

        Text missionType = createMissionTypeText(x, y);

        Text missionLoc = createMissionLocationText(missionType.getX(), missionType.getY() + 0.15 * height);

        Text missionEnemy = createMissionEnemyText(missionLoc.getX(), missionLoc.getY() + 0.1 * height);

        Text missionLevel = createMissionLevelText(missionEnemy.getX(), missionEnemy.getY() + 0.15 * height);

        createMissionTimeText(missionLevel.getX(), missionLevel.getY() + 0.2 * height);

        Text missionCash = createMissionCashText(x + width / 2 - 50, missionType.getY()+missionType.getLayoutBounds().getHeight()+5);

        root.getChildren().addAll(background, missionType, missionLoc, missionEnemy, missionLevel, missionTime, missionCash);

        if (prizeName != null) {
            createPrizeImageFrame(x + 0.95 * width - imageW, y + height * 0.5 - imageH * 0.5, root);
        }
    }

    private void createPrizeImageFrame(double x, double y, Pane root) {
        Rectangle frame = new Rectangle(x, y, imageW, imageH);
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
            int fontSize=22;
            prizeQuantity.setFont(Font.font(FONT_FAMILY, FontWeight.BOLD, fontSize));
            prizeQuantity.setFill(Color.WHITE);
            prizeQuantity.setX(frame.getX() - prizeQuantity.getLayoutBounds().getWidth() - 5);
            prizeQuantity.setY(frame.getY() + frame.getHeight() / 2 - fontSize/2);

            root.getChildren().add(prizeQuantity);
        }
    }


    private Text createMissionCashText(double x, double y) {
        Text missionCash = new Text(x, y, "CREDITS:\n" + cash);
        missionCash.setFont(Font.font(FONT_FAMILY, FontWeight.BOLD, 20));
        missionCash.setFill(Color.WHITE);
        return missionCash;
    }

    private void createMissionTimeText(double x, double y) {
        missionTime = new Text(x, y, getFormattedTime());
        missionTime.setFont(Font.font(FONT_FAMILY, FontWeight.BOLD, 20));
        missionTime.setFill(Color.WHITE);
    }

    private Text createMissionLevelText(double x, double y) {
        Text missionLevel = new Text(x, y, level);
        missionLevel.setFont(Font.font(FONT_FAMILY, FontWeight.BOLD, 18));
        missionLevel.setFill(Color.WHITE);
        return missionLevel;
    }

    private Text createMissionEnemyText(double x, double y) {
        Text missionEnemy = new Text(x, y, enemy);
        missionEnemy.setFont(Font.font(FONT_FAMILY, 16));
        missionEnemy.setFill(Color.WHITE);
        return missionEnemy;
    }

    private Text createMissionLocationText(double x, double y) {
        Text missionLoc = new Text(x, y, localization);
        missionLoc.setFont(Font.font(FONT_FAMILY, 16));
        missionLoc.setFill(Color.WHITE);
        return missionLoc;
    }

    private Text createMissionTypeText(double x, double y) {
        Text missionType = new Text(x + 0.15 * height, y + 0.15 * height, type.toUpperCase());
        missionType.setFont(Font.font(FONT_FAMILY, FontWeight.BOLD, 20));
        missionType.setFill(Color.WHITE);
        return missionType;
    }

    private Rectangle createBackground(double x, double y) {
        Rectangle background = new Rectangle(x, y, width, height);
        background.setFill(Color.DARKGRAY);
        return background;
    }

    private String getFormattedTime() {
        if(timeToStart>0){
            int minutes = (timeToStart % 3600) / 60;
            int seconds = (timeToStart % 3600) % 60;

            return "Will start in: "+ minutes + "m " + seconds + "s";
        }
        if (timeLeft < 0) {
            return "Expired";
        }
        int hours = timeLeft / 3600;
        int minutes = (timeLeft % 3600) / 60;
        int seconds = (timeLeft % 3600) % 60;

        return hours + "h " + minutes + "m " + seconds + "s";
    }

    public int getTimeLeft() {
        return timeLeft;
    }

    public String getPlanet(){
        return localization.substring(localization.indexOf('(')+1,localization.indexOf(')'));
    }
    public String getNode(){
        return localization.substring(0,localization.indexOf('(')-1);
    }
}
