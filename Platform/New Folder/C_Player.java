import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Iterator;


class C_Player extends C_Entity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	boolean noHud;

	boolean Up;
	boolean Left;
	boolean Right;
	boolean Throw;
	boolean Reload;
	int mouse_button;
	boolean canjump;
	int energy;
	int hp;
	int maxhp;
	C_Gun MainHand;
	C_Gun OffHand;
	int BulletTimer;
	int ReloadTimer;
	int dwShot;
	int dir;
	int lives;
	boolean click;
	public C_Gun SpawnWeapon;
	private boolean isSniper;
	int shootdir=0;

	float drawScope;
	public boolean isSpawned;
	int isShooting;
	int spawnTimer=30*5;
	String killerID="";
	int Kills, Deaths;
	int Team; //0-FFA, 1-Red, 2-Blue
	//Changalbe Options
	String Nickname;
	C_Sprite Sprite;
	protected int NewTeam;
	int Caps;
	int Ping;
	int PingDraw;


	private C_Flag EquippedFlag;



	public boolean yourDead;

	private float camx;

	private float camy;

	private float camxv;

	private float camyv;

	C_Player(){

		x=0;
		y=0;
		width=32;
		height=32;
		isCachable=true;
		canjump=false;
		isDestroyable=false;
		isSolid=false;
		hp=100;
		maxhp=100;
		SpawnWeapon = new C_M4(0,0);
		MainHand=SpawnWeapon;
		MainHand.isEquipped=true;
		OffHand = null;
		BulletTimer=0;
		ReloadTimer=100;
		dwShot=0;
		dir=1;
		Throw=false;
		lives=3;
		click=false;
		entityID=100;
		isSniper=true;
		isSpawned=false;
		isShooting=0;
		killerID="00";
		Nickname="Master Exploder";
		Sprite = C_SpriteList.RedPlayer;
		Kills=0;
		Deaths=0;
		Team=0;
		Caps=0;
		Ping=0;
		PingDraw=0;
	}

