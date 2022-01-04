package SimpleServer.server;

public class ServerApp {

    public static void main(String[] args) {
        MultiServer server = new MultiServer(2000);
        server.start();
    }
}