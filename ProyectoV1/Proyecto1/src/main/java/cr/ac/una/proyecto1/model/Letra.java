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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "PRO_LETRA", schema = "UNA")
@NamedQueries({
    @NamedQuery(name = "Letra.findAll", query = "SELECT l FROM Letra l"),
    @NamedQuery(name = "Letra.findByLetId", query = "SELECT l FROM Letra l WHERE l.id = :id"),
    /*@NamedQuery(name = "Letra.findByLetLetra", query = "SELECT l FROM Letra l WHERE l.letLetra = :letLetra"),
    @NamedQuery(name = "Letra.findByLetDirimg", query = "SELECT l FROM Letra l WHERE l.letDirimg = :letDirimg"),
    @NamedQuery(name = "Letra.findByLetPuntos", query = "SELECT l FROM Letra l WHERE l.letPuntos = :letPuntos"),
    @NamedQuery(name = "Letra.findByLetVersion", query = "SELECT l FROM Letra l WHERE l.letVersion = :letVersion")*/})
public class Letra implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @SequenceGenerator(name = "PRO_LETRA_LET_ID_GENERATOR", sequenceName = "una.PRO_LETRA_SEQ01", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRO_LETRA_LET_ID_GENERATOR")
    @Basic(optional = false)
    @Column(name = "LET_ID")
    private Long id;
    @Basic(optional = false)
    @Column(name = "LET_LETRA")
    private String letra;
    @Basic(optional = false)
    @Column(name = "LET_DIRIMG")
    private String dirImg;
    @Basic(optional = false)
    @Column(name = "LET_PUNTOS")
    private Long puntos;
    @Version
    @Basic(optional = false)
    @Column(name = "LET_VERSION")
    private Long version;
    @JoinColumn(name = "PART_ID", referencedColumnName = "PART_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Partida partId;
    @JoinColumn(name = "USU_ID", referencedColumnName = "USU_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario usuId;
    @OneToMany(mappedBy = "letId", fetch = FetchType.LAZY)
    private List<Celda> celdaList;

    public Letra() {
    }

    public Letra(Long letId) {
        this.id = letId;
    }

    public Letra(Long letId, String letLetra, String letDirimg, Long letPuntos) {
        this.id = letId;
        this.letra = letLetra;
        this.dirImg = letDirimg;
        this.puntos = letPuntos;
    }
    public Letra(LetraDto letraDto) {
        this.id = letraDto.getId();
        actualizarLetra(letraDto);
    }     
    
    public void actualizarLetra(LetraDto letraDto){
        this.letra = letraDto.getLetra();
        this.dirImg = letraDto.getDirimg();
        this.puntos = letraDto.getPuntos();
    }

    public Long getLetId() {
        return id;
    }

    public void setLetId(Long letId) {
        this.id = letId;
    }

    public String getLetLetra() {
        return letra;
    }

    public void setLetLetra(String letLetra) {
        this.letra = letLetra;
    }

    public String getLetDirimg() {
        return dirImg;
    }

    public void setLetDirimg(String letDirimg) {
        this.dirImg = letDirimg;
    }

    public Long getLetPuntos() {
        return puntos;
    }

    public void setLetPuntos(Long letPuntos) {
        this.puntos = letPuntos;
    }

    public Long getLetVersion() {
        return version;
    }

    public void setLetVersion(Long letVersion) {
        this.version = letVersion;
    }

    public Partida getPartId() {
        return partId;
    }

    public void setPartId(Partida partId) {
        this.partId = partId;
    }

    public Usuario getUsuId() {
        return usuId;
    }

    public void setUsuId(Usuario usuId) {
        this.usuId = usuId;
    }

    public List<Celda> getCeldaList() {
        return celdaList;
    }

    public void setCeldaList(List<Celda> celdaList) {
        this.celdaList = celdaList;
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
        if (!(object instanceof Letra)) {
            return false;
        }
        Letra other = (Letra) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.proyecto1.model.Letra[ letId=" + id + " ]";
    }
    
}
