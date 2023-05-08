package exercise.connections;

import exercise.TcpConnection;

// BEGIN
public class Disconnected implements Connection{
    private TcpConnection tcpConnection;

    public Disconnected(TcpConnection tcpConnection){
        this.tcpConnection = tcpConnection;
    }
    @Override
    public void connect() {
        tcpConnection.setStateConnection(new Connected(tcpConnection));
        System.out.println("connected");
    }

    @Override
    public String getCurrentState() {
        return "disconnected";
    }

    @Override
    public void write(String data) {
        System.out.println("Error! Connection not connected");
    }

    @Override
    public void disconnect() {
        System.out.println("Error! Connection already disconnected");
    }
}
// END
