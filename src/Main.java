import connectivity.Client;
import connectivity.Server;

import java.io.IOException;

public class Main {

    public static void main(String args[]) throws IOException {
        String var = "OL";

        for (int i = 0; i < var.length();i++) {
            String value = "";
            String a = String.valueOf(var.charAt(i));
            value = value + a;

            System.out.println(a);
        }
    }
/*
    public static void client() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(3000);
                    //Client client = new Client("127.0.0.1", 3306,true);
                    Client client = TestClient.getInstance("127.0.0.1",3306,true);

                    Thread.sleep(3000);
                    //client.getWriter().println("Teste");
                    client.getSocket().close();

                } catch(IOException e){
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }*/
}
