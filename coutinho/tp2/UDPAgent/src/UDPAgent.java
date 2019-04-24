import java.io.IOException;
import java.net.*;

public class UDPAgent {

    private DatagramSocket socket;
    private InetAddress address;
    private byte[] sendData;
    private byte[] receiveData;

    public UDPAgent(int port) throws UnknownHostException, SocketException {
        this.socket = new DatagramSocket(port);
        this.address = InetAddress.getLocalHost();
        this.sendData = new byte[512];
        this.receiveData = new byte[512];
    }

    public UDPAgent() throws SocketException {
        this.socket = new DatagramSocket(4445);
        this.sendData = new byte[512];
        this.receiveData = new byte[512];
    }

    public void setSocket(DatagramSocket socket){
        this.socket = socket;
    }

    public void setSendData(byte[] data){
        this.sendData = data;
    }

    public void setAddress(InetAddress address){
        this.address = address;
    }

    public void setReceiveData(byte[] data){
        this.receiveData = data;
    }

    public byte[] getReceiveData(){
        return this.receiveData;
    }

    public byte[] getSendData(){
        return this.sendData;
    }

    public InetAddress getAddress(){
        return this.address;
    }

    public void receivePacket(DatagramPacket packet) throws IOException {
        this.socket.receive(packet);
    }

    public void sendPacket(DatagramPacket packet) throws IOException {
        this.socket.send(packet);
    }

}
