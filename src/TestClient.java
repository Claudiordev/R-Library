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
            };
            client = new TestClient(ip, port, checkConnection,reconnection);

            System.out.println(client);
        }

        return client;
    }

    public void destroyClient() {
        client = null;
    }
}
