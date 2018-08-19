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
        getPlanetBox().setFont(Font.font("Arial", FontWeight.BOLD,18));
        getPlanetBox().setOnMouseClicked(event -> {
            if(getPlanetBox().isSelected()) {
                for (CheckBox c : getNodesBoxes()) {
                    c.setSelected(true);
                }
            }else{
                for (CheckBox c : getNodesBoxes()) {
                    c.setSelected(false);
                }
            }
        });
        for (String n : nodes) {
            CheckBox nodeBox=new CheckBox(n);
            getNodesBoxes().add(nodeBox);
            nodeBox.setOnMouseClicked(event -> {
                if(nodeBox.isSelected()){
                    planetBox.setSelected(true);
                }
            });
        }
    }

    public void addToPane(double x, double y, Pane root) {
        root.getChildren().add(getPlanetBox());
        root.getChildren().addAll(getNodesBoxes());
        getPlanetBox().setTranslateX(x);
        getPlanetBox().setTranslateY(y);
        double max=new Text(getPlanetBox().getText()).getLayoutBounds().getWidth();
        double startPoint= getPlanetBox().getTranslateY()+24;
        for(int i = 0; i< getNodesBoxes().size(); i++){
            getNodesBoxes().get(i).setTranslateX(x);
            getNodesBoxes().get(i).setTranslateY(startPoint+i*20+2);
            double m=new Text(getNodesBoxes().get(i).getText()).getLayoutBounds().getWidth();
            if(max<m){
                max=m;
            }
        }
        this.width=max+100;
        double y2= getNodesBoxes().get(getNodes().size()-1).getTranslateY()+ getNodesBoxes().get(0).getFont().getSize()+10;
        this.height=y2-y;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getNodes() {
        return nodes;
    }

    public String toString() {
        StringBuilder text = new StringBuilder();
        if(planetBox.isSelected()) {
            text.append(name + "\n");
            for (CheckBox c : nodesBoxes) {
                if (c.isSelected()) {
                    text.append("\t" + c.getText() + "\n");
                }
            }
            text.append("END\n");
        }
        return text.toString();
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public CheckBox getPlanetBox() {
        return planetBox;
    }

    public ArrayList<CheckBox> getNodesBoxes() {
        return nodesBoxes;
    }
}
