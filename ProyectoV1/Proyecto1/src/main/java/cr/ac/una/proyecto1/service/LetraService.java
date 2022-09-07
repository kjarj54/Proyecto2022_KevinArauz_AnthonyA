/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyecto1.service;

import cr.ac.una.proyecto1.model.Letra;
import cr.ac.una.proyecto1.model.LetraDto;
import cr.ac.una.proyecto1.util.EntityManagerHelper;
import cr.ac.una.proyecto1.util.Respuesta;
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
public class LetraService {
    EntityManager em = EntityManagerHelper.getInstance().getManager();
    private EntityTransaction et;
    
    public Respuesta getLetra(Long id){
        try {
            Query qryLetra = em.createNamedQuery("Letra.findById", Letra.class);
            qryLetra.setParameter("id", id);
            LetraDto letraDto = new LetraDto((Letra) qryLetra.getSingleResult());
	return new Respuesta(true, "", "", "Letra", letraDto);
        } catch (NoResultException ex) {
	    return new Respuesta(false, "No existe una Letra con las credenciales ingresadas.", "getUsuario NoResultException");
	} catch (NonUniqueResultException ex) {
	    Logger.getLogger(LetraService.class.getName()).log(Level.SEVERE, "Ocurrio un error al consultar Letra.", ex);
	    return new Respuesta(false, "Ocurrio un error al consultar la Letra.", "getEmpleado NonUniqueResultException");
	} catch (Exception ex) {
	    Logger.getLogger(LetraService.class.getName()).log(Level.SEVERE, "Error obteniendo Letra [" + id + "]", ex);
	    return new Respuesta(false, "Error obteniendo Letra.", "getLetra " + ex.getMessage());
	}
    }
    
    public Respuesta guardarLetra(LetraDto letraDto){
        try{
            et = em.getTransaction();
            et.begin();
            Letra letra;
            if(letraDto.getId() != null && letraDto.getId() > 0){
                letra = em.find(Letra.class, letraDto.getId());
                if (letra == null){
                    et.rollback();
                    return new Respuesta(false, "No se encontró el usuario a modificar", "guardarUsuario NoResultException");
                }
                letra.actualizarLetra(letraDto);
                letra = em.merge(letra);
            } else {
                letra = new Letra(letraDto);
                em.persist(letra);
            }
        et.commit();
            return new Respuesta(true, "", "", "Usuario", new LetraDto(letra));
        } catch (Exception ex){
            et.rollback();
            Logger.getLogger(UsuarioService.class.getName()).log(Level.SEVERE, "Ocurrio un error al guardar el usuario.", ex);
            return new Respuesta(false, "Ocurrio un error al guardar el usuario", "guardarUsuario " + ex.getMessage());
        }
    }
}
