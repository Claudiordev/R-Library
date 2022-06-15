package connectivity.utils;

import connectivity.Connection;

import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Process every message sent in between server and client
 */
public class Action {
    private String header;
    private List<String> arguments; //List of arguments of the message
    private Connection connection; //Connection object the message belongs to
    private String nonProccessedMessage; //Contains the non processed message

    public Action(String header, List<String> arguments, Connection connection, String nonProccessedMessage){
        this.header = header;
        this.arguments = arguments;
        this.connection = connection;
        this.nonProccessedMessage = nonProccessedMessage;
    }

    /**
     * Retrieve Header of Message, [0] at Arguments
     * @return Header
     */
    public String getHeader() {
        return header;
    }

    /**
     * Retrieve arguments of message, divided by <>
     * @return
     */
    public List<String> getArguments() {
        return arguments;
    }

    public String getNonProccessedMessage() {
        return nonProccessedMessage;
    }

    /**
     * @return Connection object respective to action
     */
    public Connection getConnection() {
        return connection;
    }

    public static List<String> processMessage(String message) {
        List<String> processedMessage = new Vector<>();
        Matcher matcher = Pattern.compile("<(.+?)>").matcher(message);

        while (matcher.find()) {
            processedMessage.add(matcher.group(1));
            System.out.println(matcher.group(1));
        }

        return processedMessage;
    }
}
