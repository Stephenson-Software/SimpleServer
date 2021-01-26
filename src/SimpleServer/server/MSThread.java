package SimpleServer.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MSThread extends Thread {

    private Socket socket = null;
    private PrintWriter out = null;
    private BufferedReader in = null;
    private String inputLine;
    private String outputLine;

    private Protocol protocol;

    public MSThread(Socket s) {
        super("MultiServerThread");
        socket = s;
        initializeWriter();
        initializeReader();
        initializeProtocol();
    }

    public void run() {
        while (readNextLine()) {
            processInput();
            processOutput();

            if (outputLine.equals("over")) {
                break;
            }
        }
    }

    private boolean initializeWriter() {
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean initializeReader() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void initializeProtocol() {
        protocol = new SimpleServer.server.Protocol();
    }

    private boolean readNextLine() {
        try {
            return (inputLine = in.readLine()) != null;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void processInput() {
        outputLine = protocol.processInput(inputLine);
    }

    private void processOutput() {
        out.println(outputLine);
    }

}
