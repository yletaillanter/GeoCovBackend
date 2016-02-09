package resources;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import core.Adresse;
import core.Client;
import db.ClientDAO;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by yoannlt on 07/01/2016.
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.TEXT_PLAIN)
@Path("/client")
public class ClientResource {

    private ClientDAO dao;

    public ClientResource(ClientDAO dao) {
        this.dao = dao;
    }

    @GET
    @Produces("application/json")
    @UnitOfWork
    public List<Client> getAll() {
        List<Client> cs = dao.findAll();
        return cs;
    }

    @GET
    @Path("/{id}")
    @UnitOfWork
    public Optional<Client> getUserById(@PathParam("id") long id) {
        return dao.findById(id);
    }

    @POST
    @UnitOfWork
    public Response addClient(Client client) {
        //Check if email already exist
        if (checkEmail(client.getEmail())) {
            return Response.status(404).build();
        } else {
            Client clientCreated = dao.create(client);
            return Response.ok(clientCreated).type(MediaType.APPLICATION_JSON).build();

        }
    }

    @POST
    @Path("/auth")
    @UnitOfWork
    public Response authClient(Client client) {
        if (!dao.auth(client)) {
            return Response.status(403).build();
        } else {
            Client c = dao.findByEmail(client.getEmail());
            return Response.ok(c).type(MediaType.APPLICATION_JSON).build();
        }

    }

    @GET
    @Path("/checkEmail/{email}")
    @UnitOfWork
    public Boolean checkEmail(@PathParam("email") String email) {
        Client client = dao.findByEmail(email);
        return client != null;
    }

    @PUT
    @Path("/{id}")
    @UnitOfWork
    public Response updateClient(@PathParam("id") long id, Client client) {
        if(client.getAdresses() != null) {
            return Response.ok(dao.updateAdresse(id, client)).type(MediaType.APPLICATION_JSON).build();
        } else {
            return Response.ok(dao.update(id, client)).type(MediaType.APPLICATION_JSON).build();
        }
    }


    // For test purpose
    @GET
    @Path("/addDummyclient")
    @UnitOfWork
    public void addDummyClients() {
        // Adresse 1
        Adresse adresse1 = new Adresse("263", "Avenue du Général Leclerc", "35000", "Rennes", false);
        adresse1.setLatitude("48.116408");
        adresse1.setLongitude("-1.638647");

        // Adresse 2
        Adresse adresse2 = new Adresse("207", "Rue de Vern", "35200", "Rennes", false);
        adresse2.setLatitude("48.088490");
        adresse2.setLongitude("-1.644350");

        // Adresse 3
        Adresse adresse3 = new Adresse("2", "Rue de la Volga", "35200", "Rennes", false);
        adresse3.setLatitude("48.087166");
        adresse3.setLongitude("-1.652265");

        // Adresse 4
        Adresse adresse4 = new Adresse("9", "Rue Jean Delourmel", "35200", "Rennes", false);
        adresse4.setLatitude("48.095914");
        adresse4.setLongitude("-1.648769");

        // Adresse 5
        Adresse adresse5 = new Adresse("20", "Avenue des Buttes de Coesmes", "35708", "Rennes", false);
        adresse5.setLatitude("48.120825");
        adresse5.setLongitude("-1.635739");

        // Adresse Arrivé
        Adresse adresseArrive = new Adresse("1", "rue du père Grignon", "35000", "Rennes", true);
        adresseArrive.setLatitude("48.111807");
        adresseArrive.setLongitude("-1.687499");

        //List d'adresse
        List<Adresse> adresses1 = new ArrayList<Adresse>();
        adresses1.add(adresse1);
        adresses1.add(adresseArrive);

        //List d'adresse
        List<Adresse> adresses2 = new ArrayList<Adresse>();
        adresses2.add(adresse2);
        adresses2.add(adresseArrive);

        //List d'adresse
        List<Adresse> adresses3 = new ArrayList<Adresse>();
        adresses3.add(adresse3);
        adresses3.add(adresseArrive);

        //List d'adresse
        List<Adresse> adresses4 = new ArrayList<Adresse>();
        adresses4.add(adresse4);
        adresses4.add(adresseArrive);

        //List d'adresse
        List<Adresse> adresses5 = new ArrayList<Adresse>();
        adresses5.add(adresse5);
        adresses5.add(adresseArrive);

        // Client
        Client client1 = new Client("Julie", "Guégnaud", "julie.guegnaud@etudiant.univ-rennes1.fr", "gateau", "0675456342");
        client1.setAdresses(adresses1);

        Client client2 = new Client("Julie", "t", "julie.guegnaud@univ-rennes1.fr", "gateau", "0675456342");
        client2.setAdresses(adresses2);

        Client client3 = new Client("Julie", "r", "julie@etudiant.univ-rennes1.fr", "gateau", "0675456342");
        client3.setAdresses(adresses3);

        Client client4 = new Client("Julie", "c", "julie.c@etudiant.univ-rennes1.fr", "gateau", "0675456342");
        client4.setAdresses(adresses4);

        Client client5 = new Client("Julie", "b", "julie.b@etudiant.univ-rennes1.fr", "gateau", "0675456342");
        client5.setAdresses(adresses5);

        addClient(client1);
        addClient(client2);
        addClient(client3);
        addClient(client4);
        addClient(client5);
    }

