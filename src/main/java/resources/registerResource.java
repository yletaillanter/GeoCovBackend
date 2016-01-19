package resources;

import db.AdresseDAO;
import db.ClientDAO;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by yoannlt on 18/01/2016.
 */
@Path("/register")
public class registerResource {

    private ClientDAO dao;

    public registerResource(ClientDAO dao) {
        this.dao = dao;
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public String post(@FormDataParam("client") String s) {
        return s;
    }
}
