

import java.awt.Color;
import java.io.*;
import java.net.*;
import java.util.Iterator;

import javax.swing.JTextArea;

public class CO_ServerReceive extends Thread implements Runnable{
	
	 
	protected DatagramSocket socket = null;
	byte[] buf = new byte[256];
	DatagramPacket packet = null;
	//InetAddress group = InetAddress.getByName("99.236.225.103"); //230.0.0.5
	JTextArea Out = null;
	int outport = 4455;
	int inport = 4445;
	
	public CO_ServerReceive(JTextArea _Out, DatagramSocket _socket) throws IOException {
		Out = _Out;
		socket = _socket;
		if (Out!=null)
			Out.append("Server Running # "+InetAddress.getLocalHost().toString()+"\n");
		else
			System.out.println("Server Receive Running");
		packet = new DatagramPacket(buf, buf.length);
	}

	public void run() {
		while(true){
			try {
				buf=new byte[256];
				packet = new DatagramPacket(buf, buf.length);
				if (socket!=null)
					socket.receive(packet);
				else
					System.out.println("OMFG NULL!!!!!");
	
				String received = new String(packet.getData());
				received.trim();
				int Check = Integer.valueOf(received.substring(0,2)).intValue();
				int Msg = Integer.valueOf(received.substring(2,4)).intValue();
				if (Check==-1){
					Iterator iter = CO_GameServer.banned.iterator();
					InetAddress address = packet.getAddress();
					boolean ban=false;
					while(iter.hasNext()){
						if (address.toString().substring(address.toString().indexOf("/")+1).trim().equals(iter.next())){
							if (Out!=null)
								Out.append(address.toString()+" Failed to Connect, Reason: Banned\n");
							else{
								System.out.println(packet.getAddress().toString()+" Failed to Connect, Reason: Banned");
							}
							buf=new byte[256];
							CO_GameServer.server.Send("1010", address);
						}
					}
					if (!ban)
						for (int a=11;a<CO_GameServer.maxClients;a++){
	
							if (CO_GameServer.clientInfo[a].equals("-1")){
								if (Out!=null)
									Out.append("Client Connected ID: "+a+" @ "+(packet.getAddress().toString())+"\n");
								else
									System.out.println("Client Connected ID: "+a+"");

								int x = Integer.valueOf(received.substring(4, received.indexOf("!"))).intValue();
								int y = Integer.valueOf(received.substring(received.indexOf("!")+1,received.indexOf("@"))).intValue();
								CO_GameServer.Clients[a]=new CO_Player(a,x,y,received.substring(received.indexOf("@")+1).trim());
								CO_GameServer.Clients[a].address=address;
								
								String output = a+"00"+"Client "+a+" Connected";
								CO_GameServer.clientInfo[a]=output;
								CO_GameServer.server.Send(a+"06"+received.substring(4));
								
								CO_GameServer.server.Send("10151"+CO_GameServer.gravity, address);
								CO_GameServer.server.Send("10152"+CO_GameServer.Team, address);
								CO_GameServer.server.Send("10153"+CO_GameServer.ctf, address);
								CO_GameServer.server.Send("1014"+CO_GameServer.LevelName, address);
								
								for (int b=11;b<CO_GameServer.maxClients;b++){
									if (!CO_GameServer.clientInfo[b].equals("-1") && a!=b){
										x = (int)CO_GameServer.Clients[b].x;
										y = (int)CO_GameServer.Clients[b].y;
										try{
										CO_GameServer.server.Send(b+"04"+x+"!"+y+"@"+CO_GameServer.Clients[b].dir+"#"+CO_GameServer.Clients[b].MainHand.entityID+"$"+C_Global.SpriteID(CO_GameServer.Clients[b].Sprite)+"%"+CO_GameServer.Clients[b].Team+"^"+CO_GameServer.Clients[b].Nickname+"&", address);
										}catch(java.lang.NullPointerException e){
											System.out.println(e.toString());
										}
										
									}
								}
								//Had to do this after so not get null pointers
								for (int b=11;b<CO_GameServer.maxClients;b++){
									if (!CO_GameServer.clientInfo[b].equals("-1") && a!=b){
										CO_GameServer.server.Send(b+"13"+CO_GameServer.Clients[b].Kills+"!"+CO_GameServer.Clients[b].Deaths+"@"+CO_GameServer.Clients[b].Caps+"#", address);
									}
								}
								break;
							}
							
						}
				}else{
					switch (Msg){
					case 00:
						Out.append(CO_GameServer.Clients[Check].Nickname+": "+received.substring(4, received.indexOf(0))+"\n");
						CO_GameServer.clientInfo[Check]=received;
						break;
					case 01:
						Out.append("Team Red - "+CO_GameServer.Clients[Check].Nickname+": "+received.substring(4, received.indexOf(0))+"\n");
						CO_GameServer.clientInfo[Check]=received;
						break;
					case 02:
						Out.append("Team Blue - "+CO_GameServer.Clients[Check].Nickname+": "+received.substring(4, received.indexOf(0))+"\n");
						CO_GameServer.clientInfo[Check]=received;
						break;
					case 04:
						int gunID = Integer.valueOf(received.substring(received.indexOf("#")+1,received.indexOf("$"))).intValue();
						C_Gun temp=null;
						switch(gunID){
						case 250:
							temp=new C_M4(0,0);
							break;
						case 251:
							temp=new C_M8(0,0);
							break;
						case 252:
							temp = new C_FlameThrower(0,0);
							break;
						case 253:
							temp=new C_Sniper(0,0);
							break;
						case 254:
							temp=new C_RPG(0,0);
							break;
						}
						CO_GameServer.Clients[Check].MainHand=temp;
						try{
							CO_GameServer.Clients[Check].Sprite=C_Global.IDSprite(Integer.valueOf(received.substring(received.indexOf("$")+1,received.indexOf("%"))).intValue());
						}catch(java.lang.StringIndexOutOfBoundsException e){
							CO_GameServer.Clients[Check].Sprite=C_Global.IDSprite(0);
						}
						CO_GameServer.Clients[Check].Nickname=received.substring(received.indexOf("^")+1,received.indexOf("&"));
					case 05:
						CO_GameServer.clientInfo[Check] = received;
						
						CO_GameServer.Clients[Check].x=Integer.valueOf(received.substring(4, received.indexOf("!"))).intValue();
						CO_GameServer.Clients[Check].y=Integer.valueOf(received.substring(received.indexOf("!")+1,received.indexOf("@"))).intValue();
						CO_GameServer.Clients[Check].dir=Integer.valueOf(received.substring(received.indexOf("@")+1,received.indexOf("#"))).intValue();;
						
						break;
					case 07:
						CO_GameServer.clientInfo[Check] = received;
						break;
					case 11:
						CO_GameServer.server.Send(received);
						break;//if problem delete this
					case 12:
						CO_GameServer.clientInfo[Check]=received;
						//CO_Client.ID+"12"+killerID
						int otherID=Integer.valueOf(received.substring(4,6)).intValue();
						if(otherID!=Check && CO_GameServer.Clients[otherID]!=null)
							CO_GameServer.Clients[otherID].Kills++;
						CO_GameServer.Clients[Check].Deaths++;
						
						break;
					case 17:
						CO_GameServer.server.Send(received);
						break;
					case 18:
						CO_GameServer.Clients[Check].Caps++;
						CO_GameServer.server.Send(received);
						break;
					case 22:
						CO_GameServer.server.Send(received);
						break;
					default:
						CO_GameServer.clientInfo[Check]=received;
						break;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	void close(){
		socket.close();
	}
}
