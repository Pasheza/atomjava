package server.auth;

/**
 * Created by User on 23.10.2016.
 */
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.Token;
import server.User;

import javax.ws.rs.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

@Path("/auth")
public class Authentication {
    private static final Logger log = LogManager.getLogger(Authentication.class);
    private static ConcurrentHashMap<User, String> credentials;
    private static ConcurrentHashMap<User, Token> tokens;
    private static ConcurrentHashMap<Token, User> tokensReversed;

    // curl -i
    //      -X POST
    //      -H "Content-Type: application/x-www-form-urlencoded"
    //      -H "Host: {IP}:8080"
    //      -d "login={Kappa}&password={Kappa}"
    // "{IP}:8080/auth/register"
    @POST
    @Path("register")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response register(@FormParam("user") String user,
                             @FormParam("password") String password) {

        if (user == null || password == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        User tempUser = new User(user);

        if (credentials.putIfAbsent(tempUser, password) != null) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
        log.info("Password", credentials.get(tempUser));
        log.info("New user '{}' registered", user);
        return Response.ok("User " + user + " registered.").build();
    }

    static {
        User tempadmin = new User("admin");
        credentials = new ConcurrentHashMap<>();
        credentials.put(tempadmin, "admin");
        tokens = new ConcurrentHashMap<>();
        Token temptoken = new Token(1L);
        tokens.put(tempadmin, temptoken);
        tokensReversed = new ConcurrentHashMap<>();
        tokensReversed.put(temptoken, tempadmin);
    }

    // curl -X POST
    //      -H "Content-Type: application/x-www-form-urlencoded"
    //      -H "Host: localhost:8080"
    //      -d "login=admin&password=admin"
    // "http://localhost:8080/auth/login"
    @POST//Сделать проверку на уникальность токена
    @Path("login")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response authenticateUser(@FormParam("user") String user,
                                     @FormParam("password") String password) {

        if (user == null || password == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        log.info("You are entering authorization");
        try {
            // Authenticate the user using the credentials provided
            User tempuser = new User(user);
            if (!authenticate(tempuser, password)) {
                log.info("You fucked up authenticate");
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
            log.info("You completed authenticate");
            // Issue a token for the user
            long token = issueToken(user);
            log.info("User '{}' logged in, token is '{}'", user, token);

            // Return the token on the response
            return Response.ok(Long.toString(token)).build();

        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @Authorized
    @POST
    @Path("logout")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response logout(@HeaderParam(HttpHeaders.AUTHORIZATION) String rawtoken) {
        Long token = Long.parseLong(rawtoken.substring("Bearer".length()).trim());
        Token temptoken = new Token(token);
        User tempuser = tokensReversed.get(temptoken);
        tokens.remove(tempuser);
        tokensReversed.remove(temptoken);
        log.info("User '{}' logged out", tempuser.getUserName());
        return Response.ok("User " + tempuser.getUserName() +  " logged out").build();
    }

    private boolean authenticate(User tempuser, String password) throws Exception {
        log.info("Password is '{}' and given pass is '{}' '{}'", credentials.get(tempuser), password, password.equals(credentials.get(tempuser)));
        return password.equals(credentials.get(tempuser));
}

    private Long issueToken(String user) {
        User tempuser = new User(user);
        Token token = tokens.get(tempuser);//Fixed
        //null if user is not logged, not null if logged
        if (token != null) {
            return token.getToken();
        }
        log.info("You completed if");
        Long randomtoken = ThreadLocalRandom.current().nextLong();
        Token temptoken = new Token(randomtoken);
        while (tokens.containsValue(temptoken)){                    //Проверка на уникальность токена
            randomtoken = ThreadLocalRandom.current().nextLong();
            temptoken = new Token(randomtoken);
        }
        tokens.put(tempuser, temptoken);
        tokensReversed.put(temptoken, tempuser);
        return randomtoken;
    }

    static void validateToken(String rawToken) throws Exception {
        Long token = Long.parseLong(rawToken);
        Token temptoken = new Token(token);
        if (!tokensReversed.containsKey(temptoken)) {
            throw new Exception("Token validation exception");
        }
        log.info("Correct token from '{}'", tokensReversed.get(temptoken).getUserName());
    }

    public static boolean changeName(Long token, String newname){
        User newuser = new User(newname);
        Token temptoken = new Token(token);
        if (credentials.get(newuser) == null) {
            User olduser = tokensReversed.get(temptoken);
            String password = credentials.get(olduser);
            credentials.remove(olduser);
            credentials.put(newuser, password);
            tokensReversed.replace(temptoken, newuser);
            tokens.remove(olduser);
            tokens.put(newuser, temptoken);
            log.info("User changed profile name from '{}' to '{}'", olduser.getUserName(), newuser.getUserName());
            return true;
        }
        else{
            log.info("There is user with this name already");
            return false;
        }
    }

    public static String getUsers(){
        Collection<User> temp = tokensReversed.values();
        ArrayList<String> list = new ArrayList<>();
        for (Object aTemp : temp) {
            list.add(aTemp.toString());
        }
        return list.toString();
    }
}
