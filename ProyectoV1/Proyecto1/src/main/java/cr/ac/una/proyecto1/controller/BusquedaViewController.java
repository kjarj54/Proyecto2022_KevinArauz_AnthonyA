/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.proyecto1.controller;

import com.jfoenix.controls.JFXButton;
import cr.ac.una.proyecto1.model.UsuarioDto;
import cr.ac.una.proyecto1.service.UsuarioService;
import cr.ac.una.proyecto1.util.Mensaje;
import cr.ac.una.proyecto1.util.Respuesta;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author UNA-Audivisuales
 */
public class BusquedaViewController extends Controller implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private Label lblTitulo;
    @FXML
    private TableView tbvResultados;
    @FXML
    private JFXButton btnAceptar;
    
    private EventHandler<KeyEvent> keyEnter;
    private ObservableList<UsuarioDto>usuarios=FXCollections.observableArrayList();
    Object resultado;
    

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
    private void onMousePressenTbvResultados(MouseEvent event) {
        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
            onActionBtnAceptar(null);
        }
    }
    
    @FXML
    private void onActionBtnAceptar(ActionEvent event) {
        resultado = tbvResultados.getSelectionModel().getSelectedItem();
        //getStage().getScene().setRoot(new Pane());
        getStage().close();
    }
    
    public Object getResultado() {
        return resultado;
    }
    
    public void busquedaUsuarios(){
        try{
            lblTitulo.setText("Busqueda Usuarios");
            
            tbvResultados.getColumns().clear();
            tbvResultados.getItems().clear();
            
            TableColumn<UsuarioDto, String> tbcId = new TableColumn<>("Id");
            tbcId.setPrefWidth(30);
            tbcId.setCellValueFactory(cd -> cd.getValue().id);
            
            TableColumn<UsuarioDto,String> tbcNombre = new TableColumn<>("Nombre");
            tbcNombre.setPrefWidth(100);
            tbcNombre.setCellValueFactory(cd -> cd.getValue().nombre);

            TableColumn<UsuarioDto,String> tbcPjugadas = new TableColumn<>("Partidas Jugadas");
            tbcPjugadas.setPrefWidth(100);
            tbcPjugadas.setCellValueFactory(cd -> cd.getValue().partidasjugadas);

            TableColumn<UsuarioDto,String> tbcPganadas = new TableColumn<>("Partidas Ganadas");
            tbcPganadas.setPrefWidth(130);
            tbcPganadas.setCellValueFactory(cd -> cd.getValue().partidasganadas);
            
            tbvResultados.getColumns().add(tbcId);
            tbvResultados.getColumns().add(tbcNombre);
            tbvResultados.getColumns().add(tbcPjugadas);
            tbvResultados.getColumns().add(tbcPganadas);
            tbvResultados.refresh();
            
            UsuarioService service = new UsuarioService();
            Respuesta respuesta = service.getUsuarios();

            if (respuesta.getEstado()) {
                usuarios.clear();
                usuarios.addAll((List<UsuarioDto>) respuesta.getResultado("Usuario"));
                tbvResultados.setItems(usuarios);
                tbvResultados.refresh();
            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar usuarios", getStage(), respuesta.getMensaje());
            }
            
        }catch(Exception ex){
            Logger.getLogger(BusquedaViewController.class.getName()).log(Level.SEVERE,"Error consultando los usuario", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Consultar usuario", getStage(), "Ocurrio un error consultando los usuarios");
        }
    }
    
}
