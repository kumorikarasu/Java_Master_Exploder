import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;

class CSprite {
	String filename;
	Image img;
	ImageObserver observer;
	int width, height;
	
	CSprite(String _filename){
		filename=_filename;
		img = CWorldEditorDisplay.toolkit.getImage(filename);
		width = img.getWidth(observer);
		height = img.getHeight(observer);
	}
	
	CSprite(String _filename, final Color _transparent){
		filename=_filename;
		img = CWorldEditorDisplay.toolkit.getImage(filename);
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