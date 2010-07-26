

import java.io.*;
import java.net.*;

import javax.swing.JTextArea;

public class CO_ServerSend extends Thread implements Runnable{

	protected DatagramSocket socket = null;
	byte[] buf = new byte[256];
	DatagramPacket packet;
	InetAddress group = InetAddress.getByName("230.0.0.5");
	JTextArea Out;
	int outport = 4455;
	int inport = 4445;
	
	public CO_ServerSend(JTextArea _Out, DatagramSocket _socket) throws IOException {
		Out = _Out;
		socket = _socket;
		buf="".getBytes();
		packet = new DatagramPacket(buf, buf.length);
	}

	public void run() {
		try {
			int[] ping = new int[CO_GameServer.maxClients];
			while(true){
				for (int a=11;a<CO_GameServer.maxClients;a++){
					String received = CO_GameServer.clientInfo[a];
					if (!received.equals("")){
					 	if(!received.equals("-1")){
							int Check = Integer.valueOf(received.substring(0,2)).intValue();
							int Msg = Integer.valueOf(received.substring(2,4)).intValue();
						
							if (Msg==07){
								DropClient(Check, received.substring(4));
							}else{
								Send(received);
								CO_GameServer.clientInfo[a]="";
								ping[a]=0;
								
							}
						}
					}else{
						ping[a]++;
						if (ping[a]>=2000){
							if (CO_GameServer.Clients[a]!=null)
								DropClient(a, CO_GameServer.Clients[a].Nickname+" timed out");
							else
								DropClient(a, "Unknown timed out");
						}
					}
				}
				Thread.sleep(10);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	boolean DropClient(int _id, String msg){
		if (_id<=10 || _id>=CO_GameServer.maxClients || CO_GameServer.clientInfo[_id].equals("-1")){
			if(Out!=null)
				Out.append("No Client with ID "+_id);
			else
				System.out.println("No Client with ID "+_id);
			return false;
		}
		Send(_id+"07"+msg);
		CO_GameServer.clientInfo[_id]="-1";
		CO_GameServer.Clients[_id]=null;
		if (Out!=null)
			Out.append("Client Dropped: "+_id+"\n");
		else
			System.out.println("Client Dropped: "+_id+"");
		return true;
	}
	
	boolean Send(String text){
		try {
			buf=new byte[256];
			buf = text.getBytes();
			//packet = new DatagramPacket(buf, buf.length, InetAddress.getByName("localhost"), outport);//CO_GameServer.Clients[a].address
			//socket.send(packet);
			for(int a=10;a<31;a++){
				if(CO_GameServer.Clients[a]!=null){
					packet = new DatagramPacket(buf, buf.length, CO_GameServer.Clients[a].address, outport);
					socket.send(packet);
				}
			}
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	boolean Send(String text, InetAddress ip){
		try {
			buf=new byte[256];
			buf = text.getBytes();
			packet = new DatagramPacket(buf, buf.length, ip, outport);//CO_GameServer.Clients[a].address
			socket.send(packet);
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	void ChangeLevel(String levelname){
		CO_GameServer.LevelName = levelname;
		for (int a=11;a<CO_GameServer.maxClients;a++){
			if(CO_GameServer.Clients[a]!=null){
				CO_GameServer.Clients[a].Kills=0;
				CO_GameServer.Clients[a].Deaths=0;
			}
		}
		Send("1014"+levelname);
	}
	
	String Command(String _c){
		_c.trim();
		if (_c.length()>=11){
			if (_c.substring(0,11).equals("sv_gravity ")){
				try{
					int grav = Integer.valueOf(_c.substring(11).trim()).intValue();
					if(grav<1 || grav > 999)
						return "Invalid Value";
					else{
						Send("10151"+grav);
						CO_GameServer.gravity=grav;
					}
					return "sv_gravity set to "+grav;
				}catch(NumberFormatException e){
					return "Invalid Value";
				}
			}
		}
		if (_c.length()>=9){
			if (_c.substring(0,9).equals("sv_teams ")){
				try{
					int value = Integer.valueOf(_c.substring(9,10)).intValue();
					if(value==0 || value==1){
						Send("10152"+value);
						CO_GameServer.Team=value;
						ChangeLevel(CO_GameServer.LevelName);
					}else
						return "Invalid Value";
						
					return "sv_teams set to "+value;
				}catch(NumberFormatException e){
					return "Invalid Value";
				}
			}
		}
		if (_c.length()>=7){
			if (_c.substring(0,7).equals("sv_ctf ")){
				int value = Integer.valueOf(_c.substring(7,8)).intValue();
				if(value==0 || value==1){
					Send("101521");
					Send("10153"+value);
					CO_GameServer.Team=1;
					CO_GameServer.ctf=value;
					ChangeLevel(CO_GameServer.LevelName);
				}else
					return "Invalid Value";
				
				return "sv_ctf set to "+value;
			}
		}
		if (_c.length()>=6){
			if (_c.substring(0,6).equals("level ")){
				if(C_FileIO.FileExists("Levels/"+_c.substring(6).trim()+".mel")){
					ChangeLevel(_c.substring(6).trim());
					return "Changing Level to "+_c.substring(6).trim();
				}else
					return "Level not found";
			}
		}
		if (_c.length()>=5){
			if (_c.substring(0,5).equals("swap ")){
				if(CO_GameServer.Team==0)
					return "Teams are not enabled";
				
				int temp;
				try{
					 temp = Integer.valueOf(_c.substring(5).trim()).intValue();
					 if(CO_GameServer.Clients[temp]!=null){
						 Send(temp+"16");
						 return "Client "+temp+" has been swapped to the opposite team";
					 }
				}catch(java.lang.ArrayIndexOutOfBoundsException e){
				}catch(NumberFormatException e){}
				return "Invalid Client ID";
			}
			if (_c.substring(0,5).equals("kill ")){
				int temp;
				try{
					 temp = Integer.valueOf(_c.substring(5).trim()).intValue();
					 if(CO_GameServer.Clients[temp]!=null){
						 Send(temp+"1210");
						 return "Killed Client "+temp;
					 }
				}catch(java.lang.ArrayIndexOutOfBoundsException e){
				}catch(NumberFormatException e){}
				return "Invalid Client ID";
			}
			if (_c.substring(0,5).equals("echo "))
				return _c.substring(5,_c.length());
			if (_c.substring(0,5).equals("kick ")){
				int temp;
				try{
					 temp = Integer.valueOf(_c.substring(5).trim()).intValue();
					 if(CO_GameServer.Clients[temp]!=null)
							DropClient(temp, CO_GameServer.Clients[temp].Nickname+" was kicked by server");
						else
							DropClient(temp, "Unknown was kicked by server");
					 return "Client "+temp+" Kicked From Server";
				}catch(java.lang.ArrayIndexOutOfBoundsException e){
				}catch(NumberFormatException e){}
				return "Invalid Client ID";
			}
		}
		if (_c.length()>=4){
			if (_c.substring(0,4).equals("info")){
				String ret="ID - Nickname - Kills - Deaths\n";
				int ppl=0;
				for(int a=10;a<31;a++){
					if(CO_GameServer.Clients[a]!=null){
						ppl++;
						ret+=a+" - "+CO_GameServer.Clients[a].Nickname+" - "+CO_GameServer.Clients[a].Kills+" - "+CO_GameServer.Clients[a].Deaths+"\n";
					}
				}
				return "Clients: "+ppl+"\n"+ret;
			}
			if (_c.substring(0,4).equals("say ")){
				Send("1000Server: "+_c.substring(4,_c.length()));
				return "Server: "+_c.substring(4,_c.length());
			}
			if (_c.substring(0,4).equals("ban ")){ 
				int temp;
				try{
					 temp = Integer.valueOf(_c.substring(4).trim()).intValue();
				}catch(NumberFormatException e){
					return "Invalid Client ID";
				}
				DropClient(temp, CO_GameServer.Clients[temp].Nickname+" was banned by server");
				CO_GameServer.banned.add(CO_GameServer.Clients[temp].address.toString().substring(CO_GameServer.Clients[temp].address.toString().indexOf("/")+1).trim());
				return "Client "+temp+" Banned From Server";
			}
		}
		
		return "Unknown Command";
	}

	void close(){
		socket.close();
	}
}
