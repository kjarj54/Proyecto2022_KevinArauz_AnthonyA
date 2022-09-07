/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.proyecto1.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.QueryHint;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 *
 * @author ANTHONY
 */
@Entity
@Table(name = "PRO_PALABRA", schema = "UNA")
@NamedQueries({
    @NamedQuery(name = "Palabra.findAll", query = "SELECT p FROM Palabra p"),
    @NamedQuery(name = "Palabra.findByPalId", query = "SELECT p FROM Palabra p WHERE p.id = :id"),
    @NamedQuery(name = "Palabra.findByPalabra", query = "SELECT p FROM Palabra p WHERE UPPER(p.palabra) = :palabra", hints = @QueryHint(name = "eclipselink.refresh", value = "true")),})
    //@NamedQuery(name = "Palabra.findByPalVersion", query = "SELECT p FROM Palabra p WHERE p.palVersion = :palVersion")})
public class Palabra implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @SequenceGenerator(name = "PRO_PALABRA_PAL_ID_GENERATOR", sequenceName = "una.PRO_PALABRA_SEQ01", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRO_PALABRA_PAL_ID_GENERATOR")
    @Basic(optional = false)
    @Column(name = "PAL_ID")
    private Long id;
    @Basic(optional = false)
    @Column(name = "PAL_PALABRA")
    private String palabra;
    @Version
    @Basic(optional = false)
    @Column(name = "PAL_VERSION")
    private Long version;

    public Palabra() {
    }

    public Palabra(Long palId) {
        this.id = palId;
    }

    public Palabra(Long palId, String palPalabra) {
        this.id = palId;
        this.palabra = palPalabra;
    }
    
    public Palabra(PalabraDto palabraDto) {
        this.id = palabraDto.getId();
        actualizarPalabra(palabraDto);
    }     
    
    public void actualizarPalabra(PalabraDto palabraDto){
        this.palabra = palabraDto.getPalabra();
    }

    public Long getPalId() {
        return id;
    }

    public void setPalId(Long palId) {
        this.id = palId;
    }

    public String getPalPalabra() {
        return palabra;
    }

    public void setPalPalabra(String palPalabra) {
        this.palabra = palPalabra;
    }

    public Long getPalVersion() {
        return version;
    }

    public void setPalVersion(Long palVersion) {
        this.version = palVersion;
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
        if (!(object instanceof Palabra)) {
            return false;
        }
        Palabra other = (Palabra) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.proyecto1.model.Palabra[ palId=" + id + " ]";
    }
    
}
