package resources;

import com.google.common.base.Optional;
import core.Client;
import db.ClientDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by yoannlt on 07/01/2016.
 */
@Produces(MediaType.APPLICATION_JSON)
@Path("/client")
public class ClientResource {

    private ClientDAO dao;

    public ClientResource(ClientDAO dao) {
        this.dao = dao;
    }

    @GET
    @Path("/all")
    @Produces("application/json")
    public List<Client> getAll() {
        return dao.findAll();
    }

    @GET
    @Path("/{id}")
    public Optional<Client> getUser(@PathParam("id") long id) {
        return dao.findById(id);
    }

    @POST
    public Client addClient(Client client) {
        Client newClient = dao.create(client);
        return newClient;
    }
}
