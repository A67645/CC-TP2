import java.util.ArrayList;
import java.utlil.HashMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class TransferCC {
	ArrayList<PDUnit> line;

	public TransferCC(){
		this.line = new ArrayList<PDUnit>();
	}

	public void new_packet(PDUnit p){
		this.line.add(p);
	}

	public void remove_packet(int id){
		PDUnit p;
		for(PDUnit t : line){
			if(t.id == id){
				p  = t;
				break;
			}
		}

		line.remove(p);
	}

	public int is_complete(){
		int end_packet = 0;
		for(PDUnit p : line){
			if(p.more_fragments == false){
				end_packet = 1;
				break;
			}
		}

		if(line.size() == line.get(0).size && end_packet == 1){
			return 1;
		}

		if(line.size() == line.get(0).size && end_packet == 0){
			return -1;
		}
		else return 0;
	}

	public void fragment(BufferedReader br, int id, int type, int priority, int src_port, int dest_port, int src_id, int dest_id, String filename){
		ArrayList<String> list = new ArrayList<String>();
		String l;

		while((l = br.readLine()) != null){
    		list.add(l);
		}

		int os = 0;
		for(String t : list){
			PDUnit p = new PDUnit(id, list.size(), 1, id+os, os, type, priority, src_port, dest_port, src_id, dest_id, filename, l.get(os));
			new_packet(p);
			os++;
		}

		list.get(os-1).more_fragments = 0;

	}

	public PrintWriter reassemble(){
		PrintWriter file = new PrintWriter();

		int order = 0;
		for(PDUnit p : line){
			for(PDUnit t : line){
				if(t.offset == order){
					file.write(t.data);
				}
			}
			order++;
		}
	}

}
