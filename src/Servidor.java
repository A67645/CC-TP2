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
import java.io.FileInputStream;

public class Servidor
{
    public static void main(String [] argv) throws IOException
    {
        
        int port = Integer.parseInt(argv[0]);
        int server_id = Integer.parseInt(argv[1]);
        DatagramSocket ss = new DatagramSocket(port);
        byte[] buffer = new byte[2048];
        
        while(true){
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            ss.receive(packet);
            byte[] data = packet.getData();
            ByteArrayInputStream in = new ByteArrayInputStream(data);
            ObjectInputStream is = new ObjectInputStream(in);
            try{
                Packet datagram = (Packet) is.readObject();
                if(datagram.type == 0){
                    System.out.println("Client " + datagram.src_id + " has connected!");

                }
                if(datagram.type == 1){
                    // Send data
                    Packet datagram2 = new Packet(datagram.id+1, 0, false, datagram.id+1, 0, 1, 2, port, 0, server_id, 0, datagram.filename);
                    ByteArrayOutputStream outputstream = new ByteArrayOutputStream();
                    ObjectOutputStream os = new ObjectOutputStream(outputstream);
                    os.writeObject(datagram2);
                    byte[] buffer2 = outputstream.toByteArray();
                    InetAddress host_addr = packet.getAddress();
                    int dest_port = packet.getPort();
                    DatagramPacket packet2 = new DatagramPacket(buffer2, buffer2.length, host_addr, dest_port);
                    ss.send(packet2);
                    datagram2.closeBuffers();

                }
                if(datagram.type == 2){
                    // Store data
                    String s;
                    while((s = datagram.sent_file.readLine()) != null){
                        datagram.received_file.write(s);
                        datagram.received_file.newLine();
                    }

                }
                if(datagram.type == 4){
                    // close socket
                    System.out.println("Client " + datagram.src_id + " has disconnected!");
                    in.close();
                    is.close();
                    ss.close();
                    break;


                }
            }
            catch(ClassNotFoundException e){
                e.printStackTrace();
            }
        }
    }
}