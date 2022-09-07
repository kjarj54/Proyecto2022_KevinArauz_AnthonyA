package cr.ac.una.proyecto1.model;

import cr.ac.una.proyecto1.model.Letra;
import cr.ac.una.proyecto1.model.Usuario;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2022-06-26T09:48:15", comments="EclipseLink-2.7.9.v20210604-rNA")
@StaticMetamodel(Partida.class)
public class Partida_ { 

    public static volatile SingularAttribute<Partida, Long> timer;
    public static volatile ListAttribute<Partida, Usuario> usuarioList;
    public static volatile ListAttribute<Partida, Letra> letraList;
    public static volatile SingularAttribute<Partida, Long> id;
    public static volatile SingularAttribute<Partida, Long> version;
    public static volatile SingularAttribute<Partida, String> dificultad;

}