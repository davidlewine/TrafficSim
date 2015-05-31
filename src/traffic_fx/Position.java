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
public class Position {
    public double x;
    public double y;
    
    public Position(double tx, double ty){
        x = tx;
        y = ty;
    }
    
    public double distanceTo(Position p){
        return Math.sqrt(Math.pow((this.x - p.x),2) + Math.pow((this.y - p.y),2));
    }
    
    public static double dist(double x1, double y1, double x2, double y2){
        return Math.sqrt(Math.pow(x1 - x2,2) + Math.pow(y1 - y2,2));
    }
    
}
