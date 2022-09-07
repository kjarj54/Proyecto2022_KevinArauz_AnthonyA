/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyecto1.model;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author kevin
 */
public class PalabraDto {
    
    public SimpleStringProperty id;
    public SimpleStringProperty palabra;
    public Long version;
    public Boolean modificado;

    public PalabraDto() {
       this.modificado = false; 
       this.id = new SimpleStringProperty();
       this.palabra = new SimpleStringProperty();  
    }
    
    public PalabraDto(Palabra palabra) {
       this();
       this.id.set(palabra.getPalId().toString());
       this.palabra.set(palabra.getPalPalabra());
       this.version = palabra.getPalVersion();
    }

    public Long getId() {
        if(id.get()!=null && !id.get().isEmpty()){
            return Long.valueOf(id.get());
        }else{
            return null;
        }
    }

    public void setId(Long palId) {
        this.id.set(palId.toString());
    }

    public Long getVersion() {
        return version;
    }

    public String getPalabra() {
        return palabra.get();
    }

    public void setPalabra(String PalPalabra) {
        this.palabra.set(PalPalabra);
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
