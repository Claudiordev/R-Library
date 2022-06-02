package connectivity;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Vector;

/**
 * Full Complement to a connection received in a ServerSocket Java Native Object;
 */
public class Connection {
    private Socket client;
    private PrintWriter serverClient; //Write
    private BufferedReader clientServer; //Read
    private List<String> messageLog; //List of messages received
    private int timeout = 5000;

    public Connection(Socket socket, PrintWriter serverClient, BufferedReader clientServer) {
        this.client = socket;
        this.serverClient = serverClient;
        this.clientServer = clientServer;
        this.messageLog = new Vector<>();
    }

    /**
     * @return Socket of Connection
     */
    public Socket getClient() {
        return client;
    }

    /**
     * Set the Socket
     * @param client Socket to be set
     */
    public void setClient(Socket client) {
        this.client = client;
    }

    /**
     * @return PrintWriter of Connection
     */
    public PrintWriter getServerClient() {
        return serverClient;
    }

    public void setServerClient(PrintWriter serverClient) {
        this.serverClient = serverClient;
    }

    public BufferedReader getClientServer() {
        return clientServer;
    }

    public void setClientServer(BufferedReader clientServer) {
        this.clientServer = clientServer;
    }

    public List<String> getMessageLog() {
        return messageLog;
    }

    public void addMessageLog(String message) {
        this.getMessageLog().add(message);
    }

    public void clearMessageLog() {
        this.getMessageLog().clear();
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

}
