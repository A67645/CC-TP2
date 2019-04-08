
public class Cliente
{

	    class AgenteUDP{
        	/*
				Porção de envio e receção de packets e establecimento de conexao.
        	*/

        }
           
        class TransferCC{
        	/*
				controlo de packets (fragmentação, montagem, controlo de erros, controlo de receção e envio de mensagens de erro)
        	*/

        }

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
            
            if(s == null) break;
            
            out.flush();

            //Writing portion of the client;
           
            if(s1 == null) break;
            
                  
        }
            
        out.close();
        cs.close();
    }
}

