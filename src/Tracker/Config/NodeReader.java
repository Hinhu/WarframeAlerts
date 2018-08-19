package Tracker.Config;

import java.io.*;
import java.util.ArrayList;

public class NodeReader {
    public static final String NODE_FILE = "Nodes.txt";
    public static final String CONFIG_FILE = "Config.txt";

    public static ArrayList<Planet> getPlanets() throws IOException {
        return getPlanetsFromFile(NodeReader.class.getResourceAsStream(NODE_FILE));
    }

    public static ArrayList<Planet> getConfig() throws IOException {
        return getPlanetsFromFile(new FileInputStream(CONFIG_FILE));
    }

    private static ArrayList<Planet> getPlanetsFromFile(InputStream fileName) throws IOException {
        ArrayList<Planet> planets = new ArrayList<Planet>();
        BufferedReader br = new BufferedReader(new InputStreamReader(fileName));

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
        return planets;
    }

}
