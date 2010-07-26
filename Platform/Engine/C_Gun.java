
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Iterator;

abstract class C_Gun extends C_Entity{
	int ammo;
	int maxammo;
	int clipsize;
	boolean isEquipped;
	boolean isHeld;
	boolean isAuto;
	int GunSpeed;
	int ReloadTime;
	int dir;

	abstract int Shoot(C_Entity Parent, int _x, int _y, float dir);
	abstract C_Gun Wep();
	abstract void drawHUD(Graphics2D g);
}

class C_USP extends C_Gun{

	C_USP(int _x,int _y){
		entityID=201; //200-249 1h series for weapons
		x=_x;
		y=_y;
		width=8;
		height=8;
		clipsize=12;
		ammo=12;
		isEquipped=false;
		isHeld=false;
		GunSpeed=10;//lower-faster higher-slower
		isAuto=false;
		ReloadTime=60;
		
	}

	//@Override
//	@Override
	void Step(Iterator _it){
		if (!isEquipped){
			yv+=C_Global.Gravity;
			x+=xv;
			y+=yv;

			if (Math.abs(xv)>0) {
				xv/=1.1;
			}
			if (Math.abs(xv)<0.9) {
				xv=0;
			}

			Collision(_it);
		}
	}

	//@Override
//	@Override
	void Draw(Graphics2D g){
		if (isHeld || !isEquipped){
			g.setColor(Color.black);
			g.fillRect((int)x-C_Global.Camera_x, (int)y-C_Global.Camera_y, width, height);
		}
	}

	@Override
	int Shoot(C_Entity Parent, int _x, int _y, float dir){
		int dmg=20;
		if (Parent.entityID==103)
			dmg=0;
		C_Bullet Bullet = new C_Bullet((int)x, (int)y, dmg);
		Bullet.xv=(float) (Math.cos((dir))*15);
		Bullet.yv=(float) (Math.sin((dir))*15);
		Bullet.creator=Parent;
		C_Global.World.Create(Bullet);
		ammo-=1;
		return (int)Math.toDegrees(dir);
	}

	@Override
	C_Gun Wep() {
		// TODO Auto-generated method stub
		return new C_USP(0,0);
	}

	void drawHUD(Graphics2D g){
		g.drawString("Ammo: "+ammo, 10, 70);
	}
};

class C_M4 extends C_Gun{

	C_M4(int _x,int _y){
		entityID=250; //250-299 2h series for weapons
		x=_x;
		y=_y;
		width=32;
		height=17;
		clipsize=30;
		ammo=30;
		isEquipped=false;
		isHeld=false;
		isAuto=true;
		GunSpeed=2;//lower-faster higher-slower
		ReloadTime=80;
		dir=1;
	}

	//@Override
//	@Override
	void Step(Iterator _it){
		if (!isEquipped){
			yv+=C_Global.Gravity;
			x+=xv;
			y+=yv;
			Collision(_it);
			if (Math.abs(xv)>0) {
				xv/=1.1;
			}
			if (Math.abs(xv)<0.9) {
				xv=0;
			}
		}
	}

	//@Override
//	@Override
	void Draw(Graphics2D g){
		if (isHeld || !isEquipped){
			g.setColor(Color.black);
			//g.fillRect((int)x-C_Global.Camera_x, (int)y-C_Global.Camera_y, width, height);
			C_Global.drawSprite(g, C_SpriteList.M4, (int)x-C_Global.Camera_x, (int)y-C_Global.Camera_y, width*dir, height);
		}
	}

	@Override
	int Shoot(C_Entity Parent, int _x, int _y, float dir){
		int dmg=13;
		if (Parent.entityID==103)
			dmg=0;
		C_Bullet Bullet = new C_Bullet(_x, _y, dmg);
		Bullet.xv=(float) (Math.cos(dir-0.0625f+(Math.random()*0.125f))*15);
		Bullet.yv=(float) (Math.sin(dir-0.0625f+(Math.random()*0.125f))*15);
		Bullet.creator=Parent;
		C_Global.World.Create(Bullet);
		ammo-=1;
		return (int)Math.toDegrees(dir);
	}

	@Override
	C_Gun Wep() {
		// TODO Auto-generated method stub
		return new C_M4(0,0);
	}
	@Override
	void drawHUD(Graphics2D g) {
		g.drawString("Ammo: "+ammo, 10, 70);
	}
};

class C_M8 extends C_Gun{

	C_M8(int _x,int _y){
		entityID=251; //250-299 2h series for weapons
		x=_x;
		y=_y;
		width=44;
		height=15;
		clipsize=6;
		ammo=6;
		isEquipped=false;
		isHeld=false;
		isAuto=false;
		GunSpeed=20;//lower-faster higher-slower
		ReloadTime=80;
	}

