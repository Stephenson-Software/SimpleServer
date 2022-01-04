package SimpleServer.server;

/**
 * @author Daniel Stephenson
 */
public class ServerApp {

    public static void main(String[] args) {
        int port = 2000;
        MultiServer server = new MultiServer(port);
        server.start();
    }
}