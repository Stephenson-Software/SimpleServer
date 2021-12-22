package SimpleServer.server;

import SimpleServer.server.abs.AbstractMultiServerThread;

import java.net.Socket;

public class MSThread extends AbstractMultiServerThread {
    private Protocol protocol;

    public MSThread(Socket s) {
        super("MultiServerThread", s);
        initializeProtocol();
    }

    @Override
    public void run() {
        while (readNextLine()) {
            processInput();
            sendResponseToClient();

            if (getOutputLine().equals("END_OF_CONNECTION")) {
                disconnect();
                break;
            }
        }
    }

    private void initializeProtocol() {
        protocol = new SimpleServer.server.Protocol();
    }

    private void processInput() {
        setOutputLine(protocol.processInput(getInputLine()));
    }

    private void sendResponseToClient() {
        getOut().println(getOutputLine());
    }

}
