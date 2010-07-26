import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.util.Iterator;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.JPanel;
class Vector4 {
	int x,y;
	int x2,y2;
	boolean isDead=false;
}

public class CWorldEditorDisplay extends JPanel{
	/**
	 * 
	 */
	public static File SaveFile;
	
	private static final long serialVersionUID = 1L;
	Vector<CWorldEntity> Entities;
	Vector<String> Names;
	Vector<CWorldEntity> Level;
	Vector<Vector4> l1;
	Vector<Vector4> l2;
	Vector<Vector4> l3;
	private BufferedReader br;

	static Toolkit toolkit = Toolkit.getDefaultToolkit();
	CWorldEntity Draw;
	protected static int mouse_x;
	protected static int mouse_y;
	private String CurrentSelection;

	CWorldEntity Deleter;

	protected CSprite Background;

	protected boolean onEnt;
	protected boolean onTile;
	boolean DrawEnt = true;

	int layer;

	public CWorldEditorDisplay() {
		setPreferredSize(new Dimension(800, 608));
		setSize(new Dimension(800, 608));

		Entities = new Vector<CWorldEntity>();
		Names = new Vector<String>();
		Level = new Vector<CWorldEntity>();
		l1 = new Vector<Vector4>();
		l2 = new Vector<Vector4>();
		l3 = new Vector<Vector4>();
		//setBackground(Color.lightGray);
		Deleter = new CWorldEntity(4);
		Deleter.width=2;
		Deleter.height=2;
		layer=1;
	}

	void ChangeSize(int _x, int _y){
		setPreferredSize(new Dimension(_x, _y));
		setSize(new Dimension(_x, _y));
		this.repaint();
	}

