/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
 * @author ANTHONY
 */
public class PrincipalViewController extends Controller implements Initializable {

    @FXML
    private JFXButton btnNuevoJuego;
    @FXML
    private JFXButton btnCargarPartida;
    @FXML
    private JFXButton btnAcercaDe;
    @FXML
    private JFXButton btnJugadores;
    @FXML
    private JFXButton btnSalir;

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
    private void OnActionBtnNuevoJuego(ActionEvent event) {
        FlowController.getInstance().goView("NuevoJuegoView");
    }

    @FXML
    private void OnActionBtnCargarPartida(ActionEvent event) {
        FlowController.getInstance().goView("CargarPartidaView");
    }

    @FXML
    private void onActionBtnAcercaDe(ActionEvent event) {
        FlowController.getInstance().goViewInWindowModal("AcercaDeView", getStage(), false);
    }

    @FXML
    private void onActionBtnJugadores(ActionEvent event) {
        FlowController.getInstance().goView("TablaDeJugadoresView");        
    }

    @FXML
    private void onActionBtnSalir(ActionEvent event) {
        FlowController.getInstance().salir();
    }
    
}
