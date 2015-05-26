/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author David
 */

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.paint.Color;

public class StopLight {
    TrafficSim parent;
    public Position position;
    double radius;
    int lightHeight = 15;
    Color lightColor; 
    int timer = 0;
    int timerOffset = 0;
    int redTime = 65;
    int yellowTime = 15;
    int greenTime = 50;
    int fullCycle;
    SimpleStringProperty redTimeProp = new SimpleStringProperty();
    SimpleStringProperty greenTimeProp = new SimpleStringProperty();
    SimpleStringProperty yellowTimeProp = new SimpleStringProperty();
    public String state;
    String states[] = {"green", "yellow", "red"};
    int stateTimes[] = {50, 15, 65};
    int lightColors[] = new int[4];
    double xOffsets[] = {-10, 0, 10, 0};
    double yOffsets[] = {0, 10, 0, -10};
    Color green, red, yellow;
    
    public StopLight(TrafficSim p, Intersection intersection, String facing){
        parent = p;
        fullCycle = redTime + greenTime + yellowTime;
        if(facing == "north"){
          radius = intersection.ns.roadWidth/3;
          position = new Position(intersection.position.x, intersection.position.y - intersection.ew.roadWidth/2);
          timer = 0;
          state = "green";
        }
        if(facing == "south"){
          radius = intersection.ns.roadWidth/3;
          position = new Position(intersection.position.x, intersection.position.y + intersection.ew.roadWidth/2);
          timer = 0;
          state = "green";
        }
        if(facing == "east"){
          radius = intersection.ew.roadWidth/3;
          position = new Position(intersection.position.x - intersection.ew.roadWidth/2, intersection.position.y); 
          timer = greenTime + yellowTime;
          state = "red";
        }
        if(facing == "west"){
          radius = intersection.ew.roadWidth/3;
          position = new Position(intersection.position.x + intersection.ew.roadWidth/2, intersection.position.y); 
          timer = greenTime + yellowTime;
          state = "red";
        }
        
        green = Color.GREEN;
        red = Color.RED;
        yellow = Color.YELLOW;
        
        state = states[0];

        
    }
    
    
//    public StopLight(TrafficSim p, Position tempPos, String startState){
//        parent = p;
//        position = new Position(tempPos.x, tempPos.y);
//        radius = parent.roadWidth/3;
//        xOffsets[0] = -radius;
//        xOffsets[2] = radius;
//        yOffsets[1] = -radius;
//        yOffsets[3] = radius;
//        state = startState;
//        
//    }
//    
//    public StopLight(TrafficSim p, Position tempPos){
//        parent = p;
//        position = new Position(tempPos.x, tempPos.y);
//        radius = parent.roadWidth/3;
//        xOffsets[0] = -radius;
//        xOffsets[2] = radius;
//        yOffsets[1] = -radius;
//        yOffsets[3] = radius;
//        state = states[(int)(3.0 * Math.random())]; 
//    }
    
//    public void display() {
//        for(int i = 0; i < 4; i++){
//            parent.fill(lightColors[i]);
//            parent.noStroke();
//            parent.ellipse((float)(position.x + xOffsets[i]), (float)(position.y + yOffsets[i]), (float)radius, (float)radius);
//        }
//  }
    
    public void display() {
        
        parent.gc.setFill(lightColor);
        //parent.noStroke();
        parent.gc.fillOval((float)(position.x - radius/2), (float)(position.y - radius/2), (float)radius, (float)radius);

    }
    
    public void green(){
//        lightColors[0] = parent.color(0, 255, 0);
//        lightColors[1] = parent.color(255, 0, 0);
//        lightColors[2] = parent.color(0, 255, 0);
//        lightColors[3] = parent.color(255, 0, 0);
        
        lightColor = green;
        
        timer += 1;
        timer = timer%fullCycle;
        if(timer >= greenTime && timer < greenTime + yellowTime){
            //timer = 0;
            state = "yellow";
        }
        if(timer >= greenTime +  yellowTime && timer < fullCycle){
            //timer = 0;
            state = "red";
        }
    }
    
    public void red(){
//        lightColors[0] = parent.color(255, 0, 0);
//        lightColors[1] = parent.color(0, 255, 0);
//        lightColors[2] = parent.color(255, 0, 0);
//        lightColors[3] = parent.color(0, 255, 0);
        
        lightColor = red;
        timer += 1;
        timer = timer%fullCycle;
//        if(timer >= redTime - yellowTime){
//            lightColors[1] = parent.color(255, 255, 0);
//            lightColors[3] = parent.color(255, 255, 0);
//        }
        if(timer < greenTime){
            state = "green";
        }
        if(timer >= greenTime && timer < greenTime + yellowTime){
            //timer = 0;
            state = "yellow";
        }
        
        
    }
    
    public void yellow(){
//        lightColors[0] = parent.color(255, 255, 0);
//        lightColors[1] = parent.color(255, 0, 0);
//        lightColors[2] = parent.color(255, 255, 0);
//        lightColors[3] = parent.color(255, 0, 0);
        lightColor = yellow;
        timer += 1;
        timer = timer%fullCycle;
        if(timer < greenTime){
            state = "green";
        }
        if(timer >= greenTime +  yellowTime && timer < fullCycle){
            //timer = 0;
            state = "red";
        }
    }
    
    public void executeState(){
        if(state == "green") green();
        if(state == "red") red();
        if(state == "yellow") yellow();
    }
    
    public void changeTiming(int g, int y, int r){
        redTime = r;
        greenTime = g;
        yellowTime = y;
    }
    
    public void setState(String newState, int t){
        timer = t;
        state = newState;
    }
    
    public void setRedTime(int rt){
        redTime = rt;
    }
    public void setGreenTime(int gt){
        greenTime = gt;
    }
    public void setYellowTime(int yt){
        yellowTime = yt;
        //yellowTimeProp.set("" + yt);
    }
    public void updateFullCycle(){
        fullCycle = redTime + greenTime + yellowTime;
    }

 
}
