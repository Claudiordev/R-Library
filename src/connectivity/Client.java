package connectivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private Connection connection;
    private BufferedReader reader;
    private PrintWriter writer;

    public Client(String ip, int port, boolean checkConnection) throws IOException {
        Socket socket = new Socket(ip,port);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(socket.getOutputStream(),true);

        if (checkConnection){
            new Thread(readMessages()).start();
            writer.write("pong");
        }
    }

    private Runnable readMessages() {
        return () -> {
            try {
                String message;
                while ((message = reader.readLine()) != null) {
                        if (message.equalsIgnoreCase("ping")) {
                        }

                        System.out.println("Client Message received: " + message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }
}
