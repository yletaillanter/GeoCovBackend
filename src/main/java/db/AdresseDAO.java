package db;

import com.google.common.base.Optional;
import core.Adresse;
import core.Client;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Created by tidus on 08/01/2016.
 */
public class AdresseDAO extends AbstractDAO<Adresse> {
    public AdresseDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Optional<Adresse> findById(long id) {
        return Optional.fromNullable(get(id));
    }

    public Adresse create(Adresse adresse) {
        return persist(adresse);
    }

    public List<Adresse> findAll() {
        return list(namedQuery("Adresse.findAll"));
    }

    public Boolean isExist(Adresse adresse) {
        return null;
       /* Query q = namedQuery("Adresse.isExist");
        q.setString("email", email);
        if (q.) {
            return true;
        } else {
            return false;
        }*/
    }
}
