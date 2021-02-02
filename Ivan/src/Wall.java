import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Wall extends GameObject {
	
	private static final long serialVersionUID = 1L;
	
	private static int count = 0;
	private final int id = ++count;
	public static Color DEF_COLOR = Color.green;
	public Wall(int x, int y, ID id, int width, int height, Color color) {
		super(x, y, id, width, height, color);
		
		this.color = color;
		++count;
		defColor = Color.green;
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, this.width, this.height);
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics2D g) {
		
		g.setColor(this.color);
		g.fillRect((int)x, (int)y, this.width, this.height);
	}
}
