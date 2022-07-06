package connectivity.utils;

public interface Behaviours {

    /**
     * On receiving messages, if detected as an Action, handle it
     * @param action
     */
    void handleAction(Action action);

    /**
     * @return true if successfully reconnected
     */
    boolean reconnect();

    /**
     * Execute when the connection is detected as offline
     */
    void offline();

    /**
     * Execute when the connection is sucessfully reconnected
     */
    void reconnected();
}
