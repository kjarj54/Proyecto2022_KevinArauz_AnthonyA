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
public class LetraDto {
    public SimpleStringProperty id;
    public Long version;
    public Boolean modificado;
    public SimpleStringProperty dirimg;
    public SimpleStringProperty letra;
    public SimpleStringProperty puntos;

    public LetraDto() {
       this.modificado = false; 
       this.id = new SimpleStringProperty();
       this.dirimg = new SimpleStringProperty();
       this.letra = new SimpleStringProperty();
       this.puntos = new SimpleStringProperty();
    }
    
    public LetraDto(Letra letra) {
      this();
      this.id.set(letra.getLetId().toString());
      this.dirimg.set(letra.getLetDirimg());
      this.letra.set(letra.getLetLetra());
      this.puntos.set(letra.getLetPuntos().toString());
    }
    
    public Long getId() {
        if(id.get()!=null && !id.get().isEmpty()) 
            return Long.valueOf(id.get()); 
        else 
            return null; 
    }

    public void setId(Long letraId) {
        this.id.set(letraId.toString());
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

    public String getDirimg() {
        return dirimg.get();
    }

    public void setDirimg(String letraDirimg) {
        this.dirimg.set(letraDirimg);
    }

    public String getLetra() {
        return letra.get();
    }

    public void setLetra(String letLetra) {
        this.letra.set(letLetra);
    }

    public Long getPuntos() {
        if(puntos.get()!=null && !puntos.get().isEmpty()) 
            return Long.valueOf(puntos.get()); 
        else 
            return null; 
    }

    public void setPuntos(Long letrasPuntos) {
        this.puntos.set(letrasPuntos.toString());
    }
    
    
}
