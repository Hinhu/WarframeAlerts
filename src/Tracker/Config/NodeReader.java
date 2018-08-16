package Tracker.Config;

import java.io.*;
import java.util.ArrayList;

public class NodeReader {
    private static final String NODE_FILE = "Nodes.txt";

    public static ArrayList<Planet> getPlanets() throws IOException {
        ArrayList<Planet> planets = new ArrayList<Planet>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(new File(NODE_FILE)));
        } catch (FileNotFoundException e) {
            System.err.println("COULDN'T FIND " + NODE_FILE + " FILE");
        }
        if (br != null) {
            String planetName;
            ArrayList<String> nodes = null;
            while ((planetName = br.readLine()) != null) {
                nodes = new ArrayList<>();
                String nodeName;
                while ((nodeName = br.readLine()) != null) {
                    if (nodeName.equals("END")) {
                        break;
                    }
                    nodes.add(nodeName.substring(1));
                }
                planets.add(new Planet(planetName, nodes));
            }
        }
        System.out.println(planets.size());
        return planets;
    }

}
