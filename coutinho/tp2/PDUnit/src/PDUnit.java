import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class PDUnit{
    int id;
    int size;
    Bool more_fragments;
    int original_id;
    int offset;
    int type;
    int priority;
    int src_port;
    int dest_port;
    int src_id;
    int dest_id;
    String filename;
    BufferedReader data;


    public PDUnit(int id, int size, Bool more_fragments, int original_id, int offset, int type, int priority, int src_port, int dest_port, int src_id, int dest_id, String filename, File data){
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
        this.filename = filename;
        if(data == null && filename == null) this.data = null;
        else {
            try {
                this.data = new BufferedReader(new FileReader(filename));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}