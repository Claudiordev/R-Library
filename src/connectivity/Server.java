package connectivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Vector;

public abstract class Server {
    final List<Connection> connections = new Vector<>(); //List of received connections
    Address address;
    ServerSocket serverSocket;
    static boolean log = true;

    //Default Constructor
    public Server() {}

    /**
     * @param ip of ServerSocket for requests receivable, ex: 127.0.0.1
     * @param port for the IP chosen from 1024 to 65535
     * @param acceptance New thread for acceptance of connections through acceptConnections Runnable, if true initiated within Constructor
     * @throws IOException
     */
    public Server(String ip,int port,boolean acceptance) throws IOException {
        this.address = new Address(ip,port);

        serverSocket = new ServerSocket(address.getPort());

        if(acceptance && serverSocket.isBound()){
            new Thread(acceptConnections()).start(); //Wait for new connections

            //new Thread(countConnections(5000)).start();
        }
    }

    /**
     * Listener for each connection receival
     */
    public abstract void handleConnection(Connection c);

    /**
     * Listener for each Message received
     * @param c Connection Object
     * @param message String received
     */
    public abstract void handleMessage(Connection c,String message);

    /**
     * Alter the state of Log state
     * @param log true to show log messages, false to not show
     */
    public static void setLog(boolean log) {
        Server.log = log;
    }

    /**
     * @return list of connections
     */
    public List<Connection> getConnections() {
        return connections;
    }

    /**
     * Remove Connection Object from connections list
     * @param c Connection object
     */
    public void removeClient(Connection c){
        synchronized (connections){
            connections.remove(c);
        }
    }

    /**
     * Write a message to a chosen connection
     * @param c Connection object
     * @param message to be sent to Connection in question
     */
    public void writeToClient(Connection c, String message){
        c.getServerClient().println(message);
    }

    /**
     * Runnable that receives new Socket connections from ServerSocket,
     * and add each Connection to the List;
     */
    public Runnable acceptConnections() {
        return () -> {
            if (log) {
                System.out.println("Server listening at ip " + address.getIp() + " and port " + address.getPort() + " , waiting for new connections...");
            }

            while (true) {
                try {
                    Socket localSocket = serverSocket.accept(); //Stop here the loop and wait for new Connection, reason that need to be used in new Thread
                    localSocket.setKeepAlive(true);

                    BufferedReader reader = new BufferedReader(new InputStreamReader(localSocket.getInputStream()));
                    PrintWriter writer = new PrintWriter(localSocket.getOutputStream(),true);
                    Connection c = new Connection(localSocket,writer,reader);

                    if (!connections.contains(c)) {
                        synchronized (connections){
                            connections.add(c);
                        }

                        new Thread(() -> handleConnection(c)).start(); //Handle Connection within separated Thread

                        startConnectionReceived(c);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    /**
     * Read message received within Connection object
     * @param c Connection object
     * @return Runnable to run within separated Thread
     */
    public Runnable readMessages(Connection c) {
        return () -> {
            try {
                String message;
                while ((message = c.getClientServer().readLine()) != null) {
                    c.addMessageLog(message);
                    handleMessage(c, message);
                }
            } catch (IOException e) {
                removeClient(c);
            }
        };
    }

    /**
     * Check connection by reading ping-pong messages within timeout time
     * defined on Connection object;
     * @param c Connection object
     * @return Runnable to be run within Separated Thread
     */
    public Runnable checkConnection(Connection c) {
        return () -> {
            while (true) {
                c.getServerClient().println("ping");

                if (!c.getMessageLog().contains("pong")){
                    if (connections.contains(c)) {
                        if (log) {
                            try {
                                if (c.getClient().isClosed()) c.getClient().close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            removeClient(c);

                            System.out.println("Client disconnected and removed");
                        }
                    }
                }

                c.clearMessageLog();
                try {
                    Thread.sleep(c.getTimeout());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    /**
     * @param millis milliseconds between each log of count of Connections
     * @return Runnable that will print count of connections established each second
     */
    public Runnable countConnections(int millis) {
        return () -> {
            if (log) {
                while (true) {
                    try {
                        System.out.println("Clients connected " + connections.size());
                        Thread.sleep(millis);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    /**
     * Example of Handle of new connection received
     * @param c Connection object of connection received
     */
    public void startConnectionReceived(Connection c) {
        c.addMessageLog("pong"); //Add first log message on Connection creation
        new Thread(readMessages(c)).start();
        new Thread(checkConnection(c)).start();
    }
}


