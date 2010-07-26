import java.io.*;
import java.net.*;
class CO_ClientReceive implements Runnable{
	DatagramSocket socket;
	//InetAddress address;
	int outport = 4445;
	int inport = 4455;
	int ID;
	
	public CO_ClientReceive(DatagramSocket _sock){
	socket = _sock;
		//address = InetAddress.getByName(""); //230.0.0.5 99.236.225.103
		//socket.joinGroup(address);
		ID=-1;
	}
	
	@SuppressWarnings("deprecation")
	public void run(){
		try {
			while(!CO_Client.EXIT){
				byte[] buf = new byte[256];
				DatagramPacket packet = new DatagramPacket(buf, buf.length);
				socket.receive(packet);
				String received = new String(packet.getData());
				
				int Check = Integer.valueOf(received.substring(0,2)).intValue();
				int Msg = Integer.valueOf(received.substring(2,4)).intValue();
				if (ID==-1){
					if (Msg==06){
						ID = Integer.valueOf(received.substring(0,2)).intValue();
						CO_Client.ID=ID;
					//	System.out.println("ID: "+ID);
					}
				}else if (Check==10){
					switch (Msg){
					case 00:
					case 01:
					case 02:
						C_Global.World.ReceiveString="Server: "+received.substring(4, received.indexOf(0));
						break;
					case 07:
						CO_Client.EXIT=true;
						C_Global.World.DestroyLevel();
						C_Global.World.inGame=false;
					case 10:
						CO_Client.EXIT=true;
						C_Global.World.DestroyLevel();
						C_Global.World.ReceiveString="Failed To Connect, Reason: You are Banned";
						break;
					case 14:
						C_Global.World.inGame=false;
						C_Global.World.Player.isSpawned=false;
						for(int a=0;a<31;a++){
							CO_Client.Client[a]=null;
						}
						C_Global.World.DestroyLevel();
						C_Global.World.CreateLevel(received.substring(4, received.indexOf((char)0)));
						break;
					case 15:
						int varID=Integer.valueOf(received.substring(4,5)).intValue();
						if (varID==1)
							C_Global.Gravity=Integer.valueOf(received.substring(5,received.indexOf((char)0))).intValue()/100f;
						else if(varID==2){
							C_Global.Team=Integer.valueOf(received.substring(5,6)).intValue();
							C_MainMenu.Team2.isActive=false;
							C_MainMenu.Team1.isActive=false;
							C_MainMenu.Team0.isActive=false;
							C_MainMenu.TeamAuto.isActive=true;
						}else if(varID==3)
							if(Integer.valueOf(received.substring(5,6)).intValue()==1){
								C_Global.ctf=true;
							}else{
								C_Global.ctf=false;
							}

						break;
					}
				}else if (ID!=Check){
					int x,y,dir;
					if(CO_Client.Client[Check]!=null){
						CO_Client.Client[Check].PingDraw=CO_Client.Client[Check].Ping;
						CO_Client.Client[Check].Ping=0;
					}
					switch (Msg){
					case 00:
						C_Global.World.ReceiveString=CO_Client.Client[Check].Nickname+": "+received.substring(4, received.indexOf(0));
						break;
					case 01:
						if(CO_Client.Client[Check].Team==1)
							C_Global.World.ReceiveString="Red Team - "+CO_Client.Client[Check].Nickname+": "+received.substring(4, received.indexOf(0));
						break;
					case 02:
						if(CO_Client.Client[Check].Team==2)
							C_Global.World.ReceiveString="Red Team - "+CO_Client.Client[Check].Nickname+": "+received.substring(4, received.indexOf(0));
						break;
					case 04:
						x = Integer.valueOf(received.substring(4, received.indexOf("!"))).intValue();
						y = Integer.valueOf(received.substring(received.indexOf("!")+1,received.indexOf("@"))).intValue();
						dir = Integer.valueOf(received.substring(received.indexOf("@")+1,received.indexOf("#"))).intValue();
						int gunID = Integer.valueOf(received.substring(received.indexOf("#")+1,received.indexOf("$"))).intValue();
						int SpriteID = Integer.valueOf(received.substring(received.indexOf("$")+1,received.indexOf("%"))).intValue();
						int team = Integer.valueOf(received.substring(received.indexOf("%")+1,received.indexOf("^"))).intValue();
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
						if (CO_Client.Client[Check]==null){
							CO_Client.Client[Check] = new CO_Player(Check, x, y, received.substring(received.indexOf("^")+1,received.indexOf("&")));
							C_Global.World.Create(CO_Client.Client[Check]);
						}
						//if(!CO_Client.Client[Check].isSpawned){
						CO_Client.Client[Check].Spawn(temp, null, x, y, C_Global.IDSprite(SpriteID), team, received.substring(received.indexOf("^")+1,received.indexOf("&")));
						//}
						
						break;
					case 05:
						x = Integer.valueOf(received.substring(4, received.indexOf("!"))).intValue();
						y = Integer.valueOf(received.substring(received.indexOf("!")+1,received.indexOf("@"))).intValue();
						dir=Integer.valueOf(received.substring(received.indexOf("@")+1,received.indexOf("#"))).intValue();;
						int isShooting = Integer.valueOf(received.substring(received.indexOf("#")+1,received.indexOf("$"))).intValue();
						int shootdir = Integer.valueOf(received.substring(received.indexOf("$")+1,received.indexOf("%"))).intValue();
						if (CO_Client.Client[Check]!=null){
							CO_Client.Client[Check].SetXY(x, y);
							CO_Client.Client[Check].dir=dir;
							if (isShooting==1){
								CO_Client.Client[Check].isShooting=true;
								CO_Client.Client[Check].shootdir=(float)Math.toRadians(shootdir);
							}else
								CO_Client.Client[Check].isShooting=false;
						}else{
						//	System.out.println("WHY IS THIS HAPPENING");
						}
						break;
					case 06:
						x = Integer.valueOf(received.substring(4, received.indexOf("!"))).intValue();
						y = Integer.valueOf(received.substring(received.indexOf("!")+1,received.indexOf("@"))).intValue();
						CO_Client.Client[Check] = new CO_Player(Check, x, y, received.substring(received.indexOf("@")+1).trim());
						C_Global.World.Create(CO_Client.Client[Check]);
						break;
					case 07:
						if(CO_Client.Client[Check]!=null){
							if(CO_Client.Client[Check].MainHand!=null)
								CO_Client.Client[Check].MainHand.isDead=true;
							CO_Client.Client[Check].isDead=true;
							CO_Client.Client[Check] = null;
						}
						C_Global.World.ReceiveString=received.substring(4, received.indexOf((char)0));
						break;
					case 10:
						CO_Client.Client[Check] = null;
						C_Global.World.ReceiveString="Client "+Check+" Banned From Server";
						break;
					case 12:
						int otherID = Integer.valueOf(received.substring(4,6)).intValue();
						if(otherID==ID){
							C_Global.World.ReceiveString="You Killed "+CO_Client.Client[Check].Nickname;
							C_Global.World.Player.Kills++;
						}else if(otherID==00 || otherID==Check){
							C_Global.World.ReceiveString=CO_Client.Client[Check].Nickname+" Committed Suicide";
						}else if(otherID==10){
							C_Global.World.ReceiveString=CO_Client.Client[Check].Nickname+" Was Killed By Server";
						}else{
							C_Global.World.ReceiveString=CO_Client.Client[Check].Nickname+" Was Killed By "+CO_Client.Client[otherID].Nickname;
							CO_Client.Client[otherID].Kills++;
						}
						CO_Client.Client[Check].Die();
						break;
					case 13:
						int kills = Integer.valueOf(received.substring(4,received.indexOf("!"))).intValue();
						int deaths = Integer.valueOf(received.substring(received.indexOf("!")+1,received.indexOf("@"))).intValue();
						int caps = Integer.valueOf(received.substring(received.indexOf("@")+1,received.indexOf("#"))).intValue();
						CO_Client.Client[Check].UpdateScore(kills,deaths, caps);
						break;
					case 16:
						if(CO_Client.Client[Check]!=null)
							C_Global.World.ReceiveString=CO_Client.Client[Check].Nickname+" has been swapped to the opposite team";
						break;
					case 17:
						int flagID = Integer.valueOf(received.substring(4,5)).intValue();
						if(CO_Client.Client[Check]!=null){
							if(flagID==1)
								CO_Client.Client[Check].EquippedFlag=C_Global.red;
							else
								CO_Client.Client[Check].EquippedFlag=C_Global.blue;
							CO_Client.Client[Check].EquippedFlag.isEquipped=true;
						}
						break;
					case 18:
						if(CO_Client.Client[Check]!=null){
							CO_Client.Client[Check].Caps+=1;
							if(CO_Client.Client[Check].EquippedFlag!=null)
								CO_Client.Client[Check].EquippedFlag.toSpawn();
							CO_Client.Client[Check].EquippedFlag=null;
						}
						break;
					case 19:
						if(CO_Client.Client[Check]!=null){
							CO_Client.Client[Check].DropFlag();
						}
						break;
					case 22:
						C_Global.World.ReceiveString=CO_Client.Client[Check].Nickname+": Frag Out!";
						x = Integer.valueOf(received.substring(4, received.indexOf("!"))).intValue();
						y = Integer.valueOf(received.substring(received.indexOf("!")+1,received.indexOf("@"))).intValue();
						int dirx = Integer.valueOf(received.substring(received.indexOf("@")+1,received.indexOf("#"))).intValue();
						int diry = Integer.valueOf(received.substring(received.indexOf("#")+1,received.indexOf("$"))).intValue();
						if(CO_Client.Client[Check]!=null){
							CO_Client.Client[Check].ThrowNade(x, y, dirx, diry);
						}
						break;
					}
				}else{
					C_Global.World.Player.PingDraw=C_Global.World.Player.Ping;
					C_Global.World.Player.Ping=0;
					if (Msg==07){
						CO_Client.EXIT=true;
						C_Global.World.DestroyLevel();
						C_Global.World.inGame=false;
					}
					if (Msg==10){
						C_Global.World.ReceiveString="You have been Banned";
						CO_Client.EXIT=true;
						C_Global.World.DestroyLevel();	
					}
						
					switch(Msg){
						case 00:
							C_Global.World.ReceiveString=C_Global.World.Player.Nickname+": "+received.substring(4, received.indexOf(0));
							break;
						case 01:
							if(C_Global.World.Player.Team==1)
								C_Global.World.ReceiveString="Red Team - "+C_Global.World.Player.Nickname+": "+received.substring(4, received.indexOf(0));
							break;
						case 02:
							if(C_Global.World.Player.Team==2)
								C_Global.World.ReceiveString="Blue Team - "+C_Global.World.Player.Nickname+": "+received.substring(4, received.indexOf(0));
							break;
						case 04:
							C_Global.SpawnSuccess=0;
							C_Global.SpawnRetries=0;
							C_Global.World.Player.isSpawned=true;
							break;
						case 11:
							int dmg=Integer.valueOf(received.substring(6).trim()).intValue();
							int otherID=Integer.valueOf(received.substring(4,6)).intValue();
							C_Global.World.Player.hp-=dmg;
							C_Global.World.Player.killerID=""+otherID;
							//System.out.println("Took "+dmg+" Damage from "+CO_Client.Client[otherID].Nickname);
							break;
						case 12:
							otherID=Integer.valueOf(received.substring(4,6)).intValue();
							if(otherID==00 || otherID==ID){
								C_Global.World.ReceiveString="You Committed Suicide";
								C_Global.World.Player.Deaths++;
							}else if(otherID==10){
								C_Global.World.ReceiveString="You Were Killed by Server";
								C_Global.World.Player.MainHand.isDead=true;
								C_Global.World.Player.isSpawned=false;
								C_Global.World.Player.Deaths++;
							}else{
								C_Global.World.ReceiveString="You Were Killed by "+CO_Client.Client[otherID].Nickname;
								CO_Client.Client[otherID].Kills++;
								C_Global.World.Player.Deaths++;
							}
							break;
						case 16:
							C_Global.World.ReceiveString="You have swapped to the opposite team";
							C_Global.World.Player.MainHand.isDead=true;
							C_Global.World.Player.isSpawned=false;
							C_MainMenu.Team1.isActive=false;
							C_MainMenu.Team2.isActive=false;
							if(C_Global.World.Player.Team==1){
								C_MainMenu.Team2.isActive=true;
								C_Global.World.Player.Team=2;
								C_Global.World.Player.NewTeam=2;
							}else{
								C_MainMenu.Team1.isActive=true;
								C_Global.World.Player.NewTeam=1;
								C_Global.World.Player.Team=1;
							}
							break;
						case 22:
						//	C_Global.World.ReceiveString=C_Global.World.Player.Nickname+": Frag Out!";
							break;
					}
				}
			}
			CO_Client.Send.Send(ID+"07"+CO_Client.Player.Nickname+" Disconnected");
			socket.close();
			Thread.currentThread().stop();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

};