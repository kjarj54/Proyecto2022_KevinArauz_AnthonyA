/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.proyecto1.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author ANTHONY
 */
public class CeldaDto {
    
    public SimpleStringProperty id;
    public Long version;
    public Boolean modificado;
    public SimpleStringProperty posX;
    public SimpleStringProperty posY;
    ObservableList<LetraDto> letras;
    
    public CeldaDto() {
        this.id = new SimpleStringProperty();
        this.posX = new SimpleStringProperty();
        this.posY = new SimpleStringProperty();
        this.modificado = false;
        letras = FXCollections.observableArrayList();
    }
    
    public CeldaDto(Celda celda) {
        this();
        this.id.set(celda.getCelId().toString());
        this.posX.set(celda.getCelPosx().toString());
        this.posY.set(celda.getCelPosy().toString());
        this.version = celda.getCelVersion();
    }
    
    public Long getId() {
        if(id.get()!=null && !id.get().isEmpty()){
            return Long.valueOf(id.get());
        }else{
            return null;
        }
    }

    public void setId(Long celId) {
        this.id.set(celId.toString());
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

    public Long getPosX() {
        if(posX.get()!=null && !posX.get().isEmpty()) 
            return Long.valueOf(posX.get()); 
        else 
            return null; 
    }

    public void setPosX(Long celPosX) {
        this.posX.set(celPosX.toString());
    }

    public Long getPosY() {
       if(posY.get()!=null && !posY.get().isEmpty()) 
            return Long.valueOf(posY.get()); 
        else 
            return null; 
    }

    public void setPosY(Long celPosY) {
        this.posY.set(celPosY.toString());
    }  

    
    
}