//	@Override
//	@Override
	void Step(Iterator _it) {
		Ping++;
		if (isSpawned){
			if (x > C_Global.World.CurrentLevel.width || x < -32 || y>C_Global.World.CurrentLevel.height+32){
				hp=0;
				killerID="00";
			}
			if (hp<=0) {
				yourDead=true;
				x=0;
				y=0;
				hp=100;
				Reload=false;
				ReloadTimer=100;	
				if(MainHand!=null)
					MainHand.isDead=true;
				spawnTimer=30*5;
				isSpawned=false;
				isShooting=0;
				CO_Client.Send.Send(CO_Client.ID+"12"+killerID);
				killerID="00";
				Team=NewTeam;

				if (EquippedFlag!=null){
					EquippedFlag.isEquipped=false;
					EquippedFlag.xv=xv;
					EquippedFlag=null;
				}
			}
			if (!yourDead){
				if (!isSniper){
					C_Global.Camera_x=(int)x-320;//640
					C_Global.Camera_y=(int)y-240;//480
					drawScope=0;
				}else{
					int xm = (int) ((Math.abs(C_Global.Mouse.x+x)-640)), ym=(int) ((Math.abs(C_Global.Mouse.y+y)-480));
					float camdir=C_Global.point_direction(camx, camy, xm, ym);
					float camdis=C_Global.point_distance(camx,camy,xm,ym);
					camxv=(float) (Math.cos(camdir))*camdis/16;
					camyv=(float) (Math.sin(camdir))*camdis/16;
					camx+=camxv;
					camy+=camyv;
					C_Global.Camera_x=(int) camx;//(int)(x-320+C_Global.Mouse.x)/2;
					C_Global.Camera_y=(int) camy;//(int)(y-240+C_Global.Mouse.y)/2;
					if (C_Global.World.playertomouse>400){
						if (C_Global.World.playertomouse<500){
							drawScope=1-Math.abs(((float)(C_Global.World.playertomouse-500))/100.0f);
						}else{
							drawScope=1;
						}
					}else{
						drawScope=0;
					}
				}

				if (C_Global.Camera_x<0) {
					C_Global.Camera_x=0;
				}
				if (C_Global.Camera_y<0) {
					C_Global.Camera_y=0;
				}
				if (C_Global.Camera_y>C_Global.World.CurrentLevel.height-480+32) {
					C_Global.Camera_y=C_Global.World.CurrentLevel.height-480+32;
				}
				if (C_Global.Camera_x>C_Global.World.CurrentLevel.width-640) {
					C_Global.Camera_x=C_Global.World.CurrentLevel.width-640;
				}
			}
			yv+=C_Global.Gravity;

			if (Left){
				xv-=1;
				dir=-1;
			}
			if (Right){
				xv+=1;
				dir=1;
			}
			if (Up && canjump){
				yv=-10;
				canjump=false;
			}

			if (Math.abs(xv)>0) {
				xv/=1.1;
			}
			if (Math.abs(xv)<0.9) {
				xv=0;
			}

			if (xv>8) {
				xv=8;
			}
			if (xv<-8) {
				xv=-8;
			}

			if (yv>12) {
				yv=12;
			}
			if (yv<-12) {
				yv=-12;
			}

			//Update Position
			x+=xv;
			y+=yv;


			//Point Collision Init
			canjump=false;
			float[][] point = new float[2][2];
			point[0][0]=x+16+(30*dir);
			point[0][1]=y+16;
			point[1][0]=0;
			point[1][1]=0;
			boolean[] isPoint = new boolean[2];
			isPoint[0]=false;
			isPoint[1]=false;
			//Collisions
			while(_it.hasNext()){
				C_Entity Entity = (C_Entity)_it.next ();

				if (Entity.entityID>=200 && Entity.entityID<300 || Entity.entityID==154){
					if (!(x+width < Entity.x || x > Entity.x+Entity.width)){
						if (!(y+height < Entity.y || y > Entity.y+Entity.height)){
							if(Entity.entityID==154){
								C_Flag TempFlag =(C_Flag) Entity;
								if (!TempFlag.isEquipped && TempFlag.red && Team==2 && EquippedFlag==null){
									EquippedFlag=TempFlag;
									EquippedFlag.isEquipped=true;
									CO_Client.send("17", "1");
								}else if (!TempFlag.isEquipped && !TempFlag.red && Team==1 && EquippedFlag==null){
									EquippedFlag=TempFlag;
									EquippedFlag.isEquipped=true;
									CO_Client.send("17", "2");
								}else if(TempFlag.red && Team==1){
									TempFlag.returnToSpawn=true;
									if (TempFlag.x==TempFlag.spawnx && TempFlag.y==TempFlag.spawny && EquippedFlag!=null){
										Caps++;
										CO_Client.send("18", "");
										System.out.println("You Capped a Flag!");
										EquippedFlag.returnToSpawn=true;
										EquippedFlag.isEquipped=false;
										EquippedFlag.x = EquippedFlag.spawnx;
										EquippedFlag.y = EquippedFlag.spawny;
										EquippedFlag=null;
									}
								}else if (!TempFlag.red && Team==2){
									TempFlag.returnToSpawn=true;
									if (TempFlag.x==TempFlag.spawnx && TempFlag.y==TempFlag.spawny && EquippedFlag!=null){
										Caps++;
										System.out.println("You Capped a Flag!");
										EquippedFlag.returnToSpawn=true;
										EquippedFlag.isEquipped=false;
										EquippedFlag.x = EquippedFlag.spawnx;
										EquippedFlag.y = EquippedFlag.spawny;
										EquippedFlag=null;
									}
								}
							}
							/*		C_Gun Temp = (C_Gun)Entity;
								if (Entity.entityID>=200 && Entity.entityID <250 && Entity!=MainHand && Entity!=OffHand && !Temp.isEquipped){ //One Handers
									if  (MainHand==null){
										MainHand=(C_Gun)Entity;
										MainHand.isEquipped=true;
										BulletTimer=0;
									}else if (OffHand==null && MainHand.entityID<250){
										OffHand=(C_Gun)Entity;
										OffHand.isEquipped=true;
									}

								}
								if (Entity.entityID>=250 && Entity.entityID <300 && Entity!=MainHand && OffHand==null && MainHand==null && !Temp.isEquipped){ //Two Handers
									MainHand=(C_Gun)Entity;
									MainHand.isEquipped=true;
									BulletTimer=0;
								}	
							 */
							//}
						}
					}
				}



				if (Entity.isSolid && Entity!=this){
					float Al,Ar,At,Ab,Asxh,Asyh,Axh,Ayh; // player
					float Bl,Br,Bt,Bb,Bsxh,Bsyh,Bxh,Byh; // brush

					float dx,dy,px,py; // difference

					//Axv = xv; Ayv = yv;
					//Bxv = Entity.x; Byv = Entity.y();

					Al = x; Ar = x+width; 
					At = y; Ab = y+height;
					Bl = Entity.x; Br = Entity.x+Entity.width; 
					Bt = Entity.y; Bb = Entity.y+Entity.height;

					// halfwidths
					Asxh = width / 2;
					Asyh = height / 2;
					Ayh = y + (height/2);
					Axh = x + (width/2);

					Bsxh = Entity.width / 2;
					Bsyh = Entity.height / 2;
					Byh = Bt + Bsyh;
					Bxh = Bl + Bsxh;

					// check if is inside
					if( Ar >= Bl && Al <= Br && Ab >= Bt && At <= Bb ){

						// difference in halfwidths
						dx = Axh-Bxh;
						// x penetration
						px = (Asxh+Bsxh)-Math.abs(dx);

						if(px>0){

							dy = Ayh-Byh;
							// y penetration
							py = (Asyh+Bsyh)-Math.abs(dy);		
							// both x and y penetration
							if(py>0){
								// does x have less penetration?
								if(px<py){
									// move left
									if(dx<0){
										x -= px;
										xv /= 2;
									}
									else{					
										x += px; // right
										xv /= 2;
									}
								}else{ // more y penetration
									if(dy<0){
										canjump=true;
										if (Entity.xv!=0) {
											x+=Entity.xv;
										}
										y -= py;
										yv /= 2;
									}
									else{
										y += py; // down
										yv /= 2;
									}
								}
							}
						}
					}
					//Point Collision
					for (int i=0;i<2;i++){
						if (point[i][0] != -1 && point[i][1] != -1){
							if (!(point[i][0] < Entity.x || point[i][0] > Entity.x+Entity.width)){
								if (!(point[i][1] < Entity.y || point[i][1] > Entity.y+Entity.height)){
									isPoint[i]=true;
									break;
								}
							}
						}
					}
				}
			}//End While
			
			if (mouse_button==0){
				mousedown=false;
			}
				
			if (mouse_button==3 && !mousedown){
				C_Grenade asdf=new C_Grenade((int)x, (int)y);
				asdf.Shoot(null, (int)C_Global.Mouse.x, (int)C_Global.Mouse.y, 0);
				C_Global.World.Create(asdf);
				mousedown=true;
			}

			if (MainHand!=null && (Reload || MainHand.ammo==0) &&  MainHand.ammo!=MainHand.clipsize){
				ReloadTimer-=1;
				BulletTimer=-1;
				if (ReloadTimer==0){
					Reload=false;
					BulletTimer=0;
					ReloadTimer=100;
					MainHand.ammo=MainHand.clipsize;
					if (OffHand!=null) {
						OffHand.ammo=OffHand.clipsize;
					}
				}
			}else{
				Reload=false;
				ReloadTimer=100;
			}

			if (EquippedFlag!=null){
				EquippedFlag.x = x;
				EquippedFlag.y = y-32;
			}


			if (MainHand!=null){
				MainHand.x = x+16-MainHand.width/2+(8*dir);
				MainHand.y = y+16;
				MainHand.isHeld=true;
			}else{
				if (BulletTimer==0) {
					BulletTimer=-1;
				}
			}

			if (OffHand!=null){
				OffHand.x = x+16-OffHand.width/2+(16*dir);
				OffHand.y = y+12;
				OffHand.isHeld=true;
			}

			if(EquippedFlag!=null && Throw && !isPoint[0]){
				EquippedFlag.x+=34*dir;
				EquippedFlag.isEquipped=false;
				EquippedFlag.xv=10*dir;
				EquippedFlag=null;
			}else
				Throw=false;

			if (mouse_button==1 && BulletTimer==0){
				click=false;
				if ((OffHand==null || dwShot==0)&& MainHand.ammo>0){
					if(!C_MainMenu.DWep){
						shootdir=MainHand.Shoot(this, (int)x+16, (int)y+16, C_Global.World.playerdirmouse);
						isShooting=1;
						BulletTimer=MainHand.GunSpeed;
						dwShot=1;
					}
				}else if (OffHand!=null && ((MainHand.ammo==0 && OffHand.ammo>0) || (dwShot==1 && OffHand.ammo>0))){
					shootdir=OffHand.Shoot(this, (int)x+16, (int)y+16, C_Global.World.playerdirmouse);
					isShooting=1;
					BulletTimer=MainHand.GunSpeed;
					dwShot=0;
				}
			}else// if(mouse_button==1){
				isShooting=0;
			//}
			if (BulletTimer>0) {
				BulletTimer--;
			}
			spawnTimer=30*5;
		}else{
			spawnTimer--;
		}
		if (CO_Client.Send!=null && !CO_Client.EXIT)
			CO_Client.Send.Update(isShooting, shootdir, spawnTimer);

	}

//	@Override
//	@Override
	void Draw(Graphics g) {
		if (isSpawned){
			//g.fillOval((int)x-C_Global.Camera_x, (int)y-C_Global.Camera_y, width,height);

			C_Global.drawSprite(g, Sprite, (int)x-C_Global.Camera_x, (int)y-C_Global.Camera_y, 32*dir, 32);

			if (!noHud){
				if (MainHand!=null) {
					MainHand.drawHUD(g);
				}
				if (OffHand!=null) {
					OffHand.drawHUD(g);
				}
			}
		}	
	}
}
