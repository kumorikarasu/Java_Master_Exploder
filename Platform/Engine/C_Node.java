
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Iterator;


class C_Node extends C_Entity {
	boolean isDrawn;
	
	C_Node(){
		this.width=16;
		this.height=16;
		this.isSolid=false;
		this.entityID=150;
		this.isNode=true;
	};
	C_Node(int _x, int _y){
		this.x=_x;
		this.y=_y;
		this.width=16;
		this.height=16;
		this.entityID=150;
		this.isNode = true;
	};
	C_Node(int _x, int _y,boolean _isdrawn){
		this.x=_x;
		this.y=_y;
		this.width=16;
		this.height=16;
		this.isSolid=false;
		this.entityID=150;
		this.isNode=true;
		this.isDrawn = _isdrawn;
	};
	

//	@Override
//	@Override
	void Step(Iterator _it){
	}
	
//	@Override
//	@Override
	void Draw(Graphics2D g) {
		if (this.isDrawn){
			g.setColor(Color.orange);
			g.fillRect((int)this.x-C_Global.Camera_x, (int)this.y-C_Global.Camera_y, this.width, this.height);
		}
	}
}

class C_Node_MultiplayerSpawn extends C_Node {

	
	C_Node_MultiplayerSpawn(){
		super();
		this.entityID=151;
	};
	C_Node_MultiplayerSpawn(int _x, int _y){
		super(_x,_y);
		this.entityID=151;
	};
	C_Node_MultiplayerSpawn(int _x, int _y,boolean _isdrawn){
		super(_x,_y,_isdrawn);
		this.entityID=151;
		this.isDrawn= _isdrawn;
	};
	
	void Step(Iterator _it){}

	void Draw(Graphics2D g) {
		if (this.isDrawn){
			g.setColor(Color.red);
			g.fillRect((int)this.x-C_Global.Camera_x, (int)this.y-C_Global.Camera_y, this.width, this.height);
		}
	}
}

class C_Node_AI_Jump extends C_Node {
	short dir;
	boolean ismandatory;
	
	C_Node_AI_Jump(int _dir, boolean _ismandatory){
		super();
		dir=(short)_dir;
		ismandatory=_ismandatory;
		this.entityID=152;
	};
	C_Node_AI_Jump(int _dir, boolean _ismandatory, int _x, int _y){
		super(_x,_y);
		dir=(short)_dir;
		ismandatory=_ismandatory;
		this.entityID=152;
	};
	C_Node_AI_Jump(int _dir, boolean _ismandatory, int _x, int _y,boolean _isdrawn){
		super(_x,_y,_isdrawn);
		dir=(short)_dir;
		ismandatory=_ismandatory;
		this.entityID=152;
		this.isDrawn= _isdrawn;
	};
	
	void Step(Iterator _it){}

	void Draw(Graphics2D g) {
		if (this.isDrawn){
			if (ismandatory)
				g.setColor(Color.red);
			else
				g.setColor(Color.pink);
			g.fillRect((int)this.x-C_Global.Camera_x, (int)this.y-C_Global.Camera_y, this.width, this.height);
			g.setColor(Color.yellow);
			g.fillRect((int)this.x+4+(4*this.dir)-C_Global.Camera_x,(int)this.y+8-C_Global.Camera_y,(12),5);
		}
	}
}
