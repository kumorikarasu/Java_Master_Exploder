
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Iterator;


class C_Bullet extends C_Entity {
	private short life;
	public int dmg;
	C_Entity creator;

	public C_Bullet(int _x, int _y, int _dmg) {
		this.life=100;
		this.x=_x;
		this.y=_y;
		this.width=4;
		this.height=4;
		this.dmg=_dmg;
		this.depth=-5;
		this.entityID=300;
	}

//	@Override
//	@Override
	void Draw(Graphics2D g) {
		g.setColor(Color.black);
		g.fillRect((int)this.x-C_Global.Camera_x, (int)this.y-C_Global.Camera_y, 2,2);
	}

//	@Override
//	@Override
	void Step(Iterator _it) {
		this.x+=this.xv;
		this.y+=this.yv;
		--this.life;
		if (this.life<0) {
			this.isDead=true;
		}

		//Collisions
		while(_it.hasNext()){
			C_Entity Entity = (C_Entity)_it.next ();

			if (Entity!=this.creator && (Entity.isSolid || Entity.isDestroyable)){
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
							this.isDead=true;
							if (Entity.isDestroyable && Entity!=C_Global.World.Player){
								Entity.hp-=dmg;
								if (Entity.entityID==101){
									C_AI ai = (C_AI) Entity;
									ai.detected=300;
								}
							}
						}
					}
				}
			}
		}
	}
}

class C_Rocket extends C_Entity {
	private short life;
	public int dmg;
	C_Entity creator;

	public C_Rocket(int _x, int _y, int _dmg) {
		this.life=100;
		this.x=_x;
		this.y=_y;
		this.width=4;
		this.height=4;
		this.dmg=_dmg;
		this.depth=-5;
		this.entityID=300;
	}

//	@Override
//	@Override
	void Draw(Graphics2D g) {
		g.setColor(Color.black);
		g.fillRect((int)this.x-C_Global.Camera_x, (int)this.y-C_Global.Camera_y, 5,5);
	}

//	@Override
//	@Override
	void Step(Iterator _it) {
		this.x+=this.xv;
		this.y+=this.yv;
		--this.life;
		if (this.life<0) {
			this.isDead=true;
		}

		//Collisions
		while(_it.hasNext()){
			C_Entity Entity = (C_Entity)_it.next ();

			if (Entity!=this.creator && (Entity.isSolid || Entity.isDestroyable)){
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
								}
								else{					
									this.x += px; // right
									this.xv /= 2;
								}
							}else{ // more y penetration
								if(dy<0){
									this.y -= py;

									this.yv /= 2;

								}else{
									this.y += py; // down
									this.yv /= 2;
								}
							}
							this.isDead=true;
							C_Global.World.Create(new C_Explosion((int)x,(int)y, 3));
						}
					}
				}
			}
		}
	}
}

class C_Flame extends C_Entity {
	private short life;
	private float dmg;
	C_Entity creator;
	boolean isDud;
	int maxlife;

