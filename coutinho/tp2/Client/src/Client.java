import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.Buffer;

public class Client extends Thread {

    private UDPAgent agent;

    public Client(int port) throws UnknownHostException, SocketException {
        this.agent = new UDPAgent(port);
    }

    @Override
    public void run() {

        try {

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            while (true) {

                String input = reader.readLine();

                if (input.equals("quit")){
                    System.out.println("Saindo\n");
                    break;
                }

                this.agent.setSendData(input.getBytes());
                DatagramPacket sendPacket = new DatagramPacket(this.agent.getSendData(), this.agent.getSendData().length, this.agent.getAddress(), 4445);

                this.agent.sendPacket(sendPacket);

                DatagramPacket receivePacket = new DatagramPacket(this.agent.getReceiveData(), this.agent.getReceiveData().length);

                this.agent.receivePacket(receivePacket);

                String output = new String(receivePacket.getData(), 0, receivePacket.getLength());

                System.out.println(output);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}