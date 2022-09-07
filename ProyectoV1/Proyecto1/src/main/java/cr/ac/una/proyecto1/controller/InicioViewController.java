/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.proyecto1.controller;

import com.jfoenix.controls.JFXButton;
import cr.ac.una.proyecto1.util.FlowController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author kevin
 */
public class InicioViewController extends Controller implements Initializable {

    @FXML
    private JFXButton btnIniciar;
    @FXML
    private JFXButton btnAcercaDe;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @Override
    public void initialize() {
        
    }

    @FXML
    private void onActionBtnIniciar(ActionEvent event) {
        FlowController.getInstance().goMain();
       getStage().close();
    }

    @FXML
    private void onActionBtnAcercaDe(ActionEvent event) {
        FlowController.getInstance().goViewInWindowModal("AcercaDeView", getStage(), false);
    }
    
}
