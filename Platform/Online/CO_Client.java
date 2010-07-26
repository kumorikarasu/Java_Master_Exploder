import java.net.DatagramSocket;
import java.net.SocketException;

public class CO_Client {
	public static CO_Player[] Client = new CO_Player[31];
	public static C_Player Player = C_Global.World.Player;
	public static String send = "";
	public static int ID = -1;
	public static boolean EXIT=false;
	public static CO_ClientSend Send=null;
	
	public static void send(String msg, String text){
		Send.Send(ID+msg+text);
	}
	
	public static void Disconnect(){
		EXIT=true;
		send("07",CO_Client.Player.Nickname+" Disconnected");
	}
	
	public static void go(){
		for (int i=0;i<31;i++)
			Client[i]=null;
		
		try {
			DatagramSocket sock = new DatagramSocket(4455);

		
		Thread t = new Thread(new CO_ClientReceive(sock));
		t.start();
		Send = new CO_ClientSend(sock);
		
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		EXIT=false;
	}
	public static void main(String[] args){
		for (int i=0;i<31;i++)
			Client[i]=null;
		
		try {
			DatagramSocket sock = new DatagramSocket(4455);

		
		Thread t = new Thread(new CO_ClientReceive(sock));
		t.start();
		Send = new CO_ClientSend(sock);
		
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		EXIT=false;
	}
}
