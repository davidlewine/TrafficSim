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

import javafx.scene.control.ScrollPane;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.GridPane;
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
    public void start(Stage primaryStage) {
        Group root = new Group();
        Scene scene = new Scene(root, width, height);
        primaryStage.setTitle("Traffic");
        primaryStage.setScene(scene);
        primaryStage.show();

        TrafficSim ts = new TrafficSim(width, height, root);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
