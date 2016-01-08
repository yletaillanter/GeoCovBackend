package core;

/**
 * Created by jeremy on 08/01/2016.
 */

import javax.persistence.*;
import javax.print.StreamPrintService;

@Entity
@Table(name = "client")
@NamedQueries({
        @NamedQuery(
                name = "Client.findAll",
                query = "SELECT c FROM Client c"
        )
})
public class Client {

    private long id;
    private String prenom;
    private String nom;
    private String email;
    private String mdp;
    private String telephone;
    private Adresse domicile;
    private Adresse travail;
    private int distanceMax;
    private Groupe groupe;

    public Client(String prenom, String nom, String email, String mdp, String telephone, Adresse domicile, Adresse travail) {
        this.prenom = prenom;
        this.nom = nom;
        this.email = email;
        this.mdp = mdp;
        this.telephone = telephone;
        this.domicile = domicile;
        this.travail = travail;
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
    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    @Column(nullable = false)
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Column(nullable = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(nullable = false)
    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    @Column(nullable = false)
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Adresse getDomicile() {
        return domicile;
    }

    public void setDomicile(Adresse domicile) {
        this.domicile = domicile;
    }

    public Adresse getTravail() {
        return travail;
    }

    public void setTravail(Adresse travail) {
        this.travail = travail;
    }

    public int getDistanceMax() {
        return distanceMax;
    }

    public void setDistanceMax(int distanceMax) {
        this.distanceMax = distanceMax;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "groupe_id")
    public Groupe getGroupe() {
        return groupe;
    }

    public void setGroupe(Groupe groupe) {
        this.groupe = groupe;
    }
}
