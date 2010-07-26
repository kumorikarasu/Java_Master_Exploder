import java.awt.Graphics2D;
import java.util.Iterator;


class CO_Red extends C_Player {

	CO_Red() {
		super();
	}
	
	void Step(Iterator _it){
		super.Step(_it);
		C_Global.Camera_x=(int)this.x-320;//640
		C_Global.Camera_y=(int)this.y-240;//480
	}
	
	void Draw(Graphics2D g){
		super.Draw(g);
	}

}
