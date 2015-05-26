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
    @Override
    public void start(Stage primaryStage) {
        

        final Canvas canvas = new Canvas(width,height);
        //GraphicsContext gc = canvas.getGraphicsContext2D();
        Group root = new Group();
        root.getChildren().add(canvas);
        
        Scene scene = new Scene(root, width, height);
        
        primaryStage.setTitle("Traffic");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        Stage guiStage = new Stage();
        Group guiRoot = new Group();
        //ScrollPane guiScrollPane = new ScrollPane();
        
        Scene guiScene = new Scene(guiRoot, 400, 400);
        guiStage.setScene(guiScene);
        guiStage.show();
        
        TrafficSim ts = new TrafficSim(width, height, canvas, guiRoot);
        ts.setup();
        resetFrame = 60/fps;
        new AnimationTimer() {
            @Override
            public void handle(long now) {
               if(frameCounter == resetFrame){
                ts.draw();
                frameCounter = 0;
               }
               frameCounter++;

            }
        }.start();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
