/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyecto1.service;

import cr.ac.una.proyecto1.model.Partida;
import cr.ac.una.proyecto1.model.PartidaDto;
import cr.ac.una.proyecto1.model.Usuario;
import cr.ac.una.proyecto1.model.UsuarioDto;
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
public class PartidaService {
    EntityManager em = EntityManagerHelper.getInstance().getManager();
    private EntityTransaction et;
    
    public Respuesta getPartida(Long id){
        try {
            Query qryPartida = em.createNamedQuery("Partida.findById", Partida.class);
            qryPartida.setParameter("id", id);
            PartidaDto partidaDto = new PartidaDto((Partida) qryPartida.getSingleResult());
	return new Respuesta(true, "", "", "Partida", partidaDto);
        } catch (NoResultException ex) {
	    return new Respuesta(false, "No existe una Partida con las credenciales ingresadas.", "getPartida NoResultException");
	} catch (NonUniqueResultException ex) {
	    Logger.getLogger(PartidaService.class.getName()).log(Level.SEVERE, "Ocurrio un error al consultar la Partida.", ex);
	    return new Respuesta(false, "Ocurrio un error al consultar Partida.", "getEmpleado NonUniqueResultException");
	} catch (Exception ex) {
	    Logger.getLogger(PartidaService.class.getName()).log(Level.SEVERE, "Error obteniendo Partida [" + id + "]", ex);
	    return new Respuesta(false, "Error obteniendo Partida.", "getPartida " + ex.getMessage());
	}
    }
    
    public Respuesta guardarPartida(PartidaDto partidaDto){
        try{
            et = em.getTransaction();
            et.begin();
            Partida partida;
            if(partidaDto.getId() != null && partidaDto.getId() > 0){
                partida = em.find(Partida.class, partidaDto.getId());
                if (partida == null){
                    et.rollback();
                    return new Respuesta(false, "No se encontró el usuario a modificar", "guardarUsuario NoResultException");
                }
                partida.actualizarPartida(partidaDto);
                partida = em.merge(partida);
            } else {
                partida = new Partida(partidaDto);
                em.persist(partida);
            }
        et.commit();
            return new Respuesta(true, "", "", "Usuario", new PartidaDto(partida));
        } catch (Exception ex){
            et.rollback();
            Logger.getLogger(PartidaService.class.getName()).log(Level.SEVERE, "Ocurrio un error al guardar el usuario.", ex);
            return new Respuesta(false, "Ocurrio un error al guardar el usuario", "guardarUsuario " + ex.getMessage());
        }
    }
    
}
