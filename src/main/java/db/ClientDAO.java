package db;

import com.google.common.base.Optional;
import core.Client;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Created by tidus on 08/01/2016.
 */
public class ClientDAO extends AbstractDAO<Client> {
    public ClientDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Optional<Client> findById(long id) {
        return Optional.fromNullable(get(id));
    }

    public Client create(Client client) {
        return persist(client);
    }

    public List<Client> findAll() {
        return list(namedQuery("Client.findAll"));
    }
}