	public void Save(){
		if (SaveFile==null){
			
			File dataDirectory = new File(".");
			final JFileChooser fc = new JFileChooser(dataDirectory);
			fc.addChoosableFileFilter(new MelFilter());
			
			int returnVal = fc.showSaveDialog(this);

	        if (returnVal == JFileChooser.APPROVE_OPTION)
	       		SaveFile = fc.getSelectedFile();
		}

		BufferedWriter bw;
		try {
			String p = SaveFile.getPath();
			if (p.substring(p.length()-4).compareToIgnoreCase(".mel") > 0){
				p = p + ".mel";
				System.out.println(""+ p);
				SaveFile.delete();
				SaveFile = new File(p);
			}
			bw = new BufferedWriter(new FileWriter(SaveFile));
			Iterator iter = Level.iterator();			

			bw.write(""+CWorldEditor.width);
			bw.newLine();
			bw.write(""+CWorldEditor.height);
			bw.newLine();
			bw.write(""+CWorldEditor.gridx);
			bw.newLine();
			bw.write(""+CWorldEditor.gridy);
			bw.newLine();
			File t = new File(Background.filename);
			bw.write("sprites/"+t.getName());
			bw.newLine();
			t = new File(CWorldEditor.Tileset.filename);
			bw.write("sprites/"+t.getName());
			bw.newLine();
			bw.newLine();
			
			while(iter.hasNext()){
				CWorldEntity Object = (CWorldEntity) iter.next();
				for (int a=0;a<Object.Arguments.length;a++){
					bw.write(""+Object.Arguments[a]+'|');
				}
				bw.newLine();
			}
			if (Level.isEmpty())
				bw.newLine();
			bw.newLine();
			
			iter = l1.iterator();
			while(iter.hasNext()){
				Vector4 Pos = (Vector4) iter.next();
				bw.write(""+Pos.x+'|');
				bw.write(""+Pos.y+'|');
				bw.write(""+Pos.x2+'|');
				bw.write(""+Pos.y2+'|');
				bw.newLine();
			}
			bw.newLine();
			iter = l2.iterator();
			while(iter.hasNext()){
				Vector4 Pos = (Vector4) iter.next();
				bw.write(""+Pos.x+'|');
				bw.write(""+Pos.y+'|');
				bw.write(""+Pos.x2+'|');
				bw.write(""+Pos.y2+'|');
				bw.newLine();
			}
			bw.newLine();
			iter = l3.iterator();
			while(iter.hasNext()){
				Vector4 Pos = (Vector4) iter.next();
				bw.write(""+Pos.x+'|');
				bw.write(""+Pos.y+'|');
				bw.write(""+Pos.x2+'|');
				bw.write(""+Pos.y2+'|');
				bw.newLine();
			}
			
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void paintComponent(Graphics g){
		//	super.paintComponent(g);
		g.setColor(Color.lightGray);
		g.fillRect(0, 0, CWorldEditor.width, CWorldEditor.height);
		
		if (Background != null)
			g.drawImage(Background.getImg(), 0, 0, Background.observer);
		
		g.setColor(Color.black);

		Deleter.Arguments[2]=mouse_x;
		Deleter.Arguments[3]=mouse_y;



		Iterator LevelIt;
		
		//***************
		//Should go at MRK1
		if (DrawEnt ){
			LevelIt = Level.iterator();
			CWorldEntity Entity = null;
			while(LevelIt.hasNext()){
				Entity = (CWorldEntity)LevelIt.next ();
				if (Entity!=null){
					if (Entity.isDead) {
						LevelIt.remove();
					}else
						Entity.Draw((Graphics2D) g);
				}
			}
		}
		//*************
		
		Vector4 Pos = null;
		
		LevelIt = l1.iterator();
		while(LevelIt.hasNext()){
			Pos = (Vector4)LevelIt.next();
			if (Pos!=null){
				if (Pos.isDead) {
					LevelIt.remove();
				}else
					g.drawImage(CWorldEditor.Tileset.getImg(), Pos.x,  Pos.y,  Pos.x+CWorldEditor.tileset.gridx,  Pos.y+CWorldEditor.tileset.gridy,Pos.x2, Pos.y2, Pos.x2+CWorldEditor.tileset.gridx, Pos.y2+CWorldEditor.tileset.gridy,  CWorldEditor.Tileset.observer);
			}
		}

		LevelIt = l2.iterator();
		while(LevelIt.hasNext()){
			Pos = (Vector4)LevelIt.next();
			if (Pos!=null){
				if (Pos.isDead) {
					LevelIt.remove();
				}else
					g.drawImage(CWorldEditor.Tileset.getImg(), Pos.x,  Pos.y,  Pos.x+CWorldEditor.tileset.gridx,  Pos.y+CWorldEditor.tileset.gridy,Pos.x2, Pos.y2, Pos.x2+CWorldEditor.tileset.gridx, Pos.y2+CWorldEditor.tileset.gridy,  CWorldEditor.Tileset.observer);
			}
		}

		//MRK1
		//Overlay
		
		LevelIt = l3.iterator();
		while(LevelIt.hasNext()){
			Pos = (Vector4)LevelIt.next();
			if (Pos!=null){
				if (Pos.isDead) {
					LevelIt.remove();
				}else
					g.drawImage(CWorldEditor.Tileset.getImg(), Pos.x,  Pos.y,  Pos.x+CWorldEditor.tileset.gridx,  Pos.y+CWorldEditor.tileset.gridy,Pos.x2, Pos.y2, Pos.x2+CWorldEditor.tileset.gridx, Pos.y2+CWorldEditor.tileset.gridy,  CWorldEditor.Tileset.observer);
			}
		}
		
		if (CWorldEditor.grid){
			for (int a=0;a<CWorldEditor.width;a+=CWorldEditor.gridx){
				g.drawLine(a, 0, a, CWorldEditor.height);
			}
			for (int b=0;b<CWorldEditor.height;b+=CWorldEditor.gridy){
				g.drawLine(0, b, CWorldEditor.width, b);
			}
		}
		
		if (Draw!=null && onEnt){
			if (mouse_x<CWorldEditor.width && mouse_y<CWorldEditor.height && mouse_x>=0 && mouse_y>=0){
				Draw.Arguments[2]=mouse_x;
				Draw.Arguments[3]=mouse_y;

				Draw.Draw(g);
			}
		}
		if (onTile && CWorldEditor.tileset != null && CWorldEditor.Tileset != null){
			g.drawImage(CWorldEditor.Tileset.getImg(), mouse_x, mouse_y, mouse_x+CWorldEditor.tileset.gridx, mouse_y+CWorldEditor.tileset.gridy,CWorldEditor.tileset.cx, CWorldEditor.tileset.cy, CWorldEditor.tileset.cx+CWorldEditor.tileset.gridx, CWorldEditor.tileset.cy+CWorldEditor.tileset.gridy,  CWorldEditor.Tileset.observer);
		}
	}

	void LoadEntities(File F){
		// Can it Find it?
		try{
			br = new BufferedReader(new FileReader(F));
		}catch(FileNotFoundException e){
			System.out.println("Cannot Find Data File, Aborting");
			System.exit(0);
		}
		//Can it Read it? (vista? lol)
		if (!F.canRead()){
			System.out.println("Cannot Read Data File, Aborting");
			System.exit(0);
		}

		int entityid=0;
		String line = "a";
		try {
			line = br.readLine();
			while(line!=null){
				int id=Integer.valueOf(line).intValue();
				int size=Integer.valueOf(br.readLine()).intValue();
				CWorldEntity A = new CWorldEntity(4+size);
				A.id=entityid;
				entityid+=1;
				A.Arguments[0]=id;
				A.Arguments[1]=4+size;
				line = br.readLine();
				Names.add(line);
				line = br.readLine();
				try{
					new BufferedReader(new FileReader(new File(line)));
				}catch(FileNotFoundException e){
					System.out.println("Cannot Find Sprite(s), Aborting");
					System.exit(0);
				}
				A.Sprite = new CSprite(line,Color.magenta);
				A.width = A.Sprite.width;
				A.height = A.Sprite.height;
				for (int a=0;a<size;a++){
					A.Arguments[a+4]=Integer.valueOf(br.readLine()).intValue();
				}
				Entities.add(A);
				line = br.readLine();
			};
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	JTree CreateTree(){
		DefaultMutableTreeNode everything = new DefaultMutableTreeNode("Entites");
		DefaultMutableTreeNode category = null;
		DefaultMutableTreeNode object = null;

		category = new DefaultMutableTreeNode("Entities");
		Iterator nameit = Names.iterator();
		while (nameit.hasNext()){
			object = new DefaultMutableTreeNode(nameit.next());
			category.add(object);
		}
		everything.add(category);

		return new JTree(everything);
	}

	public void getDraw(String _S){
		CurrentSelection = _S;
		Iterator nameitt = Names.iterator();
		int id=0;
		boolean find=false;
		while (nameitt.hasNext()){
			if (_S.compareTo((String) nameitt.next())==0){
				find=true;
				break;
			}
			id++;
		}
		if (find){
			if (mouse_x<CWorldEditor.width && mouse_y<CWorldEditor.height){
				CWorldEntity Found = Entities.elementAt(id); 
				Draw = new CWorldEntity(Found.Arguments.length);
				Draw.id = Found.id;
				Draw.Sprite = Found.Sprite; //Yay Pointers
				for (int a=0;a<Found.Arguments.length;a++){
					Draw.Arguments[a]=Found.Arguments[a];
				}

				Draw.Arguments[2]=mouse_x;
				Draw.Arguments[3]=mouse_y;
			}
		}else{
			Draw = null;
		}
	}

	public void Click(int button) {

	}

	public void Press(int button) {
		if (onEnt){
			if (button==3){
				//	this.Draw=null;
				Iterator LevelIt = Level.iterator();
				CWorldEntity Entity = null;
				while(LevelIt.hasNext()){
					Entity = (CWorldEntity)LevelIt.next ();
					if (Entity!=null){
						if (Entity.Arguments[2]==Deleter.Arguments[2] && Entity.Arguments[3]==Deleter.Arguments[3]) {
							Entity.isDead=true;
						}
					}
				}
			}
			if (button==1 && this.Draw!=null){
				Iterator LevelIt = Level.iterator();
				CWorldEntity Entity = null;
				while(LevelIt.hasNext()){
					Entity = (CWorldEntity)LevelIt.next ();
					if (Entity!=null){
						if (Entity.Arguments[2]==Deleter.Arguments[2] && Entity.Arguments[3]==Deleter.Arguments[3]) {
							Entity.isDead=true;
						}
					}
				}
				Level.add(this.Draw);
				getDraw(this.CurrentSelection);
				//	CW_Worldbuilder.isSaved=false;
			}
		}else if (onTile && CWorldEditor.Tileset != null && CWorldEditor.tileset != null){
			if (button==3){
				//	this.Draw=null;
				Iterator LevelIt;
				if (layer==1)
					LevelIt = l1.iterator();
				else if (layer==2)
					LevelIt = l2.iterator();	
				else
					LevelIt = l3.iterator();
				
				Vector4 Pos = null;
				while(LevelIt.hasNext()){
					Pos = (Vector4)LevelIt.next ();
					if (Pos!=null){
						if (Pos.x==Deleter.Arguments[2] && Pos.y==Deleter.Arguments[3]) {
							Pos.isDead=true;
						}
					}
				}
			}
			if (button==1){
				
				Iterator LevelIt;
				if (layer==1)
					LevelIt = l1.iterator();
				else if (layer==2)
					LevelIt = l2.iterator();	
				else
					LevelIt = l3.iterator();
				
				Vector4 Pos = null;
				while(LevelIt.hasNext()){
					Pos = (Vector4)LevelIt.next ();
					if (Pos!=null){
						if (Pos.x==Deleter.Arguments[2] && Pos.y==Deleter.Arguments[3]) {
							Pos.isDead=true;
						}
					}
				}
				Vector4 Add = new Vector4();
				Add.x = mouse_x;
				Add.y = mouse_y;
				Add.x2 = CWorldEditor.tileset.cx;
				Add.y2 = CWorldEditor.tileset.cy;
				
				
				if (layer==1){
					l1.add(Add);
				}
				if (layer==2)
					l2.add(Add);
				if (layer==3)
					l3.add(Add);
				//	CW_Worldbuilder.isSaved=false;
			}
		}
	}
}
