/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.proyecto1.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
@Table(name = "PRO_USUARIO", schema = "UNA")
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u"),
    @NamedQuery(name = "Usuario.findById", query = "SELECT u FROM Usuario u WHERE u.id = :id"),
    /*@NamedQuery(name = "Usuario.findByUsuNombre", query = "SELECT u FROM Usuario u WHERE u.usuNombre = :usuNombre"),
    @NamedQuery(name = "Usuario.findByUsuPartidasjugadas", query = "SELECT u FROM Usuario u WHERE u.usuPartidasjugadas = :usuPartidasjugadas"),
    @NamedQuery(name = "Usuario.findByUsuPartidasganadas", query = "SELECT u FROM Usuario u WHERE u.usuPartidasganadas = :usuPartidasganadas"),
    @NamedQuery(name = "Usuario.findByUsuEstado", query = "SELECT u FROM Usuario u WHERE u.usuEstado = :usuEstado"),
    @NamedQuery(name = "Usuario.findByUsuVersion", query = "SELECT u FROM Usuario u WHERE u.usuVersion = :usuVersion")*/})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @SequenceGenerator(name = "PRO_USUARIOS_USU_ID_GENERATOR", sequenceName = "una.PRO_USUARIO_SEQ01", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRO_USUARIOS_USU_ID_GENERATOR")
    @Basic(optional = false)
    @Column(name = "USU_ID")
    private Long id;
    @Basic(optional = false)
    @Column(name = "USU_NOMBRE")
    private String nombre;
    @Column(name = "USU_PARTIDASJUGADAS")
    private Long partidasjugadas;
    @Column(name = "USU_PARTIDASGANADAS")
    private Long partidasganadas;
    @Column(name = "USU_ESTADO")
    private String estado;
    @Version
    @Basic(optional = false)
    @Column(name = "USU_VERSION")
    private Long version;
    @JoinTable(name = "PRO_USUPART", joinColumns = {
        @JoinColumn(name = "UXP_IDUSU", referencedColumnName = "USU_ID")}, inverseJoinColumns = {
        @JoinColumn(name = "UXP_IDPART", referencedColumnName = "PART_ID")})
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Partida> partidaList;
    @OneToMany(mappedBy = "usuId", fetch = FetchType.LAZY)
    private List<Letra> letraList;

    public Usuario(UsuarioDto usuarioDto) {
        this.id = usuarioDto.getId();
        actualizar(usuarioDto);
    }

    public Usuario() {
        
    }

    public Usuario(Long empId) {
        this.id = empId;
    }

    public void actualizar(UsuarioDto usuarioDto) {
        this.nombre = usuarioDto.getNombreUsu();
        this.estado = usuarioDto.getEstado();
        if (usuarioDto.getPartidasGanadas() == null)
            this.partidasjugadas = 0L;
        else
            this.partidasganadas = usuarioDto.getPartidasGanadas();
        if (usuarioDto.getParidasJugadas() == null)
            this.partidasganadas = 0L;
        else
            this.partidasjugadas = usuarioDto.getParidasJugadas();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getPartidasjugadas() {
        return partidasjugadas;
    }

    public void setPartidasjugadas(Long partidasjugadas) {
        this.partidasjugadas = partidasjugadas;
    }

    public Long getPartidasganadas() {
        return partidasganadas;
    }

    public void setPartidasganadas(Long partidasganadas) {
        this.partidasganadas = partidasganadas;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public List<Partida> getPartidaList() {
        return partidaList;
    }

    public void setPartidaList(List<Partida> partidaList) {
        this.partidaList = partidaList;
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
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.proyecto1.model.Usuario[ usuId=" + id + " ]";
    }
    
}
