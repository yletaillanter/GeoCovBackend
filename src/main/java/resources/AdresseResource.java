package resources;

import com.google.common.base.Optional;
import core.Adresse;
import core.Client;
import db.AdresseDAO;
import db.ClientDAO;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by yoannlt on 07/01/2016.
 */
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/adresse")
public class AdresseResource {

    private AdresseDAO dao;

    public AdresseResource(AdresseDAO dao) {
        this.dao = dao;
    }

    @GET
    @Path("/all")
    @Produces("application/json")
    public List<Adresse> getAll() {
        return dao.findAll();
    }

    @GET
    @Path("/{id}")
    public Optional<Adresse> getAdresse(@PathParam("id") long id) {
        return dao.findById(id);
    }

    @POST
    @UnitOfWork
    public Adresse addAdresse(Adresse adresse) {
        //TODO verify if adress already exist
        if (dao.isExist(adresse)) {
            return adresse;
        } else {
            Adresse newAdresse = dao.create(adresse);
            return newAdresse;
        }
    }
}
