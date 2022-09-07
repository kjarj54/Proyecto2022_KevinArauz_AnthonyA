/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.proyecto1.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 *
 * @author ANTHONY
 */
@Entity
@Table(name = "PRO_PARTIDA", schema = "UNA")
@NamedQueries({
    @NamedQuery(name = "Partida.findAll", query = "SELECT p FROM Partida p"),
    @NamedQuery(name = "Partida.findByPartId", query = "SELECT p FROM Partida p WHERE p.id = :id"),
    /*@NamedQuery(name = "Partida.findByPartDificultad", query = "SELECT p FROM Partida p WHERE p.partDificultad = :partDificultad"),
    @NamedQuery(name = "Partida.findByPartVersion", query = "SELECT p FROM Partida p WHERE p.partVersion = :partVersion"),
    @NamedQuery(name = "Partida.findByPartTimer", query = "SELECT p FROM Partida p WHERE p.partTimer = :partTimer")*/})
public class Partida implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @SequenceGenerator(name = "PRO_PARTIDA_PART_ID_GENERATOR", sequenceName = "una.PRO_PARTIDA_SEQ01", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRO_PARTIDA_PART_ID_GENERATOR")
    @Basic(optional = false)
    @Column(name = "PART_ID")
    private Long id;
    @Basic(optional = false)
    @Column(name = "PART_DIFICULTAD")
    private String dificultad;
    @Version
    @Basic(optional = false)
    @Column(name = "PART_VERSION")
    private Long version;
    @Column(name = "PART_TIMER")
    private Long timer;
    @ManyToMany(mappedBy = "partidaList", fetch = FetchType.LAZY)
    private List<Usuario> usuarioList;
    @OneToMany(mappedBy = "partId", fetch = FetchType.LAZY)
    private List<Letra> letraList;

    public Partida() {
    }

    public Partida(Long tplaId) {
        this.id = tplaId;
    }

    public Partida(Long id, String dificultad, Long timer) {
        this.id = id;
        this.dificultad = dificultad;
        this.timer = timer;
    }
    
    public Partida(PartidaDto partidaDto) {
        this.id = partidaDto.getId();
        actualizarPartida(partidaDto);
    }     
    
    public void actualizarPartida(PartidaDto partidaDto){
        this.dificultad = partidaDto.getDificultad();
        this.timer = partidaDto.getTimer();
    }

    public Long getPartId() {
        return id;
    }

    public void setPartId(Long partId) {
        this.id = partId;
    }

    public String getPartDificultad() {
        return dificultad;
    }

    public void setPartDificultad(String partDificultad) {
        this.dificultad = partDificultad;
    }

    public Long getPartVersion() {
        return version;
    }

    public void setPartVersion(Long partVersion) {
        this.version = partVersion;
    }

    public Long getPartTimer() {
        return timer;
    }

    public void setPartTimer(Long partTimer) {
        this.timer = partTimer;
    }

    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    public List<Letra> getLetraList() {
        return letraList;
    }

    public void setLetraList(List<Letra> letraList) {
        this.letraList = letraList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Partida)) {
            return false;
        }
        Partida other = (Partida) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.proyecto1.model.Partida[ partId=" + id + " ]";
    }
    
}
