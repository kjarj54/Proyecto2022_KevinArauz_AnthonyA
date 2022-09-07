/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.proyecto1.controller;

import com.jfoenix.controls.JFXButton;
import cr.ac.una.proyecto1.model.Letra;
import cr.ac.una.proyecto1.model.UsuarioDto;
import cr.ac.una.proyecto1.util.CeldaPos;
import cr.ac.una.proyecto1.model.PalabraDto;
import cr.ac.una.proyecto1.service.PalabraService;
import cr.ac.una.proyecto1.util.AppContext;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import cr.ac.una.proyecto1.util.FlowController;
import cr.ac.una.proyecto1.util.Mensaje;
import cr.ac.una.proyecto1.util.Respuesta;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Paint;
import javafx.scene.text.TextAlignment;

/**
 * FXML Controller class
 *                              
 * @author ANTHONY
 */
public class JuegoViewController extends Controller implements Initializable {

    @FXML
    private AnchorPane rootJuego;
    @FXML
    private GridPane gpTabla;
    @FXML
    private GridPane gpBaraja;
    @FXML
    private JFXButton btnPasar;
    @FXML
    private JFXButton btnJugar;
    @FXML
    private JFXButton btnGuardarYsalir;
    @FXML
    private JFXButton btnAyuda;
    @FXML
    private JFXButton btnSalir;
    @FXML
    private Label lbPuntaje1;
    @FXML
    private Label lbPuntaje2;
    @FXML
    private Label lbPuntaje3;
    @FXML
    private Label lbPuntaje4;
    @FXML
    private Label lbJugador1;
    @FXML
    private Label lbJugador2;
    @FXML
    private Label lbJugador3;
    @FXML
    private Label lbJugador4;
    @FXML
    private Label lbTurno;
    
    private double x ,y;
    int coll, fill, cont = 1;
    int jj;
    Integer colIndex;
    Integer rowIndex;
    GridPane target;
    private Timer timer = new Timer();
    private String tiempos = (String)AppContext.getInstance().get("Timer");
    private int counter = 60 * Integer.parseInt(tiempos); 
    private Label timerLbl = new Label("");
    private int seconds, minutes;
    private final ObservableList<String>Rutas=FXCollections.observableArrayList();
    List<String> rutas = new ArrayList<>();
    List<Letra> letras = new ArrayList<>();       //Baraja de letras
    List<String> Palabras = new ArrayList<>();    // Lista de palabras ya usadas
    List<UsuarioDto> Jugadores = new ArrayList<>();   // Lista de jugadores
    List<CeldaPos> celdas = new ArrayList<>();   // letras puestas por el usuario
    List<CeldaPos> celdasTabla = new ArrayList<>();    // letras ya fijas en la tabla
    List<Letra> BarajaLetras = new ArrayList<>();     // Baraja de letras general
    List<Letra> BarajaLetras1 = new ArrayList<>();    //Baraja de letras de los usuarios
    List<Letra> BarajaLetras2 = new ArrayList<>();
    List<Letra> BarajaLetras3 = new ArrayList<>();
    List<Letra> BarajaLetras4 = new ArrayList<>();
    
