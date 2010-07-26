
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Iterator;


public class CW_Deleter extends C_Entity {

	CW_Deleter(){
		this.entityID=500;
		this.x=0;
		this.y=0;
		this.width=32;
		this.height=32;
	}

//	@Override
	void Draw(Graphics2D g) {
		g.setColor(Color.red);
		g.fillRect((int)this.x-C_Global.Camera_x, (int)this.y-C_Global.Camera_y, this.width, this.height);
	}

//	@Override
	void Step(Iterator _it) {
		while(_it.hasNext()){
			C_Entity Entity = (C_Entity)_it.next ();
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
						if (Entity.entityID==154){
							C_Flag Ent = (C_Flag) Entity;
							if (Ent.red==true)
								CW_Worldbuilder.Editor.RedFlag=null;
							else
								CW_Worldbuilder.Editor.BlueFlag=null;
						}
						if (Entity.entityID==100)
							CW_Worldbuilder.Editor.Player=null;
						Entity.isDead=true;
					}
				}
			}
		}
	}
};
