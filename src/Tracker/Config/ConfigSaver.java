package Tracker.Config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class ConfigSaver {
    public static void saveConfig(ArrayList<Planet> planets) {
        PrintWriter out = null;

        try {
            out=new PrintWriter("Config.txt");
        } catch (FileNotFoundException e) {
            System.err.println("COULDN'T FIND CONFIG FILE");
        }
        if(out!=null){
           for(Planet p:planets) {
               out.print(p);
           }
            out.close();
        }

    }
}
