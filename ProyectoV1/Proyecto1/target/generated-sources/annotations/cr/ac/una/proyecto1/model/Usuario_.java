package cr.ac.una.proyecto1.model;

import cr.ac.una.proyecto1.model.Letra;
import cr.ac.una.proyecto1.model.Partida;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2022-06-26T09:48:15", comments="EclipseLink-2.7.9.v20210604-rNA")
@StaticMetamodel(Usuario.class)
public class Usuario_ { 

    public static volatile ListAttribute<Usuario, Partida> partidaList;
    public static volatile SingularAttribute<Usuario, String> estado;
    public static volatile ListAttribute<Usuario, Letra> letraList;
    public static volatile SingularAttribute<Usuario, Long> partidasjugadas;
    public static volatile SingularAttribute<Usuario, Long> id;
    public static volatile SingularAttribute<Usuario, String> nombre;
    public static volatile SingularAttribute<Usuario, Long> version;
    public static volatile SingularAttribute<Usuario, Long> partidasganadas;

}