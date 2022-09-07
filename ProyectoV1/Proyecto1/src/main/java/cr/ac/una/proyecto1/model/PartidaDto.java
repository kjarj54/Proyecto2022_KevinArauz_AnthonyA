/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyecto1.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author UNA-Audivisuales
 */
public class PartidaDto {
    public SimpleStringProperty id;
    public ObjectProperty<String> dificultad;
    public SimpleStringProperty timer;
    public Long version;
    public Boolean modificado;
    ObservableList<UsuarioDto> usuarios;
    ObservableList<LetraDto> letras;
    ObservableList<CeldaDto> celdas;

    public PartidaDto() {
        this.modificado = false; 
        this.id = new SimpleStringProperty();
        this.dificultad = new SimpleObjectProperty("A");
        this.timer = new SimpleStringProperty();
        usuarios = FXCollections.observableArrayList();
        celdas = FXCollections.observableArrayList();
        letras = FXCollections.observableArrayList();
    }

    
    
    public PartidaDto(Partida partida) {
       this();
       this.id.set(partida.getPartId().toString());
       this.dificultad.set(partida.getPartDificultad());
       this.timer.set(partida.getPartTimer().toString());
       this.version = partida.getPartVersion();
    }

    public Long getTimer() {
        if(timer.get()!=null && !timer.get().isEmpty()) 
            return Long.valueOf(timer.get()); 
        else 
            return null; 
    }

    public void setTimer(Long partTimer) {
        this.timer.set(partTimer.toString()); 
    }
    
    public String getDificultad() {
        return dificultad.get();
    }

    public void setDificultad(String partDificultad) {
        this.dificultad.set(partDificultad);
    }

    public Long getId() {
        if(id.get()!=null && !id.get().isEmpty())
            return Long.valueOf(id.get());
        else
            return null;
    }

    public void setId(Long partId) {
        this.id.set(partId.toString());
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Boolean getModificado() {
        return modificado;
    }

    public void setModificado(Boolean modificado) {
        this.modificado = modificado;
    }
    
}
