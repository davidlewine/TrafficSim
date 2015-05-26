
import javafx.scene.paint.Color;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author David
 */   



class LightBar {

  String dragging = "none";
  Position position;
  double r;
  Position nsTimeOffsetPosition, ewTimeOffsetPosition, nsRedTimePosition, ewRedTimePosition,
          northBarPosition, eastBarPosition;
  int barLength,barWidth, offsetButtonWidth = 5, yellowWidth = 5;
  int c;
  int cInd;
  StopLight northLight, eastLight;
  int eastLightOffset, northLightOffset, eastOffsetLength, northOffsetLength,
          eastRedLength, eastGreenLength, eastYellowLength, 
          northRedLength, northGreenLength, northYellowLength;
  TrafficSim parent;
  Intersection intersection;

   LightBar(TrafficSim p, Intersection intersect) {
    intersection = intersect;
    barWidth = intersection.ns.roadWidth/2;
    barLength = intersection.ns.roadWidth *5/2;
    position = intersection.position;
    parent = p;
    northLight = intersection.northLight;
    eastLight = intersection.eastLight;
    eastLightOffset = intersection.eastLightOffset;
    northLightOffset = intersection.northLightOffset;
    northBarPosition = new Position(position.x - barLength/2, position.y -(1.5*intersection.ew.roadWidth + barWidth));
    eastBarPosition = new Position(position.x - (1.5*intersection.ns.roadWidth + barWidth),position.y - barLength/2);
    northOffsetLength = barLength*northLightOffset/(northLight.greenTime + northLight.redTime + northLight.yellowTime);
    eastOffsetLength = barLength*eastLightOffset/(eastLight.greenTime + eastLight.redTime + eastLight.yellowTime);
    northGreenLength = barLength*northLight.greenTime/(northLight.greenTime + northLight.redTime + northLight.yellowTime);
    northRedLength = barLength*northLight.redTime/(northLight.greenTime + northLight.redTime + northLight.yellowTime);
    northYellowLength = barLength*northLight.yellowTime/(northLight.greenTime + northLight.redTime + northLight.yellowTime);
    eastGreenLength = barLength*eastLight.greenTime/(eastLight.greenTime + eastLight.redTime + eastLight.yellowTime);
    eastRedLength = barLength*eastLight.redTime/(eastLight.greenTime + eastLight.redTime + eastLight.yellowTime);
    eastYellowLength = barLength*eastLight.yellowTime/(eastLight.greenTime + eastLight.redTime + eastLight.yellowTime);    
  }

  void update() {
    northOffsetLength = barLength*northLightOffset/(northLight.greenTime + northLight.redTime + northLight.yellowTime);
    System.out.println("lightBar" + northLightOffset);
    if (dragging.equals("none")){
        return;
    }
    else if(dragging.equals("northOffset")){
        northOffsetLength = (int)(parent.mouseX - northBarPosition.x - offsetButtonWidth/2);
        northOffsetLength = Math.max(0, northOffsetLength);
        northOffsetLength = Math.min(barLength, northOffsetLength);
        int newNorthLightOffset = (northLight.greenTime + northLight.redTime + northLight.yellowTime) * northOffsetLength/barLength;
        int deltaOffset = newNorthLightOffset - northLightOffset;
        northLightOffset = newNorthLightOffset;
        intersection.northLightOffset = northLightOffset;//update intersection;
        northLight.timer+=deltaOffset;
    }
  }
    
  void display(){
      //parent.noStroke();
        parent.gc.setFill(Color.GREEN);
        parent.gc.fillRect((float)northBarPosition.x, (float)northBarPosition.y , northGreenLength, barWidth);
        parent.gc.setFill(Color.YELLOW);
        parent.gc.fillRect((float)northBarPosition.x + northGreenLength, (float)northBarPosition.y, northYellowLength, barWidth);
        parent.gc.setFill(Color.RED);
        parent.gc.fillRect((float)northBarPosition.x + northYellowLength + northGreenLength , (float)northBarPosition.y, northRedLength, barWidth);
        parent.gc.setFill(Color.BLACK);
        parent.gc.fillRect((float)northBarPosition.x + northOffsetLength, (float)northBarPosition.y, 5, 5);
  }
  
  void onMousePressed(){
      int mx = (int)parent.mouseX;
      int my = (int)parent.mouseY;
      //check if mouse pressed near north offset button
      double d = Position.dist(mx, my, (float)northBarPosition.x + northOffsetLength + offsetButtonWidth/2, 
                      (float)northBarPosition.y + offsetButtonWidth/2);
      System.out.println(d);
      if (d < 2*offsetButtonWidth) {
          dragging = "northOffset";
      }
      else if (Position.dist(mx, my, (float)northBarPosition.x + northRedLength + yellowWidth/2, 
                      (float)northBarPosition.y + barWidth/2) < barWidth/2) {
          dragging = "northRed";
      }
      else if (Position.dist(mx, my, (float)eastBarPosition.x + offsetButtonWidth/2, 
                    (float)eastBarPosition.y + eastOffsetLength + offsetButtonWidth/2) < offsetButtonWidth/2) {
          dragging = "eastOffset";
      }
      else if (Position.dist(mx, my, (float)eastBarPosition.x + offsetButtonWidth/2, 
                    (float)eastBarPosition.y + eastRedLength + yellowWidth/2) < barWidth/2) {
          dragging = "eastRed";
      }
      else {
          dragging = "none";
      }
      //System.out.println(dragging);
  }
  
  void onMouseReleased(){
      dragging = "none";
  }
}
