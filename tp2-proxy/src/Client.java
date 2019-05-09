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

/*
* public void fragment(BufferedReader br, int id, int type, int priority, int checksum, int src_id, int dest_id, String filename);
* public PDUnit(int id, int size, int original_id, int offset, int type, int priority, int checksum, int src_id, int dest_id, String filename, String data);
*/

public class Client extends Thread {

    private UDPAgent agent;

    public Client(int port) throws UnknownHostException, SocketException {
        this.agent = new UDPAgent(port);
    }

    @Override
    public void run() {
        int id = 0;
        Random rand = new Random();

        try {

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            while (true){

                String input = reader.readLine();

                if (input.equals("quit")){
                    System.out.println("Saindo\n");
                    break;
                }

                if(input.equals("upload")){
                    System.out.println("File Name:");
                    String filename = reader.readLine();

                    // Send data block (file);

                    BufferedReader file = new BufferedReader(new FileReader(filename));

                    TransferCC packs = new TransferCC();

                    int check = rand.nextInt(500);

                    packs.fragment(file, id, 1, 2, check, agent.get_client_id(), agent.get_server_id(), filename);

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

                    // Receive ACK.
                    DatagramPacket receivePacket = new DatagramPacket(this.agent.getReceiveData(), this.agent.getReceiveData().length);

                    this.agent.receivePacket(receivePacket);

                    ByteArrayInputStream in = new ByteArrayInputStream(agent.getReceiveData());
                    ObjectInputStream is = new ObjectInputStream(in);
                    PDUnit datagram2 = (PDUnit) is.readObject();

                    System.out.println("ACK for packet " + datagram2.id + " received.");

                }

                if(input.equals("download")){
                    System.out.println("File Name:");
                    String filename = reader.readLine();
                    PrintWriter file = new PrintWriter(filename);

                    // Send data block (DATA REQUEST);
                    int check = rand.nextInt(500);

                    PDUnit r = new PDUnit(id, 0, id, 0, 2, 1, check, agent.get_client_id(), agent.get_server_id(), filename, "");
                    ByteArrayOutputStream outputstream = new ByteArrayOutputStream();
                    ObjectOutputStream os = new ObjectOutputStream(outputstream);
                    os.writeObject(r);
                    byte[] buffer2 = outputstream.toByteArray();

                    this.agent.setSendData(buffer2);

                    DatagramPacket sendPacket = new DatagramPacket(this.agent.getSendData(), this.agent.getSendData().length, this.agent.getAddress(), 4445);

                    this.agent.sendPacket(sendPacket);

                    id++;

                    // Receive DATA REQUEST ACK;
                    DatagramPacket receivePacket = new DatagramPacket(this.agent.getReceiveData(), this.agent.getReceiveData().length);

                    this.agent.receivePacket(receivePacket);
                    ByteArrayInputStream in = new ByteArrayInputStream(agent.getReceiveData());
                    ObjectInputStream is = new ObjectInputStream(in);

                    PDUnit datagram2 = (PDUnit) is.readObject();
                    if(datagram2.type == 0){
                        System.out.println("ACK for packet " + datagram2.id + " received.");
                    }

                    // RECEIVE DATA;
                    boolean complete = false;
                    TransferCC transfer = new TransferCC();

                    while(complete == false){
                        DatagramPacket receiveDataPacket = new DatagramPacket(this.agent.getReceiveData(), this.agent.getReceiveData().length);

                        this.agent.receivePacket(receiveDataPacket);
                        ByteArrayInputStream dataIn = new ByteArrayInputStream(agent.getReceiveData());
                        ObjectInputStream dataIs = new ObjectInputStream(dataIn);

                        PDUnit datagram3 = (PDUnit) dataIs.readObject();

                        transfer.new_packet(datagram3);

                        if(transfer.is_complete() == 1){
                            complete = true;
                            transfer.reassemble(datagram3.filename);
                        }

                    }


                    // SEND DATA ACK;

                    int check2 = rand.nextInt(500);

                    PDUnit r2 = new PDUnit(id, 0, id, 0, 0, 1, check, agent.get_client_id(), agent.get_server_id(), "", "");
                    ByteArrayOutputStream outputstream2 = new ByteArrayOutputStream();
                    ObjectOutputStream dataOs = new ObjectOutputStream(outputstream2);
                    dataOs.writeObject(r2);
                    byte[] buffer3 = outputstream2.toByteArray();

                    this.agent.setSendData(buffer3);

                    DatagramPacket sendPacket2 = new DatagramPacket(this.agent.getSendData(), this.agent.getSendData().length, this.agent.getAddress(), 4445);

                    this.agent.sendPacket(sendPacket2);

                    id++;
                }               
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
