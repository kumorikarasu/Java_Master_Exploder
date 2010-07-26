import java.awt.Color;
import java.awt.Graphics;


abstract class C_Button {
	int x,y,width,height;
	String Text;
	Color ac,hc,c,bc;
	boolean isActive=false;
	
	C_Button(){
		c = new Color(0x000000);
		bc = new Color(128,128,128);
		Setup();
		
	}
	void Draw(Graphics g){
		g.drawRect(x, y, width, height);
		
		g.setColor(c);
		g.drawString(Text+ " ", x+10, y+(height/2)+7);
	}
	void Draw(Graphics g, int _x, int _y){
		g.setColor(bc);
		g.fillRect(x, y, width, height);
		if (isActive)
			g.setColor(ac);
		else if (isHover(_x,_y))
			g.setColor(hc);
		else
			g.setColor(c);
		
		g.drawRect(x, y, width, height);
		g.setColor(c);
		g.drawString(Text, x+10, y+(height/2)+7);
	}
	abstract void Setup();
	abstract void Action();
	void setHover(Color _c){
		hc=_c;
	}
	void setActive(Color _c){
		ac=_c;
	}
	void setActive(boolean _b){
		isActive=_b;
	}
	boolean isHover(int _x, int _y){
		if (_x>x && _y>y && _x<x+width && _y<y+height){
			return true;
		}
		return false;
	}
	void isPressed(int _x, int _y){
		if (_x>x && _y>y && _x<x+width && _y<y+height)
			Action();
	}
}