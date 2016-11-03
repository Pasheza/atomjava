package server.profile;

/**
 * Created by User on 23.10.2016.
 */
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.auth.Authentication;
import server.auth.Authorized;

import javax.ws.rs.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

@Path("/profile")
public class Profile {
    //private static final Logger log = LogManager.getLogger(Authentication.class);

    @Authorized
    @POST
    @Path("name")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response changename(
            @FormParam("name") String newname,
            @HeaderParam(HttpHeaders.AUTHORIZATION) String rawtoken){
        Long token = Long.parseLong(rawtoken.substring("Bearer".length()).trim());
        boolean flag = Authentication.changeName(token,newname);
        if (flag)
            return Response.ok("User changed profile name to " + newname).build();
        else
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
    }

}
