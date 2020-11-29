package Connectivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class Functions {

    /**
     * @return String with External IP Address (Router Address) of user calling function
     */
    public static String getExternalIP() {
        try {
            URL checkip = new URL("https://checkip.amazonaws.com");
            BufferedReader in = new BufferedReader(new InputStreamReader(checkip.openStream()));
            return in.readLine();
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * @return String with local IP Address
     */
    public static String getLocalIP() {
        try {
            DatagramSocket socket = new DatagramSocket();
            Throwable var1 = null;

            String var2;

            try {
                socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
                var2 = socket.getLocalAddress().getHostAddress();
            } catch (Throwable var12) {
                var1 = var12;
                throw var12;
            } finally {
                if (socket != null) {
                    if (var1 != null) {
                        try {
                            socket.close();
                        } catch (Throwable var11) {
                            var1.addSuppressed(var11);
                        }
                    } else {
                        socket.close();
                    }
                }

            }

            return var2;
        } catch (SocketException | UnknownHostException var14) {
            return null;
        }
    }
}
