
import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;


class C_Bullet extends C_Entity {
	private short life;
	public int dmg;
	C_Entity creator;

	C_Bullet(){
		this.life=100;
		this.dmg=5;
		this.depth=-5;
		this.entityID=300;
	}

	public C_Bullet(int _x, int _y) {
		this.life=100;
		this.x=_x;
		this.y=_y;
		this.width=4;
		this.height=4;
		this.dmg=5;
		this.depth=-5;
		this.entityID=300;
	}

//	@Override
//	@Override
	void Draw(Graphics g) {
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

	C_Rocket(){
		this.life=100;
		this.dmg=5;
		this.depth=-5;
		this.entityID=300;
	}

	public C_Rocket(int _x, int _y) {
		this.life=100;
		this.x=_x;
		this.y=_y;
		this.width=4;
		this.height=4;
		this.dmg=5;
		this.depth=-5;
		this.entityID=300;
	}

//	@Override
//	@Override
	void Draw(Graphics g) {
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
							this.isDead=true;
							C_Global.World.Create(new C_Explosion(x,y,150,80));
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

class C_Explosion extends C_Entity{

	private int r,dmg;

	public C_Explosion(float x, float y, int r, int d) {
		// TODO Auto-generated constructor stub
		this.x=x;
		this.y=y;
		this.r=r;
		this.dmg=d;
		hp=r*2;
	}

	@Override
	void Draw(Graphics g) {
		hp--;
		int size=1;
		g.setColor(Color.red);
		if (hp<r){
			g.fillOval((int)(x-C_Global.Camera_x-hp*size/2), (int)(y-C_Global.Camera_y-hp*size/2),(int) hp*size,(int) hp*size);
		}else{
			g.fillOval((int)(x-C_Global.Camera_x-(r-((float)hp/2f))*size/2), (int)(y-C_Global.Camera_y-(r-((float)hp/2f))*size/2),(int) (r-((float)hp/2f))*size,(int) (r-((float)hp/2f))*size);
			}
		if (hp<=0)
			isDead=true;
	}

	@Override
	void Step(Iterator _it) {
		while (_it.hasNext()){
			C_Entity Ent = (C_Entity) _it.next();
			if (Ent.entityID==103 || Ent.entityID==100){
			//	float dis = C_Global.point_distance(x, y, Ent.x, Ent.y);
			//	if (dis<r)
					Ent.hp-=1;//r-dis;
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

	C_Flame(){
		this.life=15;
		this.maxlife=life;
		this.dmg=1f;
		this.depth=-5;
		this.entityID=300;
	}

	public C_Flame(int _x, int _y) {
		this.life=15;
		this.maxlife=life;
		this.x=_x;
		this.y=_y;
		this.width=4;
		this.height=4;
		this.dmg=1f;
		this.depth=-5;
		this.entityID=300;
	}

//	@Override
//	@Override
	void Draw(Graphics g) {//(/*255*/0,255/*((life/maxlife)*255)*/,0));
		g.setColor(new Color(255,(int)(255-(255*((float)life/(float)maxlife))),0,(int)(255*((float)life/(float)maxlife))));
		g.fillOval((int)this.x-C_Global.Camera_x, (int)this.y-C_Global.Camera_y, 4,4);
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
	void Draw(Graphics g) {
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

class C_Grenade extends C_Entity {
	private short life;
	private int dmg;
	C_Entity creator;

	C_Grenade(){
		this.life=100;
		this.dmg=5;
		this.depth=-5;
		this.entityID=300;
	}

	public C_Grenade(int _x, int _y) {
		this.life=100;
		this.x=_x;
		this.y=_y;
		this.width=4;
		this.height=4;
		this.dmg=5;
		this.depth=-5;
		this.entityID=300;
	}

//	@Override
//	@Override
	void Draw(Graphics g) {
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
			//BOOM
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

