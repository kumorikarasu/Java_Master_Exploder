

import java.io.IOException;
import java.util.Vector;

public class CO_LobbyServer{
	public static Vector Threads = new Vector();
	
	/*public static void main(String[] args){
		/*try{
			COG_ServerSend send = new COG_ServerSend(null, Threads);
			COG_ServerRecieve receive = new COG_ServerRecieve(null, Threads);
			System.out.println("TEST");
			Thread t=new Thread(send);
			t.start();
			t=new Thread(receive);
			t.start();
			System.out.println("TEST");
			
		}
		catch(IOException a){}
		
	}*/
	public static void main(String[] args){
		C_Server Server = new C_Server(1000);
		while(true){
			if(!Server.Step()){
				break;
			}
		}
		Server.Cleanup();
	}
}

