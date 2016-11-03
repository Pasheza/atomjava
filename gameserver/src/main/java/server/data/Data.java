package server.data;

/**
 * Created by User on 23.10.2016.
 */
        import org.apache.logging.log4j.LogManager;
        import org.apache.logging.log4j.Logger;
        import server.auth.Authentication;

        import javax.ws.rs.*;
        import javax.ws.rs.core.Response;

@Path("/data")
public class Data {
    private static final Logger log = LogManager.getLogger(Data.class);

    @GET
    @Path("users")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/json")
    public Response getUsers(){
        log.info("Logged users requested.");
        return Response.ok("Logged users: " + Authentication.getUsers()).build();
    }
}
