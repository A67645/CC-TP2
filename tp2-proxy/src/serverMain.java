import java .net.SocketException;

public class serverMain {

    public static void main(String[] args) throws SocketException {
        Server server = new Server();
        server.start();
    }
}
