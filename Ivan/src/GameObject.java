import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;

public abstract class GameObject implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//coord and movement
	protected int x, y;
	protected ID id;
	public static float interval;
	protected float speedX, speedY;
	protected float saveY;
	protected boolean sidefaced;//left-false, right - true
	protected float curSpeed;
	//gravity
	protected static final float defGravity = 7;
	protected float gravityInUse = defGravity;
	protected float gravity = gravityInUse;
	
	//jump
	protected float jumpSpeed = -10;
	protected float jumpSpeedInUse = -10;
	protected float jumpHeight = 200;
	protected boolean jumpReady = false;
	protected float jumpTimer = 5;
	protected boolean tryJump = false;
	
	//dash
	protected static final int DEF_DASH_TIME = 100;
	protected int dashTime = DEF_DASH_TIME;
	protected boolean dashReady = true;
	protected static float DEF_DASH_SPEED = 10;
	protected float dashSpeed = DEF_DASH_SPEED;
	protected static final int DEF_DASH_CD = 5000;
	protected int dashCD = DEF_DASH_CD;
	
	//left/right movement
//	protected static final int DEF_SPEED = 5;
	public float[] speedInUse = new float[2];//0 left 1 right
	public float[] speed = new float[2];//0 left 1 right
	
	//special need
	protected Color color;
	protected Color defColor;//
	protected int width;
	protected int height;
	protected Timer timer;
	protected Rectangle wallBound;
	protected int health;
	
	protected transient BufferedImage sprite;
	
	protected transient BufferedImage[] right = 
			new BufferedImage[6];
	
	protected int rightIndex = 0;
	//secondary options
	protected boolean invincible = false;
	
	//protected boolean[] wallTouch = new boolean[5]; 
	//0 - W
	//1 - A
	//2 - S
	//3 - D
	//4 - Space
	public GameObject(int x, int y, ID id, int width, int height, Color color) {
		this.x = x;
		this.y = y;
		this.id = id;
		this.width = width;
		this.height = height;
		this.color = color;
	}
	
	public abstract void tick();
	public abstract void render(Graphics2D g);
	public abstract  Rectangle getBounds();//pri dokosvane
	
	public void setX(int x) {this.x = x;}
	public void setY(int y) {this.y = y;}
	public float getX() {return x;}
	public float getY() {return y;}
	public void setId(ID id) {this.id = id;}
	public ID getId() {return id;}
	
	public void setWidth(int width) {this.width = width;}
	public void setHeight(int height) {this.height = height;}
	public int getWidth() {return width;}
	public int getHeight() {return height;}
	
	public void setGravity(float gravity) {this.gravity = gravity;}
	public void getGravity(float gravity) {this.gravity = gravity;}
	
	public void setSpeedX(float speedX) {this.speedX = speedX;}
	public void setSpeedY(float speedY) {this.speedY = speedY;}
	public float getSpeedX() {return speedX;}
	public float getSpeedY() {return speedY;}
	
	public void makeInvincible() {this.invincible = true;}
	public void makeVulnerable() {this.invincible = false;}
	
	public void setColor(Color color) {this.color = color;}
	public Color getColor() {return this.color;}
	
	public void select() {this.color = Color.red;}
	public void unselect() {this.color = this.defColor;}
	
	protected void position(Handler handler) {
		wallBound(handler);
	}
	
	boolean canSee(GameObject o) {
		if(this.sidefaced) {
			if(this.x > o.x) return false;
		} else if(this.x < o.x) return false;
		
		for(int i = 0; i < Ivan.player.handler.object.size(); ++i) {
			GameObject obj = Ivan.player.handler.object.get(i);
			if(obj.id != ID.Wall) continue;
			if(obj.y <= this.y && obj.y + obj.height >= this.y + this.height)
				if(this.sidefaced) {
					if(obj.x >= this.x && obj.x <= this.x) return false;			
				} else if(obj.x <= this.x && obj.x >= this.x) return false;
		}
		  
		if((this.y <= o.y && this.y + this.height >= o.y) || 
			(this.y + this.height >= o.y && this.y <= o.y+o.height)) {
			//System.out.println(this.id + " can see " + o.id);
			return true;
		}
		
		return false;
	}
	
	public void jumping(float interval, float jumpSpeed) {
		jumpReady = false;
		int delay = 1000;
		int period = 1000;
		timer = new Timer();
		GameObject.interval = interval;
		timer.scheduleAtFixedRate(new TimerTask() {
			//private int interval;

			public void run() {
				if(GameObject.interval <= 1) {
					System.out.println("GGGG" + gravityInUse);
					gravity = gravityInUse;
					setSpeedY(gravity);
					timer.cancel();
					//jumpReady = false;
					return;
				}
				else setSpeedY(jumpSpeed);
				--GameObject.interval;
				System.out.println(GameObject.interval);
				

				//if(GameObject.interval <= 1) return;
				}
		}, delay, period);
	}
	
	protected void jumping() {
		if(this.y < saveY - jumpHeight) {
			 speedY = gravity;
			return;
		}
		
		if(jumpReady) {
			speedY = jumpSpeed;
			
		}
		jumpReady = false;
	}
	
	protected void wallBound(Handler handler) {
		//if(speedY < 0) speedY -= gravity;
		//speedY = gravity;
		if(tryJump) jumping();
		else speedY = gravity;
		
		Ivan.Bound bound = Ivan.Bound.doesCollide(x + speedX, y, width, height, handler.object);
		if(bound.collide) {
			wallBound = bound.bound;
			if(speedX < 0) x = wallBound.x + wallBound.width;
			else if(speedX > 0) x = wallBound.x - width;
			else x -= speedX;
		} else x += speedX;
		
		bound = Ivan.Bound.doesCollide(x, y + speedY, width, height, handler.object);
		if(bound.collide) {
			wallBound = bound.bound;
			if(speedY < 0) {
				y = wallBound.height + wallBound.y;
				speedY = gravity;//pipne li gore, pada
			}
			else if(speedY > 0) y = -height + wallBound.y;
			else y -= speedY;
		}else y += speedY;
		
		x = Ivan.clamp(x, 0, Ivan.WIDTH - width);
		y = Ivan.clamp(y, 0, Ivan.HEIGHT - height);
		//handler.addObject(new Trail(x, y,ID.Trail, Color.white, 32, 32, 0.05f, handler));
	}
	
	protected class Sprite {
		public int a = 5;
	}
}
