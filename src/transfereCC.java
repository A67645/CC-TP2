class TransfereCC{

        <Packet>ArrayList line;
        <int, <Packet>ArrayList>HashMap cache;
        /*
	   controlo de packets (fragmentação, montagem, controlo de erros, controlo de receção e envio de mensagens de erro)
        */

        public TransfereCC(){
                this.line = new <Packet>Arraylist();
                this.cache = new <int, Packet>HashMap;
        }

        void insert(){

        }

        void remove(){

        }

        Boolean offset_check(int original_id){
                <Packet>ArrayList target = new <Packet>ArrayList(cache.get(original_id));

                for(Packet p : target){
                        
                }

        }

        void packet_handler(int size, int original_id, int type, int priority, int src_port, int dest_port, String filename, File data)                
        {

        }

}