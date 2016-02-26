package db;

import com.google.common.base.Optional;
import com.google.common.primitives.Booleans;
import core.Adresse;
import core.Client;
import core.Groupe;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import java.util.*;

/**
 * Created by tidus on 08/01/2016.
 */
public class ClientDAO extends AbstractDAO<Client> {

    private GroupeDAO gdao;

    public ClientDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public void setGdao(GroupeDAO gdao) {
        this.gdao = gdao;
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

    public void unsetGroupe() {
        namedQuery("Client.unsetGroupe").executeUpdate();
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
            if(oldClient.get().getAdresses().size()>0){
                int i = 0;
                while (i < oldClient.get().getAdresses().size()) {
                    if (oldClient.get().getAdresses().get(i).getEnd() == newAdresse.getEnd()) {
                        oldClient.get().getAdresses().set(i, newAdresse);
                    }
                    i++;
                }
            } else {
                oldClient.get().getAdresses().add(newAdresse);
            }

            persist(oldClient.get());
        }
        unsetGroupe();
        gdao.deleteAll();
        gdao.insertCluster();
        return oldClient.get();
    }

    public void updateGroupe(Client c, Groupe g) {
        c.setGroupe(g);
        persist(c);
    }

    /**
     *
     * @return Arraylist
     */
    public ArrayList getCluster() {
        List<Client> clients = findAll();
        HashMap<Long, HashMap> matrice = getMatrice(clients);
        //List qui contiendra les clusters
        ArrayList<ArrayList> clusters = new ArrayList<ArrayList>();
        int counter = 1;
        // ON parcours la hashmap principale
        for (HashMap.Entry<Long, HashMap> hash1 : matrice.entrySet()) {
            HashMap<Long, Double> mChildrens = hash1.getValue();
            // liste servant à stocker le cluster
            ArrayList cluster = new ArrayList();
            // on parcours la hashmap des distances
            for (HashMap.Entry<Long, Double> hash2 : mChildrens.entrySet()) {
                Long max;
                //  si vide on ajoute de suite
                if (cluster.size() < 3 && hash2.getValue()<3000) {
                    if(clusters.isEmpty()) {
                        cluster.add(hash2.getKey());
                    } else {
                        if (checkIfNotInOtherClusterFirst(hash2.getKey(), clusters, matrice)) {
                            cluster.add(hash2.getKey());
                        }
                    }
                } else { // La liste n'est pas vide
                    // init de max
                    max = null;
                    // On iterate sur la liste, voir si on est pas plus prés qu'une des valeurs présente
                    ListIterator<Long> itCh = cluster.listIterator();
                    int index = 0;
                    boolean modified = false;
                    // On parcours la liste voir si la distance actuelle est plus proche
                    while(itCh.hasNext()) {
                        index = 0;
                        Long currentChild = itCh.next();
                        if(hash2.getValue()< mChildrens.get(currentChild)){
                            if(max == null){
                                max = currentChild;
                                modified = true;
                            } else {
                                if (mChildrens.get(currentChild) > mChildrens.get(max)){
                                    max = currentChild;
                                    modified = true;
                                }
                            }
                        }
                        index++;
                    }
                    if(modified) {
                        boolean notInCluster = checkIfNotInOtherClusterFirst(hash2.getKey(), /*(long) max,*/  clusters, matrice);
                        if (notInCluster) {
                            cluster.remove(max);
                            cluster.add(hash2.getKey());
                        }
                    }
                }
            }
            if (checkIfNotInOtherClusterFirst(hash1.getKey(), clusters, matrice)) {
                cluster.add(hash1.getKey());
            }
            clusters.add(cluster);
            if (counter == Math.floor(matrice.size()/2)){
                return clusters;
            }
            counter++;
        }
        return clusters; // + " Le cluster : " + clusters.toString()
    }

    /**
     *
     * @param clients
     * @return
     */
    private HashMap<Long, HashMap> getMatrice(List<Client> clients) {
        HashMap<Long, HashMap> matrice = new HashMap<Long, HashMap>();
        Iterator<Client> itC = clients.iterator();
        while(itC.hasNext()) {
            Iterator<Client> itC2 = clients.iterator();
            HashMap<Long, Double> distance = new HashMap<Long, Double>();
            Client itClient = itC.next();
            while (itC2.hasNext()) {
                Client itClient2 = itC2.next();
                if (!itClient.equals(itClient2)) {
                    double dist = distFrom(
                            Double.parseDouble(itClient.getAdresses().get(0).getLatitude()),
                            Double.parseDouble(itClient.getAdresses().get(0).getLongitude()),
                            Double.parseDouble(itClient2.getAdresses().get(0).getLatitude()),
                            Double.parseDouble(itClient2.getAdresses().get(0).getLongitude())
                    );
                    distance.put(itClient2.getId(), dist);
                }
            }
            matrice.put(itClient.getId(), distance);
        }
        return matrice;
    }

    /**
     *
     * @param lat1
     * @param lng1
     * @param lat2
     * @param lng2
     * @return
     */
    private double distFrom(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double dist = Math.floor(earthRadius * c);

        return dist;
    }

    /**
     *
     * @param valueToCheck
     * @param clusters
     * @param matrice
     * @return
     */
    private boolean checkIfNotInOtherClusterFirst(Long valueToCheck, /*Long myValue,*/ ArrayList<ArrayList> clusters, HashMap<Long, HashMap> matrice) {
        ListIterator<ArrayList> itClu = clusters.listIterator();
        HashMap<Long, Double> distance = null;
        boolean result = true;

        while (itClu.hasNext()) {
            result = false;
            ArrayList children = itClu.next();
            if (children.contains(valueToCheck)) {
                return result;
                /*
                double total = 0;
                ListIterator<Long> itCh = children.listIterator();
                while (itCh.hasNext()) {
                    Long next = itCh.next();
                    if(!next.equals(valueToCheck)){
                        distance = matrice.get(next);
                        total += distance.get(valueToCheck);
                    }
                }
                distance = matrice.get(valueToCheck);
                Double test = distance.get(myValue);
                if (total/children.size() < test) {
                    System.out.println("return false ");
                    return false;
                }
                else
                    return true;
                */

            } else {
                result = true;
            }
        }
        return result;
    }
}
