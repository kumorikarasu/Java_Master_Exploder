
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.awt.Robot;
import java.util.Collections;
import java.awt.image.VolatileImage;

public class C_Main extends Frame implements Runnable, KeyListener{
	static final int FPS = 60;
	static final int SCREEN_WIDTH = 640,
	SCREEN_HEIGHT = 480,
	BIT_DEPTH = 32,
	REFRESH_RATE = 75;
	BufferStrategy bs;
	int full = 10;
	private static final int GAMETICKS_PER_SECOND = 30;
	private static final int MILLISECONDS_PER_GAMETICK = 1000000000 / GAMETICKS_PER_SECOND;
	GraphicsConfiguration gc = getGraphicsConfiguration();
	GraphicsDevice gd = gc.getDevice();

	public C_Main(boolean fullscreen)   {
		Toolkit tk = Toolkit.getDefaultToolkit();
		Cursor invisCursor = tk.createCustomCursor(tk.createImage(""),new Point(),null);




		if(fullscreen) {
			setUndecorated(true);
			gd.setFullScreenWindow(this);
			gd.setDisplayMode(new DisplayMode(SCREEN_WIDTH,SCREEN_HEIGHT,BIT_DEPTH,REFRESH_RATE));
			setCursor(invisCursor);
			C_Global.fullScreen=true;
			this.setBackground(Color.black);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			setBounds(300,200,400,200);
			setUndecorated(true);
			setCursor(invisCursor);
			setResizable(false);
			setVisible(true);
			C_Global.fullScreen=false;
			this.setBackground(Color.black);
		}
		createBufferStrategy(2);

		addKeyListener(this);
		addMouseListener(C_Global.World);
		addMouseMotionListener(C_Global.World);

		menu=false;

		//Disable Default Tab Key Managment
		KeyboardFocusManager kfm = KeyboardFocusManager.getCurrentKeyboardFocusManager(); 
		kfm.setDefaultFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, Collections.EMPTY_SET);
		kfm.setDefaultFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, Collections.EMPTY_SET);

		Thread t = new Thread(this);
		t.start();
	}

	static int [] controls = new int[256];
	private int x=0;
	private boolean menu;
	public static boolean Quit=false;

	public void keyPressed(KeyEvent ke) {
		controls[ke.getKeyCode()] = 1; //key handlers should ONLY set flags. The interpretation of the flags is best left to the game loop
		if(ke.getKeyCode() == KeyEvent.VK_F10){
			try{
				C_SaveOptions.Write();
				C_Global.World.DestroyLevel();
				CO_Client.Disconnect();
				C_Global.World.inGame=false;
			}catch(java.lang.NullPointerException e){
				System.exit(0);
			}
			System.exit(0);
		}
	}

	public void keyReleased(KeyEvent ke)   {
		controls[ke.getKeyCode()] = 0; //key handlers should ONLY set flags. The interpretation of the flags is best left to the game loop
		menu=false;
	} 
	public void keyTyped(KeyEvent ke) {
		if (C_Global.World.isChat){
			if (ke.getKeyChar()==KeyEvent.VK_BACK_SPACE){
				if (C_Global.World.ChatString.length()>0)
					C_Global.World.ChatString=C_Global.World.ChatString.substring(0,C_Global.World.ChatString.length()-1);
			}else{
				C_Global.World.ChatString+=ke.getKeyChar();
			}
		}
		if (!C_Global.World.inGame){
			if (ke.getKeyChar()==KeyEvent.VK_BACK_SPACE){
				if (C_Global.World.Player.Nickname.length()>0){
					C_Global.World.Player.Nickname=C_Global.World.Player.Nickname.substring(0,C_Global.World.Player.Nickname.length()-1);
				}
			}else if (ke.getKeyChar()!=KeyEvent.VK_ESCAPE){
				if (C_Global.World.Player.Nickname.length()<33)
					C_Global.World.Player.Nickname+=ke.getKeyChar();
			}
		}
	}

	public static void main(String [] args) {
		new C_Main(false);
	}

	public void run() {
		//final int NANO_FRAME_LENGTH = 1000000000/FPS;
		//long startTime = System.nanoTime();
		int frameCount = 0;
		bs = getBufferStrategy();
		long previousTime = System.nanoTime();  
		long passedTimeFPS = 0;
		long passedTime = 0;  
		Robot r = null;
		try {
			r = new Robot();
		} catch (AWTException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		while(!Quit) {
			Controls(controls);
			++frameCount;
			if (full==-1)
				Render();
			else{
				Graphics2D g = (Graphics2D) bs.getDrawGraphics();
				g.setColor(new Color(0,0,0));
				g.fillRect(0,0,SCREEN_WIDTH,SCREEN_HEIGHT);
				g.setColor(new Color(255,0,0));
				g.drawString("Loading", 100, 100);
				bs.show();
				g.dispose();
			}
			Point Mouse = MouseInfo.getPointerInfo().getLocation();
			Point Loc = this.getLocation();
			C_Global.Mouse.x = Mouse.x - Loc.x;
			C_Global.Mouse.y = Mouse.y - Loc.y;
			if (hasFocus() && !C_Global.fullScreen){
				if (C_Global.Mouse.x<5) {
					r.mouseMove(Loc.x+5, C_Global.Mouse.y+Loc.y);
				}
				if (C_Global.Mouse.y<28) {
					r.mouseMove(Loc.x+C_Global.Mouse.x ,Loc.y+28);
				}
				if (C_Global.Mouse.y>SCREEN_HEIGHT+Loc.y-5) {
					r.mouseMove(Loc.x+C_Global.Mouse.x ,Loc.y+SCREEN_HEIGHT-5);
				}
				if (C_Global.Mouse.x>SCREEN_WIDTH+Loc.x-5) {
					r.mouseMove(Loc.x+SCREEN_WIDTH-5, C_Global.Mouse.y+Loc.y);
				}
			}
			long time = System.nanoTime();  
			passedTime += (time-previousTime);
			//passedTimeFPS += (time-previousTime);  
			previousTime = time;  
			//	if (passedTimeFPS > 1000000000){
			//	System.out.println((float)frameCount/(float)GAMETICKS_PER_SECOND);
			//  frameCount=0;
			//  }

			while (passedTime>MILLISECONDS_PER_GAMETICK)  
			{  
				Tick();
				passedTime-=MILLISECONDS_PER_GAMETICK; 
			}  
			if (full>0)
				full--;
			else if (full==0){
				if(C_Global.fullScreen) {
					setUndecorated(true);
					gd.setFullScreenWindow(this);
					gd.setDisplayMode(new DisplayMode(SCREEN_WIDTH,SCREEN_HEIGHT,BIT_DEPTH,REFRESH_RATE));
					//setCursor(invisCursor);
					C_Global.fullScreen=true;
					this.setBackground(Color.black);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else {
					
					setBounds(0,0,640,480);
					//this.setUndecorated(false);
					//setCursor(invisCursor);
					setResizable(false);
					setVisible(true);
					C_Global.fullScreen=false;
					this.setBackground(Color.black);
					
					
				}
				//gd.setFullScreenWindow(this);
				//gd.setFullScreenWindow(this);
				//gd.setDisplayMode(new DisplayMode(SCREEN_WIDTH,SCREEN_HEIGHT,BIT_DEPTH,REFRESH_RATE));
				full=-1;
			}

		}
		C_SaveOptions.Write();
		dispose();
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	void Controls(int[] controls){
	/*	if (controls[KeyEvent.VK_ALT]==1 && controls[KeyEvent.VK_ENTER]==1){
			if (gd.getFullScreenWindow()==null){
				gd.setFullScreenWindow(this);
				gd.setDisplayMode(new DisplayMode(SCREEN_WIDTH,SCREEN_HEIGHT,BIT_DEPTH,REFRESH_RATE));
			}else{
				gd.setFullScreenWindow(null);
			}
			controls[KeyEvent.VK_ENTER]=0;
			controls[KeyEvent.VK_ALT]=0;
		}*/
		if (controls[KeyEvent.VK_ESCAPE]==1){
			if (C_Global.World.isChat){
				C_Global.World.isChat=false;
				C_Global.World.TeamChat=false;
			}
			else{
				if (C_Global.World.CurrentLevel!=null && C_Global.World.Loading==0 && menu==false){
					if (C_Global.World.inGame)
						C_Global.World.inGame=false;
					else
						C_Global.World.inGame=true;
					menu=true;
				}
			}
			controls[KeyEvent.VK_ESCAPE]=0;
		}
		if (controls[KeyEvent.VK_TAB]==1){
			C_Global.World.inScoreBoard=true;
		}else{
			C_Global.World.inScoreBoard=false;
		}
		if (controls[KeyEvent.VK_T]==1){
			if (C_Global.World.inGame){
				if (!C_Global.World.isChat)
					C_Global.World.isChat=true;
				C_Global.World.TeamChat=false;
				/*	else{
					if (!C_Global.World.TeamChat){
						if (C_Global.World.ChatString.trim().equals("")){
							C_Global.World.isChat=false;
							C_Global.World.ChatString="";
						}else{
							CO_Client.send("00",C_Global.World.ChatString.trim());
							C_Global.World.ReceiveString=C_Global.World.ChatString.trim();
							C_Global.World.isChat=false;
							C_Global.World.ChatString="";
						}
					}
				}
				controls[KeyEvent.VK_T]=0;*/
			}
		}
		if (controls[KeyEvent.VK_Y]==1){
			if (C_Global.World.inGame){
				if (!C_Global.World.isChat){
					C_Global.World.isChat=true;
					C_Global.World.TeamChat=true;
				}
			}
		}
		if (controls[KeyEvent.VK_ENTER]==1){
			if (C_Global.World.ChatString.trim().equals("")){
				C_Global.World.isChat=false;
				C_Global.World.ChatString="";
			}else{
				if (C_Global.World.TeamChat){
					CO_Client.send("0"+C_Global.World.Player.Team,C_Global.World.ChatString.trim());
				}else{
					CO_Client.send("00",C_Global.World.ChatString.trim());
				}
				C_Global.World.ReceiveString=C_Global.World.ChatString.trim();
				C_Global.World.isChat=false;
				C_Global.World.ChatString="";
			}
			controls[KeyEvent.VK_ENTER]=0;
		}
		/*
		 * 				else{
					if (C_Global.World.TeamChat){
						if (C_Global.World.ChatString.trim().equals("")){
							C_Global.World.isChat=false;
							C_Global.World.ChatString="";
						}else{
							CO_Client.send("0"+C_Global.World.Player.Team,C_Global.World.ChatString.trim());
							C_Global.World.ReceiveString=C_Global.World.ChatString.trim();
							C_Global.World.isChat=false;
							C_Global.World.ChatString="";
						}
					}
				}
				controls[KeyEvent.VK_Y]=0;
		 */
		if (!C_Global.World.isChat){
			if (controls[KeyEvent.VK_I]==1){
				C_Global.World.Player.hp=0;
				C_Global.World.Player.killerID=""+CO_Client.ID;
			}
			if (controls[KeyEvent.VK_F]==1)
				C_Global.World.Player.Throw=true;
			if (controls[KeyEvent.VK_A]==1)
				C_Global.World.Player.Left=true;
			else
				C_Global.World.Player.Left=false;

			if (controls[KeyEvent.VK_D]==1)
				C_Global.World.Player.Right=true;
			else
				C_Global.World.Player.Right=false;

			if (controls[KeyEvent.VK_W]==1)
				C_Global.World.Player.Up=true;
			else
				C_Global.World.Player.Up=false;

			if (controls[KeyEvent.VK_R]==1)
				C_Global.World.Player.Reload=true;
		}
	}

	void Tick(){
		C_Global.World.Step();
	}
	void Render(){
		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		g.setColor(new Color(128,128,128));
		g.fillRect(0,0,SCREEN_WIDTH,SCREEN_HEIGHT);

		C_Global.World.Draw(g);
		//misc rendering;

		bs.show();
		g.dispose();
	}
}


