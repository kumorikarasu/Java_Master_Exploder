
import java.awt.Graphics;


public class CWorldEntity {
	int id;
	int Arguments[];
	CSprite Sprite;
	public boolean isDead = false;
	public int width,height;

	CWorldEntity(int _size){
		Arguments = new int[_size];
	}

	void Draw(Graphics g){
		if (Sprite!=null)
			g.drawImage(Sprite.getImg(), Arguments[2], Arguments[3], Sprite.observer);
		//g.drawRect(10,10,100,100);
		if (Arguments[2]>=CWorldEditor.width || Arguments[3]>=CWorldEditor.height){
			isDead=true;
		}
	}
}
