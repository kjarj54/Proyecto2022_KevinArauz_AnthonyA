/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyecto1.service;

import cr.ac.una.proyecto1.model.Usuario;
import cr.ac.una.proyecto1.model.UsuarioDto;
import cr.ac.una.proyecto1.util.EntityManagerHelper;
import cr.ac.una.proyecto1.util.Respuesta;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

/**
 *
 * @author kevin
 */
public class UsuarioService {
    EntityManager em = EntityManagerHelper.getInstance().getManager();
    private EntityTransaction et;
    
    public Respuesta getUsuario(Long id){
        try {
            Query qryUsuario = em.createNamedQuery("Usuario.findById", Usuario.class);
            qryUsuario.setParameter("id", id);
            UsuarioDto usuarioDto = new UsuarioDto((Usuario) qryUsuario.getSingleResult());
	return new Respuesta(true, "", "", "Usuario", usuarioDto);
        } catch (NoResultException ex) {
	    return new Respuesta(false, "No existe un Usuario con las credenciales ingresadas.", "getUsuario NoResultException");
	} catch (NonUniqueResultException ex) {
	    Logger.getLogger(UsuarioService.class.getName()).log(Level.SEVERE, "Ocurrio un error al consultar el Usuario.", ex);
	    return new Respuesta(false, "Ocurrio un error al consultar el Usuario.", "getEmpleado NonUniqueResultException");
	} catch (Exception ex) {
	    Logger.getLogger(UsuarioService.class.getName()).log(Level.SEVERE, "Error obteniendo el Usuario [" + id + "]", ex);
	    return new Respuesta(false, "Error obteniendo el Usuario.", "getUsuario " + ex.getMessage());
	}
    }
    
    public Respuesta guardarUsuario(UsuarioDto usuarioDto){
        try{
            et = em.getTransaction();
            et.begin();
            Usuario usuario;
            if(usuarioDto.getId() != null && usuarioDto.getId() > 0){
                usuario = em.find(Usuario.class, usuarioDto.getId());
                if (usuario == null){
                    et.rollback();
                    return new Respuesta(false, "No se encontró el usuario a modificar", "guardarUsuario NoResultException");
                }
                usuario.actualizar(usuarioDto);
                usuario = em.merge(usuario);
            } else {
                usuario = new Usuario(usuarioDto);
                em.persist(usuario);
            }
        et.commit();
            return new Respuesta(true, "", "", "Usuario", new UsuarioDto(usuario));
        } catch (Exception ex){
            et.rollback();
            Logger.getLogger(UsuarioService.class.getName()).log(Level.SEVERE, "Ocurrio un error al guardar el usuario.", ex);
            return new Respuesta(false, "Ocurrio un error al guardar el usuario", "guardarUsuario " + ex.getMessage());
        }
    }
    
    public Respuesta getUsuarios() {
        try {
            Query query = em.createNamedQuery("Usuario.findAll",Usuario.class);
            List<Usuario> usuario = (List<Usuario>) query.getResultList();
            List<UsuarioDto> usuarioDto = new ArrayList<>();
            for (Usuario usu : usuario) {
                usuarioDto.add(new UsuarioDto(usu));
            }
            return new Respuesta(true, "", "", "Usuario", usuarioDto);
        } catch (NoResultException ex) {
            return new Respuesta(false, "No existen usuario con los criterios ingresados.", "getUsuario NoResultException");
        } catch (Exception ex) {
            Logger.getLogger(UsuarioService.class.getName()).log(Level.SEVERE, "Error obteniendo usuario.", ex);
            return new Respuesta(false, "Error obteniendo usuario.", "getUsuario " + ex.getMessage());
        }
    }
    
    public Respuesta eliminarUsuario(Long id) {
        try {
            et = em.getTransaction();
            et.begin();
            Usuario usuario;
            if (id != null && id > 0){
                usuario = em.find(Usuario.class, id);
                if (usuario == null){
                    et.rollback();
                    return new Respuesta(false, "No se encrontró el usuario a eliminar.", "eliminarUsuario NoResultException");
                }
                em.remove(usuario);
            } else {
                et.rollback();
                return new Respuesta(false, "Debe cargar el usuario a eliminar.", "eliminarUsuario NoResultException");
            }
            et.commit();
            return new Respuesta(true, "", "");
        } catch (Exception ex) {
            et.rollback();
            if (ex.getCause() != null && ex.getCause().getCause().getClass() == SQLIntegrityConstraintViolationException.class) {
                return new Respuesta(false, "No se puede eliminar el usuario porque tiene relaciones con otros registros.", "eliminarUsuario " + ex.getMessage());
            }
            Logger.getLogger(UsuarioService.class.getName()).log(Level.SEVERE, "Error eliminando el empleado.", ex);
            return new Respuesta(false, "Error eliminando el usuario.", "eliminarUsuario " + ex.getMessage());
        }
    }
}
