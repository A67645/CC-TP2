import java.net.Socket;
import java.net.ServerSocket;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class EchoClient
{
	public static void main(String [] argv) throws IOException
	{
		//Declaring host and port, initializing ConnectionSocket, WriteBuffer and ReadBuffer;
		String host = argv[0];
        int port = Integer.parseInt(argv[1]);
		Socket cs = new Socket(host, port);        
        PrintWriter out = new PrintWriter(cs.getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(cs.getInputStream()));
            
        while(true){
            //Reading portion of the client;       
            String s = System.console().readLine();
            if(s == null) break;
            out.println(s);
            out.flush();

            //Writing portion of the client;
            String s1 = in.readLine();
            if(s1 == null) break;
            s1.equals("ola");
            System.out.println(s1);         
        }
            
        out.close();
        cs.close();
    }
}