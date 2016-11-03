package server;

import org.jetbrains.annotations.NotNull;
import server.api.ApiServlet;
/**
 * Created by User on 23.10.2016.
 */
public class Server {
    public static void main(@NotNull String[] args) throws Exception {
        ApiServlet.start();
    }
}
