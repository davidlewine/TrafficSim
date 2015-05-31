package traffic_fx;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import traffic_fx.TrafficFXMLController;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author David
 */
public class TrafficFxLauncher extends Application {
    int width = 800;
    int height = 600;
    int fps = 15;
    int resetFrame;
    int frameCounter = 0;
    AnimationTimer animationTimer;
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TrafficFXML.fxml"));
        Parent root = loader.load();

        TrafficFXMLController guiController = loader.<TrafficFXMLController>getController();

        Scene scene = new Scene(root, width, height);
        primaryStage.setTitle("Traffic");
        primaryStage.setScene(scene);
        primaryStage.show();

        TrafficSim ts = new TrafficSim(width, height, guiController);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}

//import javafx.application.Application;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.layout.StackPane;
//import javafx.stage.Stage;
//
///**
// *
// * @author David
// */
//public class Launcher extends Application {
//
//    int fps = 60;
//
//    @Override
//    public void start(Stage stage) throws Exception {

//    }
//
//    
//
//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String[] args) {
//        Application.launch(Launcher.class, (java.lang.String[]) null);
//    }
//
//}
