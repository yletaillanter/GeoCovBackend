package core;

/**
 * Created by jeremy on 08/01/2016.
 */

import javax.persistence.*;
import java.security.Principal;
import java.util.List;

@Entity
@Table(name = "client")
@NamedQueries({
        @NamedQuery(
                name = "Client.findAll",
                query = "SELECT c FROM Client c"
        ),
        @NamedQuery(
        name = "Client.findByEmail",
        query = "SELECT c FROM Client c WHERE c.email = :email"
        )
})
public class Client implements Principal{

    private long id;
    private String name;
    private String lastname;
    private String email;
    private String password;
    private String phone;
    private List<Adresse> adresses;
    private int distanceMax;
    private Groupe groupe;

    public Client(String name, String lastname, String email, String password, String phone) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    public Client() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = false)
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Column(nullable = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(nullable = false)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @ManyToMany(cascade = CascadeType.ALL, fetch =  FetchType.EAGER)
    @JoinTable(name="client_adresse")
    public List<Adresse> getAdresses() {
        return adresses;
    }

    public void setAdresses(List<Adresse> adresses) {
        this.adresses = adresses;
    }

    public int getDistanceMax() {
        return distanceMax;
    }

    public void setDistanceMax(int distanceMax) {
        this.distanceMax = distanceMax;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "groupe_id")
    public Groupe getGroupe() {
        return groupe;
    }

    public void setGroupe(Groupe groupe) {
        this.groupe = groupe;
    }
}