	//@Override
//	@Override
	void Step(Iterator _it){
		if (!isEquipped){
			yv+=C_Global.Gravity;
			x+=xv;
			y+=yv;
			Collision(_it);
			if (Math.abs(xv)>0) {
				xv/=1.1;
			}
			if (Math.abs(xv)<0.9) {
				xv=0;
			}
		}
	}
	int Shoot(C_Entity Parent, int _x, int _y, float dir){
		int dmg=6;
		if (Parent.entityID==103)
			dmg=0;
		for (int i=0;i<8;++i){
			C_Bullet Bullet = new C_Bullet(_x, (int)y, dmg);
			Bullet.xv=(float) (Math.cos(dir-0.128f+(Math.random()*0.25f))*15);
			Bullet.yv=(float) (Math.sin(dir-0.128f+(Math.random()*0.25f))*15);
			Bullet.creator=Parent;
			C_Global.World.Create(Bullet);
		}
		ammo-=1;

		return (int)Math.toDegrees(dir);
	}
	//@Override
//	@Override
	void Draw(Graphics2D g){
		if (isHeld || !isEquipped){
			g.setColor(Color.black);
			//g.fillRect((int)x-C_Global.Camera_x, (int)y-C_Global.Camera_y, width, height);
			C_Global.drawSprite(g, C_SpriteList.M8, (int)x-C_Global.Camera_x, (int)y-C_Global.Camera_y, 44*dir, 15);
		}
	}
	@Override
	C_Gun Wep() {
		// TODO Auto-generated method stub
		return new C_M8(0,0);
	}

	@Override
	void drawHUD(Graphics2D g) {
		g.drawString("Ammo: "+ammo, 10, 70);
	}
};

class C_FlameThrower extends C_Gun{

	int heat;
	boolean overheat;

	C_FlameThrower(int _x,int _y){
		entityID=252; //250-299 2h series for weapons
		x=_x;
		y=_y;
		width=16;
		height=8;
		clipsize=1;
		ammo=1;
		isEquipped=false;
		isHeld=false;
		isAuto=false;
		GunSpeed=0;//lower-faster higher-slower
		overheat=false;
		ReloadTime=-1;
	}

	//@Override
//	@Override
	void Step(Iterator _it){
		if (!isEquipped){
			yv+=C_Global.Gravity;
			x+=xv;
			y+=yv;
			Collision(_it);
			if (Math.abs(xv)>0) {
				xv/=1.1;
			}
			if (Math.abs(xv)<0.9) {
				xv=0;
			}
		}
		if (heat>100)
			overheat=true;
		if (overheat && heat<50)
			overheat=false;
		if (heat>0)
			heat--;
		else
			overheat=false;
	}

	int Shoot(C_Entity Parent, int _x, int _y, float dir){

		if  (!overheat){
			int dmg=3;
			if (Parent.entityID==103)
				dmg=0;
			for (int i=0;i<4;++i){
				C_Flame Bullet = new C_Flame(_x, (int)y, dmg);
				Bullet.xv=(float) (Math.cos(dir-0.0625f+(Math.random()*0.125f))*12);
				Bullet.yv=(float) (Math.sin(dir-0.0625f+(Math.random()*0.125f))*12);
				Bullet.creator=Parent;
				C_Global.World.Create(Bullet);
			}
			heat+=3;
		}

		return (int)Math.toDegrees(dir);
	}
	//@Override
//	@Override
	void Draw(Graphics2D g){
		if (isHeld || !isEquipped){
			g.setColor(Color.black);
			g.fillRect((int)x-C_Global.Camera_x, (int)y-C_Global.Camera_y, width, height);
		}
	}
	@Override
	C_Gun Wep() {
		// TODO Auto-generated method stub
		return new C_FlameThrower(0,0);
	}

	@Override
	void drawHUD(Graphics2D g) {
		g.setColor(Color.red);
		g.drawString("Temperature:", 10, 46);
		g.fillRect(10,50,heat,10);
		if (overheat)
			g.drawString("Overheat", 10, 70);
	}
};


class C_Sniper extends C_Gun{

	C_Sniper(int _x,int _y){
		entityID=253; //250-299 2h series for weapons
		x=_x;
		y=_y;
		width=48;
		height=20;
		clipsize=1;
		ammo=1;
		isEquipped=false;
		isHeld=false;
		isAuto=false;
		GunSpeed=10;//lower-faster higher-slower
		ReloadTime=100;
	}

	//@Override
//	@Override
	void Step(Iterator _it){
		if (!isEquipped){
			yv+=C_Global.Gravity;
			x+=xv;
			y+=yv;
			Collision(_it);
			if (Math.abs(xv)>0) {
				xv/=1.1;
			}
			if (Math.abs(xv)<0.9) {
				xv=0;
			}
		}
	}

