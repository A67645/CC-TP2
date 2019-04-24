import java.net.SocketException;
import java.net.UnknownHostException;

public class clientMain {

    public static void main(String[] args) throws SocketException, UnknownHostException {
        Client client = new Client(5000);
        client.start();
    }
}
