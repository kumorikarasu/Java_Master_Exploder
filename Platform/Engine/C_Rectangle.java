
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Iterator;


class C_Rectangle extends C_Entity {
	
	C_Rectangle(){
		this.x=0;
		this.y=0;
		this.width=32;
		this.height=32;
		this.xv=0;
		this.yv=0;
		this.isSolid=false;
		this.entityID=102;
	};
	C_Rectangle(boolean _isSolid){
		this.x=0;
		this.y=0;
		this.width=32;
		this.height=32;
		this.xv=0;
		this.yv=0;
		this.isSolid=_isSolid;
		this.entityID=102;
	};
	C_Rectangle(boolean _isSolid,int _x, int _y){
		this.isSolid=_isSolid;
		this.x=_x;
		this.y=_y;
		this.width=32;
		this.height=32;
		this.xv=0;
		this.yv=0;
		this.entityID=102;
	}
	C_Rectangle(boolean _isSolid,int _x, int _y,int _width, int _height){
		this.isSolid=_isSolid;
		this.x=_x;
		this.y=_y;
		this.width=(short)_width;
		this.height=(short)_height;
		this.xv=0;
		this.yv=0;
		this.entityID=102;
	}
	

//	@Override
//	@Override
	void Step(Iterator _it){
	}
	
//	@Override
//	@Override
	void Draw(Graphics2D g) {
		g.setColor(Color.black);
		//g.fillRect((int)this.x-C_Global.Camera_x, (int)this.y-C_Global.Camera_y, this.width, this.height);
		C_Global.drawSprite(g, C_SpriteList.Block1, (int)x-C_Global.Camera_x, (int)y-C_Global.Camera_y, width, height);
	}
}
