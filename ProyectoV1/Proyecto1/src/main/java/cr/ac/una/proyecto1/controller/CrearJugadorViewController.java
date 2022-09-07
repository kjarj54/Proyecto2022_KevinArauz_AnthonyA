/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.proyecto1.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import cr.ac.una.proyecto1.model.UsuarioDto;
import cr.ac.una.proyecto1.service.UsuarioService;
import cr.ac.una.proyecto1.util.Formato;
import cr.ac.una.proyecto1.util.Mensaje;
import cr.ac.una.proyecto1.util.Respuesta;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author ANTHONY
 */
public class CrearJugadorViewController extends Controller implements Initializable {

    @FXML
    private JFXTextField txtId;
    @FXML
    private JFXTextField txtNombre;
    @FXML
    private JFXCheckBox chkActivo;
    @FXML
    private JFXButton btnNuevo;
    @FXML
    private JFXButton btnEliminar;
    @FXML
    private JFXButton btnGuardar;
    
    UsuarioDto usuario;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtId.setTextFormatter(Formato.getInstance().integerFormat());
        txtNombre.setTextFormatter(Formato.getInstance().cedulaFormat(50));
        usuario = new UsuarioDto();
        nuevoUsuario();
    }    

    @Override
    public void initialize() {
    }

    @FXML
    private void onKeyPressedTxtId(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER && !txtId.getText().isEmpty()) {
            cargarUsuario(Long.valueOf(txtId.getText()));
        }
    }

    @FXML
    private void onActionChkActivo(ActionEvent event) {
    }

    @FXML
    private void onActionBtnNuevo(ActionEvent event) {
        if (new Mensaje().showConfirmation("Limpiar usuario", getStage(), "¿Esta seguro que desea limpiar el registro?")) {
            nuevoUsuario();
        }
    }

    @FXML
    private void onActionBtnEliminar(ActionEvent event) {
        try {
            if (usuario.getId()== null) {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar usuario", getStage(), "Debe cargar el usuario que desea eliminar.");
            } else {

                UsuarioService service = new UsuarioService();
                Respuesta respuesta = service.eliminarUsuario(usuario.getId());
                if (!respuesta.getEstado()) {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar usuario", getStage(), respuesta.getMensaje());
                } else {
                    new Mensaje().showModal(Alert.AlertType.INFORMATION, "Eliminar usuario", getStage(), "Usuario eliminado correctamente.");
                    nuevoUsuario();
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(CrearJugadorViewController.class.getName()).log(Level.SEVERE, "Error eliminando el usuario.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar usuario", getStage(), "Ocurrio un error eliminando el usuario.");
        }
    }

    @FXML
    private void onActionBtnGuardar(ActionEvent event) {
        try {
            UsuarioService service = new UsuarioService();
            Respuesta respuesta = service.guardarUsuario(usuario);
            if (!respuesta.getEstado()) {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar usuario", getStage(), respuesta.getMensaje());
            } else {
                unbindUsuario();
                usuario = (UsuarioDto) respuesta.getResultado("Usuario");
                bindUsuario(false);
                new Mensaje().showModal(Alert.AlertType.INFORMATION, "Guardar usuario", getStage(), "Usuario actualizado correctamente.");
            }
        } catch (Exception ex) {
            Logger.getLogger(CrearJugadorViewController.class.getName()).log(Level.SEVERE, "Error guardando el usuario.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar usuario", getStage(), "Ocurrio un error guardando el usuario.");
        }
    }
    
    private void nuevoUsuario() {
        unbindUsuario();
        usuario = new UsuarioDto();
        bindUsuario(true);
        txtId.clear();
        txtId.requestFocus();
    }
    
    private void bindUsuario(Boolean nuevo) {
        if(!nuevo){
            txtId.textProperty().bind(usuario.id);
        }
        txtNombre.textProperty().bindBidirectional(usuario.nombre);
        chkActivo.selectedProperty().bindBidirectional(usuario.estado);
    }

    private void unbindUsuario() {
        txtId.textProperty().unbind();
        txtNombre.textProperty().unbindBidirectional(usuario.nombre);
        chkActivo.selectedProperty().unbindBidirectional(usuario.estado);
    }
    
    private void cargarUsuario(Long id) {
        UsuarioService service = new UsuarioService();
        Respuesta respuesta = service.getUsuario(id);

        if (respuesta.getEstado()) {
            unbindUsuario();
            usuario = (UsuarioDto) respuesta.getResultado("Usuario");
            bindUsuario(false);
        } else {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar usuario", getStage(), respuesta.getMensaje());
        }
    }
}
