import java.net.Socket;
import java.net.ServerSocket;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/*

 id -> id do packet

 size -> tamanho do packet

 more_fragments -> se é o último fragmento ou não

 ioffset -> fragment offset

types
 	0 -> connection request
 	1 -> data request
 	2 -> data packet
 	3 -> end of connection
 	-1 -> connectio refused

 priority
 	0 -> low level
 	2 -> mid level
 	3 -> max level

filename -> nome do ficheiro a ser enviado/pedido (pode ser null em packets de controlo)
 
data -> ficherio propriamente dito

*/

public class Packet{
	int id;
	int size;
	Bool more_fragments;
	int offset;
	int type;
	int priority;
	int src_port;
	int dest_port;
	int src_id;
	int dest_id;
	String filename;
	File data;

	
	class Packet(int id, int size, int more_fragments, int offset, int type, int priority, int src_port, int dest_port, int src_id; int dest_id; String filename, File data){
		this.id = id;
		this.size = size;
		this.more_fragments = more_fragments;
		this.offset	= offset;
		this.type = type;
		this.priority = priority;
		this.src_port = src_port;
		this.dest_port = dest_port;
		this.src_id = src_id;
		this.dest_id = dest_id;
		this.filename = filename;
		this.data = new File(data);
	}
}
