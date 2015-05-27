/*
 *traffic sim To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author David
 */
import java.util.*;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


public class TrafficSim {

    public ArrayList<StopLight> allLights = new ArrayList<>();
    public ArrayList<Car> allCars = new ArrayList<>();
    public ArrayList<Car> finishedCars = new ArrayList<>();
    public ArrayList<Road> streets = new ArrayList<>();
    public ArrayList<Road> avenues = new ArrayList<>();
    ;
    public ArrayList<Intersection> intersections = new ArrayList<>();
    public int eastRedTime = 30;
    public int northRedTime = 40;
    public int avenueOffset = 10;
    public int streetOffset = 6;
    public double mouseX, mouseY;
    float w;
    float h;
    //int numCars = 7;
    int numStreets = 5;
    int numAvenues = 5;
    //int numLights = 3;
    public double carBodySize = 10;//30
    public int roadWidth = 20;//50
    public int centerX = 0, centerY = 0, offsetX = 0, offsetY = 0, zoom = 100;
    boolean displayLightBars = false;
    GraphicsContext gc;
    Canvas canvas;
    //ScrollPane canvasScrollPane;
    GridPane guiGridPane;
    Group guiRoot;
    //HBox guiControls; 
    int resetFrame = 0, frameCounter = 0, fps = 15;
    AnimationTimer animationTimer;
    


    public TrafficSim(int ww, int hh, Group groot) {
        w = ww;
        h = hh;
        guiRoot = groot;
        createGui();
        resetFrame = 60/fps;
        setup();
        draw();
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
               if(frameCounter == resetFrame){
                draw();
                frameCounter = 0;
               }
               frameCounter++;

            }
        };
    }
    
    public void createGui(){
        //create main sections of gui
        canvas = new Canvas(w,h);
        gc = canvas.getGraphicsContext2D();
        ScrollPane canvasScrollPane = new ScrollPane(canvas);
        VBox controlsAndGrid = new VBox();
        SplitPane mainSplitPane = new SplitPane(canvasScrollPane, controlsAndGrid);
        mainSplitPane.setOrientation(Orientation.HORIZONTAL);
        mainSplitPane.setDividerPositions(.5);
        guiRoot.getChildren().add(mainSplitPane);
        
        //make gui control buttons
        HBox guiButtons = new HBox();
        
        Button collisionButton = new Button("Clear Collisions");
        collisionButton.setOnAction((ActionEvent e)->{
            clearCollisions();
        });
        guiButtons.getChildren().add(collisionButton);
        
        Button dataButton = new Button("print data");
        dataButton.setOnAction((ActionEvent e)->{
            displayData();
        });
        guiButtons.getChildren().add(dataButton);
        
        Button resetButton = new Button("reset");
        resetButton.setOnAction((ActionEvent e)->{
            reset();
        });
        guiButtons.getChildren().add(resetButton);
        
        Button runButton = new Button("run");
        runButton.setOnAction((ActionEvent e)->{
            animationTimer.start();
        });
        guiButtons.getChildren().add(runButton);
        
        Button pauseButton = new Button("pause");
        pauseButton.setOnAction((ActionEvent e)->{
            animationTimer.stop();
        });
        guiButtons.getChildren().add(pauseButton);
        
        controlsAndGrid.getChildren().add(guiButtons);
        
        //make gui input TextFields
        HBox textFieldBox = new HBox();
        Label streetsLabel = new Label("set streets");
        Label avenuesLabel = new Label("set avenues");
        TextField streets = new TextField("5");
        streets.setOnAction((ActionEvent e)->{
            numStreets = Integer.parseInt(streets.getText());
        });
        TextField avenues = new TextField("5");
        avenues.setOnAction((ActionEvent e)->{
            numAvenues = Integer.parseInt(avenues.getText());
        });
        textFieldBox.getChildren().addAll(streetsLabel, streets, avenuesLabel, avenues);
        controlsAndGrid.getChildren().add(textFieldBox);
        
        
       
        
        //make GridPane for light controls
        guiGridPane = new GridPane();
        ScrollPane gridScrollPane = new ScrollPane(guiGridPane);
        guiGridPane.setHgap(5);
        guiGridPane.setVgap(5);
        //guiGridPane.setGridLinesVisible(true);
        controlsAndGrid.getChildren().add(gridScrollPane);
 
    }
    
    public void reset(){
        animationTimer.stop();
        allLights.clear();
        allCars.clear();
        finishedCars.clear();
        streets.clear();
        avenues.clear();
        intersections.clear();
        guiGridPane.getChildren().clear();
        
        setup();
        draw();
    }

    public void setup() {

        for (int i = 0; i < numStreets; i++) {
            streets.add(new Road(this, (i + 1) * w / (numStreets + 1), 0, 1 - (i % 2 * 2)));
        }

        for (int i = 0; i < numAvenues; i++) {
            avenues.add(new Road(this, 0, (i + 1) * h / (numAvenues + 1), 1 - (i % 2 * 2)));
        }
        for (int i = 0; i < streets.size(); i++) {
            for (int j = 0; j < avenues.size(); j++) {
                //allLights.add(new StopLight((TrafficSim)this, new Position(streets.get(i).xInt, avenues.get(j).yInt)));
                Intersection tempIntersection = new Intersection(this, avenues.get(j), streets.get(i));
                intersections.add(tempIntersection);
                avenues.get(j).intersections.add(tempIntersection);
                streets.get(i).intersections.add(tempIntersection);
                //gridPane.add(new Label("hi"), i, j);
                guiGridPane.add(new LightGui(tempIntersection),i,j);
            }
        }
        //setLights();

//        canvas.setOnMousePressed((MouseEvent e) -> {
//            for (Intersection intersection : intersections) {
//                intersection.onMousePressed();
//            }
//        }
//        );
//
//        canvas.setOnMouseReleased((MouseEvent e)-> {
//            for (Intersection intersection : intersections) {
//                intersection.onMouseReleased();
//            }
//        }
//        );
//        
//        canvas.setOnMouseMoved((MouseEvent e)-> {
//            mouseX = e.getX();
//            mouseY = e.getY();
//        }
//        );
    }

    public void draw() {
        System.out.println("draw");
        gc.setFill(Color.WHITE);
        gc.fillRect(0,0,w, h);

        //scroll();

        for (int i = 0; i < streets.size(); i++) {
            streets.get(i).addCar();
            streets.get(i).display();
        }
        for (int i = 0; i < avenues.size(); i++) {
            avenues.get(i).addCar();
            avenues.get(i).display();
        }

//    for(int i = 0; i < intersections.size(); i++){
//        intersections.get(i).display();
//    }
        //iterate over a copy of ArrayList allCars because move() function may result in car being removed from list.
        //Call the move function of the corresponding car in the original list.
        //if car in original list is removed, iteration over copy won't give an error.
        ArrayList<Car> allCarsCopy = new ArrayList<Car>(allCars);
        //for(int i = 0; i<allCars.size(); i++)
        //println(allCars.get(i));
        for (int i = 0; i < allCarsCopy.size(); i++) {
            int nextCarIndex = allCars.indexOf(allCarsCopy.get(i));
            if (nextCarIndex > -1) {
                allCars.get(nextCarIndex).executeCurrentState();
            }
        }
//    for(int i = 0; i < allCars.size(); i++){
//        allCars.get(i).display();
//    }
        for (StopLight allLight : allLights) {
            allLight.executeState();
            allLight.display();
        }

        if (displayLightBars) {
            for (Intersection intersection : intersections) {
                intersection.lightBar.update();
                intersection.displayLightBars();
            }
        }

//        if (keyPressed && key == 'e') {
//            displayData();
//            exit();
//        }
    }
    
    public void clearCollisions(){
        Iterator<Car> carIter = allCars.iterator();
        while(carIter.hasNext()){
            if(carIter.next().state.equals("crashed")){
                carIter.remove();
            }
        }
    }
    
    public void clearCars(){
        allCars.clear();
        finishedCars.clear();
    }

    public void displayData() {
        int totalTripTimes = 0;
        for (int i = 0; i < finishedCars.size(); i++) {
            totalTripTimes += finishedCars.get(i).tripTime;
        }
        int avgTripTime = totalTripTimes / finishedCars.size();
        System.out.print("finished cars: ");
        System.out.println(finishedCars.size());
        System.out.print("avg time: ");
        System.out.println(avgTripTime);
        System.out.print("cars not finishing: ");
        System.out.println(allCars.size());

    }
    
    public void addLightController(Intersection intersection){
        
    }

