package Tracker.Config;

import java.util.ArrayList;

public class Planet {
    private String name;
    private ArrayList<String> nodes;

    public Planet(String name,ArrayList<String> nodes){
        this.name=name;
        this.nodes=nodes;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getNodes() {
        return nodes;
    }

    public String toString(){
        StringBuilder text=new StringBuilder(name+"\n");
        for(String n:nodes){
            text.append("\t"+n+"\n");
        }
        return text.toString();
    }
}
