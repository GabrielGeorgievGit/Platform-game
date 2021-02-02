import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Enemy extends GameObject {
	
	private static final long serialVersionUID = 1L;
	private int shotTrigger = 0;
	private int HEALTH;
	public static final int DEF_SPEED = 2;
	public static final Color DEF_COLOR = Color.DARK_GRAY;
	public static final Color SHOT_DEF_COLOR = Color.red;
	private boolean ghost = false;
	private Color color;
	private int damage = 10;
	private Handler handler;
	public boolean shoot = false;
	CoolDown shotPause;
	public Enemy(int x, int y, ID id, int width, int height, int HEALTH, float speed, Color color, Handler handler) {
		super(x, y, id, width, height, color);
		curSpeed = speed;
		this.handler = handler;
		speedInUse[0] = -curSpeed;
		speedInUse[1] = curSpeed;
		
		defColor = color;
		this.color = color;
		
		this.HEALTH = HEALTH;
		//super.speedX = speedX;
		//super.speedY = speedY;
		
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 16, 16);
	}
	
	public void tick() {
		position(handler);
//		this.sidefaced = true;
		if(canSee(Ivan.player)) shoot = true;
		
//		if(Ivan.player.getY() == y) {
//			if(Ivan.player.getX() > x) sidefaced = true;
//			else sidefaced = false;
//			shoot = true;// da strelq kato vidi player x i y
//		}
		if(shotTrigger != 60) ++shotTrigger;
		//shoot = true;
		if(shoot && shotTrigger % 60 == 0) {
			shoot(this.sidefaced);
			shotTrigger = 0;
			shoot = false;
		}
		
		//else br = 0;
//		System.out.println(Ivan.player.health);
//		if(y <= 0 || y >= Ivan.HEIGHT - 32) speedY *= -1;
//		if(x <= 0 || x >= Ivan.WIDTH - 16) speedX *= -1;
//		x = Ivan.clamp(x, 0, Ivan.WIDTH - width);
//		y = Ivan.clamp(y, 0, Ivan.HEIGHT - height);
		//new Enemy(x, y,ID.Enemy, handler, color, 16, 16, 0.02f, handler));
	}
	
//	String direction(boolean X) {
//		if(X) return "right";
//		else return "left";
//	}
	
	void shoot(boolean dir) {
		
		if(dir) handler.addObject(new Shot(x+width, y + (height/2), ID.Shot ,20, 20, SHOT_DEF_COLOR, dir));
		else  handler.addObject(new Shot(x, y + (height/2), ID.Shot ,20, 20, SHOT_DEF_COLOR, dir));
		
//		if(shot.x >= Ivan.WIDTH) handler.removeObject(shot);
	}
	

	
	public void render(Graphics2D g) {
		g.setColor(color);
		g.fillRect(x, y, width, height);
		
	}
	
	class Shot extends GameObject {
		
		private static final long serialVersionUID = 1L;
		public static final int DEF_SPEED = 2;
		public final Color DEF_COLOR = Color.red;
		int speed = 20;
		public Shot(int x, int y, ID id, int width, int height, Color color, boolean dir) {
			super(x, y, id, width, height, color);
			gravity = 0;
			if(dir) speedX = speed;
			else speedX = -speed;
		}

		public void tick() {//System.out.println("BOOM");
			x += speedX;
			
//			speedInUse[1] = -10;
//			this.setSpeedX(10);
			objectDelete();//
			
//				
//			}
		}
		
		public void render(Graphics2D g) {
			g.setColor(this.DEF_COLOR);
			g.fillRect(x, y, width, height);
		}

		public Rectangle getBounds() {
			return new Rectangle((int)x, (int)y, 16, 16);
		}
		void objectDelete() {
			
			if(getBounds().intersects(Ivan.player.getBounds())) {
				handler.removeObject(this);
				Ivan.player.damage(damage);
				return;
			}
			final int a = handler.object.size();
			for(int i = 0; i < a; ++i) {
				GameObject o = handler.object.get(i);
//				if(this.equals(o)) continue;
				if(Ivan.Bound.doesCollide(x, y, width, height, handler.object).collide) {
				
					switch(o.id) {
					case Shot: 
					case Spike:
					case Door: continue;
					
					default: 
						handler.removeObject(this);
						return;
					}
			}
			}
			if(x + width >= Ivan.WIDTH || x <= 0 || y + height >= Ivan.HEIGHT) {
				//System.out.println("there");
				handler.removeObject(this);
				return;
			}
		}
	}
}
