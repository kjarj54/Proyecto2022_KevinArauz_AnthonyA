package cr.ac.una.proyecto1.model;

import cr.ac.una.proyecto1.model.Celda;
import cr.ac.una.proyecto1.model.Partida;
import cr.ac.una.proyecto1.model.Usuario;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2022-06-26T09:48:15", comments="EclipseLink-2.7.9.v20210604-rNA")
@StaticMetamodel(Letra.class)
public class Letra_ { 

    public static volatile SingularAttribute<Letra, Usuario> usuId;
    public static volatile SingularAttribute<Letra, Partida> partId;
    public static volatile SingularAttribute<Letra, String> dirImg;
    public static volatile SingularAttribute<Letra, Long> id;
    public static volatile SingularAttribute<Letra, String> letra;
    public static volatile ListAttribute<Letra, Celda> celdaList;
    public static volatile SingularAttribute<Letra, Long> puntos;
    public static volatile SingularAttribute<Letra, Long> version;

}