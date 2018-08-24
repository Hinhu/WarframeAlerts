package Tracker.Panes;

import Tracker.Buttons.SaveButton;
import Tracker.Buttons.SwitchButton;
import Tracker.Config.NodeReader;
import Tracker.Config.Planet;
import Tracker.Main;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;


public class ConfigPane extends ScrollPane {

    private Pane root;

    private SwitchButton switcher;
    private SaveButton saver;

    public ConfigPane(Stage stage) {
        super();
        setStyle("-fx-background: grey");
        setVbarPolicy(ScrollBarPolicy.NEVER);
        setHbarPolicy(ScrollBarPolicy.ALWAYS);

        root = new Pane();
        root.setStyle("-fx-background-color: grey");

        ArrayList<Planet> planets = null;
        try {
            planets = NodeReader.getPlanets();
        } catch (IOException e) {
            System.err.println("THERE WAS AN ERROR WHILE READING " + NodeReader.NODE_FILE + " FILE");
        }
        if (planets != null) {
            int startX = 20;
            int startY = 120;
            double width = 0;
            for (Planet p:planets){
                p.addToPane(startX+width,startY,root);
                width+=p.getWidth();
            }
            root.setMinWidth(width+20);
            setCheckBoxesSelection(planets);

            CheckBox checkAll=new CheckBox("Check all");
            checkAll.setFont(Font.font("Arial", FontWeight.BOLD,20));
            checkAll.setTranslateX(Main.WIDTH/2-70);
            checkAll.setTranslateY(40);
            ArrayList<Planet> finalPlanets = planets;
            checkAll.setOnMouseClicked(event -> {
                setPlanetsSelection(finalPlanets,checkAll.isSelected());
            });
            checkAll.setSelected(areAllPlanetsChecked(planets));
            root.getChildren().add(checkAll);
        }



        saver=new SaveButton(30,40,planets);
        switcher = new SwitchButton(Main.WIDTH-180,40, stage);

        root.getChildren().addAll(switcher,saver);

        setContent(root);
    }

    private boolean areAllPlanetsChecked(ArrayList<Planet> planets){
        for(Planet p: planets){
            if(p.getPlanetBox().isSelected()) {
                for (CheckBox n : p.getNodesBoxes()) {
                    if(!n.isSelected()){
                        return false;
                    }
                }
            }else{
                return false;
            }
        }
        return true;
    }

    private void setPlanetsSelection(ArrayList<Planet> planets,boolean selection){
        for(Planet p: planets){
            p.getPlanetBox().setSelected(selection);
            for(CheckBox n:p.getNodesBoxes()){
                n.setSelected(selection);
            }
        }
    }

    private void setCheckBoxesSelection(ArrayList<Planet> planets) {
        ArrayList<Planet> config = null;
        try {
            config=NodeReader.getConfig();
        } catch (IOException e) {
            System.err.println("THERE WAS A PROBLEM IN READING CONFIG FILE");
        }
        if(config!=null) {
            for (Planet c : config) {
                for(Planet p:planets){
                    if(p.getName().equals(c.getName())){
                        p.getPlanetBox().setSelected(true);
                        for(CheckBox nb:p.getNodesBoxes()){
                            for(CheckBox cb:c.getNodesBoxes()){
                                if(nb.getText().equals(cb.getText())){
                                    nb.setSelected(true);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void setAlertPane(AlertPane a) {
        switcher.setAlert(a);
    }

    public Pane getRoot() {
        return root;
    }
}