//    public void mouseClicked() {
//
//        for (int i = 0; i < avenues.size(); i++) {
//            avenues.get(i).mouseClickedAction();
//        }
//        for (int i = 0; i < streets.size(); i++) {
//            streets.get(i).mouseClickedAction();
//        }
//
//    }
//    public void keyPressed() {
//        if (key == 'l') {
//            displayLightBars = !displayLightBars;
//        }
//    }

//    void scroll() {
//        if (keyPressed == true) {
//
//            if (key == 'u') {
//                zoom += 5;
//            }
//            if (key == 'j') {
//                zoom -= 5;
//            }
//
//            if (key == 'r') {
//                zoom = 100;
//                centerX = 0;
//                centerY = 0;
//            }
//
//            if (keyCode == RIGHT) {
//                centerX = centerX + 20;
//            }
//            if (keyCode == LEFT) {
//                centerX = centerX - 20;
//            }
//            if (keyCode == UP) {
//                centerY = centerY - 20;
//            }
//            if (keyCode == DOWN) {
//                centerY = centerY + 20;
//            }
//        }
//        //System.out.println("x: " + centerX + " Y: " + centerY + " zoom: " + zoom);
//        translate(centerX, centerY);
//        scale((float) (zoom / 100.0));
//    }

    public void setLights() {
        //sets lights so that avenues (ew) are synchronized
        for (int i = 0; i < avenues.size(); i++) {
            for (int j = 0; j < streets.size(); j++) {
                avenues.get(i).intersections.get(j).setLights(eastRedTime, northRedTime, (Math.abs(numStreets * (i % 2) - (numStreets - j)) * avenueOffset + (numAvenues - i) * streetOffset) % (eastRedTime + northRedTime));
            }
        }
    }
}
