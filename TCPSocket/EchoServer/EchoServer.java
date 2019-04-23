import java.net.Socket;
import java.net.ServerSocket;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * Write a description of class EchoServer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class EchoServer
{
    public static void main(String [] argv) throws IOException
    {
        
        int port = Integer.parseInt(argv[0]);
        ServerSocket ss = new ServerSocket(port);
        
        while(true){
            Socket cs = ss.accept();
            PrintWriter out = new PrintWriter(cs.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(cs.getInputStream()));
            
            while(true){                
                String s = in.readLine();
                if(s == null) break;
                out.println(s);
                out.flush();                
            }
            
            out.close();
            cs.close();
        }
    }
}
