import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Spike extends GameObject {
	
	private static final long serialVersionUID = 1L;
	
	public static Color DEF_COLOR = Color.MAGENTA;
	
	public Spike(int x, int y, ID id, int width, int height, Color color) {
		
		super(x, y, id, width, height, color);
		this.color = color;
		defColor = DEF_COLOR;
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, this.width, this.height);
	}
	
	public void tick() {
		if(getBounds().intersects(Ivan.player.getBounds()))
			Ivan.player.dead();;
	}
	
	public void render(Graphics2D g) {
		
		g.setColor(color);
		g.fillRect((int)x, (int)y, this.width, this.height);
	}
}
