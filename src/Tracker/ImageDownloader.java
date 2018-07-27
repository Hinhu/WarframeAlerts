package Tracker;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class ImageDownloader {
    private static final String BASE_ADDRESS = "http://warframe.wikia.com/wiki/";

    public static Image getImage(String n){
        Image image=new Image("Unknown.png");
        StringBuilder name=new StringBuilder(n);
        if(!n.matches(".*\\d+.*")){
            int blue = name.indexOf("Blueprint");
            if(blue>0){
                name.delete(blue-1,name.length()-1);
            }
            name=new StringBuilder(name.toString().replace(' ','_'));
            System.out.println(BASE_ADDRESS+name);
            URL url = null;
            Scanner s = null;
            try {
                url = new URL(BASE_ADDRESS+name);
                s = new Scanner(url.openStream());
            } catch (MalformedURLException e) {
                System.err.println("PROBLEM WITH CONNECTION TO THE TRACKER SITE");
            } catch (IOException e) {
                System.err.println("PROBLEM WITH READING THE INFO FROM TRACKER SITE");
            }
            if (s != null) {
                StringBuilder informations = new StringBuilder();
                while (s.hasNextLine()) {
                    informations.append(s.nextLine().replace('"', ' ') + '\n');
                }
                System.out.println(informations);
            }
        }else{
            Scanner currency=new Scanner(n);
            currency.nextInt();
            URL url = null;
            Scanner s = null;
            try {
                url = new URL(BASE_ADDRESS+currency.next());
                s = new Scanner(url.openStream());
            } catch (MalformedURLException e) {
                System.err.println("PROBLEM WITH CONNECTION TO THE TRACKER SITE");
            } catch (IOException e) {
                System.err.println("PROBLEM WITH READING THE INFO FROM TRACKER SITE");
            }
            if (s != null) {
                StringBuilder informations = new StringBuilder();
                while (s.hasNextLine()) {
                    informations.append(s.nextLine().replace('"', ' ') + '\n');
                }

                String thumb="<div class= floatright ><a href= ";
                image=new Image(informations.substring(informations.indexOf(thumb)+thumb.length(),informations.indexOf(" ",informations.indexOf(thumb)+thumb.length())));
            }


        }

        image=SwingFXUtils.toFXImage(trimImage(SwingFXUtils.fromFXImage(image, null)), null);
        return image;
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
        for (;top < bottom; top++){
            for (int x = 0; x < width; x++){
                if (raster.getSample(x, top, 0) != 0){
                    minRight = x;
                    minBottom = top;
                    break top;
                }
            }
        }

        left:
        for (;left < minRight; left++){
            for (int y = height - 1; y > top; y--){
                if (raster.getSample(left, y, 0) != 0){
                    minBottom = y;
                    break left;
                }
            }
        }

        bottom:
        for (;bottom > minBottom; bottom--){
            for (int x = width - 1; x >= left; x--){
                if (raster.getSample(x, bottom, 0) != 0){
                    minRight = x;
                    break bottom;
                }
            }
        }

        right:
        for (;right > minRight; right--){
            for (int y = bottom; y >= top; y--){
                if (raster.getSample(right, y, 0) != 0){
                    break right;
                }
            }
        }

        return image.getSubimage(left, top, right - left + 1, bottom - top + 1);
    }
}
