import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Iterator;

class C_Pickup_Spawner extends C_Entity{

	private int Timer;
	private short type;
	private Color c;
	boolean isDrawn;
	C_Pickup_Spawner(int _x, int _y,short _type, boolean b){
		x=_x;
		y=_y;
		width=16;
		height=16;
		entityID=156;
		type = _type;
		isDrawn = b;
		if (type==155)
			c = Color.white;
	}

	@Override
	void Draw(Graphics2D g) {
		if (this.isDrawn){
			g.setColor(c);
			g.fillRect((int)x, (int)y, (int)width, (int)height);
		}
	}

	@Override
	void Step(Iterator _it) {
		if (Timer>0){
			Timer--;
		}else{
			C_Entity Spawn = new C_Pickup(x,y,type);
		}
		while(_it.hasNext()){
			C_Entity Entity = (C_Entity)_it.next ();
			if (x<Entity.x && y<Entity.y && x+width<Entity.x+Entity.width && y+height>Entity.y+Entity.height){
				if (Entity.entityID==155)
						this.Timer=500;
			}
		}
	}
}

class C_Pickup extends C_Entity {
	boolean isEquipped;
	short type;
	Color c;
	public boolean returnToSpawn;
	int spawnx,spawny;
	boolean spawnSet;
	/**
	 * 
	 */
	private static final long serialVersionUID = 5821184095459774015L;

	public C_Pickup() {
		super();
		isEquipped=false;
		x=0;
		y=0;
		width=16;
		height=16;
		type = 155;
		if (type==1)
			entityID=155;
		c = Color.white;
	}
	public C_Pickup(float _x, float _y, short _type) {
		super();
		isEquipped=false;
		x=_x;
		y=_y;
		width=16;
		height=16;
		type = _type;
		if (type==1)
			entityID=155;
		c = Color.white;
	}	

	void Draw(Graphics2D g) {
		g.setColor(c);
		g.fillRect((int)x, (int)y, (int)width, (int)height);
	}

	void Step(Iterator _it) {
		if (!this.isEquipped){
			this.yv+=C_Global.Gravity;
			this.x+=this.xv;
			this.y+=this.yv;
			
			if (Math.abs(this.xv)>0) {
				this.xv/=1.1;
			}
			if (Math.abs(this.xv)<0.9) {
				this.xv=0;
			}
			while(_it.hasNext()){
				C_Entity Entity = (C_Entity)_it.next ();
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
								if (Entity.entityID==100){
									isDead=true;
									Entity.hp+=50;
								}
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
									}
									else{
										this.y += py; // down
										this.yv /= 2;
									}
								}
							}
						}
					}
				}
			}
		}
	}
}
