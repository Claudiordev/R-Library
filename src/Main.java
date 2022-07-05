import connectivity.Client;
import connectivity.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String args[]) throws IOException {

        new Gateway("192.168.1.198",3750,true);
        //client();

        //retrieveIps();
    }

    public static void client() {
        new Thread(() -> {
            try {
                Thread.sleep(3000);
                Client client = TestClient.getInstance("127.0.0.1",3750,true);

            } catch(IOException e){
                e.printStackTrace();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public static void retrieveIps() {
        List<String> ipList = new ArrayList<>();

            try {
                Process process = Runtime.getRuntime().exec("arp -a");
                process.waitFor();
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

                String ip = null;

                while ((ip = reader.readLine()) != null) {
                    System.out.println(ip);
                }
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
    }
}
