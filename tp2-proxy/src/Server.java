import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Server extends Thread {

    private UDPAgent agent;

    public Server() throws SocketException {
        this.agent = new UDPAgent();
    }

    @Override
    public void run() {
        try {
            while(true) {
                DatagramPacket receivePacket = new DatagramPacket(agent.getReceiveData(), agent.getReceiveData().length);

                this.agent.receivePacket(receivePacket);

                String output = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println(output);

                StringBuilder sb = new StringBuilder();
                sb.append("Recebi isto: ").append(output);

                /*for (int i = 0; i < 100; i++) {
                    sb.append(output).append(output).append(output);
                }*/

                this.agent.setSendData(sb.toString().getBytes());

                DatagramPacket sendPacket = new DatagramPacket(agent.getSendData(), agent.getSendData().length, receivePacket.getAddress(), receivePacket.getPort());

                this.agent.sendPacket(sendPacket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}