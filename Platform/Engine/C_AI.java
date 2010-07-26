
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Iterator;

class C_AI extends C_AIBase{

	int patrol;
	short patroldirection;
	short dir;
	float target_direction;
	int detected,returntospawnswitch;
	boolean returntospawn;

	boolean[] pointtrue; //= new boolean[4];
	float[][] point;// = new float[6][2];
	float target_distance;

	C_Gun MainHand;
	C_Gun OffHand;
	int BulletTimer;
	int ReloadTimer;
	int dwShot;
	boolean Reload;
	private C_Node_AI_Jump JumpNode;
	private int changeback;

	C_AI(){
		this.x=0;
		this.y=0;
		this.width=32;
		this.height=32;
		this.xv=0;
		this.yv=0;
		this.isSolid=false;
		this.isDestroyable=true;
		this.dir=1;
		this.inView=false;
		this.pointtrue = new boolean[10];
		this.point = new float[6][2];
		this.canJump=false;
		this.entityID=101;
		this.MainHand=null;
		this.OffHand=null;
		this.BulletTimer=0;
		this.ReloadTimer=0;
		this.dwShot=0;
		this.Reload=false;
	};
	C_AI(int _x, int _y){		
		this.x=_x;
		this.y=_y;
		this.width=32;
		this.height=32;
		this.xv=0;
		this.yv=0;
		this.isSolid=false;
		this.isDestroyable=true;
		this.patrol=25;
		this.hp=10;
		this.dir=1;
		this.inView=false;
		this.detected=-1;
		this.returntospawn=false;
		this.pointtrue = new boolean[10];
		this.point = new float[6][2];
		this.canJump=false;
		this.isSpawnSet=false;
		this.entityID=101;
		this.MainHand=null;
		this.OffHand=null;
		this.BulletTimer=0;
		this.ReloadTimer=0;		
		this.dwShot=0;
		this.Reload=false;
	}


