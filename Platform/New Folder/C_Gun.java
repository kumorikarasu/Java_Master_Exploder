
import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;

abstract class C_Gun extends C_Entity{
	int ammo;
	int maxammo;
	int clipsize;
	boolean isEquipped;
	boolean isHeld;
	boolean isAuto;
	int GunSpeed;

	abstract int Shoot(C_Entity Parent, int _x, int _y, float dir);
	abstract C_Gun Wep();
	abstract void drawHUD(Graphics g);
}

class C_USP extends C_Gun{

	C_USP(int _x,int _y){
		this.entityID=201; //200-249 1h series for weapons
		this.x=_x;
		this.y=_y;
		this.width=8;
		this.height=8;
		this.clipsize=12;
		this.ammo=12;
		this.isEquipped=false;
		this.isHeld=false;
		this.GunSpeed=10;//lower-faster higher-slower
		this.isAuto=false;
	}

	//@Override
//	@Override
	void Step(Iterator _it){
		if (!this.isEquipped){
			this.yv+=C_Global.Gravity;
			this.x+=this.xv;
			this.y+=this.yv;

			if (Math.abs(this.xv)>0) {
				this.xv/=1.1;
			}
			if (Math.abs(this.xv)<0.9) {
				this.xv=0;
			}

			Collision(_it);
		}
	}

	//@Override
//	@Override
	void Draw(Graphics g){
		if (this.isHeld || !this.isEquipped){
			g.setColor(Color.black);
			g.fillRect((int)this.x-C_Global.Camera_x, (int)this.y-C_Global.Camera_y, this.width, this.height);
		}
	}

	@Override
	int Shoot(C_Entity Parent, int _x, int _y, float dir){
		C_Bullet Bullet = new C_Bullet();
		Bullet.x=this.x;
		Bullet.y=this.y;
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

	void drawHUD(Graphics g){
		g.drawString("Ammo: "+ammo, 10, 70);
	}
};

class C_M4 extends C_Gun{

	C_M4(int _x,int _y){
		this.entityID=250; //250-299 2h series for weapons
		this.x=_x;
		this.y=_y;
		this.width=16;
		this.height=8;
		this.clipsize=30;
		this.ammo=60;
		this.isEquipped=false;
		this.isHeld=false;
		this.isAuto=true;
		this.GunSpeed=2;//lower-faster higher-slower
	}

	//@Override
//	@Override
	void Step(Iterator _it){
		if (!this.isEquipped){
			this.yv+=C_Global.Gravity;
			this.x+=this.xv;
			this.y+=this.yv;
			Collision(_it);
			if (Math.abs(this.xv)>0) {
				this.xv/=1.1;
			}
			if (Math.abs(this.xv)<0.9) {
				this.xv=0;
			}
		}
	}

	//@Override
//	@Override
	void Draw(Graphics g){
		if (this.isHeld || !this.isEquipped){
			g.setColor(Color.black);
			g.fillRect((int)this.x-C_Global.Camera_x, (int)this.y-C_Global.Camera_y, this.width, this.height);
		}
	}

	@Override
	int Shoot(C_Entity Parent, int _x, int _y, float dir){
		C_Bullet Bullet = new C_Bullet();
		Bullet.x=_x;
		Bullet.y=_y;
		Bullet.xv=(float) (Math.cos(dir-0.0625f+(Math.random()*0.125f))*15);
		Bullet.yv=(float) (Math.sin(dir-0.0625f+(Math.random()*0.125f))*15);
		Bullet.creator=Parent;
		Bullet.dmg=11;
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
	void drawHUD(Graphics g) {
		g.drawString("Ammo: "+ammo, 10, 70);
	}
};

class C_M8 extends C_Gun{

	C_M8(int _x,int _y){
		this.entityID=251; //250-299 2h series for weapons
		this.x=_x;
		this.y=_y;
		this.width=16;
		this.height=8;
		this.clipsize=6;
		this.ammo=6;
		this.isEquipped=false;
		this.isHeld=false;
		this.isAuto=false;
		this.GunSpeed=20;//lower-faster higher-slower
	}

	//@Override
//	@Override
	void Step(Iterator _it){
		if (!this.isEquipped){
			this.yv+=C_Global.Gravity;
			this.x+=this.xv;
			this.y+=this.yv;
			Collision(_it);
			if (Math.abs(this.xv)>0) {
				this.xv/=1.1;
			}
			if (Math.abs(this.xv)<0.9) {
				this.xv=0;
			}
		}
	}
	int Shoot(C_Entity Parent, int _x, int _y, float dir){
		for (int i=0;i<9;++i){
			C_Bullet Bullet = new C_Bullet();
			Bullet.x=_x;
			Bullet.y=y;
			Bullet.xv=(float) (Math.cos(dir-0.128f+(Math.random()*0.25f))*15);
			Bullet.yv=(float) (Math.sin(dir-0.128f+(Math.random()*0.25f))*15);
			Bullet.creator=Parent;
			Bullet.dmg=6;
			C_Global.World.Create(Bullet);
		}
		ammo-=1;

		return (int)Math.toDegrees(dir);
	}
	//@Override
//	@Override
	void Draw(Graphics g){
		if (this.isHeld || !this.isEquipped){
			g.setColor(Color.black);
			g.fillRect((int)this.x-C_Global.Camera_x, (int)this.y-C_Global.Camera_y, this.width, this.height);
		}
	}
	@Override
	C_Gun Wep() {
		// TODO Auto-generated method stub
		return new C_M8(0,0);
	}

