package resources;

import com.google.common.base.Optional;
import core.Adresse;
import core.Groupe;
import db.AdresseDAO;
import db.GroupeDAO;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by yoannlt on 07/01/2016.
 */
@Produces(MediaType.APPLICATION_JSON)
@Path("/groupe")
public class GroupeResource {

    private GroupeDAO dao;

    public GroupeResource(GroupeDAO dao) {
        this.dao = dao;
    }

    @GET
    @Path("/all")
    @Produces("application/json")
    @UnitOfWork
    public List<Groupe> getAll() {
        return dao.findAll();
    }

    @GET
    @Path("/{id}")
    @UnitOfWork
    public Optional<Groupe> getGroupe(@PathParam("id") long id) {
        return dao.findById(id);
    }

    @POST
    @UnitOfWork
    public Groupe addGroupe(Groupe groupe) {
        Groupe newGroupe = dao.create(groupe);
        return newGroupe;
    }

    @GET
    @Path("/test_create")
    @UnitOfWork
    public Groupe createTestGroupe() {
        Groupe newGroupe = dao.create(new Groupe());
        return newGroupe;
    }
}
