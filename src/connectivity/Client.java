package connectivity;

import connectivity.utils.Action;
import connectivity.utils.Behaviours;
import connectivity.utils.States;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Vector;

public abstract class Client {

    public static Client client; //Singleton object
    private Connection connection; //Connection object
    private List<String> messageLog = new Vector<>(); //List of messages received
    private String serverIp; //Store IP after connection
    private int serverPort; //Store Port of IP after connection
    private Behaviours behaviours; //Handler of behaviours such Reconnection, HandleAction
    private States connectionState = States.OFFLINE; //Store state of connection

    public Socket getSocket() {
        return socket;
    }
    private Socket socket;
    private Thread readMessages,checkConnections;
    boolean log = true;

    public Client(String ip, int port, boolean checkConnection, Behaviours behaviours) throws IOException {
        socket = new Socket();
        socket.connect(new InetSocketAddress(ip, port), 3000);

        this.serverIp = ip;
        this.serverPort = port;
        this.behaviours = behaviours;
        this.connection = new Connection(socket,new PrintWriter(socket.getOutputStream(),true),new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8)));

        messageLog.add("ping"); //Add "ping" message to don't start with error on checking connection state

        readMessages = new Thread(readMessages());
        readMessages.start();//Read messages on different thread

        //Check connection on a loop within a timeout between each loop
        if (checkConnection) {
            checkConnections = new Thread(checkConnection(connection.getTimeout()));
            checkConnections.start();
        }

    }

    //public abstract boolean reconnect(String ip, int port, boolean checkConnection);

    /**
     * Handle every message received
     * @return Runnable object ot be run on seperated Thread
     */
    private Runnable readMessages() {
        return () -> {
                try {
                    String message;
                    while ((message = connection.getReader().readLine()) != null && !Thread.interrupted()) {
                        messageLog.add(message);

                        //On received ping message, send pong back to complete check of connection
                        if (message.equals("ping")) {
                            connection.getWriter().println("pong");
                        }

                        if (message.contains("<") && message.contains(">")) {
                            List<String> processedAction = Action.processMessage(message);
                            Action action = new Action(processedAction.get(0),processedAction,connection,message);

                            behaviours.handleAction(action); //Handle every action received
                        }

                        if (log) System.out.println("[CLIENT] Server Message received: " + message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
        };
    }

    /**
     * Runnable that checks if the connection is still alive within a while loop
     * Each loop goes through a timeout in a sleep of a choosen time
     * @param timeout ms in between each loop
     * @return
     */
    private Runnable checkConnection(int timeout) {
        return () -> {
            while(!Thread.interrupted()) {
                try {

                    Thread.sleep(timeout); //Timeout time

                    if (messageLog.contains("ping")) {
                        connectionState = States.CONNECTED;

                        messageLog.clear();

                        if (log) System.out.println("[CLIENT] Connection still established");
                    } else {
                        connectionState = States.RECONNECTING;
                        behaviours.offline(); //Fire offline behaviour

                        if (log) System.out.println("[CLIENT] Connection lost, trying to reconnect...");

                        //reconnect(serverIp,serverPort,checkConnection);
                        if (behaviours.reconnect()) {
                            behaviours.reconnected(); //Fire reconnected behaviour
                            break;
                        }
                    }


                } catch (InterruptedException e) {
                    e.printStackTrace();
                    //If error caught, force interruption of Thread
                    //As it might caught errors, when interrupting outside when a Sleep is still going on this thread
                    checkConnections.interrupt();
                }
            }
        };
    }

    public List<String> getMessageLog() {
        return messageLog;
    }

    public Connection getConnection() {
        return connection;
    }

    /**
     * Destroy the Read and CheckConnection Thread of the object, cutting the connection on the serversocket
     *
     */
    public void destroy() {
        if (client != null) {
            checkConnections.interrupt();
            readMessages.interrupt();
            connectionState = States.DESTROYED;
        }

    }

    /**
     * Check if connection of this Client object is already destroyed or not
     * @return
     */
    public boolean isDestroyed() {
        if (connectionState.equals(States.DESTROYED)) return true;

        return false;
    }
}
