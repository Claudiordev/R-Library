package connectivity;

public class Address {
    private String ip;
    private int port = -1;

    //Constructor
    public Address(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    /**
     * @return String IP Port
     */
    public int getPort() {
        return port;
    }

    /**
     * Set IP Address Port
     * @param port int to be set
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * @return String IP Address
     */
    public String getIp() {
        return ip;
    }

    /**
     * Set IP Address Port
     * @param ip String to be set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * Override toString method from Object Class
     * if port null, returns complete ip address, if existent
     * return "ip:port"
     * @return
     */
    @Override
    public String toString(){
        if(port == -1) return ip;
        return ip + ":" + port;
    }
}
