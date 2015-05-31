package traffic_fx;

/* traffic vectors
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


public class Car {
    TrafficSim parent;
    Position position;
    Road r;
    public double tripTime = 0;
    public double xSpeed;
    public double ySpeed;
    public double speed;
    public double dir;
    public double rev;
    public double bodySize;
    public Color bodyColor; 
    public double maxDecceleration = -5;
    public double normalBrakePower = .5;
    public double brakePower;
    public double maxSpeed = 20;
    public double maxAcceleration = 4;
    public double normalAccelerationPower = .5;
    public double accelerationPower;
    public double safetyMargin;
    double acceleration = 0;
    public String state = "start";
    public Car nextCar;
    public StopLight nextLight;
    public ArrayList<StopLight> allLights;
    public ArrayList<Car> allCars;
    
    
    public Car(TrafficSim p, Position tempPos, double tempSpeed, double tempDir, double tempRev){
        parent = p;
        position = new Position(tempPos.x, tempPos.y);
        speed = tempSpeed;
        dir = tempDir;
        rev = tempRev;
        bodySize = parent.carBodySize;
        bodyColor = Color.LIGHTBLUE;
        state = "starting";
        allLights = parent.allLights;
        allCars = parent.allCars;
        safetyMargin = 10; //bodySize/3;
    }
    
    public Car(TrafficSim p, Position tempPos, double tempSpeed, double tempDir, double tempRev, Color tempColor){
        parent = p;
        position = new Position(tempPos.x, tempPos.y);
        speed = tempSpeed;
        dir = tempDir;
        rev = tempRev;
        bodySize = parent.carBodySize;
        bodyColor = tempColor;
        state = "starting";
        allLights = parent.allLights;
        allCars = parent.allCars;
        safetyMargin = 10; //bodySize/3;
    }
    
    public void display() {
    if(state == "crashed"){
        parent.gc.setFill(Color.RED);
    }
    else{
        parent.gc.setFill(bodyColor);
    }
    //parent.noStroke();
    parent.gc.fillRect((float)position.x-bodySize/2,(float)position.y-bodySize/2,(float)bodySize, (float)bodySize);
  }

  // Move stripe
    
  public void executeCurrentState(){
      
      if(state.equals("starting")){
          starting();
      }
      
      else if(state.equals("crashed")){
          crashed();
      }
      else if(state.equals("driving")){
          driving();
      }
      else if(state.equals("finished")){
          finished();
      }
      else{
      }
      
  }
  
  void starting(){
    nextCar = findNextCar(parent.allCars);
    if(!this.intersectsCar(nextCar)){
        state = "driving";
    }
  }
  
   void driving(){
     nextLight = findNextLight(parent.allLights);
     nextCar = findNextCar(parent.allCars);
     
     if(collisionWith(nextCar)){
        System.out.printf("crash.  Car 1: %f, %f car 2: %f,  %f ", position.x, position.y, nextCar.position.x, nextCar.position.y );
        System.out.print(this);
        System.out.print(", ");
        System.out.println(nextCar);
         state = "crashed";
     }
     else if (position.x > parent.w || position.y > parent.h){
        parent.finishedCars.add(this);
        state = "finished";
    }
     else{
         move();
     }
     display();
  }
  
   void crashed(){
       
       
           
       display();  
   }
   
   void finished(){
       parent.allCars.remove(this);
   }
  void move(){
     tripTime += 1;
     acceleration = 1;//must be > 0 because calculated acceleration for braking might be 0.
     nextLight = findNextLight(parent.allLights);
     nextCar = findNextCar(parent.allCars);
     
      
    if(nextLight != null){
        //if(dir == 1){
            if(nextLight.state == "red" || nextLight.state == "yellow"){
                if(brakeDist(0.0, normalBrakePower * maxDecceleration) > distanceToLight(nextLight)){
                     acceleration = brake(distanceToLight(nextLight) - safetyMargin, 0);
    //               System.out.print("near light, acceleration = ");
    //               System.out.println(acceleration);
                }
            }
    }
     
     if(nextCar != null){
        if(brakeDist(0.0, normalBrakePower * maxDecceleration) > distanceToCar(nextCar) - safetyMargin){
             double tempAcceleration = brake(distanceToCar(nextCar) - safetyMargin, 0);
             acceleration = Math.min(acceleration, tempAcceleration);
//             System.out.print("near car, acceleration = ");
//             System.out.println(acceleration);
        }
     }
     
     
     if(acceleration > 0 && speed < maxSpeed){//not braking and below max speed
         acceleration = normalAccelerationPower * maxAcceleration;
     }
     double newSpeed = speed + acceleration;
     if(newSpeed > maxSpeed){
         newSpeed = maxSpeed;
     }
     if(newSpeed < 0){
         newSpeed = 0;
     }
    position.x += rev * dir * (speed + newSpeed)/2;
//if you start the turn with speed and end the turn with newSpeed, you have travelled at an 
    //average speed = (speed + newSpeed)/2
    position.y += rev * -1*(dir - 1) * (speed + newSpeed)/2;
    speed = newSpeed;
    
    if(rev == 1){
        if (position.x > parent.w || position.y > parent.h){
            parent.finishedCars.add(this);
            state = "finished";
        }
    }
    else{
        if (position.x < 0 || position.y < 0){
            parent.finishedCars.add(this);
            state = "finished";
        }
    }
  }
  
  public StopLight findNextLight(ArrayList<StopLight> lights){
      double minDist = 5000;
      int indexNearest = -1;//needs to be negative to start in case there is no light ahead.
      //System.out.println(allLights.size());
      for(int i = 0; i < lights.size(); i++){
          if(dir == 1){
              if(lights.get(i).position.y == position.y ){
                  //light is on right street
                  if(rev * (lights.get(i).position.x - position.x) < minDist  && rev * (lights.get(i).position.x - position.x) > 0){
                      //light is near and ahead
                      indexNearest = i;
                      minDist = rev * (lights.get(i).position.x - position.x);
                  }
              }
          }
          else{
              if(lights.get(i).position.x == position.x ){
                  if(rev * (lights.get(i).position.y - position.y) < minDist && rev * (lights.get(i).position.y - position.y) > 0){
                      indexNearest = i;
                      minDist = rev * (lights.get(i).position.y - position.y);
                  }
              }
          }
      }
      if(indexNearest >= 0){
          return lights.get(indexNearest);
      }
      else{
          return null;
      }
  }
  
  public Car findNextCar(ArrayList<Car> cars){
     double minDist = 5000;
      int indexNearest = -1;//needs to be negative in case there is no car ahead 
      
      for(int i = 0; i < cars.size(); i++){
          if(dir == 1){
              if(Math.abs(cars.get(i).position.y - position.y) < (bodySize + cars.get(i).bodySize)/2 ){
                  //car is in line
                  if(rev * (cars.get(i).position.x - position.x) < minDist  && rev * (cars.get(i).position.x - position.x) > 0){
                      //car is close and ahead
                      indexNearest = i;
                      minDist = rev * (cars.get(i).position.x - position.x);
                  }
              }
          }
          else{
              if(Math.abs(cars.get(i).position.x - position.x) < (bodySize + cars.get(i).bodySize)/2){
                  //car is on same street
                  if(rev * (cars.get(i).position.y - position.y) < minDist && rev * (cars.get(i).position.y - position.y) > 0){
                      //car is close and ahead
                      indexNearest = i;
                      minDist = rev * (cars.get(i).position.y - position.y);
                  }
              }
          }
      }
   
      if(indexNearest >= 0){
          return cars.get(indexNearest);
      }
      else{
          return null;
      }
  }
  public double distanceToLight(StopLight light){
      double d = 5000;
      if(light != null){
          //d = position.distanceTo(light.position) - (bodySize + parent.roadWidth)/2;
          if(dir == 1){// moving horizontally
            d = Math.abs(position.x - light.position.x) - (bodySize + parent.roadWidth)/2;
          }
          else{
             d = Math.abs(position.y - light.position.y) - (bodySize + parent.roadWidth)/2; 
          }
      }
      //TrafficSim.print("dist to light = ");
      //TrafficSim.println(d);
      return d;
  }
     
  
  public double distanceToCar(Car car){
      double d = 5000;
      if(car != null && car != this){
          if(dir == 1){// moving horizontally
            d = Math.abs(position.x - car.position.x) - (bodySize + car.bodySize)/2;
          }
          else{
             d = Math.abs(position.y - car.position.y) - (bodySize + car.bodySize)/2; 
          }
      }
      //TrafficSim.print("dist to car = ");
      //TrafficSim.println(d);
      return d;
  }
  
  public double brakeDist(double finalSpeed, double deccelRate){
      //calculates the distance required to reach velocity vel using breaking power of breakPower
      //based on current speed.
      double avgSpeed = (speed - finalSpeed)/2;
      double brakingTime = (speed - finalSpeed)/-deccelRate;
      double d = avgSpeed * brakingTime;
      //TrafficSim.print("brakeDist = ");
      //TrafficSim.println(d);
      return d;
  }
  
  public double brake(double dist, double finalSpeed){
      //returns constant acceleration (decceleration) rate needed to brake to a speed of finalSpeed in a distance of d.
      //limited by maxBrakePower
      double accelRate;
      double avgSpeed = (speed - finalSpeed)/2;
      if(avgSpeed <= 0 || dist <= 0){
          accelRate = maxDecceleration;
      }
      else{
          double brakingTime = dist/avgSpeed;
          accelRate = (finalSpeed - speed)/brakingTime;//accelRate will be negative if braking 
          if(accelRate < maxDecceleration || accelRate > 0){//accelRate will be positive if dist is negative, which might happen if the car is already ahead of the point it is trying to stop at.
              accelRate = maxDecceleration;
          }
      }
      return accelRate;
  }
  
  public boolean collisionWith(Car nc){
      if(nc != null){
          if(!(position.x == 0 || position.y == 0 || position.x == parent.w || position.y == parent.h)){
              if (intersectsCar(nc)){
                  return true;
              }
          }
      }
      return false;
  }
  
  public boolean intersectsCar(Car c){
      if(c != null){
          if((Math.abs(position.x - c.position.x) < (bodySize + c.bodySize)/2) 
                && (Math.abs(position.y - c.position.y) < (bodySize + c.bodySize)/2)){

              return true;
          }
      }
      
      return false; 
  }
  
}

