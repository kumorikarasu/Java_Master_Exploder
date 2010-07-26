import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.MediaTracker; 

public class C_Global {
	static Toolkit toolkit = Toolkit.getDefaultToolkit();
	static int imageID = 0;
	static C_World World = new C_World();
	static MediaTracker mediaTracker = new MediaTracker(null);
	static int Camera_x=0;
	static int Camera_y=0;
	public static float Gravity = 0.5f;
	public static boolean fullScreen;
	public static Point Mouse = new Point(0,0);
	public static boolean Mousedown = false;
	public static int SpawnSuccess = 0;
	public static int SpawnRetries = 0;
	public static int Team=0;
	public static boolean ctf=false;
	static C_Flag red;
	static C_Flag blue;

	public static final float point_direction(float x, float y, float xx, float yy) {
		return (float) (Math.atan2(yy-y, xx-x));
	}
	public static final double point_direction(double x, double y, double xx, double yy) {
		return (Math.atan2(yy-y, xx-x));
	}
	public static final float point_distance(float x, float y, float xx, float yy) {
		return (float) Math.sqrt(Math.pow((xx-x),2)+Math.pow((yy-y),2));
	}
	public static final double point_distance(double x, double y, double xx, double yy) {
		return Math.sqrt(Math.pow((xx-x),2)+Math.pow((yy-y),2));
	}
	
	public static void drawSprite(Graphics g, C_Sprite spr, int x, int y){
		g.drawImage(spr.getImg(), x, y, spr.observer);
	}
	public static void drawSprite(Graphics g, C_Sprite spr, int x, int y, int width, int height){
		if (width>0)
			g.drawImage(spr.getImg(), x, y, width, height, spr.observer);
		else
			g.drawImage(spr.getImg(), x+Math.abs(width), y, width, height, spr.observer);
	}
	
	public static int SpriteID(C_Sprite _spr){
		if(_spr==C_SpriteList.RedPlayer)
			return 1; 
		if(_spr==C_SpriteList.BluePlayer)
			return 2;
		if(_spr==C_SpriteList.YellowPlayer)
			return 3;
		if(_spr==C_SpriteList.GreenPlayer)
			return 4;
		if(_spr==C_SpriteList.PurplePlayer)
			return 5;
		if(_spr==C_SpriteList.OrangePlayer)
			return 6;
		
		return 0;
	}
	
	public static C_Sprite IDSprite(int _ID){
		if (_ID==1)
			return C_SpriteList.RedPlayer;
		if (_ID==2)
			return C_SpriteList.BluePlayer;
		if (_ID==3)
			return C_SpriteList.YellowPlayer;
		if (_ID==4)
			return C_SpriteList.GreenPlayer;
		if (_ID==5)
			return C_SpriteList.PurplePlayer;
		if (_ID==6)
			return C_SpriteList.OrangePlayer;
		
		return C_SpriteList.NULL;
	}
}
