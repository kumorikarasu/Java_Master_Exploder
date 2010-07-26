import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;


public class CTileset extends JPanel{

	CSprite curser = new CSprite("data/tilesetcurser.png",Color.MAGENTA);
	protected int mouse_x;
	protected int mouse_y;
	protected int gridx=32,gridy=32;
	protected int mouse_button=0;
	protected int cx,cy;
	
	
	public void paintComponent(Graphics g){
		g.setColor(Color.lightGray);
		g.fillRect(0, 0, CWorldEditor.width, CWorldEditor.height);
		g.setColor(Color.black);
		
		if (CWorldEditor.Tileset != null){		
			g.drawImage(CWorldEditor.Tileset.getImg(), 0, 0, CWorldEditor.Tileset.observer);
			
			
			for (int a=CWorldEditor.gridx-1;a<CWorldEditor.Tileset.width-1;a+=CWorldEditor.gridx){
				g.drawLine(a, 0, a, CWorldEditor.Tileset.height-1);
			}
			for (int b=CWorldEditor.gridy-1;b<CWorldEditor.Tileset.height-1;b+=CWorldEditor.gridy){
				g.drawLine(0, b, CWorldEditor.Tileset.width-1, b);
			}
		}
		
		if (mouse_button==1){
				cx = ((int)(mouse_x / gridx)) * gridx;
				cy = ((int)(mouse_y / gridy)) * gridy;
				mouse_button=0;
		}
		int x=0;//X on main window
		int y=160;//Y on main window
		//this will draw the selected tile on main window, but right now its just on the tileset window, i dunno how to put it on main, thats for u to do
		//g.drawImage(CWorldEditor.Tileset.getImg(), x, y, x+gridx, y+gridy,cx, cy, cx+gridx, cy+gridy,  CWorldEditor.Tileset.observer);
		g.drawImage(curser.getImg(), cx, cy, curser.observer);
		
	}
}
