import java.net.Socket;
import java.net.ServerSocket;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class SumServer
{

	public static void main(String [] args) throws Exception
	{
		//Declaring port, initializing ServerSocket;
		int port = Integer.parseInt(args[0]);
        ServerSocket ss = new ServerSocket(port);
        PublicCounter reg = new PublicCounter();
        
        while(true){
            //Reading portion of server;       
            Socket cs = ss.accept();
            new ClientHandler(cs, reg).start();
        }
	}
}

class ClientHandler extends Thread
{
	Socket cs;
	PublicCounter reg;

	public ClientHandler(Socket cs, PublicCounter reg)
	{
		this.cs = cs;
		this.reg = reg;
	}

	public void run()
	{
		try{
			//Declaring port and host, initializing ConnectionSocket, PrintWriter, BufferedReader, sum and n;
			PrintWriter out = new PrintWriter(cs.getOutputStream());
			BufferedReader in = new BufferedReader(new InputStreamReader(cs.getInputStream()));
			int sum = 0;
			int n = 0;
			int total = 0;
			while(true)
			{
				String s = in.readLine();
				if (s == null) break;
				int val = Integer.parseInt(s);
				sum += val;
				total = reg.add(val);
				n++;
				out.println(total);
				out.flush();
			}

			int avg = sum / n;
			out.println(avg);
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

class PublicCounter
{
	int sum;

	PublicCounter()
	{
		this.sum = 0;
	}

	synchronized int get_sum(){
		return this.sum;
	}

	synchronized int add(int val){
		sum += val;
		return sum;
	}
}
