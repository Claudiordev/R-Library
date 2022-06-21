import connectivity.Connection;
import connectivity.Server;
import connectivity.utils.Action;

import java.io.IOException;

public class Gateway extends Server {
    public Gateway(String s, int s1, boolean b) throws IOException {
        super(s,s1,b);
    }

    @Override
    public void handleConnection(Connection c) {
        System.out.println("Connection received: " + c.getClient().getInetAddress().getHostAddress());
    }

    @Override
    public void handleMessage(Connection c, String message) {
        System.out.println("[SERVER] Message From Client received from ip " + c.getClient().getInetAddress().getHostAddress() + " : " + message);
    }

    @Override
    public void handleAction(Action action) {
        Connection connection = action.getConnection(); //Get Connection of the one that sent the action

        switch (action.getHeader()) {
            case "reconnect":
                for (Action a: actions) {
                    if (!connection.getClient().isClosed()) connection.getWriter().println(a.getNonProccessedMessage());
                    System.out.println("RECONNECTED");
                }
                break;
                //Pager section
            case "pager":
                if (action.getArguments().get(1).equals("call")) {
                    //Pager Call Action
                    String number = action.getArguments().get(2); //Number of pager
                    System.out.println("PAGER CALLED " + number);
                }
                break;

            case "phone":
                if (action.getArguments().get(1).equals("call")) {
                    //Phone Call Action
                    String number2 = action.getArguments().get(2); //Number of phone
                    System.out.println("PHONE CALLED " + number2);
                }
                break;
        }
    }
}
