import java.net.Socket;
import java.net.ServerSocket;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class BancoSkelleton
{
	BancoImpl b;
	//cmd => "operation;nib;amount"

	public static void main(String [] args) throws Exception
	{
		//Declaring port, initializing ServerSocket;
		int port = Integer.parseInt(args[0]);
        ServerSocket ss = new ServerSocket(port);
        int accounts = Integer.parseInt(args[1]);
        BancoImpl b = new BancoImpl(accounts);
        
        while(true){
            //Reading portion of server;       
            Socket cs = ss.accept();
            new ClientHandler(cs, b).start();
        }
	}
}

class ClientHandler extends Thread
{
	Socket cs;
	BancoImpl b;

	public ClientHandler(Socket cs, BancoImpl b)
	{
		this.cs = cs;
		this.b = b;
	}

	public void run()
	{
		try{
			//Declaring port and host, initializing ConnectionSocket, PrintWriter, BufferedReader, sum and n;
			PrintWriter out = new PrintWriter(cs.getOutputStream());
			BufferedReader in = new BufferedReader(new InputStreamReader(cs.getInputStream()));
			String res;
			
			while(true)
			{
				String cmd = in.readLine();
				if (cmd == null) break;
				Request_Handler r = new Request_Handler(cmd, this.b);
				res = r.handle_request();
				out.println(res);
				out.flush();
			}

			
			out.close();
			cs.close();
		}
		catch(Exception e1)
		{
			try
			{
				cs.close();
			}
			catch(IOException e2){}
		}
	}
}
