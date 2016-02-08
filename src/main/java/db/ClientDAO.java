package db;

import com.google.common.base.Optional;
import com.google.common.primitives.Booleans;
import core.Adresse;
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

    /**
     * Fonction effectuant l'authentification du client
     * @param client Client
     * @return Boolean
     */
    public Boolean auth(Client client) {
        Client c = findByEmail(client.getEmail());
        if (c != null) {
            if (c.getPassword().equals(client.getPassword()))
                return true;
            else return false;
        } else return false;
    }

    /**
     * Fonction pemettant de mettre a jour les informations personnel d'un client
     * @param id Long
     * @param client Client
     * @return Client
     */
    public Client update(Long id, Client client) {
        Optional<Client> oldClient = findById(id);
        if (oldClient.isPresent()) {
            if (client.getName() != null && oldClient.get().getName() != client.getName()) {
                oldClient.get().setName(client.getName());
            }
            if (client.getLastname() != null && oldClient.get().getLastname() != client.getLastname()) {
                oldClient.get().setLastname(client.getLastname());
            }
            if (client.getEmail() != null && oldClient.get().getEmail() != client.getEmail()) {
                oldClient.get().setEmail(client.getEmail());
            }
            if (client.getPhone() != null && oldClient.get().getPhone() != client.getPhone()) {
                oldClient.get().setPhone(client.getPhone());
            }
            if (client.getPassword() != null && oldClient.get().getPassword() != client.getPassword()) {
                oldClient.get().setPassword(client.getPassword());
            }
            persist(oldClient.get());
        }
        return oldClient.get();
    }

    /**
     * Fonction permettant de mettre à jour une adresse d'un client
     * @param id Long
     * @param client Client
     * @return Client
     */
    public Client updateAdresse(Long id, Client client) {
        Optional<Client> oldClient = findById(id);
        if (oldClient.isPresent() && client.getAdresses() != null) {
            Adresse newAdresse = client.getAdresses().get(0);
            int i = 0;
            while (i < oldClient.get().getAdresses().size()) {
                if (oldClient.get().getAdresses().get(i).getEnd() == newAdresse.getEnd()) {
                    oldClient.get().getAdresses().set(i, newAdresse);
                }
                i++;
            }
            persist(oldClient.get());
        }
        return oldClient.get();
    }
}
