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
    private String latMidPoint;
    private String longMidPoint;
    private String latDest;
    private String longDest;

    public Groupe() {
        this.latMidPoint = null;
        this.longMidPoint = null;
    }

    public Groupe(List<Client> clients) {
        this.latMidPoint = null;
        this.longMidPoint = null;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLatMidPoint() {
        return latMidPoint;
    }

    public void setLatMidPoint(String latMidPoint) {
        this.latMidPoint = latMidPoint;
    }

    public String getLongMidPoint() {
        return longMidPoint;
    }

    public void setLongMidPoint(String longMidPoint) {
        this.longMidPoint = longMidPoint;
    }

    public String getLatDest() {
        return latDest;
    }

    public void setLatDest(String latDest) {
        this.latDest = latDest;
    }

    public String getLongDest() {
        return longDest;
    }

    public void setLongDest(String longDest) {
        this.longDest = longDest;
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

    public void calculateMidPoint(double lat1,double lon1,double lat2,double lon2){

        double dLon = Math.toRadians(lon2 - lon1);

        //convert to radians
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);
        lon1 = Math.toRadians(lon1);

        double Bx = Math.cos(lat2) * Math.cos(dLon);
        double By = Math.cos(lat2) * Math.sin(dLon);
        double lat3 = Math.atan2(Math.sin(lat1) + Math.sin(lat2), Math.sqrt((Math.cos(lat1) + Bx) * (Math.cos(lat1) + Bx) + By * By));
        double lon3 = lon1 + Math.atan2(By, Math.cos(lat1) + Bx);

        setLatMidPoint(String.valueOf(Math.toDegrees(lat3)));
        setLongMidPoint(String.valueOf(Math.toDegrees(lon3)));
    }
}
