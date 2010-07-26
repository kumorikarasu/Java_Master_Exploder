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


public class CWorldEditorDisplay extends JPanel{
	/**
	 * 
	 */
	public static File SaveFile;
	
	private static final long serialVersionUID = 1L;
	Vector<CWorldEntity> Entities;
	Vector<String> Names;
	Vector<CWorldEntity> Level;
	private BufferedReader br;

	static Toolkit toolkit = Toolkit.getDefaultToolkit();
	CWorldEntity Draw;
	protected int mouse_x;
	protected int mouse_y;
	private String CurrentSelection;

	CWorldEntity Deleter;

	public CWorldEditorDisplay() {
		setPreferredSize(new Dimension(800, 608));
		setSize(new Dimension(800, 608));

		Entities = new Vector<CWorldEntity>();
		Names = new Vector<String>();
		Level = new Vector<CWorldEntity>();
		//setBackground(Color.lightGray);
		Deleter = new CWorldEntity(4);
		Deleter.width=2;
		Deleter.height=2;
	}

	void ChangeSize(int _x, int _y){
		setPreferredSize(new Dimension(_x, _y));
		setSize(new Dimension(_x, _y));
		this.repaint();
	}

	public void Save(){
		if (SaveFile==null){
			final JFileChooser fc = new JFileChooser();
			int returnVal = fc.showSaveDialog(this);

	        if (returnVal == JFileChooser.APPROVE_OPTION)
	       		SaveFile = fc.getSelectedFile();
		}

		BufferedWriter bw;
		try {
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

			while(iter.hasNext()){
				CWorldEntity Object = (CWorldEntity) iter.next();
				for (int a=0;a<Object.Arguments.length;a++){
					bw.write(""+Object.Arguments[a]+'|');
				}
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
		g.setColor(Color.black);

		Deleter.Arguments[2]=mouse_x;
		Deleter.Arguments[3]=mouse_y;

		if (Draw!=null){
			if (mouse_x<CWorldEditor.width && mouse_y<CWorldEditor.height && mouse_x>=0 && mouse_y>=0){
				Draw.Arguments[2]=mouse_x;
				Draw.Arguments[3]=mouse_y;

				Draw.Draw(g);
			}
		}

		Iterator LevelIt = Level.iterator();
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

		if (CWorldEditor.grid){
			for (int a=0;a<CWorldEditor.width;a+=CWorldEditor.gridx){
				g.drawLine(a, 0, a, CWorldEditor.height);
			}
			for (int b=0;b<CWorldEditor.height;b+=CWorldEditor.gridy){
				g.drawLine(0, b, CWorldEditor.width, b);
			}
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

	}
}
