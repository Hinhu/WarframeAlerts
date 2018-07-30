package Tracker;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class ImageDownloader {
    private static final String BASE_ADDRESS = "http://warframe.wikia.com/wiki/";

    public static Image getImage(String n) {
        Image image = new Image("Unknown.png");
        Scanner s = null;
        StringBuilder informations;
        String imageLink = null;
        try {
            s = new Scanner(new URL(getLink(n)).openStream());
        } catch (MalformedURLException e) {
            System.out.println("PROBLEM WITH CONNECTION TO THE TRACKER SITE");
        } catch (IOException e) {
            System.out.println("PROBLEM WITH READING THE INFO FROM TRACKER SITE");
        }
        if(s!=null){
            informations = new StringBuilder();
            while (s.hasNextLine()) {
                informations.append(s.nextLine().replace('"', ' ') + '\n');
            }
        }else{
            return image;
        }
        if(isComponent(n)){
            if(s!=null){
                String thumb= "<figure class= pi-item pi-image >\n" +
                              "\t\t\t\t<a href= ";
                imageLink=informations.substring(informations.indexOf(thumb) + thumb.length(), informations.indexOf(" ", informations.indexOf(thumb) + thumb.length()));
            }
        }else{
            if (s != null) {
                String thumb = "<div class= floatright ><a href= ";
                imageLink=informations.substring(informations.indexOf(thumb) + thumb.length(), informations.indexOf(" ", informations.indexOf(thumb) + thumb.length()));
            }
        }
        try {
            image = new Image(imageLink);
        } catch (IllegalArgumentException e) {
            System.out.println("COULDN'T PARSE THE IMAGE FROM WIKI PAGE");
        } catch (NullPointerException e){
            System.out.println("COULDN'T RECOGNIZE TYPE OF PRIZE");
        }
        image = SwingFXUtils.toFXImage(trimImage(SwingFXUtils.fromFXImage(image, null)), null);
        return image;
    }

    public static String getLink(String name){
        return BASE_ADDRESS+name;
    }

    private static boolean isComponent(String n){
        BufferedReader components;
        try {
            components=new BufferedReader(new FileReader(new File("Components")));
            String c;
            while ((c = components.readLine())!=null) {
                if(c.equals(n)){
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("COULDN'T FIND FILE WITH LIST OF COMPONENTS");
        } catch (IOException e) {
            System.out.println("COULDN'T READ FILE WITH LIST OF COMPONENTS");
        }

        return false;
    }

    private static BufferedImage trimImage(BufferedImage image) {
        WritableRaster raster = image.getAlphaRaster();
        int width = raster.getWidth();
        int height = raster.getHeight();
        int left = 0;
        int top = 0;
        int right = width - 1;
        int bottom = height - 1;
        int minRight = width - 1;
        int minBottom = height - 1;

        top:
        for (; top < bottom; top++) {
            for (int x = 0; x < width; x++) {
                if (raster.getSample(x, top, 0) != 0) {
                    minRight = x;
                    minBottom = top;
                    break top;
                }
            }
        }

        left:
        for (; left < minRight; left++) {
            for (int y = height - 1; y > top; y--) {
                if (raster.getSample(left, y, 0) != 0) {
                    minBottom = y;
                    break left;
                }
            }
        }

        bottom:
        for (; bottom > minBottom; bottom--) {
            for (int x = width - 1; x >= left; x--) {
                if (raster.getSample(x, bottom, 0) != 0) {
                    minRight = x;
                    break bottom;
                }
            }
        }

        right:
        for (; right > minRight; right--) {
            for (int y = bottom; y >= top; y--) {
                if (raster.getSample(right, y, 0) != 0) {
                    break right;
                }
            }
        }

        return image.getSubimage(left, top, right - left + 1, bottom - top + 1);
    }
}
