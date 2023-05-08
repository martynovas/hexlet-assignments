package exercise.connections;

import exercise.TcpConnection;

// BEGIN
public class Connected implements Connection{
    private TcpConnection tcpConnection;

    public Connected(TcpConnection tcpConnection){
        this.tcpConnection = tcpConnection;
    }

    @Override
    public void connect() {
        System.out.println("Error! Connection already connected");
    }

    @Override
    public String getCurrentState() {
        return "connected";
    }

    @Override
    public void write(String data) {

    }

    @Override
    public void disconnect() {
        tcpConnection.setStateConnection(new Disconnected(tcpConnection));
        System.out.println("disconnected");
    }
}
// END
