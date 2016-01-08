package db;

import com.google.common.base.Optional;
import core.Adresse;
import core.Groupe;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Created by tidus on 08/01/2016.
 */
public class GroupeDAO extends AbstractDAO<Groupe> {
    public GroupeDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Optional<Groupe> findById(long id) {
        return Optional.fromNullable(get(id));
    }

    public Groupe create(Groupe groupe) {
        return persist(groupe);
    }

    public List<Groupe> findAll() {
        return list(namedQuery("Groupe.findAll"));
    }
}
