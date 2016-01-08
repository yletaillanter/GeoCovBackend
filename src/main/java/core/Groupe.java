package core;

import javax.persistence.*;
import java.util.List;

/**
 * Created by tidus on 08/01/2016.
 */
public class Groupe {

    private long id;
    private List<Client> clients;
    private boolean verrouillage;

    public Groupe() {}

    public Groupe(List<Client> clients) {
        this.clients = clients;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "groupe")
    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public boolean isVerrouillage() {
        return verrouillage;
    }


    public void setVerrouillage(boolean verrouillage) {
        this.verrouillage = verrouillage;
    }

}
