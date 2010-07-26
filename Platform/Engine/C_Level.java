
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JFileChooser;

public class C_Level {

	public ArrayList LevelList = new ArrayList();
	public Iterator LevelIt;
	public boolean isFinished;
	private boolean populated;
	public int width, height, gridx, gridy;
	public C_Node_MultiplayerSpawn[] SpawnPoints = new C_Node_MultiplayerSpawn[24];
	public int Spawns=0;
	String LevelName;

	C_Level(){};
	C_Level(String levelname){
		LevelName = levelname;
		width=0;
		height=0;
		isFinished=false;
		populated=false;
	}
	/*switch(levelID){
	case 1:
		/*
		LevelList.add(new C_Rectangle(true,0,600-32,800,32));
		LevelList.add(new C_Rectangle(true,0,0,800,32));
		LevelList.add(new C_Rectangle(true,800-32,0,32,600));
		LevelList.add(new C_Rectangle(true,0,0,32,600));
		LevelList.add(new C_Rectangle(true,0,600-128,256+64,32));
		LevelList.add(new C_Rectangle(true,256+128+64,600-128,128,32));
		LevelList.add(new C_Node(256,440));
		LevelList.add(new C_Rectangle(true,256+128+64+128+100,600-128,600,32));
		//for (int a=0;a<75;++a)
			LevelList.add(new C_AI(128,400));
		LevelList.add(new C_USP(200,200));
		LevelList.add(new C_M4(300,200));
		LevelList.add(new C_M4(400,200));
		populated=true;
		return C_FileIO.OpenFile(LevelList, "Levels/level2.txt", null);
	default:
	//	LevelList.add(new C_Error("No Level exists of that ID"));
	break;
	}*/
	public boolean PopulateLevel(){
		if (!this.populated){
			C_Global.World.SetPlayerX(100);
			C_Global.World.SetPlayerY(100);
			return C_FileIO.OpenFile(this.LevelList, "Levels/level1.mel", null);
			
		}
		return false;
	}
	public void Step(){
		this.LevelIt=this.LevelList.iterator();
		while(this.LevelIt.hasNext()){
			C_Entity Entity = (C_Entity)this.LevelIt.next ();
			Entity.Step(this.LevelList.iterator());
			if (Entity.isDead) {
				if (Entity.entityID==103){
					CO_Player temp = (CO_Player)Entity;
					if (temp.MainHand!=null){
						temp.MainHand.isHeld=false;
						temp.MainHand.isEquipped=false;
						temp.MainHand.xv=temp.dir*6;
					}else if(temp.OffHand!=null){
						temp.OffHand.isHeld=false;
						temp.OffHand.isEquipped=false;
						temp.OffHand.xv=temp.dir*6;
					}
				}
				this.LevelIt.remove();
			}
		}
	}

	public void Draw(Graphics2D g) {

		this.LevelIt=this.LevelList.iterator();
		C_Entity Entity = null;
		while(this.LevelIt.hasNext()){
			Entity = (C_Entity)this.LevelIt.next ();
			Entity.Draw(g);
		}

	}
}
