import java.util.ArrayList;
import java.util.HashMap;
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
				p = new PDUnit(t);
				line.remove(p);
				break;
			}
		}
	}

	public int is_complete(){
		if(line.size() == line.get(0).size){
			return 1;
		}

		if(line.size() > line.get(0).size){
			return -1;
		}
		else return 0;
	}

	public void fragment(BufferedReader br, int id, int type, int priority, int checksum, /*int src_port, int dest_port,*/ int src_id, int dest_id, String filename){
		ArrayList<String> list = new ArrayList<String>();
		String l;

		try{
			while((l = br.readLine()) != null){
    			list.add(l);
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}

		int os = 0;
		for(String t : list){
			PDUnit p = new PDUnit(id, list.size(), id+os, os, type, priority, checksum, /*src_port, dest_port,*/ src_id, dest_id, filename, t);
			new_packet(p);
			os++;
		}
	}

	public void reassemble(String filename){

		try{
			PrintWriter file = new PrintWriter(filename);
			int order = 0;
			for(PDUnit p : line){
				for(PDUnit t : line){
					if(t.offset == order){
						file.println(t.data);
					}
				}
				order++;
			}
			file.close();
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
	}

}
