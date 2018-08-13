package Tracker.Downloaders;

import Tracker.Alert.AlertImage;

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
     * @param maxW maximal width of image
     * @param maxH maximal height of image
     * @return if image was found, then image of the object is returned; if it wasn't found, image of question mark is returned
     */

    public static AlertImage getImage(String n, double maxW, double maxH) {
        AlertImage image = new AlertImage("Unknown.png",BASE_ADDRESS,maxW,maxH);
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
                    image = new AlertImage(imageLink,getLink(n),maxW,maxH);
                } catch (IllegalArgumentException e) {
                    System.out.println("COULDN'T PARSE THE IMAGE FROM WIKI PAGE");
                    System.out.println(imageLink);
                } catch (NullPointerException e) {
                    System.out.println("COULDN'T RECOGNIZE TYPE OF PRIZE");
                }
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
                    image = new AlertImage(imageLink,wikiLink,maxW,maxH);
                } catch (IllegalArgumentException e) {
                    System.out.println("COULDN'T PARSE THE IMAGE FROM WIKI PAGE");
                } catch (NullPointerException e) {
                    System.out.println("COULDN'T RECOGNIZE TYPE OF PRIZE");
                }
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


}
