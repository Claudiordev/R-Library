package connectivity;

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
    private Connection connection;
    private BufferedReader reader;
    private PrintWriter writer;
    private List<String> messageLog = new Vector<>(); //List of messages received
    private String serverIp; //Store IP after connection
    private int serverPort;
    private boolean checkConnection;
    private boolean reconnecting = false;

    public Socket getSocket() {
        return socket;
    }

    private Socket socket;

    boolean log = true;

    private Reconnection reconnection;

    public interface Reconnection{
        boolean reconnect();
    }

    public Client(String ip, int port, boolean checkConnection, Reconnection reconnection) throws IOException {
        socket = new Socket();
        socket.connect(new InetSocketAddress(ip, port), 3000);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        writer = new PrintWriter(socket.getOutputStream(),true);

        this.serverIp = ip;
        this.serverPort = port;
        this.checkConnection = checkConnection;
        this.reconnection = reconnection;


        new Thread(readMessages()).start();

        if (checkConnection){
            new Thread(checkConnection()).start();
        }
    }

    //public abstract boolean reconnect(String ip, int port, boolean checkConnection);

    private Runnable readMessages() {
        return () -> {
            try {
                String message;
                while ((message = reader.readLine()) != null) {
                        messageLog.add(message);

                        if (message.equals("ping")) {
                            writer.println("pong");
                            //writer.flush();
                        }

                        if (log) System.out.println("[CLIENT] Server Message received: " + message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }

    private Runnable checkConnection() {
        return () -> {
            while (true) {
                try {
                    Thread.sleep(10000);
                    if (messageLog.contains("ping")) {
                        reconnecting = false;
                        messageLog.clear();
                        if (log) {
                            System.out.println("[CLIENT] Connection still established");
                        }
                    } else {
                        reconnecting = true;
                        if (log) {
                            System.out.println("[CLIENT] Connection lost, trying to reconnect...");
                        }

                        //reconnect(serverIp,serverPort,checkConnection);
                        if (reconnection.reconnect()) {
                            break;
                        }
                    }


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public BufferedReader getReader() {
        return reader;
    }

    public PrintWriter getWriter() {
        return writer;
    }

    public boolean isReconnecting() {
        return reconnecting;
    }

    public List<String> getMessageLog() {
        return messageLog;
    }
}