	public C_Flame(int _x, int _y, int _dmg) {
		this.life=20;
		this.maxlife=life;
		this.x=_x;
		this.y=_y;
		this.width=4;
		this.height=4;
		this.dmg=_dmg;
		this.depth=-5;
		this.entityID=300;
	}

//	@Override
//	@Override
	void Draw(Graphics2D g) {//(/*255*/0,255/*((life/maxlife)*255)*/,0));
		g.setColor(new Color(255,(int)(255-(255*((float)life/(float)maxlife))),0,(int)(255*((float)life/(float)maxlife))));
		g.fillOval((int)this.x-C_Global.Camera_x, (int)this.y-C_Global.Camera_y, 4,4);
	}

//	@Override
//	@Override
	void Step(Iterator _it) {
		this.x+=this.xv*1.5;
		this.y+=this.yv*1.5;
		y-=2f;
		dmg-=0.5f;
		if (dmg<1)
			dmg=1;
		--this.life;
		if (this.life<0) {
			this.isDead=true;
		}
		//yv=-0.1f;

		//Collisions
		while(_it.hasNext()){
			C_Entity Entity = (C_Entity)_it.next ();

			if (Entity!=this.creator && (Entity.isSolid || Entity.isDestroyable)){
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
							/*		if(dx<0){
						//		this.x -= px;
						//		this.xv /= 2;
							}
							else{					
						//		this.x += px; // right
						//		this.xv /= 2;
							}
						}else{ // more y penetration
							if(dy<0){
								if (Entity.xv!=0) {
									this.x+=Entity.xv;
								}
						//		this.y -= py;
						//		this.yv /= 2;
							}
							else{
						//		this.y += py; // down
						//		this.yv /= 2;
							}
							 */

							this.isDead=true;

							//if (Entity == glo.World.Player) {
							//	glo.World.Player.hp-=dmg;
							//}
							if (Entity.isDestroyable){
								Entity.hp-=dmg;
								//if (!isDud){
								//		C_FlameEfx Bullet = new C_FlameEfx((int)x-2+(int)(Math.random()*5),(int)y+1);
								//		Bullet.yv=-0.5f;
								//		glo.World.Create(Bullet);
								//		isDud=true;
								//	}
								if (Entity.entityID==101){
									C_AI ai = (C_AI) Entity;
									ai.detected=300;
								}
							}
							//if (!isDud){
							/*		C_FlameEfx Bullet = new C_FlameEfx((int)x-2+(int)(Math.random()*5),(int)y);
							Bullet.yv=-0.5f;
							C_Global.World.Create(Bullet);
							isDud=true;
							 */
							//	}
						}
					}
				}
			}
		}
	}
}

class C_FlameEfx extends C_Entity {
	private short life;
	private float dmg;
	C_Entity creator;
	boolean isDud;

	C_FlameEfx(){
		this.life=5;
		this.depth=-5;
		this.entityID=300;
	}

	public C_FlameEfx(int _x, int _y) {
		this.life=5;
		this.x=_x;
		this.y=_y;
		this.width=4;
		this.height=4;
		this.depth=-5;
		this.entityID=300;
	}

//	@Override
//	@Override
	void Draw(Graphics2D g) {
		g.setColor(Color.yellow);
		g.fillRect((int)this.x-C_Global.Camera_x, (int)this.y-C_Global.Camera_y, 4,4);
	}

//	@Override
//	@Override
	void Step(Iterator _it) {
		this.x+=this.xv;
		this.y+=this.yv;
		--this.life;
		if (this.life<0) {
			this.isDead=true;
		}
		while(_it.hasNext()){
			C_Entity Entity = (C_Entity)_it.next ();
			if (x==0){
				if(y==0){

				}
			}

		}
	}
}

class C_Explosion extends C_Entity{

	int boom;
	C_Explosion(int _x, int _y, int _boom){
		x=_x;
		y=_y;
		boom=_boom;
	}
	@Override
	void Draw(Graphics2D g) {
	}

	@Override
	void Step(Iterator _it) {
		if(boom>0){
			for(int a=0;a<45;a++){
				C_ExplosionEfx Bullet = new C_ExplosionEfx();
				Bullet.x=x;
				Bullet.y=y;
				Bullet.xv=(float) (Math.cos(Math.toRadians(a*8))*8);
				Bullet.yv=(float) (Math.sin(Math.toRadians(a*8))*8);
				Bullet.creator=null;
				C_Global.World.Create(Bullet);
			}
			boom--;
		}
	}
	
}

class C_ExplosionEfx extends C_Entity {
	private short life;
	private float dmg;
	C_Entity creator;
	boolean isDud;
	int maxlife;

	C_ExplosionEfx(){
		this.life=15;
		this.maxlife=life;
		this.dmg=1f;
		this.depth=-5;
		this.entityID=300;
		this.isDestroyable=false;
	}

