/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyecto1.service;

import cr.ac.una.proyecto1.model.Celda;
import cr.ac.una.proyecto1.model.CeldaDto;
import cr.ac.una.proyecto1.model.Palabra;
import cr.ac.una.proyecto1.model.PalabraDto;
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
public class PalabraService {
    EntityManager em = EntityManagerHelper.getInstance().getManager();
    private EntityTransaction et;
    
    public Respuesta getPalabra(String palabra){
        try {
            Query qryPalabra = em.createNamedQuery("Palabra.findByPalabra", Palabra.class);
            qryPalabra.setParameter("palabra", palabra);
            PalabraDto palabraDto = new PalabraDto((Palabra) qryPalabra.getSingleResult());
	return new Respuesta(true, "", "", "Palabra", palabraDto);
        } catch (NoResultException ex) {
	    return new Respuesta(false, "No existe una Palabra con las credenciales ingresadas.", "getPalabra NoResultException");
	} catch (NonUniqueResultException ex) {
	    Logger.getLogger(PalabraService.class.getName()).log(Level.SEVERE, "Ocurrio un error al consultar la Palabra.", ex);
	    return new Respuesta(false, "Ocurrio un error al consultar la palabra.", "getPalabra NonUniqueResultException");
	} catch (Exception ex) {
	    Logger.getLogger(PalabraService.class.getName()).log(Level.SEVERE, "Error obteniendo el la Palabra [" + palabra +"]", ex);
	    return new Respuesta(false, "Error obteniendo la Palabra.", "getPalabra " + ex.getMessage());
	}
    }
}
