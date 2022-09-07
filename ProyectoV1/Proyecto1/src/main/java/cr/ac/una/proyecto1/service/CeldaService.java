/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyecto1.service;

import cr.ac.una.proyecto1.model.Celda;
import cr.ac.una.proyecto1.model.CeldaDto;
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
public class CeldaService {
    EntityManager em = EntityManagerHelper.getInstance().getManager();
    private EntityTransaction et;
    
    public Respuesta getCelda(Long id){
        try {
            Query qryCelda = em.createNamedQuery("Celda.findById", Celda.class);
            qryCelda.setParameter("id", id);
            CeldaDto celdaDto = new CeldaDto((Celda) qryCelda.getSingleResult());
	return new Respuesta(true, "", "", "Usuario", celdaDto);
        } catch (NoResultException ex) {
	    return new Respuesta(false, "No existe una celda con las credenciales ingresadas.", "getCelda NoResultException");
	} catch (NonUniqueResultException ex) {
	    Logger.getLogger(CeldaService.class.getName()).log(Level.SEVERE, "Ocurrio un error al consultar la Celda.", ex);
	    return new Respuesta(false, "Ocurrio un error al consultar la Celda.", "getCelda NonUniqueResultException");
	} catch (Exception ex) {
	    Logger.getLogger(CeldaService.class.getName()).log(Level.SEVERE, "Error obteniendo el la celda [" + id + "]", ex);
	    return new Respuesta(false, "Error obteniendo la Celda.", "getCelda " + ex.getMessage());
	}
    }
    
     public Respuesta guardarCelda(CeldaDto celdaDto){
        try{
            et = em.getTransaction();
            et.begin();
            Celda celda;
            if(celdaDto.getId() != null && celdaDto.getId() > 0){
                celda = em.find(Celda.class, celdaDto.getId());
                if (celda == null){
                    et.rollback();
                    return new Respuesta(false, "No se encontró el usuario a modificar", "guardarUsuario NoResultException");
                }
                celda.actualizarCelda(celdaDto);
                celda = em.merge(celda);
            } else {
                celda = new Celda(celdaDto);
                em.persist(celda);
            }
        et.commit();
            return new Respuesta(true, "", "", "Celda", new CeldaDto(celda));
        } catch (Exception ex){
            et.rollback();
            Logger.getLogger(CeldaService.class.getName()).log(Level.SEVERE, "Ocurrio un error al guardar la celda.", ex);
            return new Respuesta(false, "Ocurrio un error al guardar la celda", "guardarCelda " + ex.getMessage());
        }
    }
     
    
    
}
