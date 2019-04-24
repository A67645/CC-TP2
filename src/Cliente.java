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
import java.io.InputStreamReader;

public class Cliente
{

	public static void main(String [] argv) throws IOException
	{
		//Declaring host and port, initializing ConnectionSocket, WriteBuffer and ReadBuffer;
		String host = argv[0];
		DatagramSocket cs = new DatagramSocket();
        int id = Integer.parseInt(argv[1]);
        int packet_id = 0;
        System.out.println("Port:");
        int port = Integer.parseInt(System.console().readLine());
        InetAddress host_addr = InetAddress.getLocalHost();

/*
public Packet(int id, int size, boolean more_fragments, int original_id, int offset, int type, int priority, int src_port, int dest_port, int src_id; int dest_id; String filename)
*/

        while(true){

            //Writing portion of the client;
           try{
            System.out.println("type command:");
            String s1 = System.console().readLine();
            if(s1 == null)
            {
                break;
            }
            if(s1 == "connect")
            {
                cs.connect(host_addr, port);
                Packet datagram = new Packet(packet_id++, 0, false, packet_id, 0, 0, 2, port, 0, id, 0, null);
                ByteArrayOutputStream outputstream = new ByteArrayOutputStream();
                ObjectOutputStream os = new ObjectOutputStream(outputstream);
                os.writeObject(datagram);
                byte[] buffer = outputstream.toByteArray();
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, host_addr, port);
                cs.send(packet);
                datagram.closeBuffers();

            }

            if(s1 == "send")
            {
                System.out.println("file name");
                String s2 = System.console().readLine();
                Packet datagram = new Packet(packet_id++, 0, false, packet_id, 0, 1, 2, port, 0, id, 0, s2);
                ByteArrayOutputStream outputstream = new ByteArrayOutputStream();
                ObjectOutputStream os = new ObjectOutputStream(outputstream);
                os.writeObject(datagram);
                byte[] buffer = outputstream.toByteArray();
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, host_addr, port);
                cs.send(packet);
                datagram.closeBuffers();
                
            }

            if(s1 == "request")
            {
                System.out.println("filename:");
                String s2 = System.console().readLine();
                Packet datagram = new Packet(packet_id++, 0, false, packet_id, 0, 1, 1, port, 0, id, 0, s2);
                ByteArrayOutputStream outputstream = new ByteArrayOutputStream();
                ObjectOutputStream os = new ObjectOutputStream(outputstream);
                os.writeObject(datagram);
                byte[] buffer = outputstream.toByteArray();
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, host_addr, port);
                cs.send(packet);
                datagram.closeBuffers();

                // Reading Portion of the client.
                DatagramPacket packet2 = new DatagramPacket(buffer, buffer.length);
                cs.receive(packet2);
                byte[] buffer2 = packet2.getData();
                ByteArrayInputStream in = new ByteArrayInputStream(buffer2);
                ObjectInputStream is = new ObjectInputStream(in);

                try{
                    Packet datagram2 = (Packet) is.readObject();
                    String s;
                    while((s = datagram2.sent_file.readLine()) != null){
                        datagram2.received_file.write(s);
                        datagram2.received_file.newLine();
                    }


                }
                catch(ClassNotFoundException e){
                    e.printStackTrace();
                }


            }

            if(s1 == "disconnect")
            {
                Packet datagram = new Packet(packet_id++, 0, false, packet_id, 0, 4, 3, port, 0, id, 0, null);
                ByteArrayOutputStream outputstream = new ByteArrayOutputStream();
                ObjectOutputStream os = new ObjectOutputStream(outputstream);
                os.writeObject(datagram);
                byte[] buffer = outputstream.toByteArray();
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, host_addr, port);
                cs.send(packet);
                datagram.closeBuffers();
                cs.close();
                break;
            }
            else{
                continue;
            }
           }
           catch(UnknownHostException e) {
                e.printStackTrace();
           }
           catch(SocketException e){
                e.printStackTrace();
           }
           catch(IOException e){
                e.printStackTrace();
           }
        }

    }
}

