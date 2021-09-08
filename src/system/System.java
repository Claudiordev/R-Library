package system;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class System {

    /**
     * @return long value with total of free bytes within system path "/"
     * <p>The returned number of unallocated bytes is a hint, but not
     * a guarantee, that it is possible to use most or any of these
     *  bytes.</p>
     */
    public static long getSystemFreeSpace() {
        return (new File("/")).getFreeSpace();
    }

    /**
     * @return long value with total of space in bytes, within system path "/"
     */
    public static long getSystemTotalSpace() {
        return (new File("/")).getTotalSpace();
    }

    /**
     * @return long value with total of usable free bytes within system path "/"
     */
    public static long getSystemUsableSpace() {
        return (new File("/")).getUsableSpace();
    }

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

    /**
     * @param classObj A object to be referenced, example usage "this"
     * @return File path of Jar file
     */
    public static String getJarFilePath(Object classObj) {
        Path path = null; //represent a system dependent file path
        try {
            path = Paths.get(classObj.getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
            return path.toString();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }
}
