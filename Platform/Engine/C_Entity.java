import java.awt.Graphics2D;
import java.util.Iterator;

import javax.swing.JComponent;

/*	
 *	Base Object
 *	Entity ID List
 *	100 - Player
 *	101 - AI
 *	102 - Blocks
 *  103 - Dummy Player (Online)
 *  150 - Nodes
 *	151 - Spawn Points
 *	154 - Online Flag
 *  155 - Health Pickup
 *  156 - Health Pack Spawner
 *	200 - 249 1H Weapons
 *	250 - 299 2H Weapons
 *	300 - 399 Bullets
 */

abstract class C_Entity extends JComponent{
	public boolean isDead,isSolid,isCachable,isStatic,isDestroyable,isNode,isAI;
	protected float x,y,xv,yv;
	protected short width,height;
	public float hp;
	public int entityID;
	public int depth;
	
	C_Entity(){this.entityID=100;};
	abstract void Step(Iterator _it);
	abstract void Draw(Graphics2D g);
	
	void Collision(Iterator _it){
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
						}
					}
				}
			}
		}
	}
};
