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

import java.util.*;
import javafx.scene.paint.Color;
// import math.geom2d.*;
import  static javax.swing.JOptionPane.*;

public class Intersection {
    
    public TrafficSim parent;
    public Position position;
    public ArrayList<StopLight> lights = new ArrayList<StopLight>();
    //public int
    public Road ew;
    public Road ns;
    public int fullLightCycle;
    StopLight northLight; 
    StopLight eastLight;
    public int eastLightOffset, northLightOffset, timerOffset = 0;
    public LightBar lightBar;
    //public String lightStates[] = {"green", "yellow", "red"};
    
    public Intersection(TrafficSim tp, Road tew, Road tns){
        parent = tp;
        ew = tew;
        ns = tns;
        position = new Position(ns.xInt, ew.yInt); 
        
        
        //create lights
        if(ns.roadDir == 1){
            northLight = new StopLight(parent, this, "north");
        }
        else{
            northLight = new StopLight(parent, this, "south");
        }
        if(ew.roadDir == 1){
            eastLight = new StopLight(parent, this, "east");
        }
        else{
            eastLight = new StopLight(parent, this, "west");
        }
        
        if(northLight.fullCycle == eastLight.fullCycle){
            fullLightCycle = northLight.fullCycle;
        }
        else{
            System.out.println("north and east lights have different cycle lengths.");
        }
//        //set east facing light state and timer randomly
//        int randomState = (int)(3.0 * Math.random());
//        eastLight.state = eastLight.states[randomState];
//        eastLight.timer = (int)(eastLight.stateTimes[randomState] * Math.random());
//        
//        //set other lights accordingly
//        if (eastLight.state == "green"){
//            northLight.state = "red";
//            northLight.timer = eastLight.timer;
//        }
//        if (eastLight.state == "yellow"){
//            northLight.state = "red";
//            northLight.timer = eastLight.greenTime + eastLight.timer;
//        }
//        if (eastLight.state == "red"){
//            if(eastLight.timer <= northLight.greenTime){
//                northLight.state = "green";
//                northLight.timer = eastLight.timer;
//            }
//            else{
//                northLight.state = "yellow";
//                northLight.timer = eastLight.timer - northLight.greenTime;
//            }
//        }
        
        //add lights to both parent list of all lights and this Intersection's list it's own lights.
        lights.add(eastLight);
        lights.add(northLight);
        for(int i = 0; i < lights.size(); i++){
        parent.allLights.add(lights.get(i));
        }
        lightBar = new LightBar(parent, this);
    }
    
    public void display(){
        parent.gc.setFill(Color.BLACK);
        parent.gc.fillOval((float)position.x, (float)position.y, 5, 5);
    }
    
    public void displayLightBars(){
        System.out.println("intersection" + northLightOffset);
        //draw ns light bar
        //parent.rectMode(parent.CORNER);
        int barWidth = ns.roadWidth/2;
        int barLength = ns.roadWidth *5/2;
        float greenLength = barLength*northLight.greenTime/(northLight.greenTime + northLight.redTime + northLight.yellowTime);
        float redLength = barLength*northLight.redTime/(northLight.greenTime + northLight.redTime + northLight.yellowTime);
        float yellowLength = barLength*northLight.yellowTime/(northLight.greenTime + northLight.redTime + northLight.yellowTime);
        float offsetLength = barLength*northLightOffset/(northLight.greenTime + northLight.redTime + northLight.yellowTime);
        
        int barX = (int)(position.x - barLength/2 );
        int barY = (int)(position.y -(1.5*ew.roadWidth + barWidth));
        //parent.noStroke();
        parent.gc.setFill(Color.GREEN);
        parent.gc.fillRect(barX,barY , greenLength, barWidth);
        parent.gc.setFill(Color.YELLOW);
        parent.gc.fillRect(barX + greenLength, barY, yellowLength, barWidth);
        parent.gc.setFill(Color.RED);
        parent.gc.fillRect(barX + yellowLength + greenLength , barY, redLength, barWidth);
        parent.gc.setFill(Color.BLACK);
        parent.gc.fillRect(barX + offsetLength, barY, 5, 5);
      
        //draw ew light bar
        
        barX = (int)(position.x - (1.5*ns.roadWidth + barWidth));
        barY = (int)(position.y - barLength/2);
        greenLength = barLength*eastLight.greenTime/(eastLight.greenTime + eastLight.redTime + eastLight.yellowTime);
        redLength = barLength*eastLight.redTime/(eastLight.greenTime + eastLight.redTime + eastLight.yellowTime);
        yellowLength = barLength*eastLight.yellowTime/(eastLight.greenTime + eastLight.redTime + eastLight.yellowTime);
        offsetLength = barLength*eastLightOffset/(eastLight.greenTime + eastLight.redTime + eastLight.yellowTime);
        parent.gc.setFill(Color.GREEN);
        //parent.noStroke();
        parent.gc.fillRect(barX,barY, barWidth, greenLength);
        parent.gc.setFill(Color.YELLOW);
        parent.gc.fillRect(barX, barY + greenLength, barWidth , yellowLength);
        parent.gc.setFill(Color.RED);
        parent.gc.fillRect(barX, barY + greenLength + yellowLength,barWidth, redLength);
        parent.gc.setFill(Color.BLACK);
        parent.gc.fillRect(barX , barY + offsetLength, 5, 5);
        
        
    }
//    public void setLights(){
//        if(showInputDialog("set red times? (y or n)").equals("y")){
//            setRedTimes();
//        }
//        
//        if(showInputDialog("set light timing? (y or n)").equals("y")){
//            setLightTiming();
//        }
//    }
    
