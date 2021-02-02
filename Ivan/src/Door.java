import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Door extends GameObject {
	
	private static final long serialVersionUID = 1L;
	
	private static int count = 0;
	private final int id = ++count;
	public static Color DEF_COLOR = Color.cyan;
//	private Player player;
	private String level, scene;
	
	public Door(int x, int y, ID id, int width, int height, 
			Color color, String level, String scene, Player player) {
		
		super(x, y, id, width, height, color);
//		this.player = player;
		this.color = color;
		++count;
		defColor = DEF_COLOR;
		this.level = level;
		this.scene = scene;
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, this.width, this.height);
	}
	
	public void tick() {
		if(getBounds().intersects(Ivan.player.getBounds()))
			LevelEditor.changeStage(level, scene);
	}
	
	public void render(Graphics2D g) {
		
		g.setColor(color);
		g.fillRect((int)x, (int)y, this.width, this.height);
	}
	
	public String getLevel() {return level;}
	public String getScene() {return scene;}
	
	public void setLevel(String lvl) {level = lvl;}
	public void setScene(String scen) {scene = scen;}
}