	public C_ExplosionEfx(int _x, int _y) {
		this.life=15;
		this.maxlife=life;
		this.x=_x;
		this.y=_y;
		this.width=4;
		this.height=4;
		this.dmg=1f;
		this.depth=-5;
		this.entityID=303;
		this.isDestroyable=false;
	}

//	@Override
//	@Override
	void Draw(Graphics2D g) {//(/*255*/0,255/*((life/maxlife)*255)*/,0));
		g.setColor(new Color(255,(int)(255-(255*((float)life/(float)maxlife))),0,(int)(255*((float)life/(float)maxlife))));
		g.fillOval((int)this.x-C_Global.Camera_x, (int)this.y-C_Global.Camera_y, 8,8);
	}

//	@Override
//	@Override
	void Step(Iterator _it) {
		this.x+=this.xv;
		this.y+=this.yv;
		y-=2f;
		--this.life;
		if (this.life<0) {
			this.isDead=true;
		}
		//yv=-0.1f;

		//Collisions
		while(_it.hasNext()){
			C_Entity Entity = (C_Entity)_it.next ();

			if ((Entity.isSolid || Entity.isDestroyable || Entity.entityID==100)){
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
							this.isDead=true;
							if(Entity.entityID==100)
								C_Global.World.Player.hp-=dmg;
							if (Entity.isDestroyable){
								Entity.hp-=dmg;
							}
						}
					}
				}
			}
		}
	}
}

class C_SniperBullet extends C_Entity {
	private short life;
	public int dmg;
	C_Entity creator;
	float dir;
	int endx, endy;

	public C_SniperBullet(int _x, int _y, int _dmg, float _dir) {
		this.life=100;
		this.x=_x;
		this.y=_y;
		this.width=4;
		this.height=4;
		this.dmg=_dmg;
		this.depth=-5;
		this.entityID=300;
		dir=_dir;
		endx=0;
		endy=0;
	}

//	@Override
//	@Override
	void Draw(Graphics2D g) {
		g.setColor(Color.black);
		if(life<100){
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, life/100.0f));
			g.drawLine((int)x-C_Global.Camera_x, (int)y-C_Global.Camera_y, endx-C_Global.Camera_x, endy-C_Global.Camera_y);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
		}
	}

//	@Override
//	@Override
	void Step(Iterator _it) {
		if(life==100){
			this.x+=this.xv;
			this.y+=this.yv;
			
			float[] apx = new float[200];
			float[] apy = new float[200];
			
			for (int i=0;i<200;i++){
				apx[i] = (float) (this.x+this.width/2+Math.cos(dir)*(i*8));
				apy[i] = (float) (this.y+this.height/2+Math.sin(dir)*(i*8));
			}
			boolean collision=false;
			C_Entity colEnt=null;
			//Collisions
			while(_it.hasNext()){
				C_Entity Entity = (C_Entity)_it.next ();
	
				if (Entity!=this.creator && (Entity.isSolid || Entity.isDestroyable)){
					for (int i=0;i<200;i++){
						/*if(endx < -32 || endx > C_Global.World.CurrentLevel.width+32 || endy < -32 || endy > C_Global.World.CurrentLevel.height+32){
							collision=true;
							break;
						}*/
						
						if (apx[i] != -1 && apy[i] != -1){
							if (!(apx[i] < Entity.x || apx[i] > Entity.x+Entity.width)){
								if (!(apy[i] < Entity.y || apy[i] > Entity.y+Entity.height)){
									//collision
									if(collision){
										float dist1=C_Global.point_distance(endx, endy, C_Global.World.Player.x, C_Global.World.Player.y);
										float dist2=C_Global.point_distance(apx[i], apy[i], C_Global.World.Player.x, C_Global.World.Player.y);
										if (dist2<dist1){
											endx=(int)apx[i];
											endy=(int)apy[i];
											colEnt=Entity;
										}
									}else{
										endx=(int)apx[i];
										endy=(int)apy[i];
										colEnt=Entity;
										collision=true;
									}
								}
							}
						} else {
							break;
						}
					}
				}
			}
			if(colEnt!=null){
				if(colEnt.isDestroyable)
					colEnt.hp-=dmg;
			}else{
				endx=(int)apx[199];
				endy=(int)apy[199];
			}
		}
		life-=2;
		if (life<=0) {
			isDead=true;
		}
	}
}
