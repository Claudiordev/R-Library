package connectivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Client {
    private Connection connection;


    private BufferedReader reader;
    private PrintWriter writer;

    public Client(String ip, int port, boolean checkConnection) throws IOException {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress(ip, port), 3000);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        writer = new PrintWriter(socket.getOutputStream(),true);


        new Thread(readMessages()).start();
    }

    private Runnable readMessages() {
        return () -> {
            try {
                String message;
                while ((message = reader.readLine()) != null) {
                        if (message.equals("ping")) {
                            writer.println("pong");
                            //writer.flush();
                        }

                        System.out.println("[CLIENT] Server Message received: " + message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }

    public BufferedReader getReader() {
        return reader;
    }

    public PrintWriter getWriter() {
        return writer;
    }
}
