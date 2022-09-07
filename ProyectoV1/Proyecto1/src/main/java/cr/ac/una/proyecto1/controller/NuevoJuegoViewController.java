/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.proyecto1.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import cr.ac.una.proyecto1.model.UsuarioDto;
import cr.ac.una.proyecto1.util.AppContext;
import cr.ac.una.proyecto1.util.FlowController;
import cr.ac.una.proyecto1.util.Formato;
import cr.ac.una.proyecto1.util.Mensaje;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ToggleGroup;

/**
 * FXML Controller class
 *
 * @author ANTHONY
 */
public class NuevoJuegoViewController extends Controller implements Initializable {

    @FXML
    private JFXTextField txtJugador3;
    @FXML
    private JFXTextField txtJugador1;
    @FXML
    private JFXTextField txtJugador2;
    @FXML
    private JFXTextField txtJugador4;
    @FXML
    private JFXButton btnEmpezar;
    @FXML
    private JFXRadioButton rbJovenes;
    @FXML
    private ToggleGroup tggDificultad;
    @FXML
    private JFXRadioButton rbAdultos1;
    @FXML
    private JFXButton btnAgregar;
    @FXML
    private JFXButton btnNuevo;
    @FXML
    private JFXTextField txtDuracion;
    @FXML
    private JFXButton btnEliminar1;
    @FXML
    private JFXButton btnEliminar2;
    @FXML
    private JFXButton btnEliminar3;
    @FXML
    private JFXButton btnEliminar4;
    
    List<UsuarioDto> Jugadores = new ArrayList<>();
    UsuarioDto[] jugadores = new UsuarioDto[4];
    Object Dificultad;
    Object Jugadoress;
    int cont = 0;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rbAdultos1.setUserData("A");
        rbAdultos1.setSelected(true);
        rbJovenes.setUserData("J");
        txtDuracion.setTextFormatter(Formato.getInstance().integerFormat());
    }    

    @Override
    public void initialize() {
    }

    @FXML
    private void onActionBtnEmpezar(ActionEvent event) {
        for(int i = 0; i < 4; i++) {
            if(jugadores[i] != null)
                Jugadores.add(jugadores[i]);
        }
        Dificultad = tggDificultad.getSelectedToggle().getUserData();
        
        if(Jugadores.size() >= 2 && !txtDuracion.getText().isEmpty()) {
            Jugadoress = Jugadores;
            AppContext.getInstance().set("Timer", txtDuracion.getText());
            FlowController.getInstance().goViewInWindowModal("JuegoView", getStage(),true);
        } else {
            Jugadores.clear();
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error iniciar partida", getStage(), "Faltan configurar la partida");
        }
    }

    @FXML
    private void onActionBtnAgregar(ActionEvent event) {  // para ingresar los jugadores
        BusquedaViewController busquedaController = (BusquedaViewController) FlowController.getInstance().getController("BusquedaView");
        busquedaController.busquedaUsuarios();
        FlowController.getInstance().goViewInWindowModal("BusquedaView", getStage(),true);
        UsuarioDto usuarioDto = (UsuarioDto) busquedaController.getResultado();
        if (usuarioDto != null && comprobarJugador(usuarioDto.getNombreUsu()) && cont <= 3) {
            if(txtJugador1.getText().isEmpty()) {
                jugadores[0] = usuarioDto;
                txtJugador1.setText(usuarioDto.getNombreUsu());
            } else if (txtJugador2.getText().isEmpty()) {
                jugadores[1] = usuarioDto;
                txtJugador2.setText(usuarioDto.getNombreUsu());
            } else if (txtJugador3.getText().isEmpty()) {
                jugadores[2] = usuarioDto;
                txtJugador3.setText(usuarioDto.getNombreUsu());
            } else if (txtJugador4.getText().isEmpty()) {
                jugadores[3] = usuarioDto;
                txtJugador4.setText(usuarioDto.getNombreUsu());
            }
            cont++;
        } else {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Agregar Usuario", getStage(), "Error ingresando el usuario");
        }
    }

    @FXML
    private void onActionBtnNuevo(ActionEvent event) {
        FlowController.getInstance().goViewInWindowModal("CrearJugadorView", getStage(), false);
    }

    @FXML
    private void onActionBtnEliminar1(ActionEvent event) {
        if(EliminarJugador(txtJugador1.getText()))
            txtJugador1.setText("");
    }

    @FXML
    private void onActionBtnEliminar2(ActionEvent event) {
        if(EliminarJugador(txtJugador2.getText()))
            txtJugador2.setText("");
    }

    @FXML
    private void onActionBtnEliminar3(ActionEvent event) {
        if(EliminarJugador(txtJugador3.getText()))
            txtJugador3.setText("");
    }

    @FXML
    private void onActionBtnEliminar4(ActionEvent event) {
        if(EliminarJugador(txtJugador4.getText()))
            txtJugador4.setText("");
    }
    
    public Object getResultado() {
        return Dificultad;
    }

    public Object getJugadoress() {
        return Jugadoress;
    }
    
    public Boolean comprobarJugador(String usu){  // Revisa que el jugador no esté ingresado ya
        for(int i = 0; i < 4; i++) {
            if (jugadores[i] != null) {
                if (usu.equals(jugadores[i].getNombreUsu())) {
                    return false;
                }
            }
        }
        return true;
    }
    public Boolean EliminarJugador(String usu){  // Revisa que el jugador no esté ingresado ya
        for(int i = 0; i < 4; i++) {
            if (jugadores[i] != null) {
                if (usu.equals(jugadores[i].getNombreUsu())) {
                    jugadores[i] = null;
                    cont--;
                    return true;
                }
            }
        }
        return false;
    }
}
