package exercise;
import exercise.connections.Connection;
import exercise.connections.Disconnected;

import java.util.List;
import java.util.ArrayList;

// BEGIN
public class TcpConnection implements Connection {
    private String ip;
    private int port;
    private Connection state = new Disconnected(this);

    public TcpConnection(String ip, int port){
        this.ip = ip;
        this.port = port;
    }
    public void setStateConnection(Connection stateConnection) {
        state = stateConnection;
    }

    @Override
    public void connect() {
        state.connect();
    }

    @Override
    public String getCurrentState() {
        return state.getCurrentState();
    }

    @Override
    public void write(String data) {
        state.write(data);
    }

    @Override
    public void disconnect() {
        state.disconnect();
    }
}
// END
