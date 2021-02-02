import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.MouseInfo;

public class HUD {
	public int bounds = 0;
	public static float HEALTH = 100;
	public static float FPS;
	private static int mouseX = 0, mouseY = 0;
	public Player p;
	
	public HUD(Player p) {
		this.p = p;
	}
	
	public void tick() {
		//--HEALTH;
//		HEALTH = Ivan.clamp(HEALTH, 0, 100 + (bounds/2));
//		greenValue = HEALTH*2;
//		greenValue = Ivan.clamp(greenValue, 0, 255);
		
		
		//++score;
		try {
			mouseX = MouseInfo.getPointerInfo().getLocation().x;
			mouseY = MouseInfo.getPointerInfo().getLocation().y;			
		} catch(Exception e) {
			e.printStackTrace();
		}
				
	}
	
	public void render(Graphics2D g) {
		//g.scale(Ivan.scale, Ivan.scale);
//		g.setColor(Color.gray);
//		g.fillRect(15, 15, 200 + bounds, 32);
//		g.setColor(new Color(75, (int)greenValue, 0));
//		g.fillRect(15, 15, (int)HEALTH * 2, 32);
		g.setColor(Color.white);
//		g.drawRect(15, 15, 200 + bounds, 32);
//		g.drawString((int)HEALTH + "%", 15, 15);
		g.setFont(new Font("arial", 0, 30));
		g.drawString("X: " + p.x, 0, 30);
		g.drawString("Y: " + p.y, 0, 70);
		g.drawString("FPS: " + FPS, 1150, 40);
		
		g.drawString("Mouse coord: ", 1150, 100);
		g.drawString("X: " + mouseX, 1150, 140);
		g.drawString("Y: " + mouseY, 1150, 180);
//		g.drawString("Mouse coord: ", Ivan.WIDTH/2, Ivan.HEIGHT/2);
//		g.drawString("X: " + mouseX, Ivan.WIDTH/2, Ivan.HEIGHT/2 + 50);
//		g.drawString("Y: " + mouseY, Ivan.WIDTH/2, Ivan.HEIGHT/2+100);
		//g.scale(1/Ivan.scale, 1/Ivan.scale);
	}
	/*
	public void SetScore(int score) {
		this.score = score;
	}
	
	public int getScore() {
		return score;
	}
	
	public int getLevel() {
		return level;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	*/
}

