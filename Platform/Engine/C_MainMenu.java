import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class C_MainMenu {
	static C_Button NewGame;
	static C_Button ContinueGame;
	static C_Button Exit;
	static C_Button Disconnect;
	static C_Button Done;
	
	static C_Button Team0 = new C_Button(){
		void Action(){
			C_Global.World.Player.NewTeam = 0;
			Team1.isActive=false;
			Team2.isActive=false;
			TeamAuto.isActive=false;
			setActive(true);
		}
		void Setup() {
			x=10;
			y=50;
			width=100;
			height=30;
			Text="No Team";
			hc = new Color(0xAAAAAA);
			ac = new Color(0xFF0000);
			if(C_Global.Team==0)
				isActive=true;
			else
				isActive=false;
		}
	};
	
	static C_Button Team1 = new C_Button(){
		void Action(){
			C_Global.World.Player.NewTeam = 1;
			Team0.isActive=false;
			Team2.isActive=false;
			TeamAuto.isActive=false;
			setActive(true);
		}
		void Setup() {
			x=10;
			y=50;
			width=100;
			height=30;
			Text="Team Red";
			hc = new Color(0xAAAAAA);
			ac = new Color(0xFF0000);
			isActive=false;
		}
	};
	
	static C_Button Team2 = new C_Button(){
		void Action(){
			C_Global.World.Player.NewTeam = 2;
			Team1.isActive=false;
			Team0.isActive=false;
			TeamAuto.isActive=false;
			setActive(true);
		}
		void Setup() {
			x=10;
			y=100;
			width=100;
			height=30;
			Text="Team Blue";
			hc = new Color(0xAAAAAA);
			ac = new Color(0xFF0000);
			isActive=false;
		}
	};
	
	static C_Button TeamAuto = new C_Button(){
		void Action(){
			
			C_Global.World.Player.NewTeam = 1;
			Team2.isActive=false;
			Team1.isActive=false;
			Team0.isActive=false;
			setActive(true);
		}
		void Setup() {
			x=10;
			y=150;
			width=100;
			height=30;
			Text="Auto Select";
			hc = new Color(0xAAAAAA);
			ac = new Color(0xFF0000);
			if(C_Global.Team==1){
				Team2.isActive=false;
				Team1.isActive=false;
				Team0.isActive=false;
				isActive=true;
			}
		}
	};
	/////////////////////////////////////////////////////////////
	static C_Button Red = new C_Button(){
		void Action(){
			Purple.setActive(false);
			Green.setActive(false);
			Yellow.setActive(false);
			Orange.setActive(false);
			Blue.setActive(false);
			setActive(true);
			C_Global.World.Player.Sprite=C_SpriteList.RedPlayer;
		}
		void Setup() {
			x=300;
			y=50;
			width=50;
			height=30;
			Text="Red";
			hc = new Color(0xFFFFFF);
			ac = new Color(0xFFFFFF);
			bc = new Color(0xFF0000);
			isActive=true;
		}
	};
	static C_Button Green = new C_Button(){
		void Action(){
			Red.setActive(false);
			Purple.setActive(false);
			Yellow.setActive(false);
			Orange.setActive(false);
			Blue.setActive(false);
			setActive(true);
			C_Global.World.Player.Sprite=C_SpriteList.GreenPlayer;
		}
		void Setup() {
			x=420;
			y=50;
			width=50;
			height=30;
			Text="Green";
			hc = new Color(0xFFFFFF);
			ac = new Color(0xFFFFFF);
			bc = new Color(0x008800);
			isActive=false;
		}
	};
	static C_Button Yellow = new C_Button(){
		void Action(){
			Red.setActive(false);
			Green.setActive(false);
			Purple.setActive(false);
			Orange.setActive(false);
			Blue.setActive(false);
			setActive(true);
			C_Global.World.Player.Sprite=C_SpriteList.YellowPlayer;
		}
		void Setup() {
			x=300;
			y=150;
			width=50;
			height=30;
			Text="Yellow";
			hc = new Color(0xFFFFFF);
			ac = new Color(0xFFFFFF);
			bc = new Color(0xFFFF00);
			isActive=false;
		}
	};
	static C_Button Purple = new C_Button(){
		void Action(){
			Red.setActive(false);
			Green.setActive(false);
			Yellow.setActive(false);
			Orange.setActive(false);
			Blue.setActive(false);
			setActive(true);
			C_Global.World.Player.Sprite=C_SpriteList.PurplePlayer;
		}
		void Setup() {
			x=360;
			y=150;
			width=50;
			height=30;
			Text="Purple";
			hc = new Color(0xFFFFFF);
			ac = new Color(0xFFFFFF);
			bc = new Color(0xAA00AA);
			isActive=false;
		}
	};	static C_Button Orange = new C_Button(){
		void Action(){
			Red.setActive(false);
			Green.setActive(false);
			Yellow.setActive(false);
			Blue.setActive(false);
			Purple.setActive(false);
			setActive(true);
			C_Global.World.Player.Sprite=C_SpriteList.OrangePlayer;
		}
		void Setup() {
			x=420;
			y=150;
			width=50;
			height=30;
			Text="Orange";
			hc = new Color(0xFFFFFF);
			ac = new Color(0xFFFFFF);
			bc = new Color(0xFF8800);
			isActive=false;
		}
	};
	static C_Button Blue = new C_Button(){
		void Action(){
			Red.setActive(false);
			Green.setActive(false);
			Yellow.setActive(false);
			Orange.setActive(false);
			Purple.setActive(false);
			setActive(true);
			C_Global.World.Player.Sprite=C_SpriteList.BluePlayer;
		}
		void Setup() {
			x=360;
			y=50;
			width=50;
			height=30;
			Text="Blue";
			hc = new Color(0xFFFFFF);
			ac = new Color(0xFFFFFF);
			bc = new Color(0x0000FF);
			isActive=false;
		}
	};
	///////////////////////////////////////////////////////////////////
	static C_Button AssultRifle = new C_Button(){
		void Action(){
			C_Global.World.Player.SpawnWeapon = new C_M4(0,0);
			SniperRifle.isActive=false;
			Shotgun.isActive=false;
			FlameThrower.isActive=false;
			RocketLuncher.isActive=false;
			setActive(true);
		}
		void Setup() {
			x=120;
			y=50;
			width=100;
			height=30;
			Text="Assult Rifle";
			hc = new Color(0xAAAAAA);
			ac = new Color(0xFF0000);
			isActive=true;
		}
	};
	static C_Button SniperRifle = new C_Button(){
		void Action(){
			C_Global.World.Player.SpawnWeapon = new C_Sniper(0,0);
			AssultRifle.isActive=false;
			Shotgun.isActive=false;
			FlameThrower.isActive=false;
			RocketLuncher.isActive=false;
			setActive(true);
		}
		void Setup() {
			x=120;
			y=230;
			width=100;
			height=30;
			Text="Sniper Rifle";
			hc = new Color(0xAAAAAA);
			ac = new Color(0xFF0000);
			isActive=false;
		}
	};
	static C_Button Shotgun = new C_Button(){
		void Action(){
			SniperRifle.isActive=false;
			AssultRifle.isActive=false;
			FlameThrower.isActive=false;
			RocketLuncher.isActive=false;
			setActive(true);
			C_Global.World.Player.SpawnWeapon = new C_M8(0,0);

		}
		void Setup() {
			x=120;
			y=100;
			width=100;
			height=30;
			Text="Shotgun";
			hc = new Color(0xAAAAAA);
			ac = new Color(0xFF0000);
		}
	};
	static C_Button FlameThrower = new C_Button(){
		void Action(){
			SniperRifle.isActive=false;
			AssultRifle.isActive=false;
			Shotgun.isActive=false;
			RocketLuncher.isActive=false;
			setActive(true);
			C_Global.World.Player.SpawnWeapon = new C_FlameThrower(0,0);
		}
		void Setup() {
			x=120;
			y=150;
			width=100;
			height=30;
			Text="FlameThrower";
			hc = new Color(0xAAAAAA);
			ac = new Color(0xFF0000);
		}
	};
	static C_Button RocketLuncher = new C_Button(){
		void Action(){
			SniperRifle.isActive=false;
			AssultRifle.isActive=false;
			Shotgun.isActive=false;
			FlameThrower.isActive=false;
			setActive(true);
			C_Global.World.Player.SpawnWeapon = new C_RPG(0,0);
		}
		void Setup() {
			x=120;
			y=190;
			width=100;
			height=30;
			Text="Rocket Luncher";
			hc = new Color(0xAAAAAA);
			ac = new Color(0xFF0000);
		}
	};
	static boolean DWep = false;
	static boolean loadlvl=false;

	void Draw(Graphics2D g){
		
		DWep=false;
		NewGame = new C_Button(){
			void Action(){
				if (C_Global.World.CurrentLevel==null)
					loadlvl=true;
				
				C_Global.World.Loading=100;
				
			}
			void Setup() {
				x=100;
				y=150;
				width=100;
				height=30;
				Text="New Game";
				hc = new Color(0xAAAAAA);
			}
		};
		Exit = new C_Button(){
			void Action(){
				C_Main.Quit=true;
			}
			void Setup() {
				x=100;
				y=300;
				width=100;
				height=30;
				Text="Exit Game";
				hc = new Color(0xAAAAAA);
			}
		};
		
		if (loadlvl){
			CO_Client.go();
			CO_Client.Send.Update(0,0,0);
			C_World.inGame=true;
			loadlvl=false;
			C_SpriteList.Title1 = null;
		}
		int a = (int)(Math.random()+1);
		if (C_SpriteList.Title1==null){
			C_SpriteList.Title1 = new C_Sprite("title"+a+".jpg");
		}
		C_Global.drawSprite(g, C_SpriteList.Title1, 0, 0);
		NewGame.Draw(g,C_Global.Mouse.x,C_Global.Mouse.y);
		Exit.Draw(g,C_Global.Mouse.x,C_Global.Mouse.y);
		g.setColor(Color.red);
		g.drawOval(C_Global.Mouse.x-6, C_Global.Mouse.y-6, 12, 12);
		g.setFont(new Font("MS SANS SERIF",Font.BOLD,32)); //String name, int style, int size) 
		g.drawString("Master Exploder: DTA", 125, 75);
		g.setColor(Color.black);
		
		if (C_World.inGame == true)
			CO_Client.Send.Update(0,0,0);
		
		//g.setFont(new Font("MAERIAL",Font.PLAIN,16)); //String name, int style, int size)
		//g.drawString("123,WASD,R + Mouse", 200, 100);
		//g.setFont(new Font("AERIAL",Font.PLAIN,12)); //String name, int style, int size)
		//g.drawString("Version 0.1 Internal Alpha Stage", 10, 470);
		//g.drawString(""+mouse_button,100,75);
		//g.drawString(""+playertomouse,50,50);
		//g.drawString(""+playerdirmouse, 50, 70);
	}

	public void Mouse() {
		if (!DWep){
			NewGame.isPressed(C_Global.Mouse.x,C_Global.Mouse.y);
			Exit.isPressed(C_Global.Mouse.x,C_Global.Mouse.y);
		}else{
			SniperRifle.isPressed(C_Global.Mouse.x,C_Global.Mouse.y);
			AssultRifle.isPressed(C_Global.Mouse.x,C_Global.Mouse.y);
			Shotgun.isPressed(C_Global.Mouse.x,C_Global.Mouse.y);
			FlameThrower.isPressed(C_Global.Mouse.x,C_Global.Mouse.y);
			RocketLuncher.isPressed(C_Global.Mouse.x,C_Global.Mouse.y);
			Disconnect.isPressed(C_Global.Mouse.x,C_Global.Mouse.y);
			Done.isPressed(C_Global.Mouse.x,C_Global.Mouse.y);

			
			if(C_Global.Team==0){
				Team0.isPressed(C_Global.Mouse.x,C_Global.Mouse.y);
				Red.isPressed(C_Global.Mouse.x,C_Global.Mouse.y);
				Blue.isPressed(C_Global.Mouse.x,C_Global.Mouse.y);
				Green.isPressed(C_Global.Mouse.x,C_Global.Mouse.y);
				Yellow.isPressed(C_Global.Mouse.x,C_Global.Mouse.y);
				Purple.isPressed(C_Global.Mouse.x,C_Global.Mouse.y);
				Orange.isPressed(C_Global.Mouse.x,C_Global.Mouse.y);
			}else{
				Team1.isPressed(C_Global.Mouse.x,C_Global.Mouse.y);
				Team2.isPressed(C_Global.Mouse.x,C_Global.Mouse.y);
				TeamAuto.isPressed(C_Global.Mouse.x,C_Global.Mouse.y);
			}
		}
	}

	public void DrawWep(Graphics2D g) {

		DWep=true;

		Done = new C_Button(){
			void Action(){
				if (C_Global.Team==0){
					Team0.setActive(true);
					Team1.setActive(false);
					Team2.setActive(false);
					TeamAuto.setActive(false);
				}else if(!Team1.isActive && !Team2.isActive){
					Team0.setActive(false);
					TeamAuto.setActive(true);
				}
				C_Global.World.inGame=true;
			}
			void Setup() {
				x=420;
				y=350;
				width=100;
				height=30;
				Text="Done!";
				hc = new Color(0xAAAAAA);
			}
		};
		
		Disconnect = new C_Button(){
			void Action(){
				
				C_Global.World.DestroyLevel();
				CO_Client.Disconnect();
				DWep=false;
				C_Global.World.inGame=false;
				System.exit(0);
			}
			void Setup() {
				x=100;
				y=350;
				width=100;
				height=30;
				Text="Disconnect";
				hc = new Color(0xAAAAAA);
			}
		};
		
		
		SniperRifle.Draw(g,C_Global.Mouse.x,C_Global.Mouse.y);
		AssultRifle.Draw(g,C_Global.Mouse.x,C_Global.Mouse.y);
		Shotgun.Draw(g,C_Global.Mouse.x,C_Global.Mouse.y);
		FlameThrower.Draw(g,C_Global.Mouse.x,C_Global.Mouse.y);
		Disconnect.Draw(g,C_Global.Mouse.x,C_Global.Mouse.y);
		Done.Draw(g,C_Global.Mouse.x,C_Global.Mouse.y);
		RocketLuncher.Draw(g,C_Global.Mouse.x,C_Global.Mouse.y);
		if(C_Global.Team==0){
			Team0.Draw(g,C_Global.Mouse.x,C_Global.Mouse.y);
			Red.Draw(g,C_Global.Mouse.x,C_Global.Mouse.y);
			Blue.Draw(g,C_Global.Mouse.x,C_Global.Mouse.y);
			Green.Draw(g,C_Global.Mouse.x,C_Global.Mouse.y);
			Yellow.Draw(g,C_Global.Mouse.x,C_Global.Mouse.y);
			Orange.Draw(g,C_Global.Mouse.x,C_Global.Mouse.y);
			Purple.Draw(g,C_Global.Mouse.x,C_Global.Mouse.y);
		}else{
			Team1.Draw(g,C_Global.Mouse.x,C_Global.Mouse.y);
			Team2.Draw(g,C_Global.Mouse.x,C_Global.Mouse.y);
			TeamAuto.Draw(g,C_Global.Mouse.x,C_Global.Mouse.y);
		}
		g.setColor(Color.red);
		g.drawOval(C_Global.Mouse.x-6, C_Global.Mouse.y-6, 12, 12);
		g.setColor(Color.red);
		g.drawRect(300, 100, 250, 20);
		g.setColor(Color.black);
		g.fillRect(299, 101, 249, 19);
		g.setColor(Color.white);
		g.setFont(new Font("Courier New",Font.PLAIN,12));
		g.drawString(C_Global.World.Player.Nickname, 305, 115);
	}
}
