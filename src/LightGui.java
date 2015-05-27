
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author David
 */
public class LightGui extends GridPane{
    Intersection intersection;
    //VBox box; 
    TextField nsRedTime, ewRedTime, timerOffset;
    Label nsRedTimeLabel, ewRedTimeLabel, timerOffsetLabel;
    public LightGui(Intersection is){
        intersection = is;
        
        //create timerOffset textField
        //HBox toBox = new HBox();
        timerOffsetLabel = new Label("TO:");
        //toBox.getChildren().add(timerOffsetLabel);
        timerOffset = new TextField("" + intersection.timerOffset);
        timerOffset.setPrefColumnCount(3);
        //toBox.getChildren().add(timerOffset);
        timerOffset.setOnAction((ActionEvent e)->{
            int newTimerOffset = Integer.parseInt(timerOffset.getText());
            int dTimerOffset =  intersection.timerOffset - newTimerOffset;
            //thus a positive dTimerOffset will result in a delayed green light
            
            intersection.northLight.timer+= dTimerOffset;
            intersection.eastLight.timer+= dTimerOffset;
            intersection.timerOffset = newTimerOffset;
        });
        //this.add(toBox,);
        
        //create nsRedTime text field
        //HBox nshbox = new HBox();
        nsRedTimeLabel = new Label("NSR:");
        //nshbox.getChildren().add(nsRedTimeLabel);
        nsRedTime = new TextField("" + intersection.northLight.redTime);
        nsRedTime.setPrefColumnCount(3);
        //nshbox.getChildren().add(nsRedTime);
        nsRedTime.setOnAction((ActionEvent e)->{
            System.out.println("ns set");
            //int newRedTime = Integer.parseInt(nsRedTime.getText());
            intersection.setRedTimes(Integer.parseInt(nsRedTime.getText()), Integer.parseInt(ewRedTime.getText()));
            //intersection.eastLight.setGreenTime(newRedTime - intersection.eastLight.yellowTime);
            //ewRedTime.setText("" + intersection.eastLight.redTime);
            System.out.println("nsrt: " + intersection.northLight.greenTime + " ewrt: " + intersection.eastLight.greenTime);
            
        });
        //this.getChildren().add(nshbox);

        
        //create ewRedTime text field
        //HBox ewhbox = new HBox();
        ewRedTimeLabel = new Label("EWG:");
        //ewhbox.getChildren().add(ewRedTimeLabel);
        ewRedTime = new TextField("" + intersection.eastLight.redTime);
        ewRedTime.setPrefColumnCount(3);
        //ewhbox.getChildren().add(ewRedTime);
        ewRedTime.setOnAction((ActionEvent e)->{
            System.out.println("ew set");
            //int newRedTime = Integer.parseInt(ewRedTime.getText());
            intersection.setRedTimes(Integer.parseInt(nsRedTime.getText()), Integer.parseInt(ewRedTime.getText()));
            //intersection.northLight.setGreenTime(newRedTime - intersection.northLight.yellowTime);
            //nsRedTime.setText("" + intersection.northLight.redTime);
            System.out.println("nsrt: " + intersection.northLight.greenTime + " ewrt: " + intersection.eastLight.greenTime);
        });
        //this.getChildren().add(ewhbox);
        this.add(timerOffsetLabel, 0, 0);
        this.add(timerOffset, 1, 0);
        this.add(nsRedTimeLabel, 0, 1);
        this.add(nsRedTime, 1, 1);
        this.add(ewRedTimeLabel, 0, 2);
        this.add(ewRedTime, 1, 2);
        
    }
     
}
