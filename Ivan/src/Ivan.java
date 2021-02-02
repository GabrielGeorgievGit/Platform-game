import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public class Ivan extends Canvas implements Runnable {

	private static final long serialVersionUID = 3627080020649188701L;
	public static Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
	public static double whichScale(int w, int h) {
		System.out.println(d.width+" " + d.height);
		if(w == 1366 && h == 768) return 1;
		if(w == 800 && h == 600) return 1;
		return 1;
	}
	public static double scale = whichScale(d.width, d.height);
	public static boolean defResolution = scale != 1 ? false : true;
	static int WIDTH =  d.width,
			HEIGHT = d.height;
	private Thread thread;
	static Player player;
	private Wall wall;
	private Color backgroundColor = Color.black;
	private Handler handler;
	private HUD hud;
	LevelEditor lvlEdit;
	
	public static BufferedImage playerSpriteSheet = null;
	public static BufferedImage bg = SpriteSheet.resize(new ImageLoader().loadImg("C:\\Users\\gabetoyes\\Desktop\\some\\game\\mgr.jpg"), 1920, 1080);
	private boolean running = false;
	
	public Ivan() {
		ImageLoader loader = new ImageLoader();
		
		try {
			playerSpriteSheet = loader.loadImg("C:\\Users\\gabetoyes\\Desktop\\some\\game\\player.png");
			System.out.println("p image loaded");
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		handler = new Handler();
		player = new Player(130, 0, ID.Player, (int)(100), (int)(150), 100, 3, Color.white, handler);
		
		this.addKeyListener(new KeyInput(handler, this, player));
		
		new Window(WIDTH, HEIGHT, "Ivan game", this);
		Enemy enemy = new Enemy(200, 200, ID.Enemy, 50, 50, 100, 2, Color.DARK_GRAY, handler);
		Door door = new Door(50, 50, ID.Door, 50, 50, Door.DEF_COLOR, "Level 1", "1", player);
		
		wall = new Wall(0, HEIGHT - 20, ID.Wall, 1000, 20, Color.green);
		hud = new HUD(player);
		lvlEdit = new LevelEditor(handler, hud);
		this.addMouseListener(lvlEdit);
		
		handler.addObject(player);
		handler.addObject(wall);
		handler.addObject(enemy);
		handler.addObject(door);
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	public synchronized void stop() {
		try {
			thread.join();
			running = false;
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while(running)
        {
	        long now = System.nanoTime();
	        delta += (now - lastTime) / ns;
	        lastTime = now;
	        while(delta >= 1)
            {
                tick();
                render();
                --delta;
                frames++;
            }
//          if(running)
//	        	render();
//	        frames++;
            
            if(System.currentTimeMillis() - timer > 1000)
            {
               timer += 1000;
               HUD.FPS = frames;
               //System.out.println("FPS: "+ frames);
               frames = 0;
            }
        }
        stop();
	}
	
	private void tick() {
		handler.tick();
//		player.tick();
//		wall.tick();
		hud.tick();
		LevelEditor.tick();
	}
	
	private void render() {
		
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null)
		{
			this.createBufferStrategy(3);//3 ili poveche
			return;
		}
		Graphics2D g2 = (Graphics2D) bs.getDrawGraphics(); // The newer more ellaborate child class.
//	    g2.scale(scale, scale);
	    
		//Graphics g = bs.getDrawGraphics();
		//g2.setColor(backgroundColor);
		g2.fillRect(0, 0, WIDTH, HEIGHT);
		g2.drawImage(bg, 0, 0, null);
		try {
			handler.render(g2);
			hud.render(g2);
			
//			lvlEdit.render(g2);
//		player.render(g);
			g2.dispose();
			bs.show();
//		g2.scale(1/scale, 1/scale);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static int clamp(int y, int min, int max) {
		if(y >= max) return y = max;
		else if(y <= min) return y = min;
		else return y;
		
	}
	
	public static void timer(int mSeconds) {
		try {
			TimeUnit.MILLISECONDS.sleep(mSeconds);
			
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}
	
	static class Bound {
		public Rectangle bound;
		public boolean collide;
		
		public static Bound doesCollide(float x, float y, int width, int height, LinkedList<GameObject> C) {

			float myLeft = x;
			float myRight = width + x;
			float myUp = y;
			float myDown = height + y;
			final float valX = 0, valY = 0;
			Bound bound = new Bound();
			for(int i = 0; i < C.size(); ++i) {
				
				GameObject o = C.get(i);
				if(o.id != ID.Wall) continue;
				
				float otherLeft = o.x;// + valX;
				float otherRight = o.width + o.x - valX;
				float otherUp = o.y + valY;
				float otherDown = o.height + o.y - valY;
				
				if(myLeft < otherRight && myRight > otherLeft &&
						myDown > otherUp && myUp < otherDown) {
					if(myUp < otherDown) y = 0;
//					bound.bound = wallBound;
					bound.bound = o.getBounds();
					bound.collide = true;
					return bound;
				}
			}
			bound.collide = false;
			return bound;
		}
	}
	
	public static void main(String[] args) {
		new Ivan();
	}
}
