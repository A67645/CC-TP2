
public class Cliente
{

           
        class TransfereCC{

        	<Packet>Arraylist line;
        	<line>Arraylist cache;
        	/*
				controlo de packets (fragmentação, montagem, controlo de erros, controlo de receção e envio de mensagens de erro)
        	*/
			class AgenteUDP{
        	/*
				Porção de envio e receção de packets e establecimento de conexao.
        	*/

        }

        }

	public static void main(String [] argv) throws IOException
	{
		//Declaring host and port, initializing ConnectionSocket, WriteBuffer and ReadBuffer;
		String host = argv[0];
        int src_port = system.console.readline().parseInt();
		DatagramSocket cs = new DatagramSocket(host, port);        
        PrintWriter out = new PrintWriter(cs.getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(cs.getInputStream()));
        BufferedReader data  = null;
        int user_id;
        int packet_id = 0;
        int dest_port

/*
public Packet(int id, int size, int more_fragments, int original_id, int offset, int type, int priority, int src_port, int dest_port, int src_id; int dest_id; String filename, File data)
*/

        while(true){

            //Reading portion of the client;       
            system.out.println("type");
            String s1 = System.console().readLine();
            if(s == null)
            {
            	break;
            }
            if(s == "connect")
            {
            	if(this.dest_port != 0)
            	{
            		system.out.println("already connected");
            		continue;
            	}

            	system.out.println("Port");
            	int port = system.console.readline().parseInt();
            	/*
				void packet_handler(int id, int size, int original_id, int offset, int type, int priority, int src_port, int dest_port, int src_id; int dest_id; String filename, File data)
            	*/
            	TrnasferCC.packet_handler(id++, 0, false, 0, 0, 0, 0, this.port, port, null, null);

            }

            if(s == "send")
            {
            	system.out.println("file name");
            	Sttring s2 = system.console.readline();
            	this.data  = new BufferedReader(new FileReader(s2));
            	/*
				void packet_handler(int size, int original_id, int type, int priority, int src_port, int dest_port, String filename, File data)
            	*/
            	TransfereCC.packet_handler(buf.size(), id++, 2, 1, this.src_port, this.dest_port, s2, null);
            }

            if(s == "request")
            {
            	system.out.println("filename");
            	Sttring s2 = system.console.readline();
            	/*
				void packet_handler(int size, int original_id, int type, int priority, int src_port, int dest_port, int src_id; int dest_id; String filename, File data)
            	*/
            	TransfereCC.packet_handler(buf.size(), id++, 2, 1, this.src_port, this.dest_port, 0, 0, null, null);
            }

            out.println(s);
            out.flush();

            //Writing portion of the client;
           
            String s2 = in.readLine();
            if(s1 == null) break;
            s1.equals("ola");
            System.out.println(s1);
            
                  
        }
            
        out.close();
        cs.close();
    }
}

