package connectivity;

import java.io.IOException;
import java.net.*;

public class Functions {
    /**
     * Check if ip address is reachable
     * @param ipAddress to be checked
     * @return true if reachable, false if not reachable
     * @throws IOException if any error happen.
     */
    public static boolean isReachable(String ipAddress) throws IOException{
        if (InetAddress.getByName(ipAddress).isReachable(500)) {
            return true;
        }
        return false;
    }
}