	//@Override
//	@Override
	void Step(Iterator _it){
		if (this.hp<0 || this.y>C_Global.World.CurrentLevel.height) {
			this.isDead=true;
		}

		this.yv+=C_Global.Gravity;

		if (this.xv>4) {
			this.xv=4;
		}
		if (this.xv<-4) {
			this.xv=-4;
		}

		if (this.yv>12) {
			this.yv=12;
		}
		if (this.yv<-12) {
			this.yv=-12;
		}

		this.y+=this.yv;
		this.x+=this.xv;

		//Cansee Check Init
		this.canSee=true;
		this.Target=C_Global.World.Player;
		this.target_distance = C_Global.point_distance(this.x+this.width/2, this.y+this.height/2, this.Target.x+this.Target.width/2, this.Target.y+this.Target.height/2);
		float[] apx = new float[24];
		float[] apy = new float[24];
		for (int i=0;i<24;i++){
			apx[i]=-1;
			apy[i]=-1;
		}
		this.target_direction = C_Global.point_direction(this.x+this.width/2, this.y+this.height/2, this.Target.x+this.Target.width/2, this.Target.y+this.Target.height/2);
		this.inView=false;
		if (this.dir==1){
			if (this.target_direction<0.7853 && this.target_direction>-0.7853){
				this.inView=true;
			}
		}else{
			if ((this.target_direction>2.3561 && this.target_direction<3.2) || (this.target_direction<-2.3561 && this.target_direction>-3.2)){
				this.inView=true;
			}
		}
		if (this.target_distance < 384 && this.inView){			
			for (int i=0;i<this.target_distance;i+=16){
				apx[i/16] = (float) (this.x+this.width/2+Math.cos(this.target_direction)*i);
				apy[i/16] = (float) (this.y+this.height/2+Math.sin(this.target_direction)*i);
			}
		}else{
			this.canSee=false;
		}

		//Cansee Check End
		//Point Collision Init
		for (int i=0;i<6;i++){
			this.point[i][0]=-1;//X
			this.point[i][1]=-1;//Y
			this.pointtrue[i]=false;
		}

//		this.point[0][0]=this.x+16+(20*this.dir);
//		this.point[0][1]=this.y+48;
//		this.point[1][0]=this.x+16+(65*this.dir);
//		this.point[1][1]=this.y-48;
//		this.point[2][0]=this.x+16+(16*this.dir);
//		this.point[2][1]=this.y-48;
//		this.point[3][0]=this.x+16+(65*this.dir);;
//		this.point[3][1]=this.y-80;
//		this.point[4][0]=this.x+16+(45*this.dir);
//		this.point[4][1]=this.y-48;

		this.canJump=false;
		this.JumpNode=null;
		while(_it.hasNext()) { //Begin Collision While
			C_Entity Entity = (C_Entity)_it.next ();

			if (Entity.entityID==152){ //AI can Jump?
				if (!(this.x+this.width < Entity.x || this.x > Entity.x+Entity.width)){
					if (!(this.y+this.height < Entity.y || this.y > Entity.y+Entity.height)){
						this.JumpNode=(C_Node_AI_Jump)Entity;
					}
				}
			}

			if (Entity.entityID>=200 && Entity.entityID<300){
				if (!(this.x+this.width < Entity.x || this.x > Entity.x+Entity.width)){
					if (!(this.y+this.height < Entity.y || this.y > Entity.y+Entity.height)){
						C_Gun Temp = (C_Gun)Entity;
						if (Entity.entityID>=200 && Entity.entityID <250 && Entity!=this.MainHand && Entity!=this.OffHand && !Temp.isEquipped){ //One Handers
							if  (this.MainHand==null){
								this.MainHand=(C_Gun)Entity;
								this.MainHand.isEquipped=true;
								this.BulletTimer=0;
							}else if (this.OffHand==null && this.MainHand.entityID<250){
								this.OffHand=(C_Gun)Entity;
								this.OffHand.isEquipped=true;
							}

						}
						if (Entity.entityID>=250 && Entity.entityID <300 && Entity!=this.MainHand && this.OffHand==null && this.MainHand==null && !Temp.isEquipped){ //Two Handers
							this.MainHand=(C_Gun)Entity;
							this.MainHand.isEquipped=true;
							this.BulletTimer=0;
						}		
					}
				}
			}

			if (Entity.entityID==150 && this.detected==-1) {
				if (!(this.x+this.width < Entity.x || this.x > Entity.x+Entity.width)) {
					if (!(this.y+this.height < Entity.y || this.y > Entity.y+Entity.height)){
						this.dir*=-1;
						this.x+=this.xv*-1;
						this.xv=0;
					}
				}
			}		

			if (Entity.isSolid && Entity!=this){
				float Al,Ar,At,Ab,Asxh,Asyh,Axh,Ayh; // player
				float Bl,Br,Bt,Bb,Bsxh,Bsyh,Bxh,Byh; // brush

				float dx,dy,px,py; // difference

				//Axv = xv; Ayv = yv;
				//Bxv = Entity.x; Byv = Entity.y();

				Al = this.x; Ar = this.x+this.width; 
				At = this.y; Ab = this.y+this.height;
				Bl = Entity.x; Br = Entity.x+Entity.width; 
				Bt = Entity.y; Bb = Entity.y+Entity.height;

				// halfwidths
				Asxh = this.width / 2;
				Asyh = this.height / 2;
				Ayh = this.y + (this.height/2);
				Axh = this.x + (this.width/2);

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
									this.x -= px;
									this.xv /= 2;
									this.dir*=-1;
								}
								else{					
									this.x += px; // right
									this.xv /= 2;
									this.dir*=-1;
								}
							}else{ // more y penetration
								if(dy<0){
									this.y -= py;
									if (yv>6 && detected!=300 && detected>0)
										dir*=-1;
									this.yv /= 2;
									this.canJump=true;
									if (!this.isSpawnSet){
										this.spawnx=(int)this.x;
										this.spawny=(int)this.y;
										this.isSpawnSet=true;
									}
								}else{
									this.y += py; // down
									this.yv /= 2;
								}
							}
						}
					}
				}

				//Cansee Check
				for (int i=0;i<24;i++){
					if (apx[i] != -1 && apy[i] != -1){
						if (!(apx[i] < Entity.x || apx[i] > Entity.x+Entity.width)){
							if (!(apy[i] < Entity.y || apy[i] > Entity.y+Entity.height)){
								this.canSee=false;
								break;
							}
						}
					} else {
						break;
					}
				}//End Cansee Check

				//Begin Point Check
				if (Entity.isSolid && Entity!=this){
					for (int i=0;i<6;i++){
						if (this.point[i][0] != -1 && this.point[i][1] != -1){
							if (!(this.point[i][0] < Entity.x || this.point[i][0] > Entity.x+Entity.width)){
								if (!(this.point[i][1] < Entity.y || this.point[i][1] > Entity.y+Entity.height)){
									this.pointtrue[i]=true;
								}
							}
						}	
					} //End Point Check
				}
			}
		}//End While

		if (this.Reload && this.MainHand!=null && this.MainHand.ammo!=this.MainHand.clipsize){
			this.ReloadTimer-=1;
			this.BulletTimer=-1;
			if (this.ReloadTimer==0){
				this.Reload=false;
				this.BulletTimer=0;
				this.ReloadTimer=100;
				this.MainHand.ammo=this.MainHand.clipsize;
				if (this.OffHand!=null) {
					this.OffHand.ammo=this.OffHand.clipsize;
				}
			}
		}else{
			this.Reload=false;
			this.ReloadTimer=100;
		}

		if (this.MainHand!=null){
			this.MainHand.x = this.x+16-this.MainHand.width/2+(8*this.dir);
			this.MainHand.y = this.y+16;
			this.MainHand.isHeld=true;
		}else{
			if (this.BulletTimer==0) {
				this.BulletTimer=-1;
			}
		}

		if (this.OffHand!=null){
			this.OffHand.x = this.x+16-this.MainHand.width/2+(16*this.dir);
			this.OffHand.y = this.y+12;
			this.OffHand.isHeld=true;
		}

		//Begin AI
		if (this.canSee){
			this.detected=300;
			//Shooting
			if (this.BulletTimer==0 && this.MainHand!=null){
				if ((this.OffHand==null || this.dwShot==0)&& this.MainHand.ammo>0){
					C_Bullet Bullet = new C_Bullet((int)x+16,(int)y+16, 5);
					Bullet.xv=(float) (Math.cos((this.target_direction))*15);
					Bullet.yv=(float) (Math.sin((this.target_direction))*15);
					Bullet.creator=this;
					C_Global.World.Create(Bullet);
					this.BulletTimer=this.MainHand.GunSpeed;
					this.MainHand.ammo-=1;
					this.dwShot=1;
				}else if (this.OffHand!=null && ((this.MainHand.ammo==0 && this.OffHand.ammo>0) || (this.dwShot==1 && this.OffHand.ammo>0))){
					C_Bullet Bullet = new C_Bullet((int)x+16,(int)y+16, 5);
					Bullet.xv=(float) (Math.cos((this.target_direction))*15);
					Bullet.yv=(float) (Math.sin((this.target_direction))*15);
					Bullet.creator=this;
					C_Global.World.Create(Bullet);
					this.BulletTimer=this.MainHand.GunSpeed;
					this.OffHand.ammo-=1;
					this.dwShot=0;
				}else if(this.MainHand.ammo<=0){
					this.Reload=true;
				}
			}
			/*C_Bullet Bullet = new C_Bullet();
			Bullet.x=x+16;
			Bullet.y=y+16;
			Bullet.xv=(float) (Math.cos((target_direction))*15);
			Bullet.yv=(float) (Math.sin((target_direction))*15);
			Bullet.creator=this;
			glo.World.Create(Bullet);*/
		}

		if (JumpNode!=null && canJump){
			canJump=false;
			if ((JumpNode.ismandatory && this.detected<=0) && JumpNode.dir == this.dir){
				this.yv=-10f;
			}else if (JumpNode.dir == this.dir){
				if (this.Target.y-5<this.y){
					this.yv=-10f;
				}
				if (Math.round(Math.random())==1 && this.Target.y==this.y){
					this.yv=-10f;
				}
			}
		}

		if (this.detected==-1){
			if (this.MainHand!=null && this.MainHand.ammo<this.MainHand.clipsize)
				this.Reload=true;
//			if (this.canJump){
//				if (!this.pointtrue[0]){
//					this.yv-=12f;
//				}
//				if (!this.pointtrue[2] && this.pointtrue[1] && !this.pointtrue[3] && !this.pointtrue[4]){
//					this.yv-=12f;
//				}
//			}
			this.xv+=(this.dir*2);
		}else if (this.detected>0){
			if (this.canJump){
//				if (this.Target.y<=this.y){
//					if (!this.pointtrue[0]){
//						this.yv-=12f;
//					}	
//				}if (this.Target.y<this.y){
//					if (!this.pointtrue[2] && this.pointtrue[1] && !this.pointtrue[3] && !this.pointtrue[4]){
//						this.yv-=12f;
//					}
//				}
				this.xv/=1.1;
				if (Math.abs(this.xv)<0.5) {
					this.xv=0;
				}
			}
			if (this.canSee || this.detected>285){
				if (this.Target.x<this.x) {
					this.dir=-1;
				} else {
					this.dir=1;
				}
			}
			if (!this.canSee || this.target_distance>200) {
				this.xv+=(this.dir*2);
			}
			//AI sees you, follow you!
			--this.detected;
		}else{
			if (C_Global.point_distance(this.x,this.y,this.spawnx,this.spawny)<10){
				this.detected=-1;
			}else{
				this.xv+=(this.dir*2);
//				if (this.canJump){
//					if (this.spawny>=this.y){
//						if (!this.pointtrue[0]){
//							this.yv-=12f;
//						}	
//					}else if (this.spawny>this.y){
//						if (!this.pointtrue[2] && this.pointtrue[1] && !this.pointtrue[3] && !this.pointtrue[4]){
//							this.yv-=12f;
//						}
//					}
//				}
				int tempdir=this.dir;
				if (this.returntospawnswitch<=0){
					if (this.spawnx<this.x){
						this.dir=-1;
					}else{
						this.dir=1;		
					}
				}
				if (tempdir!=this.dir) {
					this.returntospawnswitch=150;
				}
			}
		}
		if (this.returntospawnswitch>0) {
			this.returntospawnswitch--;
		}
		if (this.BulletTimer>0) {
			this.BulletTimer--;
		}

	}

