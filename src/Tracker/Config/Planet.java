package Tracker.Config;

import javafx.scene.control.CheckBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class Planet {
    private String name;
    private ArrayList<String> nodes;
    private CheckBox planetBox;
    private ArrayList<CheckBox> nodesBoxes;
    private double height;
    private double width;

    public Planet(String name, ArrayList<String> nodes) {
        this.name = name;
        this.nodes = nodes;
        nodesBoxes = new ArrayList<>();
        planetBox = new CheckBox(name);
        planetBox.setFont(Font.font("Arial", FontWeight.BOLD,18));
        planetBox.setOnMouseClicked(event -> {
            if(planetBox.isSelected()) {
                for (CheckBox c : nodesBoxes) {
                    c.setSelected(true);
                }
            }else{
                for (CheckBox c : nodesBoxes) {
                    c.setSelected(false);
                }
            }
        });
        for (String n : nodes) {
            nodesBoxes.add(new CheckBox(n));
        }
    }

    public void addToPane(double x, double y, Pane root) {
        root.getChildren().add(planetBox);
        root.getChildren().addAll(nodesBoxes);
        planetBox.setTranslateX(x);
        planetBox.setTranslateY(y);
        double max=new Text(planetBox.getText()).getLayoutBounds().getWidth();
        double startPoint=planetBox.getTranslateY()+24;
        for(int i=0;i<nodesBoxes.size();i++){
            nodesBoxes.get(i).setTranslateX(x);
            nodesBoxes.get(i).setTranslateY(startPoint+i*20+2);
            double m=new Text(nodesBoxes.get(i).getText()).getLayoutBounds().getWidth();
            if(max<m){
                max=m;
            }
        }
        this.width=max+100;
        double y2=nodesBoxes.get(nodes.size()-1).getTranslateY()+nodesBoxes.get(0).getFont().getSize()+10;
        this.height=y2-y;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getNodes() {
        return nodes;
    }

    public String toString() {
        StringBuilder text = new StringBuilder(name + "\n");
        for (String n : nodes) {
            text.append("\t" + n + "\n");
        }
        return text.toString();
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }
}