	//@Override
//	@Override
	void Draw(Graphics2D g){
		if (isHeld || !isEquipped){
			g.setColor(Color.black);
			//g.fillRect((int)x-C_Global.Camera_x, (int)y-C_Global.Camera_y, width, height);
			C_Global.drawSprite(g, C_SpriteList.Sniper, (int)x-C_Global.Camera_x, (int)y-C_Global.Camera_y, width*dir, height);
		}
	}

	@Override
	int Shoot(C_Entity Parent, int _x, int _y, float dir){
		int dmg=90;
		if (Parent.entityID==103)
			dmg=0;
		C_SniperBullet Bullet = new C_SniperBullet(_x, _y, dmg, dir);
		Bullet.creator=Parent;
		C_Global.World.Create(Bullet);
		ammo--;
		return (int)Math.toDegrees(dir);
	}
	@Override
	C_Gun Wep() {
		// TODO Auto-generated method stub
		return new C_Sniper(0,0);
	}

	@Override
	void drawHUD(Graphics2D g) {
		g.drawString("Ammo: "+ammo, 10, 70);
	}
};
class C_Grenade extends C_Gun{

	int timer=0;
	
	C_Grenade(int _x,int _y, int _timer){
		entityID=254; //250-299 2h series for weapons
		x=_x;
		y=_y;
		width=16;
		height=8;
		clipsize=1;
		ammo=1;
		isEquipped=false;
		isHeld=false;
		isAuto=false;
		GunSpeed=0;//lower-faster higher-slower
		timer=_timer;
		ReloadTime=-1;
	}

	//@Override
//	@Override
	void Step(Iterator _it){
		yv+=C_Global.Gravity;
		x+=xv;
		y+=yv;
		Collision(_it);
		if (Math.abs(xv)>0) {
			xv/=1.05;
		}
		if (Math.abs(xv)<0.5) {
			xv=0;
		}
		timer--;
		if(timer==0){
			isDead=true;
			C_Global.World.Create(new C_Explosion((int)x, (int)y, 3));
		}
			
	}


	int Shoot(C_Entity Parent, int _x, int _y, float dir){
		xv=-(x-_x)/20;
		yv=-(y-_y)/10;
		if(xv>10)xv=10;
		if(xv<-10)xv=-10;
		if(yv>10)yv=10;
		if(yv<-10)yv=-10;
		return 0;
	}
//	@Override
//	@Override
	void Draw(Graphics2D g){
		if (!isEquipped){
			g.setColor(Color.black);
			g.fillRect((int)x-C_Global.Camera_x, (int)y-C_Global.Camera_y, width, height);
		}
	}
	@Override
	C_Gun Wep() {
		// TODO Auto-generated method stub
		return new C_FlameThrower(0,0);
	}

	@Override
	void drawHUD(Graphics2D g) {
	}
};
class C_RPG extends C_Gun{

	C_RPG(int _x,int _y){
		entityID=254; //250-299 2h series for weapons
		x=_x;
		y=_y;
		width=16;
		height=8;
		clipsize=1;
		ammo=1;
		isEquipped=false;
		isHeld=false;
		isAuto=false;
		GunSpeed=10;//lower-faster higher-slower
		ReloadTime=60;
	}

	//@Override
//	@Override
	void Step(Iterator _it){
		if (!isEquipped){
			yv+=C_Global.Gravity;
			x+=xv;
			y+=yv;
			Collision(_it);
			if (Math.abs(xv)>0) {
				xv/=1.1;
			}
			if (Math.abs(xv)<0.9) {
				xv=0;
			}
		}
	}
	int Shoot(C_Entity Parent, int _x, int _y, float dir){
		C_Rocket Bullet = new C_Rocket(_x, (int)y-10, 80);
		Bullet.xv=(float) (Math.cos(dir-0.128f+(Math.random()*0.25f))*15);
		Bullet.yv=(float) (Math.sin(dir-0.128f+(Math.random()*0.25f))*15);
		Bullet.creator=Parent;
		C_Global.World.Create(Bullet);
		ammo-=1;

		return (int)Math.toDegrees(dir);
	}
	//@Override
//	@Override
	void Draw(Graphics2D g){
		if (isHeld || !isEquipped){
			g.setColor(Color.black);
			g.fillRect((int)x-C_Global.Camera_x, (int)y-C_Global.Camera_y-10, width, height);
		}
	}
	@Override
	C_Gun Wep() {
		// TODO Auto-generated method stub
		return new C_RPG(0,0);
	}

	@Override
	void drawHUD(Graphics2D g) {
		g.drawString("Ammo: "+ammo, 10, 70);
	}
};

