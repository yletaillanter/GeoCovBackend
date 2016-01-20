package resources;

import com.google.common.base.Optional;
import core.Adresse;
import core.Client;
import db.ClientDAO;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

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
    public List<Client> getAll() {
        return dao.findAll();
    }

    @GET
    @Path("/{id}")
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
            return Response.ok("Client created.").type(MediaType.TEXT_HTML).build();
        }
    }

    @GET
    @Path("/checkEmail/{email}")
    @UnitOfWork
    public Boolean checkEmail(@PathParam("email") String email) {
        Client client = dao.findByEmail(email);
        return client != null;
    }

    // For test purpose
    @GET
    @Path("/addDummyclient")
    @UnitOfWork
    public void addDummyClients() {
        // Adresse 1
        Adresse adresse1 = new Adresse();
        adresse1.setNumero("263");
        adresse1.setRue("Avenue du Général Leclerc");
        adresse1.setCp("35000");
        adresse1.setVille("Rennes");
        adresse1.setLatitude("48.116408");
        adresse1.setLongitude("-1.638647");

        // Adresse 2
        Adresse adresse2 = new Adresse();
        adresse2.setNumero("1");
        adresse2.setRue("rue du père Grignon");
        adresse2.setCp("35000");
        adresse2.setVille("Rennes");
        adresse2.setLatitude("48.111807");
        adresse2.setLongitude("-1.687499");

        //List d'adresse
        List<Adresse> adresses = new ArrayList<Adresse>();
        adresses.add(adresse1);
        adresses.add(adresse2);

        // Client
        Client client1 = new Client("Julie", "Guégnaud", "julie.guegnaud@etudiant.univ-rennes1.fr", "gateau", "0675456342");
        addClient(client1);
    }
}