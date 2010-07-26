
import java.io.*;
import java.net.*;

class CO_ClientSend{

	DatagramSocket outsocket;

	int outport = 4445;
	int inport = 4455;

	InetAddress serveraddress;
	int ID=CO_Client.ID;
	byte[] buf = new byte[256];
	String output;
	DatagramPacket packet;

	public CO_ClientSend(DatagramSocket _sock){
		try {
	//		System.out.println("Client Send Started");
			outsocket = _sock;
			serveraddress = InetAddress.getByName("99.236.225.103");//10.249.193.40");//10.249.193.138");10.249.193.41 //99.236.225.103 //99.236.225.103
			
			System.out.println("Attempting to connect to "+serveraddress+".");

			//Thread.sleep(1);
			output = ID+"06"+(int)CO_Client.Player.x+"!"+(int)CO_Client.Player.y+"@"+CO_Client.Player.Nickname;
			buf = output.getBytes();	
			
			System.out.println("Packet: "+buf+"");
			
			packet = new DatagramPacket(buf, buf.length, serveraddress, outport);
			
			outsocket.send(packet);
			
			
			C_Global.World.Player.isSpawned=false;
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void Update(int isShooting, int shootdir, int spawnTimer){
		if (!CO_Client.send.equals("")){
			Send(CO_Client.send);
			CO_Client.send="";
		}else if (CO_Client.ID!=-1){
			//buf=new byte[256];
			if(C_Global.World.Player.isSpawned && C_Global.SpawnSuccess==0){
				Send(CO_Client.ID+"05"+(int)CO_Client.Player.x+"!"+(int)CO_Client.Player.y+"@"+CO_Client.Player.dir+"#"+isShooting+"$"+shootdir+"%");	
			}else if((C_Global.World.Player.mouse_button==1 && spawnTimer<=0 && C_Global.World.inGame) || C_Global.SpawnSuccess>=20 ){
				int spawn = (int)(Math.random()*(C_Global.World.CurrentLevel.Spawns));
				CO_Client.Player.x=((int)C_Global.World.CurrentLevel.SpawnPoints[spawn].x);
				CO_Client.Player.y=((int)C_Global.World.CurrentLevel.SpawnPoints[spawn].y);
				
				CO_Client.Player.Team = CO_Client.Player.NewTeam;
				if(CO_Client.Player.Team==0 && C_Global.Team==1)
					CO_Client.Player.Team=1;
				if (CO_Client.Player.Team==1)
					CO_Client.Player.Sprite = C_SpriteList.RedPlayer;
				else if (CO_Client.Player.Team==2)
					CO_Client.Player.Sprite = C_SpriteList.BluePlayer;
				//if (glo.World.Player.MainHand!=null)
				//glo.World.Player.MainHand.isDead=true;
				C_Global.World.Player.Reload=true;
				C_Global.World.Player.ReloadTimer=0;
				C_Global.World.Player.BulletTimer=0;
				C_Global.World.Player.hp=100;
				C_Global.World.Player.yourDead=false;
				C_Global.World.Player.isSniper=false;
				C_Global.World.Player.MainHand = C_Global.World.Player.SpawnWeapon.Wep();
				C_Global.World.Player.MainHand.ammo=C_Global.World.Player.MainHand.clipsize;
				C_Global.World.Player.MainHand.isEquipped=true;
				if(C_Global.World.Player.MainHand.entityID==253)
					C_Global.World.Player.isSniper=true;
				C_Global.World.Create(C_Global.World.Player.MainHand);
				
				Send(CO_Client.ID+"04"+(int)CO_Client.Player.x+"!"+(int)CO_Client.Player.y+"@"+CO_Client.Player.dir+"#"+CO_Client.Player.MainHand.entityID+"$"+C_Global.SpriteID(CO_Client.Player.Sprite)+"%"+CO_Client.Player.Team+"^"+CO_Client.Player.Nickname+"&");	
				C_Global.World.Player.isSpawned=true;
				C_Global.SpawnSuccess=1;
			}else if (C_Global.SpawnSuccess>0){
				C_Global.SpawnSuccess++;
				C_Global.World.Player.isSpawned=false;
				if (C_Global.SpawnSuccess==20){
					System.out.println("Spawn Failed, Trying Again");
					if (C_Global.World.Player.MainHand!=null)
						C_Global.World.Player.MainHand.isDead=true;
					C_Global.SpawnRetries++;
					if (C_Global.SpawnRetries>=5){
						System.out.println("Failed to Connect to Server");
						C_Global.World.DestroyLevel();
						C_Global.World.inGame=false;
						CO_Client.EXIT=true;
					}
				}
			}
		}
	}
	public void Send(String text){
		try {
			buf=new byte[256];
			buf = (text).getBytes();
			packet=new DatagramPacket(buf, buf.length, serveraddress, outport);
			outsocket.send(packet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void Disconnect(){
		CO_Client.EXIT=true;
	}

};