import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

/*
* size -> número de fragmentos relativos a um mesmo ficheiro correspondente;
* original_id -> id do packet 0 do ficheiro correspondente;
* type -> 0 = ack;
*      -> 1 = data;
*      -> 2 = data request;
* priority -> 0 = min (start and end of connection);
*          -> 1 = mid (data request and acknowledgment);
*          -> 2 = max (data);
*/

public class PDUnit{
    int id;
    int size;
    int original_id;
    int offset;
    int type;
    int priority;
    int checksum;
    /*
    int src_port;
    int dest_port;
    */
    int src_id;
    int dest_id;
    String filename;
    String data;


    public PDUnit(int id, int size, int original_id, int offset, int type, int priority, int checksum,/*int src_port, int dest_port, */int src_id, int dest_id, String filename, String data){
        this.id = id;
        this.size = size;
        this.original_id = original_id;
        this.offset	= offset;
        this.type = type;
        this.priority = priority;
        this.checksum = checksum;
        /*
        this.src_port = src_port;
        this.dest_port = dest_port;
        */
        this.src_id = src_id;
        this.dest_id = dest_id;
        this.filename = filename;
        this.data = data;
    }

    public PDUnit(PDUnit t){
        this.id = t.id;
        this.size = t.size;
        this.original_id = t.original_id;
        this.offset = t.offset;
        this.type = t.type;
        this.priority = t.priority;
        this.checksum = t.checksum;
        /*
        this.src_port = t.src_port;
        this.dest_port = t.dest_port;
        */
        this.src_id = t.src_id;
        this.dest_id = t.dest_id;
        this.filename = t.filename;
        this.data = t.data;
    }
}
