import connectivity.Client;
import connectivity.Server;

import java.io.IOException;

public class Main {

    public static void main(String args[]) throws IOException {
        Server gateway = new Gateway("192.168.1.233",3500,true);

        client();
    }

    public static void client() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(3000);
                    Client client = new Client("192.168.1.233", 3500,true);


                } catch(IOException e){
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
}