//	@Override
//	@Override
	void Draw(Graphics2D g) {
		if (this.canSee){
			g.setColor(Color.green);
		} else {
			g.setColor(Color.red);
			//if (inView)
			//	g.drawLine((int)x+16-glo.Camera_x, (int)y+16-glo.Camera_y, (int)glo.World.GetPlayerX()+16-glo.Camera_x, (int)glo.World.GetPlayerY()+16-glo.Camera_y);
		}

		g.setColor(Color.blue);
		g.fillOval((int)this.x-C_Global.Camera_x, (int)this.y-C_Global.Camera_y, this.width,this.height);

		g.setColor(Color.yellow);
		g.fillRect((int)this.x+8+(8*this.dir)-C_Global.Camera_x,(int)this.y+16-C_Global.Camera_y,(12),5);

		g.setColor(Color.black);

		//g.drawString(""+target_distance,200,200);
		//g.drawString(""+detected,(int)x-glo.Camera_x, (int)y-glo.Camera_y-20);
		//g.drawString(""+returntospawnswitch,(int)x-glo.Camera_x, (int)y-glo.Camera_y-10);
		//g.drawString(""+spawnx+""+spawny,200,222);

		/*
		 *		point[0][0]=x+16+(48*dir);
		 *		point[0][1]=y+48;
		 */

		//	 g.setColor(Color.blue);
		// for (int a=0;a<5;++a){
		//	 g.fillRect((int)point[a][0]-glo.Camera_x,(int)point[a][1]-glo.Camera_y,3,3);
		// }
		//	g.drawString(""+pointtrue[1],300,100);
		//	g.drawString(""+pointtrue[2],300,110);
		//	if (Target.y<y)
		//		g.drawString("WUJSDH",400,400);

	}
}
