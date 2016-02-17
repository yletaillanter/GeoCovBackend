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
        Query q = namedQuery("Adresse.isExist");
        q.setString("rue", adresse.getRue());
        q.setString("numero", adresse.getNumero());
        return q.uniqueResult() != null;
    }

    public List<Adresse> getAllByRue(String nom) {
        Query q = namedQuery("Adresse.findByRue");
        q.setString("rue", nom);
        return list(q);
    }

    public List<Adresse> getAllAdresseByUser(Long id_client) {
        Query q = namedQuery("Adresse.findByUser");
        q.setLong("id_client", id_client);
        return list(q);
    }
}
