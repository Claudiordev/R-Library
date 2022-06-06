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
    private PrintWriter writer; //Write
    private BufferedReader reader; //Read
    private List<String> messageLog; //List of messages received
    private int timeout = 5000;

    public Connection(Socket socket, PrintWriter serverClient, BufferedReader clientServer) {
        this.client = socket;
        this.writer = serverClient;
        this.reader = clientServer;
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
    public PrintWriter getWriter() {
        return writer;
    }

    public void setWriter(PrintWriter writer) {
        this.writer = writer;
    }

    public BufferedReader getReader() {
        return reader;
    }

    public void setReader(BufferedReader reader) {
        this.reader = reader;
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
