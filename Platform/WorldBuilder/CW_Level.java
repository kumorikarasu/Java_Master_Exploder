
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JPanel;


public class CW_Level{

	ArrayList Levellist;
	CW_Level(){
		this.Levellist=new ArrayList();
	}

}

class WC_Editor extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4263817134462024069L;
	protected String CurrentSelection="asdf";
	protected int mouse;
	protected int mouse_x;
	protected int mouse_y;
	C_Entity Draw;
	C_Player Player;
	C_Flag BlueFlag;
	C_Flag RedFlag;
	

	WC_Editor(){
		setPreferredSize(new Dimension(800, 600));
		setBackground(Color.lightGray);
	}
	
	void ChangeSize(int _x, int _y){
		setPreferredSize(new Dimension(_x, _y));
		setSize(new Dimension(_x, _y));
		this.repaint();
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);

		if (this.Draw!=null){
			if (this.mouse_x+this.Draw.width-32<CW_Worldbuilder.width && this.mouse_y+this.Draw.height-32<CW_Worldbuilder.height){
				this.Draw.x = this.mouse_x;
				this.Draw.y = this.mouse_y;
			}
			this.Draw.Draw((Graphics2D) g);
		}

		Iterator LevelIt = CW_Worldbuilder.getLevel();
		C_Entity Entity = null;
		while(LevelIt.hasNext()){
			Entity = (C_Entity)LevelIt.next ();
			if (Entity!=null){
				if (Entity.isDead) {
					LevelIt.remove();
				}else
					Entity.Draw((Graphics2D) g);
			}
		}
		g.setColor(Color.black);
		if (CW_Worldbuilder.grid){
			for (int a=0;a<CW_Worldbuilder.width;a+=CW_Worldbuilder.gridx){
				g.drawLine(a, 0, a, CW_Worldbuilder.height);
			}
			for (int b=0;b<CW_Worldbuilder.height;b+=CW_Worldbuilder.gridy){
				g.drawLine(0, b, CW_Worldbuilder.width, b);
			}
		}
	}

	public void getDraw(String _CS) {
		this.Draw=null;
		if (_CS.equalsIgnoreCase("32x"))
			this.Draw=new C_Rectangle(true,0,0,32,32);
		else if (_CS.equalsIgnoreCase("64x32"))
			this.Draw=new C_Rectangle(true,0,0,64,32);
		else if (_CS.equalsIgnoreCase("128x32"))
			this.Draw=new C_Rectangle(true,0,0,128,32);
		else if (_CS.equalsIgnoreCase("256x32"))
			this.Draw=new C_Rectangle(true,0,0,256,32);
		else if (_CS.equalsIgnoreCase("512x32"))
			this.Draw=new C_Rectangle(true,0,0,512,32);
		else if (_CS.equalsIgnoreCase("32x64"))
			this.Draw=new C_Rectangle(true,0,0,32,64);
		else if (_CS.equalsIgnoreCase("32x128"))
			this.Draw=new C_Rectangle(true,0,0,32,128);
		else if (_CS.equalsIgnoreCase("32x256"))
			this.Draw=new C_Rectangle(true,0,0,32,256);
		else if (_CS.equalsIgnoreCase("32x512"))
			this.Draw=new C_Rectangle(true,0,0,32,512);

		else if (_CS.equalsIgnoreCase("Pistol"))
			this.Draw=new C_USP(0,0);
		else if (_CS.equalsIgnoreCase("M4"))
			this.Draw=new C_M4(0,0);

		else if (_CS.equalsIgnoreCase("AI"))
			this.Draw=new C_AI(0,0);
		else if (_CS.equalsIgnoreCase("Patrol Node"))
			this.Draw=new C_Node(0,0,true);
		else if (_CS.equalsIgnoreCase("Jump Node Left"))
			this.Draw=new C_Node_AI_Jump(-1,false,0,0,true);
		else if (_CS.equalsIgnoreCase("Jump Node Right"))
			this.Draw=new C_Node_AI_Jump(1,false,0,0,true);
		else if (_CS.equalsIgnoreCase("Jump Node Left Mandatory"))
			this.Draw=new C_Node_AI_Jump(-1,true,0,0,true);
		else if (_CS.equalsIgnoreCase("Jump Node Right Mandatory"))
			this.Draw=new C_Node_AI_Jump(1,true,0,0,true);

		else if (_CS.equalsIgnoreCase("Spawn Point"))
			this.Draw=new C_Node_MultiplayerSpawn(0,0,true);
		else if (_CS.equalsIgnoreCase("Health Pack Spawn"))
			this.Draw=new C_Pickup_Spawner(0,0,(short)155,true);

		else if (_CS.equalsIgnoreCase("Blue Flag")){
			this.Draw=new C_Flag(0,0,false);
		}
		else if (_CS.equalsIgnoreCase("Red Flag")){
			this.Draw=new C_Flag(0,0,true);
		}
		else if (_CS.equalsIgnoreCase("Player")){
			C_Player temp;
			temp = new C_Player();
			temp.noHud=true;
			this.Draw=temp;
		}else if (_CS.equalsIgnoreCase("Delete")){
			this.Draw=new CW_Deleter();
		}
	}
	
	public void Click(int _mouse) {
		if (_mouse==3)
			this.Draw=null;
		if (_mouse==1 && this.Draw!=null){
			if (this.Draw.entityID!=500 && this.Draw.entityID!=100 && this.Draw.entityID!=154){
				CW_Worldbuilder.CurrentLevel.Levellist.add(this.Draw);
				getDraw(this.CurrentSelection);
				CW_Worldbuilder.isSaved=false;
			}else if (this.Draw.entityID==100){
				if (Player==null){
					Player = new C_Player();
					CW_Worldbuilder.CurrentLevel.Levellist.add(Player);
					Player.noHud=true;
				}
				Player.x=Draw.x;
				Player.y=Draw.y;
			}else if (this.Draw.entityID==154){
				C_Flag tempflag = (C_Flag)this.Draw;
				if (tempflag.red){
					if (RedFlag==null){
						RedFlag = new C_Flag(0,0,true);
						CW_Worldbuilder.CurrentLevel.Levellist.add(RedFlag);
					}
				RedFlag.x=Draw.x;
				RedFlag.y=Draw.y;
				}
				else{
					if (BlueFlag==null){
						BlueFlag = new C_Flag(0,0,false);
						CW_Worldbuilder.CurrentLevel.Levellist.add(BlueFlag);
					}
				BlueFlag.x=Draw.x;
				BlueFlag.y=Draw.y;
				}
			}else{
				this.Draw.Step(CW_Worldbuilder.getLevel());
			}
		}
		//this.repaint();
	}
}