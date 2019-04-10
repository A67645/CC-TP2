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

                for(int i = 0; i < target.size(); i++){
                        if(target[i].offset != i){
                                return False;
                        }
                }

                if(target[target.size() - 1].get_more_fragments() != False){
                        return False;
                }

                return True;
        }

        void packet_handler(int size, int original_id, int type, int priority, int src_port, int dest_port, String filename, File data)                
        {

        }

}