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

    /**
     * Tries to get image of given object.
     * In case of failure, returns image of question mark.
     *
     * @param n name of the object
     * @return if image was found, then image of the object is returned; if it wasn't found, image of question mark is returned
     */

    public static Image getImage(String n) {
        Image image = new Image("Unknown.png");
        Scanner s = null;
        StringBuilder informations;
        String imageLink = null;
        if (!n.contains("Blueprint")) {
            try {
                s = new Scanner(new URL(getLink(n)).openStream());
            } catch (MalformedURLException e) {
                System.out.println("PROBLEM WITH CONNECTION TO THE WIKI SITE");
                System.out.println(getLink(n));
            } catch (IOException e) {
                System.out.println("PROBLEM WITH READING THE INFO FROM WIKI SITE");
                System.out.println(getLink(n));
            }
            if(s!=null) {
                informations = new StringBuilder();
                while (s.hasNextLine()) {
                    informations.append(s.nextLine().replace('"', ' ') + '\n');
                }
                informations = new StringBuilder(informations.toString().replace("\t", ""));
                String thumb;
                if (n.equals("Endo")) {
                    thumb = "<div class= floatright ><a href= ";
                } else {
                    thumb = "<figure class= pi-item pi-image >\n" +
                            "<a href= ";
                }
                imageLink = informations.substring(informations.indexOf(thumb) + thumb.length(), informations.indexOf(" ", informations.indexOf(thumb) + thumb.length()));
                try {
                    image = new Image(imageLink);
                } catch (IllegalArgumentException e) {
                    System.out.println("COULDN'T PARSE THE IMAGE FROM WIKI PAGE");
                    System.out.println(imageLink);
                } catch (NullPointerException e) {
                    System.out.println("COULDN'T RECOGNIZE TYPE OF PRIZE");
                }
                image = SwingFXUtils.toFXImage(trimImage(SwingFXUtils.fromFXImage(image, null)), null);
            }
        }else{
            try {
                s = new Scanner(new URL(getSearchLink(n)).openStream());
            } catch (MalformedURLException e) {
                System.out.println("PROBLEM WITH CONNECTION TO ECOSIA");
                System.out.println(getSearchLink(n));
            } catch (IOException e) {
                System.out.println("PROBLEM WITH READING THE INFO FROM ECOSIA");
                System.out.println(getSearchLink(n));
            }
            if(s!=null){
                StringBuilder searchHTML = new StringBuilder();
                while (s.hasNextLine()) {
                    searchHTML.append(s.nextLine().replace('"', ' ') + '\n');
                }
                searchHTML = new StringBuilder(searchHTML.toString().replace("\t", ""));
                String thumb="<a class= result-title js-result-title  href= ";
                String wikiLink=searchHTML.substring(searchHTML.indexOf(thumb) + thumb.length(), searchHTML.indexOf(" ", searchHTML.indexOf(thumb) + thumb.length()));
                try {
                    s = new Scanner(new URL(wikiLink).openStream());
                } catch (MalformedURLException e) {
                    System.out.println("PROBLEM WITH CONNECTION TO ECOSIA");
                    System.out.println(getLink(n));
                } catch (IOException e) {
                    System.out.println("PROBLEM WITH READING THE INFO FROM ECOSIA");
                    System.out.println(getLink(n));
                }
                informations = new StringBuilder();
                while (s.hasNextLine()) {
                    informations.append(s.nextLine().replace('"', ' ') + '\n');
                }
                informations = new StringBuilder(informations.toString().replace("\t", ""));
                thumb = "<figure class= pi-item pi-image >\n" +
                        "<a href= ";
                imageLink = informations.substring(informations.indexOf(thumb) + thumb.length(), informations.indexOf(" ", informations.indexOf(thumb) + thumb.length()));
                try {
                    image = new Image(imageLink);
                } catch (IllegalArgumentException e) {
                    System.out.println("COULDN'T PARSE THE IMAGE FROM WIKI PAGE");
                    System.out.println(imageLink);
                } catch (NullPointerException e) {
                    System.out.println("COULDN'T RECOGNIZE TYPE OF PRIZE");
                }
                image = SwingFXUtils.toFXImage(trimImage(SwingFXUtils.fromFXImage(image, null)), null);
            }
        }
        return image;
    }

    private static String getSearchLink(String name) {
        return "https://www.ecosia.org/search?q=Warframe+"+new Scanner(name).nextLine().replace(' ', '+');
    }

    /**
     * Generates link to wiki article about given object.
     *
     * @param name the name of object
     * @return link to the wiki article as a String
     */

    public static String getLink(String name) {
        return BASE_ADDRESS + new Scanner(name).nextLine().replace(' ', '_');
    }

    private static boolean isElement(String n, String elementType) {
        BufferedReader elements;
        try {
            elements = new BufferedReader(new FileReader(new File(elementType + "s")));
            String c;
            while ((c = elements.readLine()) != null) {
                if (c.equals(n)) {
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("COULDN'T FIND FILE WITH LIST OF " + elementType.toUpperCase() + "S");
        } catch (IOException e) {
            System.out.println("COULDN'T READ FILE WITH LIST OF " + elementType.toUpperCase() + "S");
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
