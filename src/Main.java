import connectivity.Client;
import connectivity.Server;

import java.io.IOException;

public class Main {

    public static void main(String args[]) throws IOException {
        Server gateway = new Gateway("192.168.1.4",3306,true);

        //client();
    }

    public static void client() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(3000);
                    Client client = new Client("127.0.0.1", 3306,true);

                    Thread.sleep(1000);
                    client.getWriter().println("Teste");

                } catch(IOException e){
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
}