    public void setLights(int eastRed, int northRed, int offset){
            setRedTimes(eastRed, northRed);
            setLightTiming(offset);
    }
    
//    public void setRedTimes(){
//        int east = 0;
//        int north = 1;
//        int eastRedTime = Integer.parseInt(showInputDialog("enter East Red Time"));
//        int northRedTime = Integer.parseInt(showInputDialog("enter North Red Time"));
//        //int offset = Integer.parseInt(showInputDialog("enter east offset (less than"));
//        fullLightCycle = eastRedTime + northRedTime;
//
//        lights.get(east).redTime = eastRedTime;
//        lights.get(east).greenTime = northRedTime - lights.get(0).yellowTime;
//        lights.get(north).redTime = northRedTime;
//        lights.get(north).greenTime = eastRedTime - lights.get(1).yellowTime; 
//    }
    
        public void setRedTimes(int nrt, int ert){
        int eastRedTime = ert;
        int northRedTime = nrt;
        
        //change east's timer offset by the difference between the new north redtime and the old north redtime.
        //int dEastTimerOffset = northRedTime - northLight.redTime;
        //int offset = Integer.parseInt(showInputDialog("enter east offset (less than"));
        fullLightCycle = eastRedTime + northRedTime;
        eastLight.redTime = eastRedTime;
        northLight.redTime = northRedTime;
        eastLight.greenTime = northLight.redTime - eastLight.yellowTime;
        northLight.greenTime = eastLight.redTime - northLight.yellowTime; 
        eastLight.updateFullCycle();
        northLight.updateFullCycle();
        setLightTiming(timerOffset);
    }
    
// public void setLightTiming(){
//        int east = 0;
//        int north = 1;
//        int offset = Integer.parseInt(showInputDialog("enter east offset (less than"));
//
//        //set north timer
//        if(offset <= lights.get(north).redTime){
//            lights.get(north).timer = offset;
//            lights.get(north).state = "red";
//        }
//        else if(offset <= fullLightCycle - lights.get(north).yellowTime){
//            lights.get(north).timer = offset - lights.get(north).redTime;
//            lights.get(north).state = "green";
//        }
//        else{
//            lights.get(north).timer = lights.get(north).yellowTime -(fullLightCycle - offset);
//            lights.get(north).state = "yellow";
//        }
//
//        //set east timer
//        if(offset <= lights.get(east).greenTime){
//            lights.get(east).timer = offset;
//            lights.get(east).state = "green";
//        }
//        else if(offset <= fullLightCycle - lights.get(east).redTime){
//            lights.get(east).timer = offset - lights.get(east).greenTime;
//            lights.get(east).state = "yellow";
//        }
//        else{
//            lights.get(east).timer = lights.get(east).redTime -(fullLightCycle - offset);
//            lights.get(east).state = "red";
//        } 
//    } 
 
    public void setLightTiming(int ot){
        int east = 0;
        int north = 1;
        northLightOffset = ot%fullLightCycle;
        lightBar.northLightOffset = northLightOffset;

        //set north timer
        if(northLightOffset <= northLight.redTime){
            northLight.timer = northLightOffset;
            northLight.state = "red";
        }
        else if(northLightOffset <= northLight.redTime + northLight.greenTime){
            northLight.timer = northLightOffset;
            northLight.state = "green";
        }
        else{
            northLight.timer = northLightOffset;
            northLight.state = "yellow";
        }

        //set east timer
        eastLightOffset = (eastLight.redTime + ot)%fullLightCycle;
        if(eastLightOffset <= eastLight.redTime){
            eastLight.timer = eastLightOffset;
            eastLight.state = "red";
        }
        else if(eastLightOffset <= eastLight.redTime + eastLight.greenTime){
            eastLight.timer = eastLightOffset;
            eastLight.state = "green";
        }
        else{
            eastLight.timer = eastLightOffset;
            eastLight.state = "yellow";
        }
    }
    
    public void onMousePressed(){
        lightBar.onMousePressed();
    }
    public void onMouseReleased(){
        lightBar.onMouseReleased();
    }
}
