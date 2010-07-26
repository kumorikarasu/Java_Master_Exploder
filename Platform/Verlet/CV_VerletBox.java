
import java.util.Iterator;
import java.awt.Graphics2D;
import java.awt.Color;

class CV_VerletBox extends C_Entity{

	CV_VerletNode[] Points;
	
	CV_VerletBox(){};
	CV_VerletBox(int _x, int _y){
		x=_x;
		y=_y;
		Points = new CV_VerletNode[4];
		for (int a=0;a<4;a++){
			Points[a]=new CV_VerletNode();
		}
		Points[0].x=x-16;Points[0].y=y-16;
		Points[1].x=x+16;Points[1].y=y-16;
		Points[2].x=x+16;Points[2].y=y+16;
		Points[3].x=x-16;Points[3].y=y+16;
	};

//	@Override
//	@Override
	void Step(Iterator _it){
		for (short a=0;a<4;++a){
			Points[a].Step(_it);
		}
	}

//	@Override
//	@Override
	void Draw(Graphics2D g) {
		g.setColor(Color.blue);
		g.fillRect((int)x-C_Global.Camera_x, (int)y-C_Global.Camera_y, 3, 3);
		for (short a=0;a<4;++a){
			Points[a].Draw(g);
		}
	}
	

}

