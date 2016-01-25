package db;

import com.google.common.base.Optional;
import com.google.common.primitives.Booleans;
import core.Client;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Query;
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

    public Client findByEmail(String email) {
        Query q = namedQuery("Client.findByEmail");
        q.setString("email", email);
        if (q.uniqueResult() != null) {
            return (Client) q.uniqueResult();
        } else {
            return null;
        }
    }

    public Boolean auth(Client client) {
        Client c = findByEmail(client.getEmail());
        if (c != null) {
            if (c.getPassword().equals(client.getPassword()))
                return true;
            else return false;
        } else return false;
    }
}
