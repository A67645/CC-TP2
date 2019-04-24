import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.File;
import java.io.IOException;


/*

 id -> id do packet

 size -> tamanho do packet

 more_fragments -> se é o último fragmento ou não

 original_id -> id do primeiro fragmento

 offset -> fragment offset

 type -> tipo de packet
 	0 -> connection request
 	1 -> data request
 	2 -> data packet
 	3 -> acknowledgment
 	4 -> end of connection
 	-1 -> connectio refused

 priority -> indice de prioridade da trama
 	1 -> low level
 	2 -> mid level
 	3 -> max level

 src_port -> porto da fonte do packet

 dest_port -> porto do destino do packet

 src_id -> idêntificação da fonte do packet

 dest_id -> idêntificação do destino do packet

 filename -> nome do ficheiro a ser enviado/pedido (pode ser null em packets de controlo)
 
 data -> ficherio propriamente dito
*/

public class Packet{
	int id;
	int size;
	boolean more_fragments;
	int original_id;
	int offset;
	int type;
	int priority;
	int src_port;
	int dest_port;
	int src_id;
	int dest_id;
	String filename;
	BufferedReader sent_file;
	BufferedWriter received_file;

	
	public Packet(int id, int size, boolean more_fragments, int original_id, int offset, int type, int priority, int src_port, int dest_port, int src_id, int dest_id, String filename){
		this.id = id;
		this.size = size;
		this.more_fragments = more_fragments;
		this.original_id = original_id;
		this.offset	= offset;
		this.type = type;
		this.priority = priority;
		this.src_port = src_port;
		this.dest_port = dest_port;
		this.src_id = src_id;
		this.dest_id = dest_id;

		if(filename == null) this.filename = null;
		else {
			this.filename = filename;
		}

		File sent_file = new File(filename);
		File received_file  = new File(sent_file.getParent(), filename);
		try{
			if(filename == null || type != 2) this.sent_file = null;
			else{
				this.sent_file = new BufferedReader(new FileReader(sent_file));
			}

			if(filename == null || type != 1) this.received_file = null;
			else{
				this.received_file = new BufferedWriter(new FileWriter(received_file));
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}


		
	}

	public void closeBuffers(){
		try{
			this.sent_file.close();
			this.received_file.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}

	}
}
