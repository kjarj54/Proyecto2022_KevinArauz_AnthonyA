/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.proyecto1.controller;

import com.jfoenix.controls.JFXButton;
import cr.ac.una.proyecto1.model.UsuarioDto;
import cr.ac.una.proyecto1.service.UsuarioService;
import cr.ac.una.proyecto1.util.FlowController;
import cr.ac.una.proyecto1.util.Mensaje;
import cr.ac.una.proyecto1.util.Respuesta;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author UNA-Audivisuales
 */
public class TablaDeJugadoresViewController extends Controller implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private TableView<UsuarioDto> rootTableView;
    @FXML
    private TableColumn<UsuarioDto, String> nombresColum;
    @FXML
    private TableColumn<UsuarioDto, String> partidasGanadasColum;
    @FXML
    private TableColumn<UsuarioDto, String> partidasJugadasColum;
    @FXML
    private TableColumn<UsuarioDto, Boolean> eliminarColumn;
    @FXML
    private JFXButton btnNuevo;
    
    private ObservableList<UsuarioDto>usuarios=FXCollections.observableArrayList();
    Object resultado;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        nombresColum.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        nombresColum.setCellValueFactory(cd -> cd.getValue().nombre);
        partidasGanadasColum.setCellValueFactory(new PropertyValueFactory<>("partidasganadas"));
        partidasGanadasColum.setCellValueFactory(cd -> cd.getValue().partidasganadas);
        partidasJugadasColum.setCellValueFactory(new PropertyValueFactory<>("partidasjugadas"));
        partidasJugadasColum.setCellValueFactory(cd -> cd.getValue().partidasjugadas);
        eliminarColumn.setCellValueFactory(cd->new SimpleObjectProperty(cd.getValue() !=null));
        eliminarColumn.setCellFactory(cd -> new ButtonCell());
        
        cargarUsuarios();
    }    

    @Override
    public void initialize() {
        
    }

    @FXML
    private void onActionBtnNuevo(ActionEvent event) {
        FlowController.getInstance().goViewInWindowModal("CrearJugadorView", getStage(), false);
        cargarUsuarios();
    }
    
    private void cargarUsuarios() {
            UsuarioService service = new UsuarioService();
            Respuesta respuesta = service.getUsuarios();

        if (respuesta.getEstado()) {
            usuarios.clear();
            usuarios.addAll((List<UsuarioDto>) respuesta.getResultado("Usuario"));
            rootTableView.setItems(usuarios);
            rootTableView.refresh();
        } else {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar usuarios", getStage(), respuesta.getMensaje());
        }
    }

    public Object getResultado() {
        return resultado;
    }

    public void setResultado(Object resultado) {
        this.resultado = resultado;
    }
    
    
    
    private class ButtonCell extends TableCell<UsuarioDto, Boolean> {

        final Button cellButton = new Button();

        ButtonCell() {
            cellButton.setPrefWidth(500);
            cellButton.getStyleClass().add("jfx-btnimg-tbvEliminar");

            cellButton.setOnAction((ActionEvent t) -> {
                UsuarioDto usuario = (UsuarioDto) ButtonCell.this.getTableView().getItems().get(ButtonCell.this.getIndex());
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
                        }
                    }
                } catch (Exception ex) {
                    Logger.getLogger(CrearJugadorViewController.class.getName()).log(Level.SEVERE, "Error eliminando el usuario.", ex);
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar usuario", getStage(), "Ocurrio un error eliminando el usuario.");
                }
                rootTableView.getItems().remove(usuario);
                rootTableView.refresh();
            });
        }
        
        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if (!empty) {
                setGraphic(cellButton);
            }
        }
    }
}
