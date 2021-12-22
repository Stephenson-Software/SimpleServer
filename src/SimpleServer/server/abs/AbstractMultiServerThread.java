package SimpleServer.server.abs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public abstract class AbstractMultiServerThread extends Thread{
    private Socket socket = null;
    private PrintWriter out = null;
    private BufferedReader in = null;
    private String inputLine = null;
    private String outputLine = null;

    public AbstractMultiServerThread(String label, Socket s) {
        super(label);
        socket = s;
        initializeWriter();
        initializeReader();
    }

    public Socket getSocket() {
        return socket;
    }

    public PrintWriter getOut() {
        return out;
    }

    public BufferedReader getIn() {
        return in;
    }

    public String getInputLine() {
        return inputLine;
    }

    public void setOutputLine(String newOutputLine) {
        outputLine = newOutputLine;
    }

    public String getOutputLine() {
        return outputLine;
    }

    public abstract void run();

    public void disconnect() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean readNextLine() {
        try {
            return (inputLine = in.readLine()) != null;
        } catch (SocketException e) {
            System.out.println("Could not read next line. Client has disconnected.");
            return false;
        } catch (IOException e) {
            System.out.println("Could not read next line. IO Exception.");
            return false;
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
}