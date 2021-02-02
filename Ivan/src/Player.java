import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;


public class Player extends GameObject {
	
	private static final long serialVersionUID = 1L;
	
	public static final float DEF_SPEED = 3;
	public float curSpeed;
	public static int DEF_HEALTH = 100;
	public int health;
	public static Color DEF_COLOR = Color.white;
	
	Random r = new Random();
	Handler handler;
//	private BufferedImage sprite;
	//transient final SpriteSheet ss = new SpriteSheet(Ivan.playerSpriteSheet);
	
	public Player(int x, int y, ID id, int width, int height, int HEALTH, float speed, Color color, Handler handler) {
		super(x, y, id, width, height, color);
		this.handler = handler;
		this.health = HEALTH;
		
		curSpeed = speed;
		setSpeed(curSpeed);
		
		defColor = color;
		
		//sprite = SpriteSheet.grabImage(Ivan.playerSpriteSheet, 3, 1, 9, 15);
		loadPostions();
	}
	
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}
	
	public void tick() {
		position(handler);
	}
	
	public void render(Graphics2D g) {
		
	    
//	    g2.scale(Ivan.scale, Ivan.scale);
		
	    g.setColor(this.color);
	    //g.fillRect((int)x, (int)y,  width, height);
	    
//	    g2.scale(1/Ivan.scale, 1/Ivan.scale);
		g.drawImage(sprite, x, y, null);
		
		
	}
	
	void loadPostions() {
		int x = 3;
		right = new BufferedImage[6];
		for(int i = 0; i < right.length; ++i) {
			right[i] = SpriteSheet.resize(Ivan.playerSpriteSheet.getSubimage(x, 1, 9, 15), width, height);
			x += 16;
		}
//		right[0] = Ivan.spriteSheet.getSubimage(3, 1, 9, 15);
//		right[1] = Ivan.spriteSheet.getSubimage(3+16, 1, 9, 15);
	}
	
	void setSpeed(float speed) {
		speedInUse[0] = -curSpeed;//left
		speedInUse[1] = curSpeed;//right
	}
	
	void damage(int damage) {health -= damage;}
	void dead() {health = 0;}
	
	void change(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		width = w;
		height = h;
	}
	
	void remove() {
		setX(0);
		setY(0);
		setWidth(0);
		setHeight(0);
	}
}
