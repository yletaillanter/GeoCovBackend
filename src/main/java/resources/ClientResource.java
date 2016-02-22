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
        adresse4.setLongitude("-1.748769");

        // Adresse 5
        Adresse adresse5 = new Adresse("1", "Avenue des Buttes de Coesmes", "35708", "Rennes", false);
        adresse5.setLatitude("48.120825");
        adresse5.setLongitude("-1.835739");

        // Adresse 6
        Adresse adresse6 = new Adresse("20", "Avenue des Buttes de Coesmes", "35708", "Rennes", false);
        adresse6.setLatitude("48.130825");
        adresse6.setLongitude("-1.645739");

        // Adresse 7
        Adresse adresse7 = new Adresse("20", "Avenue des Buttes de Coesmes", "35708", "Rennes", false);
        adresse7.setLatitude("48.110825");
        adresse7.setLongitude("-1.678739");

        // Adresse 8
        Adresse adresse8 = new Adresse("20", "Avenue des Buttes de Coesmes", "35708", "Rennes", false);
        adresse8.setLatitude("48.170825");
        adresse8.setLongitude("-1.235739");

        // Adresse 9
        Adresse adresse9 = new Adresse("20", "Avenue des Buttes de Coesmes", "35708", "Rennes", false);
        adresse9.setLatitude("48.100825");
        adresse9.setLongitude("-1.435739");


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

        //List d'adresse
        List<Adresse> adresses6 = new ArrayList<Adresse>();
        adresses6.add(adresse6);
        adresses6.add(adresseArrive);

        //List d'adresse
        List<Adresse> adresses7 = new ArrayList<Adresse>();
        adresses7.add(adresse7);
        adresses7.add(adresseArrive);

        //List d'adresse
        List<Adresse> adresses8 = new ArrayList<Adresse>();
        adresses8.add(adresse8);
        adresses8.add(adresseArrive);

        //List d'adresse
        List<Adresse> adresses9 = new ArrayList<Adresse>();
        adresses9.add(adresse9);
        adresses9.add(adresseArrive);

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

        Client client6 = new Client("Julie", "w", "julie.w@etudiant.univ-rennes1.fr", "gateau", "0675456342");
        client6.setAdresses(adresses6);

        Client client7 = new Client("Julie", "d", "julie.d@etudiant.univ-rennes1.fr", "gateau", "0675456342");
        client7.setAdresses(adresses7);

        Client client8 = new Client("Julie", "e", "julie.e@etudiant.univ-rennes1.fr", "gateau", "0675456342");
        client8.setAdresses(adresses8);

        Client client9 = new Client("Julie", "f", "julie.f@etudiant.univ-rennes1.fr", "gateau", "0675456342");
        client9.setAdresses(adresses9);

        addClient(client1);
        addClient(client2);
        addClient(client3);
        addClient(client4);
        addClient(client5);
        addClient(client6);
        addClient(client7);
        addClient(client8);
        addClient(client9);

        // AJOUT DE CLIENT
        Adresse adresse10 = new Adresse("9", "Rue du Gast", "35700", "Rennes", false);
        adresse10.setLatitude("48.132065");
        adresse10.setLongitude("-1.660300");

        // adresse11
        Adresse adresse11 = new Adresse("12", "Rue du Houx", "35700", "Rennes", false);
        adresse11.setLatitude("48.131177");
        adresse11.setLongitude("-1.694828");

        // adresse12
        Adresse adresse12 = new Adresse("23", "Rue de Locmariaquer", "35700", "Rennes", false);
        adresse12.setLatitude("48.134160");
        adresse12.setLongitude("-1.670150");

        // adresse13
        Adresse adresse13 = new Adresse("6", "Rue Raoul Anthony", "35700", "Rennes", false);
        adresse13.setLatitude("48.124782");
        adresse13.setLongitude("-1.633828");

        // adresse14
        Adresse adresse14 = new Adresse("20", "Avenue des Buttes de Coesmes", "35700", "Rennes", false);
        adresse14.setLatitude("48.120825");
        adresse14.setLongitude("-1.690739");

        // Adresse 6
        Adresse adresse15 = new Adresse("5", "Rue de Vincennes", "35700", "Rennes", false);
        adresse15.setLatitude("48.117994");
        adresse15.setLongitude("-1.675127");

        // Adresse 7
        Adresse adresse16 = new Adresse("1", "Rue Jean Macé", "35700", "Rennes", false);
        adresse16.setLatitude("48.117218");
        adresse16.setLongitude("-1.673284");

        // Adresse 8
        Adresse adresse17 = new Adresse("15", "Boulevard de Sévigné", "35700", "Rennes", false);
        adresse17.setLatitude("48.116380");
        adresse17.setLongitude("-1.645404");

        // Adresse 9
        Adresse adresse18 = new Adresse("23", "Rue de la Palestine", "35700", "Rennes", false);
        adresse18.setLatitude("48.115255");
        adresse18.setLongitude("-1.667759");


        //List d'adresse
        List<Adresse> adresses10 = new ArrayList<Adresse>();
        adresses10.add(adresse10);
        adresses10.add(adresseArrive);

        //List d'adresse
        List<Adresse> adresses11 = new ArrayList<Adresse>();
        adresses11.add(adresse11);
        adresses11.add(adresseArrive);

        //List d'adresse
        List<Adresse> adresses12 = new ArrayList<Adresse>();
        adresses12.add(adresse12);
        adresses12.add(adresseArrive);

        //List d'adresse
        List<Adresse> adresses13 = new ArrayList<Adresse>();
        adresses13.add(adresse13);
        adresses13.add(adresseArrive);

        //List d'adresse
        List<Adresse> adresses14 = new ArrayList<Adresse>();
        adresses14.add(adresse14);
        adresses14.add(adresseArrive);

        //List d'adresse
        List<Adresse> adresses15 = new ArrayList<Adresse>();
        adresses15.add(adresse15);
        adresses15.add(adresseArrive);

        //List d'adresse
        List<Adresse> adresses16 = new ArrayList<Adresse>();
        adresses16.add(adresse16);
        adresses16.add(adresseArrive);

        //List d'adresse
        List<Adresse> adresses17 = new ArrayList<Adresse>();
        adresses17.add(adresse17);
        adresses17.add(adresseArrive);

        //List d'adresse
        List<Adresse> adresses18 = new ArrayList<Adresse>();
        adresses18.add(adresse18);
        adresses18.add(adresseArrive);

        // Client
        Client client10 = new Client("Julie", "Guégnaud", "julie.guegnaud@etudiant.univ-rennes1.fr", "gateau", "0675456342");
        client10.setAdresses(adresses10);

        Client client11 = new Client("Julie", "aa", "julie.guegnaudaa@univ-rennes1.fr", "gateau", "0675456342");
        client11.setAdresses(adresses11);

        Client client12 = new Client("Julie", "zz", "juliezz@etudiant.univ-rennes1.fr", "gateau", "0675456342");
        client12.setAdresses(adresses12);

        Client client13 = new Client("Julie", "ee", "julie.cee@etudiant.univ-rennes1.fr", "gateau", "0675456342");
        client13.setAdresses(adresses13);

        Client client14 = new Client("Julie", "rr", "julie.brr@etudiant.univ-rennes1.fr", "gateau", "0675456342");
        client14.setAdresses(adresses14);

        Client client15 = new Client("Julie", "vv", "julie.vvw@etudiant.univ-rennes1.fr", "gateau", "0675456342");
        client15.setAdresses(adresses15);

        Client client16 = new Client("Julie", "gg", "julie.ggd@etudiant.univ-rennes1.fr", "gateau", "0675456342");
        client16.setAdresses(adresses16);

        Client client17 = new Client("Julie", "nn", "julie.enn@etudiant.univ-rennes1.fr", "gateau", "0675456342");
        client17.setAdresses(adresses17);

        Client client18 = new Client("Julie", "ssd", "julie.fssd@etudiant.univ-rennes1.fr", "gateau", "0675456342");
        client18.setAdresses(adresses18);

        addClient(client10);
        addClient(client11);
        addClient(client12);
        addClient(client13);
        addClient(client14);
        addClient(client15);
        addClient(client16);
        addClient(client17);
        addClient(client18);
    }

    // TEST
    @GET
    @Path("/testDistance")
    @UnitOfWork
    public String testDistance() {
        return dao.getCluster().toString();
    }
}