    // TEST
    @GET
    @Path("/testDistance")
    @UnitOfWork
    public String testDistance() {
        List<Client> clients = dao.findAll();
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

        //List qui contiendra les clusters
        ArrayList<ArrayList> clusters = new ArrayList<ArrayList>();

        int counter = 1;
        // ON parcours la hashmap principale
        for (HashMap.Entry<Long, HashMap> hash1 : matrice.entrySet()) {

            System.out.println("parcours de la map : " + hash1.toString());

            HashMap<Long, Double> mChildrens = hash1.getValue();

            // liste servant à stocker le cluster
            ArrayList cluster = new ArrayList();

            // on parcours la hashmap des distances
            for (HashMap.Entry<Long, Double> hash2 : mChildrens.entrySet()) {
                System.out.println("parcours de la map distances : " + hash2.toString());
                Long max;
                //  si vide on ajoute de suite
                if (cluster.size() < 2) {
                    if(clusters.isEmpty()) {
                        cluster.add(hash2.getKey());

                    } else {
                        if (checkIfNotInOtherClusterFirst(hash2.getKey(), clusters, matrice)) {
                            cluster.add(hash2.getKey());
                            System.out.println("add : " + hash2.getKey() + " to cluster : " + cluster);
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
                                System.out.println("max devient : " + max);
                            } else {
                                if (mChildrens.get(currentChild) > mChildrens.get(max)){
                                    max = currentChild;
                                    System.out.println("(in) max devient : " + max);
                                    modified = true;
                                }
                            }
                        }
                        index++;
                    }
                    if(modified) {

                        System.out.println("Je veux ajouter la valeur : "+hash2.getKey());
                        boolean notInCluster = checkIfNotInOtherClusterFirst(hash2.getKey(), /*(long) max,*/  clusters, matrice);


                        if (notInCluster) {
                            System.out.println("Modified " + max);
                            cluster.remove(max);
                            cluster.add(hash2.getKey());
                        }
                    }
                }
            }
            cluster.add(hash1.getKey());
            System.out.println("cluster final : " + cluster);
            clusters.add(cluster);


            if (counter == Math.floor(matrice.size()/2)){
                return clusters.toString();
            }
            counter++;
        }

        return clusters.toString();
        //return matrice.toString();
    }

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

    private boolean checkIfNotInOtherClusterFirst(Long valueToCheck, /*Long myValue,*/ ArrayList<ArrayList> clusters, HashMap<Long, HashMap> matrice) {
        //return true;
        ListIterator<ArrayList> itClu = clusters.listIterator();

        HashMap<Long, Double> distance = null;

        System.out.println("DANS LE CHECK");

        // Bug pour la première boucle, il n'y a pas encore de cluster dans la boucle
        while (itClu.hasNext()) {

            ArrayList children = itClu.next();
            if (children.contains(valueToCheck)) {
                System.out.println("return false : contain");
                return false;
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
                System.out.println("return TRUE : NOT contain");
                return true;
            }
        }
        System.out.println("return TRUE : PAS ENCORE DE CLUSTERS");
        return true;
    }

}