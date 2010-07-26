import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Iterator;


public class C_Flag extends C_Entity {
	boolean isEquipped;
	boolean red;
	Color c;
	public boolean returnToSpawn;
	int spawnx,spawny;
	boolean spawnSet;
	/**
	 * 
	 */
	private static final long serialVersionUID = 5821184095459774015L;

	public C_Flag() {
		super();
		isEquipped=false;
		x=0;
		y=0;
		width=32;
		height=64;
		entityID=154;
	}
	public C_Flag(int _x, int _y, boolean _red) {
		super();
		isEquipped=false;
		x=_x;
		y=_y;
		red=_red;
		width=32;
		height=64;
		entityID=154;
		if (_red==true){
			c=Color.red;
			C_Global.red=this;
		}else{
			c=Color.blue;
			C_Global.blue=this;
		}
	}	
	
	void toSpawn(){
		returnToSpawn=true;
		isEquipped=false;
		x=spawnx;
		y=spawny;
	}

	void Draw(Graphics2D g) {
		g.setColor(c);
		int[] flagx = {(int) (x+0-C_Global.Camera_x),(int) (x+32-C_Global.Camera_x),(int) (x+32-C_Global.Camera_x),(int) (x+5-C_Global.Camera_x),(int) (x+5-C_Global.Camera_x),(int) (x+0-C_Global.Camera_x)};
		int[] flagy = {(int) (y+0-C_Global.Camera_y),(int) (y+0-C_Global.Camera_y),(int) (y+32-C_Global.Camera_y),(int) (y+32-C_Global.Camera_y),(int) (y+64-C_Global.Camera_y),(int) (y+64-C_Global.Camera_y)};
		g.fillPolygon(flagx,flagy,6);
	}

	void Step(Iterator _it) {
		if (!this.isEquipped){
			if (returnToSpawn){
				xv=0;
				yv=0;
				x=spawnx;
				y=spawny;
				returnToSpawn=false;
			}
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
								if (!spawnSet){
									spawnx=(int) x;
									spawny=(int) y;
									spawnSet=true;
								}
							}
						}
					}
				}
			}
		}
	}

}
