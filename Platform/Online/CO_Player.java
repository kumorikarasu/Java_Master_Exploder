
import java.awt.Color;
import java.awt.Graphics2D;
import java.net.InetAddress;
import java.util.Iterator;


class CO_Player extends C_Entity{
	int energy;
	int ID;
	C_Gun MainHand;
	C_Gun OffHand;
	int dir;
	String Nickname;
	int lasthp;
	float shootdir;
	boolean isShooting;
	int BulletTimer;
	boolean isSpawned;
	int maxhp;
	C_Sprite Sprite;
	InetAddress address;
	int Kills, Deaths;
	int Team;
	int Ping, PingDraw;
	int Caps;
	C_Flag EquippedFlag;
	
	CO_Player(int _ID, int _x, int _y, String name){
		entityID=103;
		x=_x;
		y=_y;
		width=32;
		height=32;
		ID=_ID;
		hp=100;
		maxhp=(int)hp;
		lasthp=100;
		MainHand = new C_M4((int)x, (int)y);
		MainHand.isEquipped=true;
		OffHand = null;
		Nickname=name;
		dir=1;
		BulletTimer=0;
		isSpawned=false;
		isDestroyable=true;
		Kills=0;
		Deaths=0;
		Team=0;
		Caps=0;
		Ping=0;
		PingDraw=0;
	}
	
	void UpdateScore(int kills, int deaths, int caps){
		Kills=kills;
		Deaths=deaths;
		Caps=caps;
	}
	
	void Spawn(C_Gun _MH, C_Gun _OH, int _x, int _y, C_Sprite _spr, int _team, String name){		
		x=_x;
		y=_y;
		lasthp=100;
		hp=100;
		MainHand=_MH;
		OffHand=_OH;
		Sprite=_spr;
		Team=_team;
		Nickname=name;
		if(MainHand!=null){
			C_Global.World.Create(MainHand);
			MainHand.isEquipped=true;
		}
		if(OffHand!=null){
			C_Global.World.Create(OffHand);
			OffHand.isEquipped=true;
		}
		isSpawned=true;
	}
	
	void SetXY(int _x, int _y){
		x=_x;
		y=_y;
	}
	
	void DropFlag(){
		if(EquippedFlag!=null){
			EquippedFlag.isEquipped=false;
			EquippedFlag.xv=xv;
		}
	}
	
	void Die(){
		isSpawned=false;
		hp=maxhp;
		if(MainHand!=null)
			MainHand.isDead=true;
		y=-32;
		Deaths++;
	}
	
	void ThrowNade(int _x, int _y, int dirx, int diry){
		C_Grenade asdf=new C_Grenade((int)_x, (int)_y, 60);
		asdf.Shoot(null, dirx, diry, 0);
		C_Global.World.Create(asdf);;
	}
	
	void Step(Iterator _it) {
		Ping++;
		if(isSpawned){
			if (MainHand!=null){
				MainHand.x = x+16-MainHand.width/2+(8*dir);
				MainHand.y = y+16;
				MainHand.dir=dir;
				MainHand.isHeld=true;
			}
			if (OffHand!=null){
				OffHand.x = x+16-MainHand.width/2+(16*dir);
				OffHand.y = y+12;
				OffHand.isHeld=true;
			}
			if (EquippedFlag!=null && EquippedFlag.isEquipped){
				EquippedFlag.x = x;
				EquippedFlag.y = y-32;
			}
			if (MainHand!=null && isShooting && BulletTimer==0){
				MainHand.Shoot(this, (int)x+16, (int)y+16, shootdir);
				BulletTimer=MainHand.GunSpeed;
			}
			if (BulletTimer>0) {
				BulletTimer--;
			}
			if(lasthp!=hp){
				CO_Client.Send.Send(""+ID+"11"+CO_Client.ID+(int)(lasthp-hp));
				lasthp=(int)hp;
			}		
		}
	}

	void Draw(Graphics2D g) {
		if (isSpawned){
			C_Global.drawSprite(g, Sprite, (int)x-C_Global.Camera_x, (int)y-C_Global.Camera_y, 32*dir, 32);
			g.setColor(Color.black);
			g.fillRect((int)x-C_Global.Camera_x,(int) y-C_Global.Camera_y-10,32,(int) 5);//((hp/maxhp)*32)
			try{
				g.setColor(new Color(255-(int)((hp/maxhp)*255),(int)((hp/maxhp)*255),0));
			}catch(java.lang.IllegalArgumentException e){
				
			}
			g.fillRect((int)x-C_Global.Camera_x,(int) y-C_Global.Camera_y-10,(int)((hp/maxhp)*32),(int) 5);//
			g.setColor(Color.black);
		}
	}
}

class CO_SortedPlayer{
	String Nickname;
	int Kills;
	int Deaths;
	int Score;
	int ID;
	int Team;
	int Caps;
	int Ping;
	int PingDraw;
	
	CO_SortedPlayer(int id, String name, int team,  int kills, int deaths, int caps, int ping, int pingdraw){
		ID=id;
		Nickname=name;
		Kills=kills;
		Deaths=deaths;
		Team=team;
		Caps=caps;
		Ping=ping;
		PingDraw=pingdraw;
	}
}
