package resources;

import db.AdresseDAO;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by yoannlt on 18/01/2016.
 */
@Path("/register")
public class registerResource {

    private AdresseDAO dao;

    public registerResource(AdresseDAO dao) {
        this.dao = dao;
    }

    @POST
    @Path("client")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public String post(@FormDataParam("client") String s) {
        return s;
    }
}
