package se.orjehag.antecedent;

import java.util.ArrayList;

/**
 * Created by erik on 31/03/16.
 */
public class Socket {

    public float x, y;
    private boolean value;
    public ArrayList<Socket> connectedTo = new ArrayList<>();

    public boolean getValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public void connectTo(Socket socket) {
        if (!connectedTo.contains(socket)) {
            connectedTo.add(socket);
            socket.connectTo(this);
        }
    }

    public void unconnectFrom(Socket socket) {
        if (connectedTo.contains(socket)) {
            connectedTo.remove(socket);
            socket.unconnectFrom(this);
        }
    }
}
