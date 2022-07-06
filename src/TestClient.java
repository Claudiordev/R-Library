import connectivity.Client;
import connectivity.utils.Action;
import connectivity.utils.Behaviours;

import java.io.IOException;

public class TestClient extends Client {

    public TestClient(String ip, int port, boolean checkConnection, Behaviours reconnection) throws IOException {
        super(ip, port, checkConnection,reconnection);
    }

    public static Client getInstance(String ip, int port, boolean checkConnection) throws IOException {
        if (client == null) {
            Behaviours reconnection = new Behaviours() {
                @Override
                public void handleAction(Action action) {

                }

                @Override
                public boolean reconnect() {
                    client = null;

                    try {
                        client = TestClient.getInstance(ip, port, checkConnection);
                        return true;
                    } catch (IOException e){
                        e.printStackTrace();
                    }

                    return false;
                }

                @Override
                public void offline() {
                    System.out.println("OFFLINE");
                }

                @Override
                public void reconnected() {
                    System.out.println("RECONNECTED");
                }
            };

            client = new TestClient(ip, port, checkConnection,reconnection);
        }

        return client;
    }
}
