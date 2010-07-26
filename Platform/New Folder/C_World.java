
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MediaTracker;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JComponent;

class C_World extends JComponent implements MouseListener, MouseMotionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1566086431581220205L;

	//Cache Varibles
	Vector CacheList=new Vector();
	Iterator CacheListIt;
	Iterator LevelListIt;
	public static C_Level CurrentLevel;
	C_Player Player = new C_Player();

	private int mouse_button;

	float playertomouse;
	float playerdirmouse;

	public int Loading;

	private static C_MainMenu Menu = new C_MainMenu();
	public static boolean inGame=false;
	MediaTracker mediaTracker =  new MediaTracker(this);

	boolean inScoreBoard;

	public boolean isChat;
	public String ChatString="";
	public String ReceiveString="";
	private ArrayList<C_ChatString> MsgList = new ArrayList<C_ChatString>();

	boolean TeamChat;
	
	
	C_World(){
		this.Player.x=100;
		this.Player.y=100;
		//	CurrentLevel= new C_Level();
		this.CacheList.add(this.Player);
	};

	void CreateLevel(String levelname){
		Loading=100;
		Player.xv=0;
		Player.yv=0;
		if (this.CurrentLevel==null){
			this.CurrentLevel = new C_Level(levelname);
			this.CacheListIt=this.CacheList.iterator();
			while (this.CacheListIt.hasNext()){
				this.CurrentLevel.LevelList.add(this.CacheListIt.next());
			}
			this.CurrentLevel.PopulateLevel();
			this.CacheList=new Vector();
		}
	}


	void DestroyLevel() {
		if (this.Player!=null){
			for (int a=0;a<3;++a){
				this.Player.MainHand=null;
				this.Player.OffHand=null;
			}
		}
		if (this.CurrentLevel!=null && Loading==0){
			this.CacheList=new Vector();
			this.CacheListIt=this.CurrentLevel.LevelList.iterator();
			while (this.CacheListIt.hasNext()){
				C_Entity next=(C_Entity) this.CacheListIt.next();
				if (next.isCachable || next.isStatic){
					this.CacheList.add(next);
				}
			}
		}
		CurrentLevel=null;
	}

	public void Create(C_Entity Entity) {
		CacheList.add(Entity);
	}

	void Step(){
		//Begin Step
		playertomouse=C_Global.point_distance(Player.x+16,Player.y+16,C_Global.Camera_x+C_Global.Mouse.x,C_Global.Camera_y+C_Global.Mouse.y);
		playerdirmouse=C_Global.point_direction(Player.x+16,Player.y+16,C_Global.Camera_x+C_Global.Mouse.x,C_Global.Camera_y+C_Global.Mouse.y);
		Player.mouse_button=mouse_button;

		
		if (!ReceiveString.equals("")){
			MsgList.add(0, new C_ChatString(ReceiveString,1000));
			ReceiveString="";
		}
		
		//Main Step		
		if (CurrentLevel!=null && Loading==0){
			CurrentLevel.Step();
			if (CurrentLevel.isFinished){ 
				DestroyLevel();
			}


			CacheListIt=CacheList.iterator();
			while (CacheListIt.hasNext()){
				if (CurrentLevel!=null)
					CurrentLevel.LevelList.add(CacheListIt.next());
			}
			CacheList=new Vector();
		}
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	void Draw(Graphics2D g){
		if (inGame && Loading==0 && !C_MainMenu.loadlvl && CurrentLevel!=null){
			CurrentLevel.Draw(g);
			//Draw Hud
			if (Player.drawScope>0){
				g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, Player.drawScope));
				g.setColor(Color.black);
				g.fillRect(0, 0, C_Global.Mouse.x-100, 480);
				g.fillRect(C_Global.Mouse.x+100, 0, 640, 480);
				g.fillRect(C_Global.Mouse.x-100, 0, 200, C_Global.Mouse.y-100);
				g.fillRect(C_Global.Mouse.x-100, C_Global.Mouse.y+100, 200, 480);
				g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, Player.drawScope));
			}
			Player.noHud=false;
			g.setColor(Color.red);
			g.drawOval(C_Global.Mouse.x-6, C_Global.Mouse.y-6, 12, 12);
			g.setColor(Color.black);
			g.fillRect(9,19,102,12);
			g.drawString("Health:", 10, 16);
			g.setColor(Color.green);
			float hppercent = (float)Player.hp/(float)Player.maxhp;
			hppercent*=100;
			g.fillRect(10,20,(int)hppercent,10);
			g.setColor(Color.red);
			if (Player.ReloadTimer<100) {
				g.drawString("Reloading...", 10, 46);
				g.fillRect(10,50,Player.ReloadTimer,10);
			}
			C_MainMenu.DWep=false;
			if (inGame && inScoreBoard){
				g.setColor(Color.black);
				g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
				g.fillRect(100, 80, 440, 320);
				g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
				g.setColor(Color.white);
				g.drawRect(100, 80, 440, 320);
				g.drawString("Name", 110, 100);
				if(C_Global.ctf)
					g.drawString("Caps", 300, 100);
				g.drawString("Kills", 350, 100);
				g.drawString("Deaths", 400, 100);
				g.drawString("Ping", 450, 100);
				g.drawLine(100, 110, 540, 110);
				CO_SortedPlayer sortedList[] = new CO_SortedPlayer[31];
				CO_SortedPlayer dummy = new CO_SortedPlayer(-1,"",0,-9999,9999, 0, 0, 0);
				for(int a=0;a<31;a++){
					if(CO_Client.Client[a]!=null || a==CO_Client.ID){
						if(a==CO_Client.ID){
							sortedList[a]= new CO_SortedPlayer(CO_Client.ID,Player.Nickname,Player.Team, Player.Kills,Player.Deaths, Player.Caps, Player.Ping, Player.PingDraw);
						}else{
							sortedList[a]= new CO_SortedPlayer(a,CO_Client.Client[a].Nickname,CO_Client.Client[a].Team, CO_Client.Client[a].Kills,CO_Client.Client[a].Deaths, CO_Client.Client[a].Caps, CO_Client.Client[a].Ping, CO_Client.Client[a].PingDraw);
						}
					}else
						sortedList[a]=dummy;
				}
				
				int in, out;

				  for(out=1; out<31; out++)   // out is dividing line
				   {
				   CO_SortedPlayer temp = sortedList[out];    // remove marked item
				   in = out;           // start shifts at out
				   while(in>0 && sortedList[in-1].Kills >= temp.Kills) // until one is smaller,
				     {
					   sortedList[in] = sortedList[in-1];      // shift item right,
				     --in;            // go left one position
				     }
				   sortedList[in] = temp;         // insert marked item
				   } // end for
			    int drawpos=0;
			    int reddraw=16;
			    int bluedraw=166;
			    if (C_Global.Team!=0){
			    	g.setColor(Color.red);
					g.drawString("Team Red", 110, 130);
					g.drawLine(100, 132, 540, 132);
			    	g.setColor(Color.blue);
					g.drawString("Team Blue", 110, 280);
					g.drawLine(100, 282, 540, 282);
			    }
				for(int a=30;a>0;a--){
					if(sortedList[a]!=null && sortedList[a].ID!=-1){// && sortedList[a].ID!=1){
						if(sortedList[a].Team==1){
							g.setColor(Color.red);
							drawpos=reddraw;
							reddraw+=16;
						}else if(sortedList[a].Team==2){
							g.setColor(Color.blue);
							drawpos=bluedraw;
							bluedraw+=16;
						}else{
							g.setColor(Color.white);
						}
							g.drawString(sortedList[a].Nickname, 110, 130+drawpos);
							if(C_Global.ctf)
								g.drawString(""+sortedList[a].Caps, 300, 130+drawpos);
							g.drawString(""+sortedList[a].Kills, 350, 130+drawpos);
							g.drawString(""+sortedList[a].Deaths, 400, 130+drawpos);
							if(sortedList[a].PingDraw==0)
								sortedList[a].PingDraw=sortedList[a].Ping;
							g.drawString(""+sortedList[a].PingDraw, 450, 130+drawpos);
							drawpos+=16;
					}
				}
				g.setColor(Color.black);
			}
			if (inGame && isChat){
				g.setColor(Color.red);
				g.drawRect(10, 450, 250, 20);
				g.setColor(Color.black);
				g.fillRect(11, 451, 249, 19);
				g.setColor(Color.white);
				g.setFont(new Font("Courier New",Font.PLAIN,12));
				if (TeamChat){
					if (ChatString.length()>25){ 
						g.drawString("SayTeam: "+ChatString.substring(ChatString.length()-25), 11, 465);
					}else
						g.drawString("SayTeam: "+ChatString, 11, 465);	
				}else{
					if (ChatString.length()>29){ 
						g.drawString("Say: "+ChatString.substring(ChatString.length()-29), 11, 465);
					}else
						g.drawString("Say: "+ChatString, 11, 465);					
				}
			}
			
			g.setFont(new Font("Courier New",Font.PLAIN,12));
			g.setColor(Color.white);
			int up=0;
			Iterator<C_ChatString> it = MsgList.iterator();
			while (it.hasNext()){
				C_ChatString Msg = it.next();
				if (Msg.Timer>0){
					Msg.Timer--;
					up++;
					g.drawString(Msg.Msg, 10, 450-(up*15));	
				}else{
					it.remove();
				}
			}
		}else if (Loading>0){
			g.setColor(new Color(0x000000));
			g.fillRect(0,0,2000,2000);
			g.setColor(Color.white);
			g.drawString("Loading...",500,400);
			--Loading;
			C_Global.World.inGame=false;
		}else{ //Draw teh Main Menu Lulz
			Player.noHud=true;
			if (CurrentLevel!=null)
				CurrentLevel.Draw(g);
			if (CurrentLevel==null)
				Menu.Draw(g);
			else
				Menu.DrawWep(g);
		}
		g.setColor(Color.red);
		if (Player.spawnTimer<=0){
			g.setFont(new Font("MS SANS SERIF",Font.BOLD,32));
			g.drawString("Press Mouse Button To Spawn", 100, 200);
		}else if (Player.spawnTimer<30*5){
			g.setFont(new Font("MS SANS SERIF",Font.BOLD,32));
			g.drawString("Time until Spawn: "+(Player.spawnTimer/30+1), 200, 200);
		}
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void mouseClicked(MouseEvent e) {

	}
	public void mouseEntered(MouseEvent e) {

	}
	public void mouseExited(MouseEvent e) {

	}
	public void mousePressed(MouseEvent e) {
		mouse_button=e.getButton();
		if (!inGame){
			Menu.Mouse();

		}
	}
	public void mouseReleased(MouseEvent e) {
		mouse_button=0;

	}
	public void mouseDragged(MouseEvent e) {
	}
	public void mouseMoved(MouseEvent e) {
	}

	public void SetPlayerX(int i) {
		Player.x=i;
	}

	public void SetPlayerY(int i) {
		Player.y=i;
	}

	public float GetPlayerX(){
		return Player.x;
	}
	public float GetPlayerY(){
		return Player.y;
	}
	public float GetPlayerWidth(){
		return Player.width;
	}
	public float GetPlayerHeight(){
		return Player.height;
	}

}
