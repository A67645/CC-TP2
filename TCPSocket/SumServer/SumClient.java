import java.net.Socket;
import java.net.ServerSocket;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class SumClient
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
            //Reading portion of client;       
            String s = System.console().readLine();
            if(s == null) break;
            out.println(s);
            out.flush();
            String s1 = in.readLine();
            System.out.println(s1);

            //Writing portion of client;
        }
        
        //Cleanup portion of client;
        cs.shutdownOutput();
        String s = in.readLine();
        if (s != null) System.out.println(s);
        out.close();
        cs.close();
    }
}