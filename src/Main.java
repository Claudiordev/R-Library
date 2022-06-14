import connectivity.Client;
import connectivity.Server;

import java.io.IOException;

public class Main {

    public static void main(String args[]) throws IOException {

        new Gateway("192.168.1.3",3750,true);

        client();
    }

    public static void client() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(3000);
                    Client client = TestClient.getInstance("192.168.1.3",3750,true);

                    Thread.sleep(3000);
                    client.getSocket().close();

                } catch(IOException e){
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
}
