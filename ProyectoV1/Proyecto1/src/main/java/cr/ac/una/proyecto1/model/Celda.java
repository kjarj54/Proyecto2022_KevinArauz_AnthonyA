/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.proyecto1.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 *
 * @author ANTHONY
 */
@Entity
@Table(name = "PRO_CELDA", schema = "UNA")
@NamedQueries({
    @NamedQuery(name = "Celda.findAll", query = "SELECT c FROM Celda c"),
    @NamedQuery(name = "Celda.findByCelId", query = "SELECT c FROM Celda c WHERE c.id = :id"),
    /*@NamedQuery(name = "Celda.findByCelPosx", query = "SELECT c FROM Celda c WHERE c.celPosx = :celPosx"),
    @NamedQuery(name = "Celda.findByCelPosy", query = "SELECT c FROM Celda c WHERE c.celPosy = :celPosy"),
    @NamedQuery(name = "Celda.findByCelVersion", query = "SELECT c FROM Celda c WHERE c.celVersion = :celVersion")*/})
public class Celda implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @SequenceGenerator(name = "PRO_CELDA_CEL_ID_GENERATOR", sequenceName = "una.PRO_CELDA_SEQ01", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRO_CELDA_CEL_ID_GENERATOR")
    @Basic(optional = false)
    @Column(name = "CEL_ID")
    private Long id;
    @Basic(optional = false)
    @Column(name = "CEL_POSX")
    private Long posx;
    @Basic(optional = false)
    @Column(name = "CEL_POSY")
    private Long posy;
    @Version
    @Basic(optional = false)
    @Column(name = "CEL_VERSION")
    private Long version;
    @JoinColumn(name = "LET_ID", referencedColumnName = "LET_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Letra letId;

    public Celda() {
    }

    public Celda(Long celId) {
        this.id = celId;
    }

    public Celda(Long celId, Long celPosx, Long celPosy) {
        this.id = celId;
        this.posx = celPosx;
        this.posy = celPosy;
    }
    
    public Celda(CeldaDto celdaDto) {
        this.id = celdaDto.getId();
        actualizarCelda(celdaDto);
    }     
    
    public void actualizarCelda(CeldaDto celdaDto){
        this.posx = celdaDto.getPosX();
        this.posy = celdaDto.getPosY();
    }

    public Long getCelId() {
        return id;
    }

    public void setCelId(Long celId) {
        this.id = celId;
    }

    public Long getCelPosx() {
        return posx;
    }

    public void setCelPosx(Long celPosx) {
        this.posx = celPosx;
    }

    public Long getCelPosy() {
        return posy;
    }

    public void setCelPosy(Long celPosy) {
        this.posy = celPosy;
    }

    public Long getCelVersion() {
        return version;
    }

    public void setCelVersion(Long celVersion) {
        this.version = celVersion;
    }

    public Letra getLetId() {
        return letId;
    }

    public void setLetId(Letra letId) {
        this.letId = letId;
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
        if (!(object instanceof Celda)) {
            return false;
        }
        Celda other = (Celda) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.proyecto1.model.Celda[ celId=" + id + " ]";
    }
    
}
