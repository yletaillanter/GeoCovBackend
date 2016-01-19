package core;

import javax.persistence.*;
import java.util.List;

/**
 * Created by jeremy on 08/01/2016.
 */
@Entity
@Table(name = "adresse")
@NamedQueries({
        @NamedQuery(
                name = "Adresse.findAll",
                query = "SELECT a FROM Adresse a"
        ),
        @NamedQuery(
        name = "Adresse.isExist",
        query = "SELECT a FROM Adresse a"
        )
})
public class Adresse {

    private long id;
    private String numero;
    private String rue;
    private String cp;
    private String ville;
    private String latitude;
    private String longitude;
    private List<Client> clients;

    public Adresse() {}

    public Adresse(String numero, String rue, String cp, String ville) {
        this.numero = numero;
        this.rue = rue;
        this.cp = cp;
        this.ville = ville;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(nullable = false)
    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    @Column(nullable = false)
    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    @Column(nullable = false)
    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    @Column(nullable = false)
    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @ManyToMany(mappedBy="adresses")
    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

}
