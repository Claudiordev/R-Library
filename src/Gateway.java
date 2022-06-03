import connectivity.Connection;
import connectivity.Server;

import java.io.IOException;

public class Gateway extends Server {
    public Gateway(String s, int s1, boolean b) throws IOException {
        super(s,s1,b);
    }

    @Override
    public void handleConnection(Connection c) {
        System.out.println("Connection received: " + c.getClient().getInetAddress().getHostAddress());
    }

    @Override
    public void handleMessage(Connection c, String message) {
        System.out.println("[SERVER] Message From Client received from ip " + c.getClient().getInetAddress().getHostAddress() + " : " + message);
    }
}
