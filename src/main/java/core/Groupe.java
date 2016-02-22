package core;

import javax.persistence.*;
import java.util.List;

/**
 * Created by tidus on 08/01/2016.
 */
@Entity
@Table(name = "groupe")
@NamedQueries({
        @NamedQuery(
                name = "Groupe.findAll",
                query = "SELECT g FROM Groupe g"
        ),
        @NamedQuery(
        name = "Groupe.deleteTable",
        query = "DELETE FROM Groupe g"
)
})
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

    @OneToMany(mappedBy = "groupe", fetch = FetchType.EAGER)
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
