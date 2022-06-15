import connectivity.Client;

import java.io.IOException;

public class TestClient extends Client {
    private static Client client = null;

    public TestClient(String ip, int port, boolean checkConnection, Reconnection reconnection) throws IOException {
        super(ip, port, checkConnection,reconnection);
    }

    public static Client getInstance(String ip, int port, boolean checkConnection) throws IOException {
        if (client == null) {
            Reconnection reconnection = new Reconnection() {
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

    public void destroyClient() {
        client = null;
    }
}
