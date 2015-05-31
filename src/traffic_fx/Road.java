package traffic_fx;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author David
 */

import traffic_fx.Position;
import traffic_fx.Intersection;
import traffic_fx.Car;
import java.util.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import  static javax.swing.JOptionPane.*;

public class Road {
    public TrafficSim parent;
    public double xInt = 0;
    public double yInt = 0;
    //public ArrayList<Car> cars = new ArrayList();
    public ArrayList<StopLight> lights = new ArrayList<StopLight>();
    public ArrayList<Intersection> intersections = new ArrayList<Intersection>();
    public int numCars;
    public int roadWidth;
    public int carFreq = 3;//on average, how frequently (as a percentage) a car is added to the street
    public int roadDir;
    public GraphicsContext gc;
    
    public Road(TrafficSim tp, double txInt, double tyInt, int rDir){
        parent = tp;
        gc = parent.gc;
        xInt = txInt;
        yInt = tyInt;
        roadDir = rDir;
        roadWidth = parent.roadWidth;
        
    }
    
    public void display(){
        //parent.gc.noStroke();
        
            
        
        //parent.rectMode(parent.CENTER);
        if(xInt > yInt){//vertical road
            gc.setFill(Color.LIGHTGREY);
            gc.fillRect((float)xInt-roadWidth/2, 0, roadWidth, parent.h);
            gc.setFill(Color.BLACK);
            gc.strokeText("" + (int)xInt, (int)(xInt + roadWidth/2), (int)10.0);
        }
        else{//horizontal road
            gc.setFill(Color.LIGHTGREY);
            gc.fillRect(0, (float)yInt-roadWidth/2, parent.w, roadWidth);
            gc.setFill(Color.BLACK);
            gc.strokeText("" + (int)yInt, 0, (int)yInt);
        }
       
        
    }
    
    public void addCar(){
        if(Math.random()*100 < carFreq){
            Position tempPosition;
            double tempDir;
            double tempRev;
            if(xInt > yInt){//vertical road
                
                if(roadDir == 1){
                  tempPosition = new Position(xInt, yInt);  
                  tempDir = 0;
                  tempRev = 1;
                }
                else{
                    tempPosition = new Position(xInt, parent.h);  
                    tempDir = 0;
                    tempRev = -1;
                }
              Car tempCar = new Car(parent, tempPosition, 0, tempDir, tempRev, Color.GOLDENROD);
              //cars.add(tempCar);
              parent.allCars.add(tempCar);
            }
            else{//horizontal road
               if(roadDir == 1){
                  tempPosition = new Position(xInt, yInt);  
                  tempDir = 1;
                  tempRev = 1;
                }
                else{
                    tempPosition = new Position(parent.w, yInt);  
                    tempDir = 1;
                    tempRev = -1;
                }
               Car tempCar = new Car(parent, tempPosition, 0, tempDir, tempRev, Color.DARKBLUE);
               //cars.add(tempCar);
               parent.allCars.add(tempCar);
            } 
        }
    }
    
//    void mouseClickedAction(){
//        if (Math.sqrt(Math.pow((float)xInt - parent.mouseX,2) + Math.pow((float)yInt-parent.mouseY,2)) < roadWidth/2){
//            setLights(intersections); 
//        }
//    }
    
//    void setLights(ArrayList<Intersection> intsToSet){
//        //get user input: 2 numbers per intersection representing east and west red light length.
//        if(showInputDialog("set red times? (y or n)").equals("y")){
//            for(int i = 0; i < intsToSet.size(); i++){
//                intsToSet.get(i).setRedTimes(); 
//            }
//        }
//        if(showInputDialog("set light timing? (y or n)").equals("y")){
//            for(int i = 0; i < intsToSet.size(); i++){
//                intsToSet.get(i).setLightTiming(); 
//            }
//        }
//        
//    }
    
}
