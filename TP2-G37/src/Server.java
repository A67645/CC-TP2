import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.File;
import java.net.*;
import java.lang.*;
import java.io.Console;
import java.io.IOException;
import java.io.DataInputStream;
import java.nio.Buffer;
import java.util.Random;

public class Server extends Thread {

    private UDPAgent agent;

    public Server() throws SocketException {
        this.agent = new UDPAgent();
    }

    @Override
    public void run() {
        try {

            TransferCC ariving = new TransferCC();
            int id = 0;

            while(true) {
            	Random rand = new Random();
                // Receive packet
                DatagramPacket receivePacket = new DatagramPacket(agent.getReceiveData(), agent.getReceiveData().length);

                this.agent.receivePacket(receivePacket);

                // Convert byte[] into PDUnit.
                ByteArrayInputStream in = new ByteArrayInputStream(agent.getReceiveData());
                ObjectInputStream is = new ObjectInputStream(in);
                PDUnit datagram = (PDUnit) is.readObject();

                // Check if PDUnit is Data, Data request or ACK;

                // If ACK print ACK message;
                if(datagram.type == 0){
                    System.out.println("ACK for packet " + datagram.original_id + " recieved.");                    
                }

                // If Data Request fragment and send all packets;
                if(datagram.type == 3){
                    BufferedReader file = new BufferedReader(new FileReader(datagram.filename));

                    TransferCC packs = new TransferCC();

                    int check = rand.nextInt(500);

                    packs.fragment(file, id, 1, 2, check, agent.get_client_id(), agent.get_server_id(), datagram.filename);

                    id = id+packs.line.size()+1;

                    for(PDUnit p : packs.line){
                        
                        //convert p into byte[];
                        ByteArrayOutputStream outputstream = new ByteArrayOutputStream();
                        ObjectOutputStream os = new ObjectOutputStream(outputstream);
                        os.writeObject(p);
                        byte[] buffer = outputstream.toByteArray();


                        this.agent.setSendData(buffer);

                        DatagramPacket sendPacket = new DatagramPacket(this.agent.getSendData(), this.agent.getSendData().length, this.agent.getAddress(), 4445);

                        this.agent.sendPacket(sendPacket);
                    }

                    // Receive ACK for data sent.

                    DatagramPacket receivePacket2 = new DatagramPacket(this.agent.getReceiveData(), this.agent.getReceiveData().length);

                    this.agent.receivePacket(receivePacket2);

                    ByteArrayInputStream in2 = new ByteArrayInputStream(agent.getReceiveData());
                    ObjectInputStream is2 = new ObjectInputStream(in2);
                    PDUnit datagram2 = (PDUnit) is2.readObject();
                    
                    System.out.println("ACK for packet " + datagram2.id + " received.");

                }

                // If Data, store packet and check if all arived;

                if(datagram.type == 1){
                    ariving.new_packet(datagram);
                    if(ariving.is_complete() == 1){
                        ariving.reassemble(datagram.filename);

                        // Send ACK for data received
                        int check2 = rand.nextInt(500);

                        PDUnit r2 = new PDUnit(id, 0, id, 0, 0, 1, check2, agent.get_client_id(), agent.get_server_id(), "", "");
                        ByteArrayOutputStream outputstream2 = new ByteArrayOutputStream();
                        ObjectOutputStream dataOs = new ObjectOutputStream(outputstream2);
                        dataOs.writeObject(r2);
                        byte[] buffer3 = outputstream2.toByteArray();

                        this.agent.setSendData(buffer3);

                        DatagramPacket sendPacket2 = new DatagramPacket(this.agent.getSendData(), this.agent.getSendData().length, this.agent.getAddress(), 4445);

                        this.agent.sendPacket(sendPacket2);
                    }
                }
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}