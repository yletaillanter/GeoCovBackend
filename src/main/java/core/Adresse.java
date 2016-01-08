package core;

import javax.persistence.*;
import java.util.List;

/**
 * Created by jeremy on 08/01/2016.
 */
@Entity
@Table(name = "adresse")
public class Adresse {

    private long id;
    private int numero;
    private String rue;
    private String cp;
    private String ville;
    private String latitude;
    private String longitude;
    private List<Client> clients;

    public Adresse() {}

    public Adresse(int numero, String rue, String cp, String ville, List clients) {

        this.numero = numero;
        this.rue = rue;
        this.cp = cp;
        this.ville = ville;
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

    @Column(nullable = false)
    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
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

    @Column(nullable = false)
    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @Column(nullable = false)
    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }


    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

}
