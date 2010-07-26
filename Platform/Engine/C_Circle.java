
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Iterator;


class C_Circle extends C_Entity{
	float radius,xprev,yprev,xnext,ynext;

	C_Circle(int _x,int _y){
		entityID=201; //200-249 1h series for weapons
		x=_x;
		y=_y;
		xprev=x;
		yprev=y;
		width=32;
		height=32;
		radius=16;
		isSolid=true;
		yv = C_Global.Gravity;
	}

	//@Override
//	@Override
	void Step(Iterator _it){

		xnext=x*2-xprev+xv;
		xprev=x;
		x=xnext;

		ynext=y*2-yprev+yv;
		yprev=y;
		y=ynext;

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
							//Check Radius

							float dis,delta,dir=0;
							dis = C_Global.point_distance(px,py,0,0);

							//Radius
							delta=dis-radius;
							if (delta>0){
								// does radius have less penetration?
								if (delta<px){
									if (dx<0 && dy<0){ //Top Left
										//	Ayh,Axh;
										dir=C_Global.point_direction(Bl,Bt,Ayh,Axh);
										
									}else if(dx>0 && dy<0){ //Top Right
										dir=C_Global.point_direction(Br,Bt,Ayh,Axh);

									}else if(dx<0 && dy>0){//Bottom Left
										dir=C_Global.point_direction(Bl,Bb,Ayh,Axh);

									}else if(dx<0 && dy>0){//Bottom Right
										dir=C_Global.point_direction(Br,Bb,Ayh,Axh);
									}
									x=x+(float)Math.cos(dir)*delta;
									y=y-(float)Math.sin(dir)*delta;
								}
								// does x have less penetration?
								if(px<py){
									// move left
									if(dx<0){
										x -= px;
										//	xv /= 2;
									}
									else{					
										x += px; // right
										//	xv /= 2;
									}
								}else{ // more y penetration
									if(dy<0){
										y -= py; //up
										//	yv /= 2;
									}
									else{
										y += py; // down
										//	yv /= 2;
									}
								}
							}
						}
					}
				}
			}
		}
	}

	//@Override
//	@Override
	void Draw(Graphics2D g){
		g.setColor(Color.black);
		g.fillOval((int)x-C_Global.Camera_x, (int)y-C_Global.Camera_y, width, height);
	}
};	