    PalabraDto palabraDto;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) { // Se obtiene la lista de jugadores insertados en la pantalla anterior y la dificultad seleccionada
                                                         // y se llamana los metodos para crear la tabla y las barajas de los jugadores
        System.setProperty("prism.text", "t2k");
        System.out.println(tiempos.toString());
        timerMetodo();
        NuevoJuegoViewController busquedaController = (NuevoJuegoViewController) FlowController.getInstance().getController("NuevoJuegoView");
        String dificultad = (String) busquedaController.getResultado();
        Jugadores = (List<UsuarioDto>) busquedaController.getJugadoress();
        lbTurno.setText(Jugadores.get(0).getNombreUsu());
        if (dificultad != null) {
            MedidaTablas(dificultad);
        }
        if (Jugadores != null) {
            for(int i = 0; i < Jugadores.size(); i++) {
                if(Jugadores.get(i).getNombreUsu() != null && lbJugador1.getText().isEmpty()){
                    lbJugador1.setText(Jugadores.get(i).getNombreUsu());
                    lbPuntaje1.setText("0");                    
                } else if(Jugadores.get(i).getNombreUsu() != null && lbJugador2.getText().isEmpty()){
                    lbJugador2.setText(Jugadores.get(i).getNombreUsu());
                    lbPuntaje2.setText("0");
                } else if(Jugadores.get(i).getNombreUsu() != null && lbJugador3.getText().isEmpty()){
                    lbJugador3.setText(Jugadores.get(i).getNombreUsu());
                    lbPuntaje3.setText("0");
                } else if(Jugadores.get(i).getNombreUsu() != null && lbJugador4.getText().isEmpty()){
                    lbJugador4.setText(Jugadores.get(i).getNombreUsu());
                    lbPuntaje4.setText("0");    
                }
            }
        }
        CrearTabla();
        CrearBarajaDeCartas();
        CrearBaraja();
        for(int i = 1; i < Jugadores.size(); i++) {
            for(int j = 0; j < 7; j++) {
                RellenarBarajaJugadorN(Integer.toString(i+1));
            }
        }
    }    

    @Override
    public void initialize() {
    }
    
    public void timerMetodo(){     // El cronometro de la partida
        //Timer
        timerLbl.setTextFill(Paint.valueOf("RED"));
        timerLbl.setStyle("-fx-font-size: 4em;");
        timerLbl.setLayoutX(700);
        timerLbl.setLayoutY(545);
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if(counter > 0){
                        counter--;
                        seconds = counter % 60;
                        minutes = counter / 60;
                        if (seconds < 10 && minutes < 10) {
                            timerLbl.setText("0" + minutes + ":0" + seconds);
                        } else if (minutes < 10) {
                            timerLbl.setText("0" + minutes + ":" + seconds);
                        } else {
                            timerLbl.setText(minutes + ":" + seconds);
                        }
                        
                    }
                });
                
                if(counter == 0){
                    System.out.println("Entro");
                    timer.cancel();    
                }
            }
            
        },0,1000);
        rootJuego.getChildren().add(timerLbl);    
    }
    
    public void MedidaTablas(String Dificultad) {   // Se escoge la medida de las tablas por medio de la dificultad obtenida
        if(Dificultad == "A") {
            coll = 15;
            fill = 15;
        } else {
            coll = 11;
            fill = 11;
        }
    }
    
    public void CrearTabla() {   // Se crea la tabla dependiendo de la dificultad seleccionada
        for (int i = 0; i < fill; i++) {
            for (int j = 0; j < coll; j++) {
                Label label = new Label();
                label.setText("");
                label.setTextAlignment(TextAlignment.CENTER);
                label.setPrefHeight(45);
                label.setPrefWidth(45);
                gpTabla.add(label, i, j);
            }
        }
        if (coll == 11) {
            for (int i = 0; i < fill; i+=5) {
                for (int j = 0; j < coll; j+=5) {
                    if(i == 5 && j == 5) {
                        Label label = new Label();
                        label.setText("");
                        label.setTextFill(Paint.valueOf("White"));
                        label.setTextAlignment(TextAlignment.CENTER);
                        label.setPrefHeight(45);
                        label.setPrefWidth(45);
                        gpTabla.add(label, i, j);
                    } else {
                        Label label = new Label();
                        label.setTextFill(Paint.valueOf("White"));
                        label.setStyle("-fx-background-color: #f44336;");
                        label.setTextAlignment(TextAlignment.CENTER);
                        label.setText("");
                        label.setPrefHeight(45);
                        label.setPrefWidth(45);
                        gpTabla.add(label, i, j);
                    }
                }
            }
        } else if (coll == 15) {
            for (int i = 0; i < fill; i+=7) {
                for (int j = 0; j < coll; j+=7) {
                    if(i == 7 && j == 7) {
                        Label label = new Label();
                        label.setTextFill(Paint.valueOf("White"));
                        label.setTextAlignment(TextAlignment.CENTER);
                        label.setText("");
                        label.setPrefHeight(45);
                        label.setPrefWidth(45);
                        gpTabla.add(label, i, j);
                    } else {
                        Label label = new Label();
                        label.setTextFill(Paint.valueOf("White"));
                        label.setStyle("-fx-background-color: #f44336;");
                        label.setTextAlignment(TextAlignment.CENTER);
                        label.setText("");
                        label.setPrefHeight(45);
                        label.setPrefWidth(45);
                        gpTabla.add(label, i, j);
                    }
                }
            }
        } 
    }
    
    public void CrearBaraja() {   // Crea la baraja inicial del primer jugador        
        for(int i = 0; i<7; i++) {
            Label label = new Label();
            label.setText(RellenarBarajaJugadorN("1"));
            label.setTextFill(Paint.valueOf("White"));
            label.setTextAlignment(TextAlignment.CENTER);
            label.getStyleClass().add("cellGrid");
            label.setPrefHeight(45);
            label.setPrefWidth(45);
            
            CrearMetodosB(label);
            
            gpBaraja.add(label, i, 0);
        }
    }
    
    public void CrearMetodosB (Label label) {
        target = gpTabla;
        
        label.setOnDragDetected(new EventHandler<MouseEvent>() {
        public void handle(MouseEvent event) {
            Node clickedNode = event.getPickResult().getIntersectedNode();
            if (clickedNode != gpTabla) {
                //click on descendant node
                colIndex = GridPane.getColumnIndex(clickedNode);
                rowIndex = GridPane.getRowIndex(clickedNode);
           }

            //Drag was detected, start drap-and-drop gesture
            //Allow any transfer node
            Dragboard db = label.startDragAndDrop(TransferMode.ANY);

            //Put ImageView on dragboard
            ClipboardContent cbContent = new ClipboardContent();
            cbContent.putString(label.getText());
            //cbContent.put(DataFormat.)
            db.setContent(cbContent);
            label.setVisible(false);
            event.consume();
            }
        });

        label.setOnDragDone(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                //the drag and drop gesture has ended
                //if the data was successfully moved, clear it
                if(event.getTransferMode() == TransferMode.MOVE){
                    label.setVisible(true);
                    label.setText("");
                }
                label.setVisible(true);
                event.consume();
            }
        });

        //Drag over event handler is used for the receiving node to allow movement
        target.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                //data is dragged over to target
                //accept it only if it is not dragged from the same node
                //and if it has image data
                if(event.getGestureSource() != target && event.getDragboard().hasString()){
                    //allow for moving
                    event.acceptTransferModes(TransferMode.MOVE);
                }
                event.consume();
            }
        });

        //Drag entered changes the appearance of the receiving node to indicate to the player that they can place there
        target.setOnDragEntered(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                //The drag-and-drop gesture entered the target
                //show the user that it is an actual gesture target
                if(event.getGestureSource() != target && event.getDragboard().hasString()){
                    label.setVisible(false);
                    target.setOpacity(0.7);
                    System.out.println("Drag entered");
                }
                event.consume();
            }
        });

        //Drag exited reverts the appearance of the receiving node when the mouse is outside of the node
        target.setOnDragExited(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                //mouse moved away, remove graphical cues
                label.setVisible(true);
                target.setOpacity(1);

                event.consume();
            }
        });

        //Drag dropped draws the image to the receiving node
        target.setOnDragDropped(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                //Data dropped
                //If there is an image on the dragboard, read it and use it
                Dragboard db = event.getDragboard();
                boolean success = false;
                Node node = event.getPickResult().getIntersectedNode();
                if(node != target && db.hasString()){

                    Integer cIndex = GridPane.getColumnIndex(node);
                    Integer rIndex = GridPane.getRowIndex(node);
                    int x = cIndex == null ? 0 : cIndex;
                    int y = rIndex == null ? 0 : rIndex;
                    //target.setText(db.getImage()); --- must be changed to target.add(source, col, row)
                    //target.add(source, 5, 5, 1, 1);
                    //Places at 0,0 - will need to take coordinates once that is implemented

                    Label label = new Label();
                    label.setText(db.getString());
                    label.setTextFill(Paint.valueOf("White"));
                    label.setTextAlignment(TextAlignment.CENTER);
                    label.setPrefHeight(45);
                    label.setPrefWidth(45);
                    CrearMetodos(label);
                    celdas.add(new CeldaPos(db.getString(), AddFormarPalabra(label.getText(), "1"), x, y));
                    System.out.println(letras.size());


                    // TODO: set image size; use correct column/row span
                    gpTabla.add(label, x, y, 1, 1);
                    success = true;
                }
                //let the source know whether the image was successfully transferred and used
                event.setDropCompleted(success);

                event.consume();
            }
        });
    }
    
    public void CrearMetodos(Label label) {
        target = gpBaraja;
        
        label.setOnDragDetected(new EventHandler<MouseEvent>() {
        public void handle(MouseEvent event) {
            Node clickedNode = event.getPickResult().getIntersectedNode();
            if (clickedNode != gpTabla) {
                //click on descendant node
                colIndex = GridPane.getColumnIndex(clickedNode);
                rowIndex = GridPane.getRowIndex(clickedNode);
           }

            //Drag was detected, start drap-and-drop gesture
            //Allow any transfer node
            Dragboard db = label.startDragAndDrop(TransferMode.ANY);

            //Put ImageView on dragboard
            ClipboardContent cbContent = new ClipboardContent();
            cbContent.putString(label.getText());
            //cbContent.put(DataFormat.)
            db.setContent(cbContent);
            label.setVisible(false);
            event.consume();
            }
        });

        label.setOnDragDone(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                //the drag and drop gesture has ended
                //if the data was successfully moved, clear it
                if(event.getTransferMode() == TransferMode.MOVE){
                    label.setVisible(true);
                    label.setText("");
                }
                label.setVisible(true);
                event.consume();
            }
        });

        //Drag over event handler is used for the receiving node to allow movement
        target.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                //data is dragged over to target
                //accept it only if it is not dragged from the same node
                //and if it has image data
                if(event.getGestureSource() != target && event.getDragboard().hasString()){
                    //allow for moving
                    event.acceptTransferModes(TransferMode.MOVE);
                }
                event.consume();
            }
        });

        //Drag entered changes the appearance of the receiving node to indicate to the player that they can place there
        target.setOnDragEntered(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                //The drag-and-drop gesture entered the target
                //show the user that it is an actual gesture target
                if(event.getGestureSource() != target && event.getDragboard().hasString()){
                    label.setVisible(false);
                    target.setOpacity(0.7);
                    System.out.println("Drag entered");
                }
                event.consume();
            }
        });

        //Drag exited reverts the appearance of the receiving node when the mouse is outside of the node
        target.setOnDragExited(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                //mouse moved away, remove graphical cues
                label.setVisible(true);
                target.setOpacity(1);

                event.consume();
            }
        });

        //Drag dropped draws the image to the receiving node
        target.setOnDragDropped(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                //Data dropped
                //If there is an image on the dragboard, read it and use it
                Dragboard db = event.getDragboard();
                boolean success = false;
                Node node = event.getPickResult().getIntersectedNode();
                if(node != target && db.hasString()){

                    Integer cIndex = GridPane.getColumnIndex(node);
                    Integer rIndex = GridPane.getRowIndex(node);
                    int x = cIndex == null ? 0 : cIndex;
                    int y = rIndex == null ? 0 : rIndex;
                    //target.setText(db.getImage()); --- must be changed to target.add(source, col, row)
                    //target.add(source, 5, 5, 1, 1);
                    //Places at 0,0 - will need to take coordinates once that is implemented

                    Label label = new Label();
                    label.setText(db.getString());
                    label.setTextFill(Paint.valueOf("White"));
                    label.getStyleClass().add("cellGrid");
                    label.setTextAlignment(TextAlignment.CENTER);
                    label.setPrefHeight(50);
                    label.setPrefWidth(50);
                    CrearMetodosB(label);
                    for(int i = 0; i < celdas.size(); i++) {
                        if (celdas.get(i).getPosx() == colIndex && celdas.get(i).getPosy()== rowIndex) {
                            celdas.remove(i);
                        }
                    }
                    DeleteFormarPalabra(label.getText());
                    System.out.println(letras.size());


                    // TODO: set image size; use correct column/row span
                    gpBaraja.add(label, x, y, 1, 1);
                    success = true;
                }
                //let the source know whether the image was successfully transferred and used
                event.setDropCompleted(success);

                event.consume();
            }
        });
    }
    
    public void TurnoDe (UsuarioDto usuDto, String juga) {  // Muestra la baraja del jugador con el turno
        
        lbTurno.setText(usuDto.getNombreUsu());
        
        gpBaraja.getChildren().clear();

        for(int i = 0; i<7; i++) {
            Label label = new Label();
            if ("1".equals(juga)) {
                label.setText(RellenarPasaTurno("1", i));
            }
            if ("2".equals(juga)) {
                label.setText(RellenarPasaTurno("2", i));
            }
            if ("3".equals(juga)) {
                label.setText(RellenarPasaTurno("3", i));
            }
            if ("4".equals(juga)) {
                label.setText(RellenarPasaTurno("4", i));
            }
            label.setTextFill(Paint.valueOf("White"));
            label.getStyleClass().add("cellGrid");
            label.setTextAlignment(TextAlignment.CENTER);
            label.setPrefHeight(45);
            label.setPrefWidth(45);
            
            target = gpTabla;
        
            label.setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                Node clickedNode = event.getPickResult().getIntersectedNode();
                if (clickedNode != gpTabla) {
                    //click on descendant node
                    colIndex = GridPane.getColumnIndex(clickedNode);
                    rowIndex = GridPane.getRowIndex(clickedNode);
                }

                //Drag was detected, start drap-and-drop gesture
                //Allow any transfer node
                Dragboard db = label.startDragAndDrop(TransferMode.ANY);

                //Put ImageView on dragboard
                ClipboardContent cbContent = new ClipboardContent();
                cbContent.putString(label.getText());
                //cbContent.put(DataFormat.)
                db.setContent(cbContent);
                label.setVisible(false);
                event.consume();
                }
            });

            label.setOnDragDone(new EventHandler<DragEvent>() {
                public void handle(DragEvent event) {
                    //the drag and drop gesture has ended
                    //if the data was successfully moved, clear it
                    if(event.getTransferMode() == TransferMode.MOVE){
                        label.setVisible(true);
                        label.setText("");
                    }
                    label.setVisible(true);
                    event.consume();
                }
            });

            //Drag over event handler is used for the receiving node to allow movement
            target.setOnDragOver(new EventHandler<DragEvent>() {
                public void handle(DragEvent event) {
                    //data is dragged over to target
                    //accept it only if it is not dragged from the same node
                    //and if it has image data
                    if(event.getGestureSource() != target && event.getDragboard().hasString()){
                        //allow for moving
                        event.acceptTransferModes(TransferMode.MOVE);
                    }
                    event.consume();
                }
            });

            //Drag entered changes the appearance of the receiving node to indicate to the player that they can place there
            target.setOnDragEntered(new EventHandler<DragEvent>() {
                public void handle(DragEvent event) {
                    //The drag-and-drop gesture entered the target
                    //show the user that it is an actual gesture target
                    if(event.getGestureSource() != target && event.getDragboard().hasString()){
                        label.setVisible(false);
                        target.setOpacity(0.7);
                        System.out.println("Drag entered");
                    }
                    event.consume();
                }
            });

            //Drag exited reverts the appearance of the receiving node when the mouse is outside of the node
            target.setOnDragExited(new EventHandler<DragEvent>() {
                public void handle(DragEvent event) {
                    //mouse moved away, remove graphical cues
                    label.setVisible(true);
                    target.setOpacity(1);

                    event.consume();
                }
            });

            //Drag dropped draws the image to the receiving node
            target.setOnDragDropped(new EventHandler<DragEvent>() {
                public void handle(DragEvent event) {
                    //Data dropped
                    //If there is an image on the dragboard, read it and use it
                    Dragboard db = event.getDragboard();
                    boolean success = false;
                    Node node = event.getPickResult().getIntersectedNode();
                    if(node != target && db.hasString()){

                        Integer cIndex = GridPane.getColumnIndex(node);
                        Integer rIndex = GridPane.getRowIndex(node);
                        int x = cIndex == null ? 0 : cIndex;
                        int y = rIndex == null ? 0 : rIndex;
                        //target.setText(db.getImage()); --- must be changed to target.add(source, col, row)
                        //target.add(source, 5, 5, 1, 1);
                        //Places at 0,0 - will need to take coordinates once that is implemented

                        Label label = new Label();
                        label.setText(db.getString());
                        label.setTextFill(Paint.valueOf("White"));
                        label.setTextAlignment(TextAlignment.CENTER);
                        label.setPrefHeight(45);
                        label.setPrefWidth(45);
                        CrearMetodos(label);
                        celdas.add(new CeldaPos(db.getString(), AddFormarPalabra(label.getText(), juga), x, y));
                        System.out.println(letras.size());


                        // TODO: set image size; use correct column/row span
                        gpTabla.add(label, x, y, 1, 1);
                        success = true;
                    }
                    //let the source know whether the image was successfully transferred and used
                    event.setDropCompleted(success);

                    event.consume();
                }
            });
            gpBaraja.add(label, i, 0);
        }
    }
    
    boolean isPresent(String query, String s) { 
        String[] palabras = s.split("\\s+");
        for (String palabra : palabras) {
            if (query.contains(palabra)) {
                return true;
            }
        }
        return false;
    } 
    
    public void CrearBarajaDeCartas () { // Crea la baraja total de cartas y las desordena
        Rutas.addAll("A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A",
                "E", "E", "E", "E", "E", "E", "E", "E", "E", "E", "E", "E",
                "O", "O", "O", "O", "O", "O", "O", "O","O", 
                "I", "I", "I", "I", "I", "I", 
                "S", "S", "S", "S", "S", "S", 
                "N", "N", "N", "N", "N", 
                "L", "L",
                "L", "L", 
                "R", "R",
                "R", "R", "R", 
                "U", "U", "U", "U", "U",
                "T", "T", "T", "T",
                "D", "D", "D", "D", "D", 
                "G", "G", 
                "C", "C", "C", "C", 
                "B", "B", 
                "M", "M", 
                "P", "P", 
                "H", "H", 
                "F","V", "Y", "CH", "Q",
                "J", "LL", "Ñ", "RR",
                "X", "Z", "Espacio", "Espacio");
        int numero;
        for(int i = 0; i<100; i++) {
            numero = (int)(Math.random()*Rutas.size());
            crearClaseLetra(Rutas.get(numero));
            Rutas.remove(numero);            
        }
    }
    
    public String RellenarBaraja() {    // Rellena la baraja del primer jugador
        int numero = (int)(Math.random()*BarajaLetras.size());
        if(!BarajaLetras.isEmpty()) {
            String letra = BarajaLetras.get(numero).getLetLetra();
            BarajaLetras1.add(BarajaLetras.get(numero));
            BarajaLetras.remove(numero);
            return letra;
        }
        else {
            return null;
        }
    }
    
    public String RellenarPasaTurno(String n, int n1) {   // Para rellenar la baraja del jugador cuando pasa de turno
        if(!BarajaLetras1.isEmpty() && "1".equals(n)) {
            if (BarajaLetras1.get(n1) != null){
                String letra = BarajaLetras1.get(n1).getLetLetra();
                return letra;
            }else {
                return "";
            }
        } else if(!BarajaLetras2.isEmpty() && "2".equals(n)) {
            if (BarajaLetras2.get(n1) != null){
                String letra = BarajaLetras2.get(n1).getLetLetra();
                return letra;
            }else {
                return "";
            }
                
        } else if(!BarajaLetras3.isEmpty() && "3".equals(n)) {
            if (BarajaLetras3.get(n1) != null){
                String letra = BarajaLetras3.get(n1).getLetLetra();
                return letra;
            }else {
                return "";
            }
        } else if(!BarajaLetras4.isEmpty() && "4".equals(n)) {
            if (BarajaLetras4.get(n1) != null){
                String letra = BarajaLetras4.get(n1).getLetLetra();
                return letra;
            }else {
                return "";
            }
        }
        return "";
    }
    
    public String RellenarBarajaJugadorN(String n) { // Retorna una letra nueva para la baraja del usuario seleccionado
        int numero = (int)(Math.random()*BarajaLetras.size());
        if(!BarajaLetras.isEmpty() && "1".equals(n)) {
            String letra = BarajaLetras.get(numero).getLetLetra();
            BarajaLetras1.add(BarajaLetras.get(numero));
            BarajaLetras.remove(numero);
            return letra;
        } else if(!BarajaLetras.isEmpty() && "2".equals(n)) {
            String letra = BarajaLetras.get(numero).getLetLetra();
            BarajaLetras2.add(BarajaLetras.get(numero));
            BarajaLetras.remove(numero);
            return letra;
        } else if(!BarajaLetras.isEmpty() && "3".equals(n)) {
            String letra = BarajaLetras.get(numero).getLetLetra();
            BarajaLetras3.add(BarajaLetras.get(numero));
            BarajaLetras.remove(numero);
            return letra;
        } else if(!BarajaLetras.isEmpty() && "4".equals(n)) {
            String letra = BarajaLetras.get(numero).getLetLetra();
            BarajaLetras4.add(BarajaLetras.get(numero));
            BarajaLetras.remove(numero);
            return letra;
        } else {
            return null;
        }
    }
    
    public Long AddFormarPalabra(String letrai, String n) { // Retorna una letra del usuario seleccionado
        if(!BarajaLetras1.isEmpty() && "1".equals(n)) {
            for(int i = 0; i < BarajaLetras1.size(); i++) {
                if (BarajaLetras1.get(i).getLetLetra().equals(letrai) ) {
                    letras.add(BarajaLetras1.get(i));
                    return BarajaLetras1.get(i).getLetPuntos();
                }
            }
        } else if(!BarajaLetras2.isEmpty() && "2".equals(n)) {
            for(int i = 0; i < BarajaLetras2.size(); i++) {
                if (BarajaLetras2.get(i).getLetLetra().equals(letrai) ) {
                    letras.add(BarajaLetras2.get(i));
                    return BarajaLetras2.get(i).getLetPuntos();
                }
            }
        } else if(!BarajaLetras3.isEmpty() && "3".equals(n)) {
            for(int i = 0; i < BarajaLetras3.size(); i++) {
                if (BarajaLetras3.get(i).getLetLetra().equals(letrai) ) {
                    letras.add(BarajaLetras3.get(i));
                    return BarajaLetras3.get(i).getLetPuntos();
                }
            } 
        } else if(!BarajaLetras4.isEmpty() && "4".equals(n)) {
            for(int i = 0; i < BarajaLetras4.size(); i++) {
                if (BarajaLetras4.get(i).getLetLetra().equals(letrai) ) {
                    letras.add(BarajaLetras4.get(i));
                    return BarajaLetras4.get(i).getLetPuntos();
                }
            }  
        }
        return null;
    }
    
    public boolean DeleteFormarPalabra(String letrai) { // Retorna una letra del usuario seleccionado
        for(int i = 0; i < letras.size(); i++) {
            if (letras.get(i).getLetLetra().equals(letrai) ) {
                letras.remove(i);
                return true;
            }
        }
        return false;
    }
    
    public void crearClaseLetra(String ruta) {  // Crea la clase de la letra 
        Letra letra = new Letra();
        if ("A".equals(ruta))         //1 punto
            letra = new Letra(null, "A", ruta, 1L);
        if ("Espacio".equals(ruta)) 
            letra = new Letra(null, "ESPACIO", ruta, 1L);
        if ("E".equals(ruta)) 
            letra = new Letra(null, "E", ruta, 1L);
        if ("O".equals(ruta)) 
            letra = new Letra(null, "O", ruta, 1L);
        if ("I".equals(ruta)) 
            letra = new Letra(null, "I", ruta, 1L);
        if ("S".equals(ruta)) 
            letra = new Letra(null, "S", ruta, 1L);
        if ("N".equals(ruta)) 
            letra = new Letra(null, "N", ruta, 1L);
        
        if ("LL".equals(ruta)) 
            letra = new Letra(null, "LL", ruta, 8L); //8 puntos
        if ("L".equals(ruta)) 
            letra = new Letra(null, "L", ruta, 1L);
        if ("RR".equals(ruta)) 
            letra = new Letra(null, "RR", ruta, 8L); //8 puntos
        if ("R".equals(ruta)) 
            letra = new Letra(null, "R", ruta, 1L);
        
        if ("U".equals(ruta)) 
            letra = new Letra(null, "U", ruta, 1L);
        if ("T".equals(ruta)) 
            letra = new Letra(null, "T", ruta, 1L);
        //2 puntos
        if ("D".equals(ruta)) 
            letra = new Letra(null, "D", ruta, 2L);
        if ("G".equals(ruta)) 
            letra = new Letra(null, "G", ruta, 2L);
        //3 puntos
        if ("CH".equals(ruta)) 
            letra = new Letra(null, "CH", ruta, 5L); //5 puntos
        if ("C".equals(ruta)) 
            letra = new Letra(null, "C", ruta, 3L);
        if ("B".equals(ruta)) 
            letra = new Letra(null, "B", ruta, 3L);
        if ("M".equals(ruta)) 
            letra = new Letra(null, "M", ruta, 3L);
        if ("P".equals(ruta)) 
            letra = new Letra(null, "P", ruta, 3L);
        //4 puntos
        if ("H".equals(ruta)) 
            letra = new Letra(null, "H", ruta, 4L);
        if ("F".equals(ruta)) 
            letra = new Letra(null, "F", ruta, 4L);
        if ("V".equals(ruta)) 
            letra = new Letra(null, "V", ruta, 4L);
        if ("Y".equals(ruta)) 
            letra = new Letra(null, "Y", ruta, 4L);
        //5 puntos
        if ("Q".equals(ruta)) 
            letra = new Letra(null, "Q", ruta, 5L);
        //8 puntos
        if ("J".equals(ruta)) 
            letra = new Letra(null, "J", ruta, 8L);
        if ("Ñ".equals(ruta)) 
            letra = new Letra(null, "N", ruta, 8L);
        if ("X".equals(ruta)) 
            letra = new Letra(null, "X", ruta, 8L);
        //10 puntos
        if ("Z".equals(ruta)) 
            letra = new Letra(null, "Z", ruta, 10L);
        BarajaLetras.add(letra);
    }
    
    public List ComprobarPalabra() {    //Verifica que la palabra puesta en la tabla esté con el formato correcto
        List<Integer> celdasx = new ArrayList<>();
        List<Integer> celdasy = new ArrayList<>();
        List<String> palabras = new ArrayList<>();
        String Palabra = "";
        String pos = "";
        Boolean bandera;
        
        int posx = celdas.get(0).getPosx();
        pos = "vertical";
        for(int i = 0; i < celdas.size(); i++) {
            if(celdas.get(i).getPosx()!= posx) {
                pos = "no";
            }
        }
        
        if ("vertical".equals(pos)) {
            for(int i = 0; i < celdas.size(); i++) {
                celdasy.add(celdas.get(i).getPosy());
            }
            celdasy.sort(Comparator.naturalOrder());
            
            for(int i = 0; i < celdasy.size(); i++) {
                bandera = true;
                for(int j = 0; j < celdas.size(); j++) {
                    if (celdas.get(j).getPosy() == celdasy.get(i) && bandera == true) {
                        Palabra = Palabra + celdas.get(j).getLetra();
                        bandera = false;
                        System.out.println("entra");
                    }
                }
            }
            palabras.add(Palabra);

            Palabra = "";
            for(int i = celdasy.size()-1; i >= 0 ; i--) {
                bandera = true;
                for(int j = celdas.size()-1; j >= 0; j--) {
                    if (celdas.get(j).getPosy() == celdasy.get(i) && bandera == true) {
                        Palabra = Palabra + celdas.get(j).getLetra();
                        bandera = false;
                        System.out.println("entra2");
                    }
                }
            }
            palabras.add(Palabra);
            return palabras;
        }
        
        int posy = celdas.get(0).getPosy();
        pos = "horizontal";
        for(int i = 0; i < celdas.size(); i++) {
            if(celdas.get(i).getPosy()!= posy) {
                pos = "no";
            }
        }
        
        if ("horizontal".equals(pos)) {
            for(int i = 0; i < celdas.size(); i++) {
                celdasx.add(celdas.get(i).getPosx());
            }
            celdasy.sort(Comparator.naturalOrder());
            
            for(int i = 0; i < celdasx.size(); i++) {
                bandera = true;
                for(int j = 0; j < celdas.size(); j++) {
                    if (celdas.get(j).getPosx() == celdasx.get(i) && bandera == true) {
                        Palabra = Palabra + celdas.get(j).getLetra();
                        bandera = false;
                        System.out.println("entra");
                    }
                }
            }
            palabras.add(Palabra);

            Palabra = "";
            for(int i = celdasx.size()-1; i >= 0 ; i--) {
                bandera = true;
                for(int j = celdas.size()-1; j >= 0; j--) {
                    if (celdas.get(j).getPosx() == celdasx.get(i) && bandera == true) {
                        Palabra = Palabra + celdas.get(j).getLetra();
                        bandera = false;
                        System.out.println("entra2");
                    }
                }
            }
            palabras.add(Palabra);
            return palabras;
        }
        
        if ("no".equals(pos)) {
            return null;
        }
        return null;
    }
    
    private Boolean ProbarPalabra(String palabra1, String palabra2, String juga) {  // Prueba la palabra el la base de datos y da una respuesta
        PalabraService service = new PalabraService();
        Respuesta respuesta = service.getPalabra(palabra1);
        
        if (!respuesta.getEstado()) {
            respuesta = service.getPalabra(palabra2);
        }
        
        if (respuesta.getEstado()) {
            palabraDto = (PalabraDto) respuesta.getResultado("Palabra");
            Palabras.add(palabraDto.getPalabra().toUpperCase());

            int Puntaje = 0;
            for(CeldaPos celdapos : celdas){
                Puntaje += celdapos.getPuntos();
            }

            Boolean bandera;
            if ("1".equals(juga)){
                for(int i = 0; i < letras.size(); i++) {
                    bandera = true;
                    for(int j = 0; j < BarajaLetras1.size(); j++) {
                        if (letras.get(i).getLetLetra().equals(BarajaLetras1.get(j).getLetLetra()) && bandera == true) {
                            BarajaLetras1.remove(j);
                            bandera = false;
                        }
                    }
                }
                int number = Integer.parseInt(lbPuntaje1.getText());
                Puntaje += number;
                lbPuntaje1.setText(""+Puntaje);
            } else if("2".equals(juga)) {
                for(int i = 0; i < letras.size(); i++) {
                    bandera = true;
                    for(int j = 0; j < BarajaLetras2.size(); j++) {
                        if (letras.get(i).getLetLetra().equals(BarajaLetras2.get(j).getLetLetra()) && bandera == true) {
                            BarajaLetras2.remove(j);
                            bandera = false;
                        }
                    }
                }
                int number = Integer.parseInt(lbPuntaje2.getText());
                Puntaje += number;
                lbPuntaje2.setText(""+Puntaje);
            } else if("3".equals(juga)) {
                for(int i = 0; i < letras.size(); i++) {
                    bandera = true;
                    for(int j = 0; j < BarajaLetras3.size(); j++) {
                        if (letras.get(i).getLetLetra().equals(BarajaLetras3.get(j).getLetLetra()) && bandera == true) {
                            BarajaLetras3.remove(j);
                            bandera = false;
                        }
                    }
                }
                int number = Integer.parseInt(lbPuntaje3.getText());
                Puntaje += number;
                lbPuntaje3.setText(""+Puntaje);
            } else if("4".equals(juga)) {
                for(int i = 0; i < letras.size(); i++) {
                    bandera = true;
                    for(int j = 0; j < BarajaLetras4.size(); j++) {
                        if (letras.get(i).getLetLetra().equals(BarajaLetras4.get(j).getLetLetra()) && bandera == true) {
                            BarajaLetras4.remove(j);
                            bandera = false;
                        }
                    }
                }
                int number = Integer.parseInt(lbPuntaje4.getText());
                Puntaje += number;
                lbPuntaje4.setText(""+Puntaje);
            }
            letras.clear();

            for(CeldaPos celdapos : celdas) {
                celdasTabla.add(celdapos);
            }
            celdas.clear();

            if ("1".equals(juga)){
                int f = 7-BarajaLetras1.size();
                for(int i = 0; i < f; i++) {
                    RellenarBarajaJugadorN(juga);
                }
            } else if("2".equals(juga)) {
                int f = 7-BarajaLetras2.size();
                for(int i = 0; i < f; i++) {
                    RellenarBarajaJugadorN(juga);
                }
            } else if("3".equals(juga)) {
                int f = 7-BarajaLetras3.size();
                for(int i = 0; i < f; i++) {
                    RellenarBarajaJugadorN(juga);
                }
            } else if("4".equals(juga)) {
                int f = 7-BarajaLetras4.size();
                for(int i = 0; i < f; i++) {
                    RellenarBarajaJugadorN(juga);
                }
            }
            new Mensaje().showModal(Alert.AlertType.INFORMATION, "Comprobar Palabra", getStage(), "Palabra Correcta");
            return true;
        } else {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Comprobar Palabra", getStage(), respuesta.getMensaje());
            return false;
        }
    }

    @FXML
    private void onActionBtnPasar(ActionEvent event) {  // Pasa el turno del jugador
        if (celdas.isEmpty()) {
            letras.clear();
            celdas.clear();
            if (cont == Jugadores.size()) {
                cont = 0;
                String jug= Integer.toString(cont+1);
                TurnoDe(Jugadores.get(cont),jug);
            }
            else {
                String jug = Integer.toString(cont+1);
                TurnoDe(Jugadores.get(cont), jug);
            }
            cont++;
        } else {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error al pasar de turno", getStage(), "Devuelva las letras a la baraja");
        }
        
    }

    @FXML
    private void onActionBtnJugar(ActionEvent event) {  // Comprueba la palabra digitada
        if(!celdas.isEmpty()) {
            List<String> palabrasss = new ArrayList<>();
            palabrasss = ComprobarPalabra();
            String jug;
            Boolean respuestaa;
            Boolean bandera = true;

            for(String pala : Palabras) {
                if(pala.equals(palabrasss.get(0)) || pala.equals(palabrasss.get(1))) {
                    bandera = false;
                }
            }

            if (bandera) {
                if(palabrasss != null) {
                    if (cont == Jugadores.size()) {
                        cont = 0;
                        jug = Integer.toString(cont+1);
                        respuestaa = ProbarPalabra(palabrasss.get(0),palabrasss.get(1), Integer.toString(cont+2));
                    }
                    else {
                        jug = Integer.toString(cont+1);
                        respuestaa = ProbarPalabra(palabrasss.get(0),palabrasss.get(1), Integer.toString(cont));
                    }

                    if(!respuestaa) {
                        //new Mensaje().showModal(Alert.AlertType.ERROR, "Palabra no existe", getStage(), "No se encontró la palabra digitada");
                    } else {
                        TurnoDe(Jugadores.get(cont), jug);
                        cont++;
                    }
                } else {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Error al comprobar", getStage(), "Palabra mal puesta");
                }
            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Error al comprobar", getStage(), "Palabra repetida");
            }
        } else {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error al comprobar", getStage(), "No hay letras puestas");
        }
    }

    @FXML
    private void onActionBtnGuardarYsalir(ActionEvent event) {
    }

    @FXML
    private void onActionBtnAyuda(ActionEvent event) {
    }
    
    @FXML
    private void onActionSalir(ActionEvent event) {
        getStage().close();
    }
    
}