	@Override
	void drawHUD(Graphics g) {
		g.drawString("Ammo: "+ammo, 10, 70);
	}
};

class C_RPG extends C_Gun{

	C_RPG(int _x,int _y){
		this.entityID=254; //250-299 2h series for weapons
		this.x=_x;
		this.y=_y;
		this.width=16;
		this.height=8;
		this.clipsize=1;
		this.ammo=1;
		this.isEquipped=false;
		this.isHeld=false;
		this.isAuto=false;
		this.GunSpeed=10;//lower-faster higher-slower
	}

	//@Override
//	@Override
	void Step(Iterator _it){
		if (!this.isEquipped){
			this.yv+=C_Global.Gravity;
			this.x+=this.xv;
			this.y+=this.yv;
			Collision(_it);
			if (Math.abs(this.xv)>0) {
				this.xv/=1.1;
			}
			if (Math.abs(this.xv)<0.9) {
				this.xv=0;
			}
		}
	}
	int Shoot(C_Entity Parent, int _x, int _y, float dir){
		C_Rocket Bullet = new C_Rocket();
		Bullet.x=_x;
		Bullet.y=y-10;
		Bullet.xv=(float) (Math.cos(dir-0.128f+(Math.random()*0.25f))*15);
		Bullet.yv=(float) (Math.sin(dir-0.128f+(Math.random()*0.25f))*15);
		Bullet.creator=Parent;
		Bullet.dmg=80;
		C_Global.World.Create(Bullet);
		ammo-=1;

		return (int)Math.toDegrees(dir);
	}
	//@Override
//	@Override
	void Draw(Graphics g){
		if (this.isHeld || !this.isEquipped){
			g.setColor(Color.black);
			g.fillRect((int)this.x-C_Global.Camera_x, (int)this.y-C_Global.Camera_y-10, this.width, this.height);
		}
	}
	@Override
	C_Gun Wep() {
		// TODO Auto-generated method stub
		return new C_RPG(0,0);
	}

	@Override
	void drawHUD(Graphics g) {
		g.drawString("Ammo: "+ammo, 10, 70);
	}
};

class C_FlameThrower extends C_Gun{

	int heat;
	boolean overheat;

	C_FlameThrower(int _x,int _y){
		this.entityID=252; //250-299 2h series for weapons
		this.x=_x;
		this.y=_y;
		this.width=16;
		this.height=8;
		this.clipsize=1;
		this.ammo=1;
		this.isEquipped=false;
		this.isHeld=false;
		this.isAuto=false;
		this.GunSpeed=0;//lower-faster higher-slower
		overheat=false;
	}

	//@Override
//	@Override
	void Step(Iterator _it){
		if (!this.isEquipped){
			this.yv+=C_Global.Gravity;
			this.x+=this.xv;
			this.y+=this.yv;
			Collision(_it);
			if (Math.abs(this.xv)>0) {
				this.xv/=1.1;
			}
			if (Math.abs(this.xv)<0.9) {
				this.xv=0;
			}
		}
		if (heat>100)
			overheat=true;
		if (heat>0)
			heat--;
		else
			overheat=false;
	}

	int Shoot(C_Entity Parent, int _x, int _y, float dir){
		if  (!overheat){
			for (int i=0;i<4;++i){
				C_Flame Bullet = new C_Flame();
				Bullet.x=_x;
				Bullet.y=this.y;
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
	void Draw(Graphics g){
		if (this.isHeld || !this.isEquipped){
			g.setColor(Color.black);
			g.fillRect((int)this.x-C_Global.Camera_x, (int)this.y-C_Global.Camera_y, this.width, this.height);
		}
	}
	@Override
	C_Gun Wep() {
		// TODO Auto-generated method stub
		return new C_FlameThrower(0,0);
	}

	@Override
	void drawHUD(Graphics g) {
		g.setColor(Color.red);
		g.drawString("Temperature:", 10, 46);
		g.fillRect(10,50,heat,10);
		if (overheat)
			g.drawString("Overheat", 10, 70);
	}
};


class C_Sniper extends C_Gun{

	C_Sniper(int _x,int _y){
		this.entityID=253; //250-299 2h series for weapons
		this.x=_x;
		this.y=_y;
		this.width=16;
		this.height=8;
		this.clipsize=30;
		this.ammo=30;
		this.isEquipped=false;
		this.isHeld=false;
		this.isAuto=true;
		this.GunSpeed=2;//lower-faster higher-slower
	}

	//@Override
//	@Override
	void Step(Iterator _it){
		if (!this.isEquipped){
			this.yv+=C_Global.Gravity;
			this.x+=this.xv;
			this.y+=this.yv;
			Collision(_it);
			if (Math.abs(this.xv)>0) {
				this.xv/=1.1;
			}
			if (Math.abs(this.xv)<0.9) {
				this.xv=0;
			}
		}
	}

	//@Override
//	@Override
	void Draw(Graphics g){
		if (this.isHeld || !this.isEquipped){
			g.setColor(Color.black);
			g.fillRect((int)this.x-C_Global.Camera_x, (int)this.y-C_Global.Camera_y, this.width, this.height);
		}
	}

	@Override
	int Shoot(C_Entity Parent, int _x, int _y, float dir){
		C_Bullet Bullet = new C_Bullet();
		Bullet.x=_x;
		Bullet.y=this.y+16;
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
		return new C_Sniper(0,0);
	}

	@Override
	void drawHUD(Graphics g) {
		g.drawString("Ammo: "+ammo, 10, 70);
	}
};