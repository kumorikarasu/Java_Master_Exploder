import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;

class C_Sprite {
	String filename;
	Image img;
	ImageObserver observer;
	int width, height;
	
	C_Sprite(String _filename){
		filename="Sprites/"+_filename;
		img = C_Global.toolkit.getImage(filename);
		width = img.getWidth(observer);
		height = img.getHeight(observer);
	}
	
	C_Sprite(String _filename, final Color _transparent){
		filename="Sprites/"+_filename;
		img = C_Global.toolkit.getImage(filename);
		ImageFilter filter = new RGBImageFilter() {
		      // the color we are looking for... Alpha bits are set to opaque
		      public int markerRGB = _transparent.getRGB() | 0xFF000000;

		      public final int filterRGB(int x, int y, int rgb) {
		        if ( ( rgb | 0xFF000000 ) == markerRGB ) {
		          // Mark the alpha bits as zero - transparent
		          return 0x00FFFFFF & rgb;
		          }
		        else {
		          // nothing to do
		          return rgb;
		          }
		        }
		      }; 

		    ImageProducer ip = new FilteredImageSource(img.getSource(), filter);
		    img = Toolkit.getDefaultToolkit().createImage(ip);
	}
	
	public Image getImg(){
		return img;
	}	
}
class C_SpriteList{
	static C_Sprite RedPlayer=new C_Sprite("red.PNG", Color.magenta);
	static C_Sprite GreenPlayer=new C_Sprite("green.PNG", Color.magenta);
	static C_Sprite YellowPlayer=new C_Sprite("yellow.PNG", Color.magenta);
	static C_Sprite PurplePlayer=new C_Sprite("purple.PNG", Color.magenta);
	static C_Sprite BluePlayer=new C_Sprite("blue.PNG", Color.magenta);
	static C_Sprite OrangePlayer=new C_Sprite("orange.PNG", Color.magenta);
	static C_Sprite NullPlayer=new C_Sprite("darkgray.PNG", Color.magenta);
	static C_Sprite white = new C_Sprite("white.PNG", Color.magenta);
	static C_Sprite M4 = new C_Sprite("gun_as.PNG", Color.magenta);
	static C_Sprite M8 = new C_Sprite("gun_shotty.PNG", Color.magenta);
	static C_Sprite Sniper=new C_Sprite("gun_snipe.PNG", Color.magenta);
	static C_Sprite FlameThrower;
	static C_Sprite Block1 = new C_Sprite("BLOCK2.PNG");
	static C_Sprite NULL=new C_Sprite("white.PNG", Color.magenta);
	static C_Sprite Title1;// = new C_Sprite("title1.jpg");
}
