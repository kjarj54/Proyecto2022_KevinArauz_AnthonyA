/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyecto1.model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author UNA-Audivisuales
 */
public class UsuarioDto {
    
    public SimpleStringProperty id;
    public SimpleStringProperty nombre;
    public SimpleStringProperty partidasganadas;
    public SimpleStringProperty partidasjugadas;
    public SimpleBooleanProperty estado;
    public Boolean modificado;   
    
    public UsuarioDto(){
        this.modificado = false;
        this.id = new SimpleStringProperty();
        this.nombre = new SimpleStringProperty();
        this.partidasjugadas = new SimpleStringProperty();
        this.partidasganadas = new SimpleStringProperty();
        this.estado = new SimpleBooleanProperty(true);
    }
    
    public UsuarioDto(Usuario usuario){
        this();
        this.id.set(usuario.getId().toString());
        this.nombre.set(usuario.getNombre());
        if (usuario.getPartidasjugadas() == null)
            this.partidasjugadas.set("0");
        else
            this.partidasjugadas.set(usuario.getPartidasjugadas().toString());
        if (usuario.getPartidasganadas() == null)
            this.partidasganadas.set("0");
        else
            this.partidasganadas.set(usuario.getPartidasganadas().toString());
        this.estado.setValue(usuario.getEstado().equalsIgnoreCase("A"));
    }

    public String getEstado() {
        return estado.getValue()?"A":"I";
    }

    public void setEstado(String usuEstado) {
        this.estado.setValue(usuEstado.equalsIgnoreCase("A"));
    }
    
    public Long getPartidasGanadas() {
        if(partidasganadas.get()!=null && !partidasganadas.get().isEmpty())
            return Long.valueOf(partidasganadas.get());
        else
            return 0L;
    }

    public void setPartidasGanadas(Long usupartidasGanadas) {
        this.partidasganadas.set(usupartidasGanadas.toString());
    }

    public Long getParidasJugadas() {
        if(partidasjugadas.get()!=null && !partidasjugadas.get().isEmpty())
            return Long.valueOf(partidasjugadas.get());
        else
            return 0L;
    }

    public void setParidasJugadas(Long usuparidasJugadas) {
        this.partidasjugadas.set(usuparidasJugadas.toString());
    }

    public Long getId() {
        if(id.get()!=null && !id.get().isEmpty())
            return Long.valueOf(id.get());
        else
            return null;
    }

    public void setId(Long usuId) {
        this.id.set(usuId.toString());
    }

    public String getNombreUsu() {
        return nombre.get();
    }

    public void setNombreUsu(String usuNombre) {
        this.nombre.set(usuNombre);
    }

    public Boolean getModificado() {
        return modificado;
    }

    public void setModificado(Boolean modificado) {
        this.modificado = modificado;
    }
    
    
}
