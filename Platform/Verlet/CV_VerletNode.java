
import java.util.Iterator;
import java.awt.Graphics2D;
import java.awt.Color;

class CV_VerletNode extends C_Entity{
	float xprev,yprev;
	float xv,yv;
	CV_VerletNode[] Constraint;
	
	CV_VerletNode(){
	Constraint = new CV_VerletNode[4];	
	};
	
//	@Override
//	@Override
	void Step(Iterator _it){
		yv=0.3f;
		
		xprev=x;
		yprev=y;
		x=x*2-xprev+xv;
		y=y*2-yprev+yv;
		
		while(_it.hasNext()){
		C_Entity Entity = (C_Entity)_it.next ();
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
			}
		}
	}

//	@Override
//	@Override
	void Draw(Graphics2D g) {
		g.setColor(Color.blue);
		g.fillRect((int)x-C_Global.Camera_x, (int)y-C_Global.Camera_y, 3, 3);

	}
}