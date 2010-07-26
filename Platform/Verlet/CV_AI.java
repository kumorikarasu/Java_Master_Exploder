
import java.util.Iterator;
import java.awt.Graphics;
import java.awt.Color;

class CV_AI extends CV_VerletNode{
	
	
	void Draw(Graphics g) {
		g.setColor(Color.blue);
		g.fillOval((int)x-C_Global.Camera_x, (int)y-C_Global.Camera_y, width,height);
	}
};