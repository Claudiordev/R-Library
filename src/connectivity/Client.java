package connectivity;

import connectivity.utils.Action;
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
    private Connection connection; //Connection object
    private List<String> messageLog = new Vector<>(); //List of messages received
    private String serverIp; //Store IP after connection
    private int serverPort; //Store Port of IP after connection

    private Behaviours behaviours;

    private States connectionState = States.OFFLINE; //Store state of connection

    public Socket getSocket() {
        return socket;
    }

    private Socket socket;

    boolean log = true;

    private Reconnection reconnection; //Handler of reconnection behaviour

    public interface Reconnection{
        /**
         * @return true if successfully reconnected
         */
        boolean reconnect();

        void offline();

        void reconnected();
    }

    public Client(String ip, int port, boolean checkConnection, Reconnection reconnection) throws IOException {
        socket = new Socket();
        socket.connect(new InetSocketAddress(ip, port), 3000);

        this.serverIp = ip;
        this.serverPort = port;
        this.reconnection = reconnection;
        this.connection = new Connection(socket,new PrintWriter(socket.getOutputStream(),true),new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8)));

        messageLog.add("ping"); //Add "ping" message to don't start with error on checking connection state

        new Thread(readMessages()).start();

        if (checkConnection) new Thread(checkConnection(connection.getTimeout())).start();

    }

    //public abstract boolean reconnect(String ip, int port, boolean checkConnection);

    private Runnable readMessages() {
        return () -> {
            try {
                String message;
                while ((message = connection.getReader().readLine()) != null) {
                        messageLog.add(message);

                        if (message.equals("ping")) {
                            connection.getWriter().println("pong");
                            //writer.flush();
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

    private Runnable checkConnection(int timeout) {
        return () -> {
            while (true) {
                try {

                    Thread.sleep(timeout); //Timeout time

                    if (messageLog.contains("ping")) {
                        connectionState = States.CONNECTED;

                        messageLog.clear();

                        if (log) System.out.println("[CLIENT] Connection still established");
                    } else {
                        connectionState = States.RECONNECTING;
                        if (log) System.out.println("[CLIENT] Connection lost, trying to reconnect...");
                        reconnection.offline(); //Fire offline behaviour

                        //reconnect(serverIp,serverPort,checkConnection);
                        if (reconnection.reconnect()) {
                            reconnection.reconnected(); //Fire reconnected behaviour
                            break;
                        }
                    }


                } catch (InterruptedException e) {
                    e.printStackTrace();
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
}
