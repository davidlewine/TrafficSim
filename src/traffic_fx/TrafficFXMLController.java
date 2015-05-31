package traffic_fx;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author David
 */
public class TrafficFXMLController implements Initializable {
    @FXML
    public AnchorPane trafficScrollAnchor;
    @FXML
    public HBox buttonHBox;
    @FXML
    public HBox inputHBox;
    @FXML
    public AnchorPane lightGUIAnchorPane;
    @FXML
    public AnchorPane dataAnchorPane;
    @FXML
    public Canvas canvas;
    @FXML
    public TextArea dataTextArea;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
